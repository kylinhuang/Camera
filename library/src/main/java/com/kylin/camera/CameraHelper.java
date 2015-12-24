package com.kylin.camera;

import android.content.Context;
import android.hardware.Camera;
import android.util.Log;
import android.view.SurfaceHolder;

import java.io.IOException;

/**
 * Created by zhouxiyuan on 15/12/18.
 */
public class CameraHelper  extends CameraBaseHelper {

    private static CameraHelper sInstance;
    // 相机数量
    private int mTotalCameraCount;
    private int mDefaultCamera = Camera.CameraInfo.CAMERA_FACING_FRONT ;
    private int mCurCameraId;

    private SurfaceHolder mSurfaceHolder;
    private AutoFocusManager autoFocusManager;


    public static CameraHelper getInstance(Context mContext) {
        synchronized (CameraHelper.class) {
            if (sInstance == null) {
                sInstance = new CameraHelper(mContext);
            }
        }
        return sInstance ;
    }

    private CameraHelper(Context mContext){
        this.mContext = mContext;
        try {
            mTotalCameraCount = Camera.getNumberOfCameras();
        } catch (Exception e) {

        }
    }



    @Override
    public void openCamera() {
        // Find the ID of the default camera
        Camera.CameraInfo mCameraInfo = new Camera.CameraInfo();
        for (int i = 0; i < mTotalCameraCount ; i++) {
            if (mCameraInfo.facing == mDefaultCamera ) {
                mCurCameraId = i;
                Camera.getCameraInfo(i, mCameraInfo);
                break;
            }
        }
        try {
            camera = Camera.open(mCurCameraId);
        } catch (Exception e) {
            Log.e("Voip", e.toString());
            if ( null != mCameraStatusCallback ) mCameraStatusCallback.cameraError(CameraStatusCallback.CAMERA_OPEN);
            return;
        }

        initCameraProperty();

    }

    private void initCameraProperty() {
        if (camera == null) {
            return;
        }
        Camera.Parameters parameters = camera.getParameters();
        if (mCameraEntity.isOpenFlashlight) {
            parameters.setFlashMode(Camera.Parameters.FLASH_MODE_AUTO);
        } else {
            parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
        }

        if ( mCameraEntity.mPreviewSize[0] != 0 && mCameraEntity.mPreviewSize[1] != 0 ){
            parameters.setPreviewSize(mCameraEntity.mPreviewSize[0], mCameraEntity.mPreviewSize[1]);
        }

        if ( mCameraEntity.mPreviewFpsRange[0] != 0 && mCameraEntity.mPreviewFpsRange[1] != 0 ){
            parameters.setPreviewFpsRange(mCameraEntity.mPreviewFpsRange[0], mCameraEntity.mPreviewFpsRange[1]);
        }

        parameters.setAutoWhiteBalanceLock(false);
         /* 设置相片格式为JPEG */
        parameters.setPictureFormat(mCameraEntity.mPictureFormat);

        try {
            camera.setParameters(parameters);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }



    @Override
    public void stopCameraPreview() {
        if (null != camera ){
            if (mCameraEntity.isAutoFocus) {
                autoFocusManager.stop();
            }

//            mSurfaceHolder.removeCallback(mCallback);
            camera.setPreviewCallback(null);
            camera.stopPreview();
        }
    }

    @Override
    public void switchCamera() {
        mCurCameraId =  ++mCurCameraId % mTotalCameraCount ;
        releaseCamera();
        openCamera();
        startCameraPreview();
    }

    @Override
    public void releaseCamera()  {
        if (null != camera ){
            if (mCameraEntity.isAutoFocus) {
                autoFocusManager.stop();
            }

//            mSurfaceHolder.removeCallback(mCallback);
            camera.setPreviewCallback(null);
            try {
                camera.setPreviewTexture(null);
            } catch (IOException e) {
                e.printStackTrace();
            }
            camera.release();
            camera = null ;
        }
    }

    @Override
    public void startCameraPreview() {
        if (camera != null){
            camera.setPreviewCallback(mPreviewCallback);
            camera.startPreview();

            try {
                Camera.CameraInfo mCameraInfo = new Camera.CameraInfo();
                Camera.getCameraInfo(mCurCameraId, mCameraInfo);

                if (null != mSurfaceHolder){
                    camera.setPreviewDisplay(mSurfaceHolder);
                }

                if (null != mTextureView)
                    camera.setPreviewTexture(mTextureView.getSurfaceTexture());


                int result = getOrientation(mCameraInfo);
                camera.setDisplayOrientation(result); //显示  --- 与拍照数据有关
            } catch (IOException e) {
                if ( null != mCameraStatusCallback ) mCameraStatusCallback.cameraError(CameraStatusCallback.CAMERA_PRIVE);
                e.printStackTrace();
            }

            if (mCameraEntity.isAutoFocus) {
                autoFocusManager = new AutoFocusManager(camera);
                autoFocusManager.start();
            }
        }
    }


    /**
     * 拍照
     */
    public void takePicture(Camera.PictureCallback mPictureCallback) {
        if (camera != null)
            camera.takePicture(null, mPictureCallback, mPictureCallback);
    }


    /**
     * @param mSurfaceHolder
     * 设置 setSurfaceHolder 显示
     * @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (null != mSurfaceHolder){
            if ( null != camera ){
                startCameraPreview();
            }else {
                openCamera();
                startCameraPreview();
            }

        }
    }

     @Override
     public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

     }

     @Override
     public void surfaceDestroyed(SurfaceHolder holder) {
        releaseCamera();
     }
     */
    public void setSurfaceHolder(SurfaceHolder mSurfaceHolder){
        this.mSurfaceHolder = mSurfaceHolder ;
    }



    /**
     * @param mCameraInfo
     * 通过 CameraInfo 获取 显示的旋转角度
     * 参考 api setDisplayOrientation 注释更改
     * @return
     */
    private int getOrientation(Camera.CameraInfo mCameraInfo){
        int result ;
        if (mCameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            result = (360 - mCameraInfo.orientation) % 360;  // compensate the mirror
        } else {  // back-facing
            result = (mCameraInfo.orientation ) % 360;
        }
        return result;
    }

}
