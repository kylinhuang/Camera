package com.kylin.camera;

/**
 *
 * 相机状态 CallBack
 *
 */
public interface CameraStatusCallback {

     static final int CAMERA_OPEN = 1 ;
     static final int CAMERA_PRIVE = 2 ;
     static final int CAMERA_SWITCH = 3 ;

     void error(int status);

}
