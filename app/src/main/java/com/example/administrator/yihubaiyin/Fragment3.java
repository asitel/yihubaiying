package com.example.administrator.yihubaiyin;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.Toast;

import java.util.List;

public class Fragment3 extends Fragment {
    private SensorManager sm;
    private MySensorListener sensorEventListener;

    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        final View view= inflater.inflate(R.layout.activity_fragment3,container,false);

        RelativeLayout relativeLayout1=view.findViewById(R.id.bankuai1);
        RelativeLayout relativeLayout2=view.findViewById(R.id.bankuai2);
        RelativeLayout relativeLayout3=view.findViewById(R.id.bankuai3);
        RelativeLayout relativeLayout4=view.findViewById(R.id.bankuai4);
        Button qiehuan=view.findViewById(R.id.qiuhuandhanghao);
        Switch switch1=view.findViewById(R.id.switch1);


        qiehuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),dengluActivity.class));
getActivity().finish();
            }
        });


        relativeLayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),gerenxinxi.class);
                startActivity(intent);
            }
        });

        relativeLayout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startActivity(new Intent(getActivity(),guanyuwomen.class));
            }
        });


        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    if(ContextCompat.checkSelfPermission(getContext(),Manifest.permission.SEND_SMS)!=
                            PackageManager.PERMISSION_GRANTED){
                        ActivityCompat.requestPermissions(getActivity(),new String[]{
                                Manifest.permission.SEND_SMS},1);
                    }


                    sensorEventListener=new MySensorListener(getContext());

                    sm = (SensorManager) getContext().getSystemService(Context.SENSOR_SERVICE);
                    sm.registerListener(sensorEventListener, sm.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION), SensorManager.SENSOR_DELAY_NORMAL);
//                Intent serviceintent=new Intent(getContext(),Myservice.class);
//                    getContext().startService(serviceintent);
                }
                if(!isChecked){
                    if (sm!=null) {
                        sm.unregisterListener(sensorEventListener);
                    }
                    Toast.makeText(getContext(),"关闭成功",Toast.LENGTH_SHORT).show();
//                    Intent intent=new Intent(getContext(),Myservice.class);
//                    getContext().stopService(intent);

                }
            }
        });

        return  view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (sm!=null) {
            sm.unregisterListener(sensorEventListener);
        }
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode){
            case 1:
                if(grantResults.length>0&&grantResults[0]!=PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(getContext(),"没有权限",Toast.LENGTH_SHORT).show();

                }

                break;
            default:
                break;
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


}
