package com.kylin.camera;

import android.hardware.camera2.CaptureRequest;

/**
 * Created by zhouxiyuan on 15/12/18.
 */
public class Camera2Helper  extends CameraBaseHelper{
    /**
     * 拍照的Builder
     */
    private CaptureRequest.Builder mCaptureBuilder;

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
//        initHandler();//初始化子线程和handler
        //获得camera服务
//        mCameraManager = (CameraManager) getActivity().getSystemService(Context.CAMERA_SERVICE);
//        setUpCameraOutputs(viewWidth, viewHeight);
//        configureTransform(viewWidth, viewHeight);
//        initOutputSurface();
        //打开相机
//        mCameraManager.openCamera(mCameraId, mCameraDeviceStateCallback, mHandler);
//        newPreviewSession();

    }

    @Override
    public void stopCameraPreview() {

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

//    @Override
//    public void takePicture() {
        //创建构建者，配置参数
//        mCaptureBuilder = mCameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_ZERO_SHUTTER_LAG);
//        if (mFormat == ImageFormat.RAW_SENSOR) {
//            mCaptureBuilder.set(CaptureRequest.STATISTICS_LENS_SHADING_MAP_MODE, CaptureRequest.STATISTICS_LENS_SHADING_MAP_MODE_ON); // Required for RAW capture
//        }
//        mCaptureBuilder.addTarget(mImageReader.getSurface());
//        mCaptureBuilder.set(CaptureRequest.CONTROL_MODE, CameraMetadata.CONTROL_MODE_AUTO);
//        //设置连续帧
//        Range<Integer> fps[] = mCameraCharacteristics.get(CameraCharacteristics.CONTROL_AE_AVAILABLE_TARGET_FPS_RANGES);
//        mCaptureBuilder.set(CaptureRequest.CONTROL_AE_TARGET_FPS_RANGE, fps[fps.length - 1]);//设置每秒30帧
//        //得到方向
//        int rotation = getActivity().getWindowManager().getDefaultDisplay().getRotation();
//        mCaptureBuilder.set(CaptureRequest.JPEG_ORIENTATION, ORIENTATIONS.get(rotation));
//        previewBuilder2CaptureBuilder();
//        mState = STATE_CAPTURE;
//        mCameraDevice.createCaptureSession(mOutputSurfaces, mSessionCaptureStateCallback, mHandler);
//    }
}
