package com.way.mipop.widget;

import com.way.mipop.AppLog;
import com.way.mipop.R;

import android.app.Instrumentation;
import android.content.Context;

public class MeterMenu extends MeterBase {
	public static final String NAME = MeterMenu.class.getSimpleName();

	public MeterMenu(Context context) {
		super(context);
		Register(NAME, this);
		setSoundEffectsEnabled(true);
		setImageResource(R.drawable.menu_selector);
		setResId(R.drawable.menu, R.drawable.menu_pressed);
	}

	public void Click() {
		AppLog.i("CLICK", "menu click");
		playSoundEffect(0);
		new Thread() {
			public void run() {
				try {
					new Instrumentation().sendKeyDownUpSync(82);
					AppLog.i("shenzhan", "MENU implement");
					return;
				} catch (Exception e) {
					AppLog.d("HouJiong", e.toString());
				}
			}
		}.start();
	}

	public void LongClick() {
		AppLog.i("Suhao", "menu  long click");
	}
}
