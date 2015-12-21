package com.kylin.camera;

/**
 * Created by zhouxiyuan on 15/12/18.
 */
public interface CameraCallback {

    public static final int CAMERA_OPEN = 1 ;
    public static final int CAMERA_PRIVE = 2 ;
    public static final int CAMERA_SWITCH = 3 ;

    public void error(int status);

}
