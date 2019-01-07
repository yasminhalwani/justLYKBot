package app.justlykbot.gui.layouts;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import app.justlykbot.R;

public class StartActivity extends Activity{
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.startactivity);
		goToFirstActivity();
		this.finish();
		
		if(!appDirectoryExists())createAppDirectory();

		
	}
	
	public void goToFirstActivity()
	{
		Intent k = new Intent(this, Login.class);
		this.startActivity(k);
	}
	
	public boolean appDirectoryExists()
	{
		File f = new File(Environment.getExternalStorageDirectory() + "/JustLYKBot/");
		if(f.isDirectory()) {
		   return true;
		}else
		{
			return false;
		}
		
		
	}
	
	public void createAppDirectory()
	{
		// create a File object for the parent directory
		File appDirectory = new File(Environment.getExternalStorageDirectory()+"/JustLYKBot/");
		// have the object build the directory structure, if needed.
		appDirectory.mkdirs();
	}
	

}
