package com.boilingstocks.qdroid;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.boilingstocks.qdroid.objects.JSONParser;
import com.boilingstocks.qdroid.objects.Question;

public class Quiz extends Activity implements OnClickListener{

	public static String URL_QUESION_FETCH = "http://www.boilingstocks.com/qdroid/getques.php";
	
	/*
	 * 
	 * UI Elements
	 *
	 */
	private TextView question_text;
	private RadioGroup options;
	private Button submit, clear;
	private RadioButton r1,r2,r3,r4;
	private Question question;
	JSONObject jsonObject = new JSONObject();
	String[] choices = new String[4];

	@Override
	public void onCreate(Bundle savedInstanceState){
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_quiz);
		
		question_text = (TextView) findViewById(R.id.tv_question);
		options = (RadioGroup) findViewById(R.id.rg_answer);
		submit = (Button) findViewById(R.id.submit);
		submit.setOnClickListener(this);
		clear = (Button) findViewById(R.id.clear);
		
		r1 = (RadioButton) findViewById(R.id.radioButton1);
		r2 = (RadioButton) findViewById(R.id.radioButton2);
		r3 = (RadioButton) findViewById(R.id.radioButton3);
		r4 = (RadioButton) findViewById(R.id.radioButton4);
		question = new Question();
		final List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("id","1"));
		
		final JSONParser jsonParser = new JSONParser();
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
				}
				return true;
			}
			
			@Override
			protected void onPostExecute(Boolean b){
				pDialog.dismiss();
			}
		}.execute();
		
		
	}

	public void checkAnswer(){
		
		if(options.getCheckedRadioButtonId()==options.getChildAt(question.getAnswerId()-1).getId()){
			Toast.makeText(this, "Correct Answer!", Toast.LENGTH_SHORT).show();
		}
	}
	
	public void writeQuestion(){
		runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				question_text.setText(question.getqText());
				r1.setText(choices[0]);
				r2.setText(choices[1]);
				r3.setText(choices[2]);
				r4.setText(choices[3]);
			}
		});
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.submit: checkAnswer();
		break;
		default: return;
		}
	}
}
