package com.way.mipop.widget;

import com.way.mipop.R;

import android.app.Instrumentation;
import android.content.Context;
import android.util.Log;

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
		Log.i("CLICK", "home   click");
		playSoundEffect(0);
		new Thread() {
			public void run() {
				try {
					new Instrumentation().sendKeyDownUpSync(3);
					Log.i("shenzhan", "Home implement");
					return;
				} catch (Exception e) {
					Log.d("shenzhan", e.toString());
				}
			}
		}.start();
	}

	public void LongClick() {
		Log.i("Suhao", "home  long click");
	}
}
