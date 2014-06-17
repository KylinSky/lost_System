package com.example.itents;

import com.example.itents.R;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;

public class Loginaf extends Activity{
	private ImageBt ib1,ib2,ib3,ib4;
	FirstFrameLayout ff1 = null;
	SecondFrameLayout ff2 = null;
	ThirdFrameLayout ff3 = null;
	FourFrameLayout ff4 = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
		super.onCreate(savedInstanceState);
	        this.setContentView(R.layout.report);
	        
	        ib1=(ImageBt)findViewById(R.id.bt_menu);
	        ib2=(ImageBt)findViewById(R.id.bt_find);
	        ib3=(ImageBt)findViewById(R.id.bt_lost);
	        ib4=(ImageBt)findViewById(R.id.bt_yourself);
	        
	        
	        ib1.setTextViewText("目录");
	        ib1.setImageResource(R.drawable.tushu);
	        ib2.setTextViewText("寻主");
	        ib2.setImageResource(R.drawable.findpeople);
	        ib3.setTextViewText("寻物");
	        ib3.setImageResource(R.drawable.foundthing);
	        ib4.setTextViewText("目录");
	        
	        ib4.setImageResource(R.drawable.setting);
	     //ib5.setImageResource(R.drawable.ic_launcher);
	        buttonlistener b1 = new buttonlistener();
	        ib1.setOnClickListener(b1);
	        ib2.setOnClickListener(b1);
	        ib3.setOnClickListener(b1);
	        ib4.setOnClickListener(b1);
	        if(ff1==null){ff1=new FirstFrameLayout();}
			//显示
			FragmentManager fm=Loginaf.this.getFragmentManager();
			
			FragmentTransaction tran=fm.beginTransaction();
			
			//过渡效果
			tran.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
			
			//过渡
			tran.replace(R.id.frame1,ff1 );
			
			//生效
			tran.commit();
	    }
	    class buttonlistener implements OnClickListener{

			public void onClick(View arg0) {
				ib1.setBackgroundResource(R.drawable.bg1);
				ib2.setBackgroundResource(R.drawable.bg2);
				ib3.setBackgroundResource(R.drawable.bg2);
				ib4.setBackgroundResource(R.drawable.bg2);
				// TODO Auto-generated method stub
				if(arg0.getId()== R.id.bt_menu){
					if(ff1==null){ff1=new FirstFrameLayout();}
					//显示
					FragmentManager fm=Loginaf.this.getFragmentManager();
					
					FragmentTransaction tran=fm.beginTransaction();
					
					//过渡效果
					tran.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
					
					//过渡
					tran.replace(R.id.frame1,ff1 );
					
					//生效
					tran.commit();
				}
				if(arg0.getId()== R.id.bt_find){
					ib1.setBackgroundResource(R.drawable.bg2);
					ib2.setBackgroundResource(R.drawable.bg1);
					ib3.setBackgroundResource(R.drawable.bg2);
					ib4.setBackgroundResource(R.drawable.bg2);
					if(ff2==null){ff2=new SecondFrameLayout();}
					//显示
					FragmentManager fm=Loginaf.this.getFragmentManager();
					
					FragmentTransaction tran=fm.beginTransaction();
					
					//过渡效果
					tran.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
					
					//过渡
					tran.replace(R.id.frame1,ff2 );
					
					//生效
					tran.commit();
				}
				if(arg0.getId()== R.id.bt_lost){
					ib1.setBackgroundResource(R.drawable.bg2);
					ib2.setBackgroundResource(R.drawable.bg2);
					ib3.setBackgroundResource(R.drawable.bg1);
					ib4.setBackgroundResource(R.drawable.bg2);
					if(ff3==null){ff3=new ThirdFrameLayout();}
					//显示
					FragmentManager fm=Loginaf.this.getFragmentManager();
					
					FragmentTransaction tran=fm.beginTransaction();
					
					//过渡效果
					tran.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
					
					//过渡
					tran.replace(R.id.frame1,ff3 );
					
					//生效
					tran.commit();
				}
				if(arg0.getId()== R.id.bt_yourself){
					ib1.setBackgroundResource(R.drawable.bg2);
					ib2.setBackgroundResource(R.drawable.bg2);
					ib3.setBackgroundResource(R.drawable.bg2);
					ib4.setBackgroundResource(R.drawable.bg1);
					if(ff4==null){ff4=new FourFrameLayout();}
					//显示
					FragmentManager fm=Loginaf.this.getFragmentManager();
					
					FragmentTransaction tran=fm.beginTransaction();
					
					//过渡效果
					tran.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
					
					//过渡
					tran.replace(R.id.frame1,ff4 );
					
					//生效
					tran.commit();
				}
			}
	    	
	    }
	 
	
}
