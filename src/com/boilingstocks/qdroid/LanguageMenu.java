package com.boilingstocks.qdroid;

import android.os.Bundle;
import android.preference.PreferenceActivity;

public class LanguageMenu extends PreferenceActivity{
	
	public static String LANGUAGE_ENGLISH = "english";
	public static String LANGUAGE_BAHDINI = "bahdini";
	public static String LANGUAGE_KURMANJI = "kurmanji";
	public static String LANGUAGE_SORANI = "sorani";
	public static String LANGUAGE = "lang";

	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.prefs);
		
		
	}


}
