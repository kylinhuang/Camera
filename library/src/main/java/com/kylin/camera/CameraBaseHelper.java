package com.kylin.camera;

import android.hardware.Camera;
import android.view.SurfaceHolder;

/**
 */
public class CameraBaseHelper  implements ICamera{


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
    public void takePicture(Camera.PictureCallback mPictureCallback) {

    }


    @Override
    public void setSurfaceHolder(SurfaceHolder mSurfaceHolder) {

    }
}
