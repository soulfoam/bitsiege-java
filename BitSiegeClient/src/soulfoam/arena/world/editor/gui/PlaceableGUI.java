package soulfoam.arena.world.editor.gui;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

import soulfoam.arena.main.game.Game;
import soulfoam.arena.main.resources.Res;
import soulfoam.arena.world.MapLayer;
import soulfoam.arena.world.MapObject;
import soulfoam.arena.world.editor.MapEditor;

public class PlaceableGUI extends BaseEditorGUI {

	public MapObject selectedMapObject;
	public ArrayList<MapObject> MAP_OBJECT_LIBRARY = new ArrayList<MapObject>();

	public PlaceableGUI(float x, float y, MapEditor editor) {
		this.x = x;
		this.y = y;
		this.editor = editor;
		title = "Placeables";
		defaultRenderLayer = MapLayer.YSORTED;
		initSandObjects();
	}

	public void initSandObjects() {
		MAP_OBJECT_LIBRARY.addAll(Res.MAP_RESOURCE.getMapObjectsByGroupID(MapLayer.GROUP_STANDARD_OBJECT));
		width = Res.MAP_RESOURCE.getStandardTilesPlaceable().getSheet().getWidth();
		height = Res.MAP_RESOURCE.getStandardTilesPlaceable().getSheet().getHeight();
	}

	public void update(GameContainer gc, int delta) {
		checkForPickUp(gc);
		mouseX = (int) (gc.getInput().getMouseX() + Game.getGame().getCam().getX());
		mouseY = (int) (gc.getInput().getMouseY() + Game.getGame().getCam().getY());

	}

	public void render(GameContainer gc, Graphics g) {
		if (!hidden) {
			g.setColor(new Color(8, 103, 170, 200));
			g.fillRect(x, y, width, height);

			g.setColor(Color.white);
			g.drawRect(x, y, width, height);

			for (MapObject o : MAP_OBJECT_LIBRARY) {
				if (o != null) {
					o.render(g, x + o.getX(), y + o.getY());
				}
			}

		}

		if (selectedMapObject != null) {
			selectedMapObject.render(g, gc.getInput().getMouseX() - selectedMapObject.getWidth() / 2,
					gc.getInput().getMouseY() - selectedMapObject.getHeight() / 2);
		}
	}

	public void mousePressed(int button, int x, int y) {
		;
		if (!editor.clickingOnGUI(x, y)) {
			if (button == Input.MOUSE_LEFT_BUTTON && selectedMapObject != null) {
				Game.getGame().getWorld().addMapObject(mouseX - selectedMapObject.getWidth() / 2,
						mouseY - selectedMapObject.getHeight() / 2, selectedMapObject.id, editor.layerGUI.layerIndex);
			}
			if (button == Input.MOUSE_RIGHT_BUTTON) {
				MapObject[] objectsTempList = new MapObject[Game.getGame().getWorld().getMap().getMapObjects().size()];
				Game.getGame().getWorld().getMap().getMapObjects().toArray(objectsTempList);
				for (MapObject o : objectsTempList) {
					if (o != null && o.getBounds().contains(mouseX, mouseY)) {
						Game.getGame().getWorld().getMap().getMapObjects().remove(o);
						return;
					}
				}
			}
			if (button == Input.MOUSE_MIDDLE_BUTTON) {
				for (MapObject o : Game.getGame().getWorld().getMap().getMapObjects()) {
					if (o != null && o.getBounds().contains(mouseX, mouseY)) {
						selectedMapObject = o;
						return;
					}
				}
			}
		} else {
			for (MapObject o : MAP_OBJECT_LIBRARY) {
				if (o.getBounds(this.x + o.getX(), this.y + o.getY()).contains(x, y)) {
					selectedMapObject = o;
				}
			}
		}
	}

	public void keyReleased(int key, char c) {

	}

	public void mouseWheelMoved(int m) {
		// TODO Auto-generated method stub

	}

}
