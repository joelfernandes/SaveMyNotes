package pt.test.utils;

public class Log {
	private static boolean isProductionMode = false;
	public static String Tag = "Test";
	
	/**
	 * This method switches application to production mode, 
	 * where no logs will be shown. 
	 */
	public void releaseTheKraken() {
		isProductionMode = true;
	}
	
	public static void d (String message) {
		if(isProductionMode == false) {
			android.util.Log.d(Tag, message);
		}
	}
	
	public static void w (String message) {
		if(isProductionMode == false) {
			android.util.Log.w(Tag, message);
		}
	}
	
	public static void e (String message) {
		if(isProductionMode == false) {
			android.util.Log.e(Tag, message);
		}
	}
	
	public static void e (String message, Throwable throwable) {
		if(isProductionMode == false) {
			android.util.Log.e(Tag, message, throwable);
		}
	}

	
}
