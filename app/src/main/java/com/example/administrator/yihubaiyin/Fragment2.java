package com.example.administrator.yihubaiyin;

import android.Manifest;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.telephony.SmsManager;
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
import com.baidu.location.LocationClientOption;

import java.util.List;

import static com.example.administrator.yihubaiyin.R.id.message;


public class Fragment2 extends Fragment  {
    View view;
    private MyDatabaseHelper dbHelper;
    String SOSData="";


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.activity_fragment2, container, false);
        final EditText editText = (EditText)view.findViewById(R.id.et_content1);
        editText.setInputType(InputType.TYPE_CLASS_NUMBER);
        Button addData = (Button) view.findViewById(R.id.ibn_add1);
        Button jianData = (Button)view.findViewById(R.id.ibn_jian);
        Button qiuzhu = (Button)view.findViewById(R.id.faduanxin);
        final TextView textView = (TextView)view.findViewById(R.id.showSOS);
        final int count = 0;
        if(ContextCompat.checkSelfPermission(getActivity(),Manifest.permission.SEND_SMS)!=
                PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(getActivity(),new String[]{
                    Manifest.permission.SEND_SMS},1);
        }

        dbHelper = new MyDatabaseHelper(getContext(), "SosId.db", null, 1);
        addData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                String inputText = editText.getText().toString();
                if (editText.getText().toString().trim().equals("")){
                    Toast.makeText(getActivity(),"输入不能为空",Toast.LENGTH_SHORT).show();
                }else{
                    if (db.rawQuery("select * from SOSID", null).getCount() != 0){
                        Toast.makeText(getActivity(),"至多可添加一位紧急联系人，添加失败",Toast.LENGTH_SHORT).show();
                    }else{
                            values.put("tel", inputText);
                            Toast.makeText(getActivity(),"紧急联系人添加成功",Toast.LENGTH_SHORT).show();
                            db.insert("SOSID", null, values);
                            Cursor cursor = db.rawQuery("select * from SOSID",null);
                            if (cursor.moveToFirst()) {
                                do {
                                    String telnumber = cursor.getString(cursor.getColumnIndex("tel"));
                                    SOSData += telnumber ;
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
                    db.delete("SOSID",null,null);
                }else{
                    Toast.makeText(getActivity(),"紧急联系人为空，删除失败",Toast.LENGTH_SHORT).show();
                }
            }
        });

        qiuzhu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        SQLiteDatabase db = dbHelper.getWritableDatabase();
                        Cursor cursor = db.rawQuery("select * from SOSID",null);
                        while (cursor.moveToNext()){
                            String telnumber = cursor.getString(cursor.getColumnIndex("tel"));
                            sendMessage(telnumber,"cxll");
                        }
                    }
                });
//              sendMessage("救我");

//                Toast.makeText(getActivity(),SOSData,Toast.LENGTH_SHORT).show();

//            onReceiveLocation();
            }
        });
        return view;
    }
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode){
            case 1:
                if(grantResults.length>0&&grantResults[0]!=PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(getActivity(),"权限",Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void sendMessage(String telnumber1,String message){
        android.telephony.SmsManager smsManager = android.telephony.SmsManager.getDefault();
        List<String> divideContents = smsManager.divideMessage(message);
        for (String text : divideContents) {
            smsManager.sendTextMessage(telnumber1, null, text, null, null);
    }
    }

//    public void onReceiveLocation(final BDLocation location){
//        LocationClientOption option = new LocationClientOption();
//        option.setIsNeedAddress(true);
//        getActivity().runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                Toast.makeText(getActivity(),location.getCountry() + location.getProvince() + location.getCity() + location.getDistrict() + location.getStreet(),Toast.LENGTH_SHORT).show();
//            }
//        });
//
//    }
}




