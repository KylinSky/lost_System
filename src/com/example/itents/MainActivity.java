package com.example.itents;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.example.itents.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class MainActivity extends Activity {
Button bt1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);		
		setContentView(R.layout.activity_main);
		
		new Widget(this);
		this.setTitle("Lost & Found");
	    
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
