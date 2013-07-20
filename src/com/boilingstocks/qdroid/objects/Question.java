package com.boilingstocks.qdroid.objects;


public class Question {

	private int id;
	private String qText;
	private String category;
	private String[] options = new String[4];
	private int answerId;
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the qText
	 */
	public String getqText() {
		return qText;
	}
	/**
	 * @param qText the qText to set
	 */
	public void setqText(String qText) {
		this.qText = qText;
	}
	/**
	 * @return the category
	 */
	public String getCategory() {
		return category;
	}
	/**
	 * @param category the category to set
	 */
	public void setCategory(String category) {
		this.category = category;
	}
	/**
	 * @return the options
	 */
	public String[] getOptions() {
		return options;
	}
	/**
	 * @param options the options to set
	 */
	public void setOptions(String[] options) {
		this.options = options;
	}
	/**
	 * @return the answerId
	 */
	public int getAnswerId() {
		return answerId;
	}
	/**
	 * @param answerId the answerId to set
	 */
	public void setAnswerId(int answerId) {
		this.answerId = answerId;
	}
	
	
}
