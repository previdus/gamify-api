package com.core.api.beans;

public class StudentStats {
	
	private int noOfQuestionsAttempted;
	private int noOfQuestionsAnsweredItCorrect;
	private int noOfQuestionsAnsweredItInCorrect;
	private int noOfQuestionsAnsweredInBestTime;
	private int noOfQuestionsNotAttempted;
	private double averageTimeToAnswer;
	
	public StudentStats() {
	}
	
	public StudentStats(int noOfQuestionsAttempted,
			int noOfQuestionsAnsweredItCorrect,
			int noOfQuestionsAnsweredItInCorrect,
			int noOfQuestionsNotAttempted, int averageTimeToAnswer) {
		super();
		this.noOfQuestionsAttempted = noOfQuestionsAttempted;
		this.noOfQuestionsAnsweredItCorrect = noOfQuestionsAnsweredItCorrect;
		this.noOfQuestionsAnsweredItInCorrect = noOfQuestionsAnsweredItInCorrect;
		this.noOfQuestionsNotAttempted = noOfQuestionsNotAttempted;
		this.averageTimeToAnswer = averageTimeToAnswer;
	}
	
	
	
	
	public int getNoOfQuestionsAnsweredInBestTime() {
		return noOfQuestionsAnsweredInBestTime;
	}

	public void setNoOfQuestionsAnsweredInBestTime(
			int noOfQuestionsAnsweredInBestTime) {
		this.noOfQuestionsAnsweredInBestTime = noOfQuestionsAnsweredInBestTime;
	}

	public int getNoOfQuestionsAttempted() {
		return noOfQuestionsAttempted;
	}
	public void setNoOfQuestionsAttempted(int noOfQuestionsAttempted) {
		this.noOfQuestionsAttempted = noOfQuestionsAttempted;
	}
	public int getNoOfQuestionsAnsweredItCorrect() {
		return noOfQuestionsAnsweredItCorrect;
	}
	public void setNoOfQuestionsAnsweredItCorrect(int noOfQuestionsAnsweredItCorrect) {
		this.noOfQuestionsAnsweredItCorrect = noOfQuestionsAnsweredItCorrect;
	}
	public int getNoOfQuestionsAnsweredItInCorrect() {
		return noOfQuestionsAnsweredItInCorrect;
	}
	public void setNoOfQuestionsAnsweredItInCorrect(
			int noOfQuestionsAnsweredItInCorrect) {
		this.noOfQuestionsAnsweredItInCorrect = noOfQuestionsAnsweredItInCorrect;
	}
	public int getNoOfQuestionsNotAttempted() {
		return noOfQuestionsNotAttempted;
	}
	public void setNoOfQuestionsNotAttempted(int noOfQuestionsNotAttempted) {
		this.noOfQuestionsNotAttempted = noOfQuestionsNotAttempted;
	}
	public double getAverageTimeToAnswer() {
		return averageTimeToAnswer;
	}
	public void setAverageTimeToAnswer(double averageTimeToAnswer) {
		this.averageTimeToAnswer = averageTimeToAnswer;
	}
	
	
	

}
