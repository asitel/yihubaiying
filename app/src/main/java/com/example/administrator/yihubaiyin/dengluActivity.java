package com.example.administrator.yihubaiyin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class dengluActivity extends Activity {
 private EditText yhmsrk,mmsrk;
private String ss1,ss2;

int zt=200,code;
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
                if(ss1.equals("")&&ss2.equals("")){
                    Toast.makeText(getApplicationContext(),"用户名或密码错误",Toast.LENGTH_SHORT).show();
                }else {
                    RequestBody requestBody = new FormBody.Builder().add("username", ss1).add("password", ss2).build();

                    okhttp_post(requestBody, "http://192.168.199.187:3000/signin");
                }

            }
        });


    }


    public void okhttp_post(RequestBody formBody, String url) {



        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().post(formBody).url(url).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
//                final List<Map<String, Object>> list = new ArrayList<>();
               String data = response.body().string();
                try {


                    JSONObject jsonObject=new JSONObject(data);

                    zt=jsonObject.getInt("code");
                    Log.d("状态码","code检验:==="+String.valueOf(zt));

                    Log.d("=====","调用"+zt);
                    if(zt==200){
                        Bundle bundle=new Bundle();
                        Intent intent=new Intent(dengluActivity.this,MainActivity.class);
                        bundle.putString("username",ss1);
                        intent.putExtras(bundle);
                        startActivity(intent);
                        finish();
                        zt=0;
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"用户名或密码不正确",Toast.LENGTH_LONG).show();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });


    }
}
