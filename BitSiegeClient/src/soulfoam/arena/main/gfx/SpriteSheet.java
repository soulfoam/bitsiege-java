package soulfoam.arena.main.gfx;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import soulfoam.arena.main.resources.TileResourceObject;
import soulfoam.arena.world.MapObject;
import soulfoam.arena.world.Tile;

public class SpriteSheet {

	private Image spriteSheet;

	public SpriteSheet(String path) {
		try {
			spriteSheet = new Image(path, false);
			spriteSheet.setFilter(GL11.GL_NEAREST);
		} catch (SlickException e) {
			System.out.println("Sprite sheet could not be loaded :(");
			e.printStackTrace();
		}
	}

	public Image grabImage(int col, int row, int width, int height) {

		Image returnImage = spriteSheet.getSubImage(col * width, row * height, width, height);
		return returnImage;

	}

	public Image grabImageTileSize(int col, int row, int width, int height) {

		Image returnImage = spriteSheet.getSubImage(col * Tile.TILE_SIZE, row * Tile.TILE_SIZE, width, height);
		return returnImage;

	}

	public Image[] grabRow(int row, int width, int height, int framesToGrab) {

		Image[] returnImages = new Image[framesToGrab];

		for (int i = 0; i < framesToGrab; i++) {
			returnImages[i] = spriteSheet.getSubImage(i * width, row * height, width, height);
		}

		return returnImages;

	}

	public Image[] grabRow(int row, int width, int height) {

		int framesToGrab = 6;
		Image[] returnImages = new Image[framesToGrab];

		for (int i = 0; i < framesToGrab; i++) {
			returnImages[i] = spriteSheet.getSubImage(i * width, row * height, width, height);
		}

		return returnImages;

	}

	public MapObject grabMapObject(int col, int row, int width, int height, float hitBoxWidth, float hitBoxHeight,
			int id, int groupID) {

		Image objectImage = spriteSheet.getSubImage(col * Tile.TILE_SIZE, row * Tile.TILE_SIZE, width, height);

		MapObject returnObject = new MapObject(objectImage, col * Tile.TILE_SIZE, row * Tile.TILE_SIZE, id, groupID,
				width, height, hitBoxWidth, hitBoxHeight);

		return returnObject;

	}

	public ArrayList<TileResourceObject> grabTileRow(int row, int tilesToGrab) {

		int width = Tile.TILE_SIZE;
		int height = Tile.TILE_SIZE;

		ArrayList<TileResourceObject> returnList = new ArrayList<TileResourceObject>();

		for (int i = 0; i < tilesToGrab; i++) {
			returnList.add(new TileResourceObject(-1, i * width, row * height, -1,
					spriteSheet.getSubImage(i * width, row * height, width, height)));
		}

		return returnList;

	}

	public Image[] grabTileRowImage(int row, int tilesToGrab) {

		int width = Tile.TILE_SIZE;
		int height = Tile.TILE_SIZE;

		Image[] returnImages = new Image[tilesToGrab];

		for (int i = 0; i < tilesToGrab; i++) {
			returnImages[i] = new TileResourceObject(-1, i * width, row * height, -1,
					spriteSheet.getSubImage(i * width, row * height, width, height)).tileImage;
		}

		return returnImages;

	}

	public Image[] grabCharacter(int row) {
		int characterAnimCount = 15;
		Image[] returnImages = new Image[characterAnimCount];

		for (int i = 0; i < characterAnimCount; i++) {
			returnImages[i] = spriteSheet.getSubImage(Tile.TILE_SIZE * i, row * Tile.TILE_SIZE, 32, 32);
		}

		return returnImages;
	}

	public Image[] grabCharacter(int row, int width, int height, int characterAnimCount) {

		Image[] returnImages = new Image[characterAnimCount];

		for (int i = 0; i < characterAnimCount; i++) {
			returnImages[i] = spriteSheet.getSubImage(width * i, row * height, width, height);
		}

		return returnImages;
	}

	public Image[] grabCharacter(int row, int width, int height, int characterAnimCount, boolean flipped) {

		Image[] returnImages = new Image[characterAnimCount];

		for (int i = 0; i < characterAnimCount; i++) {
			returnImages[i] = spriteSheet.getSubImage(width * i, row * height, width, height).getFlippedCopy(flipped, false);
		}

		return returnImages;
	}
	
	public Image[] grabCharacter(int row, int characterAnimCount) {

		Image[] returnImages = new Image[characterAnimCount];

		for (int i = 0; i < characterAnimCount; i++) {
			returnImages[i] = spriteSheet.getSubImage(Tile.TILE_SIZE * i, row * Tile.TILE_SIZE, Tile.TILE_SIZE,
					Tile.TILE_SIZE * 2);
		}

		return returnImages;
	}

	public Image getSheet() {

		return spriteSheet;

	}

}
