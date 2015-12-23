package com.kylin.camera;

import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.util.Log;
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
    private Camera camera;
    private SurfaceHolder mSurfaceHolder;
    private AutoFocusManager autoFocusManager;


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
            Log.e("Voip" ,e.toString() );
            if ( null != mCameraStatusCallback ) mCameraStatusCallback.error(CameraStatusCallback.CAMERA_OPEN);
            return;
        }

    }

    @Override
    public void closeCamera() {

    }

    @Override
    public void switchCamera() {
        mCurCameraId =  ++mCurCameraId % mTotalCameraCount ;
        releaseCamera();
        openCamera();
        startCameraPreview();
    }

    @Override
    public void releaseCamera() {
        if (null != camera ){
            autoFocusManager.stop();
            mSurfaceHolder.removeCallback(mCallback);
            camera.setPreviewCallback(null);
            camera.release();
        }
    }

    @Override
    public void startCameraPreview() {
        if (camera != null){
            camera.setPreviewCallback(mPreviewCallback);
            camera.startPreview();

            if (null != mSurfaceHolder ){
                try {
                    Camera.CameraInfo mCameraInfo = new Camera.CameraInfo();
                    Camera.getCameraInfo(mCurCameraId, mCameraInfo);

                    camera.setPreviewDisplay(mSurfaceHolder);

                    int result = getOrientation(mCameraInfo);
                    camera.setDisplayOrientation(result); //显示
                } catch (IOException e) {
                    if ( null != mCameraStatusCallback ) mCameraStatusCallback.error(CameraStatusCallback.CAMERA_PRIVE);
                    e.printStackTrace();
                }
            }

            autoFocusManager = new AutoFocusManager(camera);
            autoFocusManager.start();
        }
    }

    private void initCamera() {
        try {
            mTotalCameraCount = Camera.getNumberOfCameras();
            Camera.CameraInfo mcameraInfo = new Camera.CameraInfo();
            for (int i = 0; i < mTotalCameraCount ; i++) {
                Camera.getCameraInfo(i, mcameraInfo);
                Log.e("Voip", mcameraInfo.orientation + " " + i);
            }
        } catch (Exception e) {

        }

    }

    /**
     * 拍照
     */
    public void takePicture(Camera.PictureCallback mPictureCallback) {
        if (camera != null)
            camera.takePicture(null, null, mPictureCallback);
    }



    public void setSurface(SurfaceTexture mSurfaceTexture){

    }

    public void setSurfaceHolder(SurfaceHolder mSurfaceHolder){
        this.mSurfaceHolder = mSurfaceHolder ;
        mSurfaceHolder.addCallback(mCallback);
    }

    private SurfaceHolder.Callback mCallback = new SurfaceHolder.Callback() {
        @Override
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
    };


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
