package soulfoam.arena.main.resources;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import soulfoam.arena.main.gfx.SpriteSheet;
import soulfoam.arena.main.misc.DataObject;
import soulfoam.arena.world.Map;
import soulfoam.arena.world.MapLayer;
import soulfoam.arena.world.MapObject;
import soulfoam.arena.world.Tile;

public class MapResource {

	private SpriteSheet standardTilesLiquid;
	private SpriteSheet standardTilesGround;
	private SpriteSheet standardTilesPlaceable;
	private SpriteSheet standardTilesWall;
	private Image mapImage;

	public ArrayList<TileResourceObject> TILE_LIBRARY = new ArrayList<TileResourceObject>();
	public ArrayList<MapObject> MAP_OBJECT_LIBRARY = new ArrayList<MapObject>();

	public ArrayList<Integer> TILE_ID_POOL = new ArrayList<Integer>();
	public ArrayList<Integer> OBJECT_ID_POOL = new ArrayList<Integer>();

	public ArrayList<Map> maps = new ArrayList<Map>();

	public Image[] WATER_STILL = new Image[3];
	public Image[] WATER_UP_FLOW = new Image[3];
	public Image[] WATER_DOWN_FLOW = new Image[3];
	public Image[] LAVA_STILL = new Image[3];
	public Image[] LAVA_UP_FLOW = new Image[3];
	public Image[] LAVA_DOWN_FLOW = new Image[3];

	public MapResource() {
		init();
	}

	public void init() {
		initTileIDPool();
		initObjectIDPool();
		initStandardMap();
		try {
			mapImage = new Image("art/maps/map.png", false);
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	public void loadMaps() {
//		loadMap(0, "Squared Off", "databit1");
//		loadMap(1, "Hex's Castle", "databit2");
//		loadMap(2, "Courtyard", "databit3");
		loadMap(0, "new", "new");
		
	}
	
	public Map getMap(int mapID){
		for (Map m : maps){
			if (m.getMapID() == mapID){
				return m;
			}
		}
		
		return null;
	}

	public void initStandardMap() {

		standardTilesGround = new SpriteSheet("art/maps/standard/ground.png");
		standardTilesPlaceable = new SpriteSheet("art/maps/standard/placeables.png");
		standardTilesWall = new SpriteSheet("art/maps/standard/walls.png");
		standardTilesLiquid = new SpriteSheet("art/maps/standard/liquid.png");

		ArrayList<TileResourceObject> groundTiles = new ArrayList<TileResourceObject>();
		ArrayList<TileResourceObject> wallTiles = new ArrayList<TileResourceObject>();
		ArrayList<TileResourceObject> liquidTiles = new ArrayList<TileResourceObject>();

		WATER_STILL = standardTilesLiquid.grabTileRowImage(0, 3);
		WATER_UP_FLOW = standardTilesLiquid.grabTileRowImage(1, 3);
		WATER_DOWN_FLOW = standardTilesLiquid.grabTileRowImage(2, 3);
		LAVA_STILL = standardTilesLiquid.grabTileRowImage(3, 3);
		LAVA_UP_FLOW = standardTilesLiquid.grabTileRowImage(4, 3);
		LAVA_DOWN_FLOW = standardTilesLiquid.grabTileRowImage(5, 3);

		for (int i = 0; i < 6; i++) {
			liquidTiles.addAll(standardTilesLiquid.grabTileRow(i, 10));
		}

		for (int i = 0; i < 10; i++) {
			groundTiles.addAll(standardTilesGround.grabTileRow(i, 15));
		}

		for (int i = 0; i < 5; i++) {
			wallTiles.addAll(standardTilesWall.grabTileRow(i, 10));
		}

		for (TileResourceObject tro : groundTiles) {
			tro.id = getAvailableTileIDFromPool();
			tro.groupID = MapLayer.GROUP_STANDARD_GROUND;
		}

		for (TileResourceObject tro : wallTiles) {
			tro.id = getAvailableTileIDFromPool();
			tro.groupID = MapLayer.GROUP_STANDARD_WALL;
		}

		for (TileResourceObject tro : liquidTiles) {
			tro.id = getAvailableTileIDFromPool();
			tro.groupID = MapLayer.GROUP_STANDARD_LIQUID;
		}

		TILE_LIBRARY.addAll(liquidTiles);
		TILE_LIBRARY.addAll(groundTiles);
		TILE_LIBRARY.addAll(wallTiles);

		MAP_OBJECT_LIBRARY.add(getStandardTilesPlaceable().grabMapObject(1, 0, Tile.TILE_SIZE * 2, Tile.TILE_SIZE * 4, Tile.TILE_SIZE * 2 - 1, Tile.TILE_SIZE * 4 - 4, getAvailableObjectIDFromPool(), MapLayer.GROUP_STANDARD_OBJECT));
		MAP_OBJECT_LIBRARY.add(getStandardTilesPlaceable().grabMapObject(0, 4, Tile.TILE_SIZE * 3, Tile.TILE_SIZE * 2, Tile.TILE_SIZE * 3 - 3, 0, getAvailableObjectIDFromPool(), MapLayer.GROUP_STANDARD_OBJECT));
		MAP_OBJECT_LIBRARY.add(getStandardTilesPlaceable().grabMapObject(3, 0, Tile.TILE_SIZE * 2, Tile.TILE_SIZE * 2, Tile.TILE_SIZE * 2 - 4, Tile.TILE_SIZE * 2 - 8, getAvailableObjectIDFromPool(),MapLayer.GROUP_STANDARD_OBJECT));
		MAP_OBJECT_LIBRARY.add(getStandardTilesPlaceable().grabMapObject(3, 2, Tile.TILE_SIZE, Tile.TILE_SIZE, Tile.TILE_SIZE - 2, Tile.TILE_SIZE - 4, getAvailableObjectIDFromPool(), MapLayer.GROUP_STANDARD_OBJECT));
		MAP_OBJECT_LIBRARY.add(getStandardTilesPlaceable().grabMapObject(4, 2, Tile.TILE_SIZE, Tile.TILE_SIZE, Tile.TILE_SIZE - 3,Tile.TILE_SIZE - 4, getAvailableObjectIDFromPool(), MapLayer.GROUP_STANDARD_OBJECT));
		MAP_OBJECT_LIBRARY.add(getStandardTilesPlaceable().grabMapObject(3, 3, Tile.TILE_SIZE, Tile.TILE_SIZE * 2, Tile.TILE_SIZE, Tile.TILE_SIZE * 2 - 8, getAvailableObjectIDFromPool(), MapLayer.GROUP_STANDARD_OBJECT));
		MAP_OBJECT_LIBRARY.add(getStandardTilesPlaceable().grabMapObject(3, 5, Tile.TILE_SIZE * 2, Tile.TILE_SIZE * 2, Tile.TILE_SIZE * 2 - 7, Tile.TILE_SIZE * 2 - 8, getAvailableObjectIDFromPool(),MapLayer.GROUP_STANDARD_OBJECT));
		MAP_OBJECT_LIBRARY.add(getStandardTilesPlaceable().grabMapObject(5, 0, Tile.TILE_SIZE * 4, Tile.TILE_SIZE * 7, Tile.TILE_SIZE * 4,Tile.TILE_SIZE * 7 - 7, getAvailableObjectIDFromPool(), MapLayer.GROUP_STANDARD_OBJECT));
		MAP_OBJECT_LIBRARY.add(getStandardTilesPlaceable().grabMapObject(9, 0, Tile.TILE_SIZE * 4, Tile.TILE_SIZE * 6, Tile.TILE_SIZE * 4,Tile.TILE_SIZE * 6 - 5, getAvailableObjectIDFromPool(), MapLayer.GROUP_STANDARD_OBJECT));
		MAP_OBJECT_LIBRARY.add(getStandardTilesPlaceable().grabMapObject(13, 0, Tile.TILE_SIZE * 3, Tile.TILE_SIZE * 4,Tile.TILE_SIZE * 3 - 7, Tile.TILE_SIZE * 4 - 6, getAvailableObjectIDFromPool(),MapLayer.GROUP_STANDARD_OBJECT));
		MAP_OBJECT_LIBRARY.add(getStandardTilesPlaceable().grabMapObject(16, 0, Tile.TILE_SIZE * 3, Tile.TILE_SIZE * 6,Tile.TILE_SIZE * 3 - 7, Tile.TILE_SIZE * 6 - 5, getAvailableObjectIDFromPool(),MapLayer.GROUP_STANDARD_OBJECT));
		MAP_OBJECT_LIBRARY.add(getStandardTilesPlaceable().grabMapObject(19, 0, Tile.TILE_SIZE * 2, Tile.TILE_SIZE * 3,Tile.TILE_SIZE * 2 - 4, Tile.TILE_SIZE * 3 - 3, getAvailableObjectIDFromPool(),MapLayer.GROUP_STANDARD_OBJECT));
		MAP_OBJECT_LIBRARY.add(getStandardTilesPlaceable().grabMapObject(19, 3, Tile.TILE_SIZE * 3, Tile.TILE_SIZE * 4,Tile.TILE_SIZE * 3 - 2, Tile.TILE_SIZE * 4 - 3, getAvailableObjectIDFromPool(),MapLayer.GROUP_STANDARD_OBJECT));
		MAP_OBJECT_LIBRARY.add(getStandardTilesPlaceable().grabMapObject(21, 0, Tile.TILE_SIZE * 3, Tile.TILE_SIZE * 4,Tile.TILE_SIZE * 3 - 5, Tile.TILE_SIZE * 4 - 6, getAvailableObjectIDFromPool(),MapLayer.GROUP_STANDARD_OBJECT));
		MAP_OBJECT_LIBRARY.add(getStandardTilesPlaceable().grabMapObject(24, 0, Tile.TILE_SIZE * 4, Tile.TILE_SIZE * 6,Tile.TILE_SIZE * 4 - 7, Tile.TILE_SIZE * 6 - 7, getAvailableObjectIDFromPool(),MapLayer.GROUP_STANDARD_OBJECT));
		MAP_OBJECT_LIBRARY.add(getStandardTilesPlaceable().grabMapObject(0, 7, Tile.TILE_SIZE * 4, Tile.TILE_SIZE * 6,Tile.TILE_SIZE * 4 - 7, Tile.TILE_SIZE * 6 - 7, getAvailableObjectIDFromPool(),MapLayer.GROUP_STANDARD_OBJECT));
		MAP_OBJECT_LIBRARY.add(getStandardTilesPlaceable().grabMapObject(4, 7, Tile.TILE_SIZE * 3, Tile.TILE_SIZE * 2,Tile.TILE_SIZE * 3, 0, getAvailableObjectIDFromPool(),MapLayer.GROUP_STANDARD_OBJECT));
		
	}

	public void initTileIDPool() {
		TILE_ID_POOL.clear();
		for (int i = 0; i < 5000; i++) {
			TILE_ID_POOL.add(i);
		}
	}

	public int getAvailableTileIDFromPool() {
		return TILE_ID_POOL.remove(0);
	}

	public void initObjectIDPool() {
		OBJECT_ID_POOL.clear();
		for (int i = 0; i < 5000; i++) {
			OBJECT_ID_POOL.add(i);
		}
	}

	public int getAvailableObjectIDFromPool() {
		return OBJECT_ID_POOL.remove(0);
	}

	public TileResourceObject getTileResourceByID(int id) {
		for (TileResourceObject tile : TILE_LIBRARY) {
			if (tile.id == id) {
				return tile;
			}
		}

		TileResourceObject returnTile = new TileResourceObject(-1, 0, 0, -1, null);

		return returnTile;
	}

	public MapObject getMapObjectByID(int id) {
		for (MapObject o : MAP_OBJECT_LIBRARY) {
			if (o.id == id) {
				return o;
			}
		}

		MapObject returnObject = new MapObject(null, 0, 0, -1, -1, 0, 0, 0, 0);

		return returnObject;
	}

	public MapObject getMapObjectByGroupID(int groupID) {
		for (MapObject o : MAP_OBJECT_LIBRARY) {
			if (o.groupID == groupID) {
				return o;
			}
		}

		return null;
	}

	public ArrayList<MapObject> getMapObjectsByGroupID(int groupID) {
		ArrayList<MapObject> returnList = new ArrayList<MapObject>();

		for (MapObject o : MAP_OBJECT_LIBRARY) {
			if (o.groupID == groupID) {
				returnList.add(o);
			}
		}

		return returnList;
	}

	public void loadMap(int mapID, String mapName, String fileName) {

		if (fileName.trim().equals("")) {
			fileName = "databit";
		}
		InputStream in = getClass().getResourceAsStream("/" + fileName + ".bitsiege");

		try (BufferedReader br = new BufferedReader(new InputStreamReader(in))) {

			StringBuilder sb = new StringBuilder();
			String line = br.readLine();

			while (line != null) {
				sb.append(line);
				sb.append(System.lineSeparator());
				line = br.readLine();
			}
			String everything = Res.decode(sb.toString(), Res.encryptOffSet);
			parseMap(mapID, everything, mapName);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void parseMap(int mapID, String theMap, String mapName) {

		Map loadedMap = new Map(mapName);
		loadedMap.setMapID(mapID);

		String[] mapInfo = theMap.trim().split("!");

		String[] mapLayers = mapInfo[2].trim().split("]");

		loadedMap.setMapWidth(Integer.parseInt(mapInfo[0]));
		loadedMap.setMapHeight(Integer.parseInt(mapInfo[1]));

		ArrayList<Tile> infoLayerTileList = new ArrayList<Tile>();
		ArrayList<Tile> liquidLayerTileList = new ArrayList<Tile>();
		ArrayList<Tile> groundLayerTileList = new ArrayList<Tile>();
		ArrayList<Tile> backLayerTileList = new ArrayList<Tile>();
		ArrayList<Tile> frontLayerTileList = new ArrayList<Tile>();
		ArrayList<Tile> aboveAllLayerTileList = new ArrayList<Tile>();

		String[] infoLayerSplit = mapLayers[MapLayer.INFORMATION].trim().split("~");
		String[] liquidLayerSplit = mapLayers[MapLayer.LIQUID].trim().split("~");
		String[] groundLayerSplit = mapLayers[MapLayer.GROUND].trim().split("~");
		String[] backLayerSplit = mapLayers[MapLayer.BACK].trim().split("~");
		String[] frontLayerSplit = mapLayers[MapLayer.YSORTED].trim().split("~");
		String[] aboveAllLayerSplit = mapLayers[MapLayer.ABOVEALL].trim().split("~");

		if (mapLayers.length >= 7) {
			String[] mapObjectLayerSplit = mapLayers[6].trim().split("~");
			for (int i = 0; i < mapObjectLayerSplit.length; i++) {
				String[] mapObjectParameter = mapObjectLayerSplit[i].trim().split(",");
				if (!mapObjectParameter[0].isEmpty()) {
					loadedMap.getMapObjects().add(new MapObject(Float.parseFloat(mapObjectParameter[0]),
							Float.parseFloat(mapObjectParameter[1]), Integer.parseInt(mapObjectParameter[2]),
							Integer.parseInt(mapObjectParameter[3])));
				}
			}
		}

		if (mapLayers.length >= 8) {
			String[] objectiveLayerSplit = mapLayers[7].trim().split("~");

			for (int i = 0; i < objectiveLayerSplit.length; i++) {
				String[] objectiveParameter = objectiveLayerSplit[i].trim().split(",");
				if (!objectiveParameter[0].isEmpty()) {
					loadedMap.getOBJECTIVES_TO_SPAWN().add(new DataObject(Float.parseFloat(objectiveParameter[0]),
							Float.parseFloat(objectiveParameter[1]), Byte.parseByte(objectiveParameter[2])));
				}
			}

		}

		Tile[][] infoLayer = new Tile[loadedMap.getMapWidth()][loadedMap.getMapHeight()];
		Tile[][] liquidLayer = new Tile[loadedMap.getMapWidth()][loadedMap.getMapHeight()];
		Tile[][] groundLayer = new Tile[loadedMap.getMapWidth()][loadedMap.getMapHeight()];
		Tile[][] backLayer = new Tile[loadedMap.getMapWidth()][loadedMap.getMapHeight()];
		Tile[][] frontLayer = new Tile[loadedMap.getMapWidth()][loadedMap.getMapHeight()];
		Tile[][] aboveAllLayer = new Tile[loadedMap.getMapWidth()][loadedMap.getMapHeight()];

		loadedMap.getLayers().add(infoLayer);
		loadedMap.getLayers().add(liquidLayer);
		loadedMap.getLayers().add(groundLayer);
		loadedMap.getLayers().add(backLayer);
		loadedMap.getLayers().add(frontLayer);
		loadedMap.getLayers().add(aboveAllLayer);

		for (int i = 0; i < infoLayerSplit.length; i++) {
			String[] tileParameter = infoLayerSplit[i].trim().split(",");
			infoLayerTileList.add(new Tile(Integer.parseInt(tileParameter[0]), Integer.parseInt(tileParameter[1]),
					Integer.parseInt(tileParameter[2]), Boolean.parseBoolean(tileParameter[3]),
					Boolean.parseBoolean(tileParameter[4])));
		}

		for (int i = 0; i < infoLayerTileList.size(); i++) {
			loadedMap.getLayers().get(MapLayer.INFORMATION)[(int) infoLayerTileList.get(i).getX()
					/ Tile.TILE_SIZE][(int) infoLayerTileList.get(i).getY() / Tile.TILE_SIZE] = infoLayerTileList
							.get(i);
			addInfoTile(infoLayerTileList.get(i), loadedMap);
		}

		for (int i = 0; i < liquidLayerSplit.length; i++) {
			String[] tileParameter = liquidLayerSplit[i].trim().split(",");
			liquidLayerTileList.add(new Tile(Integer.parseInt(tileParameter[0]), Integer.parseInt(tileParameter[1]),
					Integer.parseInt(tileParameter[2]), Integer.parseInt(tileParameter[3])));
		}

		for (int i = 0; i < liquidLayerTileList.size(); i++) {
			loadedMap.getLayers().get(MapLayer.LIQUID)[(int) liquidLayerTileList.get(i).getX()
					/ Tile.TILE_SIZE][(int) liquidLayerTileList.get(i).getY() / Tile.TILE_SIZE] = liquidLayerTileList
							.get(i);
		}

		for (int i = 0; i < groundLayerSplit.length; i++) {
			String[] tileParameter = groundLayerSplit[i].trim().split(",");
			groundLayerTileList.add(new Tile(Integer.parseInt(tileParameter[0]), Integer.parseInt(tileParameter[1]),
					Integer.parseInt(tileParameter[2])));
		}

		for (int i = 0; i < groundLayerTileList.size(); i++) {
			loadedMap.getLayers().get(MapLayer.GROUND)[(int) groundLayerTileList.get(i).getX()
					/ Tile.TILE_SIZE][(int) groundLayerTileList.get(i).getY() / Tile.TILE_SIZE] = groundLayerTileList
							.get(i);
		}

		for (int i = 0; i < backLayerSplit.length; i++) {
			String[] tileParameter = backLayerSplit[i].trim().split(",");
			backLayerTileList.add(new Tile(Integer.parseInt(tileParameter[0]), Integer.parseInt(tileParameter[1]),
					Integer.parseInt(tileParameter[2])));
		}

		for (int i = 0; i < backLayerTileList.size(); i++) {
			loadedMap.getLayers().get(MapLayer.BACK)[(int) backLayerTileList.get(i).getX()
					/ Tile.TILE_SIZE][(int) backLayerTileList.get(i).getY() / Tile.TILE_SIZE] = backLayerTileList
							.get(i);
		}

		for (int i = 0; i < frontLayerSplit.length; i++) {
			String[] tileParameter = frontLayerSplit[i].trim().split(",");
			Tile t = new Tile(Integer.parseInt(tileParameter[0]), Integer.parseInt(tileParameter[1]),
					Integer.parseInt(tileParameter[2]));
			t.setHeightLayer(Integer.parseInt(tileParameter[3]));
			frontLayerTileList.add(t);
		}

		for (int i = 0; i < frontLayerTileList.size(); i++) {
			loadedMap.getLayers().get(MapLayer.YSORTED)[(int) frontLayerTileList.get(i).getX()
					/ Tile.TILE_SIZE][(int) frontLayerTileList.get(i).getY() / Tile.TILE_SIZE] = frontLayerTileList
							.get(i);
		}

		for (int i = 0; i < aboveAllLayerSplit.length; i++) {
			String[] tileParameter = aboveAllLayerSplit[i].trim().split(",");
			aboveAllLayerTileList.add(new Tile(Integer.parseInt(tileParameter[0]), Integer.parseInt(tileParameter[1]),
					Integer.parseInt(tileParameter[2])));
		}

		for (int i = 0; i < aboveAllLayerTileList.size(); i++) {
			loadedMap.getLayers().get(MapLayer.ABOVEALL)[(int) aboveAllLayerTileList.get(i).getX()
					/ Tile.TILE_SIZE][(int) aboveAllLayerTileList.get(i).getY()
							/ Tile.TILE_SIZE] = aboveAllLayerTileList.get(i);
		}
		loadedMap.createMapPreview();
		loadedMap.createMiniMap();
		maps.add(loadedMap);
	}

	public void addInfoTile(Tile tile, Map map) {
		if (tile.getTileType() == -1) {
			return;
		}
		if (tile.getTileType() == 0) {
			map.getTEAMD_SPAWN().add(tile);
		}
		if (tile.getTileType() == 1) {
			map.getTEAMA_SPAWN().add(tile);
		}
		if (tile.getTileType() == 2) {
			map.getTILE_DEFENDER_PATROL().add(tile);
			map.getTILE_ATTACKER_PATROL().add(tile);
		}
	}

	public SpriteSheet getStandardTilesPlaceable() {
		return standardTilesPlaceable;
	}

	public void setStandardTilesPlaceable(SpriteSheet standardTilesPlaceable) {
		this.standardTilesPlaceable = standardTilesPlaceable;
	}

	public Image getMapImage() {
		return mapImage;
	}


}
