package com.way.mipop.api;

import android.app.Application;
import android.view.View;

import com.way.mipop.animation.AnimationParking;
import com.way.mipop.animation.AnimationTransparent;
import com.way.mipop.widget.MeterBack;
import com.way.mipop.widget.MeterBase;
import com.way.mipop.widget.MeterHome;
import com.way.mipop.widget.MeterMenu;
import com.way.mipop.widget.MeterRecent;
import com.way.mipop.widget.Until;

public class App extends Application {

	public static void hideMipop() {
		MeterBase.MeterMap.get(MeterHome.NAME).setVisibility(View.GONE);
		MeterBase.MeterMap.get(MeterRecent.NAME).setVisibility(View.GONE);
		MeterBase.MeterMap.get(MeterMenu.NAME).setVisibility(View.GONE);
		MeterBase.MeterMap.get(MeterBack.NAME).setVisibility(View.GONE);
	}

	public static void showMipop() {
		AnimationParking.stop();
		AnimationParking.mOriginSide = AnimationParking.LEFT;
		AnimationParking.baseX = -1;
		AnimationParking.updateAll(-1, MeterBack.baseY);
		MeterBase.MeterMap.get(MeterBack.NAME).setVisibility(View.VISIBLE);
		MeterBase.MeterMap.get(MeterHome.NAME).setVisibility(View.GONE);
		MeterBase.MeterMap.get(MeterRecent.NAME).setVisibility(View.GONE);
		MeterBase.MeterMap.get(MeterMenu.NAME).setVisibility(View.GONE);
		MeterBase.MeterMap.get(MeterBack.NAME).setAlpha(0.4f);
		AnimationParking.shrinkStart();
	}

	public void onCreate() {
		super.onCreate();
		Until.initialPop(this);
		new MeterMenu(this);
		new MeterRecent(this);
		new MeterHome(this);
		new MeterBack(this);
	}
}
