package com.linyongan.model;

/**
 * ����ʵ����
 * 
 * @author yongan
 * 
 */
public class Test {
	/** id */
	private int _id;
	/** ���� */
	private String question;
	/** ѡ��1 */
	private String option1;
	/** ѡ��2 */
	private String option2;
	/** ѡ��3 */
	private String option3;
	/** ѡ��4 */
	private String option4;
	/** �ղصı�� */
	private String mark;
	private String answer;

	public Test() {
		super();
	}

	// ��������ʱ�õ�
	public Test(int _id, String note, String mark) {
		this._id = _id;
		this.mark = mark;
	}

	public int get_id() {
		return _id;
	}

	public String getAnswer() {
		return answer;
	}

	public String getMark() {
		return mark;
	}

	public String getOption1() {
		return option1;
	}

	public String getOption2() {
		return option2;
	}

	public String getOption3() {
		return option3;
	}

	public String getOption4() {
		return option4;
	}

	public String getQuestion() {
		return question;
	}

	public void set_id(int _id) {
		this._id = _id;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}

	public void setOption1(String option1) {
		this.option1 = option1;
	}

	public void setOption2(String option2) {
		this.option2 = option2;
	}

	public void setOption3(String option3) {
		this.option3 = option3;
	}

	public void setOption4(String option4) {
		this.option4 = option4;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

}
