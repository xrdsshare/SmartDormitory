package com.xrdsgzs.kechengbiao.model;

import org.litepal.crud.DataSupport;

/**
 * �γ�ʵ����
 * @author lizhangqu
 * @date 2015-2-1
 */
public class Course extends DataSupport{
	private int id;//���I������
	private int startYear;//ѧ�꿪ʼ��
	private int endYear;//ѧ�������
	private int semester;//ѧ��
	private String courseName;//�γ���
	private String courseTime;//�γ�ʱ�䣬�����ֶ�
	private String classsroom;//����
	private String teacher;//��ʦ
	private int dayOfWeek;//���ڼ�
	private int startSection;//�ڼ��ڿο�ʼ
	private int endSection;//�ڼ��ڿν���
	private int startWeek;//��ʼ��
	private int endWeek;//������
	private int everyWeek;//����Ƿ��ǵ�˫�ܣ�0Ϊÿ��,1���ܣ�2˫��
	public int getStartYear() {
		return startYear;
	}
	public void setStartYear(int startYear) {
		this.startYear = startYear;
	}
	public int getEndYear() {
		return endYear;
	}
	public void setEndYear(int endYear) {
		this.endYear = endYear;
	}
	public int getSemester() {
		return semester;
	}
	public void setSemester(int semester) {
		this.semester = semester;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getEveryWeek() {
		return everyWeek;
	}
	public void setEveryWeek(int everyWeek) {
		this.everyWeek = everyWeek;
	}
	public int getDayOfWeek() {
		return dayOfWeek;
	}
	public void setDayOfWeek(int dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}
	public int getStartSection() {
		return startSection;
	}
	public void setStartSection(int startSection) {
		this.startSection = startSection;
	}
	public int getEndSection() {
		return endSection;
	}
	public void setEndSection(int endSection) {
		this.endSection = endSection;
	}
	public int getStartWeek() {
		return startWeek;
	}
	public void setStartWeek(int startWeek) {
		this.startWeek = startWeek;
	}
	public int getEndWeek() {
		return endWeek;
	}
	public void setEndWeek(int endWeek) {
		this.endWeek = endWeek;
	}
	
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public String getCourseTime() {
		return courseTime;
	}
	public void setCourseTime(String courseTime) {
		this.courseTime = courseTime;
	}
	public String getClasssroom() {
		return classsroom;
	}
	public void setClasssroom(String classsroom) {
		this.classsroom = classsroom;
	}
	public String getTeacher() {
		return teacher;
	}
	public void setTeacher(String teacher) {
		this.teacher = teacher;
	}
	
	@Override
	public String toString() {
		return "Course [id=" + id + ", startYear=" + startYear + ", endYear="
				+ endYear + ", semester=" + semester + ", courseName="
				+ courseName + ", courseTime=" + courseTime + ", classsroom="
				+ classsroom + ", teacher=" + teacher + ", dayOfWeek="
				+ dayOfWeek + ", startSection=" + startSection
				+ ", endSection=" + endSection + ", startWeek=" + startWeek
				+ ", endWeek=" + endWeek + ", everyWeek=" + everyWeek + "]";
	}
	
	
	
	

}
