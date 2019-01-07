package app.justlykbot.game.jeeblikarak;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;

public class Start extends Activity{

	Start handle = this;
	String username;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		setContentView(R.layout.result);
		
		username = "noUser";
		
//		new CountDownTimer(3000,1000){
//			@Override
//			public void onTick(long millisUntilFinished){} 
//
//			@Override
//			public void onFinish(){
//				
//				try{
//					//Bundle extras = getIntent().getExtras(); //TODO remove comment here
//					//username = extras.getString("username");
//					Intent gameIntent = new Intent(Start.this, Instructions.class);
//					startActivity(gameIntent);
//					handle.finish();
//				}catch(NullPointerException e)
//				{
//					Intent intent = new Intent(Start.this,
//							Error.class);
//					startActivity(intent);
//					finish();
//				}
//				
//
//			}
//		}.start();








	}


}
