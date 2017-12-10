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

		initValue();// ������ʼ��
		initView();// ��ͼ��ʼ��
	}

	/**
	 * ��ʼ������
	 */
	private void initValue() {
		linkService = LinkService.getLinkService();
		courseService = CourseService.getCourseService();
	}

	/**
	 * ��ʼ��View
	 */
	private void initView() {
		jump2Kb(false);
	}

	/**
	 * �����α�ҳ��
	 */
	private void jump2Kb(boolean flag) {
		SharedPreferenceUtil util = new SharedPreferenceUtil(
				getApplicationContext(), "flag");
		if (flag) {
			// flagΪtrueֱ����ת
			util.setKeyData(LinkUtil.XSGRKB, "TRUE");
			Intent intent = new Intent(ScheduleActivity.this,
					CourseActivity.class);
			startActivity(intent);
			finish();
		} else {
			// flagΪfalse�������ж��Ƿ��ȡ���α�
			// ����Ѿ���ȡ���α�����ת
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
					// δ��ȡ���ȡ
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
					T.showShort(getApplicationContext(), "δ����α�");
				}

			}
		}
	}

	@Override
	public void isbulebooth() {
		// super.isbulebooth();
	}
}
