package com.core.domain.lms;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.FilterJoinTable;
import org.hibernate.annotations.Filters;
import org.hibernate.annotations.ParamDef;
import org.hibernate.annotations.Proxy;

import com.core.constants.EntityStateENUM;

@Entity
@Table(name="exam")
@Proxy(lazy=false)
@FilterDef(name = Exam.ACTIVE_EXAMS,  parameters = @ParamDef(name = "activeState", type = "java.lang.String"))
@Filters( {
    @Filter(name=Exam.ACTIVE_EXAMS, condition="state = :activeState")
   
} )
public class Exam  implements Serializable{
	public static final String ACTIVE_EXAMS = "activeExamsFilter";


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	@Column(name = "exam_name")
	private String examName;
	@Column(name = "exam_image_name")
	private String examImageName;

	
	@Column(name="state", nullable=false, columnDefinition = "character varying (20) default ACTIVE", length = 20)
	private String state;
	
	@OneToMany(mappedBy="exam", fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)	
	private List<ExamSection> examSections;

	
	
	
	
	
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}


	public Exam(Long id) {
		super();
		this.id = id;
	}

	public Exam() {
		super();
	}

	public Exam(Long id, String examName, String examImageName,
			List<ExamSection> examSections) {
		super();
		this.id = id;
		this.examName = examName;
		this.examImageName = examImageName;
		this.examSections = examSections;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getExamName() {
		return examName;
	}

	public String getExamImageName() {
		return examImageName;
	}

	public void setExamImageName(String examImageName) {
		this.examImageName = examImageName;
	}

	public void setExamName(String examName) {
		this.examName = examName;
	}

	public List<ExamSection> getExamSections() {
		return examSections;
	}

	public void setExamSections(List<ExamSection> examSections) {
		this.examSections = examSections;
	}

}
