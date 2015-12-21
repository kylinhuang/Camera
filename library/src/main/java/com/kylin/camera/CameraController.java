package com.kylin.camera;

import android.graphics.SurfaceTexture;
import android.os.Build;
import android.support.v4.util.SparseArrayCompat;
import android.view.SurfaceHolder;

/**
 */
public class CameraController  implements ICamera {
    private int userApi = CAMERA_API;
    public static final int CAMERA_API   = 1  ;
    public static final int CAMERA_API2  = 2  ;
    private static SparseArrayCompat<CameraBaseHelper> mCameraModeSupported = new SparseArrayCompat<CameraBaseHelper>();
    private static CameraController sInstance;
    private Boolean isSupportCamera2 = false;

    static {
        mCameraModeSupported.put(CAMERA_API, CameraHelper.getInstance());
        mCameraModeSupported.put(CAMERA_API2, Camera2Helper.getInstance());
    }

    public static CameraController getInstance() {
        synchronized (CameraHelper.class) {
            if (sInstance == null) {
                sInstance = new CameraController();
            }
        }
        return sInstance;
    }

    private CameraController() {
        isSupportCamera2 = isSupportCamera2();
        if(isSupportCamera2)
            userApi = CAMERA_API2 ;

    }

    /**
     * 使用 Camera API
     */
    private Boolean useAPI(int api){
        if (  !isSupportCamera2 ){ //不支持Camera2
            if ( api == CAMERA_API2  ){
                userApi = CAMERA_API ;
                return false ;
            }
        }
        userApi = api ;
        return true ;
    }


    /**
     * @return 是否支持Camera2
     */
    private Boolean isSupportCamera2() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            // your code using Camera API here - is between 1-20
            return false ;
        } else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // your code using Camera2 API here - is api 21 or higher
            return true ;
        }
        return false ;
    }

    @Override
    public void openCamera() {
        CameraBaseHelper mCameraHelper = mCameraModeSupported.get(userApi);
        mCameraHelper.openCamera();
    }


    @Override
    public void closeCamera() {
        CameraBaseHelper mCameraHelper = mCameraModeSupported.get(userApi);
        mCameraHelper.closeCamera();
    }

    @Override
    public void switchCamera() {
        CameraBaseHelper mCameraHelper = mCameraModeSupported.get(userApi);
        mCameraHelper.switchCamera();
    }

    @Override
    public void releaseCamera() {
        CameraBaseHelper mCameraHelper = mCameraModeSupported.get(userApi);
        mCameraHelper.releaseCamera();
    }

    @Override
    public void startCameraPreview() {
        CameraBaseHelper mCameraHelper = mCameraModeSupported.get(userApi);
        mCameraHelper.startCameraPreview();
    }


    public void setSurface(SurfaceTexture mSurfaceTexture){
//        mSurfaceTexture.

    }

    public void setSurfaceHolder(SurfaceHolder mSurfaceHolder){
//        mSurfaceTexture.

    }



}
