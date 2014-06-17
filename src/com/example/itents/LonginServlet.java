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
		
		//创建参数集合
		List<NameValuePair> params=new ArrayList<NameValuePair>();
		
		//把见面上用户填写的信息加入到参数集合中
		params.add(new BasicNameValuePair("username",username));
		params.add(new BasicNameValuePair("password",password));
		
		//把参数集放入HTTP请求包中，并设置编码方式为UTF-8
		try {
			
			request.setEntity(new UrlEncodedFormEntity(params,HTTP.UTF_8));
			
			//线程的主体主要用以将用户输入的信息通过Post的形式发送到服务器
			HttpClient httpclient=new DefaultHttpClient();
			
			//执行服务器访问 
			HttpResponse response=httpclient.execute(request);
			
			//服务器能正确响应
			if(response.getStatusLine().getStatusCode()== HttpStatus.SC_OK)
			{
				//将返回值变成字符串
			    msg=EntityUtils.toString(response.getEntity());
				System.out.println(msg);
				
				Intent it = new Intent();
				it.setClass(my, Loginaf.class);
				my.startActivity(it);
				
			}
			else
			{   
				//Toast.makeText(Context.this, "用户名或 密码 错误",Toast.LENGTH_SHORT).show();
				System.out.println("访问失败");
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
