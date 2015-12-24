package com.kylin.camera.demo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        findViewById(R.id.bt_camera_surface).setOnClickListener(this);
        findViewById(R.id.bt_camera_tureview).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_camera_surface :
                CameraSurfaceActivity.actionStart(MainActivity.this);
                break;
            case R.id.bt_camera_tureview :
                CameraTureActivity.actionStart(MainActivity.this);
                break;
            case R.id.bt_camera2_surface :
                Camera2SurfaceActivity.actionStart(MainActivity.this);
                break;
            case R.id.bt_camera2_tureview :
                Camera2TureActivity.actionStart(MainActivity.this);
                break;
        }

    }
}
