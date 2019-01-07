package app.justlykbot.gui.layouts;

import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import app.justlykbot.R;
import app.justlykbot.datatypes.ChallengeRequest;
import app.justlykbot.datatypes.FriendRequest;
import app.justlykbot.datatypes.Game;
import app.justlykbot.datatypes.User;
import app.justlykbot.gui.listadapters.ChallengeRequestAdapter;
import app.justlykbot.gui.listadapters.FriendRequestAdapter;
import app.justlykbot.localdata.DataLoader;

public class HomeTab{

	//GUI variables
	View rootView;
	TextView challengeRequestMessage, friendRequestMessage, challengeRequestsCount, friendRequestsCount;
	ImageView challengeRequestNotification;
	ImageView friendRequestNotification;
	ListView challengeRequestsList;
	ListView friendRequestsList;

	//Data variables
	List<FriendRequest> friendRequests;
	List<ChallengeRequest> challengeRequests;
	DataLoader data;
	User appUser;

	

	public View loadContent(View rootView, User appUser, DataLoader data)
	{
		this.rootView = rootView;
		
		guiSetup();

		this.appUser = appUser;
		this.data = data;

		loadChallengeRequests();
		if(challengeRequests.size()!=0)
		{
			challengeRequestNotification.setImageResource(R.drawable.new_item);
			challengeRequestMessage.setText(R.string.challenge_requests_yes);
			challengeRequestsCount.setText(challengeRequests.size()+" ");
			
			displayChallengeRequests();			
			
		}
		else
		{
			challengeRequestNotification.setImageResource(R.drawable.old_item);
			challengeRequestMessage.setText(R.string.challenge_requests_no);
		}

		loadFriendRequests();
		if(friendRequests.size()!=0)
		{
			friendRequestNotification.setImageResource(R.drawable.new_item);
			friendRequestMessage.setText(R.string.friend_requests_yes);
			friendRequestsCount.setText(friendRequests.size()+" ");
			
			displayFriendRequests();
		}else
		{
			friendRequestNotification.setImageResource(R.drawable.old_item);
			friendRequestMessage.setText(R.string.friend_requests_no);
		}


		return rootView;
	}

	
	public void loadChallengeRequests()
	{
		challengeRequests = appUser.getChallengeRequest();
	}
	
	public void displayChallengeRequests()
	{
        ChallengeRequestAdapter adapter = new ChallengeRequestAdapter
        (rootView.getContext(), R.layout.list_item, challengeRequests);
        challengeRequestsList.setAdapter(adapter);
        
        challengeRequestsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {				

				goToGameOverview(challengeRequests.get(position).getGame());

			}
		});
	}

		
	public void loadFriendRequests()
	{
		friendRequests = appUser.getFriendRequest();
	}
	
	public void displayFriendRequests()
	{
        FriendRequestAdapter adapter = new FriendRequestAdapter
        (rootView.getContext(), R.layout.list_item, friendRequests);
        friendRequestsList.setAdapter(adapter);
        
        friendRequestsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {	
				
				final int requestId = friendRequests.get(position).getRequestId();

				DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
				    @Override
				    public void onClick(DialogInterface dialog, int which) {
				        switch (which){
				        case DialogInterface.BUTTON_POSITIVE:
				            //Yes button clicked
				        	data.respondToFriendRequest(requestId, "yes");
				            break;

				        case DialogInterface.BUTTON_NEGATIVE:
				            //No button clicked
				        	data.respondToFriendRequest(requestId, "no");
				            break;
				        }
				    }
				};

				AlertDialog.Builder builder = new AlertDialog.Builder(rootView.getContext());
				builder.setMessage(rootView.getContext().getResources().getString(R.string.friend_request_prompt))
				.setPositiveButton(rootView.getContext().getResources().getString(R.string.message_yes), dialogClickListener)
				    .setNegativeButton(rootView.getContext().getResources().getString(R.string.message_no), dialogClickListener).show();

			}
		});
        
        
	}
	
	
	public void goToGameOverview(Game game)
	{
		Intent intent = new Intent(rootView.getContext(), GameOverview.class);
		intent.putExtra("game", game);
		intent.putExtra("appUser", appUser);
		rootView.getContext().startActivity(intent);
	}
	
	public void guiSetup()
	{
		
		challengeRequestMessage = (TextView) rootView.findViewById(R.id.home_textView_challengeRequests);
		friendRequestMessage = (TextView) rootView.findViewById(R.id.home_textView_friendRequests);
		
		challengeRequestsCount= (TextView) rootView.findViewById(R.id.home_textView_challengeRequests_count);
		friendRequestsCount = (TextView) rootView.findViewById(R.id.home_textView_friendRequests_count);
		
		challengeRequestNotification = (ImageView) rootView.findViewById(R.id.home_imageView_challengeRequestNotification);
		friendRequestNotification = (ImageView) rootView.findViewById(R.id.home_imageView_friendRequestNotification);
		
		challengeRequestsList = (ListView) rootView.findViewById(R.id.home_listView_challengeRequests);		
		friendRequestsList = (ListView) rootView.findViewById(R.id.home_listView_friendRequests);
	}
	
	
}
