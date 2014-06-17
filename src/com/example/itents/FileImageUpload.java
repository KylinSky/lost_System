package com.example.itents;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;


import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;

public class FileImageUpload {

	 private static final String TAG = "uploadFile"; 
	 private static final int TIME_OUT = 10*10000000; //超时时间 
	 private static final String CHARSET = "utf-8"; //设置编码 
	 public static final String SUCCESS="1"; 
     public static final String FAILURE="0"; 
     public static final String LINE_END = "\r\n"; 
    // public static final String PREFIX="-----------------------------";
     public static final String PREFIX="---------7d4a6d158c9"; 

     //直接上传位图文件到服务器
     public static String uploadFile(Bitmap bmp,String RequestURL) { 
    	 
    	 String BOUNDARY ="---------------------------7d4a6d158c9";// UUID.randomUUID().toString(); //边界标识 随机生成 String PREFIX = "--" , LINE_END = "\r\n"; 
		 String CONTENT_TYPE = "multipart/form-data"; //内容类型 
		 try {
			 URL url = new URL(RequestURL); 
			 HttpURLConnection conn = (HttpURLConnection) url.openConnection();
             conn.setReadTimeout(TIME_OUT); 
             conn.setConnectTimeout(TIME_OUT); 
             conn.setDoInput(true); //允许输入流
			 conn.setDoOutput(true); //允许输出流
			 conn.setUseCaches(false); //不允许使用缓存 
			 conn.setRequestMethod("POST"); //请求方式 
			 conn.setRequestProperty("Charset", CHARSET); 
			 //设置编码 
			 conn.setRequestProperty("connection", "keep-alive"); 
			 conn.setRequestProperty("Content-Type", CONTENT_TYPE + ";boundary=" + BOUNDARY);
			// File file=new File(fileName);
			 if(bmp!=null && bmp.getByteCount()>0) { 
                 OutputStream outputSteam=conn.getOutputStream(); 
				 DataOutputStream dos = new DataOutputStream(outputSteam); 
				 StringBuffer sb = new StringBuffer(); 
				 sb.append("--");     
				 sb.append(BOUNDARY);     
				 sb.append("\r\n");

				// sb.append(PREFIX); 
				// sb.append(BOUNDARY);
				// sb.append(LINE_END); 
				 /** 
				 * 这里重点注意： 
				 * name里面的值为服务器端需要key 只有这个key 才可以得到对应的文件 
				 * filename是文件的名字，包含后缀名的 比如:abc.png 
				 */ 
				 //Log.e(TAG, "filename:"+file.getName()); 
				 sb.append("Content-Disposition: form-data; name=\"img\"; filename=\""+"FromCamera.PNG"+"\""+LINE_END);
				 sb.append("Content-Type: image/jpeg; charset="+CHARSET+LINE_END); 
				 sb.append(LINE_END);
				 byte[] temp=sb.toString().getBytes();
				 dos.write(temp,0,temp.length); 
				
				// InputStream is = new FileInputStream(file);
				// byte[] bytes = new byte[is.available()]; 
				 byte[] bytes =  ImageDispose.Bitmap2Bytes(bmp);
	
				 dos.write(bytes, 0, bytes.length); 
				 temp=LINE_END.getBytes();
				 dos.write(temp,0,temp.length);
				 byte[] end_data = ("\r\n--" + BOUNDARY + "--\r\n").getBytes();// 定义最后数据分隔线
				 //byte[] end_data = (PREFIX+BOUNDARY+"--"+LINE_END).getBytes(); 
				 dos.write(end_data,0,end_data.length); 
				 dos.flush();
				 System.out.println(sb.toString());
				 System.out.println("*********");
				 System.out.println("\r\n--" + BOUNDARY + "--\r\n");
				 /** 
				 * 获取响应码 200=成功 
				 * 当响应成功，获取响应的流 
				 */ 
				 int res = conn.getResponseCode(); 
				 Log.e(TAG, "response code:"+res); 
				 if(res == 200) 
				 {
				 return SUCCESS; 
				 }
				 else
				 {
					 return FAILURE;  
				 }
			 } 
		 } catch (MalformedURLException e) 
		 { e.printStackTrace(); } 
		 catch (IOException e) 
		 { e.printStackTrace(); } 
		 return FAILURE; 
    	 
     }

//上传单一图片和多个参数到服务器
public static String uploadFile(Bitmap bmp,Map < String , String > params ,String RequestURL) { 
    	 
    	 String BOUNDARY ="---------------------------7d4a6d158c9";// UUID.randomUUID().toString(); //边界标识 随机生成 String PREFIX = "--" , LINE_END = "\r\n"; 
		 String CONTENT_TYPE = "multipart/form-data"; //内容类型 
		 try {
			 URL url = new URL(RequestURL); 
			 HttpURLConnection conn = (HttpURLConnection) url.openConnection();
             conn.setReadTimeout(TIME_OUT); 
             conn.setConnectTimeout(TIME_OUT); 
             conn.setDoInput(true); //允许输入流
			 conn.setDoOutput(true); //允许输出流
			 conn.setUseCaches(false); //不允许使用缓存 
			 conn.setRequestMethod("POST"); //请求方式 
			 conn.setRequestProperty("Charset", CHARSET); 
			 //设置编码 
			 conn.setRequestProperty("connection", "keep-alive"); 
			 conn.setRequestProperty("Content-Type", CONTENT_TYPE + ";boundary=" + BOUNDARY);
			
			//  //下面开始上传文本类型的参数  
             StringBuilder sb = new StringBuilder ( ) ;  
             for ( Map.Entry < String , String > entry : params.entrySet ( ) )  
                 {  
            	 sb.append("--");     
				 sb.append(BOUNDARY);     
				 sb.append("\r\n"); 
                     sb.append ( "Content-Disposition: form-data; name=\""  
                             + entry.getKey () + "\"" + LINE_END ) ;  
                     sb.append ( "Content-Type: text/plain; charset="  
                             + CHARSET + LINE_END ) ;  
                     sb.append ( "Content-Transfer-Encoding: 8bit" + LINE_END ) ;  
                     sb.append ( LINE_END ) ;  
                     sb.append ( entry.getValue ( ) ) ;  
                     sb.append ( LINE_END ) ;  
                 }  

            DataOutputStream outStream = new DataOutputStream (  
                    conn.getOutputStream ( ) ) ;  
            outStream.write ( sb.toString ( ).getBytes ( ) ) ;

			 
			 
			 // File file=new File(fileName);
			 if(bmp!=null && bmp.getByteCount()>0) { 
                 OutputStream outputSteam=conn.getOutputStream(); 
				 DataOutputStream dos = new DataOutputStream(outputSteam); 
				 StringBuffer sbf = new StringBuffer(); 
				 sbf.append("--");     
				 sbf.append(BOUNDARY);     
				 sbf.append("\r\n");


				 /** 
				 * 这里重点注意： 
				 * name里面的值为服务器端需要key 只有这个key 才可以得到对应的文件 
				 * filename是文件的名字，包含后缀名的 比如:abc.png 
				 */ 
				 //下面开始上传文件; 
				 sbf.append("Content-Disposition: form-data; name=\"img\"; filename=\""+"imageUpload.JPG"+"\""+LINE_END);
				 sbf.append("Content-Type: image/jpeg; charset="+CHARSET+LINE_END); 
				 sbf.append(LINE_END);
				 byte[] temp=sbf.toString().getBytes();
				 dos.write(temp,0,temp.length); 
				
				// InputStream is = new FileInputStream(file);
				// byte[] bytes = new byte[is.available()]; 
				 byte[] bytes =  ImageDispose.Bitmap2Bytes(bmp);
	
				 dos.write(bytes, 0, bytes.length); 
				 temp=LINE_END.getBytes();
				 dos.write(temp,0,temp.length);
				 byte[] end_data = ("\r\n--" + BOUNDARY + "--\r\n").getBytes();// 定义最后数据分隔线
				 //byte[] end_data = (PREFIX+BOUNDARY+"--"+LINE_END).getBytes(); 
				 dos.write(end_data,0,end_data.length); 
				 dos.flush();
				 System.out.println(sbf.toString());
				 System.out.println("*********");
				 System.out.println("\r\n--" + BOUNDARY + "--\r\n");
				 /** 
				 * 获取响应码 200=成功 
				 * 当响应成功，获取响应的流 
				 */ 
				 int res = conn.getResponseCode(); 
				 Log.e(TAG, "response code:"+res); 
				 if(res == 200) 
				 {
					// 开始GET数据
					 String encoding = conn.getContentEncoding();
					 InputStream is = conn.getInputStream();
					 int read = -1;
					 ByteArrayOutputStream baos = new ByteArrayOutputStream();
					 while ((read = is.read()) != -1) {
					 baos.write(read);
					 }
					 byte[] data = baos.toByteArray();
					 baos.close();

					 String content = null;
					 if (encoding != null) {
					  content = new String(data, encoding);
					 } else {
					   content = new String(data);
					 }	 
				 return content; 
				 }
				 else
				 {
					 return FAILURE;  
				 }
			 } 
		 } catch (MalformedURLException e) 
		 { e.printStackTrace(); } 
		 catch (IOException e) 
		 { e.printStackTrace(); } 
		 return FAILURE; 
    	 
     }
    
//上传单一图片和多个参数到服务器,规定是缩略图和全图
public static String uploadFile(Bitmap thumnail,Bitmap origin,Map < String , String > params ,String RequestURL) { 
  	 
  	 String BOUNDARY ="---------------------------7d4a6d158c9";// UUID.randomUUID().toString(); //边界标识 随机生成 String PREFIX = "--" , LINE_END = "\r\n"; 
		 String CONTENT_TYPE = "multipart/form-data"; //内容类型 
		 try {
			 URL url = new URL(RequestURL); 
			 HttpURLConnection conn = (HttpURLConnection) url.openConnection();
           conn.setReadTimeout(TIME_OUT); 
           conn.setConnectTimeout(TIME_OUT); 
           conn.setDoInput(true); //允许输入流
			 conn.setDoOutput(true); //允许输出流
			 conn.setUseCaches(false); //不允许使用缓存 
			 conn.setRequestMethod("POST"); //请求方式 
			 conn.setRequestProperty("Charset", CHARSET); 
			 //设置编码 
			 conn.setRequestProperty("connection", "keep-alive"); 
			 conn.setRequestProperty("Content-Type", CONTENT_TYPE + ";boundary=" + BOUNDARY);
			
			//  //下面开始上传文本类型的参数  
           StringBuilder sb = new StringBuilder ( ) ;  
           for ( Map.Entry < String , String > entry : params.entrySet ( ) )  
               {  
          	 sb.append("--");     
				 sb.append(BOUNDARY);     
				 sb.append("\r\n"); 
                   sb.append ( "Content-Disposition: form-data; name=\""  
                           + entry.getKey () + "\"" + LINE_END ) ;  
                   sb.append ( "Content-Type: text/plain; charset="  
                           + CHARSET + LINE_END ) ;  
                   sb.append ( "Content-Transfer-Encoding: 8bit" + LINE_END ) ;  
                   sb.append ( LINE_END ) ;  
                   sb.append ( entry.getValue ( ) ) ;  
                   sb.append ( LINE_END ) ;  
               }  

          DataOutputStream outStream = new DataOutputStream (  
                  conn.getOutputStream ( ) ) ;  
          outStream.write ( sb.toString ( ).getBytes ( ) ) ;

			 
			 
			 // 下面开始上传文件集合;
             OutputStream outputSteam=conn.getOutputStream(); 
			 DataOutputStream dos = new DataOutputStream(outputSteam);
			 StringBuffer sbf = new StringBuffer();
			 Bitmap target_temp=null;;
             for(int j=0;j<2;j++)
             {
            	 if(j==0) 
            	 {
            		 target_temp=thumnail;
            	 }
            	 else
            	 {
            		 target_temp=origin;
            	 }
			  if(target_temp!=null && target_temp.getByteCount()>0) { 
                 
				 
				 sbf.append("--");     
				 sbf.append(BOUNDARY);     
				 sbf.append("\r\n");

				 /** 
				 * 这里重点注意： 
				 * name里面的值为服务器端需要key 只有这个key 才可以得到对应的文件 
				 * filename是文件的名字，包含后缀名的 比如:abc.png 
				 */ 
				 //下面开始上传文件; 
				 if(j==0) 
            	 {
					 sbf.append("Content-Disposition: form-data; name=\"img1\"; filename=\""+"thumnail.JPG"+"\""+LINE_END);
            	 }
            	 else
            	 {
            		 sbf.append("Content-Disposition: form-data; name=\"img2\"; filename=\""+"origin.JPG"+"\""+LINE_END);
            	 }
				 
				// sbf.append("Content-Type: application/octet-stream; charset="+CHARSET+LINE_END); 
				 sbf.append("Content-Type:image/jpeg; charset="+CHARSET+LINE_END); 
				 sbf.append(LINE_END);
				 byte[] temp=sbf.toString().getBytes();
				 dos.write(temp,0,temp.length); 

				 byte[] bytes =  ImageDispose.Bitmap2Bytes(target_temp);
	
				 dos.write(bytes, 0, bytes.length); 
				 temp=LINE_END.getBytes();
				 dos.write(temp,0,temp.length);
				 
			   }//end of if bitmap is available
             }//end of bitmap file upload
             
             byte[] end_data = ("\r\n--" + BOUNDARY + "--\r\n").getBytes();// 定义最后数据分隔线				
			 dos.write(end_data,0,end_data.length); 
			 
				 dos.flush();

				 /** 
				 * 获取响应码 200=成功 
				 * 当响应成功，获取响应的流 
				 */ 
				 int res = conn.getResponseCode(); 
				 Log.e(TAG, "response code:"+res); 
				 if(res == 200) 
				 {
				 return SUCCESS; 
				 }
				 else
				 {
					 return FAILURE;  
				 }
			 
		 } catch (MalformedURLException e) 
		 { e.printStackTrace(); } 
		 catch (IOException e) 
		 { e.printStackTrace(); } 
		 return FAILURE; 
  	 
   }

	public static String uploadFile(String fileName,String RequestURL) { 
		 String BOUNDARY ="---------------------------7d4a6d158c9";// UUID.randomUUID().toString(); //边界标识 随机生成 String PREFIX = "--" , LINE_END = "\r\n"; 
		 String CONTENT_TYPE = "multipart/form-data"; //内容类型 
		 try {
			 URL url = new URL(RequestURL); 
			 HttpURLConnection conn = (HttpURLConnection) url.openConnection();
             conn.setReadTimeout(TIME_OUT); 
             conn.setConnectTimeout(TIME_OUT); 
             conn.setDoInput(true); //允许输入流
			 conn.setDoOutput(true); //允许输出流
			 conn.setUseCaches(false); //不允许使用缓存 
			 conn.setRequestMethod("POST"); //请求方式 
			 conn.setRequestProperty("Charset", CHARSET); 
			 //设置编码 
			 conn.setRequestProperty("connection", "keep-alive"); 
			 conn.setRequestProperty("Content-Type", CONTENT_TYPE + ";boundary=" + BOUNDARY);
			 File file=new File(fileName);
			 if(file!=null) { 
				 /** * 当文件不为空，把文件包装并且上传 */
				 OutputStream outputSteam=conn.getOutputStream(); 
				 DataOutputStream dos = new DataOutputStream(outputSteam); 
				 StringBuffer sb = new StringBuffer(); 
				 sb.append("--");     
				 sb.append(BOUNDARY);     
				 sb.append("\r\n");

				// sb.append(PREFIX); 
				// sb.append(BOUNDARY);
				// sb.append(LINE_END); 
				 /** 
				 * 这里重点注意： 
				 * name里面的值为服务器端需要key 只有这个key 才可以得到对应的文件 
				 * filename是文件的名字，包含后缀名的 比如:abc.png 
				 */ 
				 Log.e(TAG, "filename:"+file.getName()); 
				 sb.append("Content-Disposition: form-data; name=\"img\"; filename=\""+file.getName()+"\""+LINE_END);
				 sb.append("Content-Type: application/octet-stream; charset="+CHARSET+LINE_END); 
				 sb.append(LINE_END);
				 byte[] temp=sb.toString().getBytes();
				 dos.write(temp,0,temp.length); 
				 InputStream is = new FileInputStream(file);
				 byte[] bytes = new byte[is.available()]; 
				 int len = 0; 
				 while((len=is.read(bytes))!=-1) 
				 { 
					dos.write(bytes, 0, len); 
					 Log.e(TAG, "file size :"+len+" read!"); 
				 } 
				 is.close(); 
				 temp=LINE_END.getBytes();
				 dos.write(temp,0,temp.length);
				 byte[] end_data = ("\r\n--" + BOUNDARY + "--\r\n").getBytes();// 定义最后数据分隔线
				 //byte[] end_data = (PREFIX+BOUNDARY+"--"+LINE_END).getBytes(); 
				 dos.write(end_data,0,end_data.length); 
				 dos.flush();
				 System.out.println(sb.toString());
				 System.out.println("*********");
				 System.out.println("\r\n--" + BOUNDARY + "--\r\n");
				 /** 
				 * 获取响应码 200=成功 
				 * 当响应成功，获取响应的流 
				 */ 
				 int res = conn.getResponseCode(); 
				 Log.e(TAG, "response code:"+res); 
				 if(res == 200) 
				 {
				 return SUCCESS; 
				 }
				 else
				 {
					 return FAILURE;  
				 }
			 } 
		 } catch (MalformedURLException e) 
		 { e.printStackTrace(); } 
		 catch (IOException e) 
		 { e.printStackTrace(); } 
		 return FAILURE; 
	 } 
//图片下载
 
	
}
