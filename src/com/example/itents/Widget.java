package com.example.itents;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.itents.R;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.provider.OpenableColumns;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

public class Widget extends Activity {
	Button but1;
	EditText et1, et2, et3;
	CheckBox checkbox;
	String st1, st2;
	SharedPreferences sp;
	MainActivity main;
	String strServer;

	Widget(final MainActivity Context) {
		but1 = (Button) Context.findViewById(R.id.leftchange);
		et1 = (EditText) Context.findViewById(R.id.editText1);
		et2 = (EditText) Context.findViewById(R.id.editText2);

		checkbox = (CheckBox) Context.findViewById(R.id.checkbox);
		checkbox.setChecked(true);
		sp = Context.getSharedPreferences("Password", Context.MODE_APPEND);
		// Fileinput();
		et1.setText(sp.getString("username", ""));
		et2.setText(sp.getString("password", ""));
		// but1.setOnClickListener(new Listen(Context,et1,et2));
		// 点击就跳转
		// new Thread(new HttpPost().new Getdate()).start();

		but1.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				st1 = et1.getText().toString();
				st2 = et2.getText().toString();
				
				if (flag(st1, st2)) {
					if (checkbox.isChecked()) {
						Editor editor = sp.edit();
						editor.putString("username", et1.getText().toString());
						editor.putString("password", et2.getText().toString());
						editor.commit();
					}
					/*
					 * Intent it = new Intent(); it.setClass(Context,
					 * Loginaf.class); Context.startActivity(it);
					 */
					new Thread(new LonginServlet(st1, st2, Context)).start();

				} else {
					Toast.makeText(Context, "用户名或密码错误", Toast.LENGTH_SHORT)
							.show();

				}
			}
		});

	}

	public boolean flag(String str1, String str2) {
		// System.out.println(str2);
		// System.out.println(str2.equals(123456));
		if (str1.equals("tom") && str2.equals("123456")) {
			return true;
		} else
			return false;

	}

	
}
