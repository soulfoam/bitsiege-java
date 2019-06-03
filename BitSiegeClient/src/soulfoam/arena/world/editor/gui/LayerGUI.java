package soulfoam.arena.world.editor.gui;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

import soulfoam.arena.main.game.Game;
import soulfoam.arena.main.gfx.HUDButton;
import soulfoam.arena.main.resources.Res;
import soulfoam.arena.world.MapLayer;
import soulfoam.arena.world.editor.MapEditor;

public class LayerGUI extends BaseEditorGUI {

	public HUDButton guiBack;
	public HUDButton guiForward;

	public HUDButton layerBack;
	public HUDButton layerForward;

	public HUDButton heightBack;
	public HUDButton heightForward;

	public HUDButton hide;

	public BaseEditorGUI currentGUI;
	public int currentGUIIndex;

	public int layerIndex;
	public int heightLayer = 1;

	public boolean ctrlDown;
	public boolean cDown;

	public LayerGUI(float x, float y, MapEditor editor) {
		this.x = x;
		this.y = y;
		this.editor = editor;
		width = 100;
		height = 17;

		guiBack = new HUDButton("<", x, y, 6, 5);
		guiForward = new HUDButton(">", x, y, 6, 5);

		layerBack = new HUDButton("<", x, y, 6, 5);
		layerForward = new HUDButton(">", x, y, 6, 5);

		heightBack = new HUDButton("<", x, y, 6, 5);
		heightForward = new HUDButton(">", x, y, 6, 5);

		hide = new HUDButton("X", x, y, 6, 5);
		renderMe = true;

		currentGUI = editor.informationGUI;
	}

	public void update(GameContainer gc, int delta) {

		if (gc.getInput().isKeyDown(Input.KEY_LCONTROL)) {
			ctrlDown = true;
		} else {
			ctrlDown = false;
		}

		if (gc.getInput().isKeyDown(Input.KEY_C)) {
			cDown = true;
		} else {
			cDown = false;
		}

		guiBack.update(gc);
		guiForward.update(gc);

		layerBack.update(gc);
		layerForward.update(gc);

		heightBack.update(gc);
		heightForward.update(gc);

		hide.update(gc);

		if (guiBack.isClicked()) {
			setGUI(false);
		}

		if (guiForward.isClicked()) {
			setGUI(true);
		}

		if (layerBack.isClicked()) {
			setLayer(false);
		}

		if (layerForward.isClicked()) {
			setLayer(true);
		}

		if (heightBack.isClicked()) {
			setHeight(false);
		}

		if (heightForward.isClicked()) {
			setHeight(true);
		}

		if (hide.isClicked()) {
			currentGUI.hidden = !currentGUI.hidden;
		}

		if (layerIndex == MapLayer.YSORTED) {
			height = 25;
		} else {
			height = 17;
		}

		checkForPickUp(gc);

		setButtons();
	}

	public void render(GameContainer gc, Graphics g) {
		g.setColor(new Color(8, 103, 170, 150));
		g.fillRect(x, y, width, height);

		g.setColor(Color.white);
		g.drawRect(x, y, width, height);

		Res.bitFont.drawString(x + 2, y + 2, "GUI: " + currentGUI.title);

		Res.bitFont.drawString(x + 2, y + 10, "Layer: " + getRenderLayerName());

		if (layerIndex == MapLayer.YSORTED) {
			Res.bitFont.drawString(x + 2, y + 18, "Height Layer: " + heightLayer);
			heightBack.render(g, 0, 0);
			heightForward.render(g, 1, 0);
		}

		guiBack.render(g, 0, 0);
		guiForward.render(g, 1, 0);

		layerBack.render(g, 0, 0);
		layerForward.render(g, 1, 0);

		hide.render(g);

	}

	public Shape getBounds() {
		return new Rectangle(x, y, width, height);
	}

	public void setGUI(boolean add) {
		if (!add) {
			currentGUIIndex--;
			if (currentGUIIndex < 1) {
				currentGUIIndex = editor.guiObjects.size() - 1;
			}
			currentGUI = editor.guiObjects.get(currentGUIIndex);
			editor.setRenders(currentGUI);
		} else {
			currentGUIIndex++;
			if (currentGUIIndex == editor.guiObjects.size()) {
				currentGUIIndex = 1;
			}
			currentGUI = editor.guiObjects.get(currentGUIIndex);
			editor.setRenders(currentGUI);
		}
	}

	public void setLayer(boolean add) {
		if (!add) {
			layerIndex--;
			if (layerIndex < 0) {
				layerIndex = 5;
			}
		} else {
			layerIndex++;
			if (layerIndex > 5) {
				layerIndex = 0;
			}
		}
	}

	public void setHeight(boolean add) {
		if (!add) {
			heightLayer--;
			if (heightLayer <= 1) {
				heightLayer = 1;
			}
		} else {
			heightLayer++;
		}
	}

	public void setButtons() {
		guiBack.setX(x + 75);
		guiBack.setY(y + 2);

		guiForward.setX(x + 82);
		guiForward.setY(y + 2);

		layerBack.setX(x + 75);
		layerBack.setY(y + 10);

		layerForward.setX(x + 82);
		layerForward.setY(y + 10);

		heightBack.setX(x + 75);
		heightBack.setY(y + 18);

		heightForward.setX(x + 82);
		heightForward.setY(y + 18);

		hide.setX(x + 92);
		hide.setY(y + 2);

		if (currentGUI != null) {
			if (currentGUI.hidden) {
				hide.setButtonColor(new Color(9, 183, 25));
			} else {
				hide.setButtonColor(new Color(183, 29, 9));
			}
		}
	}

	public String getRenderLayerName() {
		if (layerIndex == MapLayer.INFORMATION) {
			return "Info(0)";
		}
		if (layerIndex == MapLayer.LIQUID) {
			return "Liquid(1)";
		}
		if (layerIndex == MapLayer.GROUND) {
			return "Ground(2)";
		}
		if (layerIndex == MapLayer.BACK) {
			return "Back(3)";
		}
		if (layerIndex == MapLayer.YSORTED) {
			return "Y-Sorted(4)";
		}
		if (layerIndex == MapLayer.ABOVEALL) {
			return "Above(5)";
		}
		return "";
	}

	public void mousePressed(int button, int x, int y) {

	}

	public void keyReleased(int key, char c) {
		if (editor.editMode) {
			if (key == Input.KEY_SPACE) {
				currentGUI.hidden = !currentGUI.hidden;
			}
			if (key == Input.KEY_LALT) {
				Game.getGame().getClientSettings()
						.setShowBoundsMapObjects(!Game.getGame().getClientSettings().isShowBoundsMapObjects());
				Game.getGame().getClientSettings()
						.setShowBoundsTiles(!Game.getGame().getClientSettings().isShowBoundsTiles());
			}
		}
	}

	public void mouseWheelMoved(int m) {
		if (cDown) {

			if (m > 0) {
				setHeight(true);
			} else if (m < 0) {
				setHeight(false);
			}
		} else if (ctrlDown) {
			if (m > 0) {
				setLayer(true);
			} else if (m < 0) {
				setLayer(false);
			}
		} else {
			if (m > 0) {
				setGUI(true);
			} else if (m < 0) {
				setGUI(false);
			}
		}

	}

}
