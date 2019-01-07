package app.justlykbot.gui.layouts;

import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import app.justlykbot.R;
import app.justlykbot.datatypes.User;
import app.justlykbot.gui.listadapters.UserAdapter;
import app.justlykbot.localdata.DataLoader;

public class FriendsTab {

	//GUI variables
	View rootView;
	View footerView;
	ListView friendsList;

	//Data variables
	User appUser;
	DataLoader data;
	List<User> friends;

	public View loadContent(View rootView, View footerView, User appUser, DataLoader data)
	{
		this.rootView = rootView;
		this.footerView = footerView;

		guiSetup();

		this.appUser = appUser;
		this.data = data;


		loadFriends();
		displayFriends();

		return rootView;
	}

	public void loadFriends()
	{
		friends = appUser.getFriend();
	}

	public void displayFriends()
	{
		UserAdapter adapter = new UserAdapter
				(rootView.getContext(), R.layout.list_item, R.layout.add_more_footer, friends);
		friendsList.setAdapter(adapter);

		friendsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {				

				goToFriendProfile(friends.get(position));

			}
		});
	}

	public void guiSetup()
	{
		friendsList = (ListView) rootView.findViewById(R.id.friends_listView_friends);
		friendsList.addFooterView(footerView);

		footerView.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				showAddNewFriendDialog();
			}
		});

	}

	public void goToFriendProfile(User friend)
	{
		Intent friendProfileIntent = new Intent(rootView.getContext(), FriendProfile.class);
		friendProfileIntent.putExtra("friendObject", friend);
		friendProfileIntent.putExtra("appUser", appUser);
		rootView.getContext().startActivity(friendProfileIntent);
	}

	public void showAddNewFriendDialog()
	{
		AlertDialog.Builder alert = new AlertDialog.Builder(rootView.getContext());

		alert.setTitle(rootView.getContext().getResources().getString(R.string.add_friend_message_dialog_title));
		alert.setMessage(rootView.getContext().getResources().getString(R.string.add_friend_message_dialog));

		// Set an EditText view to get user input 
		final EditText input = new EditText(rootView.getContext());
		alert.setView(input);

		alert.setPositiveButton(rootView.getContext().getResources().getString(R.string.message_ok), new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				String username = input.getText().toString();

				data.sendFriendRequest(appUser.getName(), username);

			}
		});

		alert.setNegativeButton(rootView.getContext().getResources().getString(R.string.message_cancel), new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				// Canceled.
			}
		});

		alert.show();
	}
}
