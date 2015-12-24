package com.kylin.camera;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Size;
import android.view.Surface;

import java.util.Arrays;

/**
 * Created by zhouxiyuan on 15/12/18.
 */
@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class Camera2Helper  extends CameraBaseHelper{
    /**
     * 拍照的Builder
     */
    private CaptureRequest.Builder mCaptureBuilder;

    private static Camera2Helper sInstance;
    private CameraManager mCameraManager;
    private HandlerThread mHandlerThread;
    private Handler mHandler;
    public CameraDevice mCameraDevice;
    private Size mPreviewSize;
    private CaptureRequest.Builder mPreviewBuilder;

    private CameraCaptureSession.StateCallback mSessionStateCallback = new CameraCaptureSession.StateCallback() {
        @Override
        public void onConfigured(CameraCaptureSession session) {
            try {
                updatePreview(session);
            } catch (CameraAccessException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onConfigureFailed(CameraCaptureSession session) {

        }
    };

    private void updatePreview(CameraCaptureSession session) throws CameraAccessException {
        session.setRepeatingRequest(mPreviewBuilder.build(), null, mHandler);
    }


    private CameraDevice.StateCallback mCameraDeviceStateCallback = new CameraDevice.StateCallback() {
        @Override
        public void onOpened(CameraDevice camera) {
            mCameraDevice =  camera ;
            startCameraPreview();
        }

        @Override
        public void onDisconnected(CameraDevice camera) {

        }

        @Override
        public void onError(CameraDevice camera, int error) {

        }
    };



    public static Camera2Helper getInstance(Context mContext) {
        synchronized (CameraHelper.class) {
            if (sInstance == null) {
                sInstance = new Camera2Helper(mContext);
            }
        }
        return sInstance;
    }

    private Camera2Helper(Context mContext){
        this.mContext = mContext ;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void openCamera() {
        initHandler();//初始化子线程和handler
        try {
            String[] cameraIds = mCameraManager.getCameraIdList();
            if ( null != cameraIds && cameraIds.length > 0 )
            mCameraManager.openCamera(cameraIds[0], mCameraDeviceStateCallback, mHandler);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }

        try {
            //获得CameraManager
            CameraManager cameraManager = (CameraManager) mContext.getSystemService(Context.CAMERA_SERVICE);
            //获得属性
            CameraCharacteristics characteristics = cameraManager.getCameraCharacteristics(String.valueOf(0));
            //支持的STREAM CONFIGURATION
            StreamConfigurationMap map = characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
            //显示的size
            mPreviewSize = map.getOutputSizes(SurfaceTexture.class)[0];
            //打开相机
            cameraManager.openCamera(String.valueOf(0), mCameraDeviceStateCallback, mHandler);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }

    }

    private void initHandler() {
        mHandlerThread = new HandlerThread("Android_L_Camera");
        mHandlerThread.start();
        mHandler = new Handler(mHandlerThread.getLooper());
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
        if (null != mTextureView ){
            SurfaceTexture texture = mTextureView.getSurfaceTexture();
            texture.setDefaultBufferSize(mPreviewSize.getWidth(), mPreviewSize.getHeight());
            Surface surface = new Surface(texture);
            try {
                mPreviewBuilder = mCameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
                mPreviewBuilder.addTarget(surface);
                mCameraDevice.createCaptureSession(Arrays.asList(surface), mSessionStateCallback, mHandler);
            } catch (CameraAccessException e) {
                e.printStackTrace();
            }
        }

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

    /**
     * 得到CameraCharacteristics等信息，设置显示大小
     *
     * @throws CameraAccessException
     */
//    private void setUpCameraOutputs(int width, int height) throws CameraAccessException {
//        mFormat = mSp.getInt("format", 256);
//        //描述CameraDevice属性
//        mCameraCharacteristics = mCameraManager.getCameraCharacteristics(mCameraId);
//        //流配置
//        StreamConfigurationMap map = mCameraCharacteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
//        if (mCameraId.equals("0") && mFormat == ImageFormat.JPEG) {
//            mlargest = Collections.max(Arrays.asList(map.getOutputSizes(ImageFormat.JPEG)), new CompareSizesByArea());
//            mPreviewSize = chooseOptimalSize(map.getOutputSizes(SurfaceTexture.class), width, height, mlargest);
//        } else if (mCameraId.equals("0") && mFormat == ImageFormat.RAW_SENSOR) {
//            mlargest = Collections.max(Arrays.asList(map.getOutputSizes(ImageFormat.RAW_SENSOR)), new CompareSizesByArea());
//            mPreviewSize = chooseOptimalSize(map.getOutputSizes(SurfaceTexture.class), width, height, mlargest);
//        } else {
////            mPreviewSize = new Size(1280, 720);
//            mlargest = Collections.max(Arrays.asList(map.getOutputSizes(ImageFormat.JPEG)), new CompareSizesByArea());
//            mPreviewSize = chooseOptimalSize(map.getOutputSizes(SurfaceTexture.class), width, height, mlargest);
//        }
//        int orientation = getResources().getConfiguration().orientation;
//        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
//            mTextureView.fitWindow(mPreviewSize.getWidth(), mPreviewSize.getHeight());
//        } else {
//            mTextureView.fitWindow(mPreviewSize.getHeight(), mPreviewSize.getWidth());
//        }
//    }
}
