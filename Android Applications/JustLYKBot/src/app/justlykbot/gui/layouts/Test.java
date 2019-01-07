package app.justlykbot.gui.layouts;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import app.justlykbot.R;
import app.justlykbot.datatypes.Server;
import app.justlykbot.localdata.DataLoader;

public class Test extends Activity{
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test);
		
		TextView text = (TextView) findViewById(R.id.test);
		
		DataLoader data = new DataLoader(this);
		data.authenticate("Bluebot", "7e45k");
		List<Server> servers = data.loadServers();		
		
		
		String x = "";
		for(int i=0; i<servers.size(); i++)
			x = x + servers.get(i).getName()+"\n------\n";
		
		text.setText(x);
		
		
	}

}
