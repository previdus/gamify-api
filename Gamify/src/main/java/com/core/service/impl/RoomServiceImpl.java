package com.core.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core.dao.ExamDAO;
import com.core.dao.RoomDAO;
import com.core.domain.lms.Room;
import com.core.service.RoomService;
import com.googlecode.ehcache.annotations.Cacheable;

@Service("roomService")
public class RoomServiceImpl implements RoomService {

	@Autowired
	private RoomDAO roomDAO;
	
	@Autowired
	private ExamDAO examDAO;
	
	@Cacheable(cacheName = "room")
	public Room getRoom(){
		Room room = new Room();
		room.setExams(examDAO.findAll());
		return room;
	}
	
	
	
}
