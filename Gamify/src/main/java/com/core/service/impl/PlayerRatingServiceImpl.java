package com.core.service.impl;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.core.constants.GameConstants;
import com.core.domain.User;
import com.core.domain.knockout.GameInstance;
import com.core.domain.knockout.Player;
import com.core.service.PlayerRatingService;

@Service("playerRatingService")
public class PlayerRatingServiceImpl implements PlayerRatingService {
	
	
	
    
    
    public void  calulateRatingAndNumberOfGamesPlayed(GameInstance gameInstance)
    {   
    	User gameWinner = gameInstance.getCurrentQuestionWinner();
    	Map<Long,Player> players = gameInstance.getPlayers();      
        int numberOfPlayers = players.size();
       
        for(Player player: players.values())
        {
                        
            double rating = (thereAreNoWinnersOrThisPlayerIsNotTheWinner(gameWinner, player))?
            		calcRatingHelperLose(player, numberOfPlayers, calcAverage(players, player)):
            			calcRatingHelperWin(player, numberOfPlayers, calcAverage(players, player));
            int playerScore =  (int)( Math.round(player.getEloRating() + rating));
           
            player.setEloRating(playerScore);
            player.setNoOfQuestionsAnswered(player.getNoOfQuestionsAnswered() + 1);
        }
        
        
    }

	private static boolean thereAreNoWinnersOrThisPlayerIsNotTheWinner(
			User gameWinner, Player player) {
		return gameWinner == null || player.getUser().getId() != gameWinner.getId();
	}
    
    private static int calcAverage(Map<Long,Player> players, Player currentPlayer)
    {
        int avgRating = 0;
        for(Player player: players.values())
        {
            avgRating += player.getEloRating();
        }
        avgRating -= currentPlayer.getEloRating();
        avgRating = avgRating / (players.size() - 1);
        return avgRating;
    }
    
    private static double calcRatingHelperWin(Player player, int numPlayers, int avgRating)
    {
        int rating = player.getEloRating();
        
        double exponent = -1 * (rating - avgRating) / 400.0;
        double denominator = Math.pow(10, exponent) + 1;
        double expectancy = (1.0 / denominator) * (2.0 / numPlayers);
        if(player.isProvisional())
        {
            return (2 * GameConstants.KVALUE_FOR_ELO_RATING * (1 - expectancy));
        }
        return (GameConstants.KVALUE_FOR_ELO_RATING * (1 - expectancy));
    }
    
    private static double calcRatingHelperLose(Player player, int numPlayers, int avgRating)
    {
        int rating = player.getEloRating();
        
        double exponent = -1 * (rating - avgRating) / 400.0;
        double denominator = Math.pow(10, exponent) + 1;
        double expectancy = (1.0 / denominator) * (2.0 / numPlayers);
        if(player.isProvisional())
        {
            return (2 * GameConstants.KVALUE_FOR_ELO_RATING * (0 - expectancy));
        }
        return (GameConstants.KVALUE_FOR_ELO_RATING * (0 - expectancy));
    }

}
