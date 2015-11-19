package com.core.domain.knockout;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import com.core.domain.User;

@Entity
@Table(name="user_points")
public class UserPoints {
	
	@Id
	@GeneratedValue
	private Long id;
	
	@ManyToOne(optional=false)
	@JoinColumn(name="user_id")
	private User user;
	
	@Column(name="points_won" ,columnDefinition="int default 0")
	private int pointsWon=0;
	
	@Column(name="topic_id")
	private long topicId;
	
	@Column(name = "point_gain_time")
	private long pointGainTime;

	
	public UserPoints(User user, int pointsWon, long topicId) {
		super();
		this.user = user;
		this.pointsWon = pointsWon;
		this.topicId = topicId;
		this.pointGainTime = System.currentTimeMillis();
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}


	public int getPointsWon() {
		return pointsWon;
	}


	public void setPointsWon(int pointsWon) {
		this.pointsWon = pointsWon;
	}


	public long getTopicId() {
		return topicId;
	}


	public void setTopicId(long topicId) {
		this.topicId = topicId;
	}


	public long getPointGainTime() {
		return pointGainTime;
	}


	public void setPointGainTime(long pointGainTime) {
		this.pointGainTime = pointGainTime;
	}
	
	
	
	
	
	

}
