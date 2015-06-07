package com.way.mipop.api;

import android.app.Application;
import android.view.View;
import android.view.WindowManager;

import com.way.mipop.animation.AnimationParking;
import com.way.mipop.animation.AnimationTransparent;
import com.way.mipop.widget.MeterBack;
import com.way.mipop.widget.MeterBase;
import com.way.mipop.widget.MeterHome;
import com.way.mipop.widget.MeterMenu;
import com.way.mipop.widget.MeterRecent;
import com.way.mipop.widget.Until;

public class App extends Application {
	private static MeterBack back = null;
	private static MeterHome home = null;
	private static boolean mPowerRecovery = true;
	private static MeterMenu menu = null;
	private static MeterRecent recent = null;

	private String TAG = "MyApp";
	public boolean mSwitchIcon = true;
	public WindowManager wm4Icon = null;
	private WindowManager.LayoutParams wmParams = new WindowManager.LayoutParams();
	WindowManager.LayoutParams wmParams4Window = null;

	public static void hideMipop() {
		MeterBase.MeterMap.get(MeterHome.NAME).setVisibility(View.GONE);
		MeterBase.MeterMap.get(MeterRecent.NAME).setVisibility(View.GONE);
		MeterBase.MeterMap.get(MeterMenu.NAME).setVisibility(View.GONE);
		MeterBase.MeterMap.get(MeterBack.NAME).setVisibility(View.GONE);
	}

	public static void showMipop() {
		AnimationParking.stop();
		AnimationParking.mOriginSide = true;
		AnimationParking.baseX = -1;
		AnimationParking.updateAll(-1, MeterBack.baseY);
		MeterBase.MeterMap.get(MeterBack.NAME).setVisibility(View.VISIBLE);
		AnimationTransparent.start();
	}

	public WindowManager.LayoutParams getMywmParams() {
		return wmParams;
	}

	public void onCreate() {
		super.onCreate();
		Until.initialPop(getApplicationContext());
		menu = new MeterMenu(getApplicationContext());
		recent = new MeterRecent(getApplicationContext());
		home = new MeterHome(getApplicationContext());
		back = new MeterBack(getApplicationContext());
	}
}
