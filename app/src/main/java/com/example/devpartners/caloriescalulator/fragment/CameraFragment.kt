package com.example.devpartners.caloriescalulator.fragment

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.example.devpartners.caloriescalulator.R
import com.example.devpartners.caloriescalulator.activity.DashBoardActivity
import org.jetbrains.anko.startActivity
import timber.log.Timber
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.media.Image
import android.graphics.BitmapFactory
import kotlinx.android.synthetic.main.fragment_camera.*


@SuppressLint("ValidFragment")
class CameraFragment(activity : Activity) : Fragment() {
    var timer : TextView? = null
    var back : TextView? = null
    //var camera : com.priyankvasa.android.cameraviewex.CameraView? = null
    var fabButton : android.support.design.widget.FloatingActionButton? = null
    var sample1 : Bitmap? = null
    var sample2 : Bitmap? = null

    @SuppressLint("NewApi")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.fragment_camera, container, false)
        setVar(view)

        back?.setOnClickListener {
            activity?.startActivity<DashBoardActivity>()
            activity?.finish()
        }

        fabButton?.setOnClickListener {
            camera.capture()
            println("capture")
        }

        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        camera.addPictureTakenListener { imageData: ByteArray ->
            Timber.i("Picture taken.")
            println("Picture taken")
        }

        camera.setPreviewFrameListener { image: Image ->
            Timber.i("Preview frame available.")
            println("review frame available")
        }

    }
    @SuppressLint("MissingPermission")
    override fun onResume() {
        super.onResume()
        camera.run { if (!this!!.isCameraOpened) start() }
    }

    override fun onPause() {
        camera.run { if (this!!.isCameraOpened) stop() }
        super.onPause()
    }

    override fun onDestroyView() {
        camera.run { if (this!!.isCameraOpened) stop(removeAllListeners = true) }
        super.onDestroyView()
    }

    private fun setVar(view : View) {
        //camera = view.findViewById(R.id.camera)
        timer = view.findViewById(R.id.timer)
        back = view.findViewById(R.id.back)
        fabButton = view.findViewById(R.id.fabButton)
    }

    fun createImage(width: Int, height: Int, color: Int): Bitmap {
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        val paint = Paint()
        paint.color = color
        canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), paint)
        return bitmap
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
}
