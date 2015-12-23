package com.kylin.camera;

import android.hardware.Camera;
import android.view.SurfaceHolder;

/**
 */
public interface ICamera {

    /**
     * 开启相机
     */
    void openCamera();

    /**
     * 关闭相机
     */
    void closeCamera();

    /**
     * 切换相机
     */
    void switchCamera();


    /**
     * 释放相机
     */
    void releaseCamera();

    /**
     * 开始预览
     */
    void startCameraPreview();

    void takePicture(Camera.PictureCallback mPictureCallback);

    void setSurfaceHolder(SurfaceHolder mSurfaceHolder);


}
