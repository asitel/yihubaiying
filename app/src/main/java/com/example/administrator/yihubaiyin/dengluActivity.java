package com.example.administrator.yihubaiyin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class dengluActivity extends Activity {
 private EditText yhmsrk,mmsrk;
private String ss1,ss2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_denglu);
        TextView tx=findViewById(R.id.zhuce);
        Button dl=findViewById(R.id.denglubutton);
       yhmsrk=findViewById(R.id.yhm);
        mmsrk=findViewById(R.id.mm);

//        注册响应监听
        tx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(dengluActivity.this,zhuceActivity.class);
                startActivity(intent);


            }
        });

//        登陆按钮响应监听
        dl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ss1=yhmsrk.getText().toString();
                ss2=mmsrk.getText().toString();
                yonghuxinxi yx=new yonghuxinxi();

            if(yx.denglujiance(ss1,ss2)==true){
                   Toast.makeText(dengluActivity.this,"用户名或密码不正确",Toast.LENGTH_LONG).show();
                }
                else{
                    dengluxinxi dlxx=new dengluxinxi();
                            dlxx.getdenluxinxi(ss1,ss2);
                    Intent intent=new Intent(dengluActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }

            }
        });


    }
}
