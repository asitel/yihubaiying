package com.example.administrator.yihubaiyin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class gerenxinxi extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gerenxinxi);
        TextView textView1=findViewById(R.id.yonghuming);
        TextView textView2=findViewById(R.id.mima);
        TextView textView3=findViewById(R.id.xinbie);
       String s1= new dengluxinxi().getdlname();
       String s2=new dengluxinxi().getdlpassword();
       textView1.setText(s1);
       textView2.setText(s2);
       if(s2==null){
           textView1.setText("临时用户登陆，未设置密码");
       }
       else
           textView2.setText(s2);

        if(s1==null){
            textView1.setText("临时用户登陆，未设置用户名");
        }
        else
            textView1.setText(s1);
       textView3.setText("临时用户登录，未设置性别");
    }
}
