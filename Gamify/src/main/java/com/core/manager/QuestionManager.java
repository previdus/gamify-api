package com.core.manager;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.core.domain.AnswerKey;
import com.core.domain.Question;
import com.core.domain.knockout.GameInstance;
import com.core.domain.knockout.PlayerResponseLog;
import com.core.domain.knockout.PreviousQuestionLog;
import com.core.domain.lms.Topic;
import com.core.service.AnswerKeyService;
import com.core.service.GameInstanceService;
import com.core.service.QuestionService;

@Component
public class QuestionManager {

	private static Random random = new Random();

	private static final Logger log = LoggerFactory
			.getLogger(QuestionManager.class);

	private static AnswerKeyService answerKeyService;
	
	

	/**
	 * Sets the answerKeyService This method should never be called except by
	 * Spring
	 * 
	 * @param userAccessor
	 *            The user accessor to set
	 */
	@Autowired(required = true)
	public void setAnswerKeyService(AnswerKeyService answerKeyService) {
		QuestionManager.answerKeyService = answerKeyService;
	}
	
	private static GameInstanceService gameInstanceService;
		
		@Autowired(required = true)
		public void setGameInstanceService(GameInstanceService gameInstanceService) {
			QuestionManager.gameInstanceService = gameInstanceService;
		}

	private static QuestionService questionService;

	/**
	 * Sets the questionService This method should never be called except by
	 * Spring
	 * 
	 * @param userAccessor
	 *            The user accessor to set
	 */
	@Autowired(required = true)
	public void setQuestionService(QuestionService questionService) {
		QuestionManager.questionService = questionService;
	}

	public static void savePreviousQuestionLog(GameInstance gi) {
		// save previous question log
		try {
			// AnswerKey answerKey =
			// answerKeyService.getAnswerKey(gi.getCurrentQuestion());
			PreviousQuestionLog pql = new PreviousQuestionLog();
			// pql.setAnswerKey(answerKey);
			pql.setQuestion(gi.getCurrentQuestion());
			pql.setGameInstance(gi);
			pql.setPlayersResponses(new ArrayList<PlayerResponseLog>(gi
					.getPlayerResponsesToCurrentQuestion().values()));
			pql.setBestTime(new Long(gi.getBestTimeForCurrentQuestion())
					.toString());
			pql.setWinner(gi.getCurrentQuestionWinner());
			List<PreviousQuestionLog> logs = GameQueueManager.gameResponseLog
					.get(gi.getId());
			if (logs == null)
				logs = new LinkedList<PreviousQuestionLog>();
			logs.add(pql);
			GameQueueManager.gameResponseLog.put(gi.getId(), logs);
			gi.setPreviousQuestionLogs(logs);
						gameInstanceService.saveOrUpdate(gi);
			log.info("Prev Question Log Size after adding "
					+ GameQueueManager.gameResponseLog.get(gi.getId()).size());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void attachQuestionToGameInstance(GameInstance gi) {
		log.info("***************ENTERED - attachQuestionToGameInstance()***************");
		List<Topic> topics = gi.getExamSection().getTopics();

		List<Question> questions = gi.fetchPreLoadedQuestions();

		questions = fetchQuestionsIfEmpty(topics, questions);
		attachNextQuestionToGameInstanceFromPrefetchedQuestions(gi, questions);

	}

	private static void attachNextQuestionToGameInstanceFromPrefetchedQuestions(
			GameInstance gi, List<Question> questions) {
		if (questions.size() > 0) {
			log.info("*************** Attaching ***************");
			gi.setPreLoadedQuestions(questions);
			gi.getPlayerResponsesToCurrentQuestion().clear();

			Question newCurrentQuestion = null;
			if (gi.getCurrentQuestion() == null) {

				Question question = questions.get(random.nextInt(questions.size()));
				AnswerKey answerKey = answerKeyService.getAnswerKey(question);
				gi.setCurrentQuestion(question, answerKey, System.currentTimeMillis());
			} else {

				while (true) {
					newCurrentQuestion = questions.get(random.nextInt(questions
							.size()));
					if (!newCurrentQuestion.getId().equals(
							gi.getCurrentQuestion().getId())) {
						// attach new question
						log.info("*****************************ATTACHING QUESTION*****************************************");
						
						AnswerKey answerKey = answerKeyService.getAnswerKey(newCurrentQuestion);
						gi.setCurrentQuestion(newCurrentQuestion, answerKey, System.currentTimeMillis());
						break;
					} else {
						log.info("ALARM***************************************DUPLICATE QUESTION GENERATED******************************");
					}
				}
			}

		}
	}

	private static List<Question> fetchQuestionsIfEmpty(List<Topic> topics,
			List<Question> questions) {
		if (questions == null || questions.size() == 0) {
			questions = new ArrayList<Question>();
			log.info("***************ENTERED - attachQuestionToGameInstance() - Loading Questions from DB ***************");
			try {
				for (Topic topic : topics) {
					List<Question> thisTopicQuestions = null;
					try {
						thisTopicQuestions = questionService
								.getQuestions(topic);
					} catch (Exception q) {
						q.printStackTrace();
					}
					if (thisTopicQuestions != null
							&& thisTopicQuestions.size() > 0) {

						questions.addAll(thisTopicQuestions);
					}
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return questions;
	}

}
