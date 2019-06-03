package soulfoam.arena.world.editor.gui;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

import soulfoam.arena.world.editor.MapEditor;

public abstract class BaseEditorGUI {

	public float x, y, width, height;
	public int groupID;

	public String title;
	public int defaultRenderLayer;

	public int mouseX, mouseY;

	public boolean isPickedUp;

	public boolean hidden;
	public boolean renderMe;

	public MapEditor editor;

	public abstract void update(GameContainer gc, int delta);

	public abstract void render(GameContainer gc, Graphics g);

	public abstract void mousePressed(int button, int x, int y);

	public abstract void keyReleased(int key, char c);

	public abstract void mouseWheelMoved(int m);

	public void checkForPickUp(GameContainer gc) {
		if (renderMe && !hidden) {
			if (gc.getInput().isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
				if (isPickedUp) {
					isPickedUp = false;
					return;
				}
			}
			if (gc.getInput().isMouseButtonDown(Input.MOUSE_RIGHT_BUTTON)) {
				if (getBounds().contains(gc.getInput().getMouseX(), gc.getInput().getMouseY())) {
					isPickedUp = true;
					return;
				}
			}

			if (isPickedUp) {
				x = gc.getInput().getMouseX() - width / 2;
				y = gc.getInput().getMouseY() - height / 2;
			}
		}
	}

	public Shape getBounds() {
		return new Rectangle(x, y, width, height);
	}
}
