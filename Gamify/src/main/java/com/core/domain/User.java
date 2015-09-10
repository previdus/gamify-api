package com.core.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.core.util.StringEncryptionDecryptionUtil;

@Entity
@Table(name = "user")
public class User implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String name;
	@Column(name = "display_name")
	private String displayName;
	@Column(name = "email_id")
	private String emailId;
	@Column(name = "phone_no")
	private String phoneNo;

	public String getFacebookId() {
		return facebookId;
	}

	public void setFacebookId(String facebookId) {
		this.facebookId = facebookId;
	}

	private String pwd;
	private String gender;
	@Column(name = "image_url")
	private String imageUrl;
	@Column(name = "facebook_id")
	private String facebookId;

	public User() {
		super();
	}

	public User(Long id) {
		super();
		this.id = id;
	}

	public User(String userName, String password, String email) {
		super();
		this.name = userName;
		this.pwd = password;
		this.emailId = email;
	}

	public User(String userName, String password, String email,
			String displayName, String gender, String facebookId) {
		super();
		this.name = userName;
		this.pwd = password;
		this.emailId = email;
		this.displayName = displayName;
		this.gender = gender;
		this.facebookId = facebookId;
	}

	public User(String userName, String password, String email,
			String displayName, String gender, String facebookId,
			String imageUrl) {
		super();
		this.name = userName;
		this.pwd = password;
		this.emailId = email;
		this.displayName = displayName;
		this.gender = gender;
		this.facebookId = facebookId;
		this.imageUrl = imageUrl;
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

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	
	public void encodeSensitiveData() throws Exception{
		if(this.emailId!= null) this.emailId = StringEncryptionDecryptionUtil.encrypt(this.emailId);
		if(this.facebookId!= null)this.facebookId = StringEncryptionDecryptionUtil.encrypt(this.facebookId);
		if(this.gender!= null)this.gender = StringEncryptionDecryptionUtil.encrypt(this.gender);
		if(this.name!= null)this.name = StringEncryptionDecryptionUtil.encrypt(this.name);
		if(this.phoneNo!= null)this.phoneNo = StringEncryptionDecryptionUtil.encrypt(this.phoneNo);
		if(this.pwd!= null)this.pwd = StringEncryptionDecryptionUtil.encrypt(this.pwd);		
	}
	
	public void decodeSensitiveData() throws Exception{
		this.emailId = StringEncryptionDecryptionUtil.decrypt(this.emailId);
		this.facebookId = StringEncryptionDecryptionUtil.decrypt(this.facebookId);
		this.gender = StringEncryptionDecryptionUtil.decrypt(this.gender);
		this.name = StringEncryptionDecryptionUtil.decrypt(this.name);
		this.phoneNo = StringEncryptionDecryptionUtil.decrypt(this.phoneNo);
		this.pwd = StringEncryptionDecryptionUtil.decrypt(this.pwd);		
	}

}
