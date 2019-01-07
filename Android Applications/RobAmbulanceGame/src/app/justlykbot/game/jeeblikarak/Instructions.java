package app.justlykbot.game.jeeblikarak;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Instructions extends Activity{
	
	Button yalla;
	
	Instructions handle = this;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.instructions);
		
		yalla = (Button) findViewById(R.id.yalla);
		
		yalla.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				Intent karakIntent = new Intent(Instructions.this, Karak.class);
				startActivity(karakIntent);
				
				handle.finish();
				
			}
		});

	}

}
