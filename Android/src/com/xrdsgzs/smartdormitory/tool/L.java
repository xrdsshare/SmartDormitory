package com.xrdsgzs.smartdormitory.tool;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;

import android.os.Environment;
import android.util.Log;

/**
 * L ��־������
 * @author XRDSGZS
 *
 */

public class L {

	private static final String FirstLogFolder = "";// Log����Ŀ¼

//	private static final Boolean issave = true;// Log������־
	private static final Boolean issave = false;// Log��������־

	private static final String TAG = "test";

	// ����tag
	public static void i(String msg) {
		Log.i(TAG, msg);
		if (issave) {
			write(time() + TAG + " --> " + msg + "\n");
		}
	}

	public static void d(String msg) {
		Log.d(TAG, msg);
		if (issave) {
			write(time() + TAG + " --> " + msg + "\n");
		}
	}

	public static void i(int msg) {
		Log.i(TAG, msg+"");
		if (issave) {
			write(time() + TAG + " --> " + msg + "\n");
		}
	}

	public static void d(int msg) {
		Log.d(TAG, msg+"");
		if (issave) {
			write(time() + TAG + " --> " + msg + "\n");
		}
	}

	public static void i(Long msg) {
		Log.i(TAG, msg+"");
		if (issave) {
			write(time() + TAG + " --> " + msg + "\n");
		}
	}

	public static void d(Long msg) {
		Log.d(TAG, msg+"");
		if (issave) {
			write(time() + TAG + " --> " + msg + "\n");
		}
	}

	public static void i(Short msg) {
		Log.i(TAG, msg+"");
		if (issave) {
			write(time() + TAG + " --> " + msg + "\n");
		}
	}

	public static void d(Short msg) {
		Log.d(TAG, msg+"");
		if (issave) {
			write(time() + TAG + " --> " + msg + "\n");
		}
	}

	public static void i(Boolean msg) {
		Log.i(TAG, msg+"");
		if (issave) {
			write(time() + TAG + " --> " + msg + "\n");
		}
	}

	public static void d(Boolean msg) {
		Log.d(TAG, msg+"");
		if (issave) {
			write(time() + TAG + " --> " + msg + "\n");
		}
	}

	// �����Ǵ����Զ���tag�ĺ���
	public static void i(String tag, String msg) {
		Log.i(tag, msg);
		if (issave) {
			write(time() + tag + " --> " + msg + "\n");
		}
	}

	public static void d(String tag, String msg) {
		Log.d(tag, msg);
		if (issave) {
			write(time() + tag + " --> " + msg + "\n");
		}
	}

	public static void i(String tag, int msg) {
		Log.i(tag, msg+"");
		if (issave) {
			write(time() + tag + " --> " + msg + "\n");
		}
	}

	public static void d(String tag, int msg) {
		Log.d(tag, msg+"");
		if (issave) {
			write(time() + tag + " --> " + msg + "\n");
		}
	}

	public static void i(String tag, Long msg) {
		Log.i(tag, msg+"");
		if (issave) {
			write(time() + tag + " --> " + msg + "\n");
		}
	}

	public static void d(String tag, Long msg) {
		Log.d(tag, msg+"");
		if (issave) {
			write(time() + tag + " --> " + msg + "\n");
		}
	}

	public static void i(String tag, Short msg) {
		Log.i(tag, msg+"");
		if (issave) {
			write(time() + tag + " --> " + msg + "\n");
		}
	}

	public static void d(String tag, Short msg) {
		Log.d(tag, msg+"");
		if (issave) {
			write(time() + tag + " --> " + msg + "\n");
		}
	}

	public static void i(String tag, Boolean msg) {
		Log.i(tag, msg+"");
		if (issave) {
			write(time() + tag + " --> " + msg + "\n");
		}
	}

	public static void d(String tag, Boolean msg) {
		Log.d(tag, msg+"");
		if (issave) {
			write(time() + tag + " --> " + msg + "\n");
		}
	}

	/**
	 * ��ʶÿ����־������ʱ��
	 * 
	 * @return
	 */
	private static String time() {
		return "["
				+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(
						System.currentTimeMillis())) + "] ";
	}

	/**
	 * ����������Ϊ��־�ļ�����
	 * 
	 * @return
	 */
	private static String date() {
		return new SimpleDateFormat("yyyy-MM-dd").format(new Date(System
				.currentTimeMillis()));
	}

	/**
	 * ���浽��־�ļ�
	 * 
	 * @param content
	 */
	public static synchronized void write(String content) {
		try {
			FileWriter writer = new FileWriter(getFile(), true);
			writer.write(content);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * ��ȡ��־�ļ�·��
	 * 
	 * @return
	 */
	public static String getFile() {
		File sdDir = null;

		if (Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED))
			sdDir = Environment.getExternalStorageDirectory();

		File cacheDir = new File(sdDir + File.separator + FirstLogFolder);
		if (!cacheDir.exists())
			cacheDir.mkdir();

		File logDir = new File(cacheDir.getPath() + File.separator + "Log");
		if (!logDir.exists())
			logDir.mkdir();

		File logPath = new File(logDir + File.separator + date() + ".txt");

		return logPath.toString();
	}
}
