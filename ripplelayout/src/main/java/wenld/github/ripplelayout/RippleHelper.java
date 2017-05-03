package wenld.github.ripplelayout;

import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Build;
import android.support.annotation.ColorRes;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;

import static android.view.WindowManager.LayoutParams.TYPE_TOAST;

/**
 * Created by wenld on 2017/4/25.
 */

public class RippleHelper implements View.OnTouchListener {
    private String TAG = "RippleHelper";

    private Context mContext;
    private View mView;
    private int mStatusBarHeight;

    private RippleLayout rippleLayout;
    private ViewGroup.LayoutParams rippleLayoutParams;

    private WindowManager mWs;
    private WindowManager.LayoutParams mWsParams;

    ViewTreeObserver viewTreeObserver;
    public RippleHelper(Context mContext, View view) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            this.mView = view;
            this.mContext = mContext;
            if (mStatusBarHeight == 0)
                mStatusBarHeight = getStatusBarHeight(mContext);
            //
            mWs = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
            mWsParams = new WindowManager.LayoutParams();
            mWsParams.type=TYPE_TOAST;
            mWsParams.format = PixelFormat.TRANSLUCENT;
            mWsParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
            mWsParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
            mWsParams.gravity = Gravity.TOP | Gravity.LEFT;
            //

            rippleLayout = new RippleLayout(mContext);
            rippleLayout.setiRippleLayoutListener(layoutListener);

            //
            mView.setOnTouchListener(this);
            viewTreeObserver=mView.getViewTreeObserver();
            viewTreeObserver.addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
                @Override
                public void onScrollChanged() {
                    if(rippleLayout.getParent() != null){
                        int[] location = new int[2];
                        mView.getLocationOnScreen(location);
                        int x = location[0];
                        int y = location[1];
//                    Log.e(TAG, "x : " + x + " y :" + y + " mStatusBarHeight:" + mStatusBarHeight +
//                            "\n  rippleLayoutParams.width:" + mView.getWidth() +
//                            "\n  rippleLayoutParams.height:" + mView.getHeight() +
//                            "\n mView.getPaddingLeft():" + mView.getPaddingLeft() + "  mView.getPaddingTop()：" + mView.getPaddingTop() +
//                            "\n  mView.getPaddingRight():" + mView.getPaddingRight() + " mView.getPaddingBottom():" + mView.getPaddingBottom());
                        mWsParams.x = x;
                        mWsParams.y = y - mStatusBarHeight;
                        try {
                            mWs.updateViewLayout(rippleLayout, mWsParams);
                        } catch (Exception e) {

                        }
                    }
                }
            });
        } else {

        }
    }

    public RippleHelper setRippleColor(int rippleColor) {
        rippleLayout.setRippleColor(rippleColor);
        return this;
    }

    public RippleHelper setRippleBackground(int color) {
        rippleLayout.setRippleBackground(color);
        return this;
    }

    public RippleHelper setRippleColorResource(@ColorRes int colorRes) {

        rippleLayout.setRippleColor(mContext.getResources().getColor(colorRes));
        return this;
    }

    public RippleHelper setRippleBackgroundResource(@ColorRes int colorRes) {
        rippleLayout.setRippleBackground(mContext.getResources().getColor(colorRes));
        return this;
    }


    public RippleHelper setRipple_show_time(int ripple_show_time) {
        rippleLayout.setRipple_show_time(ripple_show_time);
        return this;
    }


    public RippleHelper setRipple_radius(int ripple_radius) {
        rippleLayout.setRipple_radius(ripple_radius);
        return this;
    }


    public RippleHelper setRipple_maxCount(int ripple_maxCount) {
        rippleLayout.setRipple_maxCount(ripple_maxCount);
        return this;
    }


    public RippleHelper setRipple_interval(int ripple_interval) {
        rippleLayout.setRipple_interval(ripple_interval);
        return this;
    }


    @Override
    public final boolean onTouch(View view, MotionEvent event) {
        boolean superOnTouchEvent = view.onTouchEvent(event);

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (rippleLayout.getParent() == null) {
                    // 添加 rippleLayout到windows中
                    mWs.addView(rippleLayout, mWsParams);
                    rippleLayoutParams = rippleLayout.getLayoutParams();
                    rippleLayoutParams.width = mView.getWidth();
                    rippleLayoutParams.height = mView.getHeight();
                    rippleLayout.setLayoutParams(rippleLayoutParams);
                    rippleLayout.setPadding(
                            DensityUtils.px2dip(mContext, mView.getPaddingLeft()),
                            DensityUtils.px2dip(mContext, mView.getPaddingTop()),
                            DensityUtils.px2dip(mContext, mView.getPaddingRight()),
                            DensityUtils.px2dip(mContext, mView.getPaddingBottom())
                    );

                    int[] location = new int[2];
                    mView.getLocationOnScreen(location);
                    int x = location[0];
                    int y = location[1];
//                    Log.e(TAG, "x : " + x + " y :" + y + " mStatusBarHeight:" + mStatusBarHeight +
//                            "\n  rippleLayoutParams.width:" + mView.getWidth() +
//                            "\n  rippleLayoutParams.height:" + mView.getHeight() +
//                            "\n mView.getPaddingLeft():" + mView.getPaddingLeft() + "  mView.getPaddingTop()：" + mView.getPaddingTop() +
//                            "\n  mView.getPaddingRight():" + mView.getPaddingRight() + " mView.getPaddingBottom():" + mView.getPaddingBottom());
                    mWsParams.x = x;
                    mWsParams.y = y - mStatusBarHeight;
                    try {
                        mWs.updateViewLayout(rippleLayout, mWsParams);
                    } catch (Exception e) {

                    }
                }
                rippleLayout.onTouchEvent(event);
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:

                rippleLayout.onTouchEvent(event);

                break;
            case MotionEvent.ACTION_CANCEL:
                layoutListener.onFinish();
                break;

        }
        return true;
    }

    /**
     * @return
     */
    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }

        return result;

    }

    RippleLayout.IRippleLayoutListener layoutListener = new RippleLayout.IRippleLayoutListener() {
        @Override
        public void onFinish() {
            if (mWs != null && rippleLayout.getParent() != null) {
                mWs.removeView(rippleLayout);
            }
        }
    };
}
