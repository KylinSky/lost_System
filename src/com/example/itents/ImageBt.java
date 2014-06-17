package com.example.itents;

import com.example.itents.R;

import android.content.Context;  
import android.util.AttributeSet;  
import android.view.LayoutInflater;  
import android.view.View;
import android.widget.ImageView;  
import android.widget.LinearLayout;  
import android.widget.TextView;  
  
public class ImageBt extends LinearLayout {  
  
    private ImageView iv;  
    private TextView  tv;  
  
    public ImageBt(Context context) {  
        this(context, null);  
    }  
  
    public ImageBt(Context context, AttributeSet attrs) {  
        super(context, attrs);  
        // 导入布局  
       View  v = LayoutInflater.from(context).inflate(R.layout.firstdo, this, true);  
        iv = (ImageView)v.findViewById(R.id.iv1);  
        tv = (TextView)v. findViewById(R.id.tv1);  
  
    }  
  
    /** 
     * 设置图片资源 
     */  
    public void setImageResource(int resId) {  
        iv.setImageResource(resId);  
    }  
  
    /** 
     * 设置显示的文字 
     */  
    public void setTextViewText(String text) {  
        tv.setText(text);  
    }  
  
}  
