package com.core.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "email_verification")
public class EmailVerification {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long id;
	
	@Column(name = "user_id")
	long userId;
	
	@Column(name = "verification_code")
	long verificationCode;
	
	public EmailVerification() {
	}
	
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getVerificationCode() {
		return verificationCode;
	}

	public void setVerificationCode(long verificationCode) {
		this.verificationCode = verificationCode;
	}

}
