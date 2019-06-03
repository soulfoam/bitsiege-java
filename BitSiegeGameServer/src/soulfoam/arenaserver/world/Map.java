package soulfoam.arenaserver.world;

import java.util.ArrayList;

import soulfoam.arenaserver.main.misc.DataObject;

public class Map {

	private int mapWidth = 200, mapHeight = 200;

	private ArrayList<Tile> TILE_DEFENDER_PATROL = new ArrayList<Tile>();
	private ArrayList<Tile> TILE_ATTACKER_PATROL = new ArrayList<Tile>();
	
	private ArrayList<Tile> TEAMD_SPAWN = new ArrayList<Tile>();
	private ArrayList<Tile> TEAMA_SPAWN = new ArrayList<Tile>();
	
	private ArrayList<DataObject> OBJECTIVES_TO_SPAWN = new ArrayList<DataObject>();
	
	private ArrayList<Tile[][]> layers = new ArrayList<Tile[][]>();
	
	private String mapName;
	private World world;

	
	public int mapID;
	
	public Map(String mapName){
		this.mapName = mapName;
	}

	public int getMapWidth() {
		return mapWidth;
	}

	public void setMapWidth(int mapWidth) {
		this.mapWidth = mapWidth;
	}

	public int getMapHeight() {
		return mapHeight;
	}

	public void setMapHeight(int mapHeight) {
		this.mapHeight = mapHeight;
	}

	public ArrayList<Tile> getTILE_DEFENDER_PATROL() {
		return TILE_DEFENDER_PATROL;
	}

	public void setTILE_DEFENDER_PATROL(ArrayList<Tile> tILE_DEFENDER_PATROL) {
		TILE_DEFENDER_PATROL = tILE_DEFENDER_PATROL;
	}

	public ArrayList<Tile> getTILE_ATTACKER_PATROL() {
		return TILE_ATTACKER_PATROL;
	}

	public void setTILE_ATTACKER_PATROL(ArrayList<Tile> tILE_ATTACKER_PATROL) {
		TILE_ATTACKER_PATROL = tILE_ATTACKER_PATROL;
	}

	public ArrayList<Tile> getTEAMD_SPAWN() {
		return TEAMD_SPAWN;
	}

	public void setTEAMD_SPAWN(ArrayList<Tile> tEAMD_SPAWN) {
		TEAMD_SPAWN = tEAMD_SPAWN;
	}

	public ArrayList<Tile> getTEAMA_SPAWN() {
		return TEAMA_SPAWN;
	}

	public void setTEAMA_SPAWN(ArrayList<Tile> tEAMA_SPAWN) {
		TEAMA_SPAWN = tEAMA_SPAWN;
	}

	public ArrayList<DataObject> getOBJECTIVES_TO_SPAWN() {
		return OBJECTIVES_TO_SPAWN;
	}

	public void setOBJECTIVES_TO_SPAWN(ArrayList<DataObject> oBJECTIVES_TO_SPAWN) {
		OBJECTIVES_TO_SPAWN = oBJECTIVES_TO_SPAWN;
	}

	public ArrayList<Tile[][]> getLayers() {
		return layers;
	}

	public void setLayers(ArrayList<Tile[][]> layers) {
		this.layers = layers;
	}

	public String getMapName() {
		return mapName;
	}

	public void setMapName(String mapName) {
		this.mapName = mapName;
	}

	public World getWorld() {
		return world;
	}

	public void setWorld(World world) {
		this.world = world;
	}

	public int getMapID() {
		return mapID;
	}

	public void setMapID(int mapID) {
		this.mapID = mapID;
	}
	
}
