package com.core.dao.impl;

import java.io.Serializable;
import org.springframework.stereotype.Repository;
import com.core.dao.ExamSectionDAO;
import com.core.dao.generic.HibernateGenericRepository;
import com.core.domain.lms.ExamSection;

@Repository("examSectionDAO")
public class ExamSectionDAOImpl extends
		HibernateGenericRepository<ExamSection, Serializable> implements
		ExamSectionDAO {

}
