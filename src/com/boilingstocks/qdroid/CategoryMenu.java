package com.boilingstocks.qdroid;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
public class CategoryMenu extends Activity implements OnClickListener{

	private Button his, ent, spo, sci,ran;
	Intent intent;
	String LANGUAGE;
	String[] menu = new String[5];

	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.actiivty_category);
		SharedPreferences p = PreferenceManager.getDefaultSharedPreferences(this);
		LANGUAGE = p.getString("lang", "english");
		LANGUAGE = LANGUAGE.toLowerCase();

		his = (Button) findViewById(R.id.cat1);
		ent = (Button) findViewById(R.id.cat4);
		spo = (Button) findViewById(R.id.cat2);
		sci = (Button) findViewById(R.id.cat3);
		ran = (Button) findViewById(R.id.cat5);
		

		new AsyncTask<Void,Void,Boolean>(){
			ProgressDialog pDialog = new ProgressDialog(CategoryMenu.this);

			@Override
			protected void onPreExecute(){
				pDialog.setTitle("Please Wait...");
				pDialog.setMessage("Loading Question");
				pDialog.setIndeterminate(true);
				pDialog.show();
			}
			@Override
			protected Boolean doInBackground(Void... arg0) {
				// TODO Auto-generated method stub
				if(LANGUAGE=="english")
				{
					menu = getResources().getStringArray(R.array.english);
				}else if(LANGUAGE=="bahdini"){
					menu = getResources().getStringArray(R.array.bahdini);
    			}else if(LANGUAGE == "kurmanji"){
					menu = getResources().getStringArray(R.array.kurmanji);
				}else{
					menu = getResources().getStringArray(R.array.sorani);
				}
				changeButtonTexts(menu);
				return true;
			}
			@Override
			protected void onPostExecute(Boolean b){
				pDialog.dismiss();
			}
		}.execute();
		

		his.setOnClickListener(this);
		ent.setOnClickListener(this);
		spo.setOnClickListener(this);
		sci.setOnClickListener(this);
		ran.setOnClickListener(this);
		intent = new Intent(CategoryMenu.this,QuizMode.class);
		
	}
	
	public void changeButtonTexts(String[] m){
		runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				menu = getResources().getStringArray(R.array.sorani);
				for(String m:menu){
					Log.d("QDROID","Strings menu -> " + m);
				}
				his.setText(menu[0]);
				spo.setText(menu[1]);
				sci.setText(menu[2]);
				ent.setText(menu[3]);
				ran.setText(menu[4]);

			}
		});
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		
		case R.id.cat1: intent.putExtra("cat", "HISTORY");
						startActivity(intent);
			break;
		case R.id.cat2: intent.putExtra("cat", "GEOGRAPHY");
						startActivity(intent);
			break;
		case R.id.cat3: intent.putExtra("cat", "SCIENCE");
						startActivity(intent);
			break;
		case R.id.cat4: intent.putExtra("cat", "ENTERTAINMENT");
						startActivity(intent);
			break;
		case R.id.cat5 : intent.putExtra("cat", "random");
						startActivity(intent);
			break;
		default:
		}
	}

}
