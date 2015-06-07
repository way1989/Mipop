package com.way.mipop.widget;

import com.way.mipop.AppLog;
import com.way.mipop.R;

import android.app.Instrumentation;
import android.content.Context;

public class MeterHome extends MeterBase {
	public static final String NAME = MeterHome.class.getSimpleName();

	public MeterHome(Context context) {
		super(context);
		Register(NAME, this);
		setSoundEffectsEnabled(true);
		setImageResource(R.drawable.home_selector);
		setResId(R.drawable.home, R.drawable.home_pressed);
	}

	public void Click() {
		AppLog.i("CLICK", "home   click");
		playSoundEffect(0);
		new Thread() {
			public void run() {
				try {
					new Instrumentation().sendKeyDownUpSync(3);
					AppLog.i("shenzhan", "Home implement");
					return;
				} catch (Exception e) {
					AppLog.d("shenzhan", e.toString());
				}
			}
		}.start();
	}

	public void LongClick() {
		AppLog.i("Suhao", "home  long click");
	}
}
