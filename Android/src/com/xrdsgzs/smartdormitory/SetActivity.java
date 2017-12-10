package com.xrdsgzs.smartdormitory;

import java.util.Calendar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xrdsgzs.kechengbiao.LoginActivity;
import com.xrdsgzs.kechengbiao.util.SharedPreferenceUtil;

public class SetActivity extends BaseActivity {
	Button btn_drkkb, btn_czsj, btn_zdjz, btn_qd, btn_wdjz, btn_sdjz, btn_wqjz;
	LinearLayout ll_3;
	EditText et_wdjz, et_sdjz, et_wqjz, et_y, et_m, et_d, et_h, et_f, et_c, et_w;
	boolean wd, sd, gq, sk, ks, kd, fs;
	ImageButton ib_znkfs, ib_znkd, ib_kstx, ib_sktx;
	Short TColValue;
	TextView tv_kstxd, tv_znkdd, tv_znkfsd, tv_sktxd;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_set0);
        
        BuleBoothClass.activityfla = 3;

		wd = sd = gq = sk = ks = kd = fs = true;

		ll_3 = (LinearLayout) findViewById(R.id.ll_3);
		et_wdjz = (EditText) findViewById(R.id.et_wdjz);
		et_sdjz = (EditText) findViewById(R.id.et_sdjz);
		et_wqjz = (EditText) findViewById(R.id.et_wqjz);
		et_y = (EditText) findViewById(R.id.et_y);
		et_m = (EditText) findViewById(R.id.et_m);
		et_d = (EditText) findViewById(R.id.et_d);
		et_h = (EditText) findViewById(R.id.et_h);
		et_f = (EditText) findViewById(R.id.et_f);
		et_c = (EditText) findViewById(R.id.et_c);
		et_w = (EditText) findViewById(R.id.et_w);
		btn_drkkb = (Button) findViewById(R.id.btn_drkkb);
		btn_czsj = (Button) findViewById(R.id.btn_czsj);
		btn_zdjz = (Button) findViewById(R.id.btn_zdjz);
		btn_qd = (Button) findViewById(R.id.btn_qd);
		btn_wdjz = (Button) findViewById(R.id.btn_wdjz);
		btn_sdjz = (Button) findViewById(R.id.btn_sdjz);
		btn_wqjz = (Button) findViewById(R.id.btn_wqjz);
		ib_sktx = (ImageButton) findViewById(R.id.ib_sktx);
		ib_znkfs = (ImageButton) findViewById(R.id.ib_znkfs);
		ib_znkd = (ImageButton) findViewById(R.id.ib_znkd);
		ib_kstx = (ImageButton) findViewById(R.id.ib_kstx);
		tv_kstxd = (TextView) findViewById(R.id.tv_kstxd);
		tv_znkdd = (TextView) findViewById(R.id.tv_znkdd);
		tv_znkfsd = (TextView) findViewById(R.id.tv_znkfsd);
		tv_sktxd = (TextView) findViewById(R.id.tv_sktxd);

		btn_drkkb.setOnClickListener(OnClickListener);
		btn_czsj.setOnClickListener(OnClickListener);
		btn_zdjz.setOnClickListener(OnClickListener);
		btn_qd.setOnClickListener(OnClickListener);
		btn_wdjz.setOnClickListener(OnClickListener);
		btn_sdjz.setOnClickListener(OnClickListener);
		btn_wqjz.setOnClickListener(OnClickListener);
		ib_sktx.setOnClickListener(ibOnClickListener);
		ib_znkfs.setOnClickListener(ibOnClickListener);
		ib_znkd.setOnClickListener(ibOnClickListener);
		ib_kstx.setOnClickListener(ibOnClickListener);
	}

	private android.view.View.OnClickListener ibOnClickListener = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			switch (arg0.getId()) {
			case R.id.ib_sktx:
				if (sk) {
					ib_sktx.setBackgroundResource(R.drawable.off);
					tv_sktxd.setTextColor(0x00FFFFFF);
					sk = false;
				} else {
					ib_sktx.setBackgroundResource(R.drawable.on);
					tv_sktxd.setTextColor(0xffffff00);
					sk = true;
				}
				break;

			case R.id.ib_kstx:
				if (ks) {
					ib_kstx.setBackgroundResource(R.drawable.off);
					tv_kstxd.setTextColor(0x00FFFFFF);
					ks = false;
				} else {
					ib_kstx.setBackgroundResource(R.drawable.on);
					tv_kstxd.setTextColor(0xffffff00);
					ks = true;
				}
				break;

			case R.id.ib_znkfs:
				if (fs) {
					ib_znkfs.setBackgroundResource(R.drawable.off);
					tv_znkfsd.setTextColor(0x00FFFFFF);
					fs = false;
				} else {
					ib_znkfs.setBackgroundResource(R.drawable.on);
					tv_znkfsd.setTextColor(0xffffff00);
					fs = true;
				}
				break;

			case R.id.ib_znkd:
				if (kd) {
					ib_znkd.setBackgroundResource(R.drawable.off);
					tv_znkdd.setTextColor(0x00FFFFFF);
					kd = false;
				} else {
					ib_znkd.setBackgroundResource(R.drawable.on);
					tv_znkdd.setTextColor(0xffffff00);
					kd = true;
				}
				break;

			default:
				break;
			}
		}
	};

	private OnClickListener OnClickListener = new OnClickListener() {
//		Intent intent;
		short yeart, mont, dayt, hourt, mint, cont, weekt;
		short twd;

		@Override
		public void onClick(View arg0) {
			switch (arg0.getId()) {
			case R.id.btn_drkkb:
				SharedPreferenceUtil util = new SharedPreferenceUtil(
						getApplicationContext(), "accountInfo");
				String isLogin = util.getKeyData("isLogin");
				//是否已登录
				if (isLogin.equals("TRUE")) {
					Intent intent = new Intent(SetActivity.this,
							ScheduleActivity.class);
					startActivity(intent);
					finish();
				} else {
					Intent intent = new Intent(SetActivity.this,
							LoginActivity.class);
					startActivity(intent);
					finish();
				}
				break;

			case R.id.btn_czsj:
				ll_3.setVisibility(LinearLayout.VISIBLE);
				break;

			case R.id.btn_zdjz:

				ll_3.setVisibility(LinearLayout.GONE);
				Calendar c = Calendar.getInstance();
				yeart = (short) (c.get(Calendar.YEAR) % 100);
				mont = (short) (c.get(Calendar.MONTH) + 1);
				dayt = (short) c.get(Calendar.DAY_OF_MONTH);
				hourt = (short) c.get(Calendar.HOUR_OF_DAY);
				mint = (short) c.get(Calendar.MINUTE);
				cont = (short) c.get(Calendar.SECOND);
				weekt = (short) (c.get(Calendar.DAY_OF_WEEK) - 1);
				if(weekt == 0)
					weekt = (short)7;

				Short aa = 0X6002;
				BuleBoothClass.bthsend(aa);
				BuleBoothClass.bthsend(yeart);
				BuleBoothClass.bthsend(mont);
				BuleBoothClass.bthsend(dayt);
				BuleBoothClass.bthsend(hourt);
				BuleBoothClass.bthsend(mint);
				BuleBoothClass.bthsend(cont);
				BuleBoothClass.bthsend(weekt);
				break;

			case R.id.btn_qd:
				ll_3.setVisibility(LinearLayout.GONE);
				
				yeart = (short)(Integer.valueOf(et_y.getText().toString()).intValue()%100);
				mont = (short) Integer.valueOf(et_m.getText().toString()).intValue();
				dayt = (short) Integer.valueOf(et_d.getText().toString()).intValue();
				hourt = (short) Integer.valueOf(et_h.getText().toString()).intValue();
				mint = (short) Integer.valueOf(et_f.getText().toString()).intValue();
				cont = (short) Integer.valueOf(et_c.getText().toString()).intValue();
				weekt = (short) Integer.valueOf(et_w.getText().toString()).intValue();
				
				Short aaa = 0X6002;
				BuleBoothClass.bthsend(aaa);
				BuleBoothClass.bthsend(yeart);
				BuleBoothClass.bthsend(mont);
				BuleBoothClass.bthsend(dayt);
				BuleBoothClass.bthsend(hourt);
				BuleBoothClass.bthsend(mint);
				BuleBoothClass.bthsend(cont);
				BuleBoothClass.bthsend(weekt);
				break;

			case R.id.btn_wdjz:
				if (wd) {
					btn_wdjz.setText("确定");
					et_wdjz.setVisibility(EditText.VISIBLE);
//					TColValue = DeviceActivity.ColValue;
					wd = false;
				} else {
					btn_wdjz.setText("温度校正");
					et_wdjz.setVisibility(EditText.GONE);
					
					Short stwd = 0X6005;
					BuleBoothClass.bthsend(stwd);
					
					twd = (short) Integer.valueOf(et_wdjz.getText().toString()).intValue();
					BuleBoothClass.bthsend(twd);
					
					wd = true;
				}
				break;

			case R.id.btn_sdjz:
				if (sd) {
					btn_sdjz.setText("确定");
					et_sdjz.setVisibility(EditText.VISIBLE);
					sd = false;
				} else {
					btn_sdjz.setText("湿度校正");
					
					Short stsd = 0X6006;
					BuleBoothClass.bthsend(stsd);
					
					twd = (short) Integer.valueOf(et_sdjz.getText().toString()).intValue();
					BuleBoothClass.bthsend(twd);

					et_sdjz.setVisibility(EditText.GONE);
					sd = true;
				}
				break;

			case R.id.btn_wqjz:
				if (gq) {
					btn_wqjz.setText("确定");
					et_wqjz.setVisibility(EditText.VISIBLE);
					gq = false;
				} else {
					btn_wqjz.setText("光强校正");
					
					Short stwq = 0X6007;
					BuleBoothClass.bthsend(stwq);
					
					twd = (short) Integer.valueOf(et_wqjz.getText().toString()).intValue();
					BuleBoothClass.bthsend(twd);

					et_wqjz.setVisibility(EditText.GONE);
					gq = true;
				}
				break;

			default:
				break;
			}
		}
	};
	
	public void isbulebooth() {
		
	};

}
