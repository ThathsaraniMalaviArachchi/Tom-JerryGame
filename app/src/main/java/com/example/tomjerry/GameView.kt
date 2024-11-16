package com.example.tomjerry

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.view.MotionEvent
import android.view.View

class GameView(var c: Context, var gameTask: Gametask) : View(c) {
    private var myPaint: Paint? = null
    private var speed = 1
    private var time = 0
    private var score = 0
    private var myTomjerryPosition = 0
    private val otherCars = ArrayList<HashMap<String, Any>>()

    var viewWidth = 0
    var viewHeight = 0

    init {
        myPaint = Paint()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        viewWidth = this.measuredWidth
        viewHeight = this.measuredHeight

        if (time % 700 < 10 + speed) {
            val map = HashMap<String, Any>()
            map["lane"] = (0..2).random()
            map["startTime"] = time
            otherCars.add(map)
        }
        time += 10 + speed
        val tomjerryWidth = viewWidth / 5
        val tomjerryHeight = tomjerryWidth + 10
        myPaint!!.style = Paint.Style.FILL
        val d = resources.getDrawable(R.drawable.rjerry, null)

        d.setBounds(
            myTomjerryPosition * viewWidth / 3 + viewWidth / 15 + 25,
            viewHeight - 2 - tomjerryHeight,
            myTomjerryPosition * viewWidth / 3 + viewWidth / 15 + tomjerryWidth - 25,
            viewHeight - 2
        )
        d.draw(canvas)
        myPaint!!.color = Color.GREEN
        var highScore = 0

        for (i in otherCars.indices) {
            try {
                val carX = otherCars[i]["lane"] as Int * viewWidth / 3 + viewWidth / 15
                var carY = time - (otherCars[i]["startTime"] as Int)
                val d2 = resources.getDrawable(R.drawable.ytom, null)

                d2.setBounds(
                    carX + 25, carY - tomjerryHeight, carX + tomjerryWidth - 25, carY
                )

                d2.draw(canvas)
                if (otherCars[i]["lane"] as Int == myTomjerryPosition) {
                    if (carY > viewHeight - 2 - tomjerryHeight && carY < viewHeight - 2) {
                        gameTask.closeGame(score)
                    }
                }
                if (carY > viewHeight + tomjerryHeight) {
                    otherCars.removeAt(i)
                    score++
                    speed = 1 + Math.abs(score / 8)
                    if (score > highScore) {
                        highScore = score
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        myPaint!!.color = Color.WHITE
        myPaint!!.textSize = 40f
        canvas.drawText("Score : $score", 80f, 80f, myPaint!!)
        canvas.drawText("Speed : $speed", 380f, 80f, myPaint!!)
        invalidate()
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event != null) {
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    val x1 = event.x
                    if (x1 < viewWidth / 2) {
                        if (myTomjerryPosition > 0) {
                            myTomjerryPosition--
                        }
                    }
                    if (x1 > viewWidth / 2) {
                        if (myTomjerryPosition < 2) {
                            myTomjerryPosition++
                        }
                    }
                    invalidate()
                }
                MotionEvent.ACTION_UP -> {
                }
            }
        }
        return true
    }
}
