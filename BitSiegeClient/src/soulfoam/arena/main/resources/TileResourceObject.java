package soulfoam.arena.main.resources;

import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

import soulfoam.arena.world.Tile;

public class TileResourceObject {

	public int id;
	public float x, y;
	public Image tileImage;
	public int groupID;
	public int row;

	public TileResourceObject(int id, float x, float y, int groupID, Image tileImage) {
		this.id = id;
		this.x = x;
		this.y = y;
		this.tileImage = tileImage;
		this.groupID = groupID;
	}

	public Shape getBounds(float x, float y) {
		return new Rectangle(this.x + x, this.y + y, Tile.TILE_SIZE, Tile.TILE_SIZE);
	}
}
