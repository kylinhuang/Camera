package com.kylin.camera;

import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.os.Build;
import android.support.v4.util.SparseArrayCompat;
import android.view.SurfaceHolder;

/**
 */
public class CameraController  implements ICamera {
    /**
     * 相机状态 callback
     *
     * 相机打开失败 切换失败 等相机状态 以及callback
     */
    CameraStatusCallback mCameraStatusCallback = null ;

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

    private Camera.PreviewCallback mPreviewCallback ;

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
     * @param api CAMERA_API2
     *            CAMERA_API
     */
    public Boolean useAPI(int api){
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

    @Override
    public void takePicture(Camera.PictureCallback mPictureCallback) {
        CameraBaseHelper mCameraHelper = mCameraModeSupported.get(userApi);
        mCameraHelper.takePicture(mPictureCallback);
    }

    @Override
    public void setSurfaceHolder(SurfaceHolder mSurfaceHolder) {
        CameraBaseHelper mCameraHelper = mCameraModeSupported.get(userApi);
        mCameraHelper.setSurfaceHolder(mSurfaceHolder);
    }

    public void setSurface(SurfaceTexture mSurfaceTexture){

    }

    /**
     * @param mCameraStatusCallback
     * 设置 camera status callback
     *
     *  CameraStatusCallback.error(int status)
     *
     *  public static final int CAMERA_OPEN = 1 ;       － 相机打开错误
        public static final int CAMERA_PRIVE = 2 ;      － 相机预览错误
        public static final int CAMERA_SWITCH = 3 ;     － 相机切换错误
     *
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
        CameraBaseHelper mCameraHelper = mCameraModeSupported.get(userApi);
        mCameraHelper.setPreviewCallback(mPreviewCallback);
    }


}
