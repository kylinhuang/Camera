package com.kylin.camera.bean;

import android.graphics.PixelFormat;

/**
 * Created by zhouxiyuan on 15/12/23.
 */
public class CameraEntity {
    //自动对焦
    public Boolean isAutoFocus = false ;
    // 开启闪光灯
    public Boolean isOpenFlashlight = false ;

    /**
     * setPreviewSize 预览大小
     */
    public int [] mPreviewSize = new int[2] ;

    /**
     * setPreviewFpsRange
     */
    public int [] mPreviewFpsRange = new int[2] ;

    /**
     * 拍照格式
     * 默认 PixelFormat.JPEG
     */
    public int mPictureFormat = PixelFormat.JPEG ;


}
