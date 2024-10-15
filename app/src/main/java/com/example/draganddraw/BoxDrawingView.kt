package com.example.draganddraw
//BoxDrawingView is a custom created class
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PointF
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View


private const val TAG = "BoxDrawingView"


class BoxDrawingView(
    context: Context,
    attrs: AttributeSet?= null //extract whatever is in xml file
): View(context, attrs){ //creating View as superclass to BoxDrawingView
    private var currentBox: Box?= null
    private val boxes = mutableListOf<Box>()

    private val boxPaint = Paint().apply{
        color = 0x22ff0000.toInt()
    }

    private val backgroundPaint = Paint().apply{
        color = 0xfff8efe0.toInt()
    }


    override fun onDraw(canvas: Canvas) {
        canvas.drawPaint(backgroundPaint)

        boxes.forEach { box ->
            canvas.drawRect(box.left, box.right, box.top, box.bottom, boxPaint)
        }
    }

    //NOTE
//    On Android, the origin is the top-left corner, so the left and top values will
//    be the minimum values, and the bottom and right values will be the maximum
//    values


    override fun onTouchEvent(event: MotionEvent): Boolean {
        val current = PointF(event.x, event.y)
        var action = ""


        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                action = "ACTION_DOWN"
                currentBox = Box(current).also {
                    boxes.add(it)
                }
            }
            MotionEvent.ACTION_MOVE -> {
                action = "ACTION_MOVE"
                updateCurrentBox(current)
            }
            MotionEvent.ACTION_UP -> {
                action = "ACTION_UP"
                updateCurrentBox(current)
                currentBox = null
            }
            MotionEvent.ACTION_CANCEL -> {
                action = "ACTION_CANCEL"
                updateCurrentBox(current)
                currentBox = null
            }
        }
        Log.i(TAG, "$action at x=${current.x}, y=${current.y}")
        return true
    }

    private fun updateCurrentBox(current: PointF){
        currentBox?.let{
            it.end = current
            invalidate()
        }
    }


}