package com.core.domain.knockout;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.OrderBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.core.constants.GameConstants;
import com.core.constants.GameConstants.GAME_DIFFICULTY_LEVEL;
import com.core.constants.GameConstants.GAME_STATE;
import com.core.constants.UserCategory;
import com.core.domain.AnswerKey;
import com.core.domain.Question;
import com.core.domain.User;
import com.core.domain.lms.ExamSection;
import com.core.manager.QuestionManager;

@Entity
@Table(name = "game_instance")
public class GameInstance implements Serializable {

	@Id
	@GeneratedValue
	private Long id;
	
	@Column(name = "game_creation_time")
	private long gameCreationTime;
	
	private transient long startWaitTime;
	private transient long bestTimeForCurrentQuestion;
	private transient User currentQuestionWinner;
	private transient List<Question> preLoadedQuestions;

	private transient Map<Long, PlayerResponseLog> playerResponsesToCurrentQuestion = new HashMap<Long, PlayerResponseLog>();
	
	private transient Question currentQuestion;
	
	private transient long timeAtWhichCurrentQuestionWasAttached;
	
	//this is the id of the option which is the answerkey. The reason it has been named bang is because
	//the game instance object is visible in the frontend for every ajax poll and the player can easily figure out 
	//what the answer key to the question is if it is named the obvious
	private transient Long  bang;

	@ManyToOne(optional = false)
	@JoinColumn(name = "exam_section_id")
	private ExamSection examSection;

	@Enumerated(EnumType.STRING)
	@Column(name = "game_difficulty_level")
	private GAME_DIFFICULTY_LEVEL difficultyLevel;
	@Enumerated(EnumType.STRING)
	@Column(name = "state")
	private GAME_STATE state;

	private transient Map<Long, Player> losingPlayers = new HashMap<Long, Player>();
	private transient Map<Long, Player> quittingPlayers = new HashMap<Long, Player>();

	public Map<Long, Player> getQuittingPlayers() {
		return quittingPlayers;
	}

	public void setQuittingPlayers(Map<Long, Player> quittingPlayers) {
		this.quittingPlayers = quittingPlayers;
	}

	@ManyToOne(optional = true)
	@JoinColumn(name = "winner_user_id")
	private User gameWinner;

	@OneToMany(mappedBy = "gameInstance", fetch = FetchType.EAGER, cascade = { javax.persistence.CascadeType.ALL })
	@OrderBy(clause = "noOfLife desc")
	@Fetch(value = FetchMode.SUBSELECT)
	@javax.persistence.MapKey(name = "user")
	private Map<Long, Player> players = new HashMap<Long, Player>();

	@OneToMany(mappedBy = "gameInstance", fetch = FetchType.EAGER, cascade = { javax.persistence.CascadeType.ALL })
	@Fetch(value = FetchMode.SUBSELECT)
	private List<PreviousQuestionLog> previousQuestionLogs = new ArrayList<PreviousQuestionLog>();

	
	
	private static transient final Logger log = LoggerFactory
			.getLogger(QuestionManager.class);

	public Long getBang() {
		return bang;
	}

	public void setBang(Long bang) {
		this.bang = bang;
	}
	
	
	public long getBestTimeForCurrentQuestion() {
		return bestTimeForCurrentQuestion;
	}

	public void setBestTimeForCurrentQuestion(long bestTimeForCurrentQuestion) {
		this.bestTimeForCurrentQuestion = bestTimeForCurrentQuestion;
	}

	public User getCurrentQuestionWinner() {
		return currentQuestionWinner;
	}

	public void setCurrentQuestionWinner(User currentQuestionWinner) {
		this.currentQuestionWinner = currentQuestionWinner;
	}

	public Map<Long, Player> getLooserPlayers() {
		return losingPlayers;
	}

	public void setLooserPlayers(Map<Long, Player> looserPlayers) {
		this.losingPlayers = looserPlayers;
	}

	public void incrementPollCountForPlayer(Long userId) {
		Player player = players.get(userId);
		if (player != null) {
			player.incrementPollCount();
		}

	}
	
	public List<Question> fetchPreLoadedQuestions() {
		return preLoadedQuestions;
	}

	public void setPreLoadedQuestions(List<Question> preLoadedQuestions) {
		this.preLoadedQuestions = preLoadedQuestions;
	}

	public void addPlayer(User user) {
		Player player = new Player();
		player.setUser(user);
		player.setNoOfLife(GameConstants.NUM_OF_LIVES);
		player.setPlayerJoinTime(System.currentTimeMillis());
		player.setGameInstance(this);
		// this is to give a baut user so many poll count that he is not thrown out of game
		if(GameConstants.ADD_BOUT_USER_AFTER_WAITING_MILLISECONDS > 0 && UserCategory.B.equals(user.getCategory()))
				player.setNoOfPollsSoFar(50000);
		this.players.put(user.getId(), player);
	}

	public void addPlayerFromAnotherGame(Player player) {
		player.setNoOfLife(GameConstants.NUM_OF_LIVES);
		player.setPlayerJoinTime(System.currentTimeMillis());
		player.resetPollCount();
		this.players.put(player.getUser().getId(), player);
	}

	public void removePlayer(User user, boolean playerLost) {
		System.out
				.println("Looser Player *************************************"
						+ user.getId());
		if(playerLost){
			losingPlayers.put(user.getId(), this.players.get(user.getId()));
		}
		else{
			quittingPlayers.put(user.getId(), this.players.get(user.getId()));
		}
		this.players.remove(user.getId());
	}

	public void removePlayers(List<Long> userIds) {
		if (userIds != null) {
			for (long userId : userIds) {
				losingPlayers.put(userId, this.players.get(userId));
				this.players.remove(userId);
			}
		}
	}

	public void markGameWinner() {
		log.info("Marking Game Winner");
		if (whenNoWinnerInTheGameYetAndOnlyOneActivePlayerAndThereIsAtLeastOneLoser()) {
			Player player = new LinkedList<Player>(this.getPlayers().values()).get(0);
			if(player.getNoOfLife() > 0){
			this.setGameWinner(player.getUser());
			log.info("Game Winner Is " + player.getUser().getId());
			}
		}
	}

	private boolean whenNoWinnerInTheGameYetAndOnlyOneActivePlayerAndThereIsAtLeastOneLoser() {
		return this.getGameWinner() == null && this.getPlayers() != null
				&& this.getPlayers().size() == 1 && (this.getLooserPlayers() != null && this.getLooserPlayers().size() > 0 );
	}

	public Map<Long, PlayerResponseLog> getPlayerResponsesToCurrentQuestion() {
		return this.playerResponsesToCurrentQuestion;
	}

	public void setPlayerResponsesToCurrentQuestion(
			Map<Long, PlayerResponseLog> playerResponsesToCurrentQuestion) {
		this.playerResponsesToCurrentQuestion = playerResponsesToCurrentQuestion;
	}

	public long getStartWaitTime() {
		return startWaitTime;
	}

	public void setStartWaitTime(long startWaitTime) {
		this.startWaitTime = startWaitTime;
	}

	public GAME_STATE getState() {
		return state;
	}

	public void setState(GAME_STATE state) {
		if (!state.equals(GameConstants.GAME_STATE.READY)
				&& !state.equals(GameConstants.GAME_STATE.ONGOING)) {
			this.currentQuestion = null;
		}
		this.state = state;

	}

	public Map<Long, Player> getPlayers() {
		return players;
	}

	public void setPlayers(Map<Long, Player> players) {
		this.players = players;
	}

	public boolean isPlayerInGame(Long userId) {
		return players.get(userId) != null;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public long getGameCreationTime() {
		return gameCreationTime;
	}

	public void setGameCreationTime(long startTime) {
		this.gameCreationTime = startTime;
	}

	public int getNumOfPlayers() {
		return this.players.size();
	}

	public ExamSection getExamSection() {
		return examSection;
	}

	public void setExamSection(ExamSection examSection) {
		this.examSection = examSection;
	}

	public GAME_DIFFICULTY_LEVEL getDifficultyLevel() {
		return difficultyLevel;
	}

	public void setDifficultyLevel(GAME_DIFFICULTY_LEVEL difficultyLevel) {
		this.difficultyLevel = difficultyLevel;
	}

	public long getTimeAtWhichCurrentQuestionWasAttached() {
		return timeAtWhichCurrentQuestionWasAttached;
	}

	public Question getCurrentQuestion() {
		return currentQuestion;
	}

	public void setCurrentQuestion(Question currentQuestion, AnswerKey key, long timeAtWhichCurrentQuestionWasAttached) {
		log.info("attaching question with id:" + currentQuestion.getId());
		this.currentQuestion = currentQuestion;
		if(key!=null){
			this.bang = key.getOptionId();
		}
	
		this.timeAtWhichCurrentQuestionWasAttached = timeAtWhichCurrentQuestionWasAttached;
	}

	public List<PreviousQuestionLog> getPreviousQuestionLogs() {
		return previousQuestionLogs;
	}

	public void setPreviousQuestionLogs(
			List<PreviousQuestionLog> previousQuestionLogs) {
		this.previousQuestionLogs = previousQuestionLogs;
	}

	public User getGameWinner() {
		return gameWinner;
	}

	public void setGameWinner(User gameWinner) {
		this.gameWinner = gameWinner;
	}

	public void setStateToDone() {
		this.state = GameConstants.GAME_STATE.DONE;
		this.currentQuestion = null;
		this.playerResponsesToCurrentQuestion.clear();
	}

	public GameInstance() {
		// id = GameConstants.GAME_INSTANCE_ID++;
	}

	public void resetCurrentQuestionWinnerAndBestTime() {
		this.bestTimeForCurrentQuestion = -1L;
		this.currentQuestionWinner = null;
		// this.currentQuestion = null;
	}

	public void addPreviousQuestionLog(PreviousQuestionLog pql) {
		pql.setGameInstance(this);
		this.previousQuestionLogs.add(pql);
	}
	
	public boolean haveAllPlayersResponded(){
		return this.getPlayers().size() == this.getPlayerResponsesToCurrentQuestion().size();
	}

	public boolean hasPlayerLostTheGame(Long userId) {
		return this.losingPlayers.get(userId) != null;
	}

	public boolean isGameStillActive() {
		return !this.state.equals(GAME_STATE.DONE)
				&& !this.state.equals(GAME_STATE.EXPIRED); 
	}

}
