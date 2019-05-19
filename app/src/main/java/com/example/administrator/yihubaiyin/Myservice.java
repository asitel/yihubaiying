package com.example.administrator.yihubaiyin;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

public class Myservice extends Service {
private  mybinder binder=new mybinder();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("Mysevice","服务启动成功");
test t=new test();
t.to();
Log.d("Myservice","方法调用成功");




    }

}
