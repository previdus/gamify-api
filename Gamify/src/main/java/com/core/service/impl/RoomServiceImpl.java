package com.core.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core.dao.ExamDAO;
import com.core.dao.RoomDAO;
import com.core.domain.lms.Room;
import com.core.service.PeriodicTasksService;
import com.core.service.RoomService;
import com.googlecode.ehcache.annotations.Cacheable;

@Service("roomService")
public class RoomServiceImpl implements RoomService {

	private static final Logger log = LoggerFactory
			.getLogger(RoomService.class);
	
	@Autowired
	private RoomDAO roomDAO;

	@Autowired
	private ExamDAO examDAO;

	@Cacheable(cacheName = "room")
	public Room getRoom() {
		Room room = new Room();
		
		//	examDAO.findExamByState();
			log.info("Finding room by state");
			room.setExams(examDAO.findExamsWithAllActiveEntities());
			return room;
	}
	
	@Cacheable(cacheName = "room_all")
	public Room getRoomWithAllExams(){
		Room room = new Room();
		room.setExams(examDAO.findAll());
		return room;
	}

}
