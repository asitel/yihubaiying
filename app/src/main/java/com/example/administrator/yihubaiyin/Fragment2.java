package com.example.administrator.yihubaiyin;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.text.InputType;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.example.administrator.yihubaiyin.R.id.message;


public class Fragment2 extends Fragment {
    View view;
    private MyDatabaseHelper dbHelper;
    String SOSData = "";
    public LocationClient mLocationClient;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.activity_fragment2, container, false);
        final EditText editText = (EditText) view.findViewById(R.id.et_content1);
        editText.setInputType(InputType.TYPE_CLASS_NUMBER);
        Button addData = (Button) view.findViewById(R.id.ibn_add1);
        Button jianData = (Button) view.findViewById(R.id.ibn_jian);
        Button qiuzhu = (Button) view.findViewById(R.id.faduanxin);
//


        final TextView textView = (TextView) view.findViewById(R.id.showSOS);
        final int count = 0;

        mLocationClient = new LocationClient(getContext());
        mLocationClient.registerLocationListener(new MyLocationListener2());

        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.SEND_SMS) !=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{
                    Manifest.permission.SEND_SMS}, 1);


        }


        dbHelper = new MyDatabaseHelper(getContext(), "SosId.db", null, 1);
        addData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                String inputText = editText.getText().toString();
                if (editText.getText().toString().trim().equals("")) {
                    Toast.makeText(getActivity(), "输入不能为空", Toast.LENGTH_SHORT).show();
                } else {
                    if (db.rawQuery("select * from SOSID", null).getCount() != 0) {
                        Toast.makeText(getActivity(), "至多可添加一位紧急联系人，添加失败", Toast.LENGTH_SHORT).show();
                    } else {
                        values.put("tel", inputText);
                        Toast.makeText(getActivity(), "紧急联系人添加成功", Toast.LENGTH_SHORT).show();
                        db.insert("SOSID", null, values);
                        Cursor cursor = db.rawQuery("select * from SOSID", null);
                        if (cursor.moveToFirst()) {
                            do {
                                String telnumber = cursor.getString(cursor.getColumnIndex("tel"));
                                SOSData += telnumber;
                            } while (cursor.moveToNext());
                        }
                        textView.setText(SOSData);
                    }
                }
            }
        });

        jianData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                if (db.rawQuery("select * from SOSID", null).getCount() != 0) {
                    Toast.makeText(getActivity(), "删除紧急联系人成功", Toast.LENGTH_SHORT).show();
                    db.delete("SOSID", null, null);
                } else {
                    Toast.makeText(getActivity(), "紧急联系人为空，删除失败", Toast.LENGTH_SHORT).show();
                }
            }
        });

        qiuzhu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {


                        Toast.makeText(getContext(), "正在求助", Toast.LENGTH_SHORT).show();

                        SQLiteDatabase db = dbHelper.getWritableDatabase();
                        MyLocationListener2 myLocationListener2 = new MyLocationListener2();
                        Cursor cursor = db.rawQuery("select * from SOSID", null);

                        double smsjd = myLocationListener2.getJd();
                        double smswd = myLocationListener2.getWd();

                        while (cursor.moveToNext()) {
                            String telnumber = cursor.getString(cursor.getColumnIndex("tel"));
                            sendMessage(telnumber, "你的好友向你求助" +
                                    "\n" + "纬度：" + smswd +
                                    "\n" + "经度" + smsjd +
                                    "\n" + "国家：" + MyLocationListener2.gj +
                                    "\n" + "省" + MyLocationListener2.sheng +
                                    "\n" + "市" + MyLocationListener2.shi +
                                    "\n" + "区" + MyLocationListener2.qu +
                                    "\n" + "街道" + MyLocationListener2.jiedao

                            );
                        }
                    }
                });


            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
//        MyLocationListener2 myLocationListener2 = new MyLocationListener2();
//        myLocationListener2.LCT(mLocationClient);
        initLocation();
        mLocationClient.start();
    }

    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
//        option.setScanSpan(5000);
        option.setOpenGps(true);
        option.setIsNeedAddress(true);
        option.setLocationNotify(true);
        mLocationClient.setLocOption(option);

    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getActivity(), "权限", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void sendMessage(String telnumber1, String message) {
        android.telephony.SmsManager smsManager = android.telephony.SmsManager.getDefault();
        List<String> divideContents = smsManager.divideMessage(message);
        for (String text : divideContents) {
            smsManager.sendTextMessage(telnumber1, null, text, null, null);
        }
    }



    @Override
    public void onDestroy() {
        super.onDestroy();

        mLocationClient.stop();
    }


}

class MyLocationListener2 implements BDLocationListener {
    public static double jd, wd;
    public static String gj, sheng, shi, jiedao, qu, dwfs;
    RequestBody requestBody;
    LocationClient locationClient;

    @Override
    public void onReceiveLocation(BDLocation bdLocation) {
        Log.d("==", "开始定位");

        wd = bdLocation.getLatitude();
        jd = bdLocation.getLongitude();
        gj = bdLocation.getCountry();
        sheng = bdLocation.getProvince();
        shi = bdLocation.getCity();
        qu = bdLocation.getDistrict();
        jiedao = bdLocation.getStreet();
        Log.d("==", String.valueOf("纬度" + wd + "\n" + "经度" + jd + gj + sheng + shi + qu + jiedao));

        requestBody = new FormBody.Builder().add("longgitude", String.valueOf(jd)).add("latitude", String.valueOf(wd)).add("username", MainActivity.username).build();
        new Thread(new Runnable() {
            @Override
            public void run() {
                okhttp_post(requestBody, "http://192.168.199.187:3000/jwd");
            }
        }).start();

//locationClient.start();

    }

//    public void LCT(LocationClient locationClient) {
//        this.locationClient = locationClient;
//    }

    public double getJd() {
        return jd;
    }

    public double getWd() {
        return wd;
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


                    JSONObject jsonObject = new JSONObject(data);

                    int zt = jsonObject.getInt("code");
                    Log.d("状态码", "code检验:===" + String.valueOf(zt));

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });

//        return  zt;
    }
}




