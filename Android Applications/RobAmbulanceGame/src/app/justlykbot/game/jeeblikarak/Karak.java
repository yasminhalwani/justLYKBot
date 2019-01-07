package app.justlykbot.game.jeeblikarak;

//Long Press Button tutorial at http://blog.rochdev.com/2011/05/update-ui-when-holding-button.html
//Data transfer through bluetooth tutorial at http://english.cxem.net/arduino/arduino5.php

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnDismissListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class Karak extends Activity {


	Karak handle =this;

	/*****************************************
	 * Bluetooth-related variables
	 ****************************************/

	//BluetoothService btService = null;
	private static final String TAG = "RobAmbulance";
	char receivedChar=',';

	/*****************************************
	 * Game-related variables
	 ****************************************/

	private static final char FORWARD = '8';
	private static final char BACKWARD = '2';
	private static final char LEFT = '4';
	private static final char RIGHT = '6';

	private static int DELAY = 30;

	private boolean isSlow = false;

	private static char direction;


	/*****************************************
	 * Layout-related variables
	 ****************************************/
	private ArrowButton forwardButton;
	private ArrowButton backwardButton;
	private ArrowButton leftButton;
	private ArrowButton rightButton;
	private Button slow, medium, fast;
	private TextView timerDisplay;

	private Handler mHandler;
	private boolean mPressed;
	private final Runnable mRunnable = new Runnable() {
		public void run() {
			if(!isSlow){
				if (mPressed) {
					//btService.writeByte(direction);
				}
				mHandler.postDelayed(this, DELAY);
			}
		}
	};

	/******************************************
	 * TIMER
	 ******************************************/
	Handler viewHandler = new Handler();
	static int timer=60;
	Runnable updateViewTimer = new Runnable() {
		@Override

		public void run() {

			timerDisplay.invalidate();			
			timerDisplay.setText(timer+"");
			if(timer>0)timer--;
			viewHandler.postDelayed(updateViewTimer, 1000);

		}
	};


	/*****************************************
	 * Constructor
	 ****************************************/
	public Karak() {
		mHandler = new Handler();
	}

	/*****************************************
	 * onCreate
	 ****************************************/


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.karak);

		//timer=60;

		//btService = new BluetoothService(this);

		timerDisplay = (TextView) findViewById(R.id.timeRemaining);
		timerDisplay.setText("");


	//	viewHandler.post(updateViewTimer);





		forwardButton = (ArrowButton) findViewById(R.id.moveForward);
		forwardButton.setOnLongClickListener(new OnLongClickListener() {            
			@Override
			public boolean onLongClick(View v) {
				direction = FORWARD;
				mPressed = true;
				mHandler.post(mRunnable);
				return true;			
			}
		});
		forwardButton.setSampleLongpress(this);
		forwardButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				direction = FORWARD;
				//btService.writeByte(direction);
				if(timer<=0)endGame();
				//endGame();
			}
		});



		backwardButton = (ArrowButton) findViewById(R.id.moveBackward);
		backwardButton.setOnLongClickListener(new OnLongClickListener() {            
			@Override
			public boolean onLongClick(View v) {
				direction = BACKWARD;
				mPressed = true;
				mHandler.post(mRunnable);
				return true;
			}
		});
		backwardButton.setSampleLongpress(this);
		backwardButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				direction = BACKWARD;
				//btService.writeByte(direction);
				//if(hasReachedEnd())endGame();
			}
		});

		leftButton = (ArrowButton) findViewById(R.id.rotateLeft);
		leftButton.setOnLongClickListener(new OnLongClickListener() {            
			@Override
			public boolean onLongClick(View v) {
				direction = LEFT;
				mPressed = true;
				mHandler.post(mRunnable);
				return true;
			}
		});
		leftButton.setSampleLongpress(this);
		leftButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				direction = LEFT;
			//	btService.writeByte(direction);
				//if(hasReachedEnd())endGame();
			}
		});


		rightButton = (ArrowButton) findViewById(R.id.rotateRight);
		rightButton.setOnLongClickListener(new OnLongClickListener() {            
			@Override
			public boolean onLongClick(View v) {
				direction = RIGHT;
				mPressed = true;
				mHandler.post(mRunnable);
				return true;
			}
		});
		rightButton.setSampleLongpress(this);
		rightButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				direction = RIGHT;
				//btService.writeByte(direction);
				//if(hasReachedEnd())endGame();
			}
		});

		slow = (Button) findViewById(R.id.slow);
		slow.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				DELAY = 100;
				isSlow = true;

			}
		});

		medium = (Button) findViewById(R.id.medium);
		medium.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				DELAY = 20;
				isSlow = false;

			}
		});

		fast = (Button) findViewById(R.id.faster);
		fast.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				DELAY = 5;

				isSlow = false;

			}
		});


	}

	public boolean hasReachedEnd()
	{
		//char x = btService.readByte();
		//Toast.makeText(handle.getApplicationContext(), String.valueOf(x)+"" , Toast.LENGTH_SHORT).show();	

		//if(x=='F' || timer<=0)return true;
		//else return false;
		return false;
	}

	public void endGame()
	{
		//Toast.makeText(getApplicationContext(), "ENDED!", Toast.LENGTH_SHORT).show();

		Intent resultIntent = new Intent(Karak.this, Result.class);
		resultIntent.putExtra("result", timer+"");
		startActivity(resultIntent);
		handle.finish();

	}


	/*****************************************
	 * Layout-related methods
	 ****************************************/

	public void cancelLongPress() {
		mPressed = false;

	}

	/*****************************************
	 * Activity-related methods
	 ****************************************/
	@Override
	public void onDestroy() {
	//	btService.onDestroy();	
		super.onDestroy();		
	}

	private synchronized void stopThread(Thread theThread) throws InterruptedException
	{
		theThread.stop();
	}

	@Override
	public void onResume() {
		super.onResume();
		//btService.onResume();
	}

	@Override
	public void onPause() {
		super.onPause();	
		//btService.onPause();
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case 1:
			// Create out AlterDialog
			Builder builder = new AlertDialog.Builder(this);
			builder.setMessage("The connection to your robot was lost. Please check your robot's bluetooth connection.");
			builder.setCancelable(true);
			builder.setPositiveButton("Terminate RobAmbulance", new OkOnClickListener());
			builder.setNegativeButton("Retry", new CancelOnClickListener());
			AlertDialog dialog = builder.create();
			dialog.setOnDismissListener(new OnDismissListener() {
				@Override
				public void onDismiss(DialogInterface dialog) {
					Toast.makeText(getApplicationContext(), "Hello", Toast.LENGTH_LONG).show();
				}
			});
			dialog.show();
		}
		return super.onCreateDialog(id);
	}

	private final class CancelOnClickListener implements
	DialogInterface.OnClickListener {
		public void onClick(DialogInterface dialog, int which) {
			//Toast.makeText(getApplicationContext(), "Activity will continue",Toast.LENGTH_LONG).show();
		//	btService.onResume();
			dialog.dismiss();
		}
	}

	private final class OkOnClickListener implements
	DialogInterface.OnClickListener {
		public void onClick(DialogInterface dialog, int which) {
			Karak.this.finish();
		}
	}

	public void showDialog()
	{
		showDialog(1);
	}



}

