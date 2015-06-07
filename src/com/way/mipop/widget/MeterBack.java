package com.way.mipop.widget;

import android.app.Instrumentation;
import android.content.Context;
import android.content.res.Configuration;
import android.view.MotionEvent;

import com.way.mipop.AppLog;
import com.way.mipop.R;
import com.way.mipop.animation.AnimationParking;

public class MeterBack extends MeterBase {
	public static final String NAME = MeterBack.class.getSimpleName();
	private int changeX = 0;
	private int changeY = 0;
	private boolean hasMoved = false;
	private int mTouchStartX = 0;
	private int mTouchStartY = 0;

	public MeterBack(Context context) {
		super(context);
		Register(NAME, this);
		setSoundEffectsEnabled(true);
		setImageResource(R.drawable.back_selector);
		setResId(R.drawable.back, R.drawable.back_pressed);
	}

	public void Click() {
		AppLog.i("way", "back click");
		playSoundEffect(0);
		new Thread() {
			public void run() {
				try {
					new Instrumentation().sendKeyDownUpSync(4);
					AppLog.i("shenzhan", "Back implement");
				} catch (Exception e) {
					AppLog.d("shenzhan", e.toString());
				}
			}
		}.start();
	}

	public void LongClick() {
		AppLog.i("way", "back  long click");
	}

	protected void onConfigurationChanged(Configuration configuration) {
		super.onConfigurationChanged(configuration);
		Until.initialPop(getContext());
		AnimationParking.land();
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int rawX = (int) event.getRawX();
		int rawY = (int) event.getRawY() - Until.STATUS_HEIGHT;
		//if ((Math.abs(rawX - mTouchStartX) <= Until.MOVE_MAX_SIZE)
		//		&& (Math.abs(rawY - mTouchStartY) <= Until.MOVE_MAX_SIZE))
		//	return super.onTouchEvent(event);
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			AppLog.i("OUT", "back ACTION_DOWN" + this.hasMoved);
			this.changeX = rawX;
			this.changeY = rawY;
			this.mTouchStartX = rawX;
			this.mTouchStartY = rawY;
			break;
		case MotionEvent.ACTION_MOVE:
			AppLog.i("OUT", "back ACTION_OUTSIDE");
			int offsetX = rawX - changeX;
			int offsetY = rawY - changeY;
			if ((Math.abs(offsetX) > 3) || (Math.abs(offsetY) > 3)) {
				//AppLog.i("way", "baseX/offsetX = " + baseX + "/" + offsetX);
				baseX = offsetX + baseX;
				baseY = offsetY + baseY;
				AnimationParking.updateAll(baseX, baseY);
				changeX = rawX;
				changeY = rawY;
				moved();
			}
			break;

		case MotionEvent.ACTION_UP:
			AppLog.i("Suhao.Click", "MeterBacd.UP, MOVE_MAX_SIZE/baseX= "
					+ Until.MOVE_MAX_SIZE + " / " + baseX);
			if (!this.hasMoved) {
			}
			hasMoved = false;
			break;
		default:
			break;
		}
		return super.onTouchEvent(event);

	}
    /* boolean bool = true;
    int i = (int)event.getRawX();
    int j = -25 + (int)event.getRawY();
    switch (event.getAction())
    {
    }
    for (;;)
    {
        bool = super.onTouchEvent(event);
        do
        {
            return bool;
            AppLog.i("OUT", "back ACTION_DOWN" + this.hasMoved);
            mTouchDown = bool;
            this.changeX = i;
            this.changeY = j;
            this.mTouchStartX = i;
            this.mTouchStartY = j;
            this.isDown = bool;
            break;
            AppLog.i("OUT", "back ACTION_OUTSIDE");
            break;
            int k = i - this.changeX;
            int m = j - this.changeY;
            if ((Math.abs(k) > 3) || (Math.abs(m) > 3))
            {
                AppLog.i("MBack", "baseX/offsetX = " + baseX + "/" + k);
                baseX = k + baseX;
                baseY = m + baseY;
                AnimationParking.updateAll(baseX, baseY);
                this.changeX = i;
                this.changeY = j;
            }
        } while ((Math.abs(i - this.mTouchStartX) <= Until.MOVE_MAX_SIZE) && (Math.abs(j - this.mTouchStartY) <= Until.MOVE_MAX_SIZE));
        moved();
        return bool;
        AppLog.i("Suhao.Click", "MeterBacd.UP, MOVE_MAX_SIZE/baseX= " + Until.MOVE_MAX_SIZE + " / " + baseX);
        if (!this.hasMoved) {}
        mTouchDown = false;
        this.hasMoved = false;
        this.isDown = false;
    }*/
}
