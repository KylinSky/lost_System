package com.example.itents;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

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

import android.content.Intent;
import android.widget.Toast;

public class LonginServlet implements Runnable{
	//String serverPath="http://192.168.0.50:3000/login";
	String serverPath="http://10.10.16.26:3000/login";
   // String serverPath="http://169.254.95.126:3000/login";
	String username,password;
	//boolean falg = false;
	String msg="";
	MainActivity my;
	LonginServlet(){}
	LonginServlet(String username,String password,MainActivity Context){
		this.username=username;
		this.password=password;
		this.my=Context;
		//falg=falg(username,password);
System.out.println(username+"ddddddddddd");		
	}

	public void run() {

		HttpPost request=new HttpPost(serverPath);
		
		//������������
		List<NameValuePair> params=new ArrayList<NameValuePair>();
		
		//�Ѽ������û���д����Ϣ���뵽����������
		params.add(new BasicNameValuePair("username",username));
		params.add(new BasicNameValuePair("password",password));
		
		//�Ѳ���������HTTP������У������ñ��뷽ʽΪUTF-8
		try {
			
			request.setEntity(new UrlEncodedFormEntity(params,HTTP.UTF_8));
			
			//�̵߳�������Ҫ���Խ��û��������Ϣͨ��Post����ʽ���͵�������
			HttpClient httpclient=new DefaultHttpClient();
			
			//ִ�з��������� 
			HttpResponse response=httpclient.execute(request);
			
			//����������ȷ��Ӧ
			if(response.getStatusLine().getStatusCode()== HttpStatus.SC_OK)
			{
				//������ֵ����ַ���
			    msg=EntityUtils.toString(response.getEntity());
				System.out.println(msg);
				
				Intent it = new Intent();
				it.setClass(my, Loginaf.class);
				my.startActivity(it);
				
			}
			else
			{   
				//Toast.makeText(Context.this, "�û����� ���� ����",Toast.LENGTH_SHORT).show();
				System.out.println("����ʧ��");
			}
			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	
	}
	
	
	
	
	
	
}
