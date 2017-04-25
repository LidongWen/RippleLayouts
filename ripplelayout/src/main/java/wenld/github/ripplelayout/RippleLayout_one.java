package wenld.github.ripplelayout;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;


/**
 * Created by wenld on 2017/4/25.
 */

public class RippleLayout_one extends CustomView {
    private static final int DEFAULT_COLOR = Color.GREEN;
    private static final int DEFAULT_BACKGROUND = Color.RED;


    private Point currentPoint;   //当前触摸点
    private float duration;

    private Paint paint;
    private final Rect bounds = new Rect();

    private int rippleColor;
    private ColorDrawable rippleBackground;


    private boolean isRun = false;
    RippleAnimUtil rippleAnimUtil;

    private int curRadius = 0;


    IRippleLayoutListener iRippleLayoutListener;

    public RippleLayout_one(Context context) {
        super(context);
    }

    public RippleLayout_one(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void initAttrs(AttributeSet attributeSet) {

    }

    @Override
    public void initValue() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);

        rippleColor = DEFAULT_COLOR;
        rippleBackground = new ColorDrawable(DEFAULT_BACKGROUND);

        paint.setColor(rippleColor);
    }

    @Override
    public void sizeChangesd() {

    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (rippleAnimUtil != null) {
            rippleAnimUtil.cancel();
        }
        rippleAnimUtil = null;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (isRun) {
            //画背景
            rippleBackground.draw(canvas);
            canvas.drawCircle(currentPoint.x, currentPoint.y, curRadius, paint);
        }

//        super.onDraw(canvas);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        bounds.set(0, 0, w, h);
        rippleBackground.setBounds(bounds);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                int x = (int) event.getX();
                int y = (int) event.getY();

                int longX;
                int longY;
                if (currentPoint == null) {
                    currentPoint = new Point();
                }
                currentPoint.set(x, y);
                //left-top
                if (mCenterX < x && mCenterY < y) {
                    longX = 0;
                    longY = 0;
                }
                //right-top
                else if (mCenterX > x && mCenterY < y) {
                    longX = mWidth;
                    longY = 0;
                }// right-bottom
                else if (mCenterX > x && mCenterY > y) {
                    longX = mWidth;
                    longY = mHeight;
                } else {
                    longX = 0;
                    longY = mHeight;
                }
                duration = MathUtils.getDistance(longX, longY, x, y);
                if (rippleAnimUtil == null) {
                    rippleAnimUtil = new RippleAnimUtil(animatorUpdateListener, animatorListener);
                }
                rippleAnimUtil.palyAnim();
                break;
        }
        return true;
    }

    public void setRippleColor(int rippleColor) {
        this.rippleColor = rippleColor;
        paint.setColor(rippleColor);
//        paint.setAlpha(rippleAlpha);
        invalidate();
    }

    public void setRippleBackground(int color) {
        rippleBackground = new ColorDrawable(color);
        rippleBackground.setBounds(bounds);
        invalidate();
    }


    float currentValue;
    ValueAnimator.AnimatorUpdateListener animatorUpdateListener = new ValueAnimator.AnimatorUpdateListener() {
        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            currentValue = (float) animation.getAnimatedValue();
            curRadius = (int) (currentValue * duration);
            invalidate();
        }
    };
    Animator.AnimatorListener animatorListener = new Animator.AnimatorListener() {
        @Override
        public void onAnimationStart(Animator animation) {
            isRun = true;
        }

        @Override
        public void onAnimationEnd(Animator animation) {
//            isRun = false;
//            invalidate();
//            if (iRippleLayoutListener != null)
//                iRippleLayoutListener.onFinish();
        }

        @Override
        public void onAnimationCancel(Animator animation) {
//            isRun = false;
//            invalidate();
//            if (iRippleLayoutListener != null)
//                iRippleLayoutListener.onFinish();
        }

        @Override
        public void onAnimationRepeat(Animator animation) {

        }
    };
    ValueAnimator.AnimatorUpdateListener aphlaAULis = new ValueAnimator.AnimatorUpdateListener() {
        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            currentValue = (float) animation.getAnimatedValue();
            paint.setAlpha((int) (currentValue * 255));
            invalidate();
        }
    };
    Animator.AnimatorListener aphlaAinListener = new Animator.AnimatorListener() {
        @Override
        public void onAnimationStart(Animator animation) {
            isRun = true;
        }

        @Override
        public void onAnimationEnd(Animator animation) {
            isRun = false;
            invalidate();
            if (iRippleLayoutListener != null)
                iRippleLayoutListener.onFinish();
            paint.setAlpha(255);
        }

        @Override
        public void onAnimationCancel(Animator animation) {
            isRun = false;
            invalidate();
            if (iRippleLayoutListener != null)
                iRippleLayoutListener.onFinish();
        }

        @Override
        public void onAnimationRepeat(Animator animation) {

        }
    };


    public IRippleLayoutListener getiRippleLayoutListener() {
        return iRippleLayoutListener;
    }

    public void setiRippleLayoutListener(IRippleLayoutListener iRippleLayoutListener) {
        this.iRippleLayoutListener = iRippleLayoutListener;
    }

    public interface IRippleLayoutListener {
        void onFinish();
    }
}
