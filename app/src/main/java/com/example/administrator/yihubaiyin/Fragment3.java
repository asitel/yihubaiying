package com.example.administrator.yihubaiyin;

<<<<<<< HEAD
<<<<<<< HEAD
=======
=======
>>>>>>> parent of 8ef9520... 20:29
import android.content.Context;
>>>>>>> parent of 8ef9520... 20:29
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
<<<<<<< HEAD
=======
import android.widget.Switch;
<<<<<<< HEAD
>>>>>>> parent of 8ef9520... 20:29
=======
>>>>>>> parent of 8ef9520... 20:29

public class Fragment3 extends Fragment {

    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        final View view= inflater.inflate(R.layout.activity_fragment3,container,false);

        RelativeLayout relativeLayout1=view.findViewById(R.id.bankuai1);
        RelativeLayout relativeLayout2=view.findViewById(R.id.bankuai2);
        RelativeLayout relativeLayout3=view.findViewById(R.id.bankuai3);
        RelativeLayout relativeLayout4=view.findViewById(R.id.bankuai4);
        Button qiehuan=view.findViewById(R.id.qiuhuandhanghao);
        qiehuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),dengluActivity.class));

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
<<<<<<< HEAD
=======


        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                Intent serviceintent=new Intent(getContext(),Myservice.class);
                    getContext().startService(serviceintent);
                }
                if(!isChecked){
                    Intent intent=new Intent(getContext(),Myservice.class);
                    getContext().stopService(intent);
                }
            }
        });

>>>>>>> parent of 8ef9520... 20:29
        return  view;
    }





}
