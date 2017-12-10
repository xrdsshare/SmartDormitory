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
 * Course表的业务逻辑处理
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
	 * 保存一节课程
	 * 
	 * @param course
	 * @return
	 */
	public boolean save(Course course) {
		return course.save();
	}

	/**
	 * 查询所有课程
	 * 
	 * @return
	 */
	public List<Course> findAll() {
		return DataSupport.findAll(Course.class);
	}

	/**
	 * 根据网页返回结果解析课程并保存
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
		// 移除一些无用数据

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
			pattern = Pattern.compile("第?(\\d{1,2})?节");
			matcher = pattern.matcher(row.child(0).text());
			matcher.matches();
			// L.i(matcher.group(1));
			int StaSection = ST.ti(matcher.group(1));// 该行开始接数
			int rowspan = 0;// 所占行数，及几节课
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
						if (rdatal.length > 4) {// 存在同一行同一门课不同上课周次
							longCouresSava(rdatal);
							storeCourseByResult(rowdatas[w], startYear, endYear,
									semester, j, StaSection, StaSection + rowspan);// j只做周次参考作用不一定准确
						}else if (rdatal.length == 4) {
							storeCourseByResult(rowdatas[w], startYear, endYear,
									semester, j, StaSection, StaSection + rowspan);// j只做周次参考作用不一定准确
							L.i("j:" + j);
						}
					} else {
						// L.i("2"+rowdatas[w]);
						// TODO 缺课室或者教师代码
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
			String ks = a[v + 1];// 课室数据
			String sj = a[v];// 时间数据
			if (sj.split("周周").length == 2) {// 第一种情况
				// 第18周周3(2016-06-29)第1,2节<br>教3-303</td>
				try {
					issa = true;
					pattern = Pattern
							.compile("第?(\\d{1,2})?周{2}(\\d{1,2})?\\(?(\\d{2,4})?\\-?(\\d{1,2})?\\-?(\\d{1,2})?\\)?第?(\\d{1,2})?,?(\\d{1,2})?节");
					matcher = pattern.matcher(sj);
					matcher.matches();
					startweek = endweek = Integer.parseInt(matcher.group(1));
					dayofweek = Integer.parseInt(matcher.group(2));
					startsection = Integer.parseInt(matcher.group(6));
					endsection = Integer.parseInt(matcher.group(7));
				} catch (Exception e) {
					Log.e("test", "longCouresSava()第一种情况异常：" + e.toString());

			        MobclickAgent.reportError(MyApplication.getAppContext(), "longCouresSava()第一种情况异常："+e.toString());
					issa = false;
				}
//			}
			} else if (sj.split("月").length == 2) {// 第二种情况
				// 2016年07月04日(14:00-16:00)<br>教4-106
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
							.compile("(\\d{2,4})?年?(\\d{1,2})?月?(\\d{1,2})?日?\\(?(\\d{1,2})?\\:?(\\d{1,2})?\\-{1,2}?(\\d{1,2})?\\:?(\\d{1,2})?\\)");// (\\d{2,4})?年?(\\d{1,2})?月?(\\d{1,2})?日?\\(?(\\d{1,2})?\\:?(\\d{1,2})?\\-?(\\d{1,2})?\\:?(\\d{1,2})
					matcher = pattern.matcher(sj);
					// matcher = pattern.matcher("2016年07月04日(14:00--16:00)");
					matcher.matches();
					int ab = Ti.getDayCount(
							Ti.getCurrentTimeInString(),
							matcher.group(1) + "-" + matcher.group(2) + "-"
									+ matcher.group(3));
					L.i(ab + "天");
					// 1=星期日 7=星期六，其他类推
					Calendar c = Calendar.getInstance();
					int weekd = c.get(Calendar.DAY_OF_WEEK);
					if (weekd == 0) {
						weekd = 7;
					} else {
						weekd--;
					}
					L.i("周" + weekd);
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
					Log.e("test", "longCouresSava()第二种情况异常：" + e.toString());

			        MobclickAgent.reportError(MyApplication.getAppContext(), "longCouresSava()第二种情况异常："+e.toString());
					issa = false;
				}
			}
			if (issa) {
				Course course = new Course();
				// 课程开始学年
				course.setStartYear(startYear);
				// 课程结束学年
				course.setEndYear(endYear);
				// 课程学期
				course.setSemester(semester);
				// 课程名
				course.setCourseName(kc);
				// 课室
				course.setClasssroom(ks);
				// 课程时间，冗余字段
				course.setCourseTime(sj);
				// 教师
				course.setTeacher(ls);
				// 开始周次
				course.setStartWeek(startweek);
				// 结束周次
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
				L.i("出错");
			}
		}
	}

	/**
	 * 根据传进来的课程格式转换为对应的实体类并保存
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
		// 周二第1,2节{第4-16周} 二,1,2,4,16,null
		// 第18周周3(2016-06-29)第1,2节 教3-303
		// {第2-10周|3节/周} null,null,null,2,10,3节/周
		// 周二第1,2节{第4-16周|双周} 二,1,2,4,16,双周
		// 周二第1节{第4-16周} 二,1,null,4,16,null
		// 周二第1节{第4-16周|双周} 二,1,null,4,16,双周
		// str格式如上，这里只是简单考虑每个课都只有两节课，实际上有三节和四节，模式就要改动，其他匹配模式请自行修改

		
		// 第18周周3(2016-06-29)第1,2节
		// "第?(\\d{1,2})?周{2}(\\d{1,2})?\\(?(\\d{2,4})?\\-?(\\d{1,2})?\\-?(\\d{1,2})?\\)?第?(\\d{1,2})?,?(\\d{1,2})?节"

		// String splitPattern = "<br />";
		String splitPattern = "<br>";
		String[] temp = sub.split(splitPattern);
		if (endSection - startSection == 3) {
			reg = "周?(.)?第?(\\d{1,2})?,?(\\d{1,2})?,?(\\d{1,2})?节?\\{第(\\d{1,2})-(\\d{1,2})周\\|?((.*周))?\\}";
		}else {
			reg = "周?(.)?第?(\\d{1,2})?,?(\\d{1,2})?节?\\{第(\\d{1,2})-(\\d{1,2})周\\|?((.*周))?\\}";
		}
		L.i("temp:" + temp.length);
		L.i(sub);
		try {
			pattern = Pattern.compile(reg);
			matcher = pattern.matcher(temp[1]);
			matcher.matches();
			course = new Course();
			// 课程开始学年
			course.setStartYear(startYear);
			// 课程结束学年
			course.setEndYear(endYear);
			// 课程学期
			course.setSemester(semester);

			// 课程名
			course.setCourseName(temp[0]);
			// 课程时间，冗余字段
			course.setCourseTime(temp[1]);
			// 教师
			course.setTeacher(temp[2]);

			try {
				// 数组可能越界，即没有教室
				course.setClasssroom(temp[3]);
			} catch (ArrayIndexOutOfBoundsException e) {
				course.setClasssroom("无教室");
			}
			// 周几，可能为空，此时使用传进来的值
			if (null != matcher.group(1)) {
				course.setDayOfWeek(getDayOfWeek(matcher.group(1)));
			} else {
				course.setDayOfWeek(getDayOfWeek(week + ""));
			}
			// 课程开始节数，可能为空，此时使用传进来的值
			if (null != matcher.group(2)) {
				course.setStartSection(Integer.parseInt(matcher.group(2)));
			} else {
				course.setStartSection(startSection);
			}

			// 课程结束时的节数，可能为空，此时使用传进来的值
			if (null != matcher.group(3)) {
				course.setEndSection(Integer.parseInt(matcher.group(3)));
			} else if (endSection - startSection == 3) {
				course.setEndSection(Integer.parseInt(matcher.group(4)));
			}else {
				course.setEndSection(endSection);
			}
			
			if(endSection - startSection == 2){
				// 起始周
				course.setStartWeek(Integer.parseInt(matcher.group(4)));
				// 结束周
				course.setEndWeek(Integer.parseInt(matcher.group(5)));
				// 单双周
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
			Log.e("test", "storeCourseByResult()出错" + e.toString());
			L.i("出错");
		}
		return course;
	}

	/**
	 * 设置单双周
	 * 
	 * @param week
	 * @param course
	 */
	public void setEveryWeekByChinese(String week, Course course) {
		// 1代表单周，2代表双周
		if (week != null) {
			if (week.equals("单周"))
				course.setEveryWeek(1);
			else if (week.equals("双周"))
				course.setEveryWeek(2);
		}
		// 默认值为0，代表每周
	}

	/**
	 * 根据中文数字一，二，三，四，五，六，日，转换为对应的阿拉伯数字
	 * 
	 * @param day
	 * @return int
	 */
	public int getDayOfWeek(String day) {
		if (day.equals("一"))
			return 1;
		else if (day.equals("二"))
			return 2;
		else if (day.equals("三"))
			return 3;
		else if (day.equals("四"))
			return 4;
		else if (day.equals("五"))
			return 5;
		else if (day.equals("六"))
			return 6;
		else if (day.equals("日"))
			return 7;
		else
			return 0;
	}
}
