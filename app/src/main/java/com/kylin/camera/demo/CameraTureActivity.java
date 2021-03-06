package com.kylin.camera.demo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;

import com.kylin.camera.CameraController;
import com.kylin.camera.CameraStatusCallback;

public class CameraTureActivity extends Activity implements View.OnClickListener {

    private TextureView mTextureView;
    private Button btTakePicture;
    private Button btSwitchCamera;
    /**
     * 拍照
     */
    private Camera.PictureCallback mTakePicture = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {

        }
    };


    /**
     * 相机状态错误 callback
     */
    private CameraStatusCallback mCameraStatusCallback = new CameraStatusCallback() {
        @Override
        public void error(int status) {

        }
    };

    /**
     * 获取相机数据
     */
    private Camera.PreviewCallback mPreviewCallback = new Camera.PreviewCallback() {
        @Override
        public void onPreviewFrame(byte[] data, Camera camera) {

        }
    };

    /**
     */
    public static void actionStart(Context context ) {
        Intent intent = new Intent(context, CameraTureActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_true);
        initView();
        CameraController.getInstance().useAPI(CameraController.CAMERA_API); //
        CameraController.getInstance().setTextureView(mTextureView);

        CameraController.getInstance().setCameraStatusCallback(mCameraStatusCallback);
        CameraController.getInstance().setPreviewCallback(mPreviewCallback);

    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    private void initData() {
        CameraController.getInstance().openCamera();
        CameraController.getInstance().startCameraPreview();
    }

    private void initView() {
        mTextureView = (TextureView)findViewById(R.id.camera_textureview);
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
        CameraController.getInstance().stopCameraPreview();
        CameraController.getInstance().releaseCamera();

    }
}
