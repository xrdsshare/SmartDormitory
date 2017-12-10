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
		SimpleDateFormat formatterd = new SimpleDateFormat("yyyy��MM��dd��");
		Date curDate = new Date(System.currentTimeMillis());// ��ȡ��ǰʱ��
		tv_sj.setText(formatter.format(curDate));
		tv_nyr.setText(formatterd.format(curDate));
		tv_xq.setText(wtran());

	}

	private String wtran() {
		String a = null;
		switch (Calendar.getInstance().get(Calendar.DAY_OF_WEEK)) {
		case 1:
			a = "������";
			break;

		case 2:
			a = "����һ";
			break;

		case 3:
			a = "���ڶ�";
			break;

		case 4:
			a = "������";
			break;

		case 5:
			a = "������";
			break;

		case 6:
			a = "������";
			break;

		case 7:
			a = "������";
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
							DeviceActivity.class); // ��ת��������
					startActivity(intent); // ���÷��غ궨��
				break;

			case R.id.ib_naozhong:
				Intent intentn = new Intent(MainActivity.this,
						AlarmActivity.class); // ��ת��������
				startActivity(intentn); // ���÷��غ궨��

				break;

			case R.id.ib_kechengbiao:
				Intent intentk = new Intent(MainActivity.this,
						ScheduleActivity.class); // ��ת��������
				startActivity(intentk); // ���÷��غ궨��

				break;

			case R.id.ib_shezhi:
				Intent intents = new Intent(MainActivity.this,
						SetActivity.class); // ��ת��������
				startActivity(intents); // ���÷��غ궨��

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
//			T.showShort(MyApplication.getAppContext(), "���ӳɹ�");
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
