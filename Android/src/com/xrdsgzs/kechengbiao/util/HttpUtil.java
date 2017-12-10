package com.xrdsgzs.kechengbiao.util;

import org.apache.http.Header;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.BinaryHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.xrdsgzs.kechengbiao.service.LinkService;
import com.xrdsgzs.smartdormitory.tool.L;

/**
 * Http���󹤾���
 * @author lizhangqu
 * @date 2015-2-1
 */
/**
 * @author Administrator
 * 
 */
public class HttpUtil {
	private static AsyncHttpClient client = new AsyncHttpClient(); // ʵ��������
	// Host��ַ
	public static final String HOST = "jwgl.gdut.edu.cn";
	// ������ַ
	public static final String URL_BASE = "http://jwgl.gdut.edu.cn/";
	// ��֤���ַ
	public static final String URL_CODE = "http://jwgl.gdut.edu.cn/CheckCode.aspx";
	// ��½��ַ
	public static final String URL_LOGIN = "http://jwgl.gdut.edu.cn/default2.aspx";
	// ��¼�ɹ�����ҳ
	public static String URL_MAIN = "http://jwgl.gdut.edu.cn/xs_main.aspx?xh=XH";
	// �����ַ
	public static String URL_QUERY = "http://jwgl.gdut.edu.cn/QUERY";

	/**
	 * �������
	 */
	public static String Button1 = "";
	public static String hidPdrs = "";
	public static String hidsc = "";
	public static String lbLanguage = "";
	public static String RadioButtonList1 = "%D1%A7%C9%FA";
	// public static String __VIEWSTATE =
	// "dDwyODE2NTM0OTg7Oz4I/BV0f3bU3Jxu0+rIKYuntoTTNg==";
	public static String __VIEWSTATE = null;
	// __VIEWSTATE�ĳ�ҳ���϶�Ӧ��ֵ
	public static String TextBox2 = null;
	public static String txtSecretCode = null;
	public static String txtUserName = null;

	// ��̬��ʼ��
	static {
		client.setTimeout(10000); // �������ӳ�ʱ����������ã�Ĭ��Ϊ10s
		// ��������ͷ
		client.addHeader("Accept",
				"text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		client.addHeader("Accept-Encoding", "gzip, deflate");
		client.addHeader("Accept-Language",
				"zh-cn,zh;q=0.8,en-us;q=0.5,en;q=0.3");
		client.addHeader("Connection", "keep-alive");
		client.addHeader("Content-Type", "application/x-www-form-urlencoded");
		client.addHeader("Host", HOST);
		client.addHeader("Referer", URL_BASE);
		client.addHeader("User-Agent",
				"Mozilla/5.0 (Windows NT 6.1; WOW64; Trident/7.0; rv:11.0) like Gecko");
	}

	/**
	 * get,��һ������url��ȡһ��string����
	 * 
	 * @param urlString
	 * @param res
	 */
	public static void get(String urlString, AsyncHttpResponseHandler res) {
		client.get(urlString, res);
	}

	/**
	 * get,url���������
	 * 
	 * @param urlString
	 * @param params
	 * @param res
	 */
	public static void get(String urlString, RequestParams params,
			AsyncHttpResponseHandler res) {
		client.get(urlString, params, res);
	}

	/**
	 * get,��������ʹ�ã��᷵��byte����
	 * 
	 * @param uString
	 * @param bHandler
	 */
	public static void get(String uString, BinaryHttpResponseHandler bHandler) {
		client.get(uString, bHandler);
	}

	/**
	 * post,��������
	 * 
	 * @param urlString
	 * @param res
	 */
	public static void post(String urlString, AsyncHttpResponseHandler res) {
		client.post(urlString, res);
	}

	/**
	 * post,������
	 * 
	 * @param urlString
	 * @param params
	 * @param res
	 */
	public static void post(String urlString, RequestParams params,
			AsyncHttpResponseHandler res) {
		client.post(urlString, params, res);
	}

	/**
	 * post,���ض���������ʱʹ�ã��᷵��byte����
	 * 
	 * @param uString
	 * @param bHandler
	 */
	public static void post(String uString, BinaryHttpResponseHandler bHandler) {
		client.post(uString, bHandler);
	}

	/**
	 * ��������ͻ���
	 * 
	 * @return
	 */
	public static AsyncHttpClient getClient() {
		return client;
	}

	/**
	 * ��õ�¼ʱ������������
	 * 
	 * @return
	 */
	public static RequestParams getLoginRequestParams() {
		// �����������
		RequestParams params = new RequestParams();
		params.add("__VIEWSTATE", __VIEWSTATE);
		params.add("Button1", Button1);
		params.add("hidPdrs", hidPdrs);
		params.add("hidsc", hidsc);
		params.add("lbLanguage", lbLanguage);
		params.add("RadioButtonList1", RadioButtonList1);
		params.add("TextBox2", TextBox2);
		params.add("txtSecretCode", txtSecretCode);
		params.add("txtUserName", txtUserName);
		return params;
	}

	public interface QueryCallback {
		public String handleResult(byte[] result);
	}

	public static void getQuery(final Context context, LinkService linkService,
			final String urlName, final QueryCallback callback) {
		final ProgressDialog dialog = CommonUtil.getProcessDialog(context,
				"���ڻ�ȡ" + urlName);
		dialog.show();
		String link = linkService.getLinkByName(urlName);
		L.i("link:" + link);
		if (link != null) {
			HttpUtil.URL_QUERY = HttpUtil.URL_QUERY.replace("QUERY", link);
			L.i("HttpUtil.URL_QUERY:" + HttpUtil.URL_QUERY);
		} else {
			Toast.makeText(context, "���ӳ��ִ���", Toast.LENGTH_SHORT).show();
			return;
		}
		HttpUtil.getClient().addHeader("Referer", HttpUtil.URL_MAIN);
		HttpUtil.getClient().setURLEncodingEnabled(true);
		HttpUtil.get(HttpUtil.URL_QUERY, new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
				// L.i("getQueryonSuccess");
				if (callback != null) {
					callback.handleResult(arg2);
				}
				Toast.makeText(context, urlName + "��ȡ�ɹ�������", Toast.LENGTH_LONG)
						.show();
				dialog.dismiss();
			}

			@Override
			public void onFailure(int arg0, Header[] arg1, byte[] arg2,
					Throwable arg3) {
				dialog.dismiss();
				Toast.makeText(context, urlName + "��ȡʧ�ܣ�����", Toast.LENGTH_SHORT)
						.show();
				L.i("getQueryonFailure");
			}
		});
	}
}