package com.way.mipop.animation;

import android.os.Handler;
import android.view.View;

import com.way.mipop.AppLog;
import com.way.mipop.widget.MeterBack;
import com.way.mipop.widget.MeterBase;
import com.way.mipop.widget.MeterHome;
import com.way.mipop.widget.MeterMenu;
import com.way.mipop.widget.MeterRecent;
import com.way.mipop.widget.Until;

public class AnimationParking {
	public static final boolean LEFT = true;
	public static final boolean RIGHT = false;
	private static String TAG = "Parking";
	public static int baseX = MeterBase.baseX;
	public static int baseY = MeterBase.baseY;
	private static Handler handler4Parking = new Handler();
	private static Handler handler4PosCheck = new Handler();
	private static Handler handler4Shrink = new Handler();
	private static Handler handler4Turning = new Handler();
	private static int homeX;
	private static int homeY;
	public static boolean mAreaChanged = false;
	private static long mAutoUpdatePeriod = 10L;
	public static boolean mOriginSide = true;
	private static long mParking2Shrink = 2000L;
	private static int mStep = 15;
	private static boolean mTimeOut;
	private static long mVelocityTime = 300L;
	private static int menuX;
	private static int menuY;
	private static int recentX;
	private static int recentY;
	private static Runnable runnable4Parking = new Runnable() {

		@Override
		public void run() {
			 parking();
		}
	};
	private static Runnable runnable4PosCheck = new Runnable() {

		@Override
		public void run() {
			mTimeOut = true;
			velocityCheck = true;
			if (mAreaChanged) {
				mAreaChanged = false;
			}
		}
	};
	private static Runnable runnable4Shrink = new Runnable() {
		@Override
		public void run() {
			 shrinking();
		}
	};
	private static Runnable runnable4Turning = new Runnable() {

		@Override
		public void run() {
			 turning();
		}
	};
	private static boolean velocityCheck;

	private static void hideSub() {
		MeterBase.MeterMap.get(MeterRecent.NAME).setVisibility(View.INVISIBLE);
		MeterBase.MeterMap.get(MeterHome.NAME).setVisibility(View.INVISIBLE);
		MeterBase.MeterMap.get(MeterMenu.NAME).setVisibility(View.INVISIBLE);
	}

	private static void initial() {
		//MeterBase.MeterMap.get(MeterBack.NAME).baseX = MeterBase.baseX;
		baseX = MeterBase.baseX;
		//MeterBase.MeterMap.get(MeterBack.NAME).baseY = MeterBase.baseY;
		baseY = MeterBase.baseY;
	}

	public static void land() {
		if (!mOriginSide) {
			stop();
			baseX = Until.SCREEM_WIDTH - Until.IMAGE_WIDTH;
			updateAll(baseX, baseY);
		}
		if (MeterBase.baseY > Until.SCREEM_HEIGHT) {
			updateBottom(baseX, baseY);
		}
	}

	private static void parking() {
		//AppLog.d("way", "parking baseX = " + baseX);
		if (baseX < Until.MID_LINE) {
			parking2Margin(true);
		} else {
			AppLog.d("MBack", "baseX = " + baseX);
			parking2Margin(false);
		}
	}

	private static void parking2Margin(boolean isLeft) {
		//AppLog.i("way", "Until.EXPEND_LINE = " + Until.EXPEND_LINE + ", Until.IMAGE_WIDTH = " + Until.IMAGE_WIDTH);
		int expendLine = Until.EXPEND_LINE;
		if (!isLeft) {
			expendLine = Until.SCREEM_WIDTH - Until.IMAGE_WIDTH - Until.EXPEND_LINE;
		}
		int speed = mStep;
		if (baseX > expendLine) {
			speed = -mStep;
		}
		baseX = speed + baseX;
		updateAll(baseX, baseY);
		if (Math.abs(baseX - expendLine) <= mStep) {
			baseX = expendLine;
			//AppLog.i("way", "final  baseX = " + baseX);
			updateAll(baseX, baseY);
			handler4Parking.removeCallbacks(runnable4Parking);
			handler4Turning.postDelayed(runnable4Turning, mParking2Shrink);
			return;
		}
		handler4Shrink.removeCallbacks(runnable4Shrink);
		handler4Parking.postDelayed(runnable4Parking, mAutoUpdatePeriod);
	}

	private static void posCalculateLeftX(int x, int y) {
		if (x <= Until.EXPEND_LINE) {
			recentX = x - Until.EXPEND_LINE;
			recentY = y;
			homeX = x / 2;
			homeY = y - x / 2;
			menuX = x / 2;
			menuY = y + x / 2;
			return;
		}
		if (mOriginSide) {
			if (x < Until.MID_LINE) {
				recentX = x - Until.EXPEND_LINE;
				recentY = y;
				homeX = x - Until.EXPEND_LINE / 2;
				homeY = y - Until.EXPEND_LINE / 2;
				menuX = x - Until.EXPEND_LINE / 2;
				menuY = y + Until.EXPEND_LINE / 2;
				return;
			}
			if (x < Until.MID_LINE + Until.SHRINK_LINE) {
				AppLog.i("Park", "Left shrink x=" + x);
				recentX = x - Until.EXPEND_LINE + Until.EXPEND_LINE
						/ Until.SHRINK_LINE * (x - Until.MID_LINE);
				recentY = y;
				homeX = x - Until.EXPEND_LINE / 2 + Until.EXPEND_LINE
						/ Until.SHRINK_LINE * (x - Until.MID_LINE) / 2;
				homeY = y - Until.EXPEND_LINE / 2 + Until.EXPEND_LINE
						/ Until.SHRINK_LINE * (x - Until.MID_LINE) / 2;
				menuX = x - Until.EXPEND_LINE / 2 + Until.EXPEND_LINE
						/ Until.SHRINK_LINE * (x - Until.MID_LINE) / 2;
				menuY = y + Until.EXPEND_LINE / 2 - Until.EXPEND_LINE
						/ Until.SHRINK_LINE * (x - Until.MID_LINE) / 2;
				return;
			}
		}
		mAreaChanged = true;
		recentX = x;
		recentY = y;
		homeX = x;
		homeY = y;
		menuX = x;
		menuY = y;
		hideSub();
		
	}

	private static void quickSlide() {
		handler4PosCheck.removeCallbacks(runnable4PosCheck);
		if (!mTimeOut) {
			velocityCheck = true;
		}
		if (mAreaChanged) {
			velocityCheck = false;
		}
	}

	private static void showOrHide(int x) {
		//AppLog.i("way", "showOrHide velocityCheck = " + velocityCheck + ", mAreaChanged = " + mAreaChanged);
		if (!velocityCheck) {
			hideSub();
		} else {
			if (mAreaChanged) {
				hideSub();
				return;
			}
			if ((x > Until.SCREEM_WIDTH - Until.IMAGE_WIDTH) || (x < 0)) {
				hideSub();
				return;
			}
			
			showSub();
		}
	}

	private static void showSub() {
		MeterBase.MeterMap.get(MeterRecent.NAME).setVisibility(View.VISIBLE);
		MeterBase.MeterMap.get(MeterHome.NAME).setVisibility(View.VISIBLE);
		MeterBase.MeterMap.get(MeterMenu.NAME).setVisibility(View.VISIBLE);
	}

	public static void shrinkStart() {
		handler4Parking.removeCallbacks(runnable4Parking);
		handler4Shrink.postDelayed(runnable4Shrink, mAutoUpdatePeriod);
	}

	private static void shrinking() {
		int speed = mStep;
		if (baseX < Until.MID_LINE) {
			speed = -speed;
		}
		baseX = speed + baseX;
		AppLog.i("Suhao", "shrinking x= " + baseX);
		updateAll(baseX, baseY);
		if (baseX >= Until.SCREEM_WIDTH - Until.IMAGE_WIDTH) {
			baseX = Until.SCREEM_WIDTH - Until.IMAGE_WIDTH;
			updateAll(baseX, baseY);
			velocityCheck = false;
			mOriginSide = false;
			mAreaChanged = false;
			handler4Shrink.removeCallbacks(runnable4Shrink);
			AppLog.i("Suhao.TransParent",
					"AnimationParking.shrinking(), baseX>SCREEN_WIDTH-IMAGE_WIDTH");
			AnimationTransparent.start();
			return;
		}
		if (baseX <= 1) {
			baseX = 0;
			updateAll(baseX, baseY);
			velocityCheck = false;
			mOriginSide = true;
			mAreaChanged = false;
			handler4Shrink.removeCallbacks(runnable4Shrink);
			AppLog.i("Suhao.TransParent", "AnimationParking.shrinking(), baseX<1");
			AnimationTransparent.start();
			return;
		}
		handler4Shrink.postDelayed(runnable4Shrink, mAutoUpdatePeriod);
	}

	public static void start() {
		AppLog.i("Suhao", "AnimationParking start()");
		quickSlide();
		initial();
		if (baseX <= 0) {
			AppLog.i("Suhao.TransParent", "AnimationParking.start(), baseX<0");
			mOriginSide = true;
			mAreaChanged = false;
			velocityCheck = false;
			baseX = 0;
			AnimationTransparent.start();
			return;
		}
		if (baseX >= Until.SCREEM_WIDTH - Until.IMAGE_WIDTH) {
			AppLog.i("Suhao.TransParent",
					"AnimationParking.start(), baseX>SCREEN_WIDTH-IMAGE_WIDTH");
			mOriginSide = false;
			mAreaChanged = false;
			velocityCheck = false;
			baseX = Until.SCREEM_WIDTH - Until.IMAGE_WIDTH;
			AnimationTransparent.start();
			return;
		}
		updateTop(baseX, baseY);
		updateBottom(baseX, baseY);
		if (!mAreaChanged) {
			if ((baseX < Until.PARKING_LINE)
					|| (baseX > Until.PARKING_LINE_RIGHT)) {
				AppLog.i("Suhao", "LEFT && > MID_LINE");
				handler4Parking.removeCallbacks(runnable4Parking);
				handler4Shrink.postDelayed(runnable4Shrink, mAutoUpdatePeriod);
				return;
			}
			if ((mOriginSide) && (baseX > Until.MID_LINE)) {
				AppLog.i("Suhao", "LEFT && > MID_LINE");
				handler4Parking.removeCallbacks(runnable4Parking);
				handler4Shrink.postDelayed(runnable4Shrink, mAutoUpdatePeriod);
				return;
			}
			if ((!mOriginSide) && (baseX < Until.MID_LINE)) {
				AppLog.i("Suhao", "LEFT && > MID_LINE");
				handler4Parking.removeCallbacks(runnable4Parking);
				handler4Shrink.postDelayed(runnable4Shrink, mAutoUpdatePeriod);
				return;
			}
			handler4Shrink.removeCallbacks(runnable4Shrink);
			handler4Parking.postDelayed(runnable4Parking, mAutoUpdatePeriod);
			return;
		}
		AppLog.i("Suhao", "else");
		handler4Parking.removeCallbacks(runnable4Parking);
		handler4Shrink.postDelayed(runnable4Shrink, mAutoUpdatePeriod);
	}

	public static void stop() {
		AppLog.i("Suhao.TransParent", "AnimationParking.stop()");
		AnimationTransparent.stop();
		handler4Parking.removeCallbacks(runnable4Parking);
		handler4Shrink.removeCallbacks(runnable4Shrink);
		handler4Turning.removeCallbacks(runnable4Turning);
		mTimeOut = false;
		handler4PosCheck.postDelayed(runnable4PosCheck, mVelocityTime);
	}

	private static void turning() {
		handler4Shrink.postDelayed(runnable4Shrink, mAutoUpdatePeriod);
		handler4Turning.removeCallbacks(runnable4Turning);
	}

	public static void updateAll(int x, int y) {
		if (x < 0) {
			x = 0;
		}
		if (x > Until.SCREEM_WIDTH - Until.IMAGE_WIDTH) {
			x = Until.SCREEM_WIDTH - Until.IMAGE_WIDTH;
		}
		if (y < 0) {
			y = 0;
		}
		MeterBase.MeterMap.get(MeterBack.NAME).update(x, y);
		//MeterBase.MeterMap.get(MeterBack.NAME).baseX = x;
		MeterBase.baseX = x;
		//MeterBase.MeterMap.get(MeterBack.NAME).baseY = y;
		MeterBase.baseY = y;
		//AppLog.i("way", "updateAll mOriginSide = " + mOriginSide);
		if (mOriginSide) {
			updateAllLeft(x, y);
		} else {
			updateAllRight(x, y);
		}
	}

	private static void updateAllLeft(int x, int y) {
		showOrHide(x);
		posCalculateLeftX(x, y);
		MeterBase.MeterMap.get(MeterRecent.NAME).update(recentX, recentY);
		MeterBase.MeterMap.get(MeterHome.NAME).update(homeX, homeY);
		MeterBase.MeterMap.get(MeterMenu.NAME).update(menuX, menuY);
	}

	private static void updateAllRight(int x, int y) {
		// (Until.IMAGE_WIDTH / 40);
		showOrHide(x);
		if (x >= Until.EXPEND_LINE_RIGHT) {
			recentX = x + Until.EXPEND_LINE;
			recentY = y;
			homeX = (x + Until.SCREEM_WIDTH - Until.IMAGE_WIDTH) / 2;
			homeY = y - (Until.SCREEM_WIDTH - Until.IMAGE_WIDTH - x) / 2;
			menuX = (x + Until.SCREEM_WIDTH - Until.IMAGE_WIDTH) / 2;
			menuY = y + (Until.SCREEM_WIDTH - Until.IMAGE_WIDTH - x) / 2;
		} else {
			if (x > Until.MID_LINE) {
				recentX = x + Until.EXPEND_LINE;
				recentY = y;
				homeX = x + Until.EXPEND_LINE / 2;
				homeY = y - Until.EXPEND_LINE / 2;
				menuX = x + Until.EXPEND_LINE / 2;
				menuY = y + Until.EXPEND_LINE / 2;
			} else if (x > Until.MID_LINE - Until.SHRINK_LINE) {
				recentX = x + Until.EXPEND_LINE + Until.EXPEND_LINE
						/ Until.SHRINK_LINE * (x - Until.MID_LINE);
				recentY = y;
				homeX = x + Until.EXPEND_LINE / 2 + Until.EXPEND_LINE
						/ Until.SHRINK_LINE * (x - Until.MID_LINE) / 2;
				homeY = y - Until.EXPEND_LINE / 2 - Until.EXPEND_LINE
						/ Until.SHRINK_LINE * (x - Until.MID_LINE) / 2;
				menuX = x + Until.EXPEND_LINE / 2 + Until.EXPEND_LINE
						/ Until.SHRINK_LINE * (x - Until.MID_LINE) / 2;
				menuY = y + Until.EXPEND_LINE / 2 + Until.EXPEND_LINE
						/ Until.SHRINK_LINE * (x - Until.MID_LINE) / 2;
			} else if (!mOriginSide) {
				mAreaChanged = true;
				recentX = x;
				recentY = y;
				homeX = x;
				homeY = y;
				menuX = x;
				menuY = y;
				hideSub();
			}
		}
		MeterBase.MeterMap.get(MeterRecent.NAME).update(recentX, recentY);
		MeterBase.MeterMap.get(MeterHome.NAME).update(homeX, homeY);
		MeterBase.MeterMap.get(MeterMenu.NAME).update(menuX, menuY);
	}

	private static void updateBottom(int x, int y) {
		if (y <= Until.BOTTOM_LINE) {
			AppLog.i("Bottom", "return");
			return;
		}
		if ((x > Until.SCREEM_WIDTH - Until.PARKING_LINE)
				|| (x <= Until.MID_LINE))
			return;

		if ((x < Until.PARKING_LINE) && (x >= Until.MID_LINE))
			return;

		if (mOriginSide) {
			AppLog.i("Bottom", "LEFT bar = " + Until.STATUS_HEIGHT);
			int j = Until.BOTTOM_LINE;
			baseX = x;
			baseY = j;
			updateAll(x, j);
		} else {
			AppLog.i("Bottom", "RIGHT");
			int i = Until.BOTTOM_LINE;
			baseX = x;
			baseY = i;
			updateAll(x, i);
		}
	}

	private static void updateTop(int x, int y) {
		if (y >= (int) (0.707D * Until.IMAGE_WIDTH)) {
			return;
		}
		if ((x > Until.SCREEM_WIDTH - Until.PARKING_LINE)
				|| (x <= Until.MID_LINE))
			return;
		if ((x < Until.PARKING_LINE) || (x >= Until.MID_LINE))
			return;

		if (mOriginSide) {
			int j = Until.EXPEND_LINE / 2;
			baseX = x;
			baseY = j;
			updateAll(x, j);
		} else {
			int i = Until.EXPEND_LINE / 2;
			baseX = x;
			baseY = i;
			updateAll(x, i);
		}

	}
}
