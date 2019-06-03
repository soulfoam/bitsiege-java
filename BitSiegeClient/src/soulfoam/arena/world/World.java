package soulfoam.arena.world;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import soulfoam.arena.entities.Entity;
import soulfoam.arena.entities.GameObject;
import soulfoam.arena.entities.abilities.Ability;
import soulfoam.arena.entities.objectives.Objective;
import soulfoam.arena.main.game.Game;
import soulfoam.arena.main.misc.ChatMessage;
import soulfoam.arena.main.resources.Res;
import soulfoam.arenashared.main.gameinfo.GameInfo;
import soulfoam.arenashared.main.objectiveinfo.ObjectiveInfo;

public class World {

	public static int MAP_ID = 0;

	private Map map;

	public void setTile(float x, float y, Tile t, int layer) {

		getMap().getLayers().get(layer)[(int) x / Tile.TILE_SIZE][(int) y / Tile.TILE_SIZE] = t;
	}

	public void setTile(Tile ot, Tile nt, int layer) {

		getMap().getLayers().get(layer)[(int) ot.getX() / Tile.TILE_SIZE][(int) ot.getY() / Tile.TILE_SIZE] = nt;

	}

	public Tile getTile(float x, float y, int layer) {

		if (x >= getMap().getMapWidth() * Tile.TILE_SIZE - Tile.TILE_SIZE) {
			x = getMap().getMapWidth() * Tile.TILE_SIZE - Tile.TILE_SIZE;
		}
		if (x <= 0) {
			x = 0;
		}

		if (y >= getMap().getMapHeight() * Tile.TILE_SIZE - Tile.TILE_SIZE) {
			y = getMap().getMapHeight() * Tile.TILE_SIZE - Tile.TILE_SIZE;
		}
		if (y <= 0) {
			y = 0;
		}

		return getMap().getLayers().get(layer)[(int) x / Tile.TILE_SIZE][(int) y / Tile.TILE_SIZE];

	}

	public Tile getTile(float x, float y) {

		if (x >= getMap().getMapWidth() * Tile.TILE_SIZE - Tile.TILE_SIZE) {
			x = getMap().getMapWidth() * Tile.TILE_SIZE - Tile.TILE_SIZE;
		}
		if (x <= 0) {
			x = 0;
		}

		if (y >= getMap().getMapHeight() * Tile.TILE_SIZE - Tile.TILE_SIZE) {
			y = getMap().getMapHeight() * Tile.TILE_SIZE - Tile.TILE_SIZE;
		}
		if (y <= 0) {
			y = 0;
		}

		return getMap().getLayers().get(MapLayer.INFORMATION)[(int) x / Tile.TILE_SIZE][(int) y / Tile.TILE_SIZE];

	}

	public ArrayList<GameObject> gatherFrontLayer() {
		ArrayList<GameObject> renderObjects = new ArrayList<GameObject>();

		renderObjects.addAll(Game.getGame().getParticleSystem().particles);

		Entity[] tempListPlayers = new Entity[Game.getGame().getPlayers().size()];
		Game.getGame().getPlayers().toArray(tempListPlayers);
		for (Entity p : tempListPlayers) {
			if (p != null) {
				if (p.getX() + Tile.TILE_SIZE * 20 >= Game.getGame().getCam().getX()
						&& p.getX() <= Game.getGame().getCam().getX() + GameInfo.RES_WIDTH + Tile.TILE_SIZE * 10
						&& p.getY() + Tile.TILE_SIZE * 20 >= Game.getGame().getCam().getY()
						&& p.getY() <= Game.getGame().getCam().getY() + GameInfo.RES_HEIGHT
								+ Tile.TILE_SIZE * 10) {
					renderObjects.add(p);
				}
			}
		}

		Objective[] tempList = new Objective[Game.getGame().getObjectives().size()];
		Game.getGame().getObjectives().toArray(tempList);
		for (Objective o : tempList) {
			if (o != null) {
				if (o.getObjectiveTeam() != ObjectiveInfo.SMALL_CAPTURE_POINT
						&& o.getObjectiveTeam() != ObjectiveInfo.CAPTURE_POINT) {
					if (o.getX() + Tile.TILE_SIZE * 20 >= Game.getGame().getCam().getX()
							&& o.getX() <= Game.getGame().getCam().getX() + GameInfo.RES_WIDTH
									+ Tile.TILE_SIZE * 10
							&& o.getY() + Tile.TILE_SIZE * 20 >= Game.getGame().getCam().getY()
							&& o.getY() <= Game.getGame().getCam().getY() + GameInfo.RES_HEIGHT
									+ Tile.TILE_SIZE * 10) {
						renderObjects.add(o);

					}
				}
			}
		}

		for (MapObject o : getMap().getMapObjects()) {
			if (o != null && o.mapLayer != MapLayer.ABOVEALL && o.mapLayer != MapLayer.BACK) {
				if (o.getX() + Tile.TILE_SIZE * 20 >= Game.getGame().getCam().getX()
						&& o.getX() <= Game.getGame().getCam().getX() + GameInfo.RES_WIDTH + Tile.TILE_SIZE * 10
						&& o.getY() + Tile.TILE_SIZE * 20 >= Game.getGame().getCam().getY()
						&& o.getY() <= Game.getGame().getCam().getY() + GameInfo.RES_HEIGHT
								+ Tile.TILE_SIZE * 10) {
					renderObjects.add(o);
				}
			}
		}

		Ability[] tempListAbilities = new Ability[Game.getGame().getAbilities().size()];
		Game.getGame().getAbilities().toArray(tempListAbilities);
		for (Ability a : tempListAbilities) {
			if (a != null && a.getRenderLayer() == Ability.RENDER_FRONT) {
				if (a.getX() + Tile.TILE_SIZE * 20 >= Game.getGame().getCam().getX()
						&& a.getX() <= Game.getGame().getCam().getX() + GameInfo.RES_WIDTH + Tile.TILE_SIZE * 10
						&& a.getY() + Tile.TILE_SIZE * 20 >= Game.getGame().getCam().getY()
						&& a.getY() <= Game.getGame().getCam().getY() + GameInfo.RES_HEIGHT
								+ Tile.TILE_SIZE * 10) {
					renderObjects.add(a);
				}
			}
		}

		// y order rendering for tiles
		for (int y = 0; y < getMap().getLayers().get(MapLayer.YSORTED)[0].length; y++) {
			for (int x = 0; x < getMap().getLayers().get(MapLayer.YSORTED).length; x++) {
				if (getMap().getLayers().get(MapLayer.YSORTED)[x][y].getX() + Tile.TILE_SIZE >= Game.getGame().getCam()
						.getX()
						&& getMap().getLayers().get(MapLayer.YSORTED)[x][y].getX() <= Game.getGame().getCam().getX()
								+ GameInfo.RES_WIDTH
						&& getMap().getLayers().get(MapLayer.YSORTED)[x][y].getY() + Tile.TILE_SIZE >= Game.getGame()
								.getCam().getY()
						&& getMap().getLayers().get(MapLayer.YSORTED)[x][y].getY() <= Game.getGame().getCam().getY()
								+ GameInfo.RES_HEIGHT) {
					if (getMap().getLayers().get(MapLayer.YSORTED)[x][y].getTileID() != -1) {
						renderObjects.add(getMap().getLayers().get(MapLayer.YSORTED)[x][y]);
					}
				}
			}
		}

		if (!renderObjects.isEmpty()) {
			Collections.sort(renderObjects, new Comparator<GameObject>() {

				public int compare(GameObject o1, GameObject o2) {
					return Float.compare(o1.sortByY(), o2.sortByY());
				}
			});
		}

		return renderObjects;
	}

	public ArrayList<GameObject> gatherBackLayer() {
		ArrayList<GameObject> renderObjects = new ArrayList<GameObject>();

		for (MapObject o : getMap().getMapObjects()) {
			if (o != null && o.mapLayer == MapLayer.BACK) {
				if (o.getX() + Tile.TILE_SIZE * 20 >= Game.getGame().getCam().getX()
						&& o.getX() <= Game.getGame().getCam().getX() + GameInfo.RES_WIDTH + Tile.TILE_SIZE * 10
						&& o.getY() + Tile.TILE_SIZE * 20 >= Game.getGame().getCam().getY()
						&& o.getY() <= Game.getGame().getCam().getY() + GameInfo.RES_HEIGHT
								+ Tile.TILE_SIZE * 10) {
					renderObjects.add(o);
				}
			}
		}

		Ability[] tempListAbilities = new Ability[Game.getGame().getAbilities().size()];
		Game.getGame().getAbilities().toArray(tempListAbilities);
		for (Ability a : tempListAbilities) {
			if (a != null && a.getRenderLayer() == Ability.RENDER_BACK) {
				if (a.getX() + Tile.TILE_SIZE * 20 >= Game.getGame().getCam().getX()
						&& a.getX() <= Game.getGame().getCam().getX() + GameInfo.RES_WIDTH + Tile.TILE_SIZE * 10
						&& a.getY() + Tile.TILE_SIZE * 20 >= Game.getGame().getCam().getY()
						&& a.getY() <= Game.getGame().getCam().getY() + GameInfo.RES_HEIGHT
								+ Tile.TILE_SIZE * 10) {
					renderObjects.add(a);
				}
			}
		}

		if (!renderObjects.isEmpty()) {
			Collections.sort(renderObjects, new Comparator<GameObject>() {

				public int compare(GameObject o1, GameObject o2) {
					return Float.compare(o1.sortByY(), o2.sortByY());
				}
			});
		}

		return renderObjects;
	}

	public ArrayList<GameObject> gatherGroundLayer() {
		ArrayList<GameObject> renderObjects = new ArrayList<GameObject>();

		for (Ability a : Game.getGame().getAbilities()) {
			if (a.getRenderLayer() == Ability.RENDER_GROUND) {
				if (a.getX() + Tile.TILE_SIZE * 20 >= Game.getGame().getCam().getX()
						&& a.getX() <= Game.getGame().getCam().getX() + GameInfo.RES_WIDTH + Tile.TILE_SIZE * 10
						&& a.getY() + Tile.TILE_SIZE * 20 >= Game.getGame().getCam().getY()
						&& a.getY() <= Game.getGame().getCam().getY() + GameInfo.RES_HEIGHT
								+ Tile.TILE_SIZE * 10) {
					renderObjects.add(a);
				}
			}
		}

		if (!renderObjects.isEmpty()) {
			Collections.sort(renderObjects, new Comparator<GameObject>() {

				public int compare(GameObject o1, GameObject o2) {
					return Float.compare(o1.sortByY(), o2.sortByY());
				}
			});
		}
		return renderObjects;
	}

	public ArrayList<GameObject> gatherAboveAllLayer() {
		ArrayList<GameObject> renderObjects = new ArrayList<GameObject>();

		for (MapObject o : getMap().getMapObjects()) {
			if (o.mapLayer == MapLayer.ABOVEALL) {
				if (o.getX() + Tile.TILE_SIZE * 20 >= Game.getGame().getCam().getX()
						&& o.getX() <= Game.getGame().getCam().getX() + GameInfo.RES_WIDTH + Tile.TILE_SIZE * 10
						&& o.getY() + Tile.TILE_SIZE * 20 >= Game.getGame().getCam().getY()
						&& o.getY() <= Game.getGame().getCam().getY() + GameInfo.RES_HEIGHT
								+ Tile.TILE_SIZE * 10) {
					renderObjects.add(o);
				}
			}
		}

		for (int y = 0; y < getMap().getLayers().get(MapLayer.ABOVEALL)[0].length; y++) {
			for (int x = 0; x < getMap().getLayers().get(MapLayer.ABOVEALL).length; x++) {
				if (getMap().getLayers().get(MapLayer.ABOVEALL)[x][y].getX() + Tile.TILE_SIZE >= Game.getGame().getCam()
						.getX()
						&& getMap().getLayers().get(MapLayer.ABOVEALL)[x][y].getX() <= Game.getGame().getCam().getX()
								+ GameInfo.RES_WIDTH
						&& getMap().getLayers().get(MapLayer.ABOVEALL)[x][y].getY() + Tile.TILE_SIZE >= Game.getGame()
								.getCam().getY()
						&& getMap().getLayers().get(MapLayer.ABOVEALL)[x][y].getY() <= Game.getGame().getCam().getY()
								+ GameInfo.RES_HEIGHT) {
					if (getMap().getLayers().get(MapLayer.ABOVEALL)[x][y].getTileID() != -1) {
						renderObjects.add(getMap().getLayers().get(MapLayer.ABOVEALL)[x][y]);
					}
				}
			}
		}

		if (!renderObjects.isEmpty()) {
			Collections.sort(renderObjects, new Comparator<GameObject>() {

				public int compare(GameObject o1, GameObject o2) {
					return Float.compare(o1.sortByY(), o2.sortByY());
				}
			});
		}

		return renderObjects;
	}

	public ArrayList<GameObject> gatherSkyLayer() {
		ArrayList<GameObject> renderObjects = new ArrayList<GameObject>();

		for (Ability a : Game.getGame().getAbilities()) {
			if (a.getRenderLayer() == Ability.RENDER_SKY) {
				if (a.getX() + Tile.TILE_SIZE * 20 >= Game.getGame().getCam().getX()
						&& a.getX() <= Game.getGame().getCam().getX() + GameInfo.RES_WIDTH + Tile.TILE_SIZE * 10
						&& a.getY() + Tile.TILE_SIZE * 20 >= Game.getGame().getCam().getY()
						&& a.getY() <= Game.getGame().getCam().getY() + GameInfo.RES_HEIGHT
								+ Tile.TILE_SIZE * 10) {
					renderObjects.add(a);
				}
			}
		}

		if (!renderObjects.isEmpty()) {
			Collections.sort(renderObjects, new Comparator<GameObject>() {

				public int compare(GameObject o1, GameObject o2) {
					return Float.compare(o1.sortByY(), o2.sortByY());
				}
			});
		}
		return renderObjects;
	}

	public ArrayList<GameObject> gatherPlayerMiscLayer() {

		ArrayList<GameObject> renderObjects = new ArrayList<GameObject>();

		for (Entity p : Game.getGame().getPlayers()) {

			if (p.getX() + Tile.TILE_SIZE * 20 >= Game.getGame().getCam().getX()
					&& p.getX() <= Game.getGame().getCam().getX() + GameInfo.RES_WIDTH + Tile.TILE_SIZE * 10
					&& p.getY() + Tile.TILE_SIZE * 20 >= Game.getGame().getCam().getY()
					&& p.getY() <= Game.getGame().getCam().getY() + GameInfo.RES_HEIGHT
							+ Tile.TILE_SIZE * 10) {
				renderObjects.add(p);
			}
			
		}

		if (!renderObjects.isEmpty()) {
			Collections.sort(renderObjects, new Comparator<GameObject>() {

				public int compare(GameObject o1, GameObject o2) {
					return Float.compare(o1.sortByY(), o2.sortByY());
				}
			});
		}
		return renderObjects;
	}

	public void update(int delta) {
		

		// for (int y = 0; y < map.layers.get(1)[0].length; y++) {
		// for (int x = 0; x < map.layers.get(1).length; x++) {
		// if (map.layers.get(1)[x][y] != null)
		// map.layers.get(1)[x][y].update(delta);
		// }
		// }

	}

	public void render(Graphics g) {

		ArrayList<GameObject> groundLayerObjects = gatherGroundLayer();
		ArrayList<GameObject> backLayerObjects = gatherBackLayer();
		ArrayList<GameObject> frontLayerObjects = gatherFrontLayer();
		ArrayList<GameObject> aboveAllLayerObjects = gatherAboveAllLayer();
		ArrayList<GameObject> skyLayerObjects = gatherSkyLayer();
		ArrayList<GameObject> playerMiscLayerObjects = gatherPlayerMiscLayer();

		for (int y = 0; y < getMap().getLayers().get(MapLayer.LIQUID)[0].length; y++) {
			for (int x = 0; x < getMap().getLayers().get(MapLayer.LIQUID).length; x++) {
				if (getMap().getLayers().get(MapLayer.LIQUID)[x][y] != null) {
					if (getMap().getLayers().get(MapLayer.LIQUID)[x][y].getX() + Tile.TILE_SIZE >= Game.getGame().getCam()
							.getX()
							&& getMap().getLayers().get(MapLayer.LIQUID)[x][y].getX() <= Game.getGame().getCam().getX()
									+ GameInfo.RES_WIDTH
							&& getMap().getLayers().get(MapLayer.LIQUID)[x][y].getY() + Tile.TILE_SIZE >= Game.getGame()
									.getCam().getY()
							&& getMap().getLayers().get(MapLayer.LIQUID)[x][y].getY() <= Game.getGame().getCam().getY()
									+ GameInfo.RES_HEIGHT) {

						getMap().getLayers().get(MapLayer.LIQUID)[x][y].render(g);

					}
				}
			}
		}

		for (int y = 0; y < getMap().getLayers().get(MapLayer.GROUND)[0].length; y++) {
			for (int x = 0; x < getMap().getLayers().get(MapLayer.GROUND).length; x++) {
				if (getMap().getLayers().get(MapLayer.GROUND)[x][y] != null) {
					if (getMap().getLayers().get(MapLayer.GROUND)[x][y].getX() + Tile.TILE_SIZE >= Game.getGame().getCam()
							.getX()
							&& getMap().getLayers().get(MapLayer.GROUND)[x][y].getX() <= Game.getGame().getCam().getX()
									+ GameInfo.RES_WIDTH
							&& getMap().getLayers().get(MapLayer.GROUND)[x][y].getY() + Tile.TILE_SIZE >= Game.getGame()
									.getCam().getY()
							&& getMap().getLayers().get(MapLayer.GROUND)[x][y].getY() <= Game.getGame().getCam().getY()
									+ GameInfo.RES_HEIGHT) {

						getMap().getLayers().get(MapLayer.GROUND)[x][y].render(g);

					}
				}
			}
		}

		// Res.tilemap.draw(0, 0, new Color(255, 255, 255,
		// MapEditor.opacityOverlay));

		for (int y = 0; y < getMap().getLayers().get(MapLayer.BACK)[0].length; y++) {
			for (int x = 0; x < getMap().getLayers().get(MapLayer.BACK).length; x++) {
				if (getMap().getLayers().get(MapLayer.BACK)[x][y] != null) {
					if (getMap().getLayers().get(MapLayer.BACK)[x][y].getX() + Tile.TILE_SIZE >= Game.getGame().getCam()
							.getX()
							&& getMap().getLayers().get(MapLayer.BACK)[x][y].getX() <= Game.getGame().getCam().getX()
									+ GameInfo.RES_WIDTH
							&& getMap().getLayers().get(MapLayer.BACK)[x][y].getY() + Tile.TILE_SIZE >= Game.getGame()
									.getCam().getY()
							&& getMap().getLayers().get(MapLayer.BACK)[x][y].getY() <= Game.getGame().getCam().getY()
									+ GameInfo.RES_HEIGHT) {

						getMap().getLayers().get(MapLayer.BACK)[x][y].render(g);

					}
				}
			}
		}

		Objective[] tempListObjectives = new Objective[Game.getGame().getObjectives().size()];
		Game.getGame().getObjectives().toArray(tempListObjectives);
		for (Objective o : tempListObjectives) {
			if (o != null) {
				if (o.getObjectiveTeam() == ObjectiveInfo.CAPTURE_POINT
						|| o.getObjectiveTeam() == ObjectiveInfo.SMALL_CAPTURE_POINT) {
					if (o.getX() + Tile.TILE_SIZE * 20 >= Game.getGame().getCam().getX()
							&& o.getX() <= Game.getGame().getCam().getX() + GameInfo.RES_WIDTH
									+ Tile.TILE_SIZE * 10
							&& o.getY() + Tile.TILE_SIZE * 20 >= Game.getGame().getCam().getY()
							&& o.getY() <= Game.getGame().getCam().getY() + GameInfo.RES_HEIGHT
									+ Tile.TILE_SIZE * 10) {
						o.render(g);
					}
				}
			}
		}
		
		Res.MAP_RESOURCE.getMapImage().draw(0, 0);

		GameObject[] tempGroundLayerObjects = new GameObject[groundLayerObjects.size()];
		groundLayerObjects.toArray(tempGroundLayerObjects);
		for (GameObject o : tempGroundLayerObjects) {
			o.render(g);
		}

		GameObject[] tempBackLayerObjects = new GameObject[backLayerObjects.size()];
		backLayerObjects.toArray(tempBackLayerObjects);
		for (GameObject o : tempBackLayerObjects) {
			o.render(g);
		}

		GameObject[] tempFrontLayerObjects = new GameObject[frontLayerObjects.size()];
		frontLayerObjects.toArray(tempFrontLayerObjects);
		for (GameObject o : tempFrontLayerObjects) {
			o.render(g);
		}

		GameObject[] tempAboveAllLayerObjects = new GameObject[aboveAllLayerObjects.size()];
		aboveAllLayerObjects.toArray(tempAboveAllLayerObjects);
		for (GameObject o : tempAboveAllLayerObjects) {
			o.render(g);
		}

		GameObject[] tempSkyLayerObjects = new GameObject[skyLayerObjects.size()];
		skyLayerObjects.toArray(tempSkyLayerObjects);
		for (GameObject o : tempSkyLayerObjects) {
			o.render(g);
		}

		GameObject[] tempPlayerLayerObjects = new GameObject[playerMiscLayerObjects.size()];
		playerMiscLayerObjects.toArray(tempPlayerLayerObjects);
		for (GameObject o : tempPlayerLayerObjects) {
			Entity p = (Entity) o;
			p.renderPlayerHealthBar(g);
			p.renderPlayerName(g);
		}
		
	}

	public void addMapObject(float x, float y, int id, int mapLayer) {
		getMap().getMapObjects().add(new MapObject(x, y, id, mapLayer));
	}

	public void createMap(String mapName) {

		Map loadedMap = new Map(mapName);

		loadedMap.setMapWidth(200);
		loadedMap.setMapHeight(200);

		Tile[][] informationLayer = new Tile[loadedMap.getMapWidth()][loadedMap.getMapHeight()];
		Tile[][] liquidLayer = new Tile[loadedMap.getMapWidth()][loadedMap.getMapHeight()];
		Tile[][] groundLayer = new Tile[loadedMap.getMapWidth()][loadedMap.getMapHeight()];
		Tile[][] backLayer = new Tile[loadedMap.getMapWidth()][loadedMap.getMapHeight()];
		Tile[][] frontLayer = new Tile[loadedMap.getMapWidth()][loadedMap.getMapHeight()];
		Tile[][] aboveAllLayer = new Tile[loadedMap.getMapWidth()][loadedMap.getMapHeight()];

		for (int y = 0; y < loadedMap.getMapHeight(); y++) {
			for (int x = 0; x < loadedMap.getMapWidth(); x++) {
				informationLayer[x][y] = new Tile(x * Tile.TILE_SIZE, y * Tile.TILE_SIZE, -1, false, false);
				liquidLayer[x][y] = new Tile(x * Tile.TILE_SIZE, y * Tile.TILE_SIZE, -1, 100);
				groundLayer[x][y] = new Tile(x * Tile.TILE_SIZE, y * Tile.TILE_SIZE, 0);
				backLayer[x][y] = new Tile(x * Tile.TILE_SIZE, y * Tile.TILE_SIZE, -1);
				frontLayer[x][y] = new Tile(x * Tile.TILE_SIZE, y * Tile.TILE_SIZE, -1);
				aboveAllLayer[x][y] = new Tile(x * Tile.TILE_SIZE, y * Tile.TILE_SIZE, -1);
			}
		}

		informationLayer[5][5].setTileType(0);
		informationLayer[10][10].setTileType(1);

		loadedMap.getLayers().add(informationLayer);
		loadedMap.getLayers().add(liquidLayer);
		loadedMap.getLayers().add(groundLayer);
		loadedMap.getLayers().add(backLayer);
		loadedMap.getLayers().add(frontLayer);
		loadedMap.getLayers().add(aboveAllLayer);

		setMap(loadedMap);
		saveMap(mapName);
	}

	public void setMap(Map map) {
		this.map = map;
	}

	public void saveMap(String mapName) {

		String layerSplitter = "]";

		StringBuilder sb = new StringBuilder();

		sb.append(getMap().getMapWidth() + "!" + getMap().getMapHeight() + "!");

		for (int y = 0; y < getMap().getLayers().get(MapLayer.INFORMATION)[0].length; y++) {
			for (int x = 0; x < getMap().getLayers().get(MapLayer.INFORMATION).length; x++) {
				sb.append((int) getMap().getLayers().get(MapLayer.INFORMATION)[x][y].getX() + ","
						+ (int) getMap().getLayers().get(MapLayer.INFORMATION)[x][y].getY() + ","
						+ getMap().getLayers().get(MapLayer.INFORMATION)[x][y].getTileType() + ","
						+ getMap().getLayers().get(MapLayer.INFORMATION)[x][y].isBlocked() + ","
						+ getMap().getLayers().get(MapLayer.INFORMATION)[x][y].isBlocksAbilities() + "~");
			}
		}

		sb.append(layerSplitter);

		for (int y = 0; y < getMap().getLayers().get(MapLayer.LIQUID)[0].length; y++) {
			for (int x = 0; x < getMap().getLayers().get(MapLayer.LIQUID).length; x++) {
				sb.append((int) getMap().getLayers().get(MapLayer.LIQUID)[x][y].getX() + ","
						+ (int) getMap().getLayers().get(MapLayer.LIQUID)[x][y].getY() + ","
						+ getMap().getLayers().get(MapLayer.LIQUID)[x][y].getTileID() + ","
						+ getMap().getLayers().get(MapLayer.LIQUID)[x][y].getAnimationFrameDuration() + "~");
			}
		}

		sb.append(layerSplitter);

		for (int y = 0; y < getMap().getLayers().get(MapLayer.GROUND)[0].length; y++) {
			for (int x = 0; x < getMap().getLayers().get(MapLayer.GROUND).length; x++) {
				sb.append((int) getMap().getLayers().get(MapLayer.GROUND)[x][y].getX() + ","
						+ (int) getMap().getLayers().get(MapLayer.GROUND)[x][y].getY() + ","
						+ getMap().getLayers().get(MapLayer.GROUND)[x][y].getTileID() + "~");
			}
		}

		sb.append(layerSplitter);

		for (int y = 0; y < getMap().getLayers().get(MapLayer.BACK)[0].length; y++) {
			for (int x = 0; x < getMap().getLayers().get(MapLayer.BACK).length; x++) {
				sb.append((int) getMap().getLayers().get(MapLayer.BACK)[x][y].getX() + ","
						+ (int) getMap().getLayers().get(MapLayer.BACK)[x][y].getY() + ","
						+ getMap().getLayers().get(MapLayer.BACK)[x][y].getTileID() + "~");
			}
		}

		sb.append(layerSplitter);

		for (int y = 0; y < getMap().getLayers().get(MapLayer.YSORTED)[0].length; y++) {
			for (int x = 0; x < getMap().getLayers().get(MapLayer.YSORTED).length; x++) {
				sb.append((int) getMap().getLayers().get(MapLayer.YSORTED)[x][y].getX() + ","
						+ (int) getMap().getLayers().get(MapLayer.YSORTED)[x][y].getY() + ","
						+ getMap().getLayers().get(MapLayer.YSORTED)[x][y].getTileID() + ","
						+ getMap().getLayers().get(MapLayer.YSORTED)[x][y].getHeightLayer() + "~");
			}
		}

		sb.append(layerSplitter);

		for (int y = 0; y < getMap().getLayers().get(MapLayer.ABOVEALL)[0].length; y++) {
			for (int x = 0; x < getMap().getLayers().get(MapLayer.ABOVEALL).length; x++) {
				sb.append((int) getMap().getLayers().get(MapLayer.ABOVEALL)[x][y].getX() + ","
						+ (int) getMap().getLayers().get(MapLayer.ABOVEALL)[x][y].getY() + ","
						+ getMap().getLayers().get(MapLayer.ABOVEALL)[x][y].getTileID() + "~");
			}
		}

		sb.append(layerSplitter);

		for (MapObject mo : getMap().getMapObjects()) {
			sb.append((int) mo.getX() + "," + (int) mo.getY() + "," + mo.id + "," + mo.mapLayer + "~");
		}

		sb.append(layerSplitter);

		if (Game.getGame().getObjectives() != null) {
			Objective[] tempList = new Objective[Game.getGame().getObjectives().size()];
			Game.getGame().getObjectives().toArray(tempList);

			for (Objective o : tempList) {
				sb.append((int) o.getX() + "," + (int) o.getY() + "," + (int) o.getObjectiveTeam() + "~");
			}
		}

		PrintWriter mapWriter;
		try {
			String theMap = mapName;
			if (theMap.trim().equals("")) {
				theMap = "databit";
			}
			mapWriter = new PrintWriter("res/" + theMap + ".bitsiege");
			mapWriter.println(Res.encode(sb.toString(), Res.encryptOffSet));
			mapWriter.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		Game.getGame().getHUDDisplay().getChatHUD().getChatList()
				.add(new ChatMessage("Map: " + mapName + " has been saved!", Color.green));
	}

	public Map getMap() {
		return map;
	}

}
