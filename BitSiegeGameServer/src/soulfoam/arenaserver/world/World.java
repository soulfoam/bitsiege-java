package soulfoam.arenaserver.world;

import java.util.Random;

import soulfoam.arenaserver.main.game.Game;


public class World {

	private Map map;

	public void setTile(float x, float y, Tile t, int layer) {

		map.getLayers().get(layer)[(int) x / Tile.TILE_SIZE][(int) y / Tile.TILE_SIZE] = t;
	}
	
	public void setTile(Tile ot, Tile nt, int layer) {

		map.getLayers().get(layer)[(int) ot.getX() / Tile.TILE_SIZE][(int) ot.getY() / Tile.TILE_SIZE] = nt;

	}

	public Tile getTile(float x, float y, int layer) {

		if (x >= (map.getMapWidth() * Tile.TILE_SIZE) - Tile.TILE_SIZE){
			x = (map.getMapWidth() * Tile.TILE_SIZE) - Tile.TILE_SIZE;
		}
		if (x <= 0){
			x = 0;
		}
		
		if (y >= (map.getMapHeight() * Tile.TILE_SIZE) - Tile.TILE_SIZE){
			y = (map.getMapHeight() * Tile.TILE_SIZE) - Tile.TILE_SIZE;
		}
		if (y <= 0){
			y = 0;
		}
		
		return map.getLayers().get(layer)[(int) x / Tile.TILE_SIZE][(int) y / Tile.TILE_SIZE];

	}
	
	public Tile getTile(float x, float y) {

		if (x >= (map.getMapWidth() * Tile.TILE_SIZE) - Tile.TILE_SIZE){
			x = (map.getMapWidth() * Tile.TILE_SIZE) - Tile.TILE_SIZE;
		}
		if (x <= 0){
			x = 0;
		}
		
		if (y >= (map.getMapHeight() * Tile.TILE_SIZE) - Tile.TILE_SIZE){
			y = (map.getMapHeight() * Tile.TILE_SIZE) - Tile.TILE_SIZE;
		}
		if (y <= 0){
			y = 0;
		}
		
		return map.getLayers().get(MapLayer.INFORMATION)[(int) x / Tile.TILE_SIZE][(int) y / Tile.TILE_SIZE];

	}
	
	public void setMap(Map map){
		this.map = map;
	}

	public Map getMap() {
		return map;
	}
	
}
