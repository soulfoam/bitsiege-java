package soulfoam.arenaserver.main.resources;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import soulfoam.arenaserver.main.misc.DataObject;
import soulfoam.arenaserver.world.Map;
import soulfoam.arenaserver.world.MapLayer;
import soulfoam.arenaserver.world.Tile;

public class MapResource {

	public ArrayList<Map> maps = new ArrayList<Map>();
	
	public MapResource(){
		loadMaps();
	}
	
	public void loadMaps(){
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
	
	public void loadMap(int mapID, String mapName, String fileName){

       if (fileName.trim().equals("")) fileName = "databit";
	   InputStream in = getClass().getResourceAsStream("/" + fileName + ".bitsiege"); 

	   try(BufferedReader br = new BufferedReader(new InputStreamReader(in))){

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
	
	public void parseMap(int mapID, String theMap, String mapName){
		
		Map loadedMap = new Map(mapName);
		loadedMap.mapID = mapID;
		
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
		
		if (mapLayers.length >= 8){
			String[] objectiveLayerSplit = mapLayers[7].trim().split("~");

			for (int i = 0; i < objectiveLayerSplit.length; i++){
				String[] objectiveParameter = objectiveLayerSplit[i].trim().split(",");
				if (!objectiveParameter[0].isEmpty()){
					loadedMap.getOBJECTIVES_TO_SPAWN().add(new DataObject(Float.parseFloat(objectiveParameter[0]), Float.parseFloat(objectiveParameter[1]), Byte.parseByte(objectiveParameter[2])));
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
		
		for (int i = 0; i < infoLayerSplit.length; i++){
			String[] tileParameter = infoLayerSplit[i].trim().split(",");
			infoLayerTileList.add(new Tile(Integer.parseInt(tileParameter[0]), Integer.parseInt(tileParameter[1]), Integer.parseInt(tileParameter[2]), Boolean.parseBoolean(tileParameter[3]), Boolean.parseBoolean(tileParameter[4])));		
		}

		for (int i = 0; i < infoLayerTileList.size(); i++){
			loadedMap.getLayers().get(MapLayer.INFORMATION)[(int)infoLayerTileList.get(i).getX() / Tile.TILE_SIZE][(int)infoLayerTileList.get(i).getY() / Tile.TILE_SIZE] = infoLayerTileList.get(i);
			addInfoTile(infoLayerTileList.get(i), loadedMap);
		}
		
		
		for (int i = 0; i < liquidLayerSplit.length; i++){
			String[] tileParameter = liquidLayerSplit[i].trim().split(",");
			liquidLayerTileList.add(new Tile(Integer.parseInt(tileParameter[0]), Integer.parseInt(tileParameter[1]), Integer.parseInt(tileParameter[2]), Integer.parseInt(tileParameter[3])));		
		}

		for (int i = 0; i < liquidLayerTileList.size(); i++){
			loadedMap.getLayers().get(MapLayer.LIQUID)[(int)liquidLayerTileList.get(i).getX() / Tile.TILE_SIZE][(int)liquidLayerTileList.get(i).getY() / Tile.TILE_SIZE] = liquidLayerTileList.get(i);
		}
		
		
		for (int i = 0; i < groundLayerSplit.length; i++){
			String[] tileParameter = groundLayerSplit[i].trim().split(",");
			groundLayerTileList.add(new Tile(Integer.parseInt(tileParameter[0]), Integer.parseInt(tileParameter[1]), Integer.parseInt(tileParameter[2])));		
		}

		for (int i = 0; i < groundLayerTileList.size(); i++){
			loadedMap.getLayers().get(MapLayer.GROUND)[(int)groundLayerTileList.get(i).getX() / Tile.TILE_SIZE][(int)groundLayerTileList.get(i).getY() / Tile.TILE_SIZE] = groundLayerTileList.get(i);
		}
		
		
		for (int i = 0; i < backLayerSplit.length; i++){
			String[] tileParameter = backLayerSplit[i].trim().split(",");
			backLayerTileList.add(new Tile(Integer.parseInt(tileParameter[0]), Integer.parseInt(tileParameter[1]), Integer.parseInt(tileParameter[2])));		
		}

		for (int i = 0; i < backLayerTileList.size(); i++){
			loadedMap.getLayers().get(MapLayer.BACK)[(int)backLayerTileList.get(i).getX() / Tile.TILE_SIZE][(int)backLayerTileList.get(i).getY() / Tile.TILE_SIZE] = backLayerTileList.get(i);
		}
		
		
		for (int i = 0; i < frontLayerSplit.length; i++){
			String[] tileParameter = frontLayerSplit[i].trim().split(",");
			Tile t = new Tile(Integer.parseInt(tileParameter[0]), Integer.parseInt(tileParameter[1]), Integer.parseInt(tileParameter[2]));
			t.setHeightLayer(Integer.parseInt(tileParameter[3]));
			frontLayerTileList.add(t);		
		}

		for (int i = 0; i < frontLayerTileList.size(); i++){
			loadedMap.getLayers().get(MapLayer.YSORTED)[(int)frontLayerTileList.get(i).getX() / Tile.TILE_SIZE][(int)frontLayerTileList.get(i).getY() / Tile.TILE_SIZE] = frontLayerTileList.get(i);
		}
		
		for (int i = 0; i < aboveAllLayerSplit.length; i++){
			String[] tileParameter = aboveAllLayerSplit[i].trim().split(",");
			aboveAllLayerTileList.add(new Tile(Integer.parseInt(tileParameter[0]), Integer.parseInt(tileParameter[1]), Integer.parseInt(tileParameter[2])));		
		}

		for (int i = 0; i < aboveAllLayerTileList.size(); i++){
			loadedMap.getLayers().get(MapLayer.ABOVEALL)[(int)aboveAllLayerTileList.get(i).getX() / Tile.TILE_SIZE][(int)aboveAllLayerTileList.get(i).getY() / Tile.TILE_SIZE] = aboveAllLayerTileList.get(i);
		}
		
		maps.add(loadedMap);
	}

	public void addInfoTile(Tile tile, Map map){
		if (tile.getTileType() == -1){
			return;
		}
		if (tile.getTileType() == 0){
			map.getTEAMD_SPAWN().add(tile);
		}
		if (tile.getTileType() == 1){
			map.getTEAMA_SPAWN().add(tile);
		}
		if (tile.getTileType() == 2){
			map.getTILE_DEFENDER_PATROL().add(tile);
			map.getTILE_ATTACKER_PATROL().add(tile);
		}
	}
}
