package com.example.administrator.yihubaiyin;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.baidu.location.LocationClient;

import java.util.List;

public class MySensorListener implements SensorEventListener {
    Context mcontext;
    private MyDatabaseHelper dbHelper;

    public MySensorListener(Context context){
        mcontext=context;

    }
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {


        if(sensorEvent.sensor.getType() == Sensor.TYPE_LINEAR_ACCELERATION) {

            float X = sensorEvent.values[0];
            float Y = sensorEvent.values[1];
            float Z = sensorEvent.values[2];

            if (Z<-5){
                dbHelper = new MyDatabaseHelper(mcontext, "SosId.db", null, 1);
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                MyLocationListener2 myLocationListener2=new MyLocationListener2();
                Cursor cursor = db.rawQuery("select * from SOSID", null);
                while (cursor.moveToNext()) {
                    String telnumber = cursor.getString(cursor.getColumnIndex("tel"));
                    sendSMS(telnumber, "你的好友向你求助" +
                            "\n" + "纬度：" + myLocationListener2.getWd() +
                            "\n" + "经度" + myLocationListener2.getJd() +
                            "\n" + "国家：" + MyLocationListener2.gj +
                            "\n" + "省" + MyLocationListener2.sheng +
                            "\n" + "市" + MyLocationListener2.shi +
                            "\n" + "区" + MyLocationListener2.qu +
                            "\n" + "街道" + MyLocationListener2.jiedao

                    );                }
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
    public void sendSMS(String phoneNumber, String message){
        //获取短信管理器
        android.telephony.SmsManager smsManager = android.telephony.SmsManager.getDefault();
        //拆分短信内容（手机短信长度限制）
        List<String> divideContents = smsManager.divideMessage(message);
        for (String text : divideContents) {
            smsManager.sendTextMessage(phoneNumber, null, text, null, null);
        }
    }
}
