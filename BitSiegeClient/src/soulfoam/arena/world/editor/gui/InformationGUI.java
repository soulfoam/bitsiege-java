package soulfoam.arena.world.editor.gui;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

import soulfoam.arena.main.game.Game;
import soulfoam.arena.main.gfx.HUDButton;
import soulfoam.arena.world.MapLayer;
import soulfoam.arena.world.Tile;
import soulfoam.arena.world.editor.MapEditor;

public class InformationGUI extends BaseEditorGUI {

	public HUDButton blockMovementButton;
	public HUDButton blockAbilitiesButton;

	public HUDButton defenderSpawnButton;
	public HUDButton attackerSpawnButton;
	public HUDButton botPathfindingButton;

	public boolean blockMovement = true;
	public boolean blockAbilities = true;

	public int infoType = -1;

	public ArrayList<HUDButton> guiButtons = new ArrayList<HUDButton>();

	public InformationGUI(float x, float y, MapEditor editor) {
		this.x = x;
		this.y = y;
		this.editor = editor;
		title = "Information";
		defaultRenderLayer = MapLayer.INFORMATION;
		width = 69;
		height = 74;

		blockMovementButton = new HUDButton("Block Movement", x, y, 65, 8);
		blockAbilitiesButton = new HUDButton("Block Abilities", x, y, 65, 8);

		defenderSpawnButton = new HUDButton("Defender Spawn", x, y, 65, 8);
		attackerSpawnButton = new HUDButton("Attacker Spawn", x, y, 65, 8);
		botPathfindingButton = new HUDButton("Bot Pathfinding", x, y, 65, 8);

		guiButtons.add(blockMovementButton);
		guiButtons.add(blockAbilitiesButton);
		guiButtons.add(defenderSpawnButton);
		guiButtons.add(attackerSpawnButton);
		guiButtons.add(botPathfindingButton);

		for (HUDButton button : guiButtons) {
			button.setTextColor(Color.white);
		}

		blockMovementButton.setToggle();
		blockAbilitiesButton.setToggle();
	}

	public void update(GameContainer gc, int delta) {
		mouseX = (int) (gc.getInput().getMouseX() + Game.getGame().getCam().getX());
		mouseY = (int) (gc.getInput().getMouseY() + Game.getGame().getCam().getY());

		if (!editor.clickingOnGUI(gc.getInput().getMouseX(), gc.getInput().getMouseY())) {
			if (gc.getInput().isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
				Tile t = Game.getGame().getWorld().getTile(mouseX, mouseY, editor.layerGUI.layerIndex);
				t.setBlocked(blockMovement);
				t.setBlocksAbilities(blockAbilities);
				t.setTileType(infoType);

				if (gc.getInput().isKeyDown(Input.KEY_LSHIFT)) {
					for (Tile tt : Game.getGame().getWorld().getTile(mouseX, mouseY, editor.layerGUI.layerIndex)
							.getNeighbors()) {
						tt.setBlocked(blockMovement);
						tt.setBlocksAbilities(blockAbilities);
						tt.setTileType(infoType);
					}
				}
			}

			if (gc.getInput().isMouseButtonDown(Input.MOUSE_MIDDLE_BUTTON)) {
				Tile t = Game.getGame().getWorld().getTile(mouseX, mouseY, editor.layerGUI.layerIndex);
				blockMovement = t.isBlocked();
				blockAbilities = t.isBlocksAbilities();
				infoType = t.getTileType();

				blockMovementButton.setToggle(blockMovement);
				blockAbilitiesButton.setToggle(blockAbilities);
			}

			if (gc.getInput().isMouseButtonDown(Input.MOUSE_RIGHT_BUTTON)) {
				Tile t = Game.getGame().getWorld().getTile(mouseX, mouseY, editor.layerGUI.layerIndex);
				t.setBlocked(false);
				t.setBlocksAbilities(false);
				t.setTileType(-1);
			}
		} else {
			for (HUDButton gui : guiButtons) {
				gui.update(gc);
			}

			if (blockMovementButton.isClicked()) {
				blockMovement = !blockMovement;
				blockMovementButton.setToggle();
				infoType = -1;
			}

			if (blockAbilitiesButton.isClicked()) {
				blockAbilities = !blockAbilities;
				blockAbilitiesButton.setToggle();
				infoType = -1;
			}

			if (defenderSpawnButton.isClicked()) {
				infoType = 0;
				disableCollision();
			}

			if (attackerSpawnButton.isClicked()) {
				infoType = 1;
				disableCollision();
			}

			if (botPathfindingButton.isClicked()) {
				infoType = 2;
				disableCollision();
			}

		}
		checkForPickUp(gc);

		setButtons();
	}

	public void render(GameContainer gc, Graphics g) {

		if (!hidden) {
			g.setColor(new Color(8, 103, 170, 150));
			g.fillRect(x, y, width, height);

			g.setColor(Color.white);
			g.drawRect(x, y, width, height);

			for (HUDButton button : guiButtons) {
				button.render(g, 1);
			}
		}

	}

	public void toggleButtons(HUDButton keepOn) {

		defenderSpawnButton.setToggle(false);
		attackerSpawnButton.setToggle(false);
		botPathfindingButton.setToggle(false);

		if (keepOn != null) {
			keepOn.setToggle();
		}
	}

	public void disableCollision() {
		blockMovementButton.setToggle(false);
		blockAbilitiesButton.setToggle(false);
		blockMovement = false;
		blockAbilities = false;
	}

	public void setButtons() {

		blockMovementButton.setX(x + 2);
		blockMovementButton.setY(y + 2);

		blockAbilitiesButton.setX(x + 2);
		blockAbilitiesButton.setY(y + 14);

		defenderSpawnButton.setX(x + 2);
		defenderSpawnButton.setY(y + 38);

		attackerSpawnButton.setX(x + 2);
		attackerSpawnButton.setY(y + 50);

		botPathfindingButton.setX(x + 2);
		botPathfindingButton.setY(y + 62);

		if (infoType == -1) {
			toggleButtons(null);
		} else if (infoType == 0) {
			toggleButtons(defenderSpawnButton);
		} else if (infoType == 1) {
			toggleButtons(attackerSpawnButton);
		} else if (infoType == 2) {
			toggleButtons(botPathfindingButton);
		}
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
