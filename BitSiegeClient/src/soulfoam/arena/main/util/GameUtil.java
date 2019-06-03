package soulfoam.arena.main.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.concurrent.ThreadLocalRandom;

import soulfoam.arena.entities.Entity;
import soulfoam.arena.entities.objectives.CapturePoint;
import soulfoam.arena.entities.objectives.Objective;
import soulfoam.arena.entities.objectives.SmallCapturePoint;
import soulfoam.arena.main.game.Game;
import soulfoam.arena.main.resources.Res;
import soulfoam.arenashared.main.objectiveinfo.ObjectiveInfo;

public class GameUtil {

	public int getTeamSize(byte team, boolean countBots) {
		int count = 0;
		Entity[] tempList = new Entity[Game.getGame().getPlayers().size()];
		Game.getGame().getPlayers().toArray(tempList);

		for (Entity p : tempList) {
			if (p != null && p.getTeam() == team) {
				if (!p.isBot()) {
					count++;
				} else {
					if (countBots) {
						count++;
					}
				}
			}
		}

		return count;
	}

	public ArrayList<Entity> getTeam(byte team) {
		ArrayList<Entity> returnList = new ArrayList<Entity>();
		for (Entity e : Game.getGame().getPlayers()) {
			if (e.getTeam() == team) {
				returnList.add(e);
			}
		}

		return returnList;
	}

	public String getTeamName(byte team) {
		if (team == Res.TEAM_A) {
			return "Attackers";
		}
		if (team == Res.TEAM_D) {
			return "Defenders";
		}

		return "Unknown";
	}

	public float chooseRandomSpawn(byte team, boolean x) {

		float randomX = 0;
		float randomY = 0;

		Random random = new Random();

		int randTeam0Index = random.nextInt(Game.getGame().getWorld().getMap().getTEAMD_SPAWN().size());
		int randTeam1Index = random.nextInt(Game.getGame().getWorld().getMap().getTEAMA_SPAWN().size());

		if (team == Res.TEAM_D) {
			randomX = Game.getGame().getWorld().getMap().getTEAMD_SPAWN().get(randTeam0Index).getX();
			randomY = Game.getGame().getWorld().getMap().getTEAMD_SPAWN().get(randTeam0Index).getY();
		} else {
			randomX = Game.getGame().getWorld().getMap().getTEAMA_SPAWN().get(randTeam1Index).getX();
			randomY = Game.getGame().getWorld().getMap().getTEAMA_SPAWN().get(randTeam1Index).getY();
		}

		if (x) {
			return randomX;
		} else {
			return randomY;
		}
	}

	public ArrayList<CapturePoint> getCapturePoints() {
		ArrayList<CapturePoint> theList = new ArrayList<CapturePoint>();
		Objective[] tempList = new Objective[Game.getGame().getObjectives().size()];
		Game.getGame().getObjectives().toArray(tempList);

		for (Objective o : tempList) {
			if (o.getObjectiveTeam() == ObjectiveInfo.CAPTURE_POINT) {
				CapturePoint cpo = (CapturePoint) o;
				theList.add(cpo);
			}
		}
		return theList;
	}
	
	public ArrayList<SmallCapturePoint> getSmallCapturePoints() {
		ArrayList<SmallCapturePoint> theList = new ArrayList<SmallCapturePoint>();
		Objective[] tempList = new Objective[Game.getGame().getObjectives().size()];
		Game.getGame().getObjectives().toArray(tempList);

		for (Objective o : tempList) {
			if (o.getObjectiveTeam() == ObjectiveInfo.SMALL_CAPTURE_POINT) {
				SmallCapturePoint cpo = (SmallCapturePoint) o;
				theList.add(cpo);
			}
			
		}
		return theList;
	}

	public ArrayList<Entity> enemiesSeenByTeam() {

		Entity[] tempList = new Entity[Game.getGame().getPlayers().size()];
		Game.getGame().getPlayers().toArray(tempList);

		ArrayList<Entity> returnList = new ArrayList<Entity>();
		ArrayList<Entity> enemyList = new ArrayList<Entity>();

		for (Entity p : tempList) {
			if (Game.getGame().getPlayer() != null && p.getTeam() != Game.getGame().getPlayer().getTeam()) {
				enemyList.add(p);
			}
		}

		for (Entity p : tempList) {
			for (Entity e : enemyList) {
				if (Game.getGame().getPlayer() != null
						&& p.getTeam() == Game.getGame().getPlayer().getTeam()) {
					if (p.getView().intersects(e.getBounds())) {
						if (!p.isDead() && !e.isDead() && !e.isInvisible()) {
							returnList.add(e);
						}
					}
				}
			}
		}

		return returnList;
	}

	public static Set<Integer> findDuplicateInteger(ArrayList<Integer> listOfDuplicates) {
		Set<Integer> setToReturn = new HashSet<Integer>();
		Set<Integer> sortingSet = new HashSet<Integer>();

		for (Integer i : listOfDuplicates) {
			if (!sortingSet.add(i)) {
				setToReturn.add(i);
			}
		}

		return setToReturn;
	}

	public static Set<Entity> findDuplicateEntity(ArrayList<Entity> listOfDuplicates) {
		Set<Entity> setToReturn = new HashSet<Entity>();
		Set<Entity> sortingSet = new HashSet<Entity>();

		for (Entity i : listOfDuplicates) {
			if (!sortingSet.add(i)) {
				setToReturn.add(i);
			}
		}

		return setToReturn;
	}

	static void shuffleArray(int[] ar) {
		Random rnd = ThreadLocalRandom.current();
		for (int i = ar.length - 1; i > 0; i--) {
			int index = rnd.nextInt(i + 1);
			int a = ar[index];
			ar[index] = ar[i];
			ar[i] = a;
		}
	}

	public static byte boolToByte(boolean value) {
		if (!value) {
			return (byte) 0;
		} else {
			return (byte) 1;
		}
	}

	public static boolean byteToBool(byte value) {
		if (value == 0) {
			return false;
		} else {
			return true;
		}
	}

	public static ArrayList<String> getStringParts(String string, int splitSize) {
		ArrayList<String> parts = new ArrayList<String>();
		int len = string.length();
		for (int i = 0; i < len; i += splitSize) {
			parts.add(string.substring(i, Math.min(len, i + splitSize)));
		}
		return parts;
	}
	
	public static String getNewLineParts(String string, int maxLength) {
		StringTokenizer tok = new StringTokenizer(string, " ");
		StringBuilder output = new StringBuilder(string.length());
		int lineLength = 0;
		while (tok.hasMoreTokens()){
			String word = tok.nextToken();
			if (lineLength + word.length() > maxLength){
				output.append("\n");
				lineLength = 0;
			}
			output.append(word);
			lineLength += word.length();
		}
		return output.toString();
	}
}
