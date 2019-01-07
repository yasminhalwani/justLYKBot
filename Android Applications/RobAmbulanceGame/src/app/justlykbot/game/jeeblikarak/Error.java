package app.justlykbot.game.jeeblikarak;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Error  extends Activity{
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.error);
		
		Button okButton = (Button) findViewById(R.id.okButton);
		
		okButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				finish();
				
			}
		});
		
		
	}

}
