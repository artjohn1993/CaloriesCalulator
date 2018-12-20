package com.example.devpartners.caloriescalulator.event

import android.content.Context
import android.hardware.Camera
import android.util.Log
import android.view.SurfaceHolder
import android.view.SurfaceView

class CameraPreview(context : Context, camera : Camera) : SurfaceView(context), SurfaceHolder.Callback {
    var mHolder : SurfaceHolder = holder
    var mCamera : Camera = camera

    init {
        mHolder.addCallback(this)
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS)
    }


    override fun surfaceChanged(p0: SurfaceHolder?, p1: Int, p2: Int, p3: Int) {
        refreshCamera(mCamera)
    }

    override fun surfaceDestroyed(p0: SurfaceHolder?) {

    }

    override fun surfaceCreated(p0: SurfaceHolder?) {
        try {
            // create the surface and start camera preview
            if (mCamera == null) {
                mCamera.setPreviewDisplay(holder)
                mCamera.startPreview()
            }
        } catch (e: Exception) {

        }
    }

    fun refreshCamera(camera: Camera) {
        if (mHolder.surface == null) {
            // preview surface does not exist
            return
        }
        // stop preview before making changes
        try {
            mCamera.stopPreview()
        } catch (e: Exception) {
            // ignore: tried to stop a non-existent preview
        }

        // set preview size and make any resize, rotate or
        // reformatting changes here
        // start preview with new settings
        setCamera(camera)
        try {
            mCamera.setPreviewDisplay(mHolder)
            mCamera.startPreview()
        } catch (e: Exception) {

        }
    }

    fun setCamera(camera: Camera) {
        //method to set a camera instance
        mCamera = camera
    }
}