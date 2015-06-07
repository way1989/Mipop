package com.way.mipop.animation;

import android.os.Handler;
import android.view.View;

import com.way.mipop.AppLog;
import com.way.mipop.widget.MeterBack;
import com.way.mipop.widget.MeterBase;
import com.way.mipop.widget.MeterHome;
import com.way.mipop.widget.MeterMenu;
import com.way.mipop.widget.MeterRecent;

public class AnimationTransparent {
	private static int currentAlpha = 255;
	private static int endAlpha = 100;
	private static Handler handler4Transparent = new Handler();
	private static int periodTime = 10;
	private static Runnable runnable4Transparent = new Runnable() {
		public void run() {
			transparenting();
		}
	};
	private static int startAlpha = 255;
	private static int steps = 0;
	private static long time4Trans = 2000L;

	public static void start() {
		AppLog.i("Suhao.TransParent", "AnimationTransparent.start()");
		periodTime = (int) (time4Trans / (startAlpha - endAlpha));
		//android.util.Log.i("way"," periodTime = " + periodTime);
		handler4Transparent.postDelayed(runnable4Transparent, 1L);
		MeterBase.MeterMap.get(MeterHome.NAME).setVisibility(View.GONE);
		MeterBase.MeterMap.get(MeterMenu.NAME).setVisibility(View.GONE);
		MeterBase.MeterMap.get(MeterRecent.NAME).setVisibility(View.GONE);
	}

	public static void stop() {
		AppLog.i("Suhao.TransParent", "AnimationTransparent.stop()");
		currentAlpha = startAlpha;
		handler4Transparent.removeCallbacks(runnable4Transparent);
		MeterBase.MeterMap.get(MeterBack.NAME).setAlpha(startAlpha/255.0f);
		MeterBase.MeterMap.get(MeterHome.NAME).setVisibility(View.VISIBLE);
		MeterBase.MeterMap.get(MeterMenu.NAME).setVisibility(View.VISIBLE);
		MeterBase.MeterMap.get(MeterRecent.NAME).setVisibility(View.VISIBLE);
	}

	private static void transparenting() {
		AppLog.i("Suhao.TransParent",
				"AnimationTransparent.transparenting(), alpha = "
						+ currentAlpha);
		if (currentAlpha <= endAlpha) {
			AppLog.i("Suhao.TransParent",
					"AnimationTransparent.transparenting(), removeCallbacks");
			handler4Transparent.removeCallbacks(runnable4Transparent);
			return;
		}
		currentAlpha = -1 + currentAlpha;
		MeterBase.MeterMap.get(MeterBack.NAME).setAlpha(currentAlpha/255.0f);
		handler4Transparent.postDelayed(runnable4Transparent, periodTime);
	}
}
