package com.xrdsgzs.smartdormitory.tool;

import org.litepal.LitePalApplication;

import android.content.Context;

//public class MyApplication extends Application {
//	
//	private static Context context; 
//
//    public void onCreate(){ 
//        super.onCreate(); 
//        MyApplication.context = getApplicationContext(); 
//    } 
//
//    public static Context getAppContext() { 
//        return MyApplication.context; 
//    }
//}

public class MyApplication extends LitePalApplication {
	
	private static Context mcontext; 

    public void onCreate(){ 
        super.onCreate(); 
        MyApplication.mcontext = LitePalApplication.getContext(); 
    } 
    
    public static Context getAppContext() {
		if (mcontext == null) {
			MyApplication.mcontext = LitePalApplication.getContext();
			return MyApplication.mcontext;
		}
		return MyApplication.mcontext;
	}
}
