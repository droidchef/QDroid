package com.boilingstocks.qdroid;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.boilingstocks.qdroid.objects.JSONParser;
import com.boilingstocks.qdroid.objects.Question;

@SuppressLint("DefaultLocale")
public class Quiz extends Activity implements OnClickListener{

	public static String URL_QUESION_FETCH = "http://www.boilingstocks.com/qdroid/getques.php";
	
	/*
	 * 
	 * UI Elements
	 *
	 */
	private TextView question_text,tv_timer;
	private Button clear;
	private Button opt1,opt2,opt3,opt4;
	private Question question;
	JSONObject jsonObject = new JSONObject();
	String[] choices = new String[4];
	
	private int questionCounter = 1;
	List<NameValuePair> params = new ArrayList<NameValuePair>();
    JSONParser jsonParser = new JSONParser();
    private String LANGUAGE;
    
    private CountDownTimer mCountDownTimer;
    private String CATEGORY;
	@SuppressLint("DefaultLocale")
	@Override
	public void onCreate(Bundle savedInstanceState){
		
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.activity_quiz);

		SharedPreferences p = PreferenceManager.getDefaultSharedPreferences(this);
		LANGUAGE = p.getString("lang", "english");
		LANGUAGE = LANGUAGE.toLowerCase();
		Intent intent = getIntent();
//		LANGUAGE = intent.getExtras().getString("lang");
//		LANGUAGE = "english";
		CATEGORY = intent.getExtras().getString("cat");
		question_text = (TextView) findViewById(R.id.tv_question);
		tv_timer = (TextView) findViewById(R.id.tv_timer);
		clear = (Button) findViewById(R.id.clear);
		clear.setOnClickListener(this);
		opt1 = (Button) findViewById(R.id.bt_opt1);
		opt2 = (Button) findViewById(R.id.bt_opt2);
		opt3 = (Button) findViewById(R.id.bt_opt3);
		opt4 = (Button) findViewById(R.id.bt_opt4);
		
		opt1.setOnClickListener(this);
		opt2.setOnClickListener(this);
		opt3.setOnClickListener(this);
		opt4.setOnClickListener(this);
		question = new Question();
		
		mCountDownTimer = new CountDownTimer(10000,1000){

			@Override
			public void onFinish() {
				// TODO Auto-generated method stub
				Toast.makeText(Quiz.this, "Time Out", Toast.LENGTH_SHORT).show();
				questionCounter++;
				clear.setVisibility(View.VISIBLE);
				opt1.setEnabled(false);
				opt2.setEnabled(false);
				opt3.setEnabled(false);
				opt4.setEnabled(false);
				}

			@Override
			public void onTick(long arg0) {
				// TODO Auto-generated method stub
				tv_timer.setText("00:"+arg0/1000);
			}
			
		};
		
		getNewQuestion();
		
	}

	public void checkAnswer(int answer){
		
		if(answer == question.getAnswerId()){
			mCountDownTimer.cancel();
			Toast.makeText(this, "Correct Answer!", Toast.LENGTH_SHORT).show();
			questionCounter++;
			getNewQuestion();
			
		}else{
			mCountDownTimer.cancel();
			Toast.makeText(this, "Wrong Answer!", Toast.LENGTH_SHORT).show();
			questionCounter++;
			opt1.setEnabled(false);
			opt2.setEnabled(false);
			opt3.setEnabled(false);
			opt4.setEnabled(false);
			clear.setVisibility(View.VISIBLE);
		}
	}
	
	public void getNewQuestion(){
		opt1.setEnabled(true);
		opt2.setEnabled(true);
		opt3.setEnabled(true);
		opt4.setEnabled(true);
		clear.setVisibility(View.GONE);
		new AsyncTask<Void,Void,Boolean>(){
			ProgressDialog pDialog = new ProgressDialog(Quiz.this);

			@Override
			protected void onPreExecute(){
				pDialog.setTitle("Please Wait...");
				pDialog.setMessage("Loading Question");
				pDialog.setIndeterminate(true);
				pDialog.show();
			}
			
			@Override
			public Boolean doInBackground(Void...x){

				
//				String paramsList = "id="+questionCounter+"&cat="+"HISTORY"+"&lang="+LANGUAGE;
				params.add(new BasicNameValuePair("id",String.valueOf(questionCounter)));
				params.add(new BasicNameValuePair("cat",CATEGORY));
				params.add(new BasicNameValuePair("lang",LANGUAGE));
				jsonObject = jsonParser.makeHttpRequest(URL_QUESION_FETCH, "GET", params);
				
				int status=0;
				try {
					status = jsonObject.getInt("status");
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(status==1){
					try{
						//Log.i("QDROID", "STATUS = Successful");
					
					choices[0] = jsonObject.getString("opt1");
					choices[1] = jsonObject.getString("opt2");
					choices[2] = jsonObject.getString("opt3");
					choices[3] = jsonObject.getString("opt4");
					question.setId(1);
					question.setqText(jsonObject.getString("question"));
					question.setOptions(choices);
					question.setAnswerId(jsonObject.getInt("answer"));
					question.setOptions(choices);
					writeQuestion();
					}
				catch(Exception e){
					e.printStackTrace();
				}
				}else{
					Toast.makeText(Quiz.this, "There was some problem. Please Contact Support.", Toast.LENGTH_LONG).show();
					
				}
				return true;
			}
			
			@Override
			protected void onPostExecute(Boolean b){
				pDialog.dismiss();
				mCountDownTimer.start();
			}
		}.execute();
	}
	
	public void writeQuestion(){
		runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				question_text.setText(question.getqText());
				opt1.setText(choices[0]);
				opt2.setText(choices[1]);
				opt3.setText(choices[2]);
				opt4.setText(choices[3]);
			}
		});
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		
		case R.id.bt_opt1: checkAnswer(1);
			break;
		case R.id.bt_opt2: checkAnswer(2);
			break;
		case R.id.bt_opt3: checkAnswer(3);
			break;
		case R.id.bt_opt4: checkAnswer(4);
			break;			
		case R.id.clear:getNewQuestion();
				break;
		default: return;
		}
	
	
	}
}
