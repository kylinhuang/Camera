package com.kylin.camera.demo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;

import com.kylin.camera.CameraController;

public class CameraActivity extends Activity implements View.OnClickListener {

    private SurfaceView surfaceview;
    private Button btTakePicture;
    private Button btSwitchCamera;
    private Camera.PictureCallback mTakePicture = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {

        }
    };

    /**
     */
    public static void actionStart(Context context ) {
        Intent intent = new Intent(context, CameraActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        initView();

    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    private void initData() {
        CameraController.getInstance().useAPI(CameraController.CAMERA_API);
        CameraController.getInstance().setSurfaceHolder(surfaceview.getHolder());
        CameraController.getInstance().openCamera();
        CameraController.getInstance().startCameraPreview();
    }

    private void initView() {
        surfaceview = (SurfaceView)findViewById(R.id.camera_surfaceview);
        btTakePicture = (Button)findViewById(R.id.bt_takepicture);
        btSwitchCamera = (Button)findViewById(R.id.bt_switch_camera);

        btTakePicture.setOnClickListener(this);
        btSwitchCamera.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_takepicture :

                CameraController.getInstance().takePicture(mTakePicture);
                break;
            case R.id.bt_switch_camera :
                CameraController.getInstance().switchCamera();
                CameraController.getInstance().startCameraPreview();
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
        CameraController.getInstance().closeCamera();
        CameraController.getInstance().releaseCamera();

    }
}
