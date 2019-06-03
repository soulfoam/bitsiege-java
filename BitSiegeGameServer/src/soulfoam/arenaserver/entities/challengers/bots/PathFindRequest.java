package soulfoam.arenaserver.entities.challengers.bots;

import java.util.ArrayList;
import java.util.Collections;

import soulfoam.arenaserver.entities.Entity;
import soulfoam.arenaserver.main.game.Game;
import soulfoam.arenaserver.world.Tile;

public class PathFindRequest {
	
	public Game gh;
	public Entity theBot;
	public Tile startTile;
	public Tile endTile;
	public ArrayList<Tile> finalPath = new ArrayList<Tile>();
	public boolean solvedPath;
	public boolean needsRemoved;
	
	public PathFindRequest(Tile start, Tile end, Entity bot){
		this.startTile = start;
		this.endTile = end;
		this.theBot = bot;
		
	}

	public ArrayList<Tile> getPath(Tile start, Tile end){
		
		ArrayList<Tile> empty = new ArrayList<Tile>();
		
		ArrayList<Tile> toCheck = new ArrayList<Tile>();
		ArrayList<Tile> checked = new ArrayList<Tile>();
		
		toCheck.add(start);
		checked.add(start);

		start.setDistance(0);

		while (!toCheck.isEmpty()){
			Tile origin = toCheck.remove(0);
			for (Tile t: origin.getNeighborsPF()){
				
				if (checked.contains(t) || t.isBlocked() || t.getTileBelow().isBlocked()) continue;
				t.setParentTile(origin);
				checked.add(t);
				t.setDistance(origin.getDistance()+1);
				t.setScore(t.getDistance(end) + t.getDistance());

				for(int i = 0; i < toCheck.size(); i++){
					Tile checker = toCheck.get(i);
					if(checker.getScore() > t.getScore()){
						break;
					}
					toCheck.add(i, t);
				}
			
			
				if (t.getX() == end.getX() && t.getY() == end.getY()){

					ArrayList<Tile> result = new ArrayList<Tile>();
					Tile current = t;
					result.add(current);
					for (int z = 0; z < checked.size(); z++){
						if (current.getParentTile() != null){
							current = current.getParentTile();
							result.add(current);
						}
					}
					for (int c = 0; c < checked.size(); c++){
						checked.get(c).setParentTile(null);
					}

					return result;
				}
			}
		}
	
		return empty;
	}
	
	public void solvePath(){
		finalPath = getPath(startTile, endTile);

		if (!finalPath.isEmpty()){
			Collections.reverse(finalPath);
			finalPath.remove(0);
			solvedPath = true;
		}

	}
}
