package com.example.tomjerry

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView

class MainActivity : AppCompatActivity(), Gametask {
    private lateinit var rootLayout: LinearLayout
    private lateinit var startBtn: Button
    private lateinit var mGameView: GameView
    private lateinit var scoreTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startBtn = findViewById(R.id.startBtn)
        rootLayout = findViewById(R.id.rootLayout)
        scoreTextView = findViewById(R.id.score)
        mGameView = GameView(this, this)

        startBtn.setOnClickListener {
            mGameView.setBackgroundResource(R.drawable.road)
            rootLayout.addView(mGameView)
            startBtn.visibility = View.GONE
            scoreTextView.visibility = View.GONE
        }
    }

    override fun closeGame(mScore: Int) {
        runOnUiThread {
            scoreTextView.text = "Score : $mScore"
            rootLayout.removeView(mGameView)
            startBtn.visibility = View.VISIBLE
            scoreTextView.visibility = View.VISIBLE
        }
    }
}
