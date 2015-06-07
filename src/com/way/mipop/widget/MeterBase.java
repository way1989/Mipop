package com.way.mipop.widget;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.os.Handler;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.ImageView;

import com.way.mipop.AppLog;
import com.way.mipop.animation.AnimationParking;

public abstract class MeterBase extends ImageView {
	public static Map<String, MeterBase> MeterMap = new HashMap<String, MeterBase>();
	public static int baseX = 0;
	public static int baseY = Until.SCREEM_HEIGHT / 2;
	public static Context mContext;
	public static int mLeftMargin = 0;
	public static boolean mTouchDown = false;
	public static Paint paint = new Paint();
	private static int pressX = 0;
	private Bitmap bmp;
	private Bitmap bmpDown;
	private int changeX = -1;
	private int changeY = -1;
	private Handler handler4LongClick = new Handler();
	private boolean hasMoved = false;
	private boolean isDown = false;
	public boolean isLongClick = false;
	private final long mTime4LongClick = 1000L;
	private int mTouchStartX = 0;
	private int mTouchStartY = 0;
	public WindowManager mWindowManager = null;
	private Rect rectDst;
	private int resId = 0;
	private int resIdPressed = 0;
	private Runnable runnable4LongClick = new Runnable() {
		public void run() {
			isLongClick = true;
			LongClick();
		}
	};
	public WindowManager.LayoutParams wmParams = new WindowManager.LayoutParams();

	public MeterBase(Context context) {
		super(context);
		mContext = context;
		this.mWindowManager = ((WindowManager) context.getApplicationContext()
				.getSystemService(Context.WINDOW_SERVICE));
		this.wmParams.type = LayoutParams.TYPE_SYSTEM_ALERT;
		this.wmParams.format = PixelFormat.TRANSPARENT;
		wmParams.flags = LayoutParams.FLAG_NOT_FOCUSABLE | LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH;
		this.wmParams.gravity = Gravity.LEFT | Gravity.TOP;
		this.wmParams.x = baseX;
		this.wmParams.y = baseY;
		this.wmParams.height = Until.IMAGE_WIDTH;
		this.wmParams.width = Until.IMAGE_WIDTH;
		this.mWindowManager.addView(this, wmParams);
		this.changeX = baseX;
		this.changeY = baseY;
	}

	public static Map<String, MeterBase> getMap() {
		return MeterMap;
	}

	protected abstract void Click();

	protected abstract void LongClick();

	public void Register(String name, MeterBase key) {
		MeterMap.put(name, key);
	}

	public void moved() {
		hasMoved = true;
		handler4LongClick.removeCallbacks(runnable4LongClick);
	}

	public boolean onTouchEvent(MotionEvent event) {
		mTouchStartX = (int) event.getRawX();
		mTouchStartY = (int) event.getRawY() - Until.STATUS_HEIGHT;
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			AppLog.i("OUT", "base DOWN" + hasMoved);
			setImageResource(resIdPressed);
			handler4LongClick.postDelayed(runnable4LongClick, 1000L);
			AnimationParking.stop();
			return true;
		case MotionEvent.ACTION_MOVE:
			return true;
		case MotionEvent.ACTION_UP:
			AppLog.i("OUT", "base UP" + this.hasMoved);
			setImageResource(resId);
			this.handler4LongClick.removeCallbacks(runnable4LongClick);
			if (!hasMoved) {
				if (!isLongClick) {
					AppLog.i("Suhao.Click", "MeterBase.UP, Click");
					Click();
				}
			}
			if(isLongClick){
				AppLog.i("Suhao.Click", "MeterBase.UP, Long click");
			}else if(hasMoved){
				AppLog.i("Suhao.Click", "MeterBase.UP, has moved");
			}
			
	        hasMoved = false;
	        isLongClick = false;
            AnimationParking.start();
            return true;

		default:
			break;
		}
		AppLog.i("OUT", "base ACTION_OUTSIDE" + this.hasMoved);
		AnimationParking.shrinkStart();
		return true;
        /*for (;;)
        {
            return true;
            AppLog.i("OUT", "base DOWN" + this.hasMoved);
            setImageResource(this.resIdPressed);
            this.handler4LongClick.postDelayed(this.runnable4LongClick, 1000L);
            AnimationParking.stop();
            continue;
            AppLog.i("OUT", "base UP" + this.hasMoved);
            setImageResource(this.resId);
            this.handler4LongClick.removeCallbacks(this.runnable4LongClick);
            if (!this.hasMoved) {
                if (!this.isLongClick)
                {
                    AppLog.i("Suhao.Click", "MeterBase.UP, Click");
                    Click();
                }
            }
            for (;;)
            {
                this.hasMoved = false;
                this.isLongClick = false;
                AnimationParking.start();
                break;
                AppLog.i("Suhao.Click", "MeterBase.UP, Long click");
                continue;
                AppLog.i("Suhao.Click", "MeterBase.UP, has moved");
            }
            AppLog.i("OUT", "base ACTION_OUTSIDE" + this.hasMoved);
            AnimationParking.shrinkStart();
        }*/
	}

	public void resetAlpha() {
		paint.setAlpha(255);
		invalidate();
	}

	public void setBitmap(int normal, int pressed) {
		this.bmp = BitmapFactory
				.decodeResource(mContext.getResources(), normal);
		this.bmpDown = BitmapFactory.decodeResource(mContext.getResources(),
				pressed);
		this.rectDst = new Rect(0, 0, Until.IMAGE_WIDTH, Until.IMAGE_WIDTH);
	}

	public void setResId(int normal, int pressed) {
		this.resId = normal;
		this.resIdPressed = pressed;
	}

	public void test() {
		getWindowVisibleDisplayFrame(new Rect());
	}

	public void update(int x, int y) {
		this.wmParams.x = x;
		this.wmParams.y = y;
		this.mWindowManager.updateViewLayout(this, wmParams);
	}
}
