package com.example.itents;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
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

import android.app.Activity;
import android.app.Fragment;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class SecondFrameLayout extends Fragment {
	EditText  et2, et3, et4, et5, et6, et7;
	Button but1, but2, but3, but4;
	pic pd;
	byte b2[];
	String dates = "";
	String picPath="";
	ImageView iv;
	String rets = "";
   
    Bitmap bitmap1=null;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		return inflater.inflate(R.layout.secondframelayout, container, false);

	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		et2 = (EditText) getActivity().findViewById(R.id.editText22);
		et3 = (EditText) getActivity().findViewById(R.id.editText23);
		et4 = (EditText) getActivity().findViewById(R.id.editText24);
		et5 = (EditText) getActivity().findViewById(R.id.editText25);
		et6 = (EditText) getActivity().findViewById(R.id.editText26);
		et7 = (EditText) getActivity().findViewById(R.id.editText27);
		but1 = (Button) getActivity().findViewById(R.id.button21);
		but2 = (Button) getActivity().findViewById(R.id.button22);
		but3 = (Button) getActivity().findViewById(R.id.paizhaosec);
		but4 = (Button) getActivity().findViewById(R.id.libr);
		iv = (ImageView) getActivity().findViewById(R.id.imageView1);

     	
		but1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				new Thread(new Dateuplod()).start();
				 //new myTack().execute();
				Toast.makeText(getActivity(),"提交成功", Toast.LENGTH_SHORT).show();	
			}
		});
		but2.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
			
				et2.setText("");
				et3.setText("");
				et4.setText("");
				et5.setText("");
				et6.setText("");
				et7.setText("");

			}
		});
		//
		but3.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				pd = new pic();
				pd.pci();

			}

		});
		but4.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				pd = new pic();
				pd.piclist();
			}
		});
	}

	// 拍下的图片存到SDcard中
	class pic extends Handler {
		public void pci() {
			Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			i.putExtra(MediaStore.EXTRA_OUTPUT, "/storage/sdcard/");
			i.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
			SecondFrameLayout.this.startActivityForResult(i, 1);

		}

		public void piclist() {
			Intent openAlbumIntent = new Intent(Intent.ACTION_GET_CONTENT);
			
			openAlbumIntent.setType("image/*");
			startActivityForResult(openAlbumIntent, 0);

		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == Activity.RESULT_OK && requestCode==1) {
			boolean sdStatus = Environment.getExternalStorageState().equals(
					Environment.MEDIA_MOUNTED);

			System.out.println("SD卡是否存在" + sdStatus);
			// System.out.println(
			// Environment.getExternalStorageDirectory().getAbsolutePath()+"tttttttttt");

			 bitmap1 = (Bitmap) data.getExtras().get("data");// 获取相机返回的数据，并转换为Bitmap图片格式
			FileOutputStream b = null;
			SimpleDateFormat si = new SimpleDateFormat("HH.mm.ss");
			dates = si.format(new Date());

			
			File file = new File("/storage/sdcard/myImage/");
			if (!file.exists())
				file.mkdirs();


			String fileName = "/storage/sdcard/myImage/" + dates + ".jpg";
			// System.out.println(file.exists());
			b2 = bitmap1.toString().getBytes();

			try {
				// fil
				b = new FileOutputStream(fileName);
				// 设置图片样式
				bitmap1.compress(Bitmap.CompressFormat.JPEG, 100, b);
				b.write(b2);
				b.flush();
				b.close();
			} catch (FileNotFoundException e) {

				e.printStackTrace();

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			iv.setImageBitmap(bitmap1);
			
		}
System.out.println(resultCode==Activity.RESULT_OK);
		if(requestCode == 0 ){
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
	// 图片上传
	class Dateuplod implements Runnable {
		// String path = "http://169.254.95.126:3000/additemlost_image";
		String picurl = "/storage/sdcard/myImage/" + dates + ".jpg";
		String path = "http://10.10.16.26:3000/additemlost_image";

		// String path = "http://10.10.16.26:3000/additemlost";
		@Override
		public void run() {		
		 Map<String,String> map = new HashMap<String, String>();
		     
		      map.put("itemname", et2.getText().toString());
		      map.put("itemtype", et3.getText().toString());
		      map.put("owner", et4.getText().toString());
		      map.put("desc", et5.getText().toString());
		      map.put("losttime", et6.getText().toString());
		      //map.put("img", et1.getText().toString());
		      map.put("foundtime", et7.getText().toString());
		
		new FileImageUpload().uploadFile(bitmap1,map,path);
		
		
		}

	}

	// 图片上传
	/*class myTack extends AsyncTask {

		@Override
		protected Object doInBackground(Object... arg0) {
			String picurl = "/storage/sdcard/myImage/" + dates + ".jpg";
			// String url = "http://169.254.95.126:3000/additemlost_image";
			String url = "http://10.10.16.26:3000/additemlost_image";
			String rets = FileImageUpload.uploadFile(picurl, url);
			return rets;
		}

	}*/

}
