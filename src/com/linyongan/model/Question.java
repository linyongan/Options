package com.linyongan.model;

/**
 * 常见问题实体类
 * 
 * @author yongan
 * 
 */
public class Question {
	public Question() {
		super();
	}

	public Question(String question, String answer) {
		super();
		this.question = question;
		this.answer = answer;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	private String question;
	private String answer;

}
