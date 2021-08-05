package com.example.dexstru;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends Activity {

    public String tag = "view-";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView textview=new TextView(this);
        textview.setText("你好！");


        int a = 10-5;
        long b = 604800000;
        double  c = 1.5707963267948966d;
        double d = a+b+c;
        Log.d("testlog",""+d );
        //GetApkInfo(this,)
        //reTest();
        //initEvent1();

        //View view  = LayoutInflater.from(this.getApplication()).inflate(R.layout.dialog_style,null);
        //Log.d("tag",view.toString());

        new Handler().postDelayed(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void run() {
                //daiLog();
                windowM(textview);
            }
        },500);

    }
    private void initEvent1() {

        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.SYSTEM_ALERT_WINDOW) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.SYSTEM_ALERT_WINDOW}, 1);
        } else {
        }

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[]
            grantResults) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && requestCode == 200) {
            for (int i = 0; i < permissions.length; i++) {
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) { // 用户点的拒绝，仍未拥有权限
                    Toast.makeText(this, "请在设置中打开摄像头或存储权限", Toast.LENGTH_SHORT).show();
                    // 可以选择添加如下代码在系统设置中打开该应用的设置页面
                    Intent intent = new Intent();
                    intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri = Uri.fromParts("package", getPackageName(), null);
                    intent.setData(uri);
                    startActivity(intent);
                    return;
                }
            }
        }
    }


    public static Drawable GetApkInfo(Context context, String apkPath) {
        String TAG = "apkinfo1";
        PackageManager pm = context.getPackageManager();
        PackageInfo info = pm.getPackageArchiveInfo(apkPath, PackageManager.GET_ACTIVITIES);
        if (info != null) {
            ApplicationInfo appInfo = info.applicationInfo;
            String packageName = appInfo.packageName;  //得到安装包名称
            appInfo.sourceDir = apkPath;
            appInfo.publicSourceDir = apkPath;
            String appName = pm.getApplicationLabel(appInfo).toString();

            Log.d(TAG, "getApkIcon: " + packageName + "-------"+appName );
            try {
                return appInfo.loadIcon(pm);
            } catch (OutOfMemoryError e) {
                Log.d(TAG, "GetApkInfo: " + e);
            }
        }
        return null;
    }

    private void hideStatusBar() {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.flags |= 1024;
        getWindow().setAttributes(lp);
        getWindow().addFlags(512);
    }


    public void reTest(){
        String str = "test_job_101_asdasfdsa";
        String pat = "(.*)_[^_]*?";
        Pattern r = Pattern.compile(pat);
        Matcher m = r.matcher(str);
        boolean b = m.find();
        Log.d("relog",m.group(1));
    }




    public void daiLog(){

        new Handler().postDelayed(new Runnable(){
            public void run() {
                //显示dialog
                Intent intent=new Intent(MainActivity.this,MyService.class);
                startService(intent);

            }
        }, 5000);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void windowM(View view){
        Context mContext = this.getApplicationContext();
        WindowManager mWindowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        final WindowManager.LayoutParams mWinParams = new WindowManager.LayoutParams();
        mWinParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        //设置图片格式，效果为背景透明
        mWinParams.format = PixelFormat.RGBA_8888;
        //设置浮动窗口不可聚焦（实现操作除浮动窗口外的其他可见窗口的操作）
        mWinParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL ;
        //调整悬浮窗显示居中
        mWinParams.gravity = Gravity.NO_GRAVITY;
        //设置悬浮窗口长宽
        mWinParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        mWinParams.height = WindowManager.LayoutParams.MATCH_PARENT;
        mWinParams.gravity = Gravity.CENTER;
        View view1 = view.getRootView();
        boolean boolean1 = view1.getRootView().isAttachedToWindow();
        Log.d(tag,view1.toString());
        Log.d(tag,boolean1+"");
        mWindowManager.addView(view,mWinParams);
    }
}