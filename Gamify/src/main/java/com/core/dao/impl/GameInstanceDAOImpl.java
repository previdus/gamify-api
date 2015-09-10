package com.core.dao.impl;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.core.api.beans.TotalNumberOfGameWonByAUser;
import com.core.dao.GameInstanceDAO;
import com.core.dao.generic.HibernateGenericRepository;
import com.core.domain.User;
import com.core.domain.knockout.GameInstance;

@Repository("gameInstanceDAO")
public class GameInstanceDAOImpl extends
		HibernateGenericRepository<GameInstance, Serializable> implements
		GameInstanceDAO {
	
	public List<TotalNumberOfGameWonByAUser> getTopPersonWhoWonMaxGames(Integer numOfTopPlayersToShow) {
		Session session = getSession();
		Query query = session.createQuery(
				" select gameWinner as winner, count(*) as noOfWins from GameInstance gi where gi.gameWinner is not null group by gi.gameWinner order by noOfWins desc  limit "+numOfTopPlayersToShow);
		List<Object[]> listResult = query.list();
		 List<TotalNumberOfGameWonByAUser> winningUsers = new LinkedList<TotalNumberOfGameWonByAUser>();
		for (Object[] aRow : listResult) {
			//encode sensitive user information here
			try{
				((User)aRow[0]).encodeSensitiveData();
			}
			catch (Exception ex){
				ex.printStackTrace();
			}
			
			winningUsers.add(new TotalNumberOfGameWonByAUser((User)aRow[0], (Long)aRow[1]));
		}
		releaseSession(session);
		return winningUsers;
	}
}
