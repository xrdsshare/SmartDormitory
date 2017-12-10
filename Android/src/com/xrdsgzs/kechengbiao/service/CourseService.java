package com.xrdsgzs.kechengbiao.service;

import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.litepal.crud.DataSupport;

import android.util.Log;

import com.umeng.analytics.MobclickAgent;
import com.xrdsgzs.kechengbiao.finals.ClassTime;
import com.xrdsgzs.kechengbiao.model.Course;
import com.xrdsgzs.kechengbiao.util.SharedPreferenceUtil;
import com.xrdsgzs.smartdormitory.tool.L;
import com.xrdsgzs.smartdormitory.tool.MyApplication;
import com.xrdsgzs.smartdormitory.tool.ST;
import com.xrdsgzs.smartdormitory.tool.Ti;

/**
 * Course���ҵ���߼�����
 * 
 * @author lizhangqu
 * @date 2015-2-1
 */
public class CourseService {
	private Pattern pattern;
	private Matcher matcher;
	private int startYear, endYear, semester;
	private int weekdata;
	private static volatile CourseService courseService;

	private CourseService() {
	}

	public static CourseService getCourseService() {
		if (courseService == null) {
			synchronized (CourseService.class) {
				if (courseService == null)
					courseService = new CourseService();
			}
		}
		return courseService;
	}

	/**
	 * ����һ�ڿγ�
	 * 
	 * @param course
	 * @return
	 */
	public boolean save(Course course) {
		return course.save();
	}

	/**
	 * ��ѯ���пγ�
	 * 
	 * @return
	 */
	public List<Course> findAll() {
		return DataSupport.findAll(Course.class);
	}

	/**
	 * ������ҳ���ؽ�������γ̲�����
	 * 
	 * @param content
	 * @return
	 */
	public String parseCourse(String content) {
		SharedPreferenceUtil util = new SharedPreferenceUtil(
				MyApplication.getAppContext(), "accountInfo");
		// String baseweek = util.getKeyData("baseweek");
		int baseweek = Integer.parseInt(util.getKeyData("baseweek"));
		Calendar c = Calendar.getInstance();
		weekdata = c.get(Calendar.WEEK_OF_YEAR) - baseweek;
		
		StringBuilder result = new StringBuilder();
		org.jsoup.nodes.Document doc = Jsoup.parse(content);
		
		Elements semesters = doc.select("option[selected=selected]");
		String[] years = semesters.get(0).text().split("-");
		startYear = Integer.parseInt(years[0]);
		endYear = Integer.parseInt(years[1]);
		semester = Integer.parseInt(semesters.get(1).text());

		// L.i("startYear:" + startYear);
		// L.i("endYear:" + endYear);
		// L.i("semester:" + semester);

		Elements elements = doc.select("table#Table1");
		Element element = elements.get(0).child(0);
		// �Ƴ�һЩ��������

		element.child(0).remove();
		element.child(0).remove();
		element.child(0).child(0).remove();
		element.child(4).child(0).remove();
		element.child(9).child(0).remove();

		// SavaData.write(element.toString());
		// L.i("element: " + element.text());
		int rowNum = element.childNodeSize();
		L.i("rowNum: " + rowNum);

		for (int i = 0; i < rowNum - 1; i++) {
			Element row = element.child(i);
			// L.i(row.text());
			pattern = Pattern.compile("��?(\\d{1,2})?��");
			matcher = pattern.matcher(row.child(0).text());
			matcher.matches();
			// L.i(matcher.group(1));
			int StaSection = ST.ti(matcher.group(1));// ���п�ʼ����
			int rowspan = 0;// ��ռ�����������ڿ�
			int columnNum = row.childNodeSize() - 2;
			L.i("columnNum: " + columnNum);
			for (int j = 1; j < columnNum; j++) {
				Element column = row.child(j);
				// L.i(column.text());
				// column = element.child(0).child(7);
				if (column.hasAttr("rowspan")) {
					rowspan = Integer.parseInt(column.attr("rowspan"));
					// L.i(rowspan+"");
				} else if (column.html().split("<br>").length > 1) {
					rowspan = 0;
					// L.i(rowspan+"");
				}
				String[] rowdatas = column.html().split("<br><br>");
				for (int w = 0; w < rowdatas.length; w++) {
					if (!(rowdatas[w].startsWith("<br>") && rowdatas[w]
							.endsWith("<br>"))) {
						// L.i("1"+rowdatas[w]);
						String[] rdatal = rowdatas[w].split("<br>");
						if (rdatal.length > 4) {// ����ͬһ��ͬһ�ſβ�ͬ�Ͽ��ܴ�
							longCouresSava(rdatal);
							storeCourseByResult(rowdatas[w], startYear, endYear,
									semester, j, StaSection, StaSection + rowspan);// jֻ���ܴβο����ò�һ��׼ȷ
						}else if (rdatal.length == 4) {
							storeCourseByResult(rowdatas[w], startYear, endYear,
									semester, j, StaSection, StaSection + rowspan);// jֻ���ܴβο����ò�һ��׼ȷ
							L.i("j:" + j);
						}
					} else {
						// L.i("2"+rowdatas[w]);
						// TODO ȱ���һ��߽�ʦ����
					}
				}
			}
		}

		return result.toString();
	}

	private void longCouresSava(String[] a) {
		String kc = a[0];
		String ls = a[2];
		String classroom = null;
		int dayofweek = 0;
		int startweek = 0;
		int endweek = 0;
		int startsection = 0;
		int endsection = 0;

		boolean issa = true;
		for (int v = 4; v < a.length; v = v + 2) {
			String ks = a[v + 1];// ��������
			String sj = a[v];// ʱ������
			if (sj.split("����").length == 2) {// ��һ�����
				// ��18����3(2016-06-29)��1,2��<br>��3-303</td>
				try {
					issa = true;
					pattern = Pattern
							.compile("��?(\\d{1,2})?��{2}(\\d{1,2})?\\(?(\\d{2,4})?\\-?(\\d{1,2})?\\-?(\\d{1,2})?\\)?��?(\\d{1,2})?,?(\\d{1,2})?��");
					matcher = pattern.matcher(sj);
					matcher.matches();
					startweek = endweek = Integer.parseInt(matcher.group(1));
					dayofweek = Integer.parseInt(matcher.group(2));
					startsection = Integer.parseInt(matcher.group(6));
					endsection = Integer.parseInt(matcher.group(7));
				} catch (Exception e) {
					Log.e("test", "longCouresSava()��һ������쳣��" + e.toString());

			        MobclickAgent.reportError(MyApplication.getAppContext(), "longCouresSava()��һ������쳣��"+e.toString());
					issa = false;
				}
//			}
			} else if (sj.split("��").length == 2) {// �ڶ������
				// 2016��07��04��(14:00-16:00)<br>��4-106
				String[] clt = { ClassTime.S1, ClassTime.E1, ClassTime.S2,
						ClassTime.E2, ClassTime.S3, ClassTime.E3, ClassTime.S4,
						ClassTime.E4, ClassTime.S5, ClassTime.E5, ClassTime.S6,
						ClassTime.E6, ClassTime.S7, ClassTime.E7, ClassTime.S8,
						ClassTime.E8, ClassTime.S9, ClassTime.E9,
						ClassTime.S10, ClassTime.E10, ClassTime.S11,
						ClassTime.E11, ClassTime.S12, ClassTime.E12 };
				try {
					issa = true;
					pattern = Pattern
							.compile("(\\d{2,4})?��?(\\d{1,2})?��?(\\d{1,2})?��?\\(?(\\d{1,2})?\\:?(\\d{1,2})?\\-{1,2}?(\\d{1,2})?\\:?(\\d{1,2})?\\)");// (\\d{2,4})?��?(\\d{1,2})?��?(\\d{1,2})?��?\\(?(\\d{1,2})?\\:?(\\d{1,2})?\\-?(\\d{1,2})?\\:?(\\d{1,2})
					matcher = pattern.matcher(sj);
					// matcher = pattern.matcher("2016��07��04��(14:00--16:00)");
					matcher.matches();
					int ab = Ti.getDayCount(
							Ti.getCurrentTimeInString(),
							matcher.group(1) + "-" + matcher.group(2) + "-"
									+ matcher.group(3));
					L.i(ab + "��");
					// 1=������ 7=����������������
					Calendar c = Calendar.getInstance();
					int weekd = c.get(Calendar.DAY_OF_WEEK);
					if (weekd == 0) {
						weekd = 7;
					} else {
						weekd--;
					}
					L.i("��" + weekd);
					if ((ab + weekd) % 7 == 0) {
						dayofweek = 7;
					} else {
						dayofweek = (ab + weekd) % 7;
					}
					if(dayofweek <= 0){
						dayofweek = dayofweek + 7;
					}
					startweek = endweek = weekdata + ab / 7 + 1;
					long stc = 0;
					String stim = matcher.group(4) + ":" + matcher.group(5);
					String etim = matcher.group(6) + ":" + matcher.group(7);

					kc = kc + "(" + stim + "--" + etim + ")";
					// L.i(kc);
					//
					// L.i(etim);
					for (int l = 0; l < 24; l = l + 2) {
						stc = Ti.getTimeCount(clt[l], stim);
						if (stc < 0) {
							if (l / 2 < 1) {
								startsection = 1;
							} else {
								startsection = l / 2;
							}
							// L.i(startsection+"");
							break;
						} else if ((l + 2) >= 24) {
							startsection = 12;
							// L.i(startsection+"");
							break;
						}
					}
					for (int l = 1; l < 24; l = l + 2) {
						stc = Ti.getTimeCount(etim, clt[l]);
						if (stc > 0) {
							if ((l + 1) / 2 > 12) {
								endsection = 12;
							} else {
								endsection = (l + 1) / 2;
							}
							// L.i(endsection+"");
							break;
						} else if ((l + 2) >= 24) {
							endsection = 12;
							// L.i(endsection+"");
							break;
						}
					}
				} catch (Exception e) {
					Log.e("test", "longCouresSava()�ڶ�������쳣��" + e.toString());

			        MobclickAgent.reportError(MyApplication.getAppContext(), "longCouresSava()�ڶ�������쳣��"+e.toString());
					issa = false;
				}
			}
			if (issa) {
				Course course = new Course();
				// �γ̿�ʼѧ��
				course.setStartYear(startYear);
				// �γ̽���ѧ��
				course.setEndYear(endYear);
				// �γ�ѧ��
				course.setSemester(semester);
				// �γ���
				course.setCourseName(kc);
				// ����
				course.setClasssroom(ks);
				// �γ�ʱ�䣬�����ֶ�
				course.setCourseTime(sj);
				// ��ʦ
				course.setTeacher(ls);
				// ��ʼ�ܴ�
				course.setStartWeek(startweek);
				// �����ܴ�
				course.setEndWeek(endweek);

				course.setDayOfWeek(dayofweek);

				course.setStartSection(startsection);

				course.setEndSection(endsection);

				List<Course> coursest = DataSupport.where(
						"courseName = ? and courseTime = ? and classsroom = ?", kc, sj, ks)
						.find(Course.class);
				if(coursest.isEmpty()){
					course.save();
					L.i(course.toString());
				}
			} else {
				// TODO
				L.i("����");
			}
		}
	}

	/**
	 * ���ݴ������Ŀγ̸�ʽת��Ϊ��Ӧ��ʵ���ಢ����
	 * 
	 * @param sub
	 * @param startYear
	 * @param endYear
	 * @param semester
	 * @param week
	 * @param startSection
	 * @param endSection
	 * @return
	 */
	private Course storeCourseByResult(String sub, int startYear, int endYear,
			int semester, int week, int startSection, int endSection) {
		Course course = null;
		String reg = null;
		// �ܶ���1,2��{��4-16��} ��,1,2,4,16,null
		// ��18����3(2016-06-29)��1,2�� ��3-303
		// {��2-10��|3��/��} null,null,null,2,10,3��/��
		// �ܶ���1,2��{��4-16��|˫��} ��,1,2,4,16,˫��
		// �ܶ���1��{��4-16��} ��,1,null,4,16,null
		// �ܶ���1��{��4-16��|˫��} ��,1,null,4,16,˫��
		// str��ʽ���ϣ�����ֻ�Ǽ򵥿���ÿ���ζ�ֻ�����ڿΣ�ʵ���������ں��Ľڣ�ģʽ��Ҫ�Ķ�������ƥ��ģʽ�������޸�

		
		// ��18����3(2016-06-29)��1,2��
		// "��?(\\d{1,2})?��{2}(\\d{1,2})?\\(?(\\d{2,4})?\\-?(\\d{1,2})?\\-?(\\d{1,2})?\\)?��?(\\d{1,2})?,?(\\d{1,2})?��"

		// String splitPattern = "<br />";
		String splitPattern = "<br>";
		String[] temp = sub.split(splitPattern);
		if (endSection - startSection == 3) {
			reg = "��?(.)?��?(\\d{1,2})?,?(\\d{1,2})?,?(\\d{1,2})?��?\\{��(\\d{1,2})-(\\d{1,2})��\\|?((.*��))?\\}";
		}else {
			reg = "��?(.)?��?(\\d{1,2})?,?(\\d{1,2})?��?\\{��(\\d{1,2})-(\\d{1,2})��\\|?((.*��))?\\}";
		}
		L.i("temp:" + temp.length);
		L.i(sub);
		try {
			pattern = Pattern.compile(reg);
			matcher = pattern.matcher(temp[1]);
			matcher.matches();
			course = new Course();
			// �γ̿�ʼѧ��
			course.setStartYear(startYear);
			// �γ̽���ѧ��
			course.setEndYear(endYear);
			// �γ�ѧ��
			course.setSemester(semester);

			// �γ���
			course.setCourseName(temp[0]);
			// �γ�ʱ�䣬�����ֶ�
			course.setCourseTime(temp[1]);
			// ��ʦ
			course.setTeacher(temp[2]);

			try {
				// �������Խ�磬��û�н���
				course.setClasssroom(temp[3]);
			} catch (ArrayIndexOutOfBoundsException e) {
				course.setClasssroom("�޽���");
			}
			// �ܼ�������Ϊ�գ���ʱʹ�ô�������ֵ
			if (null != matcher.group(1)) {
				course.setDayOfWeek(getDayOfWeek(matcher.group(1)));
			} else {
				course.setDayOfWeek(getDayOfWeek(week + ""));
			}
			// �γ̿�ʼ����������Ϊ�գ���ʱʹ�ô�������ֵ
			if (null != matcher.group(2)) {
				course.setStartSection(Integer.parseInt(matcher.group(2)));
			} else {
				course.setStartSection(startSection);
			}

			// �γ̽���ʱ�Ľ���������Ϊ�գ���ʱʹ�ô�������ֵ
			if (null != matcher.group(3)) {
				course.setEndSection(Integer.parseInt(matcher.group(3)));
			} else if (endSection - startSection == 3) {
				course.setEndSection(Integer.parseInt(matcher.group(4)));
			}else {
				course.setEndSection(endSection);
			}
			
			if(endSection - startSection == 2){
				// ��ʼ��
				course.setStartWeek(Integer.parseInt(matcher.group(4)));
				// ������
				course.setEndWeek(Integer.parseInt(matcher.group(5)));
				// ��˫��
				String t = matcher.group(6);
				setEveryWeekByChinese(t, course);
			}else if (endSection - startSection == 3) {
				course.setStartWeek(Integer.parseInt(matcher.group(5)));
				course.setEndWeek(Integer.parseInt(matcher.group(6)));
				String t = matcher.group(7);
				setEveryWeekByChinese(t, course);
			}else {
				course.setStartWeek(Integer.parseInt(matcher.group(4)));
				course.setEndWeek(Integer.parseInt(matcher.group(5)));
				String t = matcher.group(6);
				setEveryWeekByChinese(t, course);
			}
			L.i(course.toString());
			// save(course);
			course.save();
		} catch (Exception e) {
			// TODO: handle exception
			Log.e("test", "storeCourseByResult()����" + e.toString());
			L.i("����");
		}
		return course;
	}

	/**
	 * ���õ�˫��
	 * 
	 * @param week
	 * @param course
	 */
	public void setEveryWeekByChinese(String week, Course course) {
		// 1�����ܣ�2����˫��
		if (week != null) {
			if (week.equals("����"))
				course.setEveryWeek(1);
			else if (week.equals("˫��"))
				course.setEveryWeek(2);
		}
		// Ĭ��ֵΪ0������ÿ��
	}

	/**
	 * ������������һ�����������ģ��壬�����գ�ת��Ϊ��Ӧ�İ���������
	 * 
	 * @param day
	 * @return int
	 */
	public int getDayOfWeek(String day) {
		if (day.equals("һ"))
			return 1;
		else if (day.equals("��"))
			return 2;
		else if (day.equals("��"))
			return 3;
		else if (day.equals("��"))
			return 4;
		else if (day.equals("��"))
			return 5;
		else if (day.equals("��"))
			return 6;
		else if (day.equals("��"))
			return 7;
		else
			return 0;
	}
}
