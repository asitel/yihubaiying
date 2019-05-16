package com.example.administrator.yihubaiyin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class zhuceActivity extends Activity {
    EditText username,userpassword,compwd;
    RadioGroup radioGroup;
    RadioButton radioButton,rb1,rb2;
    String xb,yhm,mm,qrmm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhuce);
        rb1=findViewById(R.id.rb1);
        rb2=findViewById(R.id.rb2);
        Button bt1=findViewById(R.id.zhucebutton);
        Button bt2=findViewById(R.id.fanhubutton);
        username=findViewById(R.id.username);
        userpassword=findViewById(R.id.numberPassword);
        compwd=findViewById(R.id.compwd);
        radioGroup=findViewById(R.id.rg1);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        selectRadioButton();
    }
});

    bt2.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent=new Intent(zhuceActivity.this,dengluActivity.class);
            startActivity(intent);
            finish();
        }
    });




        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                yhm=username.getText().toString();
                mm=userpassword.getText().toString();
                qrmm=compwd.getText().toString();

                if (yhm.length()<1||mm.length()<1||qrmm.length()<1){
                    Toast.makeText(zhuceActivity.this,"输入框不能为空",Toast.LENGTH_SHORT).show();
                }else{
                    if (mm.length()<6||mm.length()>18){
                        Toast.makeText(zhuceActivity.this,"密码长度不符合规定",Toast.LENGTH_LONG).show();
                    }
                    else{
                        if (mm.equals(qrmm)==false){
                            Toast.makeText(zhuceActivity.this,"两次密码不一致",Toast.LENGTH_LONG).show();
                        }
                        else{
 yonghuxinxi yx=new yonghuxinxi();
 boolean bl=yx.yonghuxinxi(yhm,mm,xb);
 if (bl==false){
     Toast.makeText(zhuceActivity.this,"用户名重复",Toast.LENGTH_LONG).show();
 }
 else{
     Toast.makeText(zhuceActivity.this,"注册成功，即将跳转至登陆界面",Toast.LENGTH_SHORT).show();
     Intent intent=new Intent(zhuceActivity.this,dengluActivity.class);
     startActivity(intent);
     finish();

 }

                        }
                    }
                }
            }
        });

    }
    public void selectRadioButton(){
        radioButton = (RadioButton)findViewById(radioGroup.getCheckedRadioButtonId());
        if (radioButton==rb1)
        xb="男";
        else
            xb="女";

    }

}
