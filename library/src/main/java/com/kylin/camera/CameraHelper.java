package com.kylin.camera;

/**
 * Created by zhouxiyuan on 15/12/18.
 */
public class CameraHelper  extends CameraBaseHelper {

    private static CameraHelper sInstance;

    public static CameraHelper getInstance() {
        synchronized (CameraHelper.class) {
            if (sInstance == null) {
                sInstance = new CameraHelper();
            }
        }
        return sInstance;
    }

    private CameraHelper(){

    }

    @Override
    public void openCamera() {

    }

    @Override
    public void closeCamera() {

    }

    @Override
    public void switchCamera() {

    }

    @Override
    public void releaseCamera() {

    }

    @Override
    public void startCameraPreview() {

    }
}
