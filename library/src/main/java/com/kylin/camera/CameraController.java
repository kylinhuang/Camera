package com.kylin.camera;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Build;
import android.view.SurfaceHolder;
import android.view.TextureView;

import com.kylin.camera.bean.CameraEntity;

/**
 */
public class CameraController  implements ICamera {
    private final Context mContext ;
    /**
     * 相机状态 callback
     *
     * 相机打开失败 切换失败 等相机状态 以及callback
     */
    CameraStatusCallback mCameraStatusCallback = null ;

    private int userApi = CAMERA_API;
    public static final int CAMERA_API   = 1  ;
    public static final int CAMERA_API2  = 2  ;
    private static CameraController sInstance;
    private Boolean isSupportCamera2 = false;


    private Camera.PreviewCallback mPreviewCallback ;
    private TextureView mTextureView;
    private CameraEntity cameraEntity;

    public static CameraController getInstance(Context mContext) {
        synchronized (CameraHelper.class) {
            if (sInstance == null) {
                sInstance = new CameraController(mContext);
            }
        }
        return sInstance;
    }

    private CameraController(Context mContext) {
        this.mContext = mContext ;
//        mCameraModeSupported.put(CAMERA_API, CameraHelper.getInstance(mContext));
//        mCameraModeSupported.put(CAMERA_API2, Camera2Helper.getInstance(mContext));
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
        CameraBaseHelper mCameraHelper = getCameraHelper(userApi) ;
        if (null != mCameraHelper)
            mCameraHelper.openCamera();
    }




    @Override
    public void stopCameraPreview() {
        CameraBaseHelper mCameraHelper = getCameraHelper(userApi) ;
        if (null != mCameraHelper)
            mCameraHelper.stopCameraPreview();
    }

    @Override
    public void switchCamera() {
        CameraBaseHelper mCameraHelper = getCameraHelper(userApi) ;
        if (null != mCameraHelper)
            mCameraHelper.switchCamera();
    }

    @Override
    public void releaseCamera() {
        CameraBaseHelper mCameraHelper = getCameraHelper(userApi);
        if (null != mCameraHelper)
            mCameraHelper.releaseCamera();
    }

    @Override
    public void startCameraPreview() {
        CameraBaseHelper mCameraHelper = getCameraHelper(userApi);
        if (null != mCameraHelper)
            mCameraHelper.startCameraPreview();
    }



    @Override
    public void takePicture(Camera.PictureCallback mPictureCallback) {
        CameraBaseHelper mCameraHelper = getCameraHelper(userApi);
        if (null != mCameraHelper)
            mCameraHelper.takePicture(mPictureCallback);
    }

    @Override
    public void setSurfaceHolder(SurfaceHolder mSurfaceHolder) {
        CameraBaseHelper mCameraHelper = getCameraHelper(userApi);
        if (null != mCameraHelper)
            mCameraHelper.setSurfaceHolder(mSurfaceHolder);
    }

    public void setTextureView(TextureView mTextureView){
        this.mTextureView = mTextureView ;
        CameraBaseHelper mCameraHelper = getCameraHelper(userApi);
        if (null != mCameraHelper)
            mCameraHelper.setTextureView(mTextureView);
    }

    /**
     * @param mCameraStatusCallback
     * 设置 camera status callback
     *
     *  CameraStatusCallback.error(int status)
     *
     *  public static final int CAMERA_OPEN = 1 ;       － 相机打开错误
     *  public static final int CAMERA_PRIVE = 2 ;      － 相机预览错误
     *  public static final int CAMERA_SWITCH = 3 ;     － 相机切换错误
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
        CameraBaseHelper mCameraHelper = getCameraHelper(userApi);
        if (null != mCameraHelper )
            mCameraHelper.setPreviewCallback(mPreviewCallback);
    }


    public void setCameraEntity(CameraEntity cameraEntity) {
        CameraBaseHelper mCameraHelper = getCameraHelper(userApi);
        if (null != mCameraHelper )
            mCameraHelper.setCameraEntity(cameraEntity);
    }


    @Override
    public Boolean isOpen() {
        CameraBaseHelper mCameraHelper = getCameraHelper(userApi);
        if (null != mCameraHelper )
            return mCameraHelper.isOpen();

        return false ;
    }

    /**
     * 检查闪光灯
     * @param context
     * @return
     */
    public boolean checkFlashEnable(Context context) {
        return context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
    }


    private CameraBaseHelper getCameraHelper(int userApi) {
        switch (userApi){
            case CAMERA_API :
                return CameraHelper.getInstance(mContext);
            case CAMERA_API2 :
                return Camera2Helper.getInstance(mContext);
        }
        return null;
    }
}
