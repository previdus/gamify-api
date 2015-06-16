package com.core.dao.generic;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.util.CollectionUtils;

import com.core.exception.ConstraintException;
import com.core.exception.RepositoryException;


public  abstract class HibernateGenericRepository<T, ID extends Serializable>
		extends HibernateDaoSupport implements GenericRepository<T, ID> {

	private Class<T> persistentClass;

	@Autowired
	public void init(SessionFactory sessionFactory) {
		this.setSessionFactory(sessionFactory);
	}

	@SuppressWarnings("unchecked")
	public HibernateGenericRepository() {
		this.persistentClass = (Class<T>) ((ParameterizedType) getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];
	}

	public Class<T> getPersistentClass() {
		return persistentClass;
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
		List<T> find = getHibernateTemplate().find(
				"from " + persistentClass.getSimpleName());
		return find;
	}

	public List<T> findByCriteria(String... criterion)
			throws RepositoryException {
		return null;
	}

	public T findObjectById(ID id) {
		return (T) getHibernateTemplate().load(getPersistentClass(), id);
	}

	public T findObjectByIdImmediate(ID id) {
		T entity = null;
		entity = (T) getHibernateTemplate().get(getPersistentClass(), id);
		return entity;
	}

	public T findObjectByKey(String columnName, String value){		
			return null;
	} 
	
	public T findObjectByKey(Class entityClass, String columnName, String value){
		Criteria criteria = this.getSession().createCriteria(entityClass);  
		criteria.add(Restrictions.eq(columnName, value));
		List results = criteria.list();
		if(!CollectionUtils.isEmpty(results))
		     return (T)results.get(0);
		else 
			return null;
	}
	
	public List<T> findObjectsByKeyMap(Class entityClass, Map<String, Object> keyValueMap){
		Criteria criteria = this.getSession().createCriteria(entityClass);  
		for(String key:keyValueMap.keySet()){
			criteria.add(Restrictions.eq(key, keyValueMap.get(key)));			
		}
		
		List results = criteria.list();
		if(!CollectionUtils.isEmpty(results))
		     return results;
		else 
			return null;
	}
	
	public List<T> findObjectsByKeys(Class entityClass, String columnName, String value){
		Criteria criteria = this.getSession().createCriteria(entityClass);  
		criteria.add(Restrictions.eq(columnName, value));
		List results = criteria.list();
		if(!CollectionUtils.isEmpty(results))
		    return results;
		else 
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
