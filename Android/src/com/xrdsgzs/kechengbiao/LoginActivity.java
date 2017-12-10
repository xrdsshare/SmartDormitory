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
	private EditText username, password, secrectCode;// 账号，密码，验证码
	private ImageView code;// 验证码
	private Button flashCode, login;// 刷新验证码，登录
	private String weda = null;
	private PersistentCookieStore cookie;
	private SQLiteDatabase db;
	private LinkService linkService;
	private static final String[] PLANETS = new String[] { "未开学", "第1周", "第2周",
		"第3周", "第4周", "第5周", "第6周", "第7周", "第8周", "第9周", "第10周", "第11周",
		"第12周", "第13周", "第14周", "第15周", "第16周", "第17周", "第18周", "第19周",
		"第20周", "第21周", "第22周", "整学期" };
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
        
		initViewData();// 获取初始化数据
		initValue();// 变量初始化
		initView();// 视图初始化
		initEvent();// 事件初始化
		initCookie(this);// cookie初始化
		initDatabase();// 数据库初始化
	}

	/**
	 * 获取验证码，和__VIEWSTATE
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
	 * 初始化View
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

				new AlertDialog.Builder(LoginActivity.this).setTitle("请选择周数：")
						.setView(outerView).setPositiveButton("OK", null).show();
			}
		});
	
	}

	/**
	 * 初始事件
	 */
	private void initEvent() {
		// 一些列点击事件的初始化
		flashCode.setOnClickListener(listener);
		login.setOnClickListener(listener);
	}

	/**
	 * 初始化数据库
	 */
	private void initDatabase() {
		db = Connector.getDatabase();
		// 在assets目录下的litepal.xml李配置数据库名，版本，映射关系
	}

	/**
	 * 初始化Cookie
	 */
	private void initCookie(Context context) {
		// 必须在请求前初始化
		cookie = new PersistentCookieStore(context);
		HttpUtil.getClient().setCookieStore(cookie);
	}

	/**
	 * 跳转到主页
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
	 * 登录
	 */
	private void login() {
		HttpUtil.txtUserName = username.getText().toString().trim();
		HttpUtil.TextBox2 = password.getText().toString().trim();
		// 需要时打开验证码注释
		HttpUtil.txtSecretCode = secrectCode.getText().toString().trim();
		if (TextUtils.isEmpty(HttpUtil.txtUserName)
				|| TextUtils.isEmpty(HttpUtil.TextBox2)) {
			Toast.makeText(getApplicationContext(), "账号或者密码不能为空!",
					Toast.LENGTH_SHORT).show();
			return;
		}
		weda = tv_baseweek.getText().toString().trim();
		if (weda.length() == 0) {
			Toast.makeText(getApplicationContext(), "请设置周数",
					Toast.LENGTH_SHORT).show();
			L.i("no");
			return;
		}else if (weda == "未开学") {
			weda = 0+"";
		} else if (weda == "整学期") {
			weda = 100+"";
		} else {
			String[] tempstr = weda.split("第");
			String[] tempstr1 = tempstr[1].split("周");
			weda = tempstr1[0];
		}
		
		final ProgressDialog dialog = CommonUtil.getProcessDialog(
				LoginActivity.this, "正在登录中！！！");
		dialog.show();
		RequestParams params = HttpUtil.getLoginRequestParams();// 获得请求参数
		tparams = params;// 获得请求参数
		HttpUtil.URL_MAIN = HttpUtil.URL_MAIN.replace("XH",
				HttpUtil.txtUserName);// 获得请求地址

		L.i("params: " + params.toString());

		HttpUtil.getClient().setURLEncodingEnabled(true);

		HttpUtil.post(HttpUtil.URL_LOGIN, params,
				new AsyncHttpResponseHandler() {

					@Override
					public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
						try {
							String resultContent = new String(arg2, "gb2312");
							L.i("登录成功");
							// L.i("resultContent:" + resultContent);
							if (linkService.isLogin(resultContent) != null) {
								String ret = linkService
										.parseMenu(resultContent);
								// L.i("login success:" + ret);
								Toast.makeText(getApplicationContext(),
										"登录成功！！！", Toast.LENGTH_SHORT).show();
								jump2Main();

							} else {
								// Toast.makeText(getApplicationContext(),
								// "账号或者密码错误！！！", Toast.LENGTH_SHORT)
								// .show();
								Toast.makeText(getApplicationContext(),
										"系统或验证码错误！！！", Toast.LENGTH_SHORT)
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
						Toast.makeText(getApplicationContext(), "登录失败！！！！",
								Toast.LENGTH_SHORT).show();
						dialog.dismiss();
					}
				});
	}

	/**
	 * 获得验证码
	 */
	private void getCode() {
		final ProgressDialog dialog = CommonUtil.getProcessDialog(
				LoginActivity.this, "正在获取验证码");
		dialog.show();
		HttpUtil.get(HttpUtil.URL_CODE, new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {

				InputStream is = new ByteArrayInputStream(arg2);
				Bitmap decodeStream = BitmapFactory.decodeStream(is);
				code.setImageBitmap(decodeStream);
				Toast.makeText(getApplicationContext(), "验证码获取成功！！！",
						Toast.LENGTH_SHORT).show();
				dialog.dismiss();
			}

			@Override
			public void onFailure(int arg0, Header[] arg1, byte[] arg2,
					Throwable arg3) {

				Toast.makeText(getApplicationContext(), "验证码获取失败！！！",
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
