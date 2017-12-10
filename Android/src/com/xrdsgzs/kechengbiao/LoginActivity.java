package com.xrdsgzs.kechengbiao;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Calendar;

import org.apache.http.Header;
import org.litepal.tablemanager.Connector;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;
import com.umeng.analytics.MobclickAgent;
import com.umeng.analytics.MobclickAgent.EScenarioType;
import com.xrdsgzs.kechengbiao.WheelView.WheelView;
import com.xrdsgzs.kechengbiao.service.LinkService;
import com.xrdsgzs.kechengbiao.util.CommonUtil;
import com.xrdsgzs.kechengbiao.util.HttpUtil;
import com.xrdsgzs.kechengbiao.util.SharedPreferenceUtil;
import com.xrdsgzs.smartdormitory.R;
import com.xrdsgzs.smartdormitory.ScheduleActivity;
import com.xrdsgzs.smartdormitory.tool.L;
import com.xrdsgzs.smartdormitory.tool.MyApplication;

public class LoginActivity extends Activity {
	RequestParams tparams;
	private TextView tv_baseweek;
	private EditText username, password, secrectCode;// �˺ţ����룬��֤��
	private ImageView code;// ��֤��
	private Button flashCode, login;// ˢ����֤�룬��¼
	private String weda = null;
	private PersistentCookieStore cookie;
	private SQLiteDatabase db;
	private LinkService linkService;
	private static final String[] PLANETS = new String[] { "δ��ѧ", "��1��", "��2��",
		"��3��", "��4��", "��5��", "��6��", "��7��", "��8��", "��9��", "��10��", "��11��",
		"��12��", "��13��", "��14��", "��15��", "��16��", "��17��", "��18��", "��19��",
		"��20��", "��21��", "��22��", "��ѧ��" };
	private OnClickListener listener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.getCode:
				getCode();
				break;
			case R.id.login:
				login();
				break;
			}

		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
        
        MobclickAgent.setScenarioType(getApplicationContext(), EScenarioType.E_UM_NORMAL);
        
		initViewData();// ��ȡ��ʼ������
		initValue();// ������ʼ��
		initView();// ��ͼ��ʼ��
		initEvent();// �¼���ʼ��
		initCookie(this);// cookie��ʼ��
		initDatabase();// ���ݿ��ʼ��
	}

	/**
	 * ��ȡ��֤�룬��__VIEWSTATE
	 */
	private void initViewData() {
		HttpUtil.get(HttpUtil.URL_BASE, new AsyncHttpResponseHandler() {

			@Override
			public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
				L.i("onSuccess");
				try {
					String resultContent = new String(arg2, "gb2312");
					// L.i("resultContent:" + resultContent);
					HttpUtil.__VIEWSTATE = linkService.isdata(resultContent);
					L.i(linkService.isdata(resultContent));
					getCode();

				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				} finally {
				}
			}

			@Override
			public void onFailure(int arg0, Header[] arg1, byte[] arg2,
					Throwable arg3) {
				L.i("onFailure");
			}
		});
	}

	private void initValue() {
		linkService = LinkService.getLinkService();
		tv_baseweek = (TextView) findViewById(R.id.tv_baseweek);
	}

	/**
	 * ��ʼ��View
	 */
	private void initView() {
		secrectCode = (EditText) findViewById(R.id.secrectCode);
		username = (EditText) findViewById(R.id.username);
		password = (EditText) findViewById(R.id.password);
		flashCode = (Button) findViewById(R.id.getCode);
		login = (Button) findViewById(R.id.login);
		code = (ImageView) findViewById(R.id.codeImage);

		tv_baseweek.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				View outerView = LayoutInflater.from(MyApplication.getAppContext()).inflate(R.layout.wheel_view,
						null);
				WheelView wv = (WheelView) outerView.findViewById(R.id.wheel_view_wv);
				wv.setOffset(2);
				wv.setItems(Arrays.asList(PLANETS));
				wv.setSeletion(0);
				wv.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
					@Override
					public void onSelected(int selectedIndex, String item) {
						L.i("item: " + item);
						tv_baseweek.setText(item);
					}
				});

				new AlertDialog.Builder(LoginActivity.this).setTitle("��ѡ��������")
						.setView(outerView).setPositiveButton("OK", null).show();
			}
		});
	
	}

	/**
	 * ��ʼ�¼�
	 */
	private void initEvent() {
		// һЩ�е���¼��ĳ�ʼ��
		flashCode.setOnClickListener(listener);
		login.setOnClickListener(listener);
	}

	/**
	 * ��ʼ�����ݿ�
	 */
	private void initDatabase() {
		db = Connector.getDatabase();
		// ��assetsĿ¼�µ�litepal.xml���������ݿ������汾��ӳ���ϵ
	}

	/**
	 * ��ʼ��Cookie
	 */
	private void initCookie(Context context) {
		// ����������ǰ��ʼ��
		cookie = new PersistentCookieStore(context);
		HttpUtil.getClient().setCookieStore(cookie);
	}

	/**
	 * ��ת����ҳ
	 */
	private void jump2Main() {
		Calendar c = Calendar.getInstance();
		int weekdata = c.get(Calendar.WEEK_OF_YEAR) - Integer.parseInt(weda);
		SharedPreferenceUtil util = new SharedPreferenceUtil(
				getApplicationContext(), "accountInfo");
		util.setKeyData("username", HttpUtil.txtUserName);
		util.setKeyData("password", HttpUtil.TextBox2);
		util.setKeyData("baseweek", weekdata+"");
		util.setKeyData("isLogin", "TRUE");
		Intent intent = new Intent(LoginActivity.this, ScheduleActivity.class);
		startActivity(intent);
		finish();
	}

	/**
	 * ��¼
	 */
	private void login() {
		HttpUtil.txtUserName = username.getText().toString().trim();
		HttpUtil.TextBox2 = password.getText().toString().trim();
		// ��Ҫʱ����֤��ע��
		HttpUtil.txtSecretCode = secrectCode.getText().toString().trim();
		if (TextUtils.isEmpty(HttpUtil.txtUserName)
				|| TextUtils.isEmpty(HttpUtil.TextBox2)) {
			Toast.makeText(getApplicationContext(), "�˺Ż������벻��Ϊ��!",
					Toast.LENGTH_SHORT).show();
			return;
		}
		weda = tv_baseweek.getText().toString().trim();
		if (weda.length() == 0) {
			Toast.makeText(getApplicationContext(), "����������",
					Toast.LENGTH_SHORT).show();
			L.i("no");
			return;
		}else if (weda == "δ��ѧ") {
			weda = 0+"";
		} else if (weda == "��ѧ��") {
			weda = 100+"";
		} else {
			String[] tempstr = weda.split("��");
			String[] tempstr1 = tempstr[1].split("��");
			weda = tempstr1[0];
		}
		
		final ProgressDialog dialog = CommonUtil.getProcessDialog(
				LoginActivity.this, "���ڵ�¼�У�����");
		dialog.show();
		RequestParams params = HttpUtil.getLoginRequestParams();// ����������
		tparams = params;// ����������
		HttpUtil.URL_MAIN = HttpUtil.URL_MAIN.replace("XH",
				HttpUtil.txtUserName);// ��������ַ

		L.i("params: " + params.toString());

		HttpUtil.getClient().setURLEncodingEnabled(true);

		HttpUtil.post(HttpUtil.URL_LOGIN, params,
				new AsyncHttpResponseHandler() {

					@Override
					public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
						try {
							String resultContent = new String(arg2, "gb2312");
							L.i("��¼�ɹ�");
							// L.i("resultContent:" + resultContent);
							if (linkService.isLogin(resultContent) != null) {
								String ret = linkService
										.parseMenu(resultContent);
								// L.i("login success:" + ret);
								Toast.makeText(getApplicationContext(),
										"��¼�ɹ�������", Toast.LENGTH_SHORT).show();
								jump2Main();

							} else {
								// Toast.makeText(getApplicationContext(),
								// "�˺Ż���������󣡣���", Toast.LENGTH_SHORT)
								// .show();
								Toast.makeText(getApplicationContext(),
										"ϵͳ����֤����󣡣���", Toast.LENGTH_SHORT)
										.show();
							}

						} catch (UnsupportedEncodingException e) {
							e.printStackTrace();
						} finally {
							dialog.dismiss();
						}
					}

					@Override
					public void onFailure(int arg0, Header[] arg1, byte[] arg2,
							Throwable arg3) {
						Toast.makeText(getApplicationContext(), "��¼ʧ�ܣ�������",
								Toast.LENGTH_SHORT).show();
						dialog.dismiss();
					}
				});
	}

	/**
	 * �����֤��
	 */
	private void getCode() {
		final ProgressDialog dialog = CommonUtil.getProcessDialog(
				LoginActivity.this, "���ڻ�ȡ��֤��");
		dialog.show();
		HttpUtil.get(HttpUtil.URL_CODE, new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {

				InputStream is = new ByteArrayInputStream(arg2);
				Bitmap decodeStream = BitmapFactory.decodeStream(is);
				code.setImageBitmap(decodeStream);
				Toast.makeText(getApplicationContext(), "��֤���ȡ�ɹ�������",
						Toast.LENGTH_SHORT).show();
				dialog.dismiss();
			}

			@Override
			public void onFailure(int arg0, Header[] arg1, byte[] arg2,
					Throwable arg3) {

				Toast.makeText(getApplicationContext(), "��֤���ȡʧ�ܣ�����",
						Toast.LENGTH_SHORT).show();
				dialog.dismiss();

			}
		});
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
}
