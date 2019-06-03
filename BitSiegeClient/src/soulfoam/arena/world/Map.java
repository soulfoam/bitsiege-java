package soulfoam.arena.world;

import java.util.ArrayList;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import soulfoam.arena.main.misc.DataObject;
import soulfoam.arena.main.resources.Res;
import soulfoam.arenashared.main.objectiveinfo.ObjectiveInfo;

public class Map {

	private int mapWidth = 200, mapHeight = 200;

	private ArrayList<Tile> TILE_DEFENDER_PATROL = new ArrayList<Tile>();
	private ArrayList<Tile> TILE_ATTACKER_PATROL = new ArrayList<Tile>();

	private ArrayList<Tile> TEAMD_SPAWN = new ArrayList<Tile>();
	private ArrayList<Tile> TEAMA_SPAWN = new ArrayList<Tile>();

	private ArrayList<DataObject> OBJECTIVES_TO_SPAWN = new ArrayList<DataObject>();
	private ArrayList<MapObject> mapObjects = new ArrayList<MapObject>();

	private ArrayList<Tile[][]> layers = new ArrayList<Tile[][]>();

	private String mapName;

	private Image miniMap;
	private Image mapPreview;

	Graphics miniMapRenderer;
	Graphics mapPreviewRenderer;

	private int mapID;

	public Map(String mapName) {
		this.mapName = mapName;
	}

	public void createMiniMap() {
		try {
			miniMap = new Image(getMapWidth() * Tile.TILE_SIZE, getMapHeight() * Tile.TILE_SIZE);
			miniMapRenderer = getMiniMap().getGraphics();
		} catch (SlickException e) {
			e.printStackTrace();
		}

		if (miniMapRenderer != null) {
			for (Tile[][] t : getLayers()) {

				for (int x = 0; x < t.length; x++) {
					for (int y = 0; y < t[0].length; y++) {
						t[x][y].render(miniMapRenderer);
					}
				}

			}
		}
	}

	public void createMapPreview() {
		try {
			mapPreview = new Image(mapWidth * Tile.TILE_SIZE, mapHeight * Tile.TILE_SIZE);
			mapPreviewRenderer = mapPreview.getGraphics();
		} catch (SlickException e) {
			e.printStackTrace();
		}

		if (mapPreviewRenderer != null) {
			for (Tile[][] t : getLayers()) {

				for (int x = 0; x < t.length; x++) {
					for (int y = 0; y < t[0].length; y++) {
						t[x][y].render(mapPreviewRenderer);
					}
				}

			}

			for (MapObject o : getMapObjects()) {
				o.render(mapPreviewRenderer);
			}

			for (DataObject data : getOBJECTIVES_TO_SPAWN()) {
				if (data.team == ObjectiveInfo.HEALTHPICKUP_OBJECTIVE) {
					Res.OBJECTIVE_RESOURCE.HEALTH_ORB[0].draw(data.x - 8, data.y - 8);
				}
				if (data.team == ObjectiveInfo.CAPTURE_POINT) {
					Res.OBJECTIVE_RESOURCE.CAPTURE_POINT[0].draw(data.x - 88, data.y - 48);
				}
				if (data.team == ObjectiveInfo.SMALL_CAPTURE_POINT) {
					Res.OBJECTIVE_RESOURCE.SMALL_CAPTURE_POINT[0].draw(data.x, data.y);
				}
			}
		}
	}

	public int getMapHeight() {
		return mapHeight;
	}

	public void setMapHeight(int mapH) {
		this.mapHeight = mapH;
	}

	public int getMapWidth() {
		return mapWidth;
	}

	public void setMapWidth(int mapW) {
		this.mapWidth = mapW;
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

	public ArrayList<Tile[][]> getLayers() {
		return layers;
	}

	public void setLayers(ArrayList<Tile[][]> layers) {
		this.layers = layers;
	}

	public ArrayList<MapObject> getMapObjects() {
		return mapObjects;
	}

	public void setMapObjects(ArrayList<MapObject> mapObjects) {
		this.mapObjects = mapObjects;
	}

	public ArrayList<DataObject> getOBJECTIVES_TO_SPAWN() {
		return OBJECTIVES_TO_SPAWN;
	}

	public void setOBJECTIVES_TO_SPAWN(ArrayList<DataObject> oBJECTIVES_TO_SPAWN) {
		OBJECTIVES_TO_SPAWN = oBJECTIVES_TO_SPAWN;
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

	public Image getMapPreview() {
		return mapPreview;
	}

	public void setMapPreview(Image mapPreview) {
		this.mapPreview = mapPreview;
	}

	public String getMapName() {
		return mapName;
	}

	public void setMapName(String mapName) {
		this.mapName = mapName;
	}

	public Image getMiniMap() {
		return miniMap;
	}

	public void setMiniMap(Image miniMap) {
		this.miniMap = miniMap;
	}

	public int getMapID() {
		return mapID;
	}

	public void setMapID(int mapID) {
		this.mapID = mapID;
	}
}
