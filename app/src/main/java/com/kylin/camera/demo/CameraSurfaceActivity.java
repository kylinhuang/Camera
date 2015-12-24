package com.kylin.camera.demo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.os.Bundle;
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

public class CameraSurfaceActivity extends Activity implements View.OnClickListener {

    private SurfaceView surfaceview;
    private Button btTakePicture;
    private Button btSwitchCamera;
    /**
     * 拍照
     */
    private Camera.PictureCallback mTakePicture = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(final byte[] data, final Camera camera) {
            if (null==data)return;

            new Thread(new Runnable() {
                @Override
                public void run() {
                    BitmapFactory.Options mOptions = new BitmapFactory.Options();
                    mOptions.inJustDecodeBounds = false;
                    mOptions.inSampleSize = 10;   //width，hight设为原来的十分一
                    Bitmap bitmap = BitmapFactory.decodeByteArray
                            (data, 0, data.length ,mOptions);

                    saveMyBitmap(System.currentTimeMillis()+"",bitmap);
                    bitmap.recycle();
                }
            }).start();

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
        public void onPreviewFrame(final byte[] data, final Camera camera) {

//            final byte [] updata = new byte [data.length] ;
//            System.arraycopy(data, 0, updata, 0, data.length) ;
//
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    int imageFormat=camera.getParameters().getPreviewFormat();
//                    Camera.Size size = camera.getParameters().getPreviewSize();
//                    int w = size.width;
//                    int h = size.height;
//                    Rect rect=new Rect(0,0,w,h);
//                    YuvImage yuvImg = new YuvImage(updata , imageFormat , w , h ,null);
//                    ByteArrayOutputStream outputstream = new ByteArrayOutputStream();
//                    yuvImg.compressToJpeg(rect, 100, outputstream);
//                    BitmapFactory.Options mOptions = new BitmapFactory.Options();
//                    mOptions.inJustDecodeBounds = false;
//                    mOptions.inSampleSize = 10;   //width，hight设为原来的十分一
//                    Bitmap bitmap = BitmapFactory.decodeByteArray(outputstream.toByteArray(), 0, outputstream.size(),mOptions);
//
//                    saveMyBitmap(System.currentTimeMillis()+"",bitmap);
//                    bitmap.recycle();
//                }
//            }).start();
        }
    };

    /**
     */
    public static void actionStart(Context context ) {
        Intent intent = new Intent(context, CameraSurfaceActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        initView();
        CameraController.getInstance().useAPI(CameraController.CAMERA_API); //
        CameraController.getInstance().setSurfaceHolder(surfaceview.getHolder());
        CameraController.getInstance().setCameraStatusCallback(mCameraStatusCallback);
        CameraController.getInstance().setPreviewCallback(mPreviewCallback);
        CameraEntity mCameraEntity =  new CameraEntity();
        CameraController.getInstance().setCameraEntity(mCameraEntity);

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
        CameraController.getInstance().releaseCamera();

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

}
