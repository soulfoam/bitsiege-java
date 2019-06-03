package soulfoam.arena.world;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

import soulfoam.arena.entities.GameObject;
import soulfoam.arena.main.resources.Res;

public class MapObject extends GameObject {

	public int id;
	public int groupID;
	public Image objectImage;

	public int mapLayer = MapLayer.BACK;

	public MapObject(float x, float y, int id, int mapLayer) {

		MapObject libraryObject = Res.MAP_RESOURCE.getMapObjectByID(id);
		this.id = id;
		objectImage = libraryObject.objectImage;
		this.x = x;
		this.y = y;
		width = libraryObject.width;
		height = libraryObject.height;
		hitBoxWidth = libraryObject.hitBoxWidth;
		hitBoxHeight = libraryObject.hitBoxHeight;
		this.mapLayer = mapLayer;
	}

	public MapObject(Image objectImage, float x, float y, int id, int groupID, float width, float height,
			float hitBoxWidth, float hitBoxHeight) { // always loading here for
														// library resources
		this.id = id;
		this.objectImage = objectImage;
		this.groupID = groupID;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.hitBoxWidth = hitBoxWidth;
		this.hitBoxHeight = hitBoxHeight;
	}

	public void update(int delta) {

	}

	public void render(Graphics g) {
		if (objectImage != null) {
			objectImage.draw(x, y, width, height);
		}
	}

	public void render(Graphics g, float x, float y) {
		if (objectImage != null) {
			objectImage.draw(x, y, width, height);
		}
	}

	public void render(Graphics g, float x, float y, float width, float height) {
		if (objectImage != null) {
			objectImage.draw(x, y, width, height);
		}
	}

	public Shape getBounds() {
		return new Rectangle(x, y, hitBoxWidth, hitBoxHeight);
	}

	public Shape getBounds(float x, float y) {
		return new Rectangle(x, y, hitBoxWidth, hitBoxHeight);
	}

	public Shape getBounds(float x, float y, float width, float height) {
		return new Rectangle(x, y, width, height);
	}
}
