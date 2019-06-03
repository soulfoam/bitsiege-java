package soulfoam.arena.world.editor;

import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

import soulfoam.arena.main.game.Game;
import soulfoam.arena.main.resources.Res;
import soulfoam.arena.world.editor.gui.BaseEditorGUI;
import soulfoam.arena.world.editor.gui.InformationGUI;
import soulfoam.arena.world.editor.gui.LayerGUI;
import soulfoam.arena.world.editor.gui.LiquidGUI;
import soulfoam.arena.world.editor.gui.ObjectiveGUI;
import soulfoam.arena.world.editor.gui.PlaceableGUI;
import soulfoam.arena.world.editor.gui.TileSetGUI;
import soulfoam.arena.world.editor.gui.WallGUI;

public class MapEditor {

	public TileSetGUI tileSetGUI;
	public LayerGUI layerGUI;
	public InformationGUI informationGUI;
	public PlaceableGUI placeableGUI;
	public ObjectiveGUI objectiveGUI;
	public LiquidGUI liquidGUI;
	public WallGUI wallGUI;

	public boolean editMode;

	public static int opacityOverlay = 0;
	public boolean pressingOpacityButton;

	public ArrayList<BaseEditorGUI> guiObjects = new ArrayList<BaseEditorGUI>();

	public MapEditor() {

		tileSetGUI = new TileSetGUI(7, 6, this);
		informationGUI = new InformationGUI(2, 2, this);
		placeableGUI = new PlaceableGUI(1, 2, this);
		layerGUI = new LayerGUI(218, 2, this);
		wallGUI = new WallGUI(2, 2, this);
		objectiveGUI = new ObjectiveGUI(2, 2, this);
		liquidGUI = new LiquidGUI(2, 2, this);

		guiObjects.add(layerGUI);
		guiObjects.add(informationGUI);
		guiObjects.add(liquidGUI);
		guiObjects.add(tileSetGUI);
		guiObjects.add(wallGUI);
		guiObjects.add(placeableGUI);
		guiObjects.add(objectiveGUI);

		setRenders(informationGUI);

	}

	public void update(GameContainer gc, int delta) {
		if (gc.getInput().isKeyDown(Input.KEY_LSHIFT) && gc.getInput().isKeyDown(Input.KEY_LCONTROL)
				&& gc.getInput().isKeyDown(Input.KEY_LALT) && gc.getInput().isKeyDown(Input.KEY_E)) {
			editMode = true;
			Game.getGame().getHUDDisplay().setShowHUD(false);
			Game.getGame().getClientSettings().setShowBoundsTiles(true);
			Game.getGame().getClientSettings().setShowBoundsMapObjects(true);
			//Game.getGame().getWorld().getMap().createMiniMap();
		}
		if (gc.getInput().isKeyDown(Input.KEY_LSHIFT) && gc.getInput().isKeyDown(Input.KEY_LCONTROL)
				&& gc.getInput().isKeyDown(Input.KEY_LALT) && gc.getInput().isKeyDown(Input.KEY_R)) {
			editMode = false;
			Game.getGame().getHUDDisplay().setShowHUD(true);
			Game.getGame().getClientSettings().setShowBoundsTiles(false);
			Game.getGame().getClientSettings().setShowBoundsMapObjects(false);
			Game.getGame().getWorld().getMap().createMiniMap();
		}
		if (gc.getInput().isKeyDown(Input.KEY_LSHIFT) && gc.getInput().isKeyDown(Input.KEY_LCONTROL)
				&& gc.getInput().isKeyDown(Input.KEY_LALT) && gc.getInput().isKeyDown(Input.KEY_C)) {
			Game.getGame().getHUDDisplay().setShowHUD(true);
			Game.getGame().getWorld().getMap().createMiniMap();
		}

		if (editMode) {
			if (gc.getInput().isKeyDown(Input.KEY_LSHIFT)) {
				pressingOpacityButton = true;
			} else {
				pressingOpacityButton = false;
			}

			if (gc.getInput().isKeyDown(Input.KEY_LSHIFT) && gc.getInput().isKeyDown(Input.KEY_LCONTROL)
					&& gc.getInput().isKeyDown(Input.KEY_S)) {
				Game.getGame().getWorld().saveMap(Game.getGame().getWorld().getMap().getMapName());
			}


			if (gc.getInput().isKeyDown(Input.KEY_LSHIFT) && gc.getInput().isKeyDown(Input.KEY_LCONTROL)
					&& gc.getInput().isKeyDown(Input.KEY_N)) {
				Game.getGame().getWorld().createMap("new");
			}
			
			for (BaseEditorGUI gui : guiObjects) {
				if (gui != null && gui.renderMe) {
					gui.update(gc, delta);
				}
			}
		}

	}

	public void render(GameContainer gc, Graphics g) {
		if (editMode) {
			for (BaseEditorGUI gui : guiObjects) {
				if (gui != null && gui.renderMe) {
					gui.render(gc, g);
				}
			}

			Res.bitFont.drawString(266, 168, "pP: " + Game.getGame().getWorld()
					.getTile(Game.getGame().getPlayer().getX(), Game.getGame().getPlayer().getY()));
			Res.bitFont.drawString(266, 174, "mP: " + Game.getGame().getWorld()
					.getTile(Game.getGame().getInput().getMouseX(), Game.getGame().getInput().getMouseY()));

		}
	}

	public boolean clickingOnGUI(float x, float y) {
		for (BaseEditorGUI gui : guiObjects) {
			if (gui != null && gui.renderMe && !gui.hidden && gui.getBounds().contains(x, y)) {
				return true;
			}
		}

		return false;
	}

	public void setRenders(BaseEditorGUI gui) {
		for (BaseEditorGUI ui : guiObjects) {
			if (ui != null && ui != layerGUI) {
				ui.renderMe = false;
				ui.hidden = false;
			}
		}

		gui.renderMe = true;
		layerGUI.layerIndex = gui.defaultRenderLayer;
	}

	public void keyReleased(int key, char c) {
		for (BaseEditorGUI gui : guiObjects) {
			if (gui != null && gui.renderMe) {
				gui.keyReleased(key, c);
			}
		}
	}

	public void mousePressed(int button, int x, int y) {
		if (editMode) {
			for (BaseEditorGUI gui : guiObjects) {
				if (gui != null && gui.renderMe) {
					gui.mousePressed(button, x, y);
				}
			}
		}
	}

	public void mouseWheelMoved(int m) {
		if (editMode) {
			if (pressingOpacityButton) {
				if (m > 0) {
					opacityOverlay += 25;
					if (opacityOverlay >= 255) {
						opacityOverlay = 255;
					}
				} else if (m < 0) {
					opacityOverlay -= 25;
					if (opacityOverlay <= 0) {
						opacityOverlay = 0;
					}
				}
			} else {
				for (BaseEditorGUI gui : guiObjects) {
					if (gui != null && gui.renderMe) {
						gui.mouseWheelMoved(m);
					}
				}
			}
		}
	}
}
