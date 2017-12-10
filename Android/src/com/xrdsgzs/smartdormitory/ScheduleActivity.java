package com.xrdsgzs.smartdormitory;

import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.Set;

import com.xrdsgzs.kechengbiao.CourseActivity;
import com.xrdsgzs.kechengbiao.service.CourseService;
import com.xrdsgzs.kechengbiao.service.LinkService;
import com.xrdsgzs.kechengbiao.util.HttpUtil;
import com.xrdsgzs.kechengbiao.util.LinkUtil;
import com.xrdsgzs.kechengbiao.util.SharedPreferenceUtil;
import com.xrdsgzs.kechengbiao.util.HttpUtil.QueryCallback;
import com.xrdsgzs.smartdormitory.tool.T;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class ScheduleActivity extends BaseActivity {
	private LinkService linkService;
	private CourseService courseService;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		BuleBoothClass.activityfla = 5;

		initValue();// 变量初始化
		initView();// 视图初始话
	}

	/**
	 * 初始化变量
	 */
	private void initValue() {
		linkService = LinkService.getLinkService();
		courseService = CourseService.getCourseService();
	}

	/**
	 * 初始化View
	 */
	private void initView() {
		jump2Kb(false);
	}

	/**
	 * 跳到课表页面
	 */
	private void jump2Kb(boolean flag) {
		SharedPreferenceUtil util = new SharedPreferenceUtil(
				getApplicationContext(), "flag");
		if (flag) {
			// flag为true直接跳转
			util.setKeyData(LinkUtil.XSGRKB, "TRUE");
			Intent intent = new Intent(ScheduleActivity.this,
					CourseActivity.class);
			startActivity(intent);
			finish();
		} else {
			// flag为false，则先判断是否获取过课表
			// 如果已经获取过课表，则跳转
			String keyData = util.getKeyData(LinkUtil.XSGRKB);
			if (keyData.equals("TRUE")) {
				Intent intent = new Intent(ScheduleActivity.this,
						CourseActivity.class);
				startActivity(intent);
				finish();
			} else {
				SharedPreferenceUtil isutil = new SharedPreferenceUtil(
						getApplicationContext(), "accountInfo");
				if (isutil.getKeyData("isLogin") == "TRUE") {
					// 未获取则获取
					HttpUtil.getQuery(ScheduleActivity.this, linkService,
							LinkUtil.XSGRKB, new QueryCallback() {
								@Override
								public String handleResult(byte[] result) {
									String ret = null;
									try {
										ret = courseService
												.parseCourse(new String(result,
														"gb2312"));
									} catch (UnsupportedEncodingException e) {
										e.printStackTrace();
									}
									jump2Kb(true);
									return ret;
								}
							});
				}else {
					T.showShort(getApplicationContext(), "未导入课表");
				}

			}
		}
	}

	@Override
	public void isbulebooth() {
		// super.isbulebooth();
	}
}
