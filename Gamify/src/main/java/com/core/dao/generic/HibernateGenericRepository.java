package com.core.dao.generic;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.core.exception.ConstraintException;
import com.core.exception.MultipleObjectsFoundException;
import com.core.exception.RepositoryException;
import com.core.exception.ZeroObjectsFoundException;




public abstract class HibernateGenericRepository<T, ID extends Serializable>
extends HibernateDaoSupport implements
GenericRepository<T, ID > {

	private Class<T> persistentClass;
	
   @Autowired
	public void init(SessionFactory sessionFactory){
		this.setSessionFactory(sessionFactory);
	}


	@SuppressWarnings("unchecked")
	public HibernateGenericRepository(){
		this.persistentClass = (Class<T>) ((ParameterizedType) getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];
	}

	public Class<T> getPersistentClass() {
		return  persistentClass;
	}

	public void attach(T entity) throws RepositoryException {
		getHibernateTemplate().update(entity);	
	}
	
	public void merge(T entity) throws RepositoryException {
		getHibernateTemplate().merge(entity);
		
	}
	public T saveOrUpdate(T entity) throws ConstraintException {
		getHibernateTemplate().saveOrUpdate(entity);
		return entity;
	}
	
	public void delete(T entity) throws RepositoryException {
		getHibernateTemplate().delete(entity);	

	}

	public void detach(T entity) throws RepositoryException {
		getHibernateTemplate().evict(entity);
	}

	public void detachAll() {
		getHibernateTemplate().clear();
	}

	@SuppressWarnings("unchecked")
	public List<T> findAll() {
		List<T> find = getHibernateTemplate().find("from " + persistentClass.getSimpleName());
		return find;
	}

	public List<T> findByCriteria(String... criterion)
	throws RepositoryException {
		return null;
	}

	public T findObjectById(ID id)  {
		return (T) getHibernateTemplate().load(getPersistentClass(), id);
	}

	public T findObjectByIdImmediate(ID id)
	{
		T entity=null;
		entity = (T) getHibernateTemplate().get(getPersistentClass(), id);
		return entity;
	}

	public T findObjectByKey(String keyColumn, String keyValue) 
	throws ZeroObjectsFoundException,MultipleObjectsFoundException {
		return null;
	}

	public void flush() {
		getHibernateTemplate().flush();
	}

	public T saveNew(T entity) throws ConstraintException {
		getHibernateTemplate().save(entity);
		return entity;
	}
	


}
