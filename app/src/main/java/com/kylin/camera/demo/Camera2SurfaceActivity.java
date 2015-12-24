package com.kylin.camera.demo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;

import com.kylin.camera.CameraController;
import com.kylin.camera.CameraStatusCallback;
import com.kylin.camera.bean.CameraEntity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Camera2SurfaceActivity extends Activity implements View.OnClickListener {

    private SurfaceView surfaceview;
    private Button btTakePicture;
    private Button btSwitchCamera;

    /**
     * 相机状态错误 callback
     */
    private CameraStatusCallback mCameraStatusCallback = new CameraStatusCallback() {
        @Override
        public void cameraError(int status) {

        }
    };



    /**
     */
    public static void actionStart(Context context ) {
        Intent intent = new Intent(context, Camera2SurfaceActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera2);
        initView();
        CameraController.getInstance(getApplicationContext()).useAPI(CameraController.CAMERA_API2); //
        CameraController.getInstance(getApplicationContext()).setSurfaceHolder(surfaceview.getHolder());
        CameraController.getInstance(getApplicationContext()).setCameraStatusCallback(mCameraStatusCallback);
//        CameraController.getInstance().setPreviewCallback(mPreviewCallback);
        CameraEntity mCameraEntity =  new CameraEntity();
        CameraController.getInstance(getApplicationContext()).setCameraEntity(mCameraEntity);

    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    private void initData() {
        CameraController.getInstance(getApplicationContext()).openCamera();
        CameraController.getInstance(getApplicationContext()).startCameraPreview();
    }

    private void initView() {
        surfaceview = (SurfaceView)findViewById(R.id.camera_surfaceview);
        btTakePicture = (Button)findViewById(R.id.bt_takepicture);
        btSwitchCamera = (Button)findViewById(R.id.bt_switch_camera);

        btTakePicture.setOnClickListener(this);
        btSwitchCamera.setOnClickListener(this);
        surfaceview.getHolder().addCallback(mHolderCallBack);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_takepicture :
//                CameraController.getInstance().takePicture(mTakePicture);
                break;
            case R.id.bt_switch_camera :
                CameraController.getInstance(getApplicationContext()).switchCamera();
                break;
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        super.onStop();
        CameraController.getInstance(getApplicationContext()).releaseCamera();

    }


    public void saveMyBitmap(String bitName,Bitmap mBitmap){
        File f = new File("/sdcard/Voip/" + bitName + ".png");
        try {
            f.createNewFile();
        } catch (IOException e) {
        }
        FileOutputStream fOut = null;
        try {
            fOut = new FileOutputStream(f);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        mBitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
        try {
            fOut.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private SurfaceHolder.Callback mHolderCallBack = new SurfaceHolder.Callback() {
        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            if (CameraController.getInstance(getApplicationContext()).isOpen()){
                CameraController.getInstance(getApplicationContext()).startCameraPreview();
            }else {
                CameraController.getInstance(getApplicationContext()).openCamera();
                CameraController.getInstance(getApplicationContext()).startCameraPreview();
            }
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {

        }
    };
}
