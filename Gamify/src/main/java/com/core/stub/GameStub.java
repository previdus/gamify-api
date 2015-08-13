package com.core.stub;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.core.domain.Option;
import com.core.domain.Question;
import com.core.domain.User;
import com.core.domain.lms.Exam;
import com.core.domain.lms.ExamSection;
import com.core.domain.lms.Room;
import com.core.domain.lms.Topic;

public class GameStub {

	public static Map<String, User> usersMapForlogin = new ConcurrentHashMap<String, User>();
	public static Map<Long, User> users = new HashMap<Long, User>();
	public static Map<Long, ExamSection> examSectionMap = new HashMap<Long, ExamSection>();
	public static Map<Long, Option> questionCorrectAnswerMap = new HashMap<Long, Option>();
	public static List<Exam> exams;
	public static Room room = new Room();

	private static String[] optionTemplate = { "a", "b", "c", "d", "e" };

	private static long OPTION_COUNT = 1;
	private static long QUESTION_COUNT = 1;

	public static Map<Long, List<Question>> topicQuestions = new HashMap<Long, List<Question>>();

	static {

		for (int i = 0; i < 100; i++) {
			createUser(i);
		}

		exams = new ArrayList<Exam>();
		/**
		 * Exam 1 GMAT
		 */
		Exam exam = new Exam();
		exam.setId(1L);
		exam.setExamName("GMAT");
		exam.setExamImageName(exam.getExamName().toLowerCase());

		List<ExamSection> examSections = new ArrayList<ExamSection>();

		ExamSection examSection = new ExamSection();
		examSection.setId(1L);
		examSection.setName("Gmat Quant");

		List<Topic> topics = new ArrayList<Topic>();
		Topic topic = new Topic();

		topic.setId(1L);
		topic.setName("Gmat DS");
		topics.add(topic);

		createQuestions(topic);

		topic.setId(2L);
		topic.setName("GMAT PS");
		topics.add(topic);
		createQuestions(topic);
		examSection.setTopics(topics);
		examSections.add(examSection);
		examSectionMap.put(examSection.getId(), examSection);

		examSection = new ExamSection();
		examSection.setId(2L);
		examSection.setName("Gmat Verbal");
		topics = new ArrayList<Topic>();
		topic = new Topic();
		topic.setId(3L);
		topic.setName("GMAT SC");
		topics.add(topic);
		createQuestions(topic);
		topic.setId(4L);
		topic.setName("GMAT RC");
		topics.add(topic);
		createQuestions(topic);
		topic.setId(5L);
		topic.setName("GMAT CR");
		topics.add(topic);
		createQuestions(topic);
		examSection.setTopics(topics);
		examSections.add(examSection);
		examSectionMap.put(examSection.getId(), examSection);

		examSection = new ExamSection();
		examSection.setId(3L);
		examSection.setName("Full Length GMAT");
		topics = new ArrayList<Topic>();
		topic = new Topic();
		topic.setId(1L);
		topic.setName("GMAT DS");
		topics.add(topic);
		createQuestions(topic);
		topic.setId(2L);
		topic.setName("GMAT PS");
		topics.add(topic);
		createQuestions(topic);
		topic.setId(3L);
		topic.setName("GMAT SC");
		topics.add(topic);
		createQuestions(topic);
		topic.setId(4L);
		topic.setName("RCGMAT");
		topics.add(topic);
		createQuestions(topic);
		topic.setId(5L);
		topic.setName("GMAT RC");
		topics.add(topic);
		createQuestions(topic);
		examSection.setTopics(topics);
		examSections.add(examSection);
		examSectionMap.put(examSection.getId(), examSection);

		exam.setExamSections(examSections);

		exams.add(exam);
		// Exam 1 end

		/**
		 * Exam 2 GRE
		 */
		exam = new Exam();
		exam.setId(2L);
		exam.setExamName("GRE");
		exam.setExamImageName(exam.getExamName().toLowerCase());
		examSections = new ArrayList<ExamSection>();
		examSection = new ExamSection();
		examSection.setId(4L);
		examSection.setName("GRE Quant");
		topics = new ArrayList<Topic>();
		topic = new Topic();
		topic.setId(6L);
		topic.setName("GRE Algebra");
		topics.add(topic);
		createQuestions(topic);
		topic.setId(7L);
		topic.setName("GRE Geometry");
		topics.add(topic);

		createQuestions(topic);

		examSection.setTopics(topics);
		examSections.add(examSection);
		examSectionMap.put(examSection.getId(), examSection);
		topic = new Topic();
		topic.setId(1L);
		topic.setName("GMAT DS");
		topics.add(topic);
		topic.setId(2L);
		topic.setName("GMAT PS");
		topics.add(topic);
		topic.setId(3L);
		topic.setName("GMAT SC");
		topics.add(topic);
		topic.setId(4L);
		topic.setName("RCGMAT");
		topics.add(topic);
		createQuestions(topic);
		topic.setId(5L);
		topic.setName("GMAT RC");
		topics.add(topic);
		examSection = new ExamSection();
		examSection.setId(5L);
		examSection.setName("GRE Verbal");
		topics = new ArrayList<Topic>();
		topic = new Topic();
		topic.setId(8L);
		topic.setName("GRE SC");
		topics.add(topic);
		createQuestions(topic);
		topic.setId(9L);
		topic.setName("GRE RC");
		topics.add(topic);
		createQuestions(topic);
		topic.setId(10L);
		topic.setName("GRE CR");
		topics.add(topic);
		createQuestions(topic);
		examSection.setTopics(topics);
		examSections.add(examSection);
		examSectionMap.put(examSection.getId(), examSection);

		examSection = new ExamSection();
		examSection.setId(6L);
		examSection.setName("Full Length GRE");
		topics = new ArrayList<Topic>();
		topic = new Topic();
		topic.setId(6L);
		topic.setName("GRE Algebra");
		topics.add(topic);
		createQuestions(topic);
		topic.setId(7L);
		topic.setName("GRE Geometry");
		topics.add(topic);
		createQuestions(topic);
		topic.setId(8L);
		topic.setName("GRE SC");
		topics.add(topic);
		createQuestions(topic);
		topic.setId(9L);
		topic.setName("GRE RC");
		topics.add(topic);
		createQuestions(topic);
		topic.setId(10L);
		topic.setName("GRE CR");
		topics.add(topic);
		createQuestions(topic);
		examSection.setTopics(topics);
		examSections.add(examSection);
		examSectionMap.put(examSection.getId(), examSection);

		exam.setExamSections(examSections);
		exams.add(exam);

		room.setExams(exams);
		// log.info("completely done loading static maps in GameStub");
	}

	public static List<Exam> getExams() {
		List<Exam> exams = new ArrayList<Exam>();
		exams.add(new Exam(1L, "GMAT", "GMAT", null));
		exams.add(new Exam(2L, "GRE", "GRE", null));
		exams.add(new Exam(3L, "CAT", "CAT", null));
		return exams;
	}

	public static List<ExamSection> getExamSection() {
		List<ExamSection> examSections = new ArrayList<ExamSection>();

		List<Topic> topics = new ArrayList<Topic>();
		topics.add(new Topic(3L));
		topics.add(new Topic(2L));
		examSections.add(new ExamSection(1L, " VERBAL ", topics, new Exam(1l)));
		topics = new ArrayList<Topic>();
		topics.add(new Topic(7L));
		topics.add(new Topic(8L));
		examSections.add(new ExamSection(2L, " QUANT  ", topics, new Exam(1l)));
		topics.add(new Topic(3L));
		topics.add(new Topic(2L));
		examSections.add(new ExamSection(3L, " FULL LENGTH  ", topics,
				new Exam(1l)));

		topics = new ArrayList<Topic>();
		topics.add(new Topic(1L));
		topics.add(new Topic(4L));
		examSections.add(new ExamSection(5L, " QUANT  ", topics, new Exam(2l)));
		topics = new ArrayList<Topic>();
		topics.add(new Topic(5L));
		topics.add(new Topic(6L));
		examSections.add(new ExamSection(4L, " VERBAL ", topics, new Exam(2l)));
		topics.add(new Topic(1L));
		topics.add(new Topic(4L));
		examSections.add(new ExamSection(6L, " FULL LENGTH  ", topics,
				new Exam(2l)));
		topics = new ArrayList<Topic>();
		topics.add(new Topic(1L));
		topics.add(new Topic(4L));
		examSections.add(new ExamSection(8L, " QUANT  ", topics, new Exam(3l)));
		topics = new ArrayList<Topic>();
		topics.add(new Topic(9L));
		examSections.add(new ExamSection(7L, " VERBAL ", topics, new Exam(3l)));
		topics.add(new Topic(1L));
		topics.add(new Topic(4L));
		examSections.add(new ExamSection(9L, " FULL LENGTH  ", topics,
				new Exam(3l)));
		return examSections;

	}

	public static List<Topic> getTopics() {
		List<Topic> topics = new ArrayList<Topic>();
		topics.add(new Topic(1L, "Gemeotry"));
		topics.add(new Topic(2L, "GMAT SC"));
		topics.add(new Topic(3L, "GMAT CR"));
		topics.add(new Topic(4L, "Alzebra"));
		topics.add(new Topic(5L, "GRE RC"));
		topics.add(new Topic(6L, "GRE CR"));
		topics.add(new Topic(7L, "GMAT DS"));
		topics.add(new Topic(8L, "GMAT PS"));
		topics.add(new Topic(9L, "CAT VERBAL"));
		return topics;
	}

	private static void createQuestions(Topic topic) {

		List<Question> questions = new ArrayList<Question>();
		for (int j = 0; j < 10; j++) {

			Question question = new Question();
			question.setDifficultyLevel((byte) 0);

			question.setId(QUESTION_COUNT);
			question.setQuestionText("Question" + QUESTION_COUNT++);
			question.setTopic(topic);
			List<Option> options = new ArrayList<Option>();
			// log.info("before option_count loop");
			for (long i = OPTION_COUNT; i < OPTION_COUNT + 5; i++) {
				Option option = new Option(i);
				option.setText("option" + i);
				options.add(option);
			}

			question.setOptions(options);
			questions.add(question);

			questionCorrectAnswerMap.put(question.getId(), options.get(0));

		}
		topicQuestions.put(topic.getId(), questions);

	}

	private static void createUser(int i) {
		User user = new User();
		user.setDisplayName("Last man standing " + i);
		user.setEmailId("lastmanstanding" + i + "@gmail.com");
		user.setGender("male");
		user.setId(new Long(i));
		user.setName("lastmanstanding" + i++);
		user.setPwd("lms");
		usersMapForlogin.put(user.getName(), user);
		// log.info("user.getId():"+
		// usersMapForlogin.get(user.getName()).getId());
		users.put(user.getId(), user);
	}

}
