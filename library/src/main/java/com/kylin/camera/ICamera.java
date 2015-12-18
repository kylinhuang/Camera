package com.kylin.camera;

/**
 */
public interface ICamera {

    /**
     * 开启相机
     */
    public void openCamera();

    /**
     * 关闭相机
     */
    public void closeCamera();

    /**
     * 切换相机
     */
    public void switchCamera();


    /**
     * 释放相机
     */
    public void releaseCamera();

    /**
     * 开始预览
     */
    public void startCameraPreview();
}
