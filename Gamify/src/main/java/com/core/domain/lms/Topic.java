package com.core.domain.lms;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "topic")
public class Topic implements Serializable {

	@Id
	private Long id;

	private String name;

	public Topic(Long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public Topic(Long id) {
		super();
		this.id = id;
	}

	public Topic() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
