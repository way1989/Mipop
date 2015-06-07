package com.way.mipop.widget;

import android.content.Context;
import android.os.RemoteException;

import com.way.mipop.AppLog;
/*import android.os.ServiceManager;
import com.android.internal.statusbar.IStatusBarService;
import com.android.internal.statusbar.IStatusBarService.Stub;*/
import com.way.mipop.R;

public class MeterRecent extends MeterBase {
	public static final String NAME = MeterRecent.class.getSimpleName();

	public MeterRecent(Context paramContext) {
		super(paramContext);
		Register(NAME, this);
		setSoundEffectsEnabled(true);
		setImageResource(R.drawable.recent_selector);
		setResId(R.drawable.recent, R.drawable.recent_pressed);
	}

	public void Click() {
		AppLog.i("way", "recent  click");
		playSoundEffect(0);
		new Thread() {
			public void run() {
				/*IStatusBarService iStatusBarService = IStatusBarService.Stub
						.asInterface(ServiceManager.getService("statusbar"));
				if (iStatusBarService != null) {
				}
				try {
					iStatusBarService.topAppWindowChanged(false);
					iStatusBarService.toggleRecentApps();
					return;
				} catch (RemoteException e) {
					AppLog.i("Input", "DeadOjbectException");
				}*/
			}
		}.start();
	}

	public void LongClick() {
		AppLog.i("way", "recent  long click");
	}
}
