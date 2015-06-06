package com.way.mipop.widget;

import android.app.Instrumentation;
import android.content.Context;
import android.content.res.Configuration;
import android.util.Log;
import android.view.MotionEvent;

import com.way.mipop.R;
import com.way.mipop.animation.AnimationParking;

public class MeterBack extends MeterBase {
	public static final String NAME = MeterBack.class.getSimpleName();
	private final boolean LEFT = true;
	private final boolean RIGHT = false;
	private int changeX = 0;
	private int changeY = 0;
	private boolean hasMoved = false;
	private boolean isDown = false;
	private int mBackX = 0;
	private int mBackY = 0;
	private int mTouchStartX = 0;
	private int mTouchStartY = 0;
	private int pressX = 0;

	public MeterBack(Context context) {
		super(context);
		Register(NAME, this);
		setSoundEffectsEnabled(true);
		setImageResource(R.drawable.back_selector);
		setResId(R.drawable.back, R.drawable.back_pressed);
	}

	public void Click() {
		Log.i("Suhao.Click", "back click");
		playSoundEffect(0);
		new Thread() {
			public void run() {
				try {
					new Instrumentation().sendKeyDownUpSync(4);
					Log.i("shenzhan", "Back implement");
				} catch (Exception e) {
					Log.d("shenzhan", e.toString());
				}
			}
		}.start();
	}

	public void LongClick() {
	}

	protected void onConfigurationChanged(Configuration configuration) {
		super.onConfigurationChanged(configuration);
		Until.initialPop(mContext);
		AnimationParking.land();
	}

	public boolean onTouchEvent(MotionEvent event) {
		int rawX = (int) event.getRawX();
		int rawY = (int) event.getRawY() - Until.STATUS_HEIGHT;
		if ((Math.abs(rawX - mTouchStartX) <= Until.MOVE_MAX_SIZE)
				&& (Math.abs(rawY - mTouchStartY) <= Until.MOVE_MAX_SIZE))
			return super.onTouchEvent(event);
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			Log.i("OUT", "back ACTION_DOWN" + this.hasMoved);
			mTouchDown = super.onTouchEvent(event);
			this.changeX = rawX;
			this.changeY = rawY;
			this.mTouchStartX = rawX;
			this.mTouchStartY = rawY;
			this.isDown = super.onTouchEvent(event);
			break;
		case MotionEvent.ACTION_MOVE:
			Log.i("OUT", "back ACTION_OUTSIDE");
			int k = rawX - changeX;
			int m = rawY - changeY;
			if ((Math.abs(k) > 3) || (Math.abs(m) > 3)) {
				Log.i("MBack", "baseX/offsetX = " + baseX + "/" + k);
				baseX = k + baseX;
				baseY = m + baseY;
				AnimationParking.updateAll(baseX, baseY);
				changeX = rawX;
				changeY = rawY;
				moved();
			}
			break;

		case MotionEvent.ACTION_UP:
			Log.i("Suhao.Click", "MeterBacd.UP, MOVE_MAX_SIZE/baseX= "
					+ Until.MOVE_MAX_SIZE + " / " + baseX);
			if (!this.hasMoved) {
			}
			mTouchDown = false;
			hasMoved = false;
			isDown = false;
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
            Log.i("OUT", "back ACTION_DOWN" + this.hasMoved);
            mTouchDown = bool;
            this.changeX = i;
            this.changeY = j;
            this.mTouchStartX = i;
            this.mTouchStartY = j;
            this.isDown = bool;
            break;
            Log.i("OUT", "back ACTION_OUTSIDE");
            break;
            int k = i - this.changeX;
            int m = j - this.changeY;
            if ((Math.abs(k) > 3) || (Math.abs(m) > 3))
            {
                Log.i("MBack", "baseX/offsetX = " + baseX + "/" + k);
                baseX = k + baseX;
                baseY = m + baseY;
                AnimationParking.updateAll(baseX, baseY);
                this.changeX = i;
                this.changeY = j;
            }
        } while ((Math.abs(i - this.mTouchStartX) <= Until.MOVE_MAX_SIZE) && (Math.abs(j - this.mTouchStartY) <= Until.MOVE_MAX_SIZE));
        moved();
        return bool;
        Log.i("Suhao.Click", "MeterBacd.UP, MOVE_MAX_SIZE/baseX= " + Until.MOVE_MAX_SIZE + " / " + baseX);
        if (!this.hasMoved) {}
        mTouchDown = false;
        this.hasMoved = false;
        this.isDown = false;
    }*/
}
