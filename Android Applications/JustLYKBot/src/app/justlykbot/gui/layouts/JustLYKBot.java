package app.justlykbot.gui.layouts;

import java.text.ParseException;
import java.util.List;
import java.util.Locale;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.filter.MessageTypeFilter;
import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.util.StringUtils;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.sip.SipAudioCall;
import android.net.sip.SipException;
import android.net.sip.SipManager;
import android.net.sip.SipProfile;
import android.net.sip.SipRegistrationListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import app.justlykbot.R;
import app.justlykbot.datatypes.Server;
import app.justlykbot.datatypes.User;
import app.justlykbot.localdata.DataLoader;


public class JustLYKBot extends FragmentActivity implements
ActionBar.TabListener {

	/********************************************************************
	 * Constants
	 ********************************************************************/
	public static final int HOME_TAB = 0;
	public static final int GAMES_TAB = 1;
	public static final int FRIENDS_TAB = 2;
	public static final int PROFILE_TAB = 3;

	public static final String XMPP_TAG = "XMPPClient";
	public static final String SIP_TAG = "SIPClient";


	/********************************************************************
	 * Attributes
	 ********************************************************************/
	//------------- Layout-related 
	SectionsPagerAdapter mSectionsPagerAdapter;
	ViewPager mViewPager;
	private boolean resumeHasRun = false;


	//------------- Handle
	public JustLYKBot handle = this;

	//------------- Servers	
	//XMPP
	public static XMPPConnection xmppConnection;
	public static boolean xmppConnectionError = true;
	private Server xmppServer = new Server();
	//SIP
	public static boolean sipConnectionError = true;
	private Server sipServer = new Server();
	public static SipManager sipManager = null;
	public static SipProfile localSipProfile = null;
	public static SipAudioCall call = null;

	//------------- Preferences and Data
	SharedPreferences sipPref, xmppPref, gamePref;
	boolean wasXmppDialogDisplayed = false;
	boolean wasSipDialogDisplayed = false;
	boolean isSipSetUp = false;
	boolean isXmppSetUp = false;
	static User appUser;
	static DataLoader data;



	/********************************************************************
	 * Methods
	 ********************************************************************/

	/**********************************
	 * OnCreate
	 **********************************/
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_tabs);

		Intent intent = getIntent();
		appUser = (User) intent.getSerializableExtra("appUser");
		data = Login.data;


		guiSetup();
		getNetworkPreferences();
		setupServersConnections();

	}

	/**********************************
	 * Options Menu
	 **********************************/
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.just_lykbot, menu);	

		return true;
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch(item.getItemId())
		{
		default:
			Intent settingsIntent = new Intent(JustLYKBot.this, Settings.class);
			startActivity(settingsIntent);
			return super.onOptionsItemSelected(item);
		}
	}

	/**********************************
	 * Tabs
	 **********************************/
	@Override
	public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
		// When the given tab is selected, switch to the corresponding page in
		// the ViewPager.
		mViewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabReselected(Tab arg0, FragmentTransaction arg1) {
	}

	@Override
	public void onTabUnselected(Tab arg0, FragmentTransaction arg1) {
	}

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			// getItem is called to instantiate the fragment for the given page.
			// Return a DummySectionFragment (defined as a static inner class
			// below) with the page number as its lone argument.
			Fragment fragment = new DummySectionFragment(position);
			Bundle args = new Bundle();
			args.putInt(DummySectionFragment.ARG_SECTION_NUMBER, position + 1);
			fragment.setArguments(args);
			return fragment;
		}

		@Override
		public int getCount() {
			// Show 3 total pages.
			return 4;
		}

		@Override
		public CharSequence getPageTitle(int position) {

			Locale l = Locale.getDefault();
			switch (position) {
			case 0:
				return getString(R.string.title_section1).toUpperCase(l);
			case 1:
				return getString(R.string.title_section2).toUpperCase(l);
			case 2:
				return getString(R.string.title_section3).toUpperCase(l);
			case 3:
				return getString(R.string.title_section4).toLowerCase(l);
			}
			return null;
		}
	}

	/**
	 * A dummy fragment representing a section of the app, but that simply
	 * displays dummy text.
	 */
	public static class DummySectionFragment extends Fragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		public static final String ARG_SECTION_NUMBER = "section_number";

		int position=0;

		public DummySectionFragment(int position) {
			this.position = position;
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

			switch(position)
			{
			case HOME_TAB:
				View homeRootView = inflater.inflate(R.layout.home,container, false);
				return new HomeTab().loadContent(homeRootView, appUser, data);
			case GAMES_TAB:
				View gamesRootView = inflater.inflate(R.layout.games,container, false);
				View gamesFooterView = inflater.inflate(R.layout.add_more_footer, null);	
				return new GamesTab().loadContent(gamesRootView, gamesFooterView, appUser, data);
			case FRIENDS_TAB:
				View friendsRootView = inflater.inflate(R.layout.friends,container, false);
				View friendsFooterView = inflater.inflate(R.layout.add_more_footer, null);
				return new FriendsTab().loadContent(friendsRootView, friendsFooterView, appUser, data);
			case PROFILE_TAB:
				View profileRootView = inflater.inflate(R.layout.app_user_profile,container, false);
				return new ProfileTab().loadContent(profileRootView, appUser, data);

			default:
				return null;

			}

		}
	}

	public void guiSetup()
	{		
		// Set up the action bar.
		final ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		// Create the adapter that will return a fragment for each of the three
		// primary sections of the app.
		mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);

		// When swiping between different sections, select the corresponding
		// tab. We can also use ActionBar.Tab#select() to do this if we have
		// a reference to the Tab.
		mViewPager
		.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				actionBar.setSelectedNavigationItem(position);
			}
		});

		// For each of the sections in the app, add a tab to the action bar.
		for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
			// Create a tab with text corresponding to the page title defined by
			// the adapter. Also specify this Activity object, which implements
			// the TabListener interface, as the callback (listener) for when
			// this tab is selected.
			actionBar.addTab(actionBar.newTab()
					.setText(mSectionsPagerAdapter.getPageTitle(i))
					.setTabListener(this));

		}
	}

	public void getNetworkPreferences()
	{				
		//SIP
		sipPref = this.getSharedPreferences("SIP", Context.MODE_PRIVATE);
		if(sipPref.getString("ipAddress", "").equals(""))
		{
			isSipSetUp = false;
			if(!wasSipDialogDisplayed)
				showNoServerDetectedMessage("sip");
			wasSipDialogDisplayed = true;
		}else{
			sipServer.setDomain(sipPref.getString("domain", ""));
			sipServer.setFunction(sipPref.getString("function", ""));
			sipServer.setIpAddress(sipPref.getString("ipAddress", ""));
			sipServer.setName(sipPref.getString("name", ""));
			sipServer.setPort(sipPref.getString("port", ""));
			sipServer.setService(sipPref.getString("service", ""));
			sipServer.setXcapRoot(sipPref.getString("xcap_root", ""));	

			isSipSetUp = true;
		}

		//XMPP
		xmppPref = this.getSharedPreferences("XMPP", Context.MODE_PRIVATE);
		if(xmppPref.getString("ipAddress", "").equals(""))
		{
			isXmppSetUp = false;
			if(!wasXmppDialogDisplayed)
				showNoServerDetectedMessage("xmpp");
			wasXmppDialogDisplayed = true;
		}else{
			xmppServer.setDomain(xmppPref.getString("domain", ""));
			xmppServer.setFunction(xmppPref.getString("function", ""));
			xmppServer.setIpAddress(xmppPref.getString("ipAddress", ""));
			xmppServer.setName(xmppPref.getString("name", ""));
			xmppServer.setPort(xmppPref.getString("port", ""));
			xmppServer.setService(xmppPref.getString("service", ""));
			xmppServer.setXcapRoot(xmppPref.getString("xcap_root", ""));

			isXmppSetUp = true;
		}		
	}

	public void showNoServerDetectedMessage(String serverType)
	{
		if(serverType.equals("sip"))
		{
			DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					switch (which){
					case DialogInterface.BUTTON_POSITIVE:
						//Yes button clicked
						Intent settingsIntent = new Intent(JustLYKBot.this, Settings.class);
						startActivity(settingsIntent);
						break;

					case DialogInterface.BUTTON_NEGATIVE:
						//No button clicked
						break;
					}
				}
			};

			AlertDialog.Builder builder = new AlertDialog.Builder(handle);
			builder.setMessage(handle.getResources().getString(R.string.no_sip_message))
			.setPositiveButton(handle.getResources().getString(R.string.message_yes), dialogClickListener)
			.setNegativeButton(handle.getResources().getString(R.string.message_no), dialogClickListener).show();			
		}
		else if(serverType.equals("xmpp"))
		{
			DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					switch (which){
					case DialogInterface.BUTTON_POSITIVE:
						//Yes button clicked
						Intent settingsIntent = new Intent(JustLYKBot.this, Settings.class);
						startActivity(settingsIntent);
						break;

					case DialogInterface.BUTTON_NEGATIVE:
						//No button clicked
						break;
					}
				}
			};

			AlertDialog.Builder builder = new AlertDialog.Builder(handle);
			builder.setMessage(handle.getResources().getString(R.string.no_xmpp_message))
			.setPositiveButton(handle.getResources().getString(R.string.message_yes), dialogClickListener)
			.setNegativeButton(handle.getResources().getString(R.string.message_no), dialogClickListener).show();
		}
	}

	public void setupServersConnections()
	{
		if(isXmppSetUp)
		{
			try{
				// XMPP Server
				XmppServerConnection xmppServerConnection = new XmppServerConnection(this, xmppServer);
				xmppServerConnection.execute("","","");
			}catch (Exception e)
			{
				showFailedToRegisterSipDialog();
			}
		}else
			showFailedToRegisterSipDialog();

		if(isSipSetUp)
		{
			try{
				SipServerConnection sipServerConnection = new SipServerConnection(this, sipServer);
				sipServerConnection.execute("","","");
			}
			catch (Exception e)
			{
				showFailedToConnectToXmppDialog();
			}
		}else
			showFailedToConnectToXmppDialog();

	}

	private class XmppServerConnection extends AsyncTask<String, String, String> 
	{
		// XMPP Variables
		public Presence xmppPresence;
		public ConnectionConfiguration connConfig;

		String host, port, service, username, password;

		// Other Variables
		Context context;
		Server xmppServer;
		User appUser, recipient;

		public XmppServerConnection(Context context, Server xmppServer)
		{
			this.context = context;
			this.xmppServer = xmppServer;
		}

		@Override
		protected String doInBackground(String... params) {

			initializeAppUser();			
			getXmppSettings(); 

			try{
				createXmppConnection();
			}catch(Exception e)
			{
				Toast.makeText(getApplicationContext(), "Xmpp Error", Toast.LENGTH_SHORT).show();
			}

			return null;
		}

		@Override
		protected void onPostExecute (String result) {
			if(xmppConnectionError)
			{
				showFailedToConnectToXmppDialog();
			}
		}

		public void initializeAppUser()
		{
			appUser = data.getAppUser();
		}

		public void setXmppConnection(XMPPConnection connection) {
			xmppConnection = connection;

			if (connection != null) {
				// Add a packet listener to get messages sent to us
				PacketFilter filter = new MessageTypeFilter(Message.Type.chat);
				connection.addPacketListener(new PacketListener() {
					public void processPacket(Packet packet) {
						Message message = (Message) packet;
						if (message.getBody() != null) {
							String fromName = StringUtils.parseBareAddress(message.getFrom());
							Log.i(XMPP_TAG, "Got text [" + message.getBody() + "] from [" + fromName + "]");

							Intent x = new Intent(context, ChatClient.class);
							recipient = getUserFromXmppAddress(message.getFrom());
							Log.i(XMPP_TAG, recipient.getName());
							if(!recipient.equals(null)){
								x.putExtra("FriendObject", recipient);
								x.putExtra("UserObject", appUser);
								x.putExtra("Message", message.getBody());
								if(!ChatClient.active)context.startActivity(x);		

							}
						}
					}
				}, filter);
			}
		}

		public void getXmppSettings() {

			host = xmppServer.getIpAddress();
			port = xmppServer.getPort();
			service =  xmppServer.getService();
			username = appUser.getXmppID();
			password = appUser.getXmppPass();
		}

		public void createXmppConnection()
		{
			// Create a connection
			connConfig = new ConnectionConfiguration(host, Integer.parseInt(port), service);
			xmppConnection = new XMPPConnection(connConfig);

			try {
				xmppConnection.connect();
				Log.i(XMPP_TAG, "[SettingsDialog] Connected to " + xmppConnection.getHost());
				Log.i(XMPP_TAG, "[SettingsDialog] Connection ID: " + xmppConnection.getConnectionID());
				xmppConnectionError = false;
			} catch (XMPPException ex) {
				Log.e(XMPP_TAG, "[SettingsDialog] Failed to connect to " + xmppConnection.getHost());
				Log.e(XMPP_TAG, ex.toString());
				this.setXmppConnection(null);
				xmppConnectionError = true;
			}
			if(!xmppConnectionError){
				try {
					xmppConnection.login(username, password);
					Log.i(XMPP_TAG, "Logged in as " + xmppConnection.getUser());
					// Set the status to available
					xmppPresence = new Presence(Presence.Type.available);
					xmppConnection.sendPacket(xmppPresence);
					this.setXmppConnection(xmppConnection);
				} catch (XMPPException ex) {
					Log.e(XMPP_TAG, "[SettingsDialog] Failed to log in as " + username);
					Log.e(XMPP_TAG, ex.toString());
					this.setXmppConnection(null);
					xmppConnectionError=true;
				}
			}
		}

		private User getUserFromXmppAddress(String xmppAddress)
		{

			Log.i(XMPP_TAG, "XMPP ADDRESS:"+xmppAddress);

			int dot = xmppAddress.indexOf("@");
			String xmppName = xmppAddress.substring(0, dot);

			Log.i(XMPP_TAG, "XMPP NAME:"+xmppName);


			List<User> friend= appUser.getFriend();
			for(int i=0; i<appUser.getFriend().size(); i++)
			{
				if(xmppName.equals(friend.get(i).getXmppID()))
				{
					Log.i(XMPP_TAG, "Friend NAME:"+friend.get(i).getName());
					return friend.get(i);
				}
			}
			return null;
		}



	}

	private class SipServerConnection extends AsyncTask<String, String, String>
	{
		// Other Variables
		Context context;
		Server sipServer;
		User appUser, recipient;

		//SIP
		IncomingCallReceiver callReceiver;

		String username, password, domain, xcapRoot;

		public SipServerConnection(Context context, Server sipServer)
		{
			this.context = context;
			this.sipServer = sipServer;
		}

		@Override
		protected String doInBackground(String... params) {

			initializeAppUser();
			initializeManager();
			initializeLocalProfile();
			registerCallReceiver();

			return null;
		}

		@Override
		protected void onPostExecute (String result) {
			if(sipConnectionError)
			{
				showFailedToRegisterSipDialog();
			}
		}

		public void initializeAppUser()
		{
			appUser = data.getAppUser();
		}

		public void initializeManager() {
			if(sipManager == null) {
				sipManager = SipManager.newInstance(handle);
			}			
		}

		public void initializeLocalProfile() {
			/**
			 * Logs you into your SIP provider, registering this device as the location to
			 * send SIP calls to for your SIP address.
			 */
			if (sipManager == null) {
				Log.e(SIP_TAG, "sipManager is null");
				showFailedToRegisterSipDialog();
				return;
			}

			if (localSipProfile != null) {
				closeLocalProfile();
			}

			//Note: XcapRoot for Sip2sip.info is "https://xcap.sipthor.net/xcap-root"
			username = appUser.getSipID();
			password = appUser.getSipPass();
			domain = sipServer.getIpAddress();
			xcapRoot = sipServer.getXcapRoot();

			try {

				SipProfile.Builder builder = new SipProfile.Builder(username, domain);
				builder.setPassword(password);
				// Note: for using Sip2sip.info: 
				// builder.setOutboundProxy("proxy.sipthor.net");
				localSipProfile = builder.build();

				sipManager.setRegistrationListener(localSipProfile.getUriString(), new SipRegistrationListener() {
					public void onRegistering(String localProfileUri) {
						Log.i(SIP_TAG, "Registering with SIP Server...");
					}

					public void onRegistrationDone(String localProfileUri, long expiryTime) {
						Log.i(SIP_TAG, "Ready");
						sipConnectionError = false;
					}

					public void onRegistrationFailed(String localProfileUri, int errorCode,
							String errorMessage) {
						Log.i(SIP_TAG, "Registration failed.  Please check settings.");
						showFailedToRegisterSipDialog();
						sipConnectionError = true;
					}
				});


			} catch (ParseException pe) {
				Log.i(SIP_TAG, "Connection Error.");
				showFailedToRegisterSipDialog();
				sipConnectionError = true;
			} catch (SipException se) {
				Log.i(SIP_TAG, "Connection Error.");
				showFailedToRegisterSipDialog();
				sipConnectionError = true;
			}
		}

		public void closeLocalProfile() {
			/**
			 * Closes out your local profile, freeing associated objects into memory
			 * and unregistering your device from the server.
			 */
			if (sipManager == null) {
				return;
			}
			try {
				if (localSipProfile != null) {
					sipManager.close(localSipProfile.getUriString());

				}
			} catch (Exception ee) {
				Log.e(SIP_TAG, "Failed to close local profile.");
			}
		}

		public void registerCallReceiver()
		{
			Intent i = new Intent();
			i.setAction("android.SipDemo.INCOMING_CALL");
			PendingIntent pi = PendingIntent.getBroadcast(handle, 0, i, Intent.FILL_IN_DATA);
			try {
				sipManager.open(localSipProfile, pi, null);
			} catch (SipException e) {
				showFailedToRegisterSipDialog();
				e.printStackTrace();
			}

			IntentFilter filter = new IntentFilter();
			filter.addAction("android.SipDemo.INCOMING_CALL");
			callReceiver = new IncomingCallReceiver();
			handle.registerReceiver(callReceiver, filter);
		}

		public void receiveCall(SipAudioCall incomingCall)
		{
			User caller = null;
			String callerName = "";
			call = incomingCall;

			caller = getUserFromSipAddress(incomingCall.getPeerProfile().getUserName());
			callerName = caller.getName();

			showIncomingCallDialog(callerName);

		}

		public void showIncomingCallDialog(String callerName)
		{

			final Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
			long[] pattern = { 0, 200, 500 };

			vibrator.vibrate(pattern, 0);

			DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					switch (which){
					case DialogInterface.BUTTON_POSITIVE:
						//Yes button clicked
						vibrator.cancel();
						acceptCall();
						break;

					case DialogInterface.BUTTON_NEGATIVE:
						//No button clicked
						vibrator.cancel();
						rejectCall();
						break;
					}
				}
			};

			AlertDialog.Builder builder = new AlertDialog.Builder(handle);
			builder.setMessage("["+callerName+ "]\n " + handle.getResources().getString(R.string.incoming_call_notifiation))
			.setPositiveButton(handle.getResources().getString(R.string.message_yes), dialogClickListener)
			.setNegativeButton(handle.getResources().getString(R.string.message_no), dialogClickListener).show();
		}

		public void acceptCall()
		{
			try {

				User caller = null;
				String callerName = "";
				caller = getUserFromSipAddress(call.getPeerProfile().getUserName());
				callerName = caller.getName();			
				call.answerCall(30);
				call.startAudio();
				call.setSpeakerMode(true);
				if(call.isMuted()) {
					call.toggleMute();
				}	
				
				AlertDialog ad = new AlertDialog.Builder(handle).create();  
				ad.setCancelable(true); // This blocks the 'BACK' button  
				ad.setMessage(getResources().getString(R.string.talking_to)+"\n"+ callerName);  
				ad.setButton(getResources().getString(R.string.hang_up), new DialogInterface.OnClickListener() {  
					@Override  
					public void onClick(DialogInterface dialog, int which) {  
						rejectCall();
						dialog.dismiss();                      
					}  
				});  
				ad.show();

			} catch (SipException e) {
				e.printStackTrace();
			}
		}
		
		public void rejectCall()
		{
			if(call != null) {
				try {
					call.endCall();
				} catch (SipException se) {
					Log.d(SIP_TAG,"Error ending call.", se);
				}
				call.close();
			}

		}

		public User getUserFromSipAddress(String sipAddress)
		{
			List<User> friends = appUser.getFriend();
			for(User friend: friends)
			{
				if(sipAddress.equals(friend.getSipID()))
					return friend;
			}
			return null;
		}


		private class IncomingCallReceiver extends BroadcastReceiver {

			@Override	
			public void onReceive(Context context, Intent intent) {
				SipAudioCall incomingCall = null;

				Log.d(SIP_TAG, "IncomingCallReceiver Started");

				try {
					SipAudioCall.Listener listener = new SipAudioCall.Listener() {};

					incomingCall = sipManager.takeAudioCall(intent, listener);
					receiveCall(incomingCall);


				} catch (Exception e) {
					if (incomingCall != null) {
						incomingCall.close();
					}
				}
			}
		}

	}

	public void showFailedToRegisterSipDialog()
	{
		AlertDialog ad = new AlertDialog.Builder(handle).create();  
		ad.setCancelable(false); // This blocks the 'BACK' button  
		ad.setMessage(getResources().getString(R.string.connection_error_sip));  
		ad.setButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {  
			@Override  
			public void onClick(DialogInterface dialog, int which) {  
				dialog.dismiss();                      
			}  
		});  
		ad.show();
	}

	public void showFailedToConnectToXmppDialog()
	{
		AlertDialog ad = new AlertDialog.Builder(handle).create();  
		ad.setCancelable(false); // This blocks the 'BACK' button  
		ad.setMessage(getResources().getString(R.string.connection_error_xmpp));  
		ad.setButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {  
			@Override  
			public void onClick(DialogInterface dialog, int which) {  
				dialog.dismiss();                      
			}  
		});  
		ad.show();
	}

}
