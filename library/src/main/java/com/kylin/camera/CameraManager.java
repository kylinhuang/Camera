//package com.kylin.camera;
//
//import android.graphics.SurfaceTexture;
//import android.hardware.Camera;
//import android.hardware.Camera.CameraInfo;
//import android.hardware.Camera.Parameters;
//import android.hardware.Camera.PreviewCallback;
//
//import com.kinstalk.qinjian.utils.DebugUtil;
//import com.kinstalk.voip.sdk.common.Log;
//
//import java.lang.ref.WeakReference;
//
//public class CameraManager
//{
//	public static final String TAG = "CameraManager";
//
//	private static int gTotalCameraCount, gCurrentCameraId, gCurrentCameraFacing, gDefaultCameraId;
//	private static Camera gCurrentCamera;
//	private static SurfaceTexture gTexture;
//	private static OpenCameraThread gThread = new OpenCameraThread();
//	private static WeakReference<CameraListener> gCallback;
//	private static volatile boolean mInitialized = false;
//	public static CameraCallback mCameraCallback;
//
//	public synchronized static final void init()
//	{
//		if (mInitialized == false) {
//		try
//		{
//			mInitialized = true;
//			gTotalCameraCount = Camera.getNumberOfCameras();
//			gDefaultCameraId = 0;
//			// Find the ID of the default camera
//			CameraInfo cameraInfo = new CameraInfo();
//			for (int i = 0; i < gTotalCameraCount; i++)
//			{
//				Camera.getCameraInfo(i, cameraInfo);
//				if (cameraInfo.facing == CameraInfo.CAMERA_FACING_FRONT)
//				{
//					gDefaultCameraId = i;
//					break;
//				}
//			}
//			gCurrentCameraId = gDefaultCameraId;
//			gCurrentCameraFacing = cameraInfo.facing;
//			gThread.start();
//		}
//		catch (Exception e)
//		{
//			Log.e(TAG, "", e);
//		}
//		}
//	}
//
//	public static final void openCamera(CameraListener callback, SurfaceTexture st)
//	{
//		DebugUtil.LogE("Voip","openCamera");
//		try
//		{
//			gTexture = st;
//			gCallback = new WeakReference<CameraListener>(callback);
//			synchronized (gThread)
//			{
//				gThread.notifyAll();
//			}
//		}
//		catch (Exception e)
//		{
//			Log.e(TAG, "", e);
//		}
//	}
//
//	public static final void closeCamera()
//	{
//		try
//		{
//			stopCameraPreview();
//
//			Camera curCam = gCurrentCamera;
//			if (curCam != null)
//			{
//				synchronized (curCam)
//				{
//					curCam.setPreviewCallback(null);
//					curCam.release();
//				}
//
//				Log.i(TAG, "camera preview stopped...[id=" + gCurrentCameraId + "]");
//			}
//
//			gCurrentCamera = null;
//		}
//		catch (Exception e)
//		{
//			Log.e(TAG, "", e);
//		}
//
//	}
//
//	static class OpenCameraThread extends Thread
//	{
//		public void run()
//		{
//			while (true)
//			{
//				synchronized (this)
//				{
//					try
//					{
//						wait();
//					}
//					catch (InterruptedException e)
//					{
//						Log.e(TAG, "", e);
//					}
//				}
//
//				try
//				{
//					Log.e(TAG, "last camera not closed, trying to close it...");
//					Camera curCam = gCurrentCamera;
//					if (curCam != null)
//					{
//						synchronized (curCam)
//						{
//							curCam.setPreviewCallback(null);
//							curCam.stopPreview();
//							curCam.release();
//						}
//					}
//					Log.i(TAG, "try to open camera...[id=" + gCurrentCameraId + "]");
//					gCurrentCamera = Camera.open(gCurrentCameraId);
//					curCam = gCurrentCamera;
//					synchronized (curCam)
//					{
//						Parameters p = curCam.getParameters();
//						p.setPreviewSize(320, 240); //4/3   16/9
////						p.setPreviewFpsRange(15000,20000);//设置帧率
////						p.setPreviewSize(320, 180);640、480
//						curCam.setParameters(p);
//						curCam.setDisplayOrientation(90);
//						CameraInfo info = new CameraInfo();
//						Camera.getCameraInfo(gCurrentCameraId, info);
//						gCurrentCameraFacing = info.facing;
//
//						if (gTexture != null)
//						{
//							curCam.setPreviewTexture(gTexture);
//							startCameraPreview();
//						}
//					}
//
//					notifyCameraOpenResult(true);
//				}
//				catch (Exception e)
//				{
//					if (null != mCameraCallback) mCameraCallback.onCameraState(CameraCallback.OPEN_CAMERA_FAILED);
//					Log.e(TAG, "Camera open failed!", e);
//					notifyCameraOpenResult(false);
//				}
//			}
//		}
//	}
//
//	public static void switchNextCamera()
//	{
//		try
//		{
//			stopCameraPreview();
//
//			gCurrentCameraId = (gCurrentCameraId + 1) % gTotalCameraCount;
//			synchronized (gThread)
//			{
//				gThread.notifyAll();
//			}
//		}
//		catch (Exception e)
//		{
//			Log.e(TAG, "", e);
//		}
//	}
//
//	public static void resetToDefaultCamera()
//	{
//		try
//		{
//			stopCameraPreview();
//
//			gCurrentCameraId = gDefaultCameraId;
//		}
//		catch (Exception e)
//		{
//			Log.e(TAG, "", e);
//		}
//	}
//
//	public static int getCurrentCameraFacing()
//	{
//		return gCurrentCameraFacing;
//	}
//
//	private static void startCameraPreview()
//	{
//		Camera curCam = gCurrentCamera;
//
//		if (curCam != null)
//		{
//			synchronized (curCam)
//			{
//
//				if (gCallback != null && gCallback.get() != null)
//				{
//					Log.i(TAG, "setting preview callback...[" + gCallback.get().toString() + "]");
//					curCam.setPreviewCallback(gCallback.get());
//				}
//				else
//				{
//					Log.i(TAG, "camera preview callback is null, skip setting callback...");
//				}
//				Log.i(TAG, "starting camera preview...[id=" + gCurrentCameraId + ", facing=" + gCurrentCameraFacing + "]");
//				curCam.startPreview();
//
//			}
//		}
//		else
//		{
//			Log.i(TAG, "camera instance is null, skip start preview...");
//		}
//
//	}
//
//	private static void stopCameraPreview()
//	{
//		Log.i(TAG, "stopping camera preview...[id=" + gCurrentCameraId + ", facing=" + gCurrentCameraFacing + "]");
//		Camera curCam = gCurrentCamera;
//
//		if (curCam == null)
//		{
//			Log.i(TAG, "current camera is null, skip stopping...");
//			return;
//		}
//		else
//		{
//			synchronized (curCam)
//			{
//				curCam.stopPreview();
//				curCam.setPreviewCallback(null);
//			}
//		}
//
//		Log.i(TAG, "camera preview stopped...");
//	}
//
//	public interface CameraListener extends PreviewCallback
//	{
//		public void onCameraOpenResult(boolean isSuccess);
//	}
//
//	private static void notifyCameraOpenResult(boolean result)
//	{
//		if (gCallback != null)
//		{
//			CameraListener listener = gCallback.get();
//			if (listener != null)
//			{
//				listener.onCameraOpenResult(result);
//			}
//		}
//	}
//
//	public static void setCameraCallback(CameraCallback mCallback){
//		mCameraCallback = mCallback ;
//	}
//
//	public interface CameraCallback {
//		public static final int OPEN_CAMERA_FAILED = 1;
//
//		public void onCameraState(int type);
//	}
//}
