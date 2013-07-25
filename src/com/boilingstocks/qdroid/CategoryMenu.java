package com.boilingstocks.qdroid;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
public class CategoryMenu extends Activity implements OnClickListener{

	private Button his, ent, spo, sci,ran;
	Intent intent;
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
	
	public void changeText(){
		
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
