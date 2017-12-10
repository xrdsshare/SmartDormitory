package com.xrdsgzs.smartdormitory;

import java.util.Iterator;
import java.util.Set;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.umeng.analytics.MobclickAgent;
import com.xrdsgzs.smartdormitory.tool.L;

public class BaseActivity extends ActionBarActivity {
	String address = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		android.app.ActionBar actionBar = getActionBar();
		actionBar.setDisplayShowHomeEnabled(false);
		isbulebooth();
		// if (BuleBoothClass.straddss != null) {
		// BuleBoothClass.lianjieblue();
		// setTitle(BuleBoothClass._device.getName());
		// }

		// if(!BuleBoothClass.blainjie){
		// dialog();
		// }
	}

	public void isbulebooth() {
		if (BuleBoothClass._bluetooth != null) {
			if (!BuleBoothClass._bluetooth.isEnabled()) {
				Intent intent = new Intent(
						BluetoothAdapter.ACTION_REQUEST_ENABLE);
				startActivity(intent);
			}
			Set<BluetoothDevice> devices = BuleBoothClass._bluetooth
					.getBondedDevices();
			if (devices.size() > 0) {
				for (Iterator iterator = devices.iterator(); iterator.hasNext();) {
					BluetoothDevice bluetoothDevice = (BluetoothDevice) iterator
							.next();
					L.i("111:"+ bluetoothDevice.getAddress());
					if (bluetoothDevice.getAddress().toString() == BuleBoothClass
							.getaddress()) {
						L.i("222");
					}
				}
			}
			if (BuleBoothClass._bluetooth.isEnabled()) {
				if (BuleBoothClass.getaddress() == null) {
					dialog();
				} else {
						dialog();
				}
			}
		}
	}

	protected void dialog() {
		AlertDialog.Builder builder = new Builder(BaseActivity.this);
		builder.setMessage("是否连接蓝牙设备");
		builder.setTitle("提示");
		builder.setPositiveButton("连接", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				Intent serverIntent = new Intent(getApplicationContext(),
						DeviceListActivity.class); // 跳转程序设置
				startActivityForResult(serverIntent, 1); // 设置返回宏定义
				dialog.dismiss();
			}
		});
		builder.setNegativeButton("取消", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		builder.create().show();
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		MobclickAgent.onResume(getApplicationContext());
	}

	@Override
	protected void onPause() {
		super.onPause();
		MobclickAgent.onPause(getApplicationContext());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			Intent serverIntent = new Intent(getApplicationContext(),
					DeviceListActivity.class); // 跳转程序设置
			startActivityForResult(serverIntent, 1); // 设置返回宏定义
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		switch (arg0) {
		case 1:
			if (arg1 == Activity.RESULT_OK) {
				address = arg2.getExtras().getString(
						DeviceListActivity.EXTRA_DEVICE_ADDRESS);
				BuleBoothClass.setaddress(address);
				BuleBoothClass.lianjieblue();
				setTitle(BuleBoothClass._device.getName());
			}
			break;

		default:
			break;
		}
		super.onActivityResult(arg0, arg1, arg2);
	}

}
