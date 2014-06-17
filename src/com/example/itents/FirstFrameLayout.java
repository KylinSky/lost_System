package com.example.itents;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.itents.R;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.PendingIntent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.test.MoreAsserts;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class FirstFrameLayout extends Fragment {
	ListView lv;
	String strServer = "";
	bbt hander;
	
	Map<String, Object> map;
	String result = "",results = "";

	JSONArray jsonarray;
	ImageView[] imageView;
	ImageView pt;
	List<Map<String, Object>> list;
	SimpleAdapter adapter;
	Bitmap serverResult = null;
	Button but;
	ProgressBar pg;

	// 最后可见条目的索引
	private int lastVisibleIndex;
	private Handler handler;
	private int MaxDateNum;
	View view;
	Button search;
	EditText searchtext;
	String searesult="";
	int i = 0;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		return inflater.inflate(R.layout.firstframelayout, container, false);

	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {

		super.onActivityCreated(savedInstanceState);
		// 不是在同一个xml文件中要进行转换
		/* LayoutInflater inflater =LayoutInflater.from(getActivity()); */
		view = getActivity().getLayoutInflater().inflate(R.layout.listview,
				null);

		search = (Button) getActivity().findViewById(R.id.search);
		searchtext = (EditText) getActivity().findViewById(R.id.searchtext);

		lv = (ListView) this.getActivity().findViewById(R.id.listView1);
		but = (Button) getActivity().findViewById(R.id.bt_load);
		pg = (ProgressBar) getActivity().findViewById(R.id.pg);

		handler = new Handler();

		hander = new bbt();
		new Thread(new getdate()).start();

		but.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				pg.setVisibility(View.VISIBLE);// 将进度条可见
				but.setVisibility(View.GONE);// 按钮不可见

				handler.postDelayed(new Runnable() {

					@Override
					public void run() {
						pg.setVisibility(View.GONE);// 将进度条可见
						but.setVisibility(View.VISIBLE);// 按钮不可见
						i++;
						adapter.notifyDataSetChanged();
						hander = new bbt();
						new Thread(new getdate()).start();
						if(result.equals(""))
							Toast.makeText(getActivity(), "已到底部", Toast.LENGTH_SHORT).show();
						
					}
				}, 2000);

			}
		});

		search.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				searesult = searchtext.getText().toString();
				adapter.notifyDataSetChanged();
				
				new Thread(new search()).start();
				hander = new bbt();
			}
		});

	}
	//搜索功能
	
class search implements Runnable{

	@Override
	public void run() {
		if(!searesult.equals("")){	
			 Map<String,String> map = new HashMap<String, String>();
			 map.put("criteria", searesult);
			 map.put("itemtype", "lost");
			 map.put("page", "0");
			 map.put("pagesize", "3");
			new HttpUtils();
			String pt;
			try {
				results = HttpUtils.submitPostData(new URL("http://10.10.16.26:3000/SearchItembySize"), map, "utf-8");
System.out.println(results);
              
             //adapter.notifyDataSetChanged();
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Message msg = new Message();
			msg.what = 2;
			hander.sendMessage(msg);
			
			
			 }
		
	}
	
}


class getdate implements Runnable {

		@Override
		public void run() {
			System.out.println("tt");
			try {

				String str = new HttpUtils().sendGetRequest(new URL(
						"http://10.10.16.26:3000/getItemLostcount"));
				try {
					JSONObject json = new JSONObject(str);
					MaxDateNum = Integer.parseInt(json.getString("result"));
				} catch (JSONException e) {
					
					e.printStackTrace();
				}

				/*
				 * result = new HttpUtils().sendGetRequest(new URL(
				 * "http://169.254.95.126:3000/getItemLostBySize?page="
				 * +0+"&pagesize="+1));
				 */
				result = new HttpUtils().sendGetRequest(new URL(
						"http://10.10.16.26:3000/getItemLostBySize?page=" + i
								+ "&pagesize=" + 4));
				
				
			
	
				Message msg = new Message();
				msg.what = 1;
				hander.sendMessage(msg);
			} catch (MalformedURLException e) {

				e.printStackTrace();
			}
		}
	}

	class bbt extends Handler {

		@Override
		public void handleMessage(Message msg) {

			super.handleMessage(msg);

			if (msg.what == 1) {
				System.out.println(result);

				adapter = new SimpleAdapter(getActivity(), getList(result),
						R.layout.listview, new String[] { "title", "detail",
								"time", "attn",  "img" }, new int[] {
								R.id.lvtv1, R.id.lvtv2, R.id.lvtv3, R.id.lvtv4,
								 R.id.image });

				lv.addFooterView(view);
				lv.setAdapter(adapter);
				// lv.invalidate();

				adapter.notifyDataSetChanged();
			
			} else {

				System.out.println("访问失败 ");
			}
			
			if (msg.what == 2) {
System.out.println(results);					
        adapter = new SimpleAdapter(getActivity(), getList2(results),
		R.layout.listview, new String[] { "title", "detail",
				"time", "attn", "img" }, new int[] {
				R.id.lvtv1, R.id.lvtv2, R.id.lvtv3, R.id.lvtv4,
				 R.id.image });
        lv.setAdapter(adapter);
System.out.println("添加成功");       
			} else {

				System.out.println("访问失败 ");
			}
		}

	}
	public List<Map<String, Object>> getList2(String str) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		try {
			jsonarray = new JSONArray(str);

			System.out.println(jsonarray.length());

			for (int i = 0; i < jsonarray.length(); i++) {
				map = new HashMap<String, Object>();
				map.put("title",
						jsonarray.getJSONObject(i).getString("itemname"));				
				/*map.put("detail", jsonarray.getJSONObject(i)
						.getString("itemtype"));*/
				map.put("attn", jsonarray.getJSONObject(i).getString("desc"));
				map.put("time",
						jsonarray.getJSONObject(i).getString("losttime"));
				
				//map.put("owner", jsonarray.getJSONObject(i).getString("owner"));
				map.put("img", jsonarray.getJSONObject(i).getString("img"));
				System.out.println(jsonarray.getJSONObject(i).getString("img"));

				DelayTask dt = new DelayTask();
				dt.execute("");

				// 10.10.16.26

				list.add(map);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return list;
	}
	public List<Map<String, Object>> getList(String str) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		try {
			jsonarray = new JSONArray(str);

			System.out.println(jsonarray.length());

			for (int i = 0; i < jsonarray.length(); i++) {
				map = new HashMap<String, Object>();
				map.put("title",
						jsonarray.getJSONObject(i).getString("itemname"));				
				map.put("detail", jsonarray.getJSONObject(i)
						.getString("itemtype"));
				map.put("attn", jsonarray.getJSONObject(i).getString("desc"));
				map.put("time",
						jsonarray.getJSONObject(i).getString("losttime"));
				
				//map.put("owner", jsonarray.getJSONObject(i).getString("owner"));
				map.put("img", jsonarray.getJSONObject(i).getString("img"));
				System.out.println(jsonarray.getJSONObject(i).getString("img"));

				DelayTask dt = new DelayTask();
				dt.execute("");

				// 10.10.16.26

				list.add(map);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return list;
	}

	// 图片下载
	class MyTask extends AsyncTask<String, Integer, Bitmap> {
		// 从网站上获取图片
		int target = 0;

		@Override
		protected Bitmap doInBackground(String... params) {
			target = Integer.parseInt(params[1]);

			this.publishProgress(1);
			HttpClient hc = new DefaultHttpClient();

			// 提供get的位置

			HttpGet hg = new HttpGet(params[0]);

			Bitmap bm = null;

			this.publishProgress(10);

			try {

				HttpResponse hr = hc.execute(hg);

				this.publishProgress(70);

				bm = BitmapFactory.decodeStream(hr.getEntity().getContent());

			} catch (ClientProtocolException e) {
				e.printStackTrace();
				return null;
			} catch (IOException e) {

				e.printStackTrace();
				return null;
			}
			System.out.println("bm:" + bm);
			this.publishProgress(100);

			return bm;
		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			System.out.println("当前进度" + values[0]);
		}

		// 当图片回来了（doInBackground函数结束，并有返回值返回)
		@Override
		protected void onPostExecute(Bitmap result) {

			System.out.println("Server ack! ok!");
			serverResult = result;

			// iv.setImageBitmap(serverResult);
			imageView[target].setImageBitmap(serverResult);
		}
		// 图片回来了

	}

	class DelayTask extends AsyncTask<String, Integer, String> {

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(String result) {

			MyTask[] tasks = new MyTask[jsonarray.length()];

			imageView = new ImageView[jsonarray.length()];

			for (int i = 0; i < jsonarray.length(); i++) {
				System.out.println("goint to create task!");
				View temp = lv.getChildAt(i);
				System.out.println("success in getChildAt!");
				if (temp != null) {
					System.out.println("List Item " + i + " found!");
				} else {
					System.out.println("List Item " + i + " not found!");
				}

				imageView[i] = (ImageView) temp.findViewById(R.id.image);
				System.out.println(imageView[i] + "tttttt");
				if (imageView[i] != null) {
					System.out.println("imageView " + i + " found!");
				} else {
					System.out.println("imageView " + i + " not found!");

				}

				tasks[i] = new MyTask();
				try {
					System.out.println("going to fetch image"
							+ jsonarray.getJSONObject(i).getString("img"));
					// tasks[i].execute("http://169.254.95.126:3000/uploads/"+jsonarray.getJSONObject(i).getString("img"),String.valueOf(i));
					tasks[i].execute("http://10.10.16.26:3000/uploads/"
							+ jsonarray.getJSONObject(i).getString("img"),
							String.valueOf(i));
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}	
	}

}
	
	
	
	

