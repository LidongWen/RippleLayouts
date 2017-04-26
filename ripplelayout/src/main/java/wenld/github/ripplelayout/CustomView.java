package wenld.github.ripplelayout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;


/**
 * Created by wenld on 2017/4/25.
 */

public abstract class CustomView  extends View {
    //画布大小
    protected int mWidth;
    protected int mHeight;

    //View默认最小宽度
    protected int DEFAULT_MIN_WIDTH = 200;

    //中心点 坐标
    protected int mCenterX;
    protected int mCenterY;

    //园半径，如非园型 请忽略此属性
    protected float circleR;

    public CustomView(Context context) {
        this(context, null);
    }

    public CustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttrs(attrs);
        initValue();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        mWidth = measure(widthMeasureSpec);
        mHeight = measure(heightMeasureSpec);
        calcuateRadius();
        setMeasuredDimension(mWidth, mHeight);
        sizeChangesd();
    }

    protected void calcuateRadius() {
        mCenterX = (mWidth - getPaddingLeft() - getPaddingRight()) / 2 + getPaddingLeft();
        mCenterY = (mHeight - getPaddingTop() - getPaddingBottom()) / 2 + getPaddingTop();

        circleR = Math.min(mWidth - getPaddingLeft() - getPaddingRight(), mHeight - getPaddingTop() - getPaddingBottom()) / 2;
    }

    private int measure(int origin) {
        int result = DensityUtils.dip2px(getContext(), DEFAULT_MIN_WIDTH);
        int specMode = MeasureSpec.getMode(origin);
        int specSize = MeasureSpec.getSize(origin);
        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else {
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.max(result, specSize);
            }
        }
        return result;
    }

    public abstract void initAttrs(AttributeSet attrs);


    /**
     * 初始化 画笔之类的东西
     */
    public abstract void initValue();

    /**
     * 布局大小改变
     */
    public abstract void sizeChangesd();

    public void setDefaultWidth(int DEFAULT_MIN_WIDTH) {
        this.DEFAULT_MIN_WIDTH = DEFAULT_MIN_WIDTH;
    }
}