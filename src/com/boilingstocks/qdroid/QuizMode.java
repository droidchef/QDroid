package com.boilingstocks.qdroid;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

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
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.boilingstocks.qdroid.objects.JSONParser;
import com.boilingstocks.qdroid.objects.Question;

public class QuizMode extends Activity implements OnClickListener{
	
	Button[] options;
	Button nextques;
	public static String URL_QUESION_FETCH = "http://www.boilingstocks.com/qdroid/getques.php";
	
	/*
	 * 
	 * UI Elements
	 *
	 */
	private TextView question_text,tv_timer;

	private Question question;
	JSONObject jsonObject = new JSONObject();
	String[] choices = new String[4];
	//
	private int questionCounter = 1;
	List<NameValuePair> params = new ArrayList<NameValuePair>();
    JSONParser jsonParser = new JSONParser();
    private String LANGUAGE;
    
    private CountDownTimer mCountDownTimer;
    private String CATEGORY;

	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
		setContentView(R.layout.activity_quiz_mode);
		
		SharedPreferences p = PreferenceManager.getDefaultSharedPreferences(this);
		LANGUAGE = p.getString("lang", "english");
		LANGUAGE = LANGUAGE.toLowerCase();
		Intent intent = getIntent();
		CATEGORY = intent.getExtras().getString("cat");
		question_text = (TextView) findViewById(R.id.textView2);
		tv_timer = (TextView) findViewById(R.id.textView1);
		
		question = new Question();
		
		options = new Button[4];
		options[0] = (Button) findViewById(R.id.button1);
		options[1] = (Button) findViewById(R.id.button2);
		options[2] = (Button) findViewById(R.id.button3);
		options[3] = (Button) findViewById(R.id.button4);
		
		nextques = (Button) findViewById(R.id.button5);
		
		for(int i=0;i<4;i++){
			options[i].setOnClickListener(this);
		}
		nextques.setOnClickListener(this);
		
		mCountDownTimer = new CountDownTimer(15000,1000){

			@Override
			public void onFinish() {
				// TODO Auto-generated method stub
				Toast.makeText(QuizMode.this, "Time Out", Toast.LENGTH_SHORT).show();
				questionCounter++;
				nextques.setVisibility(View.VISIBLE);
				options[0].setEnabled(false);
				options[1].setEnabled(false);
				options[2].setEnabled(false);
				options[3].setEnabled(false);
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
			options[0].setEnabled(false);
			options[1].setEnabled(false);
			options[2].setEnabled(false);
			options[3].setEnabled(false);
			nextques.setVisibility(View.VISIBLE);
		}
	}
	
	public void getNewQuestion(){
		options[0].setEnabled(true);
		options[1].setEnabled(true);
		options[2].setEnabled(true);
		options[3].setEnabled(true);
		nextques.setVisibility(View.GONE);
		new AsyncTask<Void,Void,Boolean>(){
			ProgressDialog pDialog = new ProgressDialog(QuizMode.this);

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
					Toast.makeText(QuizMode.this, "There was some problem. Please Contact Support.", Toast.LENGTH_LONG).show();
					
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
				options[0].setText(choices[0]);
				options[1].setText(choices[1]);
				options[2].setText(choices[2]);
				options[3].setText(choices[3]);
				nextques.setVisibility(View.GONE);

			}
		});
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		
		case R.id.button1: checkAnswer(1);
			break;
		case R.id.button2: checkAnswer(2);
			break;
		case R.id.button3: checkAnswer(3);
			break;
		case R.id.button4: checkAnswer(4);
			break;			
		case R.id.button5:getNewQuestion();
				break;
		default: return;
		}
	
	
	}

}
