package soulfoam.arena.world.editor.gui;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

import soulfoam.arena.main.game.Game;
import soulfoam.arena.main.resources.Res;
import soulfoam.arena.main.resources.TileResourceObject;
import soulfoam.arena.world.MapLayer;
import soulfoam.arena.world.Tile;
import soulfoam.arena.world.editor.MapEditor;

public class LiquidGUI extends BaseEditorGUI {

	public ArrayList<TileResourceObject> selectedTiles = new ArrayList<TileResourceObject>();
	public ArrayList<TileResourceObject> tileLibrary = new ArrayList<TileResourceObject>();
	public boolean clearSelectedTiles;

	public int animationSpeed = 100;

	public LiquidGUI(float x, float y, MapEditor editor) {
		this.x = x;
		this.y = y;
		this.editor = editor;
		title = "Liquid";
		defaultRenderLayer = MapLayer.LIQUID;
		initStandardSet();
	}

	public void initStandardSet() {
		groupID = MapLayer.GROUP_STANDARD_LIQUID;
		width = Tile.TILE_SIZE * 10;
		height = Tile.TILE_SIZE * 6;

		for (TileResourceObject t : Res.MAP_RESOURCE.TILE_LIBRARY) {
			if (t.groupID == groupID) {
				tileLibrary.add(t);
			}
		}
	}

	public void update(GameContainer gc, int delta) {
		animationSpeed = 300;
		checkForPickUp(gc);

		mouseX = (int) (gc.getInput().getMouseX() + Game.getGame().getCam().getX());
		mouseY = (int) (gc.getInput().getMouseY() + Game.getGame().getCam().getY());

		if (editor.clickingOnGUI(gc.getInput().getMouseX(), gc.getInput().getMouseY())) {
			for (TileResourceObject t : tileLibrary) {
				if (t.getBounds(x, y).contains(gc.getInput().getMouseX(), gc.getInput().getMouseY())) {
					if (gc.getInput().isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {

						if (clearSelectedTiles) {
							selectedTiles.clear();
							clearSelectedTiles = false;
						}

						if (gc.getInput().isKeyDown(Input.KEY_LCONTROL)) {
							if (!selectedTiles.contains(t)) {
								selectedTiles.add(t);
							}
							return;
						}

						if (gc.getInput().isKeyDown(Input.KEY_LSHIFT)) {
							if (selectedTiles.contains(t)) {
								selectedTiles.remove(t);
							}
							return;
						}

						selectedTiles.clear();
						if (!selectedTiles.contains(t)) {
							selectedTiles.add(t);
						}

					}
				}
			}
		} else {
			if (gc.getInput().isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {

				if (selectedTiles.size() > 1) {
					ArrayList<Tile> oldTiles = new ArrayList<Tile>();
					ArrayList<Tile> oldTilesGround = new ArrayList<Tile>();

					for (TileResourceObject tro : selectedTiles) {
						oldTiles.add(Game.getGame().getWorld().getTile((int) (mouseX + tro.x - getSTWidth() / 2),
								(int) (mouseY + tro.y - getSTHeight()), editor.layerGUI.layerIndex));
						oldTilesGround
								.add(Game.getGame().getWorld().getTile((int) (mouseX + tro.x - getSTWidth() / 2),
										(int) (mouseY + tro.y - getSTHeight()), MapLayer.GROUND));

						Tile[] oldTilesTempList = new Tile[oldTiles.size()];
						oldTiles.toArray(oldTilesTempList);
						for (Tile ot : oldTilesTempList) {
							if (ot != null) {
								Game.getGame().getWorld().setTile(ot,
										new Tile(ot.getX(), ot.getY(), tro.id, animationSpeed),
										editor.layerGUI.layerIndex);
								oldTiles.remove(ot);
							}
						}

						Tile[] oldTilesTempListGround = new Tile[oldTilesGround.size()];
						oldTilesGround.toArray(oldTilesTempListGround);
						for (Tile ot : oldTilesTempListGround) {
							if (ot != null) {
								Game.getGame().getWorld().setTile(ot, new Tile(ot.getX(), ot.getY(), -1, 1),
										MapLayer.GROUND);
								oldTiles.remove(ot);

							}
						}
					}
				} else {
					Tile ot = Game.getGame().getWorld().getTile(mouseX, mouseY, editor.layerGUI.layerIndex);
					if (!selectedTiles.isEmpty()) {
						Game.getGame().getWorld().setTile(ot,
								new Tile(ot.getX(), ot.getY(), selectedTiles.get(0).id, animationSpeed),
								editor.layerGUI.layerIndex);
					}
					Game.getGame().getWorld().setTile(ot, new Tile(ot.getX(), ot.getY(), -1, animationSpeed),
							MapLayer.GROUND);
					if (gc.getInput().isKeyDown(Input.KEY_LSHIFT)) {
						for (Tile t : Game.getGame().getWorld()
								.getTile(mouseX, mouseY, editor.layerGUI.layerIndex).getNeighbors()) {
							Game.getGame().getWorld().setTile(t,
									new Tile(t.getX(), t.getY(), selectedTiles.get(0).id, animationSpeed),
									editor.layerGUI.layerIndex);
							Game.getGame().getWorld().setTile(t, new Tile(t.getX(), t.getY(), -1, animationSpeed),
									MapLayer.GROUND);
						}
					}

				}
			}

			if (gc.getInput().isMouseButtonDown(Input.MOUSE_RIGHT_BUTTON)) {
				if (selectedTiles.size() > 1) {

					ArrayList<Tile> oldTiles = new ArrayList<Tile>();

					for (TileResourceObject tro : selectedTiles) {
						oldTiles.add(Game.getGame().getWorld().getTile((int) (mouseX + tro.x - getSTWidth() / 2),
								(int) (mouseY + tro.y - getSTHeight()), editor.layerGUI.layerIndex));

						Tile[] oldTilesTempList = new Tile[oldTiles.size()];
						oldTiles.toArray(oldTilesTempList);
						for (Tile ot : oldTilesTempList) {
							if (ot != null) {
								Game.getGame().getWorld().setTile(ot, new Tile(ot.getX(), ot.getY(), -1, 1),
										editor.layerGUI.layerIndex);
								oldTiles.remove(ot);
							}
						}
					}
				} else {
					if (!selectedTiles.isEmpty()) {
						Tile ot = Game.getGame().getWorld().getTile(mouseX, mouseY, editor.layerGUI.layerIndex);
						Game.getGame().getWorld().setTile(ot, new Tile(ot.getX(), ot.getY(), -1, 1),
								editor.layerGUI.layerIndex);
					}
				}
			}

			if (gc.getInput().isMouseButtonDown(Input.MOUSE_MIDDLE_BUTTON)) {
				selectedTiles.clear();

				selectedTiles.add(Res.MAP_RESOURCE.getTileResourceByID(
						Game.getGame().getWorld().getTile(mouseX, mouseY, editor.layerGUI.layerIndex).getTileID()));
				clearSelectedTiles = true;
			}

		}

	}

	public void render(GameContainer gc, Graphics g) {

		if (!hidden) {
			g.setColor(new Color(8, 103, 170, 150));
			g.fillRect(x - 1, y - 1, width + 2, height + 2);

			g.setColor(Color.white);
			g.drawRect(x - 1, y - 1, width + 2, height + 2);

			for (TileResourceObject t : tileLibrary) {

				t.tileImage.draw(x + t.x, y + t.y);

			}
		}

		for (TileResourceObject tro : selectedTiles) {
			if (!hidden) {
				g.setColor(Color.green);
				g.draw(tro.getBounds(x, y));
			}

			if (selectedTiles.size() > 1) {
				float largestWidth = 0, largestHeight = 0, lowestWidth = 0, lowestHeight = 0;
				for (int i = 0; i < selectedTiles.size(); i++) {
					if (selectedTiles.get(i).x > largestWidth) {
						largestWidth = selectedTiles.get(i).x;
					}
					if (selectedTiles.get(i).x < lowestWidth) {
						lowestWidth = selectedTiles.get(i).x;
					}
					if (selectedTiles.get(i).y > largestHeight) {
						largestHeight = selectedTiles.get(i).y;
					}
					if (selectedTiles.get(i).y < lowestHeight) {
						lowestHeight = selectedTiles.get(i).y;
					}
				}

				largestWidth = largestWidth - lowestWidth;
				largestHeight = largestHeight - lowestHeight;

				tro.tileImage.draw(gc.getInput().getMouseX() + tro.x - largestWidth / 2,
						gc.getInput().getMouseY() + tro.y - largestHeight);
			} else {
				if (tro != null && tro.tileImage != null){
					tro.tileImage.draw(gc.getInput().getMouseX() - 2, gc.getInput().getMouseY() - 2);
				}
			}
		}
	}

	public Shape getBounds() {
		return new Rectangle(x - 1, y - 1, width + 1, height + 1);
	}

	public float getSTWidth() {
		float largestWidth = 0, lowestWidth = 0;
		for (int i = 0; i < selectedTiles.size(); i++) {
			if (selectedTiles.get(i).x > largestWidth) {
				largestWidth = selectedTiles.get(i).x;
			}
			if (selectedTiles.get(i).x < lowestWidth) {
				lowestWidth = selectedTiles.get(i).x;
			}

		}

		return largestWidth - lowestWidth;
	}

	public float getSTHeight() {
		float largestHeight = 0, lowestHeight = 0;
		for (int i = 0; i < selectedTiles.size(); i++) {

			if (selectedTiles.get(i).y > largestHeight) {
				largestHeight = selectedTiles.get(i).y;
			}
			if (selectedTiles.get(i).y < lowestHeight) {
				lowestHeight = selectedTiles.get(i).y;
			}
		}

		return largestHeight - lowestHeight;
	}

	public void mousePressed(int button, int x, int y) {

	}

	public void keyReleased(int key, char c) {
		// TODO Auto-generated method stub

	}

	public void mouseWheelMoved(int m) {
		// TODO Auto-generated method stub

	}
}
