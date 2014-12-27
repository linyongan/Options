package com.linyongan.model;

/**
 * 成绩实体类
 * 
 * @author yongan
 * 
 */
public class Grade {
	public Grade() {
		super();
	}

	public Grade(String time, String name, int grade) {
		super();
		this.name = name;
		this.time = time;
		this.grade = grade;
	}

	private String name;
	private String time;
	private int grade;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public int getGrade() {
		return grade;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}

}
