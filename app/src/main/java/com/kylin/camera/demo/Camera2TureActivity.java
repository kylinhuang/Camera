package com.kylin.camera.demo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;

import com.kylin.camera.CameraController;
import com.kylin.camera.CameraStatusCallback;

public class Camera2TureActivity extends Activity implements View.OnClickListener {

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
        public void cameraError(int status) {

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
    private TextureView.SurfaceTextureListener mSurfaceTexture = new TextureView.SurfaceTextureListener() {
        @Override
        public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {


            if ( CameraController.getInstance(getApplicationContext()).isOpen() ) {
                CameraController.getInstance(getApplicationContext()).startCameraPreview();
            }else {
                CameraController.getInstance(getApplicationContext()).openCamera();
                CameraController.getInstance(getApplicationContext()).startCameraPreview();
            }


        }

        @Override
        public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

        }

        @Override
        public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
            return false;
        }

        @Override
        public void onSurfaceTextureUpdated(SurfaceTexture surface) {

        }
    };

    /**
     */
    public static void actionStart(Context context ) {
        Intent intent = new Intent(context, Camera2TureActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera2_true);
        initView();
        CameraController.getInstance(Camera2TureActivity.this).useAPI(CameraController.CAMERA_API2); //
        CameraController.getInstance(Camera2TureActivity.this).setTextureView(mTextureView);

        CameraController.getInstance(Camera2TureActivity.this).setCameraStatusCallback(mCameraStatusCallback);
        CameraController.getInstance(Camera2TureActivity.this).setPreviewCallback(mPreviewCallback);

    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    private void initData() {
        CameraController.getInstance(Camera2TureActivity.this).openCamera();
        CameraController.getInstance(Camera2TureActivity.this).startCameraPreview();
    }

    private void initView() {
        mTextureView = (TextureView)findViewById(R.id.camera_textureview);
        btTakePicture = (Button)findViewById(R.id.bt_takepicture);
        btSwitchCamera = (Button)findViewById(R.id.bt_switch_camera);

        btTakePicture.setOnClickListener(this);
        btSwitchCamera.setOnClickListener(this);
        mTextureView.setSurfaceTextureListener(mSurfaceTexture);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_takepicture :
                CameraController.getInstance(getApplicationContext()).takePicture(mTakePicture);
                break;
            case R.id.bt_switch_camera :
                CameraController.getInstance(getApplicationContext()).switchCamera();
                CameraController.getInstance(getApplicationContext()).startCameraPreview();
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
        CameraController.getInstance(getApplicationContext()).stopCameraPreview();
        CameraController.getInstance(getApplicationContext()).releaseCamera();

    }
}
