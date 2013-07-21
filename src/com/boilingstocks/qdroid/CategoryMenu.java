package com.boilingstocks.qdroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
public class CategoryMenu extends Activity implements OnClickListener{

	private Button his, ent, spo, sci;
	Intent intent;
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.actiivty_category);
		
		his = (Button) findViewById(R.id.cat1);
		ent = (Button) findViewById(R.id.cat4);
		spo = (Button) findViewById(R.id.cat2);
		sci = (Button) findViewById(R.id.cat3);
		
		his.setOnClickListener(this);
		ent.setOnClickListener(this);
		spo.setOnClickListener(this);
		sci.setOnClickListener(this);
		
		intent = new Intent(CategoryMenu.this,Quiz.class);
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		
		case R.id.cat1: intent.putExtra("cat", "HISTORY");
						startActivity(intent);
			break;
		case R.id.cat2: intent.putExtra("cat", "SPORTS");
						startActivity(intent);
			break;
		case R.id.cat3: intent.putExtra("cat", "SCIENCE");
						startActivity(intent);
			break;
		case R.id.cat4: intent.putExtra("cat", "ENTERTAINMENT");
						startActivity(intent);
			break;
		default:
		}
	}

}
