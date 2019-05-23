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
<<<<<<< HEAD
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
=======
                yhm = username.getText().toString();
                mm = userpassword.getText().toString();
                qrmm = compwd.getText().toString();

                if (yhm.length() < 1 || mm.length() < 1 || qrmm.length() < 1) {
                    Toast.makeText(zhuceActivity.this, "输入框不能为空", Toast.LENGTH_SHORT).show();
                } else {
                    if (mm.length() < 3 || mm.length() > 18) {
                        Toast.makeText(zhuceActivity.this, "密码长度不符合规定", Toast.LENGTH_LONG).show();
                    } else {
                        if (mm.equals(qrmm) == false) {
                            Toast.makeText(zhuceActivity.this, "两次密码不一致", Toast.LENGTH_LONG).show();
                        } else {


//                            yonghuxinxi yx = new yonghuxinxi();
//                            boolean bl = yx.yonghuxinxi(yhm, mm, xb);

                            int code = okhttp_post("http://192.168.42.193:3000/signup");

                            Log.d("code", "调用方法时状态码：" + zt);


                            if (code != 200) {
                                Toast.makeText(zhuceActivity.this, "用户名重复", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(zhuceActivity.this, "注册成功，即将跳转至登陆界面", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(zhuceActivity.this, dengluActivity.class);
                                startActivity(intent);
                                finish();

                            }
>>>>>>> parent of 8ef9520... 20:29

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
<<<<<<< HEAD
            xb="女";

=======
            xb = "女";

    }


    public int okhttp_post(final String url) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient okHttpClient = new OkHttpClient();

                RequestBody requestBody = new FormBody.Builder().add("username", yhm)
                        .add("password", mm).build();


                Request request = new Request.Builder()
                        .url(url)
                        .post(requestBody)
                        .build();
                try {
                    Response response = okHttpClient.newCall(request).execute();

                    data = response.body().string();
                    JSONObject jsonObject = new JSONObject(data);
                    zt = jsonObject.getInt("code");
//                    Log.d("状态码","conde:==="+String.valueOf(zt));

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        Log.d("code", "请求返回状态码：" + zt);
        return zt;
>>>>>>> parent of 8ef9520... 20:29
    }

}
