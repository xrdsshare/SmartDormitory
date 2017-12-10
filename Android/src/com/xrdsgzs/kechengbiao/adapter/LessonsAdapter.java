package com.xrdsgzs.kechengbiao.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xrdsgzs.kechengbiao.model.Course;
import com.xrdsgzs.smartdormitory.tool.MyApplication;

public class LessonsAdapter extends BaseAdapter {

	private List<Course> courses;// 在绑定的数据
	private LayoutInflater inflater;

	public LessonsAdapter(List<Course> courses) {
		super();
		Context context = MyApplication.getAppContext();
		this.courses = courses;
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		// TODO 自动生成的方法存根
		return courses.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO 自动生成的方法存根
		return courses.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO 自动生成的方法存根
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		TextView tv_leweek0, tv_leweek1, tv_leweekd0, tv_leweekd1;
		String weeks = null;
		String lessens = null;
		String weekdays = null;
		
		if(convertView==null){
			convertView = inflater.inflate(com.xrdsgzs.smartdormitory.R.layout.lessons_item, null);//生成条目界面对象
			tv_leweek0 = (TextView) convertView.findViewById(com.xrdsgzs.smartdormitory.R.id.tv_leweek0);
			tv_leweek1 = (TextView) convertView.findViewById(com.xrdsgzs.smartdormitory.R.id.tv_leweek1);
			tv_leweekd0 = (TextView) convertView.findViewById(com.xrdsgzs.smartdormitory.R.id.tv_leweekd0);
			tv_leweekd1 = (TextView) convertView.findViewById(com.xrdsgzs.smartdormitory.R.id.tv_leweekd1);
			
			ViewCache cache = new ViewCache();
			cache.tv_leweek0 = tv_leweek0;
			cache.tv_leweek1 = tv_leweek1;	
			cache.tv_leweekd0 = tv_leweekd0;
			cache.tv_leweekd1 = tv_leweekd1;			
			convertView.setTag(cache);
		}else{
			ViewCache cache = (ViewCache) convertView.getTag();
			tv_leweek0 = cache.tv_leweek0;
			tv_leweek1 = cache.tv_leweek1;
			tv_leweekd0 = cache.tv_leweekd0;
			tv_leweekd1 = cache.tv_leweekd1;
		}
		
		Course course = courses.get(position);
		//下面代码实现数据绑定
		if(course.getEveryWeek() == 1){
			weeks = "单周" + course.getStartWeek() + "~" + course.getEndWeek() + "周";
		}else if (course.getEveryWeek() == 2) {
			weeks = "双周" + course.getStartWeek() + "~" + course.getEndWeek() + "周";
		}else {
			weeks = course.getStartWeek() + "~" + course.getEndWeek() + "周";
		}
		tv_leweek0.setText(weeks);
		
		lessens = course.getStartSection() + "~" + course.getEndSection() + "节";
		tv_leweek1.setText(lessens);
		
//		String[] temps = course.getCourseTime().split("第");
//		weekdays = temps[0];	
		switch (course.getDayOfWeek()) {
		case 1:
			weekdays = "周一";
			break;
		case 2:
			weekdays = "周二";
			break;
		case 3:
			weekdays = "周三";
			break;
		case 4:
			weekdays = "周四";
			break;
		case 5:
			weekdays = "周五";
			break;
		case 6:
			weekdays = "周六";
			break;
		case 7:
			weekdays = "周日";
			break;
		default:
			break;
		}
		tv_leweekd0.setText(weekdays);
			
		tv_leweekd1.setText(course.getClasssroom());
		return convertView;
	}

	private final class ViewCache{
		public TextView tv_leweek0;
		public TextView tv_leweek1;
		public TextView tv_leweekd0;
		public TextView tv_leweekd1;
	}
}
