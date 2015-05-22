package com.core.dao.impl;

import java.io.Serializable;
import org.springframework.stereotype.Repository;
import com.core.dao.ExamDAO;
import com.core.dao.generic.HibernateGenericRepository;
import com.core.domain.lms.Exam;

@Repository("examDAO")
public class ExamDAOImpl extends HibernateGenericRepository<Exam, Serializable>
		implements ExamDAO {

}
