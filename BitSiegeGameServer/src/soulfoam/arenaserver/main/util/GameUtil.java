package soulfoam.arenaserver.main.util;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import soulfoam.arenaserver.entities.Entity;
import soulfoam.arenaserver.entities.challengers.bots.Bot;
import soulfoam.arenaserver.main.game.Game;
import soulfoam.arenaserver.main.resources.Res;
import soulfoam.arenashared.main.entityinfo.EntityInfo;

public class GameUtil {

	public ArrayList<Integer> getRandomTeam(int teamSize, byte team){
		
		ArrayList<Integer> botList = new ArrayList<Integer>();
		ArrayList<Integer> currentTeamList = new ArrayList<Integer>();
		ArrayList<Integer> returnList = new ArrayList<Integer>();
		
		int[] shuffledList = Bot.BOTLIST;
		
		shuffleArray(shuffledList);
		
		for (int i = 0; i < shuffledList.length; i++){
			botList.add(shuffledList[i]);
		}
		
		for (Entity e : getTeam(team)){
			if (e.isBot()){
				currentTeamList.add(e.getChallengerType());
				currentTeamList.add(getPlayerIDFromBotID(e.getChallengerType()));
			}
			else{
				currentTeamList.add(e.getChallengerType());
				currentTeamList.add(getBotIDFromPlayerID(e.getChallengerType()));
			}
		}
		
		for (int b = 0; b < botList.size(); b++){
			if (b == teamSize) break;
			if (!currentTeamList.contains(botList.get(b))){
				returnList.add(botList.get(b));
			}
		}

		return returnList;
		
	}

	public ArrayList<Entity> getTeam(byte team){
		ArrayList<Entity> returnList = new ArrayList<Entity>();
		for (Entity e : Game.getGame().getPlayers()){
			if (e.getTeam() == team){
				returnList.add(e);
			}
		}
		
		return returnList;
	}
	
	public int getTeamSize(byte team){
		int theCount = 0;
		for (int i = 0; i < Game.getGame().getPlayers().size(); i++){
			if (Game.getGame().getPlayers().get(i).getTeam() == team){
				theCount++;
			}
		}
		return theCount;
	}
	
	public int getBotNotOnTeam(byte team){
		Entity[] tempList = new Entity[Game.getGame().getPlayers().size()];
		Game.getGame().getPlayers().toArray(tempList);
		ArrayList<Integer> teamList = new ArrayList<Integer>();
		ArrayList<Integer> botList = new ArrayList<Integer>();
		
		for (Entity b : tempList){
			if (b != null && b.getTeam() == team){
				teamList.add(b.getChallengerType());
			}
		}
		
		for (int botType : Bot.BOTLIST){
			if (!teamList.contains(botType) && !teamList.contains(getBotIDFromPlayerID(botType))){
				botList.add(botType);
			}
		}
		
		return botList.get(Game.getGame().getRandom().nextInt(botList.size()));
		
	}
	
	public int getBotIDFromPlayerID(int playerType){
		
		if (playerType == EntityInfo.ARCHERCHALLENGER){
			 return Bot.ARCHERCLASSBOT;
		}
		if (playerType == EntityInfo.KNIGHTCHALLENGER){
			return Bot.KNIGHTCLASSBOT;
		}
		if (playerType == EntityInfo.CLERICCHALLENGER){
			return Bot.CLERICCLASSBOT;
		}
		if (playerType == EntityInfo.ILLUSIONISTCHALLENGER){
			return Bot.ILLUSIONISTCLASSBOT;
		}
		if (playerType == EntityInfo.VOIDLORDCHALLENGER){
			return Bot.VOIDLORDCLASSBOT;
		}
		if (playerType == EntityInfo.WARLOCKCHALLENGER){
			return Bot.WARLOCKCLASSBOT;
		}
		if (playerType == EntityInfo.WATERQUEENCHALLENGER){
			return Bot.WATERQUEENCLASSBOT;
		}
		if (playerType == EntityInfo.SHAMANCHALLENGER){
			return Bot.SHAMANCLASSBOT;
		}
		
		return -1;
	}
	
	public int getPlayerIDFromBotID(int playerType){
		
		if (playerType == Bot.ARCHERCLASSBOT){
			 return EntityInfo.ARCHERCHALLENGER;
		}
		if (playerType == Bot.KNIGHTCLASSBOT){
			return EntityInfo.KNIGHTCHALLENGER;
		}
		if (playerType == Bot.CLERICCLASSBOT){
			return EntityInfo.CLERICCHALLENGER;
		}
		if (playerType == Bot.ILLUSIONISTCLASSBOT){
			return EntityInfo.ILLUSIONISTCHALLENGER;
		}
		if (playerType == Bot.VOIDLORDCLASSBOT){
			return EntityInfo.VOIDLORDCHALLENGER;
		}
		if (playerType == Bot.WARLOCKCLASSBOT){
			return EntityInfo.WARLOCKCHALLENGER;
		}
		if (playerType == Bot.WATERQUEENCLASSBOT){
			return EntityInfo.WATERQUEENCHALLENGER;
		}
		if (playerType == Bot.SHAMANCLASSBOT){
			return EntityInfo.SHAMANCHALLENGER;
		}
		
		return -1;
	}
	
	public String getTeamName(byte team){
		if (team == Res.TEAM_A){
			return "Attackers";
		}
		if (team == Res.TEAM_D){
			return "Defenders";
		}
		
		return "Unknown";
	}
	
	public String returnBotName(int botClass){
		
		if (botClass == Bot.ARCHERCLASSBOT){
			return "ARCHERBOT";
		}
		if (botClass == Bot.KNIGHTCLASSBOT){
			return "KNIGHTBOT";
		}
		if (botClass == Bot.WARLOCKCLASSBOT){
			return "WARLOCKBOT";
		}
		if (botClass == Bot.CLERICCLASSBOT){
			return "CLERICBOT";
		}
		if (botClass == Bot.ILLUSIONISTCLASSBOT){
			return "ILLUSIONISTBOT";
		}
		if (botClass == Bot.VOIDLORDCLASSBOT){
			return "VOIDLORDBOT";
		}
		if (botClass == Bot.WATERQUEENCLASSBOT){
			return "WATERQUEENBOT";
		}
		if (botClass == Bot.SHAMANCLASSBOT){
			return "SHAMANBOT";
		}
		return "BOT";
		
	}
	
	public float chooseRandomSpawn(byte team, boolean x){
		
		float randomX = 0;
		float randomY = 0;
		
		int randTeam0Index = Game.getGame().getRandom().nextInt(Game.getGame().getWorld().getMap().getTEAMD_SPAWN().size());
		int randTeam1Index = Game.getGame().getRandom().nextInt(Game.getGame().getWorld().getMap().getTEAMA_SPAWN().size());
		
		if (team == Res.TEAM_D){
			randomX = Game.getGame().getWorld().getMap().getTEAMD_SPAWN().get(randTeam0Index).getX();
			randomY = Game.getGame().getWorld().getMap().getTEAMD_SPAWN().get(randTeam0Index).getY();
		}
		else{
			randomX = Game.getGame().getWorld().getMap().getTEAMA_SPAWN().get(randTeam1Index).getX();
			randomY = Game.getGame().getWorld().getMap().getTEAMA_SPAWN().get(randTeam1Index).getY();
		}
		
		if (x){
			return randomX;
		}
		else{
			return randomY;
		}
	}
	
	public Entity getLastJoinedServerPlayer(byte team){

		ArrayList<Timestamp> timestamps = new ArrayList<Timestamp>();
		
		for (Entity p : Game.getGame().getPlayers()){
			if (p.getTeam() == team){
				timestamps.add(p.getJoinedTimeStamp());
			}
		}
		
		Collections.sort(timestamps);
		
		Timestamp newestStamp = timestamps.get(timestamps.size() - 1);

		for (Entity p : Game.getGame().getPlayers()){
			if (p.getJoinedTimeStamp() == newestStamp){
				return p;
			}
		}
		
		return null;
	}
	
	public Entity getLatestJoined(ArrayList<Entity> players){

		ArrayList<Timestamp> timestamps = new ArrayList<Timestamp>();
		
		for (Entity p : players){
			timestamps.add(p.getJoinedTimeStamp());
		}
		
		Collections.sort(timestamps);
		
		Timestamp latestJoined = timestamps.get(timestamps.size() - 1);

		for (Entity p : players){
			if (p.getJoinedTimeStamp() == latestJoined){
				return p;
			}
		}
		
		return null;
	}

	public static Set<Integer> findDuplicateInteger(ArrayList<Integer> listOfDuplicates){
		Set<Integer> setToReturn = new HashSet<Integer>();
		Set<Integer> sortingSet = new HashSet<Integer>();
		
		for (Integer i : listOfDuplicates){
			if (!sortingSet.add(i)){
				setToReturn.add(i);
			}
		}
		
		return setToReturn;
	}
	
	public static Set<Entity> findDuplicateEntity(ArrayList<Entity> listOfDuplicates){
		Set<Entity> setToReturn = new HashSet<Entity>();
		Set<Entity> sortingSet = new HashSet<Entity>();
		
		for (Entity i : listOfDuplicates){
			if (!sortingSet.add(i)){
				setToReturn.add(i);
			}
		}
		
		return setToReturn;
	}

	static void shuffleArray(int[] ar){	
		Random rnd = ThreadLocalRandom.current();
		for (int i = ar.length - 1; i > 0; i--)
		{
			int index = rnd.nextInt(i + 1);
			int a = ar[index];
			ar[index] = ar[i];
			ar[i] = a;
		}
	}
	
	public static byte boolToByte(boolean value){
		if (!value){
			return (byte) 0;
		}else{
			return (byte) 1;
		}
	}
	
	public static boolean byteToBool(byte value){
		if (value == 0){
			return false;
		}
		else{
			return true;
		}
	}
	
}
