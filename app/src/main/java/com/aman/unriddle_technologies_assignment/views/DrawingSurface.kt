package com.aman.unriddle_technologies_assignment.views

import android.content.ContentUris
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Matrix
import android.provider.MediaStore
import android.util.Log
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import androidx.core.graphics.createBitmap
import androidx.core.graphics.withSave
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.math.atan2
import kotlin.math.hypot
import kotlin.random.Random

class DrawingSurfaceView(context: Context) : SurfaceView(context), SurfaceHolder.Callback {

    private var drawBitmap: Bitmap? = null
    private var drawCanvas: Canvas? = null

    private var tempImage: Bitmap? = null
    private var imageMatrix = Matrix()

    private var lastTouchX = 0f
    private var lastTouchY = 0f
    private var activePointerId = MotionEvent.INVALID_POINTER_ID
    private var mode = NONE

    private var initialDistance = 0f
    private var initialRotation = 0f
    private var savedMatrix = Matrix()

    init {
        holder.addCallback(this)
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        drawBitmap = createBitmap(width, height)
        drawCanvas = Canvas(drawBitmap!!)
        drawCanvas?.drawColor(Color.WHITE)
        drawToSurface()
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {}
    override fun surfaceDestroyed(holder: SurfaceHolder) {}

    fun loadRandomImage() {
        val collection = MediaStore.Images.Media.EXTERNAL_CONTENT_URI

        val projection = arrayOf(
            MediaStore.Images.Media._ID
        )

        val cursor = context.contentResolver.query(
            collection,
            projection,
            null,
            null,
            null
        )

        cursor?.use {
            if (it.count > 0) {
                Log.d("ImagePicker", "Found ${it.count} images in storage.")
                while (it.moveToNext()) {
                    val id = it.getLong(it.getColumnIndexOrThrow(MediaStore.Images.Media._ID))
                    val contentUri = ContentUris.withAppendedId(collection, id)
                    Log.d("ImagePicker", "Image URI: $contentUri")
                }

                // Proceed with random selection if needed
                val randomIndex = Random.nextInt(it.count)
                it.moveToPosition(randomIndex)
                val id = it.getLong(it.getColumnIndexOrThrow(MediaStore.Images.Media._ID))
                val contentUri = ContentUris.withAppendedId(collection, id)

                try {
                    val inputStream = context.contentResolver.openInputStream(contentUri)
                    val bitmap = BitmapFactory.decodeStream(inputStream)
                    inputStream?.close()

                    if (bitmap != null) {
                        Log.d("ImagePicker", "Bitmap loaded successfully!")
                        tempImage = bitmap
                        invalidate()
                    } else {
                        Log.d("ImagePicker", "Failed to load bitmap, using fallback")
                        fallbackBlueBox()
                    }
                } catch (e: Exception) {
                    Log.e("ImagePicker", "Error loading image: ${e.message}")
                    fallbackBlueBox()
                }
            } else {
                Log.d("ImagePicker", "No images found in storage")
                fallbackBlueBox()
            }
        } ?: run {
            Log.e("ImagePicker", "Cursor is null")
            fallbackBlueBox()
        }
    }


    private fun fallbackBlueBox() {
        tempImage = createBitmap(300, 300).apply {
            eraseColor(Color.BLUE)
        }
        invalidate()
    }




    fun finalizeImage() {
        tempImage?.let { image ->
            CoroutineScope(Dispatchers.Default).launch {
                drawCanvas?.withSave {
                    concat(imageMatrix)
                    drawBitmap(image, 0f, 0f, null)
                }
                tempImage = null
                withContext(Dispatchers.Main) {
                    drawToSurface()
                }
            }
        }
    }

    private fun drawToSurface() {
        val canvas = holder.lockCanvas()
        canvas?.drawColor(Color.WHITE)
        drawBitmap?.let { bitmap ->
            canvas?.drawBitmap(bitmap, 0f, 0f, null)
        }
        tempImage?.let { image ->
            canvas?.withSave {
                concat(imageMatrix)
                drawBitmap(image, 0f, 0f, null)
            }
        }
        holder.unlockCanvasAndPost(canvas)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action and MotionEvent.ACTION_MASK) {
            MotionEvent.ACTION_DOWN -> {
                savedMatrix.set(imageMatrix)
                lastTouchX = event.x
                lastTouchY = event.y
                activePointerId = event.getPointerId(0)
                mode = DRAG
            }

            MotionEvent.ACTION_POINTER_DOWN -> {
                initialDistance = spacing(event)
                initialRotation = rotation(event)
                if (initialDistance > 10f) {
                    savedMatrix.set(imageMatrix)
                    mode = ZOOM
                }
            }

            MotionEvent.ACTION_MOVE -> {
                if (mode == DRAG) {
                    val dx = event.x - lastTouchX
                    val dy = event.y - lastTouchY
                    imageMatrix.set(savedMatrix)
                    imageMatrix.postTranslate(dx, dy)
                    drawToSurface()
                } else if (mode == ZOOM && event.pointerCount >= 2) {
                    val newDist = spacing(event)
                    val newRotation = rotation(event)

                    if (newDist > 10f) {
                        val scale = newDist / initialDistance
                        val rotate = newRotation - initialRotation

                        imageMatrix.set(savedMatrix)
                        imageMatrix.postScale(scale, scale, width / 2f, height / 2f)
                        imageMatrix.postRotate(rotate, width / 2f, height / 2f)

                        drawToSurface()
                    }
                }
            }

            MotionEvent.ACTION_UP, MotionEvent.ACTION_POINTER_UP -> {
                mode = NONE
                activePointerId = MotionEvent.INVALID_POINTER_ID
            }
        }
        return true
    }

    private fun spacing(event: MotionEvent): Float {
        return if (event.pointerCount >= 2) {
            val x = event.getX(0) - event.getX(1)
            val y = event.getY(0) - event.getY(1)
            hypot(x.toDouble(), y.toDouble()).toFloat()
        } else {
            0f
        }
    }

    private fun rotation(event: MotionEvent): Float {
        return if (event.pointerCount >= 2) {
            val deltaX = event.getX(0) - event.getX(1)
            val deltaY = event.getY(0) - event.getY(1)
            Math.toDegrees(atan2(deltaY.toDouble(), deltaX.toDouble())).toFloat()
        } else {
            0f
        }
    }

    companion object {
        const val NONE = 0
        const val DRAG = 1
        const val ZOOM = 2
    }
}



