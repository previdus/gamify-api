package com.core.service.impl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core.constants.GameConstants.GAME_DIFFICULTY_LEVEL;
import com.core.constants.GameConstants.GAME_STATE;

import com.core.dao.AnswerKeyDAO;
import com.core.dao.ExamDAO;
import com.core.dao.ExamSectionDAO;
import com.core.dao.GameInstanceDAO;
import com.core.dao.OptionDAO;
import com.core.dao.QuestionDAO;
import com.core.dao.TopicDAO;
import com.core.dao.UserDAO;
import com.core.domain.AnswerKey;
import com.core.domain.Option;
import com.core.domain.Question;
import com.core.domain.User;
import com.core.domain.knockout.GameInstance;
import com.core.domain.knockout.PlayerResponseLog;
import com.core.domain.knockout.PreviousQuestionLog;
import com.core.domain.lms.Exam;
import com.core.domain.lms.ExamSection;
import com.core.domain.lms.Topic;
import com.core.service.GenerateDummyDataInDatabase;
import com.core.stub.GameStub;

@Service("generateDummyDataInDatabase")
public class GenerateDummyDataInDatabaseImpl implements
		GenerateDummyDataInDatabase {

	@Autowired
	private UserDAO userDAO;
	@Autowired
	private ExamDAO examDAO;
	@Autowired
	private TopicDAO topicDAO;
	@Autowired
	private ExamSectionDAO examSectionDAO;
	@Autowired
	private QuestionDAO questionDAO;
	@Autowired
	private OptionDAO optionDAO;
	@Autowired
	private AnswerKeyDAO answerKeyDAO;
	@Autowired
	private GameInstanceDAO gameInstanceDAO;

	public void generateData() {
		// this is a git testing
		// examAndSections();
		// createUsers();
		// generateDummyQuestions();
		saveDummyGI();

	}

	public void saveDummyGI() {
		GameInstance gi = new GameInstance();
		gi.setExamSection(new ExamSection(1L));
		gameInstanceDAO.saveNew(gi);
		gameInstanceDAO.detach(gi);
		gi.addPlayer(new User(1l));
		gi.addPlayer(new User(2l));
		gi.setDifficultyLevel(GAME_DIFFICULTY_LEVEL.EASY);
		gi.setState(GAME_STATE.DONE);
		gi.setGameWinner(new User(1l));

		// gameInstanceDAO.detach(gi);
		List<PreviousQuestionLog> logs = new LinkedList<PreviousQuestionLog>();
		PreviousQuestionLog pql1 = new PreviousQuestionLog(new Question(2l),
				null, new User(1l), gi);
		pql1.addPlayerResponses(new PlayerResponseLog(new User(1l), new Option(
				1l), 1l, 1));
		pql1.addPlayerResponses(new PlayerResponseLog(new User(2l), new Option(
				2l), 2l, 2));
		// logs.add(new PreviousQuestionLog(new Question(3l), null, new
		// User(2l), new AnswerKey(3l, 1l) ,gi ));
		logs.add(pql1);
		gi.setPreviousQuestionLogs(logs);
		try {
			gameInstanceDAO.saveOrUpdate(gi);
		} catch (Exception ex) {
			ex.printStackTrace();
			gameInstanceDAO.merge(gi);
		}

	}

	private User createUser(int i) {
		User user = new User();
		user.setDisplayName("Last man standing " + i);
		user.setEmailId("lastmanstanding" + i + "@gmail.com");
		user.setGender("male");
		user.setId(new Long(i));
		user.setName("lastmanstanding" + i++);
		user.setPwd("lms");
		return user;
	}

	private void createUsers() {
		for (int i = 1; i < 100; i++) {
			try {
				userDAO.saveNew(createUser(i));
			} catch (Exception ex) {
				//log.info("Cannot Create Mock Users " + ex);
			}
		}
	}

	private void examAndSections() {
		exams();
		topics();
		examSections();

	}

	private void generateDummyQuestions() {
		for (long i = 1; i < 10l; i++)
			createQuestions(new Topic(i));
	}

	private void createQuestions(Topic topic) {
		for (int j = 0; j < 20; j++)
			saveQuestion(topic, j);
	}

	private void saveQuestion(Topic topic, int j) {
		List<Option> options = null;
		Option correctOption = null;
		Question question = null;
		options = new ArrayList<Option>();
		correctOption = null;
//		question = new Question("Question " + topic.getName() + j, null,
//				options, topic, (byte) 0);
		question = questionDAO.saveNew(question);
//		for (int i = 0; i < 4; i++) {
//			options.add(optionDAO.saveNew(new Option("option" + i, null,
//					(i + 1), question)));
//		}
//		correctOption = optionDAO.saveNew(new Option("option" + 4, null,
//				(4 + 1), question));
		options.add(correctOption);
		answerKeyDAO.saveNew(new AnswerKey(question, correctOption));

	}

	private void topics() {
		for (Topic topic : GameStub.getTopics()) {
			topicDAO.saveOrUpdate(topic);
		}
	}

	private void examSections() {
		for (ExamSection section : GameStub.getExamSection()) {
			examSectionDAO.saveOrUpdate(section);
		}
	}

	private void exams() {
		for (Exam section : GameStub.getExams()) {
			examDAO.saveOrUpdate(section);
		}
	}
}
