package luyao.wanandroid.test

import android.annotation.SuppressLint
import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.FrameMetrics
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_constraint_test.*
import luyao.util.ktx.ext.invisible
import luyao.util.ktx.ext.visible
import luyao.wanandroid.R
import java.lang.ref.WeakReference

/**
 * Created by luyao
 * on 2020/1/21 10:07
 */
class ConstraintLayoutTest : AppCompatActivity() {

    private var totalTime = 0L
    private val frameMetricHandler = Handler()

    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.N)
    private val frameMetricAvailableListener = Window.OnFrameMetricsAvailableListener { _, frameMetrics, _ ->
        val frameMetricsCopy = FrameMetrics(frameMetrics)
        val layoutMeasureDurationNs = frameMetricsCopy.getMetric(FrameMetrics.LAYOUT_MEASURE_DURATION)

        totalTime += layoutMeasureDurationNs
        Log.e("test","layoutMeasureDurationNs  : $layoutMeasureDurationNs \n layoutMeasureDurationNs average : ${totalTime / 100}")
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onResume() {
        super.onResume()
        window.addOnFrameMetricsAvailableListener(frameMetricAvailableListener,frameMetricHandler)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onPause() {
        super.onPause()
        window.removeOnFrameMetricsAvailableListener(frameMetricAvailableListener)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_constraint_test)

        normalTest.setOnClickListener {
            constraintTest.invisible()
            val container = layoutInflater.inflate(R.layout.item_article, null) as ViewGroup
            val asyncTask = MeasureLayoutAsyncTask(
                    getString(R.string.executing_nth_iteration),
                    WeakReference(normalTest),
                    WeakReference(textViewFinish),
                    WeakReference(container)
            )
            asyncTask.execute()
        }

        constraintTest.setOnClickListener {
            normalTest.invisible()
            val container = layoutInflater.inflate(R.layout.item_article_constraint, null) as ViewGroup
            val asyncTask = MeasureLayoutAsyncTask(
                    getString(R.string.executing_nth_iteration),
                    WeakReference(constraintTest),
                    WeakReference(textViewFinish),
                    WeakReference(container)
            )
            asyncTask.execute()
        }
    }

    private class MeasureLayoutAsyncTask(val executingNthIteration: String,
                                         val startButtonRef: WeakReference<Button>,
                                         val finishTextViewRef: WeakReference<TextView>,
                                         val containerRef: WeakReference<ViewGroup>) : AsyncTask<Void?, Int, Void?>() {
        override fun doInBackground(vararg params: Void?): Void? {
            for (i in 0 until TOTAL) {
                publishProgress(i)
                try {
                    Thread.sleep(100)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            return null
        }

        override fun onProgressUpdate(vararg values: Int?) {
            val startButton = startButtonRef.get() ?: return
            startButton.text = String.format(executingNthIteration, values[0], TOTAL)
            val container = containerRef.get() ?: return

            measureAndLayoutExactLength(container)
            measureAndLayoutWrapLength(container)
        }

        override fun onPostExecute(result: Void?) {
            val finishTextView = finishTextViewRef.get() ?: return
            finishTextView.visibility = View.VISIBLE
            val startButton = startButtonRef.get() ?: return
            startButton.visibility = View.GONE
        }

        private fun measureAndLayoutExactLength(container: ViewGroup) {
            val widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(WIDTH, View.MeasureSpec.EXACTLY)
            val heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(HEIGHT, View.MeasureSpec.EXACTLY)
            container.measure(widthMeasureSpec, heightMeasureSpec)
            container.layout(0, 0, container.measuredWidth, container.measuredHeight)
        }

        private fun measureAndLayoutWrapLength(container: ViewGroup) {
            val widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(WIDTH,
                    View.MeasureSpec.AT_MOST)
            val heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(HEIGHT,
                    View.MeasureSpec.AT_MOST)
            container.measure(widthMeasureSpec, heightMeasureSpec)
            container.layout(0, 0, container.measuredWidth,
                    container.measuredHeight)
        }
    }

    companion object {

        private val TAG = "MainActivity"

        private val TOTAL = 100

        private val WIDTH = 1920

        private val HEIGHT = 1080
    }
}