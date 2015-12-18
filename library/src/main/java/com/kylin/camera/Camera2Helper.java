package com.kylin.camera;

/**
 * Created by zhouxiyuan on 15/12/18.
 */
public class Camera2Helper  extends CameraBaseHelper{


    private static Camera2Helper sInstance;

    public static Camera2Helper getInstance() {
        synchronized (CameraHelper.class) {
            if (sInstance == null) {
                sInstance = new Camera2Helper();
            }
        }
        return sInstance;
    }

    private Camera2Helper(){

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
