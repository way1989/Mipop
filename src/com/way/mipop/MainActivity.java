package com.way.mipop;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceScreen;
import android.provider.Settings;

import com.way.mipop.api.App;

public class MainActivity extends PreferenceActivity {
	private String TAG = "MIPopActivity";
	CheckBoxPreference mFullScreen;
	CheckBoxPreference mMiPop;
	Context mcontext;

	private void setupFloatIcon() {
		boolean isMipopShow = getApplicationContext().getSharedPreferences(
				"com.android.mipop_preferences", MODE_PRIVATE).getBoolean(
				"mipop_switch", false);
		mMiPop.setChecked(isMipopShow);
		if (mFullScreen.isChecked())
			mMiPop.setEnabled(false);
	}

	private void setupFullScreen() {
		boolean isEnable = getApplicationContext().getSharedPreferences(
				"com.android.mipop_preferences", MODE_PRIVATE).getBoolean(
				"globalAction", true);
		mFullScreen.setEnabled(isEnable);
		String str = Settings.System.getString(getContentResolver(),
				"showNavigationBar");
		if ("show".equals(str)) {
			mFullScreen.setChecked(false);
		} else if ("hide".equals(str)) {
			mFullScreen.setChecked(true);
		}
	}

	public void onCreate(Bundle paramBundle) {
		AppLog.i("MyAppWidget", "activity onCreate() " + this.mcontext);
		super.onCreate(paramBundle);
		addPreferencesFromResource(R.xml.mipop_settings);
		mMiPop = ((CheckBoxPreference) findPreference("mipop_switch"));
		mFullScreen = ((CheckBoxPreference) findPreference("mipop_fullscreen"));
		mcontext = this;
	}

	public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen,
			Preference preference) {
		AppLog.i(this.TAG, "onPreferenceTreeClick");
		if (preference == mMiPop) {
			AppLog.i(TAG, "onPreferenceTreeClick preference == mMiPop");
			if (mMiPop.isChecked()) {
				((App) getApplication()).showMipop();
			} else {
				((App) getApplication()).hideMipop();
			}
		} else if (preference == mFullScreen) {
			AppLog.i(this.TAG, "onPreferenceTreeClick preference == mFullScreen");
			if (mFullScreen.isChecked()) {
				AppLog.i("Suhao.CheckBox", "mFullScreen checked mipop = true");
				Intent localIntent1 = new Intent(
						"zte.com.cn.NAVIGATIONBAR_SHOW");
				localIntent1.putExtra("state", false);
				sendBroadcast(localIntent1);
				AppLog.i(TAG, "sendbroadcast true");
				mMiPop.setChecked(true);
				mMiPop.setEnabled(false);
				((App) getApplication()).showMipop();
			} else {
				Intent localIntent2 = new Intent(
						"zte.com.cn.NAVIGATIONBAR_SHOW");
				localIntent2.putExtra("state", true);
				sendBroadcast(localIntent2);
				AppLog.i(TAG, "sendbroadcast false");
				mMiPop.setEnabled(true);
			}
		}
		return super.onPreferenceTreeClick(preferenceScreen, preference);

	}

	protected void onResume() {
		super.onResume();
		AppLog.i(this.TAG, "+++++++onResume--mcontext=" + this.mcontext);
		setupFloatIcon();
		setupFullScreen();
	}

}
