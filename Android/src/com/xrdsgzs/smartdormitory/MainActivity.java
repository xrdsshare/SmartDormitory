package com.xrdsgzs.smartdormitory;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;
import com.umeng.analytics.MobclickAgent.EScenarioType;
import com.xrdsgzs.smartdormitory.tool.T;

public class MainActivity extends BaseActivity {
	static LinearLayout LinearLayout1;
	ImageButton ib_sbkz, ib_naozhong, ib_kechengbiao, ib_shezhi;
	TextView tv_sj, tv_nyr, tv_xq;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MobclickAgent.setScenarioType(getApplicationContext(),
				EScenarioType.E_UM_NORMAL);
		setContentView(R.layout.activity_main);
        
        BuleBoothClass.activityfla = 1;

		LinearLayout1 = (LinearLayout) findViewById(R.id.LinearLayout1);
		ib_sbkz = (ImageButton) findViewById(R.id.ib_sbkz);
		ib_naozhong = (ImageButton) findViewById(R.id.ib_naozhong);
		ib_kechengbiao = (ImageButton) findViewById(R.id.ib_kechengbiao);
		ib_shezhi = (ImageButton) findViewById(R.id.ib_shezhi);
		tv_sj = (TextView) findViewById(R.id.tv_sj);
		tv_nyr = (TextView) findViewById(R.id.tv_nyr);
		tv_xq = (TextView) findViewById(R.id.tv_xq);
		ib_sbkz.setOnClickListener(ibonclicklistener);
		ib_naozhong.setOnClickListener(ibonclicklistener);
		ib_kechengbiao.setOnClickListener(ibonclicklistener);
		ib_shezhi.setOnClickListener(ibonclicklistener);

		Calendar c = Calendar.getInstance();
		SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
		SimpleDateFormat formatterd = new SimpleDateFormat("yyyy年MM月dd日");
		Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
		tv_sj.setText(formatter.format(curDate));
		tv_nyr.setText(formatterd.format(curDate));
		tv_xq.setText(wtran());

	}

	private String wtran() {
		String a = null;
		switch (Calendar.getInstance().get(Calendar.DAY_OF_WEEK)) {
		case 1:
			a = "星期天";
			break;

		case 2:
			a = "星期一";
			break;

		case 3:
			a = "星期二";
			break;

		case 4:
			a = "星期三";
			break;

		case 5:
			a = "星期四";
			break;

		case 6:
			a = "星期五";
			break;

		case 7:
			a = "星期六";
			break;

		default:
			break;
		}

		return a;
	}

	private OnClickListener ibonclicklistener = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			switch (arg0.getId()) {
			case R.id.ib_sbkz:
					Intent intent = new Intent(MainActivity.this,
							DeviceActivity.class); // 跳转程序设置
					startActivity(intent); // 设置返回宏定义
				break;

			case R.id.ib_naozhong:
				Intent intentn = new Intent(MainActivity.this,
						AlarmActivity.class); // 跳转程序设置
				startActivity(intentn); // 设置返回宏定义

				break;

			case R.id.ib_kechengbiao:
				Intent intentk = new Intent(MainActivity.this,
						ScheduleActivity.class); // 跳转程序设置
				startActivity(intentk); // 设置返回宏定义

				break;

			case R.id.ib_shezhi:
				Intent intents = new Intent(MainActivity.this,
						SetActivity.class); // 跳转程序设置
				startActivity(intents); // 设置返回宏定义

				break;

			default:
				break;
			}
		}
	};

	public static Handler mhandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			LinearLayout1.invalidate();
			super.handleMessage(msg);
		}

	};

	public static Handler ljhandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
//			T.showShort(MyApplication.getAppContext(), "连接成功");
//			getWindow().setTitle("title");
			super.handleMessage(msg);
		}

	};
	
	public void isbulebooth() {
		if (BuleBoothClass._bluetooth != null) {
			if (!BuleBoothClass._bluetooth.isEnabled()) {
				Intent intent = new Intent(
						BluetoothAdapter.ACTION_REQUEST_ENABLE);
				startActivity(intent);
			}
		}
	};
}
