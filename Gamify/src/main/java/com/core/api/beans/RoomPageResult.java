package com.core.api.beans;

import com.core.domain.lms.Room;

public class RoomPageResult extends ApiResult {

	private Room room;

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}
}
