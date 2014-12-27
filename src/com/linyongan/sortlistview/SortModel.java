package com.linyongan.sortlistview;

/**
 * 一个实体类，里面一个是ListView的name,另一个就是显示的name拼音的首字母
 * @author yongan
 *
 */
public class SortModel {

	private String name;   //显示的数据
	private String sortLetters;  //显示数据拼音的首字母
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSortLetters() {
		return sortLetters;
	}
	public void setSortLetters(String sortLetters) {
		this.sortLetters = sortLetters;
	}
}
