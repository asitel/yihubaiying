package com.example.administrator.yihubaiyin;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.LinkedList;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Color;
import android.text.InputType;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.map.BitmapDescriptor;

public class Fragment2 extends Fragment {
    View view;
    private MyDatabaseHelper dbHelper;
    String SOSData="";


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.activity_fragment2, container, false);
        final EditText editText = (EditText)view.findViewById(R.id.et_content1);
        editText.setInputType( InputType.TYPE_CLASS_NUMBER);
        ImageButton addData = (ImageButton) view.findViewById(R.id.ibn_add1);
        Button call = (Button)view.findViewById(R.id.button);
        final TextView textView = (TextView)view.findViewById(R.id.showSOS);


       dbHelper = new MyDatabaseHelper(getContext(), "SosId.db", null, 1);
        addData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                String inputText = editText.getText().toString();

                if (editText.getText().toString().trim().equals("")){
                    Toast.makeText(getActivity(),"输入不能为空",Toast.LENGTH_SHORT).show();
                } else{
                    if (db.rawQuery("select * from SOSID",null).getCount() != 0){
                        Toast.makeText(getActivity(),"至多可添加一位紧急联系人,添加失败",Toast.LENGTH_SHORT).show();
                    }else {

                        values.put("tel", inputText);
                        db.insert("SOSID", null, values);
                        Cursor cursor = db.rawQuery("select * from SOSID", null);

                        SmsManager smsManager = SmsManager.getDefault();
                        String callNumber = cursor.toString();
                        Toast.makeText(getActivity(),callNumber,Toast.LENGTH_SHORT).show();
//                        smsManager.sendTextMessage(callNumber,null,"我在。。。快来助我",null,null);
                        Toast.makeText(getActivity(),"求助成功",Toast.LENGTH_SHORT).show();
                        if (cursor.moveToFirst()) {
                            do {
                                String telnumber = cursor.getString(cursor.getColumnIndex("tel"));
                                SOSData += telnumber + "\n";

                            } while (cursor.moveToNext());

                        }
                        textView.setText(SOSData);
                    }
                }
                editText.setText("");

                }

        });
//        call.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                SQLiteDatabase db = dbHelper.getWritableDatabase();
//                Cursor cursor = db.rawQuery("select * from SOSID", null);
//                String callNumber = cursor.toString();
//                smsManager.sendTextMessage(callNumber,null,"我在。。。快来助我",null,null);
//                Toast.makeText(getActivity(),"求助成功",Toast.LENGTH_SHORT).show();
//            }
//        });
        return view;
    }

}




