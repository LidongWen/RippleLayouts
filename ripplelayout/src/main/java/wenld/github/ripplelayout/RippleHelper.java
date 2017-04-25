package wenld.github.ripplelayout;

import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Build;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

/**
 * Created by wenld on 2017/4/25.
 */

public class RippleHelper implements View.OnTouchListener {

    private Context mContext;
    private View mView;
    private int mStatusBarHeight;

    private RippleLayout_one rippleLayout_one;
    private ViewGroup.LayoutParams rippleLayoutParams;

    private WindowManager mWs;
    private WindowManager.LayoutParams mWsParams;

    public RippleHelper(Context mContext, View view) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            //初始化数据
            this.mView = view;
            this.mContext = mContext;
            if (mStatusBarHeight == 0)
                mStatusBarHeight = getStatusBarHeight(mContext);
            //初始化 windowManager
            //初始化 windowManager参数
            mWs = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
            mWsParams = new WindowManager.LayoutParams();
            mWsParams.format = PixelFormat.TRANSLUCENT;
            mWsParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
            mWsParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
            mWsParams.gravity = Gravity.TOP | Gravity.LEFT;
            //初始化 rippleLayout_one 参数

            rippleLayout_one = new RippleLayout_one(mContext);
            rippleLayout_one.setiRippleLayoutListener(layoutListener);

            // 给 view设置ontouch
            mView.setOnTouchListener(this);
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        boolean superOnTouchEvent = view.onTouchEvent(event);

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:



                // 添加 rippleLayout到windows中
                mWs.addView(rippleLayout_one, mWsParams);
                rippleLayoutParams = rippleLayout_one.getLayoutParams();
                rippleLayoutParams.width = mView.getWidth();
                rippleLayoutParams.height = mView.getHeight();
                rippleLayout_one.setLayoutParams(rippleLayoutParams);

                int[] location = new int[2];
                mView.getLocationOnScreen(location);
                int x = location[0];
                int y = location[1];
                mWsParams.x = x;
                mWsParams.y = y - mStatusBarHeight;
                try {
                    mWs.updateViewLayout(rippleLayout_one, mWsParams);
                } catch (Exception e) {

                }
                //将ontouch传进去

                rippleLayout_one.onTouchEvent(event);

//                int x = (int) event.getX();
//                int y = (int) event.getY();
//                int rawX = (int) event.getRawX();
//                int rawY = (int) event.getRawY();
//
//                int longX = mView.getWidth();
//                int longY = mView.getHeight();
//
//                Point currentPoint = null;
//                if (currentPoint == null) {
//                    currentPoint = new Point();
//                }
//                currentPoint.set(x, y);
//                //left-top
//
//                int duration = MathUtils.getDistance(longX, longY, x, y);


//                    if (rippleAnimUtil == null) {
//                        rippleAnimUtil = new RippleAnimUtil(animatorUpdateListener, animatorListener);
//                    }
//                    rippleAnimUtil.palyAnim();
                break;
        }
        return true;
    }

    /**
     * 获取状态栏高度
     *
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

    RippleLayout_one.IRippleLayoutListener layoutListener = new RippleLayout_one.IRippleLayoutListener() {
        @Override
        public void onFinish() {
            if (mWs != null && rippleLayout_one.getParent() != null) {
                mWs.removeView(rippleLayout_one);
            }
        }
    };
}
