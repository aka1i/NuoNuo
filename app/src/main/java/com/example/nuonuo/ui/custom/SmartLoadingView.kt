package com.example.nuonuo.ui.custom

import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.graphics.PathMeasure

import android.graphics.DashPathEffect
import android.view.animation.LinearInterpolator
import androidx.core.animation.addListener
import com.example.nuonuo.R


class SmartLoadingView:View {

    interface MyListener{
        fun onSuccess()

        fun onFailed()
    }

    companion object{
        const val TAG = "SmartLoadingView"

        const val STATE_NORMAL = 1

        const val STATE_NARROWING = 2

        const val STATE_ENLARGING = 4

        const val STATE_LOGING = 6

        const val STATE_OK_DRAWING = 7

        const val DATA_NOT = 0

        const val DATA_OK = 1

        const val DATA_FAILED = 2
    }
    // 按钮的文字
    private var buttonString = ""
    // 正常背景颜色
    private var normalBg:Int
    // 错误按钮文字
    private var errorStr = ""
    // 错误背景颜色
    private var errorBg:Int
    // 圆角大小
    private var cornerRaius:Float
    // 文本颜色
    private var textColor:Int
    // 文本大小
    private var textSize = 0f
    // 滚动速度
    private var scrollSpeed = 500L
    // 高
    private var viewHeight = 0f
    // 宽
    private var viewWidth  = 0f

    private val textPath = Path()
    private val okPath = Path()
    private lateinit var okPathMeasure: PathMeasure
    private val rectf = RectF()
    private val textRectF = RectF()
    // 正常文字画笔
    private val textPaint = Paint()
    // 正常画笔
    private val paint = Paint()
    // OK画笔
    private val okPaint = Paint()
    // 当前状态
    private var nowState = STATE_NORMAL
    // 当前数据状态
    private var nowDataState = DATA_NOT
    var myListener: MyListener? = null

    private lateinit var effect: DashPathEffect
    private var currentLeft = 0f
    private var circleAngle = 100f
    private var scrollSize = 0f
    private var startAngle = 0f
    private var progAngle = 270f
    private var isShowLongText = false
    private lateinit var animator_rect_to_square:ValueAnimator
    private lateinit var animator_square_to_rect:ValueAnimator
    private lateinit var animator_angle_to_rect:ValueAnimator
    private lateinit var animator_draw_ok:ValueAnimator
    private  var animator_text_scroll:ValueAnimator? = null
    // 缩放距离
    private  var default_all_distance = 0f
    // 缩放动画时长
    private var duration = 1000L
    private var isAdd = false

    constructor (context: Context?) : this(context, null)

    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr){
        val typedArray = context!!.obtainStyledAttributes(attrs, R.styleable.SmartLoadingView)
        val title = typedArray.getString(R.styleable.SmartLoadingView_textStr)
        val error = typedArray.getString(R.styleable.SmartLoadingView_errorStr)
        errorBg = typedArray.getColor(R.styleable.SmartLoadingView_errorBg,Color.RED)
        normalBg = typedArray.getColor(R.styleable.SmartLoadingView_normalBg,Color.GREEN)
        textColor = typedArray.getColor(R.styleable.SmartLoadingView_textColor,Color.WHITE)
        cornerRaius = typedArray.getDimension(R.styleable.SmartLoadingView_cornerRaius,0f)

        buttonString = if (title.isNullOrEmpty()) "" else title

        errorStr = if (error.isNullOrEmpty()) "" else error

        textSize = typedArray.getDimension(R.styleable.SmartLoadingView_textSize,14f)

        typedArray.recycle()

        paint.color = normalBg
        textPaint.textSize = textSize
        textPaint.color = textColor
    }



    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        viewHeight = measureHeight(heightMeasureSpec)
        viewWidth = measureWidth(widthMeasureSpec)
        default_all_distance = (viewWidth - viewHeight) / 2
        setMeasuredDimension(viewWidth.toInt(),viewHeight.toInt())
        Log.d(TAG , "viewHeight:$viewHeight")
        Log.d(TAG,"viewWidth:$viewWidth")
        Log.d(TAG , "textSize:$textSize")
    }

    private fun measureWidth(widthMeasureSpec: Int):Float{
        var  result:Float
        val specMode = MeasureSpec.getMode(widthMeasureSpec)
        val specSize = MeasureSpec.getSize(widthMeasureSpec).toFloat()
        if (specMode == MeasureSpec.EXACTLY){
            result = specSize
        }else{
            result = buttonString.length * textSize + viewHeight * 2 / 3
            if(specMode == MeasureSpec.AT_MOST){
                result = result.coerceAtMost(specSize)
            }
        }
        return result
    }
    private fun measureHeight(heightMeasureSpec: Int):Float{
        var  result:Float
        val specMode = MeasureSpec.getMode(heightMeasureSpec)
        val specSize = MeasureSpec.getSize(heightMeasureSpec).toFloat()
        if (specMode == MeasureSpec.EXACTLY){
            result = specSize
        }else{
            result = textSize * 7 / 3
            if(specMode == MeasureSpec.AT_MOST){
                result = result.coerceAtMost(specSize)
            }
        }
        return result
    }


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        rectf.left = currentLeft
        rectf.top = 0F
        rectf.right = (viewWidth - currentLeft)
        rectf.bottom = viewHeight
        canvas?.drawRoundRect(rectf, cornerRaius, cornerRaius, paint)

        when(nowState){
            STATE_NORMAL ->{
                drawText(canvas)
            }
            STATE_LOGING ->{
                drawLoading(canvas)
            }
            STATE_OK_DRAWING ->{
                drawOk(canvas)
            }
        }
    }

    private fun drawOk(canvas: Canvas?){
        canvas?.drawPath(okPath, okPaint)
    }


    private fun drawLoading(canvas: Canvas?){
        canvas?.drawArc(
            RectF(viewWidth / 2 - height / 2 + height / 4,
            viewHeight / 4,
            viewWidth / 2 + height / 2 - height / 4,
            viewHeight / 2 + height / 2 - height / 4),
            startAngle,
            progAngle,
            false,
            okPaint)
        startAngle += 6
        if (progAngle >= 270) {
            progAngle -= 2
            isAdd = false
        } else if (progAngle <= 45) {
            progAngle += 6
            isAdd = true
        } else {
            if (isAdd) {
                progAngle += 6
            } else {
                progAngle -= 2
            }
        }
        postInvalidate()
    }

    private fun drawText(canvas: Canvas?){
        textRectF.left = 0f
        textRectF.top = 0f
        textRectF.right = viewWidth
        textRectF.bottom = viewHeight
        val fontMetrics = textPaint.fontMetricsInt
//这里是获取文字绘制的y轴位置，可以理解上下居中
        val baseline = (textRectF.bottom + textRectF.top - fontMetrics.bottom - fontMetrics.top) / 2
//这里判断文字长度是否大于控件长度，当然我控件2边需要留文字的间距，所以不是大于width，这么说只是更好的理解
//这里是当文字内容大于控件长度，启动回滚效果。建议先看下面else里的正常情况
        Log.d(TAG,"drawText()文字长度" + (buttonString.length * textSize))
        Log.d(TAG,"drawText()文字可绘制长度" + (viewWidth - viewHeight * 2 / 3))
        if ((buttonString.length * textSize) > (viewWidth - viewHeight * 2 / 3)) {
            textPath.reset()
            //因为要留2遍间距，以heigh/3为间距
            textPath.moveTo(viewHeight / 3f, baseline)
            textPath.lineTo(viewWidth - viewHeight / 3f, baseline)
            //这里的意思是文字从哪里开始写，可以是居中，这里是右边
            textPaint.textAlign = Paint.Align.CENTER
            //这里是以路径绘制文字，scrollSize可以理解为文字在x轴上的便宜量，同时，我的混动效果就是通过改变scrollSize
            //刷新绘制来实现
            canvas!!.drawTextOnPath(buttonString, textPath, scrollSize, 0f, textPaint)
            if (isShowLongText) {
                //这里是绘制遮挡物，因为绘制路径没有间距这方法，所以绘制遮挡物类似于间距方式
                canvas.drawRect(Rect((viewWidth - viewHeight / 2 - textSize / 3).toInt(), 0, width - height / 2, height),paint)
                canvas.drawRect(Rect(viewHeight.toInt() / 2, 0, (viewHeight / 2 + textSize / 3).toInt(), height), paint)
                //这里有个bug 有个小点-5  因画笔粗细产生
                canvas.drawArc(RectF(viewWidth - viewHeight, 0f, viewWidth - 5.toFloat(), viewHeight), -90f, 180f, true, paint)
                canvas.drawArc(RectF(0f, 0f, viewHeight, viewHeight), 90f, 180f, true, paint)
            }

            if (animator_text_scroll == null) {
                //这里是计算混到最右边和最左边的距离范围
                animator_text_scroll = ValueAnimator.ofFloat(buttonString.length * textSize - viewWidth + viewHeight * 2 / 3,-textSize)
                //这里是动画的时间，scrollSpeed可以理解为每个文字滚动控件外所需的时间，可以做成控件属性提供出去
                animator_text_scroll?.duration = buttonString.length * scrollSpeed
                //设置动画的模式，这里是来回滚动
                animator_text_scroll?.repeatMode = ValueAnimator.REVERSE
                //设置插值器，让整个动画流畅
                animator_text_scroll?.interpolator = LinearInterpolator()
                //这里是滚动次数，-1无限滚动
                animator_text_scroll?.repeatCount = -1
                animator_text_scroll?.addUpdateListener{ animation ->
                        //改变文字路径x轴的偏移量
                        scrollSize = animation.animatedValue as Float
                        postInvalidate()

                }
                animator_text_scroll?.start()
            }
        } else {
            //这里是正常情况，isShowLongText，是我在启动控件动画的时候，是否启动 文字有渐变效果的标识，
            //如果是长文字，启动渐变效果的话，如果控件变小，文字内容在当前控件外，会显得很难看，所以根据这个标识，关闭，这里你可以先忽略（同时因为根据路径绘制text不能有间距效果，这个标识还是判断是否在控件2遍绘制遮挡物，这是作者的解决方式，如果你有更好的方式可以在下方留言）
            isShowLongText = false
            /**
             * 简单的绘制文字，没有考虑文字长度超过控件长度
             * */
            //这里是居中显示
            textPaint.textAlign = Paint.Align.CENTER
            //参数1：文字
            //参数2,3：绘制文字的中心点
            //参数4：画笔
            canvas?.drawText(buttonString, textRectF.centerX(), baseline, textPaint)
        }
    }




    private fun initOk() {
        okPaint.style = Paint.Style.STROKE
        okPaint.color  = textColor
        okPaint.strokeWidth = 5f
        //对勾的路径
        okPath.moveTo(default_all_distance + viewHeight / 8 * 3, viewHeight / 2)
        okPath.lineTo(default_all_distance + viewHeight / 2, viewHeight / 5 * 3)
        okPath.lineTo(default_all_distance + viewHeight / 3 * 2, viewHeight / 5 * 2)
        okPathMeasure = PathMeasure(okPath, true)
    }



    private fun initAngle2RectAnimation(){
        animator_angle_to_rect = ValueAnimator.ofInt(height / 2, cornerRaius.toInt())
        animator_angle_to_rect.duration = duration
        animator_angle_to_rect.addUpdateListener{animation ->
            circleAngle =  (animation.animatedValue as Int).toFloat()
//            刷新绘画
//            if (circleAngle == cornerRaius)
//                isLoading = true
            invalidate()
        }
    }

    private fun initRect2circleAnimation(){
        animator_rect_to_square = ValueAnimator.ofFloat(0f, default_all_distance)
        animator_rect_to_square.duration = duration
        animator_rect_to_square.addUpdateListener{ animation ->
            //这里的current_left跟onDraw相关，还记得吗
            //onDraw里的控件区域
            //控件左边区域 rectf.left = current_left;
            //控件右边区域 rectf.right = width - current_left;
            currentLeft = animation.animatedValue as Float
            //刷新绘制
            invalidate()
        }
    }

    private fun initCircle2RectAnimation(){
        animator_square_to_rect = ValueAnimator.ofFloat(default_all_distance, 0f)
        animator_square_to_rect.duration = duration
        animator_square_to_rect.addUpdateListener{ animation ->
            //这里的current_left跟onDraw相关，还记得吗
            //onDraw里的控件区域
            //控件左边区域 rectf.left = current_left;
            //控件右边区域 rectf.right = width - current_left;
            currentLeft = animation.animatedValue as Float
            if (currentLeft == 0f)
                isClickable = true
            //刷新绘制
            invalidate()
        }
    }


    private fun initDrawOKAnimation() {
        animator_draw_ok = ValueAnimator.ofFloat(1f, 0f)
        animator_draw_ok.duration = duration
        animator_draw_ok.addUpdateListener{ animation ->
            val value = animation.animatedValue as Float
            effect = DashPathEffect(
                floatArrayOf(okPathMeasure.length, okPathMeasure.length),
                value * okPathMeasure.length
            )
            okPaint.pathEffect = effect
            invalidate()
            if (value == 0f)
                myListener?.onSuccess()
        }
    }

     fun start(){
        if(nowState != STATE_NORMAL)
            return
        nowState = STATE_NARROWING
        paint.color = normalBg
        isClickable = false
        initRect2circleAnimation()
        initAngle2RectAnimation()
        initCircle2RectAnimation()
        initOk()
        initDrawOKAnimation()
        val animatorSet = AnimatorSet()
        animatorSet.play(animator_rect_to_square)
        animatorSet.start()
        animatorSet.addListener(
            {
                when(nowDataState){
                    DATA_NOT ->{
                        nowState = STATE_LOGING
                    }
                    DATA_OK ->{
                        ok()
                    }
                    DATA_FAILED->{
                        drawFailed()
                    }
                }
            }
        )
    }

    private fun drawFailed(){
        paint.color = errorBg
        buttonString = errorStr
        nowState = STATE_ENLARGING
        val animatorSet = AnimatorSet()
        animatorSet.play(animator_square_to_rect)
        animatorSet.start()
        animatorSet.addListener({
            nowState = STATE_NORMAL
            isClickable = true
            myListener?.onFailed()
        })
    }

    private fun ok(){
        nowState = STATE_OK_DRAWING
        animator_draw_ok.start()
    }

    fun success(){
        nowDataState = DATA_OK
        if (nowState == STATE_LOGING)
            ok()
    }

    fun failed(){
        nowDataState = DATA_FAILED
        if (nowState == STATE_LOGING)
            drawFailed()
    }
}