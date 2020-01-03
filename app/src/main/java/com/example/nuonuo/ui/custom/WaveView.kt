package com.example.nuonuo.ui.custom

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator
import com.example.nuonuo.R
import kotlin.math.sin

/**
@author yjn
@create 2020/1/3 - 18:28
 */
class WaveView: View {

    companion object{
        const val TAG = "WaveView"
    }

    private val path = Path()

    private var A:Int

    private var omiga:Float

    private var fi:Float

    private var changeFi: Float

    private var K:Int

    private var bg_color:Int

    private var viewWidth = 0f

    private var viewHeight = 0f

    private val paint = Paint()

    private var valueAnimator:ValueAnimator? = null

    constructor (context: Context?) : this(context, null)

    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr){
        val typedArray = context!!.obtainStyledAttributes(attrs, R.styleable.WaveView)
        bg_color = typedArray.getColor(R.styleable.WaveView_bg_color,Color.BLUE)
        A = typedArray.getInt(R.styleable.WaveView_A,15)
        K = typedArray.getInt(R.styleable.WaveView_K,0)
        omiga = typedArray.getFloat(R.styleable.WaveView_omiga,0.015f)
        fi = typedArray.getFloat(R.styleable.WaveView_fi,0f)
        changeFi = typedArray.getFloat(R.styleable.WaveView_change_fi,0.03f)
        typedArray.recycle()
        paint.color = bg_color
        initAnimation()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        viewWidth = measureWidth(widthMeasureSpec)
        viewHeight = measureHeight(heightMeasureSpec)

    }

    private fun measureWidth(widthMeasureSpec: Int):Float{
        var  result:Float
        val specMode = MeasureSpec.getMode(widthMeasureSpec)
        val specSize = MeasureSpec.getSize(widthMeasureSpec).toFloat()
        result = specSize
        return result
    }
    private fun measureHeight(heightMeasureSpec: Int):Float{
        var  result:Float
        val specMode = MeasureSpec.getMode(heightMeasureSpec)
        val specSize = MeasureSpec.getSize(heightMeasureSpec).toFloat()
        result = specSize
        return result
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        drawSin(canvas)
    }

    private fun drawSin(canvas: Canvas?){
        fi += changeFi
        var y:Float
        path.reset()
        path.moveTo(0f,viewHeight)
        for (x in 0..viewWidth.toInt() step 20){
            y = (A * sin(omiga * x + fi) + K)
            path.lineTo(x.toFloat(),viewHeight - y - A)
        }
        path.lineTo(viewWidth,0f)
        path.lineTo(0f,0f)
        path.close()
        canvas?.drawPath(path,paint)
    }

    private fun initAnimation(){
        valueAnimator = ValueAnimator.ofFloat(0f,2 * Math.PI.toFloat())
        valueAnimator?.apply {
            duration = 1000
            repeatCount = ValueAnimator.INFINITE
            interpolator = LinearInterpolator()
            addUpdateListener {
                invalidate()
            }
            start()
        }
    }

}