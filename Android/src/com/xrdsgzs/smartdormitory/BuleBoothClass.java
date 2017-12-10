package com.xrdsgzs.smartdormitory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.xrdsgzs.smartdormitory.tool.L;
import com.xrdsgzs.smartdormitory.tool.MyApplication;
import com.xrdsgzs.smartdormitory.tool.T;

public class BuleBoothClass {
	public static BluetoothAdapter _bluetooth = BluetoothAdapter
			.getDefaultAdapter(); // 获取本地蓝牙适配器，即蓝牙设备
	public static String straddss = getAdress(MyApplication.getAppContext());;
	public final static int REQUEST_CONNECT_DEVICE = 1; // 宏定义查询设备句柄
	public final static String MY_UUID = "00001101-0000-1000-8000-00805F9B34FB"; // SPP服务UUID号
	static BluetoothDevice _device = null; // 蓝牙设备
	static BluetoothSocket _socket = null; // 蓝牙通信socket
	public static InputStream is; // 输入流，用来接收蓝牙数据
	static boolean bThread = false;
	static boolean bRun = true;
	public static String smsg = ""; // 显示用数据缓存
	public static short ftmsg = 0; // 保存用数据缓存
	public static short ColValue = 0x4000; // 主控制参数缓存
	public static int numrenew = 0;
	public static int activityfla = 0;// 表示目前打开的activity 1为MainActivity
										// 2为DeviceActivity

	public static void setaddress(String aa) {
		straddss = aa;
		saveAdress(MyApplication.getAppContext(), straddss);
		L.i("set:" + straddss);
	}

	public static String getaddress() {
		String aa = getAdress(MyApplication.getAppContext());
		L.i("get:" + aa);
		return aa;
	}

	public static void lianjieblue() {
//		straddss = getAdress(MyApplication.getAppContext());
		if (straddss != null) {
			// 得到蓝牙设备句柄
			_device = _bluetooth.getRemoteDevice(straddss);
			// 用服务号得到socket
			try {
				_socket = _device.createRfcommSocketToServiceRecord(UUID
						.fromString(MY_UUID));
			} catch (IOException e) {
				T.showLong(MyApplication.getAppContext(), "连接失败！");
			}
			try {
				_socket.connect();
				T.showLong(MyApplication.getAppContext(),
						"已连接" + _device.getName());

				MainActivity.ljhandler.sendMessage(MainActivity.ljhandler
						.obtainMessage());

				bthsend(ColValue);

				// btn.setText("断开");
			} catch (IOException e) {
				try {
					_socket.close();
				} catch (IOException ee) {
					T.showShort(MyApplication.getAppContext(), "连接失败！");
				}

				return;
			}


			// 打开接收线程
			try {
				is = _socket.getInputStream(); // 得到蓝牙数据输入流
			} catch (IOException e) {
				T.showShort(MyApplication.getAppContext(), "接收数据失败！");
				return;
			}
			if (bThread == false) {
				ReadThread.start();
				bThread = true;
			} else {
				bRun = true;
			}
		}
	}

	// 接收数据线程
	static Thread ReadThread = new Thread() {

		public void run() {
			int num = 0;
			byte[] buffer = new byte[2];
			int i = 0;
			// int n = 0;
			bRun = true;
			// 接收线程
			while (true) {
				try {
					while (is.available() == 0) {
						while (bRun == false) {
						}
					}
					while (true) {
						num = is.read(buffer); // 读入数据
						// n = 0;
						for (i = 0; i < num; i++) {
							ftmsg <<= 8;
							ftmsg |= (buffer[i] & 0x00ff);
							numrenew++;
							if (numrenew % 2 == 0) {
								ColValue = ftmsg;
								numrenew = 0;
								bthsend(ColValue);
								L.i("ColValue:" + ColValue);
								// 发送显示消息，进行显示刷新
								if (activityfla == 2) {
									DeviceActivity.dahandler
											.sendMessage(DeviceActivity.dahandler
													.obtainMessage());
								}
							}
						}
						if (is.available() == 0)
							break; // 短时间没有数据才跳出进行显示
					}

				} catch (IOException e) {
				}
			}
		}
	};

	// 蓝牙发送数据
	public static void bthsend(short CData) {
		if(_socket == null){
			L.i("null");
		}
		try {
			OutputStream os = _socket.getOutputStream(); // 蓝牙连接输出流
			byte[] buf = new byte[2];
			short CTemp = CData;
			L.i("bthsend:" + CData);
			for (int i = buf.length - 1; i >= 0; i--) {
				buf[i] = (byte) (CTemp & 0x00ff);
				CTemp >>= 8;
			}

			os.write(buf);
		} catch (IOException e) {
			L.i("发送出错");
			Log.e("test",e.toString());
			T.showShort(MyApplication.getAppContext(), "发送出错");
		}
	}

	/** 自动连接上次连接过的蓝牙地址 */
	public static String getAdress(Context context) {
		SharedPreferences prference;
		String BLUE_PREF_NAME = "BLUE_PREF_NAME";
		String ADDRESS = "address";
		prference = context.getSharedPreferences(BLUE_PREF_NAME, 0);
		String tmp = prference.getString(ADDRESS, null);
		return tmp;
	}

	/** 保存蓝牙地址，SharedPreferences保存 */
	public static void saveAdress(Context context, String mac) {
		if (context != null) {
			SharedPreferences prference;
			String BLUE_PREF_NAME = "BLUE_PREF_NAME";
			String ADDRESS = "address";
			prference = context.getSharedPreferences(BLUE_PREF_NAME, 0);
			prference.edit().putString(ADDRESS, mac).commit();
		}
	}

}
