package app.justlykbot.gui.layouts;

import java.util.ArrayList;

import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.filter.MessageTypeFilter;
import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.util.StringUtils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import app.justlykbot.R;
import app.justlykbot.datatypes.Server;
import app.justlykbot.datatypes.User;

public class ChatClient extends Activity{
	
	//Layout Variables
	private TextView mRecipient;
	private EditText mSendText;
	private ListView mList;
	
	//Data Variables
	private ArrayList<String> messages = new ArrayList();
	private Handler mHandler = new Handler();
	private User recipient;
	private User appUser;
	private String message;
	private Server xmppServer;
	
	//XMPP Variables
	private XMPPConnection connection;
	
	//Preferences
	SharedPreferences prefs;
	
	//Other Variables
	public static boolean active = false;

	
	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.chat);
		
		initializeServerObject();

		//Receive info from caller class
		Intent intent = getIntent();
		recipient = (User) intent.getSerializableExtra("FriendObject");
		appUser = (User) intent.getSerializableExtra("UserObject");
		
		try{
			message = intent.getStringExtra("Message");
			if(!message.equals(""))
			{
				messages.add("[["+recipient.getName() + "]]:");
				messages.add(message);
			}

		}catch(NullPointerException e)
		{
			message = "";
		}


		mRecipient = (TextView) this.findViewById(R.id.recipientName);
		Log.i("XMPPClient", "mRecipient = " + mRecipient);
		mRecipient.setText(recipient.getName());
		mSendText = (EditText) this.findViewById(R.id.sendText);
		Log.i("XMPPClient", "mSendText = " + mSendText);
		mList = (ListView) this.findViewById(R.id.listMessages);
		Log.i("XMPPClient", "mList = " + mList);
		setListAdapter();

		getSettings();


		// Set a listener to send a chat text message
		Button send = (Button) this.findViewById(R.id.send);
		send.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				String to = recipient.getXmppID() +"@"+xmppServer.getDomain(); //FIXME
				String text = mSendText.getText().toString();

				Log.i("XMPPClient", "Sending text [" + text + "] to [" + to + "]");
				Message msg = new Message(to, Message.Type.chat);
				msg.setBody(text);
				connection.sendPacket(msg);
				messages.add("[["+appUser.getName() + "]]:");
				messages.add(text);
				setListAdapter();
				mSendText.setText("");
			}
		});
	}

	private void setListAdapter
	() {
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				R.layout.multi_line_list_item_xmpp,
				messages);
		mList.setAdapter(adapter);
	}

	public void getSettings()
	{
		connection = JustLYKBot.xmppConnection;	
		this.setConnection(connection);
		Log.i("XMPPClient", "Connection ID:" + connection.getConnectionID());
	}

	/**
	 * Called by Settings dialog when a connection is establised with the XMPP server
	 *
	 * @param connection
	 */
	public void setConnection
	(XMPPConnection
			connection) {
		this.connection = connection;
		if (connection != null) {
			// Add a packet listener to get messages sent to us
			PacketFilter filter = new MessageTypeFilter(Message.Type.chat);
			connection.addPacketListener(new PacketListener() {
				public void processPacket(Packet packet) {
					Message message = (Message) packet;
					if (message.getBody() != null) {
						String fromName = StringUtils.parseBareAddress(recipient.getName());
						Log.i("XMPPClient", "Got text [" + message.getBody() + "] from [" + fromName + "]");
						messages.add("[["+fromName + "]]:");
						messages.add(message.getBody());
						// Add the incoming message to the list view
						mHandler.post(new Runnable() {
							public void run() {
								setListAdapter();
							}
						});
					}
				}
			}, filter);
		}
	}


	@Override
	public void onStart() {
		super.onStart();
		active = true;
	} 

	@Override
	public void onStop() {
		super.onStop();
		active = false;
	}
	
	public void initializeServerObject()
	{
		xmppServer = new Server();
		prefs = getSharedPreferences("XMPP", Context.MODE_PRIVATE);
		xmppServer.setDomain(prefs.getString("domain", ""));
		xmppServer.setFunction(prefs.getString("function", ""));
		xmppServer.setIpAddress(prefs.getString("ipAddress", ""));
		xmppServer.setName(prefs.getString("name", ""));
		xmppServer.setPort(prefs.getString("port", ""));
		xmppServer.setService(prefs.getString("service", ""));
		xmppServer.setXcapRoot(prefs.getString("xcap_root", ""));
		
		
	}
}
