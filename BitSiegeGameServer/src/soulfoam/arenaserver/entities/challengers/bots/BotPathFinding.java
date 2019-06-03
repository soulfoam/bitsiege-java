package soulfoam.arenaserver.entities.challengers.bots;

import java.util.ArrayList;
import java.util.Random;

import soulfoam.arenaserver.entities.Entity;
import soulfoam.arenaserver.main.game.Game;
import soulfoam.arenaserver.world.Tile;

public class BotPathFinding extends Thread {
	
	
	public static final byte PATH_NONE = -1;
	
	public static final byte PATH_TARGETPLAYER = 0;
	public static final byte PATH_DEFENDERPATROL = 1;
	public static final byte PATH_ATTACKERPATROL = 2;
	public static final byte PATH_TARGETOBJECTIVE = 3;
	
	private Random rand = new Random();
	
	private Tile startTile;
	private Tile endTile;
	
	private boolean isRunning;
	private ArrayList<PathFindRequest> pathRequests = new ArrayList<PathFindRequest>();
	
	public BotPathFinding() {
		
		this.setRunning(true);
	}
	
	public void run(){
		while (isRunning()){
			PathFindRequest[] tempPathList = new PathFindRequest[pathRequests.size()];
			pathRequests.toArray(tempPathList);
			
			for (PathFindRequest pfr : tempPathList){
				if (pfr != null){
					if (!pfr.solvedPath){
						pfr.solvePath();
					}
					if (pfr.needsRemoved){
						pathRequests.remove(pfr);
					}
				}
			}
			try {
				Thread.sleep(50);
			} catch (Exception e) 
			{
				System.out.println(e);
			}
		}
	}
	
	public void requestNewPath(Tile start, Tile end, Entity bot){

		PathFindRequest[] tempPathList = new PathFindRequest[pathRequests.size()];
		pathRequests.toArray(tempPathList);
		
		int currentRequestCount = 0;
		
		for (PathFindRequest pfr : tempPathList){
			if (pfr != null && pfr.theBot.getPlayerID() == bot.getPlayerID()){
				currentRequestCount++;
			}
		}

		if (currentRequestCount <= 3){
			if (start != end){
				if (end.getX() != 0 || end.getY() != 0){
					pathRequests.add(new PathFindRequest(start, end, bot));
				}
			}
		}
		else{
			for (PathFindRequest pfr : tempPathList){
				if (pfr != null && pfr.theBot != null && bot != null && pfr.theBot.getPlayerID() == bot.getPlayerID()){
					pathRequests.remove(pfr);
				}
			}
		}
	}

	public Random getRand() {
		return rand;
	}

	public Tile getStartTile() {
		return startTile;
	}

	public Tile getEndTile() {
		return endTile;
	}

	public boolean isRunning() {
		return isRunning;
	}

	public ArrayList<PathFindRequest> getPathRequests() {
		return pathRequests;
	}

	public void setRunning(boolean isRunning) {
		this.isRunning = isRunning;
	}
		


}
