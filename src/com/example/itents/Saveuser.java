package com.example.itents;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;

public class Saveuser extends Activity implements Runnable{
String st1,st2;
	Saveuser(){}
	Saveuser(String st1,String st2){
		this.st1=st1;
		this.st2=st2;
		
		
	}
	@Override
	public void run() {		
		try {
			FileOutputStream  out =openFileOutput("tom.txt",Context.MODE_PRIVATE);
			JSONObject json = new JSONObject();
			json.put("username",st1);
			json.put("password", st2);
			out.write(json.toString().getBytes());
			out.flush();
			out.close();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
	} 

}
