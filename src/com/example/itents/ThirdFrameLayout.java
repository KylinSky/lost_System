package com.example.itents;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import com.example.itents.R;
import com.example.itents.SecondFrameLayout.Dateuplod;
import com.example.itents.SecondFrameLayout.pic;

import android.app.Activity;
import android.app.Fragment;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class ThirdFrameLayout extends Fragment{
	EditText et1,et2,et3,et4,et5,et6;
	Button but1,but2,but3;
	 pic pd;
	 byte b2[];
	 ImageView iv;
	 Bitmap bitmap1=null;
	 String dates = "";
		String picPath="";
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		return inflater.inflate(R.layout.thirdframelayout, container,false);
		
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {		
		super.onActivityCreated(savedInstanceState);
		
		et1 = (EditText)getActivity().findViewById(R.id.editText31);
		et2 = (EditText)getActivity().findViewById(R.id.editText32);
		et3 = (EditText)getActivity().findViewById(R.id.editText33);
		et4 = (EditText)getActivity().findViewById(R.id.editText34);
		et5 = (EditText)getActivity().findViewById(R.id.editText35);
		et6 = (EditText)getActivity().findViewById(R.id.editText36);
		
		but1 = (Button)getActivity().findViewById(R.id.button31);
		but2 = (Button)getActivity().findViewById(R.id.button32);
		but3 = (Button)getActivity().findViewById(R.id.paozhaothr);
		iv = (ImageView)getActivity().findViewById(R.id.imageView2);
		but1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				new Thread(new Dateuplod()).start();
				Toast.makeText(getActivity(),"提交成功", Toast.LENGTH_SHORT).show();
			}
		});
        but2.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				et1.setText("");
				et2.setText("");
				et3.setText("");
				et4.setText("");
				et5.setText("");
				et6.setText("");
				
				
			}
		});
	
        but3.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
			pd = new pic();
			pd.pci();
			
			}

		});
	
	
	}
	class pic extends Handler{
		 public void pci(){
			 Intent openAlbumIntent = new Intent(Intent.ACTION_GET_CONTENT);
				
				openAlbumIntent.setType("image/*");
				startActivityForResult(openAlbumIntent, 0);

			
		 }
	 }
	 @Override
		public void onActivityResult(int requestCode, int resultCode,
				Intent data) {			
			super.onActivityResult(requestCode, resultCode, data);
			if (resultCode == Activity.RESULT_OK && requestCode==0) {
				Uri uri = data.getData();  
		       	 String[] pojo = { MediaStore.Images.Media.DATA };        
					@SuppressWarnings("deprecation")
					Cursor cursor = getActivity().managedQuery(uri, pojo, null, null, null); 
		       	 if (cursor != null) {  
		                ContentResolver cr =getActivity().getContentResolver();  
		                int colunm_index = cursor  
		                        .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);  
		                
		                cursor.moveToFirst();  
		                String path = cursor.getString(colunm_index);
		                if (path.endsWith("jpg") || path.endsWith("png")) {  
		                    picPath = path;  
							try {
								bitmap1 = BitmapFactory.decodeStream(cr.openInputStream(uri));
							} catch (FileNotFoundException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}  
		                    iv.setImageBitmap(bitmap1);  

			    
			}
				}
			
		}
	}
	class Dateuplod implements Runnable{
		String picurl = "/storage/sdcard/myImage/" + dates + ".jpg";
		String path = "http://10.10.16.26:3000/addItemFound_image";
		@Override
		public void run() {
			 Map<String,String> map = new HashMap<String, String>();
			  map.put("itemid", et1.getText().toString());
			  map.put("itemid", et2.getText().toString());
			  map.put("itemid", et3.getText().toString());
			  map.put("itemid", et4.getText().toString());
			  map.put("itemid", et5.getText().toString());			  
			  map.put("itemid", et6.getText().toString());
			
			new FileImageUpload().uploadFile(bitmap1,map,path);
		
		
		
		}
		 
		 
		 
	}
	
	
	
	
	

}
