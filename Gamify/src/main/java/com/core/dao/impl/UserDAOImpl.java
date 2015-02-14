package com.core.dao.impl;

import java.io.Serializable;

import java.util.List;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.core.dao.UserDAO;
import com.core.dao.generic.HibernateGenericRepository;
import com.core.domain.User;

@Repository("userDAO")
public class UserDAOImpl extends HibernateGenericRepository<User, Serializable> implements UserDAO  {
    
	
	
	
	
   public User getUser(String userName, String pwd) {
	    User user = null;	
	    Query qry = getSession().createQuery("from User where name = :uname").setParameter("uname", userName);
		List<User> userList = qry.list();
		if(userList != null && userList.size() > 0){
			user = userList.get(0);
			if(pwd.equals(user.getPwd())){
				
				return user;
			}			
		}
		return null;
	      
   }
   
   public User getUser(Long userId){
	
	    return findObjectById(userId);
	   
   }
	
   public User getUserByName(String userName) {
	    User user = null;	
	    Query qry = getSession().createQuery("from User where name = :uname").setParameter("uname", userName);
		List<User> userList = qry.list();
		if(userList != null && userList.size() > 0){
			user = userList.get(0);
			return user;	
		}
		return null;	      
  }
   
   public User getUserByEmail(String email) {
	    User user = null;	
	    Query qry = getSession().createQuery("from User where emailId = :email").setParameter("email", email);
		List<User> userList = qry.list();
		if(userList != null && userList.size() > 0){
			user = userList.get(0);
			return user;	
		}
		return null;	      
 }
   
   public User getUserByFacebookId(String facebookId) {
	    User user = null;	
	    Query qry = getSession().createQuery("from User where facebook_id = :facebookId").setParameter("facebookId", facebookId);
		List<User> userList = qry.list();
		if(userList != null && userList.size() > 0){
			user = userList.get(0);
			return user;	
		}
		return null;	      
}
	
}
