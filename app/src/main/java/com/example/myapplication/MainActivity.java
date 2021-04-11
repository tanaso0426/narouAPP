package com.example.myapplication;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final int PERMISSION_WRITE_EX_STR = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        narouFragment myFragment = new narouFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();

        // FragmentTransactionのインスタンスを取得
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainer,myFragment);

        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();


        if (Build.VERSION.SDK_INT >= 23) {
            if(ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.WAKE_LOCK)
                    != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.INTERNET)
                    != PackageManager.PERMISSION_GRANTED)
            {
                ActivityCompat.requestPermissions(this,
                        new String[]{
                                Manifest.permission.WAKE_LOCK,
                                Manifest.permission.INTERNET
                        },
                        PERMISSION_WRITE_EX_STR);
            }
        }

    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode, String[] permission, int[] grantResults
    ){
        if (grantResults.length <= 0) { return; }
        switch(requestCode){
            case PERMISSION_WRITE_EX_STR: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    /// 許可が取れた場合・・・
                    /// 必要な処理を書いておく
                } else {
                    /// 許可が取れなかった場合・・・
                    Toast.makeText(this,
                            "アプリを起動できません....", Toast.LENGTH_LONG).show();
                    finish();
                }
            }
            return;
        }
    }





}