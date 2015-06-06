package com.way.mipop;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

public class AppLog {
	static final boolean DEBUG = true;
	static final String TAG = "MiPop";

	public static void DisplayToast(String content, Context context) {
		Toast.makeText(context, content, Toast.LENGTH_SHORT).show();
	}

	public static void d(Object paramObject, String paramString) {
		String str = paramObject.getClass().getName();
		Log.d("MiPop", str.substring(1 + str.lastIndexOf('.')) + ": "
				+ paramString);
	}

	public static void d(String paramString) {
		Log.d("MiPop", paramString);
	}

	public static void d(String paramString1, String paramString2) {
		Log.d("MiPop", paramString1 + ": " + paramString2);
	}

	public static void e(Object paramObject, String paramString) {
		String str = paramObject.getClass().getName();
		Log.e("MiPop", str.substring(1 + str.lastIndexOf('.')) + ": "
				+ paramString);
	}

	public static void e(String paramString) {
		Log.e("MiPop", paramString);
	}

	public static void e(String paramString1, String paramString2) {
		Log.e("MiPop", paramString1 + ": " + paramString2);
	}
}
