package com.example.nuonuo.ui.custom

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.graphics.Color.parseColor

import android.graphics.drawable.Drawable
import android.animation.ValueAnimator
import android.view.animation.LinearInterpolator
import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.util.Log
import android.view.MotionEvent
import android.graphics.SweepGradient
import com.example.nuonuo.R

/**
@author yjn
@create 2020/1/5 - 12:53
 */
class WaveButton:View {


    companion object{
        private const val TAG = "WaveButton"
        /**
         * 涟漪默认颜色
         */
        private const val DEFAULT_WAVE_COLOR = Color.WHITE

        /**
         * 进度条默认颜色
         */
        private val DEFAULT_PROGRESS_COLOR = parseColor("#0277bd")

        /**
         * 涟漪默认创建速度
         */
        private const val DEFAULT_SPEED = 600L

        /**
         * 涟漪默认持续时间
         */
        private const val DEFAULT_DURATION_TIME = 3000L
    }
    /**
     * 进度条初始数值
     */
    private var progress = 0f
        set(value) {
            field = value
            invalidate()
        }

    /**
     * 涟漪颜色
     */
    private var waveColor = DEFAULT_WAVE_COLOR
        set(value) {
            field = value
            invalidate()
        }

    /**
     * 进度条颜色
     */
    private var progressColor = DEFAULT_PROGRESS_COLOR
    /**
     * 渐变颜色数组
     */
    private var arcColors: IntArray? = null

    /**
     * 梯度渐变扫描渲染器
     */
    private var sweepGradient: SweepGradient? = null

    /**
     * 控件宽高
     */
    private var widgetWidth: Int = 0
    private var widgetHeight: Int = 0

    /**
     * bitmap宽高
     */
    private var bitmapWidth: Int = 0
    private var bitmapHeight: Int = 0

    /**
     * 控件宽高1/2
     */
    private var widgetWidthHalf: Float = 0f
    private var widgetHeightHalf: Float = 0f

    /**
     * 进度条半径
     */
    private var progressRadius: Float = 0f

    /**
     * 涟漪最小、最大 半径
     */
    private var mMinRadius: Float = 0.toFloat()
    private var mMaxRadius: Float = 0.toFloat()

    /**
     * 涟漪中心的Drawable资源
     */
    private var waveSrc: Drawable? = null

    /**
     * bitmap
     */
    private var mBitmap: Bitmap? = null

    /**
     * 涟漪画笔
     */
    private var wavePaint: Paint? = null

    /**
     * bitmap画笔
     */
    private var bitmapPaint: Paint? = null

    /**
     * 进度条画笔
     */
    private var progressPaint: Paint? = null


    /**
     * 涟漪集合
     */
    private val mWaveList = ArrayList<Wave>()

    /**
     * 线性插值器（匀速）
     */
    private val mInterpolator = LinearInterpolator()

    /**
     * 进度条属性动画
     */
    private var progressAnimator: ValueAnimator? = null

    /**
     * 矩形
     */
    private val rectF = RectF()

    private var isRunning = false

    /**
     * 涟漪最后创建时间
     */
    private var mLastCreateTime:Long = 0


    var mProgressListener:ProgressListener? = null

    interface ProgressListener{
        fun onEnd()
    }

    constructor (context: Context?) : this(context, null)

    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr){
        // 取出布局中自定义属性
        val typedArray = context!!.obtainStyledAttributes(attrs, R.styleable.WaveButton)
        waveSrc = typedArray.getDrawable(R.styleable.WaveButton_waveSrc)
        // xml中没有指定默认图片
        if (waveSrc == null) {
            waveSrc = context.getDrawable(R.mipmap.ic_launcher_round)
        }
        // 如果没有指定取默认值
        waveColor = typedArray.getColor(R.styleable.WaveButton_waveColor, DEFAULT_WAVE_COLOR)
        progressColor =
            typedArray.getColor(R.styleable.WaveButton_progressColor, DEFAULT_PROGRESS_COLOR)
        arcColors = intArrayOf(Color.WHITE, progressColor, Color.WHITE)
        // 资源回收
        typedArray.recycle()
        init()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        // xml 没有指定大小， 默认为bitmap宽度的两倍
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val width: Int
        val height: Int
        // View的宽高是bitmap宽度的两倍
        width = if (widthMode == MeasureSpec.EXACTLY) {
            widthSize
        } else {
            bitmapWidth * 2
        }
        height = if (heightMode == MeasureSpec.EXACTLY) {
            heightSize
        } else {
            bitmapHeight * 2
        }
        setMeasuredDimension(width, height)
    }


    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        // 控件宽高
        widgetWidth = width
        widgetHeight = height
        // 控件宽高的 1/2
        widgetWidthHalf = widgetWidth.toFloat() / 2
        widgetHeightHalf = widgetHeight.toFloat() / 2

        // cx：中心点x坐标； cy：中心点y坐标；
        // colors：颜色数组至少要两种颜色不可为null；
        // positions：可以是null。 颜色数组中每个对应颜色的相对位置，
        // 从0开始到1.0结束。如果这些值不是单调的，则绘图可能会产生意想不到的结果。
        // 如果位置为NULL，则颜色自动均匀分布。
        sweepGradient = SweepGradient(
            (widgetWidth / 2).toFloat(),
            (widgetWidth / 2).toFloat(), arcColors, null
        )
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        // 开启硬件加速
        setLayerType(LAYER_TYPE_SOFTWARE, wavePaint)
        setLayerType(LAYER_TYPE_SOFTWARE, bitmapPaint)
        setLayerType(LAYER_TYPE_SOFTWARE, progressPaint)
        val iterator = mWaveList.iterator()
        while (iterator.hasNext()) {
            val wave = iterator.next()
            if (System.currentTimeMillis() - wave.mCreateTime < DEFAULT_DURATION_TIME) {
                wavePaint?.alpha = wave.alpha
                canvas?.drawCircle(
                    widgetWidthHalf,
                    widgetHeightHalf,
                    wave.currentRadius,
                    wavePaint
                )
            } else {
                iterator.remove()
            }
        }

        canvas?.drawBitmap(mBitmap, ((widgetWidth - bitmapWidth) / 2).toFloat(),
            ( (widgetHeight - bitmapHeight) / 2).toFloat(), bitmapPaint)
        // 画布旋转， 默认0是时钟3点钟位置
//        canvas?.rotate(-90f, widgetWidthHalf, widgetHeightHalf)


        rectF.set(widgetWidthHalf - progressRadius, widgetHeightHalf - progressRadius,
            widgetWidthHalf + progressRadius, widgetHeightHalf + progressRadius)
        progressPaint?.shader = sweepGradient
        // 第一个参数圆弧的形状和轮廓，第二个参数开始的角度，
        // 第三个参数圆弧顺时针扫过的角度， 第四个参数是否经过圆心
        Log.d("123123123",progress.toString())
        canvas?.drawArc(rectF, 0f, progress, false, progressPaint)
        // 关闭硬件加速
        setLayerType(LAYER_TYPE_NONE, bitmapPaint)
        setLayerType(LAYER_TYPE_NONE, progressPaint)
        setLayerType(LAYER_TYPE_NONE, wavePaint)
    }

    private fun init(){
// 涟漪画笔
        wavePaint = Paint(Paint.ANTI_ALIAS_FLAG)
        wavePaint?.run {
            color = waveColor
        }
        // bitmap画笔
        bitmapPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        // 进度条画笔
        progressPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        progressPaint?.run {
            isDither = true
            style = Paint.Style.STROKE
            strokeCap = Paint.Cap.ROUND
            color = progressColor
            strokeWidth = 12f
        }
        // 获取屏幕大小
        val dm = resources.displayMetrics
        val screenHeight = dm.heightPixels

        // 创建bitmap
        val drawable = waveSrc as BitmapDrawable
        // 指定bitmap宽高
        mBitmap = Bitmap.createScaledBitmap(
            drawable.bitmap,
            screenHeight / 8, screenHeight / 8, true
        )
        // 获取bitmap宽高
        bitmapWidth = mBitmap?.width ?: 0
        bitmapHeight = mBitmap?.height ?: 0
        // 进度条半径
        progressRadius = ((bitmapWidth + 40) / 2).toFloat()

        // 水波纹最小半径icon * 0.8
        // 水波纹最大半径icon * 2
        mMinRadius = (bitmapWidth * 0.8 / 2).toFloat()
        mMaxRadius = bitmapWidth.toFloat()

        // 硬件加速
        setLayerType(LAYER_TYPE_HARDWARE, null)
        // 进度条的属性动画
        progressAnimator = ObjectAnimator.ofFloat(this, "progress", 0f, 360f).apply {
            addListener(object :AnimatorListenerAdapter(){
                override fun onAnimationEnd(animation: Animator?) {
                    super.onAnimationEnd(animation)
                    if (progress == 360f){
                        stop()
                        mProgressListener?.onEnd()
                    }
                }
            })
            duration = 5000
        }
        isClickable = true
    }




    override fun onTouchEvent(event: MotionEvent?): Boolean {
        val bitmapMargin = (widgetWidth - bitmapWidth) / 2
        when (event?.action) {
            // 点击暂停涟漪和进度条， 再点击继续播放
            MotionEvent.ACTION_DOWN ->{
                if (event.x >= bitmapMargin && event.x <= bitmapMargin + bitmapWidth
                        && event.y >= bitmapMargin && event.y <= bitmapMargin + bitmapHeight
                    )
                start()
            }
//                if (!isEnd)
//            // 判断点击位置是bitmap范围内
//
//                    // 判断是否正在运行， 运行就暂停， 暂停就继续
//                        if (isRunning()) {
//                            // 涟漪暂停
//                            stop()
//                            // 进度条动画暂停(注意pause()这个方法在4.4才有)
//                            progressAnimator.pause()
//                        } else {
//                            // 涟漪继续
//                            start()
//                            // 进度条动画继续(注意resume这个方法在4.4才有)
//                            progressAnimator.resume()
//                        }
            MotionEvent.ACTION_MOVE -> {
                if (!(event.x >= bitmapMargin && event.x <= bitmapMargin + bitmapWidth
                            && event.y >= bitmapMargin && event.y <= bitmapMargin + bitmapHeight)
                )
                    stop()
            }
            MotionEvent.ACTION_UP -> {
                stop()
            }
            else -> {
            }
        }
        return super.onTouchEvent(event)
    }

    private fun start() {
        if (!isRunning) {
            isRunning = true
            progressAnimator?.start()
            mCreateWave.run()
        }
    }
    /**
     * 暂停
     */
    private fun stop() {
        isRunning = false
        setLayerType(LAYER_TYPE_NONE, bitmapPaint)
        setLayerType(LAYER_TYPE_NONE, progressPaint)
        setLayerType(LAYER_TYPE_NONE, wavePaint)
        mWaveList.clear()
        progress = 0f
        progressAnimator?.cancel()
    }

    private inner class Wave {
        val mCreateTime: Long = System.currentTimeMillis()

        val alpha: Int
            get() {
                val percent =
                    (System.currentTimeMillis() - mCreateTime) * 1.0f / DEFAULT_DURATION_TIME
                return ((1.0f - mInterpolator.getInterpolation(percent)) * 255).toInt()
            }

        val currentRadius: Float
            get() {
                val percent =
                    (System.currentTimeMillis() - mCreateTime) * 1.0f / DEFAULT_DURATION_TIME
                return mMinRadius + mInterpolator.getInterpolation(percent) * (mMaxRadius - mMinRadius)
            }

    }

    private val mCreateWave = object : Runnable {
        override fun run() {
            if (isRunning) {
                createWave()
                postDelayed(this, DEFAULT_SPEED)
            }
        }
    }

    /**
     * 创建涟漪
     */
    private fun createWave() {
        val currentTime = System.currentTimeMillis()
        if (currentTime - mLastCreateTime < DEFAULT_SPEED) {
            return
        }
        val wave = Wave()
        mWaveList.add(wave)
        invalidate()
        mLastCreateTime = currentTime
    }

}