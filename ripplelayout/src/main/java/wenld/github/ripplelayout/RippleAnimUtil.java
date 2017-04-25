package wenld.github.ripplelayout;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.view.animation.DecelerateInterpolator;

/**
 * Created by wenld on 2017/4/25.
 */

public class RippleAnimUtil {
    private ValueAnimator rippleAnimator;
    private ValueAnimator.AnimatorUpdateListener animatorUpdateListener;
    private Animator.AnimatorListener animatorListener;

    public RippleAnimUtil(ValueAnimator.AnimatorUpdateListener animatorUpdateListener, Animator.AnimatorListener animatorListener) {
        this.animatorUpdateListener = animatorUpdateListener;
        this.animatorListener = animatorListener;
    }

    public void palyAnim() {
        if (animatorUpdateListener == null)
            return;
        if (animatorListener == null)
            return;
        if (rippleAnimator == null) {
            rippleAnimator = ValueAnimator.ofFloat(0, 1);
            rippleAnimator.setDuration(300);
            rippleAnimator.setInterpolator(new DecelerateInterpolator(2.0f));
            rippleAnimator.addUpdateListener(animatorUpdateListener);
            rippleAnimator.addListener(animatorListener);
        }
        if (rippleAnimator.isRunning())
            return;
        rippleAnimator.start();
    }

    public void cancel() {
        animatorUpdateListener = null;
        animatorListener = null;
        if (rippleAnimator != null && rippleAnimator.isRunning()) {
            rippleAnimator.cancel();
        }
        rippleAnimator = null;
    }

    public boolean isRunning() {
        if (rippleAnimator == null)
            return false;
        return rippleAnimator.isRunning();
    }
}
