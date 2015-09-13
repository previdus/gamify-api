package com.core.service.impl;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.core.constants.GameConstants;
import com.core.domain.User;
import com.core.domain.knockout.GameInstance;
import com.core.domain.knockout.Player;
import com.core.domain.knockout.PlayerEloRating;
import com.core.service.PlayerRatingService;

@Service("playerRatingService")
public class PlayerRatingServiceImpl implements PlayerRatingService {
	
	
	
    
    
    public void  calulateRatingAndNumberOfGamesPlayed(GameInstance gameInstance)
    {   
    	User gameWinner = gameInstance.getGameWinner();
    	Map<Long,Player> players = gameInstance.getPlayers();      
        int numberOfPlayers = players.size();
        createNewPlayerRatingIfItDoesNotExist(players);
        for(Player player: players.values())
        {
            //losing case
            if(thereAreNoWinnersOrThisPlayerIsNotTheWinner(gameWinner, player))
            {
            	
                int playerScore = (int) Math.round(player.getPlayerEloRating().getRating() + 
                        calcRatingHelperLose(player, numberOfPlayers, calcAverage(players, player)));
                
                player.getPlayerEloRating().setRating(playerScore);               
               
            }
            //winning case
            else
            {
            	int playerScore =  (int) Math.round(player.getPlayerEloRating().getRating() +
                        calcRatingHelperWin(player, numberOfPlayers, calcAverage(players, player)));
               
            	 player.getPlayerEloRating().setRating(playerScore);  
               
               
            }
            
        }
        
        
    }



	private static void createNewPlayerRatingIfItDoesNotExist(Map<Long,Player> players) {
		for(Player player:players.values()){
			if(player.getPlayerEloRating() == null){
				PlayerEloRating per = new PlayerEloRating();
				per.setNoOfGamesPlayed(1);
				per.setRating(GameConstants.INITIAL_ELO_RATING);
				per.setPlayer(player);
				player.setPlayerEloRating(per);
			}
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
            avgRating += player.getPlayerEloRating().getRating();
        }
        avgRating -= currentPlayer.getPlayerEloRating().getRating();
        avgRating = avgRating / (players.size() - 1);
        return avgRating;
    }
    
    private static double calcRatingHelperWin(Player player, int numPlayers, int avgRating)
    {
        int rating = player.getPlayerEloRating().getRating();
        
        double exponent = -1 * (rating - avgRating) / 400.0;
        double denominator = Math.pow(10, exponent) + 1;
        double expectancy = (1.0 / denominator) * (2.0 / numPlayers);
        if(player.getPlayerEloRating().isProvisional())
        {
            return (2 * GameConstants.KVALUE_FOR_ELO_RATING * (1 - expectancy));
        }
        return (GameConstants.KVALUE_FOR_ELO_RATING * (1 - expectancy));
    }
    
    private static double calcRatingHelperLose(Player player, int numPlayers, int avgRating)
    {
        int rating = player.getPlayerEloRating().getRating();
        
        double exponent = -1 * (rating - avgRating) / 400.0;
        double denominator = Math.pow(10, exponent) + 1;
        double expectancy = (1.0 / denominator) * (2.0 / numPlayers);
        if(player.getPlayerEloRating().isProvisional())
        {
            return (2 * GameConstants.KVALUE_FOR_ELO_RATING * (0 - expectancy));
        }
        return (GameConstants.KVALUE_FOR_ELO_RATING * (0 - expectancy));
    }

}
