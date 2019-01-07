package app.justlykbot.gui.layouts;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import app.justlykbot.R;
import app.justlykbot.datatypes.Server;
import app.justlykbot.localdata.DataLoader;

public class Settings extends Activity{

	//Layout Variables
	ListView sipServersList, xmppServersList, gameServersList;
	Button differentSipBtn, differentXmppBtn, differentGameBtn, clearBtn, showCurrentBtn;

	//Data Variables
	DataLoader data;
	List<Server> servers;
	List<Server> sipServers = new ArrayList<Server>();
	List<Server> xmppServers = new ArrayList<Server>();
	Server sipServer;
	Server xmppServer;

	//Preferences Variables
	Settings handle = this;
	SharedPreferences pref;
	SharedPreferences.Editor sipEditor;
	SharedPreferences.Editor xmppEditor;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings);

		data = Login.data;

		guiSetup();
		initializePreferences();
		loadServers();
		showServers();
		buttonsSetup();

	}

	public void guiSetup()
	{
		sipServersList = (ListView) findViewById(R.id.settings_listView_sipServers);
		differentSipBtn = (Button) findViewById(R.id.settings_Button_connectToDifferentSip);

		xmppServersList = (ListView) findViewById(R.id.settings_listView_xmppServers);
		differentXmppBtn = (Button) findViewById(R.id.settings_Button_connectToDifferentXmpp);

		clearBtn = (Button) findViewById(R.id.settings_Button_clearSettings);
		showCurrentBtn = (Button) findViewById(R.id.settings_Button_showCurrentSettings);
	}

	public void buttonsSetup()
	{
		sipServersList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {	
				sipEditor.putString("domain", sipServers.get(position).getDomain()).commit();
				sipEditor.putString("function", sipServers.get(position).getFunction()).commit();
				sipEditor.putString("ipAddress", sipServers.get(position).getIpAddress()).commit();
				sipEditor.putString("name", sipServers.get(position).getName()).commit();
				sipEditor.putString("port", sipServers.get(position).getPort()).commit();
				sipEditor.putString("service", sipServers.get(position).getService()).commit();
				sipEditor.putString("xcap_root", sipServers.get(position).getXcapRoot()).commit();
				
				sipServer = sipServers.get(position);
				
				Toast.makeText(getApplicationContext(), sipServers.get(position).toString()+"\n"
						+handle.getResources().getString(R.string.saved_to_preferences), Toast.LENGTH_SHORT).show();
				
				Toast.makeText(getApplicationContext(), 
						handle.getResources().getString(R.string.please_restart), Toast.LENGTH_LONG).show();

			}
		});

		xmppServersList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {	
				xmppEditor.putString("domain", xmppServers.get(position).getDomain()).commit();
				xmppEditor.putString("function", xmppServers.get(position).getFunction()).commit();
				xmppEditor.putString("ipAddress", xmppServers.get(position).getIpAddress()).commit();
				xmppEditor.putString("name", xmppServers.get(position).getName()).commit();
				xmppEditor.putString("port", xmppServers.get(position).getPort()).commit();
				xmppEditor.putString("service", xmppServers.get(position).getService()).commit();
				xmppEditor.putString("xcap_root", xmppServers.get(position).getXcapRoot()).commit();
				
				xmppServer = xmppServers.get(position);

				Toast.makeText(getApplicationContext(), xmppServers.get(position).toString()+"\n"
						+handle.getResources().getString(R.string.saved_to_preferences), Toast.LENGTH_SHORT).show();
				Toast.makeText(getApplicationContext(), 
						handle.getResources().getString(R.string.please_restart), Toast.LENGTH_LONG).show();
			}
		});

		clearBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				xmppEditor.putString("domain", "").commit();
				xmppEditor.putString("function", "").commit();
				xmppEditor.putString("ipAddress", "").commit();
				xmppEditor.putString("name", "").commit();
				xmppEditor.putString("port", "").commit();
				xmppEditor.putString("service","").commit();
				xmppEditor.putString("xcap_root", "").commit();

				sipEditor.putString("domain","").commit();
				sipEditor.putString("function", "").commit();
				sipEditor.putString("ipAddress", "").commit();
				sipEditor.putString("name", "").commit();
				sipEditor.putString("port", "").commit();
				sipEditor.putString("service", "").commit();
				sipEditor.putString("xcap_root", "").commit();
				
				xmppServer = sipServer = null;

				Toast.makeText(getApplicationContext(), getResources().getString(R.string.settings_cleared), Toast.LENGTH_LONG).show();

			}
		});

		differentSipBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				AlertDialog.Builder alert = new AlertDialog.Builder(handle);

				alert.setTitle(getResources().getString(R.string.add_ip_address_title));
				alert.setMessage(getResources().getString(R.string.add_ip_address));

				// Set an EditText view to get user input 
				final EditText ipAddressInput = new EditText(handle);
				alert.setView(ipAddressInput);

				alert.setPositiveButton(getResources().getString(R.string.message_ok), new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						String ipAddress = ipAddressInput.getText().toString();

						sipServer = new Server();
						String domain = "trixbox1.localdomain";
						String function = "sip";
						String name = "new_sip";
						String port = "5070";
						String xcapRoot = "";
						
						sipEditor.putString("domain", domain).commit();
						sipEditor.putString("function", function).commit();
						sipEditor.putString("ipAddress", ipAddress).commit();
						sipEditor.putString("name", name).commit();
						sipEditor.putString("port", port).commit();
						sipEditor.putString("service", ipAddress).commit();
						sipEditor.putString("xcap_root", xcapRoot).commit();
						
						sipServer.setDomain(domain);
						sipServer.setFunction(function);
						sipServer.setIpAddress(ipAddress);
						sipServer.setName(name);
						sipServer.setPort(port);
						sipServer.setService(ipAddress);
						sipServer.setXcapRoot(xcapRoot);

					}
				});

				alert.setNegativeButton(getResources().getString(R.string.message_cancel), new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						// Canceled.
					}
				});

				alert.show();
			}
		});

		differentXmppBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				AlertDialog.Builder alert = new AlertDialog.Builder(handle);

				alert.setTitle(getResources().getString(R.string.add_ip_address_title));
				alert.setMessage(getResources().getString(R.string.add_ip_address));

				// Set an EditText view to get user input 
				final EditText ipAddressInput = new EditText(handle);
				alert.setView(ipAddressInput);

				alert.setPositiveButton(getResources().getString(R.string.message_ok), new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						String ipAddress = ipAddressInput.getText().toString();

						xmppServer = new Server();
						String domain = "trixbox1.localdomain";
						String function = "xmpp";
						String name = "new_xmpp";
						String port = "5222";
						String xcapRoot = "";
						
						xmppEditor.putString("domain", domain).commit();
						xmppEditor.putString("function", function).commit();
						xmppEditor.putString("ipAddress", ipAddress).commit();
						xmppEditor.putString("name", name).commit();
						xmppEditor.putString("port", port).commit();
						xmppEditor.putString("service", ipAddress).commit();
						xmppEditor.putString("xcap_root", xcapRoot).commit();
						
						xmppServer.setDomain(domain);
						xmppServer.setFunction(function);
						xmppServer.setIpAddress(ipAddress);
						xmppServer.setName(name);
						xmppServer.setPort(port);
						xmppServer.setService(ipAddress);
						xmppServer.setXcapRoot(xcapRoot);

					}
				});

				alert.setNegativeButton(getResources().getString(R.string.message_cancel), new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						// Canceled.
					}
				});

				alert.show();
			}
		});
		
		showCurrentBtn.setOnClickListener(new View.OnClickListener() {
			
			String serversString = "";
						
			@Override
			public void onClick(View v) {
				
				try{
					serversString = serversString + "SIP Server: \n"+sipServer.toString()+"\n----\n";
					serversString = serversString + "XMPP Server: \n"+xmppServer.toString()+"\n----\n";
							
				}catch(Exception e)
				{
					
				}
				
				AlertDialog ad = new AlertDialog.Builder(handle).create();  
				ad.setCancelable(true); // This blocks the 'BACK' button  
				ad.setMessage(serversString);  
				ad.setButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {  
					@Override  
					public void onClick(DialogInterface dialog, int which) {  
						dialog.dismiss();                      
					}  
				});  
				ad.show();
				
			}
		});

	}

	public void initializePreferences()
	{
		sipServer = new Server();
		xmppServer = new Server();
		
		pref = handle.getSharedPreferences("SIP",
				Context.MODE_PRIVATE);
		sipEditor = pref.edit();
		
		sipServer.setDomain(pref.getString("domain", ""));
		sipServer.setFunction(pref.getString("function", ""));
		sipServer.setIpAddress(pref.getString("ipAddress", ""));
		sipServer.setName(pref.getString("name", ""));
		sipServer.setPort(pref.getString("port", ""));
		sipServer.setService(pref.getString("service", ""));
		sipServer.setXcapRoot(pref.getString("xcap_root", ""));
				

		pref = handle.getSharedPreferences("XMPP",
				Context.MODE_PRIVATE);
		xmppEditor = pref.edit();
		
		xmppServer.setDomain(pref.getString("domain", ""));
		xmppServer.setFunction(pref.getString("function", ""));
		xmppServer.setIpAddress(pref.getString("ipAddress", ""));
		xmppServer.setName(pref.getString("name", ""));
		xmppServer.setPort(pref.getString("port", ""));
		xmppServer.setService(pref.getString("service", ""));
		xmppServer.setXcapRoot(pref.getString("xcap_root", ""));
	}

	public void loadServers()
	{
		servers = data.loadServers();
		for(int i=0; i<servers.size(); i++)
		{
			if(servers.get(i).getFunction().equals("sip"))
			{
				sipServers.add(servers.get(i));
			}
			else if(servers.get(i).getFunction().equals("xmpp"))
			{
				xmppServers.add(servers.get(i));
			}
		}
	}

	public void showServers()
	{
		if(sipServers.size()!=0)
		{
			ArrayAdapter<Server> sipAdapter =
					new ArrayAdapter<Server>(this,R.layout.simple_list_item, sipServers);
			sipServersList.setAdapter(sipAdapter);
		}

		if(xmppServers.size()!=0)
		{
			ArrayAdapter<Server> xmppAdapter =
					new ArrayAdapter<Server>(this,R.layout.simple_list_item, xmppServers);
			xmppServersList.setAdapter(xmppAdapter);

		}
	}



}
