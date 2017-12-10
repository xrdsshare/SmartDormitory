package com.xrdsgzs.smartdormitory;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class AlarmActivity extends BaseActivity {
	TextView tv_nz11, tv_nz22, tv_nz33, tv_nz44, tv_nz55;
	ImageButton ib_nz1, ib_nz2, ib_nz3, ib_nz4, ib_nz5;
	boolean nz11, nz22, nz33, nz44, nz55;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_alarm);
        
        BuleBoothClass.activityfla = 4;
        
        tv_nz11 = (TextView) findViewById(R.id.tv_nz11);
        tv_nz22 = (TextView) findViewById(R.id.tv_nz22);
        tv_nz33 = (TextView) findViewById(R.id.tv_nz33);
        tv_nz44 = (TextView) findViewById(R.id.tv_nz44);
        tv_nz55 = (TextView) findViewById(R.id.tv_nz55);
        ib_nz1 = (ImageButton) findViewById(R.id.ib_nz1);
        ib_nz2 = (ImageButton) findViewById(R.id.ib_nz2);
        ib_nz3 = (ImageButton) findViewById(R.id.ib_nz3);
        ib_nz4 = (ImageButton) findViewById(R.id.ib_nz4);
        ib_nz5 = (ImageButton) findViewById(R.id.ib_nz5);
	}
	
	@Override
	public void isbulebooth() {
	}

	public void nzonclick(View view) {
		switch (view.getId()) {
		case R.id.ib_nz1:
			if (nz11) {
				ib_nz1.setBackgroundResource(R.drawable.off);
				tv_nz11.setTextColor(0x00FFFFFF);
				nz11 = false;
			} else {
				ib_nz1.setBackgroundResource(R.drawable.on);
				tv_nz11.setTextColor(0xffffff00);
				nz11 = true;
			}
			break;

		case R.id.ib_nz2:
			if (nz22) {
				ib_nz2.setBackgroundResource(R.drawable.off);
				tv_nz22.setTextColor(0x00FFFFFF);
				nz22 = false;
			} else {
				ib_nz2.setBackgroundResource(R.drawable.on);
				tv_nz22.setTextColor(0xffffff00);
				nz22 = true;
			}
			break;

		case R.id.ib_nz3:
			if (nz33) {
				ib_nz3.setBackgroundResource(R.drawable.off);
				tv_nz33.setTextColor(0x00FFFFFF);
				nz33 = false;
			} else {
				ib_nz3.setBackgroundResource(R.drawable.on);
				tv_nz33.setTextColor(0xffffff00);
				nz33 = true;
			}
			break;

		case R.id.ib_nz4:
			if (nz44) {
				ib_nz4.setBackgroundResource(R.drawable.off);
				tv_nz44.setTextColor(0x00FFFFFF);
				nz11 = false;
			} else {
				ib_nz4.setBackgroundResource(R.drawable.on);
				tv_nz44.setTextColor(0xffffff00);
				nz44 = true;
			}
			break;

		case R.id.ib_nz5:
			if (nz55) {
				ib_nz5.setBackgroundResource(R.drawable.off);
				tv_nz55.setTextColor(0x00FFFFFF);
				nz55 = false;
			} else {
				ib_nz5.setBackgroundResource(R.drawable.on);
				tv_nz55.setTextColor(0xffffff00);
				nz55 = true;
			}
			break;

		default:
			break;
		}
	}
}
