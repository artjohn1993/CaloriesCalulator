package com.example.devpartners.caloriescalulator.activity

import android.Manifest
import android.hardware.Camera
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.devpartners.caloriescalulator.R
import com.example.devpartners.caloriescalulator.event.CameraPreview
import kotlinx.android.synthetic.main.activity_camera.*
import android.content.Intent
import android.graphics.BitmapFactory
import android.R.attr.bitmap
import android.view.WindowManager
import android.R.attr.bitmap
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Handler
import android.os.SystemClock
import android.support.v4.app.ActivityCompat
import android.support.v4.app.ActivityCompat.requestPermissions
import android.support.v4.content.ContextCompat
import android.view.View
import com.example.devpartners.caloriescalulator.event.CustomSnackBar
import kotlinx.coroutines.delay
import org.jetbrains.anko.startActivity


@SuppressLint("ByteOrderMark")
class CameraActivity : AppCompatActivity() {

    lateinit var mCamera : Camera
    lateinit var mPreview : CameraPreview
    private var mPicture: Camera.PictureCallback? = null
    private var cameraFront = false
    private var holderBitmap : Bitmap? = null
    private var tempBitmap : Bitmap? = null
    var time = 0
    var isInterrupted = false
    var stopTime : Long = 0
    var isIdle : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        mCamera = Camera.open()
        mCamera.setDisplayOrientation(90)
        mPreview = CameraPreview(this, mCamera)

        cpreview.addView(mPreview)

        startButton.setOnClickListener {
            //requestPermission()
            startTimer()
            Handler().postDelayed({
                takePictures()
            },2000)
        }
        stopButton.setOnClickListener {
            isInterrupted = true
            pauseTImer()
            var tempTime = (stopTime.toDouble()) * (-1)
            stopTimer()
            var minutes = tempTime/60000
            var burnedCalories = calculateCalories(getWeight(),calculateTime(tempTime)).toString()

            if (minutes > 0.9) {
                var intent = Intent(this, ResultActivity::class.java)
                intent.putExtra("weight", getWeight().toString())
                intent.putExtra("height", getHeight().toString())
                intent.putExtra("age", getAge().toString())
                intent.putExtra("minutes", minutes.toString())
                intent.putExtra("calories", burnedCalories)
                startActivity(intent)
                finish()
            }
            else {
                CustomSnackBar.show("Invalid exercise", this)
            }


        }
        switchCam.setOnClickListener {
            val camerasNumber = Camera.getNumberOfCameras()
            if (camerasNumber > 1) {
                //release the old camera instance
                //switch camera, from the front and the back and vice versa
                println("switch")
                releaseCamera()
                chooseCamera()
            } else {
                println("not switch")
            }
        }
        back.setOnClickListener {
            startActivity<DashBoardActivity>()
            finish()
        }

        mCamera.startPreview()
    }

    override fun onPause() {
        super.onPause()
        //when on Pause, release camera in order to be used from other applications
        releaseCamera()
    }

    public override fun onResume() {

        super.onResume()
        if (mCamera == null) {
            mCamera = Camera.open()
            mCamera.setDisplayOrientation(90)
            mPicture = getPictureCallback()
            mPreview.refreshCamera(mCamera)

        } else {

        }

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            1000 -> {
                if (grantResults.isEmpty() || grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mCamera.takePicture(null, null, getPictureCallback())
                    takePictures()
                }
            }
        }
    }

    private fun findFrontFacingCamera(): Int {

        var cameraId = -1
        // Search for the front facing camera
        val numberOfCameras = Camera.getNumberOfCameras()
        for (i in 0 until numberOfCameras) {
            val info = Camera.CameraInfo()
            Camera.getCameraInfo(i, info)
            if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                cameraId = i
                cameraFront = true
                break
            }
        }
        return cameraId

    }

    private fun findBackFacingCamera(): Int {
        var cameraId = -1
        //Search for the back facing camera
        //get the number of cameras
        val numberOfCameras = Camera.getNumberOfCameras()
        //for every camera check
        for (i in 0 until numberOfCameras) {
            val info = Camera.CameraInfo()
            Camera.getCameraInfo(i, info)
            if (info.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
                cameraId = i
                cameraFront = false
                break

            }

        }
        return cameraId
    }

    private fun releaseCamera() {
        // stop and release camera
        if (mCamera != null) {
            mCamera.stopPreview()
            mCamera.setPreviewCallback(null)
            mCamera.release()
           // mCamera = null
        }
    }

    private fun getPictureCallback(): Camera.PictureCallback {
        val picture = Camera.PictureCallback { data, camera ->
            print("--------------taken--------------")
            camera.startPreview()
            var bitmap = BitmapFactory.decodeByteArray(data, 0, data.size)

            if(tempBitmap == null ) {
                println("picture 1")
                tempBitmap = bitmap
            } else {
                if (holderBitmap == null ) {
                    println("picture 2")
                    holderBitmap = bitmap
                } else {
                    var result = compareImages(tempBitmap!!, holderBitmap!!)
                    if (result) {
                        println("the same picture")
                        pauseTImer()
                        isIdle = true
                        idle.visibility = View.VISIBLE
                        timer.visibility = View.GONE
                    } else {

                        println("not the same picture")
                        if (isIdle) {
                            startTimer()
                            isIdle = false
                        }
                        idle.visibility = View.GONE
                        timer.visibility = View.VISIBLE
                    }

                    holderBitmap = tempBitmap
                    tempBitmap = bitmap
                }
            }
        }
        return picture
    }

    private fun requestPermission() {
        val permission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA)

        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), 1000)
        }
        else {
            try {
                mCamera.takePicture(null, null, getPictureCallback())
                takePictures()
            } catch (e : Exception) {
                println(e.toString())
            }

        }
    }

    fun compareImages(bitmap1: Bitmap, bitmap2: Bitmap): Boolean {
        if (bitmap1.width != bitmap2.width || bitmap1.height != bitmap2.height) {
            return false
        }

        for (y in 0 until bitmap1.height) {
            for (x in 0 until bitmap1.width) {
                if (bitmap1.getPixel(x, y) != bitmap2.getPixel(x, y)) {
                    return false
                }
            }
        }

        return true
    }

    @SuppressLint("RestrictedApi")
    fun startTimer() {
        timer.base = SystemClock.elapsedRealtime() + stopTime
        timer.start()
        startButton.visibility = View.GONE
        stopButton.visibility = View.VISIBLE
    }

    fun pauseTImer() {
        stopTime = timer.base - SystemClock.elapsedRealtime()
        timer.stop()
    }

    @SuppressLint("RestrictedApi")
    fun stopTimer() {
        timer.base = SystemClock.elapsedRealtime()
        stopTime = 0
        timer.stop()
        startButton.visibility = View.VISIBLE
        stopButton.visibility = View.GONE
    }

    fun takePictures() {
        if (!isInterrupted) {
            Handler().postDelayed({
                requestPermission()
            },4000)
        }
    }

    private fun getWeight() : Int {
        if (intent.extras != null) {
            return intent.getStringExtra("weight").toInt()
        }
        else {
            return 0
        }
    }
    private fun getHeight() : Int {
        if (intent.extras != null) {
            return intent.getStringExtra("height").toInt()
        }
        else {
            return 0
        }
    }

    private fun getAge() : Int {
        if (intent.extras != null) {
            return intent.getStringExtra("age").toInt()
        }
        else {
            return 0
        }
    }

    private fun calculateTime(time : Double ) : Double {

        return (time/60000)/60
    }

    private fun calculateCalories(weight : Int, time : Double) : Int {
        println("weight:$weight")
        println("time:$time")
        return ((8 * weight) * time).toInt()
    }

    fun chooseCamera() {
        //if the camera preview is the front
        if (cameraFront) {
            val cameraId = findBackFacingCamera()
            if (cameraId >= 0) {
                //open the backFacingCamera
                //set a picture callback
                //refresh the preview

                mCamera = Camera.open(cameraId)
                mCamera.setDisplayOrientation(90)
                mPicture = getPictureCallback()
                mPreview.refreshCamera(mCamera)
            }
        } else {
            val cameraId = findFrontFacingCamera()
            if (cameraId >= 0) {
                //open the backFacingCamera
                //set a picture callback
                //refresh the preview
                mCamera = Camera.open(cameraId)
                mCamera.setDisplayOrientation(90)
                mPicture = getPictureCallback()
                mPreview.refreshCamera(mCamera)
            }
        }
    }
}
