package com.way.mipop;

import android.content.Context;
import android.os.Debug;
import android.util.Log;
import android.widget.Toast;

public class AppLog {
	static final boolean DEBUG = Debug.isDebuggerConnected();
	static final String TAG = "MiPop";

	public static void DisplayToast(Context context, String message) {
		Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
	}

	public static void i(Object object, String log) {
		if (!DEBUG)
			return;
		String className = object.getClass().getName();
		Log.d(TAG, className.substring(1 + className.lastIndexOf('.')) + ": "
				+ log);
	}

	public static void i(String log) {
		if (!DEBUG)
			return;
		Log.d(TAG, log);
	}

	public static void i(String tag, String log) {
		if (!DEBUG)
			return;
		Log.d(TAG, tag + ": " + log);
	}

	public static void d(Object object, String log) {
		if (!DEBUG)
			return;
		String className = object.getClass().getName();
		Log.d(TAG, className.substring(1 + className.lastIndexOf('.')) + ": "
				+ log);
	}

	public static void d(String log) {
		if (!DEBUG)
			return;
		Log.d(TAG, log);
	}

	public static void d(String tag, String log) {
		if (!DEBUG)
			return;
		Log.d(TAG, tag + ": " + log);
	}

	public static void e(Object object, String log) {
		if (!DEBUG)
			return;
		String className = object.getClass().getName();
		Log.e(TAG, className.substring(1 + className.lastIndexOf('.')) + ": "
				+ log);
	}

	public static void e(String log) {
		if (!DEBUG)
			return;
		Log.e(TAG, log);
	}

	public static void e(String paramString1, String paramString2) {
		if (!DEBUG)
			return;
		Log.e(TAG, paramString1 + ": " + paramString2);
	}
}
