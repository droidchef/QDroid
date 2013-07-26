package com.boilingstocks.qdroid;

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

public class LanguageMenu extends PreferenceActivity implements OnSharedPreferenceChangeListener{
	
	public static String LANGUAGE_ENGLISH = "english";
	public static String LANGUAGE_BAHDINI = "bahdini";
	public static String LANGUAGE_KURMANJI = "kurmanji";
	public static String LANGUAGE_SORANI = "sorani";
	public static String LANGUAGE = "lang";

	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.prefs);
		
		SharedPreferences p = PreferenceManager.getDefaultSharedPreferences(this);
		LANGUAGE = p.getString("lang", "english");
		LANGUAGE = LANGUAGE.toLowerCase();
	}

	@Override
	public void onSharedPreferenceChanged(SharedPreferences arg0, String arg1) {
		// TODO Auto-generated method stub

	}


}
