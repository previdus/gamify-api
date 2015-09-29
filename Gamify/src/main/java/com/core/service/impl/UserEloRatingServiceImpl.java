package com.core.service.impl;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core.api.beans.UserEloRatingDTO;
import com.core.constants.GameConstants;
import com.core.dao.UserEloRatingPerTopicDAO;
import com.core.domain.User;
import com.core.domain.knockout.GameInstance;
import com.core.domain.knockout.Player;
import com.core.domain.knockout.UserEloRatingPerTopic;
import com.core.domain.knockout.UserTopic;
import com.core.domain.lms.Topic;
import com.core.service.UserEloRatingService;

@Service("userEloRatingService")
public class UserEloRatingServiceImpl implements UserEloRatingService {
	
	
	@Autowired
	public UserEloRatingPerTopicDAO userEloRatingPerTopicDAO;
	
	
	
	public List<UserEloRatingPerTopic> getTopUserEloRatingsPerTopic(Integer noOfPlayersToShow, Long topicId, boolean excludeProvisional){
		return userEloRatingPerTopicDAO.getTopUserEloRatingsPerTopic(noOfPlayersToShow, topicId, excludeProvisional);
	}
	
	public List<UserEloRatingDTO> getTopUserEloRatingsPerExamSection(Integer noOfPlayersToShow, Long examSectionId){
		return userEloRatingPerTopicDAO.getTopUserEloRatingsPerExamSection(noOfPlayersToShow, examSectionId);
	}
	
	public List<UserEloRatingDTO> getTopUserEloRatingsPerExam(Integer noOfPlayersToShow, Long examId){
		return userEloRatingPerTopicDAO.getTopUserEloRatingsPerExam(noOfPlayersToShow, examId);
	}
    
    public void  calulateUserEloRating(GameInstance gameInstance)
    {   
    	User gameWinner = gameInstance.getCurrentQuestionWinner();
    	Map<Long,Player> players = gameInstance.getPlayers();      
        int numberOfPlayers = players.size();
        Topic currentTopic = gameInstance.getCurrentQuestion().fetchTopic();
        List<UserEloRatingPerTopic> userEloRatingsPerTopic = new LinkedList<UserEloRatingPerTopic>();
        populateUserEloRatingsPerTopic(userEloRatingsPerTopic, gameInstance.getPlayers().values(), currentTopic);
        for(UserEloRatingPerTopic userEloRating: userEloRatingsPerTopic)
        {
        	
            		
            int numberFromWhichExpectancyWillBeSubtracted =  (thereAreNoWinnersOrThisPlayerIsNotTheWinnerForTheTopic(gameWinner, userEloRating))?
            		                                        GameConstants.LOSER_NUMBER_FROM_WHICH_EXPECTANCY_WILL_BE_SUBTRACTED_FOR_ELO_RATING:
            		                                        GameConstants.WINNER_NUMBER_FROM_WHICH_EXPECTANCY_WILL_BE_SUBTRACTED_FOR_ELO_RATING;	
            		
            double rating = calcRatingHelper(userEloRating.getEloRating(), numberOfPlayers, calcAverageRatingsPerTopic(userEloRatingsPerTopic, userEloRating), 
            		numberFromWhichExpectancyWillBeSubtracted, userEloRating.isUserRatingProvisional());
            int playerScore =  (int)( Math.round(userEloRating.getEloRating() + rating));
           
            userEloRating.setEloRating(playerScore);
            userEloRating.incrementNoOfQuestionsAttempted();
            userEloRatingPerTopicDAO.saveOrUpdate(userEloRating);
        }        
    }
    
    private void populateUserEloRatingsPerTopic(List<UserEloRatingPerTopic> userEloRatings, Collection<Player> players, Topic currentTopic){
    	
    	for(Player player: players){
    		UserEloRatingPerTopic uer = userEloRatingPerTopicDAO.getUserEloRatingPerTopic(player.getUser().getId(), currentTopic.getId());
    		if (uer == null){
    			uer = new UserEloRatingPerTopic();
    			uer.setEloRating(GameConstants.DEFAULT_INITIAL_USER_ELO_RATING);
    			UserTopic ut = new UserTopic();
    			ut.setUser(player.getUser());
    			ut.setTopic(currentTopic);
    			uer.setUserTopic(ut);
        	}
    		userEloRatings.add(uer);
    	}
    	
    }
    
  
  

	private static boolean thereAreNoWinnersOrThisPlayerIsNotTheWinnerForTheTopic(
			User gameWinner, UserEloRatingPerTopic userEloRating) {
		return gameWinner == null || userEloRating.getUserTopic().getUser().getId() != gameWinner.getId();
	}
	
	
    
    private static int calcAverageRatingsPerTopic(List<UserEloRatingPerTopic> userEloRatings, UserEloRatingPerTopic currentUserEloRating)
    {
        int avgRating = 0;
        for(UserEloRatingPerTopic userEloRating: userEloRatings)
        {
            avgRating += userEloRating.getEloRating();
        }
        avgRating -= currentUserEloRating.getEloRating();
        avgRating = avgRating / (userEloRatings.size() - 1);
        return avgRating;
    }
    
  
    
    
    private static double calcRatingHelper(int rating, int numPlayers, int avgRating, int numberFromWhichExpectancyWillBeSubtracted, boolean isProvisional){
            
        double exponent = -1 * (rating - avgRating) / 400.0;
        double denominator = Math.pow(10, exponent) + 1;
        double expectancy = (1.0 / denominator) * (2.0 / numPlayers);
      
        return (isProvisional)?(2 * GameConstants.KVALUE_FOR_ELO_RATING * (numberFromWhichExpectancyWillBeSubtracted - expectancy))
        		             :(GameConstants.KVALUE_FOR_ELO_RATING * (numberFromWhichExpectancyWillBeSubtracted - expectancy));
    }

}
