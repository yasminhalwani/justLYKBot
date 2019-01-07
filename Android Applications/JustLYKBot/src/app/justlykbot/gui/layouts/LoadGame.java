package app.justlykbot.gui.layouts;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import app.justlykbot.R;
import app.justlykbot.datatypes.Game;

public class LoadGame extends Activity {


	//Layout variables
	private Button positiveButton;
	private Button negativeButton;
	private TextView message;
	private ProgressDialog mProgressDialog;

	//Data variables
	public static final int DIALOG_DOWNLOAD_PROGRESS = 0;
	private final String PATH = Environment.getExternalStorageDirectory() + "/JustLYKBot/";
	private String downloadURL;
	private String appName;
	private String	pack;
	private Game game;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.message_prompt);

		Bundle extras = getIntent().getExtras(); 
		if(extras !=null) {
			this.game = (Game) extras.getSerializable("game");
			this.pack = game.getGamePackage();
			this.downloadURL = game.getDownloadURL();
			this.appName = game.getAppName();

		}else
		{
			killActivity();
		}


		guiSetup();


		if(isAppInstalled())
		{
			finish();
			Intent LaunchIntent = getPackageManager().getLaunchIntentForPackage(pack);
			startActivity(LaunchIntent);
		}else{

			buttonsSetup();

		}

	}

	public boolean isAppInstalled()
	{
		boolean app_installed = false;
		PackageManager pm = getPackageManager();
		try
		{
			pm.getPackageInfo(game.getGamePackage(), PackageManager.GET_ACTIVITIES);
			app_installed = true;
		}
		catch (PackageManager.NameNotFoundException e)
		{
			app_installed = false;
		}
		return app_installed ;
	}

	public void setDownloadPackage(String pack)
	{
		this.pack = pack;
	}

	protected void installApkfile(String apkFileName) {

		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.fromFile(
				new File(PATH
						+ apkFileName)), "application/vnd.android.package-archive");
		startActivityForResult(intent,0);
	}

	private void startDownload() {
		String url = downloadURL;
		new DownloadFileAsync().execute(url);
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DIALOG_DOWNLOAD_PROGRESS:
			mProgressDialog = new ProgressDialog(this);
			mProgressDialog.setMessage("Downloading file..");
			mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			mProgressDialog.setCancelable(false);
			mProgressDialog.show();
			return mProgressDialog;
		default:
			return null;
		}
	}	

	public boolean appDownloaded(String appName)
	{
		File f = new File(Environment.getExternalStorageDirectory() + "/JustLYKBot/"+appName);
		if(f.isFile()) {			
			return true;
		}else
		{			
			return false;
		}
	}

	void killActivity()
	{
		finish();
	}

	public void guiSetup()
	{
		positiveButton = (Button)findViewById(R.id.messagePrompt_Button_positiveBtn);
		negativeButton = (Button)findViewById(R.id.messagePrompt_Button_negativeBtn);
		message = (TextView)findViewById(R.id.messagePrompt_TextView_message);
	}

	public void buttonsSetup()
	{
		positiveButton.setText(getResources().getString(R.string.message_yes));
		negativeButton.setText(getResources().getString(R.string.message_no));
		
		if(appDownloaded(appName))
		{	
			message.setText(appName+" "+ getResources().getString(R.string.would_you_like_to_install));

			positiveButton.setOnClickListener(new OnClickListener(){
				public void onClick(View v) {
					installApkfile(appName);
					killActivity();
				}
			});

			negativeButton.setOnClickListener(new OnClickListener(){
				public void onClick(View v) {
					killActivity();
				}
			});
		}else
		{
			message.setText(appName+" "+getResources().getString(R.string.would_you_like_to_download));

			positiveButton.setOnClickListener(new OnClickListener(){
				public void onClick(View v) {
					startDownload();
				}
			});

			negativeButton.setOnClickListener(new OnClickListener(){
				public void onClick(View v) {
					killActivity();
				}
			});
		}
	}

	class DownloadFileAsync extends AsyncTask<String, String, String> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			showDialog(DIALOG_DOWNLOAD_PROGRESS);
		}

		@Override
		protected String doInBackground(String... aurl) {
			int count;

			Log.d("DOWNLOAD","+++++++++++++++DO IN BACKGROUND");

			try {
				URL url = new URL(aurl[0]);
				Log.d("DOWNLOAD","+++++++++++++++DO IN BACKGROUND"+aurl[0]);
				URLConnection conexion = url.openConnection();
				conexion.connect();

				int lenghtOfFile = conexion.getContentLength();
				Log.d("ANDRO_ASYNC", "Lenght of file: " + lenghtOfFile);

				InputStream input = new BufferedInputStream(url.openStream());
				OutputStream output = new FileOutputStream(PATH + appName);

				Log.d("DOWNLOAD","+++++++++++++++Downloaded: "+PATH + appName);

				byte data[] = new byte[1024];

				long total = 0;

				while ((count = input.read(data)) != -1) {
					total += count;
					publishProgress(""+(int)((total*100)/lenghtOfFile));
					output.write(data, 0, count);
				}

				output.flush();
				output.close();
				input.close();

				killActivity();



			} catch (Exception e) {
				Log.d("DOWNLOAD","+++++++++++++++EXCEPTION: "+PATH + appName);

			}
			return null;

		}
		protected void onProgressUpdate(String... progress) {
			Log.d("ANDRO_ASYNC",progress[0]);
			mProgressDialog.setProgress(Integer.parseInt(progress[0]));
		}

		@Override
		protected void onPostExecute(String unused) {
			dismissDialog(DIALOG_DOWNLOAD_PROGRESS);
		}
	}
}
