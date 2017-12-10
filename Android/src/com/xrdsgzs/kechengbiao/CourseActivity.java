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
	private static final String[] PLANETS = new String[] { "δ��ѧ", "��1��", "��2��",
			"��3��", "��4��", "��5��", "��6��", "��7��", "��8��", "��9��", "��10��", "��11��",
			"��12��", "��13��", "��14��", "��15��", "��16��", "��17��", "��18��", "��19��",
			"��20��", "��21��", "��22��", "��ѧ��" };
	// �γ�ҳ���button���ã�6��7��
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
	// ĳ�ڿεı���ͼ,���������ȡ
	private int[] bg = { R.drawable.kb1, R.drawable.kb2, R.drawable.kb3,
			R.drawable.kb4, R.drawable.kb5, R.drawable.kb6, R.drawable.kb7 };
	private CourseService courseService;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		//�����ޱ���  
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
				lesson.setBackgroundColor(0X00FFFFFF);// ���ñ���
				lesson.setText("");
				// lesson.setVisibility(Button.VISIBLE);
				LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) lesson
						.getLayoutParams();
				layoutParams.height = modelhight;
				lesson.setLayoutParams(layoutParams);
			}
		}
		if(weekdata == 0){
			tv_week0.setText("δ");
			tv_week.setText("��");
			tv_week1.setText("ѧ");
		}else if (weekdata == 100) {
			tv_week0.setText("��");
			tv_week.setText("ѧ");
			tv_week1.setText("��");
		}else {
			tv_week0.setText("��");
			tv_week.setText(weekdata + "");
			tv_week1.setText("��");
		}
	}

	/**
	 * ��ʼ������
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
//				// ��ָ����
//				case MotionEvent.ACTION_DOWN:
//					// ��¼��ʼ����
//					mStartX = event.getX();
//					break;
//				// ��ָ̧��
//				case MotionEvent.ACTION_UP:
//					// ���󻬶�
//					if (mStartX - event.getX() > 50) {
//						weekdata--;
//						if (weekdata == 0) {
//							weekdata = 1;
//						}
//						tv_week.setText(weekdata + "");
//						initView();
//						setView(weekdata);
//					}
//					// ���һ���
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
						if (item == "δ��ѧ") {
							weekdata = 0;
						} else if (item == "��ѧ��") {
							weekdata = 100;
						} else {
							String[] tempstr = item.split("��");
							String[] tempstr1 = tempstr[1].split("��");
							weekdata = Integer.parseInt(tempstr1[0]);
						}
					}
				});

				new AlertDialog.Builder(CourseActivity.this)
						.setTitle("��ѡ��������")
						.setView(outerView)
						.setPositiveButton("OK",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										initView();
										setView(weekdata);
										if(weekdata == c.get(Calendar.WEEK_OF_YEAR) - baseweek){
											tv_isweek.setText("����");
										}else {
											tv_isweek.setText("���ر���");
										}
									}
								}).show();
			}
		});
	}

	/**
	 * �γ̼�������
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
	 * @author mxd ��ť�����¼�
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
	 * ��ʼ����ͼ
	 */
	private void setView(int setweek) {
		// �������߼����⣬ֻ�Ǽ򵥵���ʾ�������ݣ����ݲ���һ������ʾ����ȷλ��
		// �γ̿������ص�
		// �γ̿�����1�ڿεģ�2�ڿεģ�3�ڿεģ��������Ӧ�øĳ����Զ���View����ʾ������
		// List<Course> courses=courseService.findAll();//������ݿ��еĿγ�
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
		// ѭ������
		for (int i = 0; i < courses.size(); i++) {
			course = courses.get(i);// �õ���ǰ�γ�
			int dayOfWeek = course.getDayOfWeek() - 1;// ת��Ϊlessons�����Ӧ���±�
			int section = course.getStartSection() - 1;// ת��Ϊlessons�����Ӧ���±�
			Button lesson = (Button) findViewById(lessons[section][dayOfWeek]);// ��øýڿε�button
			int bgRes = bg[CommonUtil.getRandom(bg.length - 1)];// �����ȡ����ɫ
			lesson.setBackgroundResource(bgRes);// ���ñ���
			lesson.setText(course.getCourseName() + "@"
					+ course.getClasssroom());// �����ı�Ϊ�γ���+��@��+����
			// lesson.setVisibility(Button.VISIBLE);

			int ends = course.getEndSection();
			int starts = course.getStartSection();
			int xzheight = 0;
			if(ends - starts > 1){//��ť�������
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
