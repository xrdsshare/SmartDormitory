package com.xrdsgzs.kechengbiao;

import java.util.List;

import org.litepal.crud.DataSupport;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;
import com.umeng.analytics.MobclickAgent.EScenarioType;
import com.xrdsgzs.kechengbiao.adapter.LessonsAdapter;
import com.xrdsgzs.kechengbiao.model.Course;
import com.xrdsgzs.smartdormitory.R;
import com.xrdsgzs.smartdormitory.tool.L;

public class LessonsActivity extends Activity { 
	ListView lv_list;
	TextView tv_teacher, esname;
	ImageButton ib_back;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lesson);
        
        MobclickAgent.setScenarioType(getApplicationContext(), EScenarioType.E_UM_NORMAL);
        
		
		lv_list = (ListView) findViewById(R.id.lv_list);
		ib_back = (ImageButton) findViewById(R.id.ib_back);
		tv_teacher = (TextView) findViewById(R.id.tv_teacher);
		esname = (TextView) findViewById(R.id.esname);
		ib_back.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		Intent intent = getIntent();
		Bundle bundle=intent.getExtras();  
        String str=bundle.getString("lename"); 
        L.i(str);
        
        List<Course> courses = DataSupport.where(
				"courseName = ?", str)
				.find(Course.class);
        tv_teacher.setText(courses.get(0).getTeacher());
        esname.setText(str);
        
        LessonsAdapter adapter = new LessonsAdapter(courses);
        lv_list.setAdapter(adapter);
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
