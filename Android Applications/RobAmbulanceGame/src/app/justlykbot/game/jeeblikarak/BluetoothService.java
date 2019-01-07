package app.justlykbot.game.jeeblikarak;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.UUID;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;
import android.content.Context;

public class BluetoothService  {

	public boolean connectionLost = true;
	
	Activity activity;
	/*****************************************
	 * Bluetooth-related variables
	 ****************************************/
	private static final String TAG = "BluetoothService: ";
	private BluetoothAdapter btAdapter = null;
	private BluetoothSocket btSocket = null;
	private OutputStream outStream = null;
	private InputStream inStream = null;
	private boolean connected = false;

	// SPP UUID service 
	private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

	// MAC-address of Bluetooth module (you must edit this line)
	//private static String address = "00:17:A0:01:6D:4C";
	private static String address = "00:17:A0:01:6C:96";


	public BluetoothService(Activity activity)
	{
		btAdapter = BluetoothAdapter.getDefaultAdapter();
		this.activity = activity;
		checkBTState();
	}


	private void checkBTState() {
		// Check for Bluetooth support and then check to make sure it is turned on
		// Emulator doesn't support Bluetooth and will return null
		if(btAdapter==null) { 
			errorExit("Fatal Error", "Bluetooth not supported");
		} else {
			if (btAdapter.isEnabled()) {
				Log.d(TAG, "...Bluetooth ON...");
			} else {
				//Prompt user to turn on Bluetooth
				Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
				activity.startActivityForResult(enableBtIntent, 1);
			}
		}
	}

	private void errorExit(String title, String message){
		Log.d(TAG+"errorExit", title + " - " + message);
		connectionLost = true;
	}

	public void writeByte(char character)
	{

		String message = character+"";
		byte[] msgBuffer = message.getBytes();

		try {
			outStream.write(msgBuffer);
		} catch (IOException e) {			
			errorExit("Fatal Error", "Error at writeBye()");       
		}
		connectionLost = false;		
	}

	public char readByte()
	{		
		char receivedByte='.';

		if (connected) {
			return receivedByte;
		}

		Log.d(TAG+"Read", "++++ Connected");
		byte[] buffer = new byte[1];
		int read = 0;
		Log.d(TAG+"Read", "++++ Listening...");


		//writeByte('0');

		try {
			read = inStream.read(buffer);
			connected = true;

			receivedByte = new String(buffer).charAt(0);

			Log.d(TAG+"Read", "++++ Read "+ read +" bytes: "+ receivedByte);


		} catch (IOException e) {			
			errorExit("Fatal Error", "Error at readBye()");  
		}
		Log.d(TAG+"Read", "++++ Done: readByte()");
		connected = false;
		connectionLost = false;	
		return receivedByte;
	}
	

	public void onDestroy()
	{

		try {
			if (inStream != null) {
				inStream.close();
			}
			if (btSocket != null) {
				btSocket.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void onResume()
	{
		Log.d(TAG, "...onResume - try connect...");

		BluetoothDevice device = btAdapter.getRemoteDevice(address);
		try {
			btSocket = createBluetoothSocket(device);
		} catch (IOException e1) {
			errorExit("Fatal Error", "In onResume() and socket create failed: " + e1.getMessage() + ".");
		}

		btAdapter.cancelDiscovery();

		Log.d(TAG, "...Connecting...");
		try {
			btSocket.connect();
			Log.d(TAG, "...Connection ok...");
		} catch (IOException e) {
			try {
				btSocket.close();
			} catch (IOException e2) {
				errorExit("Fatal Error", "In onResume() and unable to close socket during connection failure" + e2.getMessage() + ".");
			}
		}

		// Create a data stream so we can talk to server.
		Log.d(TAG, "...Create Socket...");

		try {
			outStream = btSocket.getOutputStream();
			inStream = btSocket.getInputStream();
		} catch (IOException e) {
			errorExit("Fatal Error", "In onResume() and output stream creation failed:" + e.getMessage() + ".");
		}
		
		connectionLost = false;

	}

	public void onPause()
	{
		Log.d(TAG, "...In onPause()...");

		if (outStream != null) {
			try {
				outStream.flush();
			} catch (IOException e) {
				errorExit("Fatal Error", "In onPause() and failed to flush output stream: " + e.getMessage() + ".");
			}
		}

		try     {
			btSocket.close();
		} catch (IOException e2) {
			errorExit("Fatal Error", "In onPause() and failed to close socket." + e2.getMessage() + ".");
		}
		connectionLost = false;

	}

	private BluetoothSocket createBluetoothSocket(BluetoothDevice device) throws IOException {	
		
		if(Build.VERSION.SDK_INT >= 10){
			try {
				final Method  m = device.getClass().getMethod("createInsecureRfcommSocketToServiceRecord", new Class[] { UUID.class });
				return (BluetoothSocket) m.invoke(device, MY_UUID);
			} catch (Exception e) {
				Log.e(TAG, "Could not create Insecure RFComm Connection",e);
			}
		}
		return  device.createRfcommSocketToServiceRecord(MY_UUID);
	}

	
	
}
