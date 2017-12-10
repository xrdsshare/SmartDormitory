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
			.getDefaultAdapter(); // ��ȡ�����������������������豸
	public static String straddss = getAdress(MyApplication.getAppContext());;
	public final static int REQUEST_CONNECT_DEVICE = 1; // �궨���ѯ�豸���
	public final static String MY_UUID = "00001101-0000-1000-8000-00805F9B34FB"; // SPP����UUID��
	static BluetoothDevice _device = null; // �����豸
	static BluetoothSocket _socket = null; // ����ͨ��socket
	public static InputStream is; // ������������������������
	static boolean bThread = false;
	static boolean bRun = true;
	public static String smsg = ""; // ��ʾ�����ݻ���
	public static short ftmsg = 0; // ���������ݻ���
	public static short ColValue = 0x4000; // �����Ʋ�������
	public static int numrenew = 0;
	public static int activityfla = 0;// ��ʾĿǰ�򿪵�activity 1ΪMainActivity
										// 2ΪDeviceActivity

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
			// �õ������豸���
			_device = _bluetooth.getRemoteDevice(straddss);
			// �÷���ŵõ�socket
			try {
				_socket = _device.createRfcommSocketToServiceRecord(UUID
						.fromString(MY_UUID));
			} catch (IOException e) {
				T.showLong(MyApplication.getAppContext(), "����ʧ�ܣ�");
			}
			try {
				_socket.connect();
				T.showLong(MyApplication.getAppContext(),
						"������" + _device.getName());

				MainActivity.ljhandler.sendMessage(MainActivity.ljhandler
						.obtainMessage());

				bthsend(ColValue);

				// btn.setText("�Ͽ�");
			} catch (IOException e) {
				try {
					_socket.close();
				} catch (IOException ee) {
					T.showShort(MyApplication.getAppContext(), "����ʧ�ܣ�");
				}

				return;
			}


			// �򿪽����߳�
			try {
				is = _socket.getInputStream(); // �õ���������������
			} catch (IOException e) {
				T.showShort(MyApplication.getAppContext(), "��������ʧ�ܣ�");
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

	// ���������߳�
	static Thread ReadThread = new Thread() {

		public void run() {
			int num = 0;
			byte[] buffer = new byte[2];
			int i = 0;
			// int n = 0;
			bRun = true;
			// �����߳�
			while (true) {
				try {
					while (is.available() == 0) {
						while (bRun == false) {
						}
					}
					while (true) {
						num = is.read(buffer); // ��������
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
								// ������ʾ��Ϣ��������ʾˢ��
								if (activityfla == 2) {
									DeviceActivity.dahandler
											.sendMessage(DeviceActivity.dahandler
													.obtainMessage());
								}
							}
						}
						if (is.available() == 0)
							break; // ��ʱ��û�����ݲ�����������ʾ
					}

				} catch (IOException e) {
				}
			}
		}
	};

	// ������������
	public static void bthsend(short CData) {
		if(_socket == null){
			L.i("null");
		}
		try {
			OutputStream os = _socket.getOutputStream(); // �������������
			byte[] buf = new byte[2];
			short CTemp = CData;
			L.i("bthsend:" + CData);
			for (int i = buf.length - 1; i >= 0; i--) {
				buf[i] = (byte) (CTemp & 0x00ff);
				CTemp >>= 8;
			}

			os.write(buf);
		} catch (IOException e) {
			L.i("���ͳ���");
			Log.e("test",e.toString());
			T.showShort(MyApplication.getAppContext(), "���ͳ���");
		}
	}

	/** �Զ������ϴ����ӹ���������ַ */
	public static String getAdress(Context context) {
		SharedPreferences prference;
		String BLUE_PREF_NAME = "BLUE_PREF_NAME";
		String ADDRESS = "address";
		prference = context.getSharedPreferences(BLUE_PREF_NAME, 0);
		String tmp = prference.getString(ADDRESS, null);
		return tmp;
	}

	/** ����������ַ��SharedPreferences���� */
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
