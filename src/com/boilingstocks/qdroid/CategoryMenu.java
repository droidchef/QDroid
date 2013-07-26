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
	int id;
	String LANGUAGE;

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


		
		his.setOnClickListener(this);
		ent.setOnClickListener(this);
		spo.setOnClickListener(this);
		sci.setOnClickListener(this);
		ran.setOnClickListener(this);
		intent = new Intent(CategoryMenu.this,QuizMode.class);
		
	}
	

	
	@Override
	public void onResume(){
		super.onResume();
		SharedPreferences p = PreferenceManager.getDefaultSharedPreferences(CategoryMenu.this);
		LANGUAGE = p.getString("lang", "english");
		LANGUAGE = LANGUAGE.toLowerCase();
		Log.i("QDROID",LANGUAGE);
		new AsyncTask<String,Void,Boolean>(){
			ProgressDialog pDialog = new ProgressDialog(CategoryMenu.this);

			@Override
			protected void onPreExecute(){
				pDialog.setTitle("Please Wait...");
				pDialog.setMessage("Changing Language");
				pDialog.setIndeterminate(true);
				pDialog.show();
			}
			@Override
			protected Boolean doInBackground(String... pa) {
				// TODO Auto-generated method stub
				String LAN = pa[0];
				Log.i("QDROID","LAN = "+LAN);
				if(LAN.equalsIgnoreCase("english"))
				{
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
					changeButtonTexts();
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
		}.execute(LANGUAGE);

	}
	
	public void changeButtonTexts(){
		
		runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				switch(id){
				case 1 : his.setText("History");
				spo.setText("Geography");
				sci.setText("Science");
				ent.setText("Entertainment");
				ran.setText("Random");
				Log.i("QDROID", "CAse1");
				break;
				case 2 : his.setText("Mêjû");
				spo.setText("Zanist");
				sci.setText("Cografya");
				ent.setText("Kêf");
				ran.setText("Têkel");
				Log.i("QDROID", "CAse2");
				break;
				case 3 : his.setText("Mêjû");
				spo.setText("Zanist");
				sci.setText("Cografya");
				ent.setText("Kêf");
				ran.setText("Têkel");
				Log.i("QDROID", "CAse3");
				break;
				case 4 : his.setText("Mêjû");
				spo.setText("Zanist");
				sci.setText("Cografî");
				ent.setText("Kêf");
				ran.setText("Têkel");
				Log.i("QDROID", "CAse4");
				break;
				default : 
					break;
				}


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
