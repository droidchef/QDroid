package com.boilingstocks.qdroid;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.boilingstocks.qdroid.objects.JSONParser;

public class Facts extends Activity implements OnClickListener {

	public static String URL_GET_FACT = "http://www.boilingstocks.com/qdroid/getfacts.php"; 
	TextView fact ;
	Button next;
	String fact_text;
	List<NameValuePair> params = new ArrayList<NameValuePair>();
	int factcounter;
	long seed;
	Random r;
	String LANGUAGE;
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.activity_facts);
		SharedPreferences p = PreferenceManager.getDefaultSharedPreferences(this);
		LANGUAGE = p.getString("lang", "english");
		LANGUAGE = LANGUAGE.toLowerCase();
		fact = (TextView) findViewById(R.id.tv_fact);
		next = (Button) findViewById(R.id.bt_next);
		next.setOnClickListener(this);
		r = new Random();		
		factcounter = 1 + r.nextInt(3);
		
	
	}
	
	public void getNewFact(){
		
		new AsyncTask<Void,Void,Void>(){
			
			@Override
			public Void doInBackground(Void...p){
			
				params.add(new BasicNameValuePair("id",String.valueOf(factcounter)));
				params.add(new BasicNameValuePair("lang",LANGUAGE));
				
				JSONParser jsonParser = new JSONParser();
				JSONObject jsonObject = jsonParser.makeHttpRequest(URL_GET_FACT, "GET", params);
				
				try {
					int status = jsonObject.getInt("status");
					
					if(status==1){
						fact_text = jsonObject.getString("fact");
						updateUI();
					}
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				return null;
			}
			
		}.execute();
		
	}

	public void updateUI(){
	
		runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				fact.setText(fact_text);
			}
		});
		
	}
	
	@Override
	public void onResume(){
		super.onResume();
		getNewFact();
	}
	
	@Override
	public void onClick(View v){
		
		if(v.getId()==R.id.bt_next){
			factcounter = 1 + r.nextInt(3);
			getNewFact();
		}
		
	}
	
}
