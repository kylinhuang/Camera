package com.kylin.camera;

/**
 *
 * 相机状态 CallBack
 *
 */
public interface CameraStatusCallback {

     int CAMERA_OPEN     = 1 ;
     int CAMERA_PRIVE    = 2 ;
     int CAMERA_SWITCH   = 3 ;

     void cameraError(int status);

}
