package com.xrdsgzs.smartdormitory;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TableLayout;

import com.xrdsgzs.smartdormitory.tool.L;
import com.xrdsgzs.smartdormitory.tool.T;

public class DeviceActivity extends BaseActivity {
	private static ImageButton ib_l0, ib_l1, ib_l2, ib_l3;
	private static ImageButton ib_m0, ib_m1, ib_m2;
	private static ImageButton ib_i0, ib_i1, ib_i2, ib_i3, ib_i4;
	private static Button btn_dc0;
	private static TableLayout tl_deviceactivity;
//	private short BuleBoothClass.ColValue = 0X4000;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device);
        
        BuleBoothClass.activityfla = 2;
        
        ib_l0 = (ImageButton) findViewById(R.id.ib_l0);
        ib_l1 = (ImageButton) findViewById(R.id.ib_l1);
        ib_l2 = (ImageButton) findViewById(R.id.ib_l2);
        ib_l3 = (ImageButton) findViewById(R.id.ib_l3);

        ib_m1 = (ImageButton) findViewById(R.id.ib_m1);
        ib_m2 = (ImageButton) findViewById(R.id.ib_m2);

        ib_i0 = (ImageButton) findViewById(R.id.ib_i0);
        ib_i1 = (ImageButton) findViewById(R.id.ib_i1);
        ib_i2 = (ImageButton) findViewById(R.id.ib_i2);
        ib_i3 = (ImageButton) findViewById(R.id.ib_i3);
        ib_i4 = (ImageButton) findViewById(R.id.ib_i4);

        btn_dc0 = (Button) findViewById(R.id.btn_dc0);
        
        tl_deviceactivity = (TableLayout) findViewById(R.id.TableLayout1);
        
        ib_l0.setOnClickListener(ibonclicklistener);
        ib_l1.setOnClickListener(ibonclicklistener);
        ib_l2.setOnClickListener(ibonclicklistener);
        ib_l3.setOnClickListener(ibonclicklistener);
        ib_m1.setOnClickListener(ibonclicklistener);
        ib_m2.setOnClickListener(ibonclicklistener);
        ib_i0.setOnClickListener(ibonclicklistener);
        ib_i1.setOnClickListener(ibonclicklistener);
        ib_i2.setOnClickListener(ibonclicklistener);
        ib_i3.setOnClickListener(ibonclicklistener);
        ib_i4.setOnClickListener(ibonclicklistener);
        btn_dc0.setOnClickListener(ibonclicklistener);
        
//        if (BuleBoothClass.straddss != null) {
//        	setTitle(BuleBoothClass._device.getName());
//        }
	}
	
	private OnClickListener ibonclicklistener = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			switch (arg0.getId()) {
			case R.id.ib_l0:
				BuleBoothClass.ColValue ^= 0x0001;
				L.i("ColValue：" + BuleBoothClass.ColValue);
				if (BuleBoothClass.ColValue == 0x0001) {
					T.showShort(getApplicationContext(), "已开灯");
				} else {
					T.showShort(getApplicationContext(), "已关灯");
				}
				BuleBoothClass.bthsend(BuleBoothClass.ColValue);
				// 发送显示消息，进行显示刷新
				dahandler.sendMessage(dahandler.obtainMessage());
				break;

			case R.id.ib_l1:
				BuleBoothClass.ColValue ^= 0x0002;
				L.i("ColValue：" + BuleBoothClass.ColValue);
				if (BuleBoothClass.ColValue == 0x0002) {
					T.showShort(getApplicationContext(), "已开灯");
				} else {
					T.showShort(getApplicationContext(), "已关灯");
				}
				BuleBoothClass.bthsend(BuleBoothClass.ColValue);
				// 发送显示消息，进行显示刷新
				dahandler.sendMessage(dahandler.obtainMessage());
				break;

			case R.id.ib_l2:
				BuleBoothClass.ColValue ^= 0x0004;
				L.i("ColValue：" + BuleBoothClass.ColValue);
				if (BuleBoothClass.ColValue == 0x0004) {
					T.showShort(getApplicationContext(), "已开灯");
				} else {
					T.showShort(getApplicationContext(), "已关灯");
				}
				BuleBoothClass.bthsend(BuleBoothClass.ColValue);
				// 发送显示消息，进行显示刷新
				dahandler.sendMessage(dahandler.obtainMessage());
				break;

			case R.id.ib_l3:
				BuleBoothClass.ColValue ^= 0x0008;
				L.i("ColValue：" + BuleBoothClass.ColValue);
				if (BuleBoothClass.ColValue == 0x0008) {
					T.showShort(getApplicationContext(), "已开灯");
				} else {
					T.showShort(getApplicationContext(), "已关灯");
				}
				BuleBoothClass.bthsend(BuleBoothClass.ColValue);
				// 发送显示消息，进行显示刷新
				dahandler.sendMessage(dahandler.obtainMessage());
				break;

			case R.id.ib_m1:
				BuleBoothClass.ColValue ^= 0x0020;
				L.i("ColValue：" + BuleBoothClass.ColValue);
				if (BuleBoothClass.ColValue == 0x0020) {
					T.showShort(getApplicationContext(), "已开风扇");
				} else {
					T.showShort(getApplicationContext(), "已关风扇");
				}
				BuleBoothClass.bthsend(BuleBoothClass.ColValue);
				// 发送显示消息，进行显示刷新
				dahandler.sendMessage(dahandler.obtainMessage());
				break;

			case R.id.ib_m2:
				BuleBoothClass.ColValue ^= 0x0040;
				L.i("ColValue：" + BuleBoothClass.ColValue);
				if (BuleBoothClass.ColValue == 0x0040) {
					T.showShort(getApplicationContext(), "已开风扇");
				} else {
					T.showShort(getApplicationContext(), "已关风扇");
				}
				BuleBoothClass.bthsend(BuleBoothClass.ColValue);
				// 发送显示消息，进行显示刷新
				dahandler.sendMessage(dahandler.obtainMessage());
				break;

			case R.id.ib_i0:
				BuleBoothClass.ColValue ^= 0x0080;
				L.i("ColValue：" + BuleBoothClass.ColValue);
				if (BuleBoothClass.ColValue == 0x0080) {
					T.showShort(getApplicationContext(), "已开插座");
				} else {
					T.showShort(getApplicationContext(), "已关插座");
				}
				BuleBoothClass.bthsend(BuleBoothClass.ColValue);
				// 发送显示消息，进行显示刷新
				dahandler.sendMessage(dahandler.obtainMessage());
				break;

			case R.id.ib_i1:
				BuleBoothClass.ColValue ^= 0x0100;
				L.i("ColValue：" + BuleBoothClass.ColValue);
				if (BuleBoothClass.ColValue == 0x0100) {
					T.showShort(getApplicationContext(), "已开插座");
				} else {
					T.showShort(getApplicationContext(), "已关插座");
				}
				BuleBoothClass.bthsend(BuleBoothClass.ColValue);
				// 发送显示消息，进行显示刷新
				dahandler.sendMessage(dahandler.obtainMessage());
				break;

			case R.id.ib_i2:
				BuleBoothClass.ColValue ^= 0x0200;
				L.i("ColValue：" + BuleBoothClass.ColValue);
				if (BuleBoothClass.ColValue == 0x0200) {
					T.showShort(getApplicationContext(), "已开插座");
				} else {
					T.showShort(getApplicationContext(), "已关插座");
				}
				BuleBoothClass.bthsend(BuleBoothClass.ColValue);
				// 发送显示消息，进行显示刷新
				dahandler.sendMessage(dahandler.obtainMessage());
				break;

			case R.id.ib_i3:
				BuleBoothClass.ColValue ^= 0x0400;
				L.i("ColValue：" + BuleBoothClass.ColValue);
				if (BuleBoothClass.ColValue == 0x0400) {
					T.showShort(getApplicationContext(), "已开插座");
				} else {
					T.showShort(getApplicationContext(), "已关插座");
				}
				BuleBoothClass.bthsend(BuleBoothClass.ColValue);
				// 发送显示消息，进行显示刷新
				dahandler.sendMessage(dahandler.obtainMessage());
				break;

			case R.id.ib_i4:
				BuleBoothClass.ColValue ^= 0x0800;
				L.i("ColValue：" + BuleBoothClass.ColValue);
				if (BuleBoothClass.ColValue == 0x0800) {
					T.showShort(getApplicationContext(), "已开插座");
				} else {
					T.showShort(getApplicationContext(), "已关插座");
				}
				BuleBoothClass.bthsend(BuleBoothClass.ColValue);
				// 发送显示消息，进行显示刷新
				dahandler.sendMessage(dahandler.obtainMessage());
				break;

			case R.id.btn_dc0:
//				BuleBoothClass.ColValue ^= 0x1000;
//				if (BuleBoothClass.ColValue == 0x1000) {
////					T.showShort(getApplicationContext(), "已开门");
//				} else {
////					T.showShort(getApplicationContext(), "已关门");
//				}
				T.showShort(getApplicationContext(), "已开门");
				Short sdc = 0x6008;
				L.i("ColValue：" + sdc);
				BuleBoothClass.bthsend(sdc);
				// 发送显示消息，进行显示刷新
				dahandler.sendMessage(dahandler.obtainMessage());
				break;

			default:
				break;
			}
		}
	};

	public static Handler dahandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			renew();
			tl_deviceactivity.invalidate();
			super.handleMessage(msg);
		}

	};

	private static void renew() {
		if ((BuleBoothClass.ColValue & 0x0001) == 0x0001) {
			ib_l0.setBackgroundResource(R.drawable.light_open);
		} else if ((BuleBoothClass.ColValue & 0x0001) == 0) {
			ib_l0.setBackgroundResource(R.drawable.light_close);
		}
		if ((BuleBoothClass.ColValue & 0x0002) == 0x0002) {
			ib_l1.setBackgroundResource(R.drawable.light_open);
		} else if ((BuleBoothClass.ColValue & 0x0002) == 0) {
			ib_l1.setBackgroundResource(R.drawable.light_close);
		}
		if ((BuleBoothClass.ColValue & 0x0004) == 0x0004) {
			ib_l2.setBackgroundResource(R.drawable.light_open);
		} else if ((BuleBoothClass.ColValue & 0x0004) == 0) {
			ib_l2.setBackgroundResource(R.drawable.light_close);
		}
		if ((BuleBoothClass.ColValue & 0x0008) == 0x0008) {
			ib_l3.setBackgroundResource(R.drawable.light_open);
		} else if ((BuleBoothClass.ColValue & 0x0008) == 0) {
			ib_l3.setBackgroundResource(R.drawable.light_close);
		}
		if ((BuleBoothClass.ColValue & 0x0020) == 0x0020) {
			ib_m1.setBackgroundResource(R.drawable.fanon);
		} else if ((BuleBoothClass.ColValue & 0x0020) == 0) {
			ib_m1.setBackgroundResource(R.drawable.fanoff);
		}
		if ((BuleBoothClass.ColValue & 0x0040) == 0x0040) {
			ib_m2.setBackgroundResource(R.drawable.fanon);
		} else if ((BuleBoothClass.ColValue & 0x0040) == 0) {
			ib_m2.setBackgroundResource(R.drawable.fanoff);
		}
		if ((BuleBoothClass.ColValue & 0x0080) == 0x0080) {
			ib_i0.setBackgroundResource(R.drawable.device_cz1);
		} else if ((BuleBoothClass.ColValue & 0x0080) == 0) {
			ib_i0.setBackgroundResource(R.drawable.device_cz);
		}
		if ((BuleBoothClass.ColValue & 0x0100) == 0x0100) {
			ib_i1.setBackgroundResource(R.drawable.device_cz1);
		} else if ((BuleBoothClass.ColValue & 0x0100) == 0) {
			ib_i1.setBackgroundResource(R.drawable.device_cz);
		}
		if ((BuleBoothClass.ColValue & 0x0200) == 0x0200) {
			ib_i2.setBackgroundResource(R.drawable.device_cz1);
		} else if ((BuleBoothClass.ColValue & 0x0200) == 0) {
			ib_i2.setBackgroundResource(R.drawable.device_cz);
		}
		if ((BuleBoothClass.ColValue & 0x0400) == 0x0400) {
			ib_i3.setBackgroundResource(R.drawable.device_cz1);
		} else if ((BuleBoothClass.ColValue & 0x0400) == 0) {
			ib_i3.setBackgroundResource(R.drawable.device_cz);
		}
		if ((BuleBoothClass.ColValue & 0x0800) == 0x0800) {
			ib_i4.setBackgroundResource(R.drawable.device_cz1);
		} else if ((BuleBoothClass.ColValue & 0x0800) == 0) {
			ib_i4.setBackgroundResource(R.drawable.device_cz);
		}
		if ((BuleBoothClass.ColValue & 0x1000) == 0x1000) {
			// ib_dc0.setBackgroundResource(R.drawable.toggle_me_on);
		} else if ((BuleBoothClass.ColValue & 0x1000) == 0) {
			// ib_dc0.setBackgroundResource(R.drawable.toggle_me_off);
		}
	}
}
