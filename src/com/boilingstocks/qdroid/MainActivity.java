package com.boilingstocks.qdroid;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {

	private String LANGUAGE = "english";
	Button lang,quiz,facts;
	int id;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_main);


		lang = (Button) findViewById(R.id.bt_settings);
		
		lang.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(MainActivity.this,LanguageMenu.class));

			}
		});
		
		quiz = (Button) findViewById(R.id.bt_quizmode);
		quiz.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(MainActivity.this,CategoryMenu.class));

			}
		});
		
		facts = (Button) findViewById(R.id.bt_factsmode);
		facts.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				startActivity(new Intent(MainActivity.this,Facts.class));
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void showBubbles(){
		new CountDownTimer(3000,1000) {
			
			@Override
			public void onTick(long millisUntilFinished) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onFinish() {
				// TODO Auto-generated method stub
				Toast.makeText(MainActivity.this, "Click on a Book!", Toast.LENGTH_SHORT).show();

			}
		}.start();
	}
	
	
	@Override
	public void onResume(){
		super.onResume();
		showBubbles();
		SharedPreferences p = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
		LANGUAGE = p.getString("lang", "english");
		LANGUAGE = LANGUAGE.toLowerCase();
		Log.i("QDROID",LANGUAGE);
		ChangeBackgroundDrawables cbd = new ChangeBackgroundDrawables();
		cbd.execute(LANGUAGE);
		
		
	}
	
	private class ChangeBackgroundDrawables extends AsyncTask<String,Void,Boolean>{

		ProgressDialog pDialog = new ProgressDialog(MainActivity.this);

		@Override
		protected void onPreExecute(){
			pDialog.setTitle("Please Wait...");
			pDialog.setMessage("Refreshing UI");
			pDialog.setIndeterminate(true);
			pDialog.show();
		}
		@Override
		protected Boolean doInBackground(String... pa) {
			// TODO Auto-generated method stub
			String LAN = pa[0];
			Log.i("QDROID","LAN = "+LAN);
			if(LAN.equalsIgnoreCase("english")){
				Log.i("QDROID", LANGUAGE);
				id=1;
			}else if(LAN.equalsIgnoreCase("bahdini")){
			
				Log.i("QDROID", LANGUAGE);

				id=2;
			}else if(LAN.equalsIgnoreCase("kurmanji")){
				Log.i("QDROID", LANGUAGE);

				id=3;
			}else if(LAN.equalsIgnoreCase("sorani")){
				Log.i("QDROID", LANGUAGE);

				id=4;
			}else{
				//nothing to do
				
			}
			try {
				Thread.sleep(500);
				setImageofButtons();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return true;
		}
		@Override
		protected void onPostExecute(Boolean b){
			pDialog.dismiss();
		}
		
	}
	
	public static Drawable getAssetImage(Context context, String filename) throws IOException {
	    AssetManager assets = context.getResources().getAssets();
	    InputStream buffer = new BufferedInputStream((assets.open("drawable/" + filename + ".png")));
	    Bitmap bitmap = BitmapFactory.decodeStream(buffer);
	    return new BitmapDrawable(context.getResources(), bitmap);
	}
	
	public void setImageofButtons(){
		
		runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				switch(id){
				case 1:	quiz.setBackgroundResource(R.drawable.quiz);
						lang.setBackgroundResource(R.drawable.support);
						facts.setBackgroundResource(R.drawable.funfacts);
					break;
				case 2:	quiz.setBackgroundResource(R.drawable.quiz_kur);
						lang.setBackgroundResource(R.drawable.sup_bah);
						facts.setBackgroundResource(R.drawable.fun_rest);
					break;
				case 3:	quiz.setBackgroundResource(R.drawable.quiz_kur);
						lang.setBackgroundResource(R.drawable.sup_kur);
						facts.setBackgroundResource(R.drawable.fun_rest);
					break;
				case 4:	quiz.setBackgroundResource(R.drawable.quiz_sor);
						lang.setBackgroundResource(R.drawable.sup_sor);
						facts.setBackgroundResource(R.drawable.fun_rest);
					break;
				default:
					break;
				}
			}
		});
		
	}
	
	

}
