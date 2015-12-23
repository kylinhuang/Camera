package com.kylin.camera;

import android.hardware.Camera;
import android.view.SurfaceHolder;

/**
 */
public class CameraBaseHelper  implements ICamera{

    CameraStatusCallback mCameraStatusCallback = null ;
    Camera.PreviewCallback mPreviewCallback = null;

    /**
     * 开启相机
     */
    public void openCamera(){}

    /**
     * 关闭相机
     */
    public void closeCamera(){}

    /**
     * 切换相机
     */
    public void switchCamera(){}


    /**
     * 释放相机
     */
    public void releaseCamera(){}

    /**
     * 开始预览
     */
    public void startCameraPreview(){}

    /**
     * 拍照
     */
    @Override
    public void takePicture(Camera.PictureCallback mPictureCallback) {}


    @Override
    public void setSurfaceHolder(SurfaceHolder mSurfaceHolder) {}


    /**
     * @param mCameraStatusCallback
     */
    public void setCameraStatusCallback(CameraStatusCallback mCameraStatusCallback){
       this.mCameraStatusCallback = mCameraStatusCallback;
    }

    /**
     * 设置预览 callback
     * @param mPreviewCallback
     */
    public void setPreviewCallback(Camera.PreviewCallback mPreviewCallback) {
        this.mPreviewCallback = mPreviewCallback ;
    }


}
