
package com.xrdsgzs.kechengbiao.util;

import android.app.ProgressDialog;
import android.content.Context;


/**
 * ����������
 * @author lizhangqu
 * @date 2015-2-1
 */
public class CommonUtil {
	
	/**���ָ����Χ�ڵ������
	 * @param max
	 * @return int
	 */
	public static int getRandom(int max){
		return (int)(Math.random()*max);
	}
	
	/**
	 * ����һ���Ի���
	 * @param context
	 * @param tips
	 * @return
	 */
	public static ProgressDialog getProcessDialog(Context context,String tips){
		ProgressDialog dialog = new ProgressDialog(context);
		dialog.setMessage(tips);
		dialog.setCancelable(false);
		return dialog;
	}
	
}
