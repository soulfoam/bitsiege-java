package soulfoam.arenaserver.world;

import java.util.ArrayList;

import org.newdawn.slick.geom.Rectangle;

import soulfoam.arenaserver.entities.GameObject;
import soulfoam.arenaserver.main.game.Game;
import soulfoam.arenashared.main.entityinfo.EntityInfo;

public class Tile extends GameObject{

	public final static int TILE_SIZE = 8;
	
	private int renderLayer;
	
	private int tileID;
	private int tileType = -1;

	private boolean blocked;
	private int animationFrameDuration;
	private int score = 0;
	private int distance = 0;
	private boolean blocksAbilities;

	private int heightLayer = 1;


	private Tile parentTile;

	
	public Tile(float x, float y, int tileType, boolean isBlocked, boolean blocksAbilities) {
		
		this.x = x;
		this.y = y;
		this.tileType = tileType;
		this.blocked = isBlocked;
		this.blocksAbilities = blocksAbilities;

		this.width = Tile.TILE_SIZE;
		this.height = Tile.TILE_SIZE;
		this.hitBoxWidth = Tile.TILE_SIZE;
		this.hitBoxHeight = Tile.TILE_SIZE;
	}
	
	public Tile(float x, float y, int tileID, int animationFrameDuration) {

		this.x = x;
		this.y = y;
		this.tileID = tileID;

		this.animationFrameDuration = animationFrameDuration;


		this.width = Tile.TILE_SIZE;
		this.height = Tile.TILE_SIZE;
		this.hitBoxWidth = Tile.TILE_SIZE;
		this.hitBoxHeight = Tile.TILE_SIZE;
		
	}
	
	public Tile(float x, float y, int tileID) {

		this.x = x;
		this.y = y;
		this.tileID = tileID;


		this.width = Tile.TILE_SIZE;
		this.height = Tile.TILE_SIZE;
		
		
		this.hitBoxWidth = Tile.TILE_SIZE;
		this.hitBoxHeight = Tile.TILE_SIZE;
	}
	
	
	public void update(int delta){

	}
	

	public ArrayList<Tile> getNeighborsPF(){
		
		ArrayList<Tile> neighborList = new ArrayList<Tile>(); 
		
		Tile top = null;
		Tile left = null;
		Tile bottom = null;
		Tile right = null;

		
		if (x / Tile.TILE_SIZE <= 0 || x / Tile.TILE_SIZE >= Game.getGame().getWorld().getMap().getMapWidth() || y / Tile.TILE_SIZE <= 0 || y / Tile.TILE_SIZE >= Game.getGame().getWorld().getMap().getMapHeight()){ 
			return neighborList; 
		} 
		else{ 
			
			if (!(y <= 8)){
				top = Game.getGame().getWorld().getTile(x, y - Tile.TILE_SIZE, MapLayer.INFORMATION);
			}
			if (!(x <= 8)){
				left = Game.getGame().getWorld().getTile(x - Tile.TILE_SIZE, y, MapLayer.INFORMATION);
			}
			if (!(y >= (Game.getGame().getWorld().getMap().getMapHeight() * Tile.TILE_SIZE) - Tile.TILE_SIZE)){
				bottom = Game.getGame().getWorld().getTile(x, y + Tile.TILE_SIZE, MapLayer.INFORMATION);
			}
			if (!(x >= (Game.getGame().getWorld().getMap().getMapWidth() * Tile.TILE_SIZE) - Tile.TILE_SIZE)){
				right = Game.getGame().getWorld().getTile(x + Tile.TILE_SIZE, y, MapLayer.INFORMATION);
			}
			
			if (top != null)
			neighborList.add(top);
			if (left != null)
			neighborList.add(left);
			if (bottom != null)
			neighborList.add(bottom);
			if (right != null)
			neighborList.add(right);
			
	
			return neighborList;
		}
		
	}
	
	public ArrayList<Tile> getNeighbors(){
		
		ArrayList<Tile> neighborList = new ArrayList<Tile>(); 
		
		Tile top = null;
		Tile left = null;
		Tile bottom = null;
		Tile right = null;
		Tile topLeft = null;
		Tile topRight = null;
		Tile bottomLeft = null;
		Tile bottomRight = null;
		

		if (x / Tile.TILE_SIZE <= 0 || x / Tile.TILE_SIZE >= Game.getGame().getWorld().getMap().getMapWidth() || y / Tile.TILE_SIZE <= 0 || y / Tile.TILE_SIZE >= Game.getGame().getWorld().getMap().getMapHeight()){ 
			return neighborList; 
		} 
		else{ 
			
			if (!(y <= 8)){
				top = Game.getGame().getWorld().getTile(x, y - Tile.TILE_SIZE, MapLayer.INFORMATION);
				topLeft = Game.getGame().getWorld().getTile(x - Tile.TILE_SIZE, y - Tile.TILE_SIZE, MapLayer.INFORMATION);
			}
			if (!(x <= 8)){
				left = Game.getGame().getWorld().getTile(x - Tile.TILE_SIZE, y, MapLayer.INFORMATION);
				bottomLeft = Game.getGame().getWorld().getTile(x - Tile.TILE_SIZE, y + Tile.TILE_SIZE, MapLayer.INFORMATION);
			}
			if (!(y >= (Game.getGame().getWorld().getMap().getMapHeight() * Tile.TILE_SIZE) - Tile.TILE_SIZE)){
				bottom = Game.getGame().getWorld().getTile(x, y + Tile.TILE_SIZE, MapLayer.INFORMATION);
				bottomRight = Game.getGame().getWorld().getTile(x + Tile.TILE_SIZE, y + Tile.TILE_SIZE, MapLayer.INFORMATION);
			}
			if (!(x >= (Game.getGame().getWorld().getMap().getMapWidth() * Tile.TILE_SIZE) - Tile.TILE_SIZE)){
				right = Game.getGame().getWorld().getTile(x + Tile.TILE_SIZE, y, MapLayer.INFORMATION);
				topRight = Game.getGame().getWorld().getTile(x + Tile.TILE_SIZE, y + Tile.TILE_SIZE, MapLayer.INFORMATION);
			}
			
			if (top != null)
			neighborList.add(top);
			if (left != null)
			neighborList.add(left);
			if (bottom != null)
			neighborList.add(bottom);
			if (right != null)
			neighborList.add(right);
			
			if (topLeft != null)
			neighborList.add(topLeft);
			if (bottomLeft != null)
			neighborList.add(bottomLeft);
			if (bottomRight != null)
			neighborList.add(bottomRight);
			if (topRight != null)
			neighborList.add(topRight);
	
			return neighborList;
		}

	}
	
	public Tile getTileBelow(){
		Tile tileBelow = null;
		tileBelow = Game.getGame().getWorld().getTile(x, y + Tile.TILE_SIZE, MapLayer.INFORMATION);
		if (tileBelow == null){
			tileBelow = new Tile(0, 0, 0, false, false);
		}
		return tileBelow;
	}
	
	public Tile getTileAbove(){
		Tile tileAbove = null;
		tileAbove = Game.getGame().getWorld().getTile(x, y - Tile.TILE_SIZE, MapLayer.INFORMATION);
		if (tileAbove == null){
			tileAbove = new Tile(0, 0, 0, false, false);
		}
		return tileAbove;
	}
	
	public Tile getTileLeft(){
		Tile tileLeft = null;
		tileLeft = Game.getGame().getWorld().getTile(x - Tile.TILE_SIZE, y, MapLayer.INFORMATION);
		if (tileLeft == null){
			tileLeft = new Tile(0, 0, 0, false, false);
		}
		return tileLeft;
	}
	
	public Tile getTileRight(){
		Tile tileRight = null;
		tileRight = Game.getGame().getWorld().getTile(x + Tile.TILE_SIZE, y, MapLayer.INFORMATION);
		if (tileRight == null){
			tileRight = new Tile(0, 0, 0, false, false);
		}
		return tileRight;
	}
	
	public ArrayList<Tile> lineOfSight (byte dir){
		
		ArrayList<Tile> sight = new ArrayList<Tile>();
		

		if (dir == EntityInfo.DIR_UP){
			sight.add(Game.getGame().getWorld().getTile(x, y - Tile.TILE_SIZE, MapLayer.INFORMATION));
			sight.add(Game.getGame().getWorld().getTile(x, y - Tile.TILE_SIZE * 2, MapLayer.INFORMATION));
			sight.add(Game.getGame().getWorld().getTile(x, y - Tile.TILE_SIZE * 3, MapLayer.INFORMATION));
			sight.add(Game.getGame().getWorld().getTile(x, y - Tile.TILE_SIZE * 4, MapLayer.INFORMATION));
			sight.add(Game.getGame().getWorld().getTile(x, y - Tile.TILE_SIZE * 5, MapLayer.INFORMATION));
			sight.add(Game.getGame().getWorld().getTile(x - Tile.TILE_SIZE, y, MapLayer.INFORMATION));
			sight.add(Game.getGame().getWorld().getTile(x + Tile.TILE_SIZE, y, MapLayer.INFORMATION));
			sight.add(Game.getGame().getWorld().getTile(x - Tile.TILE_SIZE * 2, y, MapLayer.INFORMATION));
			sight.add(Game.getGame().getWorld().getTile(x + Tile.TILE_SIZE * 2, y, MapLayer.INFORMATION));
		}
		else if (dir == EntityInfo.DIR_LEFT){
			sight.add(Game.getGame().getWorld().getTile(x - Tile.TILE_SIZE, y, MapLayer.INFORMATION));
			sight.add(Game.getGame().getWorld().getTile(x - Tile.TILE_SIZE * 2, y, MapLayer.INFORMATION));
			sight.add(Game.getGame().getWorld().getTile(x - Tile.TILE_SIZE * 3, y, MapLayer.INFORMATION));
			sight.add(Game.getGame().getWorld().getTile(x - Tile.TILE_SIZE * 4, y, MapLayer.INFORMATION));
			sight.add(Game.getGame().getWorld().getTile(x - Tile.TILE_SIZE * 5, y, MapLayer.INFORMATION));
		}
		else if (dir == EntityInfo.DIR_DOWN){
			sight.add(Game.getGame().getWorld().getTile(x, y + Tile.TILE_SIZE, MapLayer.INFORMATION));
			sight.add(Game.getGame().getWorld().getTile(x, y + Tile.TILE_SIZE * 2, MapLayer.INFORMATION));
			sight.add(Game.getGame().getWorld().getTile(x, y + Tile.TILE_SIZE * 3, MapLayer.INFORMATION));
			sight.add(Game.getGame().getWorld().getTile(x, y + Tile.TILE_SIZE * 4, MapLayer.INFORMATION));
			sight.add(Game.getGame().getWorld().getTile(x, y + Tile.TILE_SIZE * 5, MapLayer.INFORMATION));
			sight.add(Game.getGame().getWorld().getTile(x - Tile.TILE_SIZE, y, MapLayer.INFORMATION));
			sight.add(Game.getGame().getWorld().getTile(x + Tile.TILE_SIZE, y, MapLayer.INFORMATION));
			sight.add(Game.getGame().getWorld().getTile(x - Tile.TILE_SIZE * 2, y, MapLayer.INFORMATION));
			sight.add(Game.getGame().getWorld().getTile(x + Tile.TILE_SIZE * 2, y, MapLayer.INFORMATION));
		}
		else if (dir == EntityInfo.DIR_RIGHT){
			sight.add(Game.getGame().getWorld().getTile(x + Tile.TILE_SIZE, y, MapLayer.INFORMATION));
			sight.add(Game.getGame().getWorld().getTile(x + Tile.TILE_SIZE * 2, y, MapLayer.INFORMATION));
			sight.add(Game.getGame().getWorld().getTile(x + Tile.TILE_SIZE * 3, y, MapLayer.INFORMATION));
			sight.add(Game.getGame().getWorld().getTile(x + Tile.TILE_SIZE * 4, y, MapLayer.INFORMATION));
			sight.add(Game.getGame().getWorld().getTile(x + Tile.TILE_SIZE * 5, y, MapLayer.INFORMATION));
		}
		
		for (Tile t : getNeighbors()){
			if (t != null && !sight.contains(t)) sight.add(t);
		}
	    
		
		return sight;
	}
	
	public Rectangle getBounds(){
		return new Rectangle((int)x, (int)y, hitBoxWidth, hitBoxHeight);
	}
	
	public String toString(){
		return x / Tile.TILE_SIZE + ":" + y / Tile.TILE_SIZE;
	}
	
	public int getDistance(Tile end){
		int dx = Math.abs((int)(this.x-end.x));
		int dy = Math.abs((int)(this.y-end.y));
		
		return dx+dy;
	}

	public void setHeightLayer(int layer){
		this.heightLayer = layer;
		this.hitBoxHeight = layer * Tile.TILE_SIZE;
	}

	public int getRenderLayer() {
		return renderLayer;
	}

	public int getTileID() {
		return tileID;
	}

	public int getTileType() {
		return tileType;
	}

	public boolean isBlocked() {
		return blocked;
	}

	public int getAnimationFrameDuration() {
		return animationFrameDuration;
	}

	public int getScore() {
		return score;
	}

	public int getDistance() {
		return distance;
	}

	public boolean isBlocksAbilities() {
		return blocksAbilities;
	}

	public int getHeightLayer() {
		return heightLayer;
	}

	public Tile getParentTile() {
		return parentTile;
	}

	public void setRenderLayer(int renderLayer) {
		this.renderLayer = renderLayer;
	}

	public void setTileID(int tileID) {
		this.tileID = tileID;
	}

	public void setTileType(int tileType) {
		this.tileType = tileType;
	}

	public void setBlocked(boolean isBlocked) {
		this.blocked = isBlocked;
	}

	public void setAnimationFrameDuration(int animationFrameDuration) {
		this.animationFrameDuration = animationFrameDuration;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}

	public void setBlocksAbilities(boolean blocksAbilities) {
		this.blocksAbilities = blocksAbilities;
	}

	public void setParentTile(Tile parentTile) {
		this.parentTile = parentTile;
	}

}
