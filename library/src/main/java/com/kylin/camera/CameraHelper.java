package com.kylin.camera;

import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.view.SurfaceHolder;

/**
 * Created by zhouxiyuan on 15/12/18.
 */
public class CameraHelper  extends CameraBaseHelper {

    private static CameraHelper sInstance;
    //相机数量
    private  int mTotalCameraCount;
    private int mDefaultCamera = Camera.CameraInfo.CAMERA_FACING_FRONT ;
    private int mDefaultCameraId;
    private Camera.PictureCallback mPictureCallback;
    private Camera.PreviewCallback mPreviewCallback;
    private Camera camera;


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
//        mTotalCameraCount = Camera.getNumberOfCameras();

    }



    @Override
    public void openCamera() {

    }

    @Override
    public void closeCamera() {

    }

    @Override
    public void switchCamera() {

    }

    @Override
    public void releaseCamera() {

    }

    @Override
    public void startCameraPreview() {
        if (camera != null){
            camera.setPreviewCallback(mPreviewCallback);
            camera.startPreview();
        }
    }

    private void initCamera() {
        try {
            mTotalCameraCount = Camera.getNumberOfCameras();
            // Find the ID of the default camera
            Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
            for (int i = 0; i < mTotalCameraCount ; i++) {
                Camera.getCameraInfo(i, cameraInfo);
                if (cameraInfo.facing == mDefaultCamera ) {
                    mDefaultCameraId = i;
                    break;
                }
            }
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
    public void takePicture() {
        if (camera != null)
            camera.takePicture(null, null, mPictureCallback);
    }



    public void setSurface(SurfaceTexture mSurfaceTexture){
//        mSurfaceTexture.

    }

    public void setSurfaceHolder(SurfaceHolder mSurfaceHolder){
//        mSurfaceTexture.

    }



}
