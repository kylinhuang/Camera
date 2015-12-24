package com.kylin.camera;

import android.content.Context;
import android.hardware.Camera;
import android.view.SurfaceHolder;
import android.view.TextureView;

import com.kylin.camera.bean.CameraEntity;

/**
 */
public class CameraBaseHelper implements ICamera{

    CameraStatusCallback mCameraStatusCallback = null ;
    Camera.PreviewCallback mPreviewCallback = null;
    public CameraEntity mCameraEntity = new CameraEntity();
    public Camera camera;
    public TextureView mTextureView;
    public Context mContext;

    /**
     * 开启相机
     */
    public void openCamera(){}

    /**
     * 关闭相机
     */
    public void stopCameraPreview(){}

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

    @Override
    public Boolean isOpen() {
        if (camera == null){
            return false ;
        }else {
            return true ;
        }
    }

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

    @Override
    public void setTextureView(TextureView mTextureView) {
        this.mTextureView = mTextureView ;

    }

    public void setCameraEntity(CameraEntity mCameraEntity) {
        this.mCameraEntity = mCameraEntity;
    }
}
