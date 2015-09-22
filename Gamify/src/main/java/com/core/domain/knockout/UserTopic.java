package com.core.domain.knockout;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.core.domain.User;
import com.core.domain.lms.Topic;

@Embeddable
public class UserTopic implements Serializable {
	
	@ManyToOne(optional=false)
    @JoinColumn(name = "user_id")
    private User user;

	@ManyToOne(optional=false)
    @JoinColumn(name = "topic_id")
    private Topic topic;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Topic getTopic() {
		return topic;
	}

	public void setTopic(Topic topic) {
		this.topic = topic;
	}

    //getter, setter methods
}