package soulfoam.arena.world;

import java.util.ArrayList;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Rectangle;

import soulfoam.arena.entities.GameObject;
import soulfoam.arena.main.game.Game;
import soulfoam.arena.main.resources.Res;

public class Tile extends GameObject {

	public final static int TILE_SIZE = 8;

	private int renderLayer;

	private int tileID;
	private int tileType = -1;
	private Image tileImage;

	private boolean isBlocked;
	private int animationFrameDuration;
	private int score = 0;
	private int distance = 0;
	private boolean blocksAbilities;

	private int heightLayer = 1;
	private boolean isAnimated;

	private Animation animation = new Animation();
	private Tile parentTile;

	public Tile(float x, float y, int tileType, boolean isBlocked, boolean blocksAbilities) {

		this.x = x;
		this.y = y;
		this.setTileType(tileType);
		this.setBlocked(isBlocked);
		this.setBlocksAbilities(blocksAbilities);

		width = Tile.TILE_SIZE;
		height = Tile.TILE_SIZE;
		hitBoxWidth = Tile.TILE_SIZE;
		hitBoxHeight = Tile.TILE_SIZE;
	}

	public Tile(float x, float y, int tileID, int animationFrameDuration) {

		this.x = x;
		this.y = y;
		this.setTileID(tileID);
		animation = getLiquidAnimation(animationFrameDuration);
		this.setAnimationFrameDuration(animationFrameDuration);

		isAnimated = true;

		width = Tile.TILE_SIZE;
		height = Tile.TILE_SIZE;
		hitBoxWidth = Tile.TILE_SIZE;
		hitBoxHeight = Tile.TILE_SIZE;

	}

	public Tile(float x, float y, int tileID) {

		this.x = x;
		this.y = y;
		this.setTileID(tileID);

		width = Tile.TILE_SIZE;
		height = Tile.TILE_SIZE;

		hitBoxWidth = Tile.TILE_SIZE;
		hitBoxHeight = Tile.TILE_SIZE;
	}

	public void update(int delta) {
		// if (isAnimated){
		// if (animation.isStopped()){
		// animation = getLiquidAnimation(animationFrameDuration);
		// }
		// }
	}

	public void render(Graphics g) {

		if (!isAnimated) {
			if (tileImage != null) {
				tileImage.draw(Res.roundForRendering(x), Res.roundForRendering(y), Tile.TILE_SIZE, Tile.TILE_SIZE);
			}
		} else {
			if (animation != null && animation.getFrameCount() > 0) {
				animation.draw(Res.roundForRendering(x), Res.roundForRendering(y));
			}
		}
	}

	public Animation getLiquidAnimation(int animationDuration) {
		Animation returnAnim = new Animation();

		if (animationDuration == 0) {
			animationDuration = 100;
		}
		if (getTileID() >= 200 && getTileID() <= 202) {
			returnAnim = new Animation(Res.MAP_RESOURCE.WATER_STILL, animationDuration);
		}
		if (getTileID() >= 210 && getTileID() <= 212) {
			returnAnim = new Animation(Res.MAP_RESOURCE.WATER_UP_FLOW, animationDuration);
		}
		if (getTileID() >= 220 && getTileID() <= 222) {
			returnAnim = new Animation(Res.MAP_RESOURCE.WATER_DOWN_FLOW, animationDuration);
		}
		if (getTileID() >= 230 && getTileID() <= 232) {
			returnAnim = new Animation(Res.MAP_RESOURCE.LAVA_STILL, animationDuration);
		}
		if (getTileID() >= 240 && getTileID() <= 242) {
			returnAnim = new Animation(Res.MAP_RESOURCE.LAVA_UP_FLOW, animationDuration);
		}
		if (getTileID() >= 250 && getTileID() <= 252) {
			returnAnim = new Animation(Res.MAP_RESOURCE.LAVA_DOWN_FLOW, animationDuration);
		}

		return returnAnim;
	}

	public ArrayList<Tile> getNeighborsPF() {

		ArrayList<Tile> neighborList = new ArrayList<Tile>();

		Tile top = null;
		Tile left = null;
		Tile bottom = null;
		Tile right = null;

		if (Game.getGame().getWorld() != null) {
			if (x / Tile.TILE_SIZE <= 0 || x / Tile.TILE_SIZE >= Game.getGame().getWorld().getMap().getMapWidth()
					|| y / Tile.TILE_SIZE <= 0 || y / Tile.TILE_SIZE >= Game.getGame().getWorld().getMap().getMapHeight()) {
				return neighborList;
			} else {

				if (!(y <= 8)) {
					top = Game.getGame().getWorld().getTile(x, y - Tile.TILE_SIZE, MapLayer.INFORMATION);
				}
				if (!(x <= 8)) {
					left = Game.getGame().getWorld().getTile(x - Tile.TILE_SIZE, y, MapLayer.INFORMATION);
				}
				if (!(y >= Game.getGame().getWorld().getMap().getMapHeight() * Tile.TILE_SIZE - Tile.TILE_SIZE)) {
					bottom = Game.getGame().getWorld().getTile(x, y + Tile.TILE_SIZE, MapLayer.INFORMATION);
				}
				if (!(x >= Game.getGame().getWorld().getMap().getMapWidth() * Tile.TILE_SIZE - Tile.TILE_SIZE)) {
					right = Game.getGame().getWorld().getTile(x + Tile.TILE_SIZE, y, MapLayer.INFORMATION);
				}

				if (top != null) {
					neighborList.add(top);
				}
				if (left != null) {
					neighborList.add(left);
				}
				if (bottom != null) {
					neighborList.add(bottom);
				}
				if (right != null) {
					neighborList.add(right);
				}

				return neighborList;
			}
		}

		return neighborList;
	}

	public ArrayList<Tile> getNeighbors() {

		ArrayList<Tile> neighborList = new ArrayList<Tile>();

		Tile top = null;
		Tile left = null;
		Tile bottom = null;
		Tile right = null;
		Tile topLeft = null;
		Tile topRight = null;
		Tile bottomLeft = null;
		Tile bottomRight = null;

		if (x / Tile.TILE_SIZE <= 0 || x / Tile.TILE_SIZE >= Game.getGame().getWorld().getMap().getMapWidth()
				|| y / Tile.TILE_SIZE <= 0 || y / Tile.TILE_SIZE >= Game.getGame().getWorld().getMap().getMapHeight()) {
			return neighborList;
		} else {

			if (!(y <= 8)) {
				top = Game.getGame().getWorld().getTile(x, y - Tile.TILE_SIZE, MapLayer.INFORMATION);
				topLeft = Game.getGame().getWorld().getTile(x - Tile.TILE_SIZE, y - Tile.TILE_SIZE,
						MapLayer.INFORMATION);
			}
			if (!(x <= 8)) {
				left = Game.getGame().getWorld().getTile(x - Tile.TILE_SIZE, y, MapLayer.INFORMATION);
				bottomLeft = Game.getGame().getWorld().getTile(x - Tile.TILE_SIZE, y + Tile.TILE_SIZE,
						MapLayer.INFORMATION);
			}
			if (!(y >= Game.getGame().getWorld().getMap().getMapHeight() * Tile.TILE_SIZE - Tile.TILE_SIZE)) {
				bottom = Game.getGame().getWorld().getTile(x, y + Tile.TILE_SIZE, MapLayer.INFORMATION);
				bottomRight = Game.getGame().getWorld().getTile(x + Tile.TILE_SIZE, y + Tile.TILE_SIZE,
						MapLayer.INFORMATION);
			}
			if (!(x >= Game.getGame().getWorld().getMap().getMapWidth() * Tile.TILE_SIZE - Tile.TILE_SIZE)) {
				right = Game.getGame().getWorld().getTile(x + Tile.TILE_SIZE, y, MapLayer.INFORMATION);
				topRight = Game.getGame().getWorld().getTile(x + Tile.TILE_SIZE, y + Tile.TILE_SIZE,
						MapLayer.INFORMATION);
			}

			if (top != null) {
				neighborList.add(top);
			}
			if (left != null) {
				neighborList.add(left);
			}
			if (bottom != null) {
				neighborList.add(bottom);
			}
			if (right != null) {
				neighborList.add(right);
			}

			if (topLeft != null) {
				neighborList.add(topLeft);
			}
			if (bottomLeft != null) {
				neighborList.add(bottomLeft);
			}
			if (bottomRight != null) {
				neighborList.add(bottomRight);
			}
			if (topRight != null) {
				neighborList.add(topRight);
			}

			return neighborList;
		}

	}

	public Tile getTileBelow() {
		Tile tileBelow = null;
		tileBelow = Game.getGame().getWorld().getTile(x, y + Tile.TILE_SIZE, MapLayer.INFORMATION);
		if (tileBelow == null) {
			tileBelow = new Tile(0, 0, 0, false, false);
		}
		return tileBelow;
	}

	public Tile getTileAbove() {
		Tile tileAbove = null;
		tileAbove = Game.getGame().getWorld().getTile(x, y - Tile.TILE_SIZE, MapLayer.INFORMATION);
		if (tileAbove == null) {
			tileAbove = new Tile(0, 0, 0, false, false);
		}
		return tileAbove;
	}

	public Tile getTileLeft() {
		Tile tileLeft = null;
		tileLeft = Game.getGame().getWorld().getTile(x - Tile.TILE_SIZE, y, MapLayer.INFORMATION);
		if (tileLeft == null) {
			tileLeft = new Tile(0, 0, 0, false, false);
		}
		return tileLeft;
	}

	public Tile getTileRight() {
		Tile tileRight = null;
		tileRight = Game.getGame().getWorld().getTile(x + Tile.TILE_SIZE, y, MapLayer.INFORMATION);
		if (tileRight == null) {
			tileRight = new Tile(0, 0, 0, false, false);
		}
		return tileRight;
	}

	public Rectangle getBounds() {
		return new Rectangle((int) x, (int) y, hitBoxWidth, hitBoxHeight);
	}

	public String toString() {
		return x / Tile.TILE_SIZE + ":" + y / Tile.TILE_SIZE;
	}

	public int getDistance(Tile end) {
		int dx = Math.abs((int) (x - end.x));
		int dy = Math.abs((int) (y - end.y));

		return dx + dy;
	}

	public void setHeightLayer(int layer) {
		heightLayer = layer;
		hitBoxHeight = layer * Tile.TILE_SIZE;
	}

	public boolean isBlocked() {
		return isBlocked;
	}

	public void setBlocked(boolean isBlocked) {
		this.isBlocked = isBlocked;
	}

	public int getTileType() {
		return tileType;
	}

	public void setTileType(int tileType) {
		this.tileType = tileType;
	}

	public int getTileID() {
		return tileID;
	}

	public void setTileID(int tileID) {
		this.tileID = tileID;
		tileImage = Res.MAP_RESOURCE.getTileResourceByID(tileID).tileImage;
	}

	public int getHeightLayer() {
		return heightLayer;
	}

	public boolean isBlocksAbilities() {
		return blocksAbilities;
	}

	public void setBlocksAbilities(boolean blocksAbilities) {
		this.blocksAbilities = blocksAbilities;
	}

	public int getAnimationFrameDuration() {
		return animationFrameDuration;
	}

	public void setAnimationFrameDuration(int animationFrameDuration) {
		this.animationFrameDuration = animationFrameDuration;
	}

}
