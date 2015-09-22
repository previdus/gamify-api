package com.core.service.impl;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core.constants.GameConstants;
import com.core.dao.UserDAO;
import com.core.dao.UserEloRatingDAO;
import com.core.domain.User;
import com.core.domain.knockout.GameInstance;
import com.core.domain.knockout.Player;
import com.core.domain.knockout.UserEloRating;
import com.core.service.UserEloRatingService;

@Service("userEloRatingService")
public class UserEloRatingServiceImpl implements UserEloRatingService {
	
	
	@Autowired
	public UserEloRatingDAO userEloRatingDAO;
	
	public List<UserEloRating> getTopUserEloRatings(Integer noOfPlayersToShow, boolean excludeProvisional){
		return userEloRatingDAO.getTopUserEloRatings(noOfPlayersToShow, excludeProvisional);
	}
    
    public void  calulateUserEloRating(GameInstance gameInstance)
    {   
    	User gameWinner = gameInstance.getCurrentQuestionWinner();
    	Map<Long,Player> players = gameInstance.getPlayers();      
        int numberOfPlayers = players.size();
        
        List<UserEloRating> userEloRatings = new LinkedList<UserEloRating>();
        populateUserEloRatings(userEloRatings, gameInstance.getPlayers().values());
       
        for(UserEloRating userEloRating: userEloRatings)
        {
        	
            		
            int numberFromWhichExpectancyWillBeSubtracted =  (thereAreNoWinnersOrThisPlayerIsNotTheWinner(gameWinner, userEloRating))?
            		                                        GameConstants.LOSER_NUMBER_FROM_WHICH_EXPECTANCY_WILL_BE_SUBTRACTED_FOR_ELO_RATING:
            		                                        GameConstants.WINNER_NUMBER_FROM_WHICH_EXPECTANCY_WILL_BE_SUBTRACTED_FOR_ELO_RATING;	
            		
            double rating = calcRatingHelper(userEloRating, numberOfPlayers, calcAverage(userEloRatings, userEloRating), numberFromWhichExpectancyWillBeSubtracted);
            int playerScore =  (int)( Math.round(userEloRating.getEloRating() + rating));
           
            userEloRating.setEloRating(playerScore);
            userEloRating.incrementNoOfQuestionsAttempted();
            userEloRatingDAO.saveOrUpdate(userEloRating);
        }
        
        
    }
    
    private void populateUserEloRatings(List<UserEloRating> userEloRatings, Collection<Player> players){
    	
    	for(Player player: players){
    		UserEloRating uer = userEloRatingDAO.getUserEloRatingByUserId(player.getUser().getId());
    		if (uer == null){
    			uer = new UserEloRating();
    			uer.setEloRating(GameConstants.DEFAULT_INITIAL_USER_ELO_RATING);
    			uer.setUser(player.getUser());
        	}
    		userEloRatings.add(uer);
    	}
    	
    }
  

	private static boolean thereAreNoWinnersOrThisPlayerIsNotTheWinner(
			User gameWinner, UserEloRating userEloRating) {
		return gameWinner == null || userEloRating.getUser().getId() != gameWinner.getId();
	}
    
    private static int calcAverage(List<UserEloRating> userEloRatings, UserEloRating currentUserEloRating)
    {
        int avgRating = 0;
        for(UserEloRating userEloRating: userEloRatings)
        {
            avgRating += userEloRating.getEloRating();
        }
        avgRating -= currentUserEloRating.getEloRating();
        avgRating = avgRating / (userEloRatings.size() - 1);
        return avgRating;
    }
    
    
    
    private static double calcRatingHelper(UserEloRating userEloRating, int numPlayers, int avgRating, int numberFromWhichExpectancyWillBeSubtracted){
       int rating = userEloRating.getEloRating();
        
        double exponent = -1 * (rating - avgRating) / 400.0;
        double denominator = Math.pow(10, exponent) + 1;
        double expectancy = (1.0 / denominator) * (2.0 / numPlayers);
        if(userEloRating.isUserRatingProvisional())
        {
            return (2 * GameConstants.KVALUE_FOR_ELO_RATING * (numberFromWhichExpectancyWillBeSubtracted - expectancy));
        }
        return (GameConstants.KVALUE_FOR_ELO_RATING * (numberFromWhichExpectancyWillBeSubtracted - expectancy));
    }

}
