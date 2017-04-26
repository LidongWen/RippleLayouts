package wenld.github.ripplelayout;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.ColorInt;
import android.util.AttributeSet;
import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.Iterator;


/**
 * Created by wenld on 2017/4/25.
 */

public class RippleLayout extends CustomView {
    private static final int DEFAULT_COLOR = 0xFFFF4081;
    private static final int DEFAULT_BACKGROUND = 0x55FF4081;
    private static final long ANIM_TIME = 300;
    private static final long ONCLICK_INTERVAL = 60;

    private ArrayList<OnclickInfo> onClickTimes = new ArrayList<>(5);


    private Paint paint;
    private final Rect bounds = new Rect();

    private int rippleColor;
    private int backgroundColor = -1;
    private int ripple_show_time;
    private int ripple_radius;
    private int ripple_maxCount;
    private int ripple_interval;


    private ColorDrawable rippleBackground;


    private boolean isDown = false;
    private boolean isRun = false;
    private boolean isStopping = false;
    RippleAnimUtil rippleAnimUtil;

    IRippleLayoutListener iRippleLayoutListener;

    public RippleLayout(Context context) {
        super(context);
    }

    public RippleLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void initAttrs(AttributeSet attributeSet) {
        TypedArray ta = getContext().obtainStyledAttributes(attributeSet, R.styleable.RippleLayout);

        rippleColor = ta.getColor(R.styleable.RippleLayout_ripple_color, DEFAULT_COLOR);
        backgroundColor = ta.getInt(R.styleable.RippleLayout_ripple_background, DEFAULT_BACKGROUND);
        ripple_show_time = ta.getInteger(R.styleable.RippleLayout_ripple_show_time, (int) ANIM_TIME);
        ripple_interval = ta.getColor(R.styleable.RippleLayout_ripple_interval, (int) ONCLICK_INTERVAL);
        ripple_radius = ta.getDimensionPixelOffset(R.styleable.RippleLayout_ripple_radius, DensityUtils.dip2px(getContext(), 100));
        ripple_maxCount = ta.getInteger(R.styleable.RippleLayout_ripple_maxCount, 5);

        ta.recycle();
    }

    @Override
    public void initValue() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);

        rippleBackground = new ColorDrawable();
        rippleBackground.setColor(backgroundColor);

        paint.setColor(rippleColor);
    }

    @Override
    public void sizeChangesd() {
        bounds.set(getPaddingLeft(), getPaddingTop(), mWidth - getPaddingRight(), mHeight - getPaddingRight());
        rippleBackground.setBounds(bounds);
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
            rippleBackground.draw(canvas);
            canvas.save(Canvas.CLIP_SAVE_FLAG);
            Iterator iter = onClickTimes.iterator();
            canvas.clipRect(bounds);
            OnclickInfo onclickInfo;
            while (iter.hasNext()) {
                onclickInfo = (OnclickInfo) iter.next();
                long dTime = System.currentTimeMillis() - onclickInfo.startTime;
                if (ripple_show_time <= dTime)
                    break;
                int alpha = (int) (255 - 255 * dTime / ripple_show_time);
                alpha = (alpha < 30) ? 30 : alpha;
                paint.setAlpha(alpha);
                canvas.drawCircle(onclickInfo.startPoint.x, onclickInfo.startPoint.y, ripple_radius * (System.currentTimeMillis() - onclickInfo.startTime) / ripple_show_time, paint);
            }
            canvas.restore();
        } else if (isStopping) {
            rippleBackground.setAlpha(paint.getAlpha());
            rippleBackground.draw(canvas);
        }
        if (isDown) {
            rippleBackground.draw(canvas);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                isDown = true;
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                isDown = false;
                if (isStopping) {
                    break;
                }
                if ((onClickTimes.size() > 0) && (System.currentTimeMillis() - onClickTimes.get(0).startTime < ripple_interval)) {
                    break;
                }

                int x = (int) event.getX();
                int y = (int) event.getY();

                onClickTimes.add(0, new OnclickInfo(System.currentTimeMillis(), new Point(x, y)));
                if (onClickTimes.size() > ripple_maxCount) {
                    for (int i = onClickTimes.size() - 1; i > 4; i--) {
                        onClickTimes.remove(i);
                    }
                }
                if (rippleAnimUtil == null) {
                    rippleAnimUtil = new RippleAnimUtil(animatorUpdateListener, animatorListener);
                }
                rippleAnimUtil.setDuration(ANIM_TIME).palyAnim();
                break;
        }
        return true;
    }

    public void setRippleColor(@ColorInt int rippleColor) {
        this.rippleColor = rippleColor;
        paint.setColor(rippleColor);
//        paint.setAlpha(rippleAlpha);
        invalidate();
    }

    public void setRippleBackground(@ColorInt int color) {
        backgroundColor = color;
        rippleBackground.setColor(backgroundColor);
//        rippleBackground.setBounds(bounds);
        invalidate();
    }

    public int getRipple_show_time() {
        return ripple_show_time;
    }

    public void setRipple_show_time(int ripple_show_time) {
        this.ripple_show_time = ripple_show_time;
    }

    public int getRipple_radius() {
        return ripple_radius;
    }

    public void setRipple_radius(int ripple_radius) {
        this.ripple_radius = ripple_radius;
    }

    public int getRipple_maxCount() {
        return ripple_maxCount;
    }

    public void setRipple_maxCount(int ripple_maxCount) {
        this.ripple_maxCount = ripple_maxCount;
    }

    public int getRipple_interval() {
        return ripple_interval;
    }

    public void setRipple_interval(int ripple_interval) {
        this.ripple_interval = ripple_interval;
    }


    public ColorDrawable getRippleBackground() {
        return rippleBackground;
    }

    public void setRippleBackground(ColorDrawable rippleBackground) {
        this.rippleBackground = rippleBackground;
    }

    ValueAnimator.AnimatorUpdateListener animatorUpdateListener = new ValueAnimator.AnimatorUpdateListener() {
        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
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
            isRun = false;
            isStopping = true;

            ValueAnimator rippleAnimator = ValueAnimator.ofFloat(1, 0);
            rippleAnimator.setDuration(ripple_show_time / 2);
//            rippleAnimator.setInterpolator(new DecelerateInterpolator(2.0f));
            rippleAnimator.addUpdateListener(aphlaAULis);
            rippleAnimator.addListener(aphlaAinListener);
            rippleAnimator.start();
        }

        @Override
        public void onAnimationCancel(Animator animation) {
            back();
        }

        @Override
        public void onAnimationRepeat(Animator animation) {

        }
    };

    ValueAnimator.AnimatorUpdateListener aphlaAULis = new ValueAnimator.AnimatorUpdateListener() {
        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
//            currentValue = (float) animation.getAnimatedValue();
            paint.setAlpha((int) ((float) animation.getAnimatedValue() * 255));
            invalidate();
        }
    };
    Animator.AnimatorListener aphlaAinListener = new Animator.AnimatorListener() {
        @Override
        public void onAnimationStart(Animator animation) {
            isStopping = true;
        }

        @Override
        public void onAnimationEnd(Animator animation) {
            back();
        }

        @Override
        public void onAnimationCancel(Animator animation) {
            back();
        }

        @Override
        public void onAnimationRepeat(Animator animation) {

        }
    };

    private void back() {
        isRun = false;
        isStopping = false;
        invalidate();

//        if (backgroundColor != -1) {
//            rippleBackground.setColor(backgroundColor);
//        } else {
//            rippleBackground.setColor(DEFAULT_BACKGROUND);
//        }
        rippleBackground.setAlpha(255);

        if (iRippleLayoutListener != null)
            iRippleLayoutListener.onFinish();
        paint.setAlpha(255);
    }

    public void setiRippleLayoutListener(IRippleLayoutListener iRippleLayoutListener) {
        this.iRippleLayoutListener = iRippleLayoutListener;
    }

    public interface IRippleLayoutListener {
        void onFinish();
    }

    static class OnclickInfo {
        public Long startTime;
        public Point startPoint;

        public OnclickInfo(Long startTime, Point startPoint) {
            this.startTime = startTime;
            this.startPoint = startPoint;
        }
    }
}
