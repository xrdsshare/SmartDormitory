package com.xrdsgzs.kechengbiao;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import org.litepal.crud.DataSupport;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;
import com.umeng.analytics.MobclickAgent.EScenarioType;
import com.xrdsgzs.kechengbiao.WheelView.WheelView;
import com.xrdsgzs.kechengbiao.model.Course;
import com.xrdsgzs.kechengbiao.service.CourseService;
import com.xrdsgzs.kechengbiao.util.CommonUtil;
import com.xrdsgzs.kechengbiao.util.SharedPreferenceUtil;
import com.xrdsgzs.smartdormitory.R;
import com.xrdsgzs.smartdormitory.tool.L;

/**
 * @author lizhangqu
 * @date 2015-2-1
 */
public class CourseActivity extends Activity {
	private TextView tv_week, tv_week0, tv_week1, tv_isweek;
	private ImageButton ib_left, ib_right, menu;
	private int weekdata;
	private int baseweek;
	private float mStartX;
	private ScrollView scrollview;
	private int modelhight;
	private Calendar c;
	private static final String[] PLANETS = new String[] { "未开学", "第1周", "第2周",
			"第3周", "第4周", "第5周", "第6周", "第7周", "第8周", "第9周", "第10周", "第11周",
			"第12周", "第13周", "第14周", "第15周", "第16周", "第17周", "第18周", "第19周",
			"第20周", "第21周", "第22周", "整学期" };
	// 课程页面的button引用，6行7列
	private int[][] lessons = {
			{ R.id.lesson0101, R.id.lesson0102, R.id.lesson0103,
					R.id.lesson0104, R.id.lesson0105, R.id.lesson0106,
					R.id.lesson0107 },
			{ R.id.lesson0201, R.id.lesson0202, R.id.lesson0203,
					R.id.lesson0204, R.id.lesson0205, R.id.lesson0206,
					R.id.lesson0207 },
			{ R.id.lesson0301, R.id.lesson0302, R.id.lesson0303,
					R.id.lesson0304, R.id.lesson0305, R.id.lesson0306,
					R.id.lesson0307 },
			{ R.id.lesson0401, R.id.lesson0402, R.id.lesson0403,
					R.id.lesson0404, R.id.lesson0405, R.id.lesson0406,
					R.id.lesson0407 },
			{ R.id.lesson0501, R.id.lesson0502, R.id.lesson0503,
					R.id.lesson0504, R.id.lesson0505, R.id.lesson0506,
					R.id.lesson0507 },
			{ R.id.lesson0601, R.id.lesson0602, R.id.lesson0603,
					R.id.lesson0604, R.id.lesson0605, R.id.lesson0606,
					R.id.lesson0607 },
			{ R.id.lesson0701, R.id.lesson0702, R.id.lesson0703,
					R.id.lesson0704, R.id.lesson0705, R.id.lesson0706,
					R.id.lesson0707 },
			{ R.id.lesson0801, R.id.lesson0802, R.id.lesson0803,
					R.id.lesson0804, R.id.lesson0805, R.id.lesson0806,
					R.id.lesson0807 },
			{ R.id.lesson0901, R.id.lesson0902, R.id.lesson0903,
					R.id.lesson0904, R.id.lesson0905, R.id.lesson0906,
					R.id.lesson0907 },
			{ R.id.lesson1001, R.id.lesson1002, R.id.lesson1003,
					R.id.lesson1004, R.id.lesson1005, R.id.lesson1006,
					R.id.lesson1007 },
			{ R.id.lesson1101, R.id.lesson1102, R.id.lesson1103,
					R.id.lesson1104, R.id.lesson1105, R.id.lesson1106,
					R.id.lesson1107 },
			{ R.id.lesson1201, R.id.lesson1202, R.id.lesson1203,
					R.id.lesson1204, R.id.lesson1205, R.id.lesson1206,
					R.id.lesson1207 }, };
	// 某节课的背景图,用于随机获取
	private int[] bg = { R.drawable.kb1, R.drawable.kb2, R.drawable.kb3,
			R.drawable.kb4, R.drawable.kb5, R.drawable.kb6, R.drawable.kb7 };
	private CourseService courseService;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		//设置无标题  
        requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_course);
        
        MobclickAgent.setScenarioType(getApplicationContext(), EScenarioType.E_UM_NORMAL);
        
		initValue();
		initView();
		setView(weekdata);
	}

	private void initView() {
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 7; j++) {
				Button lesson = (Button) findViewById(lessons[i][j]);
				lesson.setBackgroundColor(0X00FFFFFF);// 设置背景
				lesson.setText("");
				// lesson.setVisibility(Button.VISIBLE);
				LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) lesson
						.getLayoutParams();
				layoutParams.height = modelhight;
				lesson.setLayoutParams(layoutParams);
			}
		}
		if(weekdata == 0){
			tv_week0.setText("未");
			tv_week.setText("开");
			tv_week1.setText("学");
		}else if (weekdata == 100) {
			tv_week0.setText("整");
			tv_week.setText("学");
			tv_week1.setText("期");
		}else {
			tv_week0.setText("第");
			tv_week.setText(weekdata + "");
			tv_week1.setText("周");
		}
	}

	/**
	 * 初始化变量
	 */
	private void initValue() {
		SharedPreferenceUtil util = new SharedPreferenceUtil(
				getApplicationContext(), "accountInfo");
		// String baseweek = util.getKeyData("baseweek");
		baseweek = Integer.parseInt(util.getKeyData("baseweek"));
		L.i("baseweek:" + baseweek);

		courseService = CourseService.getCourseService();
		ib_left = (ImageButton) findViewById(R.id.ib_left);
		ib_right = (ImageButton) findViewById(R.id.ib_right);
		menu = (ImageButton) findViewById(R.id.menu);
		tv_week = (TextView) findViewById(R.id.tv_week);
		tv_week0 = (TextView) findViewById(R.id.tv_week0);
		tv_week1 = (TextView) findViewById(R.id.tv_week1);
		tv_isweek = (TextView) findViewById(R.id.tv_isweek);
		scrollview = (ScrollView) findViewById(R.id.scrollview);
		ib_left.setOnClickListener(guideonclicklistener);
		ib_right.setOnClickListener(guideonclicklistener);
		c = Calendar.getInstance();
		weekdata = c.get(Calendar.WEEK_OF_YEAR) - baseweek;
		L.i("weekdata:" + weekdata);
		Button lesson = (Button) findViewById(R.id.btn_model);// btn_model
		LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) lesson
				.getLayoutParams();
		modelhight = layoutParams.height;
		L.i("modelhight:" + modelhight);
		
		menu.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				//TODO
			}
		});

		scrollview.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
//				switch (event.getAction()) {
//				// 手指按下
//				case MotionEvent.ACTION_DOWN:
//					// 记录起始坐标
//					mStartX = event.getX();
//					break;
//				// 手指抬起
//				case MotionEvent.ACTION_UP:
//					// 往左滑动
//					if (mStartX - event.getX() > 50) {
//						weekdata--;
//						if (weekdata == 0) {
//							weekdata = 1;
//						}
//						tv_week.setText(weekdata + "");
//						initView();
//						setView(weekdata);
//					}
//					// 往右滑动
//					else if (event.getX() - mStartX > 50) {
//						weekdata++;
//						if (weekdata == 22) {
//							weekdata = 21;
//						}
//						tv_week.setText(weekdata + "");
//						initView();
//						setView(weekdata);
//					}
//					break;
//				}
				return false;
			}
		});

		tv_week.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				View outerView = LayoutInflater.from(getApplicationContext())
						.inflate(R.layout.wheel_view, null);
				WheelView wv = (WheelView) outerView
						.findViewById(R.id.wheel_view_wv);
				wv.setOffset(2);
				wv.setItems(Arrays.asList(PLANETS));
				wv.setSeletion(weekdata);
				wv.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
					@Override
					public void onSelected(int selectedIndex, String item) {
						L.i("item: " + item);
						if (item == "未开学") {
							weekdata = 0;
						} else if (item == "整学期") {
							weekdata = 100;
						} else {
							String[] tempstr = item.split("第");
							String[] tempstr1 = tempstr[1].split("周");
							weekdata = Integer.parseInt(tempstr1[0]);
						}
					}
				});

				new AlertDialog.Builder(CourseActivity.this)
						.setTitle("请选择周数：")
						.setView(outerView)
						.setPositiveButton("OK",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										initView();
										setView(weekdata);
										if(weekdata == c.get(Calendar.WEEK_OF_YEAR) - baseweek){
											tv_isweek.setText("本周");
										}else {
											tv_isweek.setText("返回本周");
										}
									}
								}).show();
			}
		});
	}

	/**
	 * 课程监听方法
	 * 
	 * @param v
	 */
	public void lessononclick(View v) {
		Button lesson = (Button) findViewById(v.getId());
		String strlesson = lesson.getText() + "";
		if (strlesson != "") {
			String[] temps = strlesson.split("@");
			Intent intent = new Intent(getApplicationContext(),
					LessonsActivity.class);
			intent.putExtra("lename", temps[0]);
			startActivity(intent);
		}
	}

	/**
	 * @author mxd 按钮监听事件
	 */
	private OnClickListener guideonclicklistener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.ib_left:
				weekdata--;
				if (weekdata < 1) {
					weekdata = 1;
				}
				tv_week.setText(weekdata + "");
				initView();
				setView(weekdata);
				break;

			case R.id.ib_right:
				weekdata++;
				if (weekdata > 22) {
					weekdata = 22;
				}
				tv_week.setText(weekdata + "");
				initView();
				setView(weekdata);
				break;

			default:
				break;
			}
		}
	};

	/**
	 * 初始化视图
	 */
	private void setView(int setweek) {
		// 这里有逻辑问题，只是简单的显示了下数据，数据并不一定是显示在正确位置
		// 课程可能有重叠
		// 课程可能有1节课的，2节课的，3节课的，因此这里应该改成在自定义View上显示更合理
		// List<Course> courses=courseService.findAll();//获得数据库中的课程
		List<Course> courses = null;
		if (setweek == 0) {
			courses = DataSupport.where("startWeek = ? ",
					"100").find(Course.class);
		} else if (setweek == 100) {
			courses = DataSupport.findAll(Course.class);
		} else {
			courses = DataSupport.where("startWeek <= ? and endWeek >= ?",
					setweek + "", setweek + "").find(Course.class);
		}
		Course course = null;
		// 循环遍历
		for (int i = 0; i < courses.size(); i++) {
			course = courses.get(i);// 拿到当前课程
			int dayOfWeek = course.getDayOfWeek() - 1;// 转换为lessons数组对应的下标
			int section = course.getStartSection() - 1;// 转换为lessons数组对应的下标
			Button lesson = (Button) findViewById(lessons[section][dayOfWeek]);// 获得该节课的button
			int bgRes = bg[CommonUtil.getRandom(bg.length - 1)];// 随机获取背景色
			lesson.setBackgroundResource(bgRes);// 设置背景
			lesson.setText(course.getCourseName() + "@"
					+ course.getClasssroom());// 设置文本为课程名+“@”+教室
			// lesson.setVisibility(Button.VISIBLE);

			int ends = course.getEndSection();
			int starts = course.getStartSection();
			int xzheight = 0;
			if(ends - starts > 1){//按钮间隔修正
				xzheight = (int) (modelhight * (ends - starts - 1) * 0.04);
			}
			int heightt = modelhight * (ends - starts + 1) + xzheight;
			LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) lesson
					.getLayoutParams();
			layoutParams.height = heightt;
			lesson.setLayoutParams(layoutParams);
			for (int j = starts; j < ends; j++) {
				lesson = (Button) findViewById(lessons[j][dayOfWeek]);
				// lesson.setVisibility(Button.GONE);
				layoutParams = (LinearLayout.LayoutParams) lesson
						.getLayoutParams();
				layoutParams.height = 0;
				lesson.setLayoutParams(layoutParams);
			}
		}
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
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
