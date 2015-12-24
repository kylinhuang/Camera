package com.kylin.camera;

import android.hardware.Camera;
import android.view.SurfaceHolder;
import android.view.TextureView;

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
    void stopCameraPreview();

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

    Boolean isOpen();

    void takePicture(Camera.PictureCallback mPictureCallback);

    void setSurfaceHolder(SurfaceHolder mSurfaceHolder);
    void setTextureView(TextureView mTextureView);

}
