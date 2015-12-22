package com.kylin.camera;

import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.view.SurfaceHolder;

import java.io.IOException;

/**
 * Created by zhouxiyuan on 15/12/18.
 */
public class CameraHelper  extends CameraBaseHelper {

    private static CameraHelper sInstance;
    //相机数量
    private int mTotalCameraCount;
    private int mDefaultCamera = Camera.CameraInfo.CAMERA_FACING_FRONT ;
    private int mCurCameraId;
    private Camera.PictureCallback mPictureCallback;
    private Camera.PreviewCallback mPreviewCallback;
    private Camera camera;
    private SurfaceHolder mSurfaceHolder;



    public static CameraHelper getInstance() {
        synchronized (CameraHelper.class) {
            if (sInstance == null) {
                sInstance = new CameraHelper();
            }
        }
        return sInstance ;
    }

    private CameraHelper(){
        initCamera();
    }



    @Override
    public void openCamera() {
        // Find the ID of the default camera
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        for (int i = 0; i < mTotalCameraCount ; i++) {
            if (cameraInfo.facing == mDefaultCamera ) {
                mCurCameraId = i;
                Camera.getCameraInfo(i, cameraInfo);
                break;
            }
        }
        camera = Camera.open(mCurCameraId);
        if (null != mSurfaceHolder ){
            try {
                camera.setPreviewDisplay(mSurfaceHolder);
                camera.setDisplayOrientation(90);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void closeCamera() {

    }

    @Override
    public void switchCamera() {
        mCurCameraId =  ++mCurCameraId % mTotalCameraCount ;
        camera.release();
        openCamera();
    }

    @Override
    public void releaseCamera() {
        camera.setPreviewCallback(null);
        camera.release();
    }

    @Override
    public void startCameraPreview() {
        if (camera != null){
            camera.setPreviewCallback(mPreviewCallback);
            camera.startPreview();
//            AutoFocusManager autoFocusManager = new AutoFocusManager( camera);
        }
    }

    private void initCamera() {
        try {
            mTotalCameraCount = Camera.getNumberOfCameras();
        } catch (Exception e) {

        }

    }

    /**
     * 设置拍照 callback
     * @param mPictureCallback
     */
    public void setPictureCallback(Camera.PictureCallback mPictureCallback) {
        this.mPictureCallback = mPictureCallback ;
    }

    /**
     * 设置预览 callback
     * @param mPreviewCallback
     */
    public void setPreviewCallback(Camera.PreviewCallback mPreviewCallback) {
        this.mPreviewCallback = mPreviewCallback ;
    }


    /**
     * 拍照
     */
    public void takePicture(Camera.PictureCallback mPictureCallback) {
        if (camera != null)
            camera.takePicture(null, null, mPictureCallback);
    }



    public void setSurface(SurfaceTexture mSurfaceTexture){
//        mSurfaceTexture.

    }

    public void setSurfaceHolder(SurfaceHolder mSurfaceHolder){
        this.mSurfaceHolder = mSurfaceHolder ;
        mSurfaceHolder.addCallback(mCallback);
    }

    private SurfaceHolder.Callback mCallback = new SurfaceHolder.Callback() {
        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            if (null != mSurfaceHolder  && null != camera){
                try {
                    camera.setPreviewDisplay(mSurfaceHolder);
                    camera.setDisplayOrientation(90);
                } catch (IOException e) {
                    e.printStackTrace();
                }
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
