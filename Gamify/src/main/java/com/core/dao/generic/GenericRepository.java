package com.core.dao.generic;

import java.io.Serializable;
import java.util.List;
import com.core.exception.ConstraintException;
import com.core.exception.MultipleObjectsFoundException;
import com.core.exception.NotFoundException;
import com.core.exception.RepositoryException;
import com.core.exception.ZeroObjectsFoundException;

public interface GenericRepository<T, ID extends Serializable> {

	public T findObjectById(ID id);

	public T findObjectByIdImmediate(ID id) throws NotFoundException;

	public T findObjectByKey(String keyColumn, String keyValue)
			throws ZeroObjectsFoundException, MultipleObjectsFoundException;

	public List<T> findAll() throws ZeroObjectsFoundException;

	public List<T> findByCriteria(String... criterion)
			throws RepositoryException;

	public T saveNew(T entity) throws ConstraintException;

	public void attach(T entity) throws RepositoryException;

	public void merge(T entity) throws RepositoryException;

	public void detach(T entity) throws RepositoryException;

	public void delete(T entity) throws RepositoryException;

	public void flush();

	public void detachAll();

	public T saveOrUpdate(T entity) throws ConstraintException;
}
