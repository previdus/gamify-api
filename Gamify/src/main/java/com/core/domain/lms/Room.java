package com.core.domain.lms;

import java.io.Serializable;
import java.util.List;

public class Room implements Serializable {

	private long roomId;
	private String roomName;
	private String imageName;

	private List<Exam> exams;

	public long getRoomId() {
		return roomId;
	}

	public void setRoomId(long roomId) {
		this.roomId = roomId;

	}

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public List<Exam> getExams() {
		return exams;
	}

	public void setExams(List<Exam> exams) {
		this.exams = exams;
	}

}
