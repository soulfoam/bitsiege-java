package soulfoam.arena.main;

import java.util.ArrayList;

//import java.awt.event.KeyEvent;
//import java.awt.event.KeyListener;
//import java.awt.event.MouseEvent;
//import java.awt.event.MouseListener;
//import java.awt.event.MouseMotionListener;
//import java.awt.event.MouseWheelEvent;
//import java.awt.event.MouseWheelListener;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;

import soulfoam.arena.entities.Entity;
import soulfoam.arena.entities.abilities.Ability;
import soulfoam.arena.main.command.client.local.ClientLocalInputCommand;
import soulfoam.arena.main.game.Game;
import soulfoam.arena.main.resources.Res;
import soulfoam.arena.world.Tile;
import soulfoam.arenashared.main.entityinfo.EntityInfo;

import soulfoam.arenashared.main.opcode.OPCode;

public class InputHandler {

	private boolean upPressed;
	private boolean leftPressed;
	private boolean downPressed;
	private boolean rightPressed;
	private boolean autoAttackPressed;
	private boolean spellCastPressed;

	private boolean toolTipsPressed;


	private boolean selectSpellOne;
	private boolean selectSpellTwo;
	private boolean selectSpellThree;
	private boolean selectSpellFour;

	private boolean scoreboardPressed;
	
	private boolean escapePressed;

	private int mouseX;
	private int mouseY;

	private GameContainer gc;

	private Entity p;

	private boolean inputDisabled;
	private boolean canChangeSpectator = true;

	private float mouseLookCDTime = 0.25f * 1000;
	private float mouseLookCDTimer = mouseLookCDTime;
	
	private float aimingDirectionTimer = 0.25f;
	
	private boolean autoAttacking;
	private byte currentDirection;

	private byte currentVelocity;
	
	public InputHandler(GameContainer gc) {
		this.gc = gc;
	}

	public void update(int delta, GameContainer gc) {

		if (p != null) {
			handleInput();
			handlePlayerInput(gc, delta);
		} else {
			p = Game.getGame().getPlayer();
		}

	}

	public void handlePlayerInput(GameContainer gc, int delta) {
	
		if (!isInputDisabled()) {

			if (selectSpellOne) {
				p.setSelectedAbility(1);
			}
			if (selectSpellTwo) {
				p.setSelectedAbility(2);
			}
			if (selectSpellThree) {
				p.setSelectedAbility(3);
			}
			if (selectSpellFour) {
				p.setSelectedAbility(4);
			}

			if (!p.isDead()) {
				if (!p.isStunned()) {
					if (leftPressed) {
						setDirection(EntityInfo.DIR_LEFT);
						currentVelocity = EntityInfo.DIR_LEFT;
					}

					else if (rightPressed) {
						setDirection(EntityInfo.DIR_RIGHT);
						currentVelocity = EntityInfo.DIR_RIGHT;
					}

					if (upPressed) {
						setDirection(EntityInfo.DIR_UP);
						currentVelocity = EntityInfo.DIR_UP;
					}

					else if (downPressed) {
						setDirection(EntityInfo.DIR_DOWN);
						currentVelocity = EntityInfo.DIR_DOWN;
					}

					if (upPressed && leftPressed) {
						currentVelocity = EntityInfo.DIR_UP_LEFT;
						setDirection(EntityInfo.DIR_UP);
					} else if (upPressed && rightPressed) {
						currentVelocity = EntityInfo.DIR_UP_RIGHT;
						setDirection(EntityInfo.DIR_UP);
					} else if (downPressed && leftPressed) {
						currentVelocity = EntityInfo.DIR_DOWN_LEFT;
						setDirection(EntityInfo.DIR_DOWN);
					} else if (downPressed && rightPressed) {
						currentVelocity = EntityInfo.DIR_DOWN_RIGHT;
						setDirection(EntityInfo.DIR_DOWN);
					}

					if (!upPressed && !leftPressed && !downPressed && !rightPressed) {
						currentVelocity = EntityInfo.DIR_NONE;
					}
					

					if (!p.isBlind() && !p.isConfused()) {
						if (autoAttackPressed) {
							setAimingDirection(gc);
							autoAttacking = true;
						} else {
							autoAttacking = false;
						}

						if (spellCastPressed) {
							if (getSelectedAbility() != null && getSelectedAbility().changeDirectionOnCast()) {
								setAimingDirection(gc);
							}
							castAbility();
						}

						if (selectSpellOne || selectSpellTwo || selectSpellThree || selectSpellFour) {
							if (SettingManager.SMART_CAST) {
								if (getSelectedAbility() != null && getSelectedAbility().changeDirectionOnCast()) {
									setAimingDirection(gc);
								}
								castAbility();
							}
						}
					} else {
						autoAttacking = false;
					}

				}
			} else {
				autoAttacking = false;
			}
		}

		if (mouseLookCDTimer > 0) {
			mouseLookCDTimer -= delta;
		}
		if (aimingDirectionTimer > 0) {
			aimingDirectionTimer -= delta;
		}

		if (p.isDead()) {
			if (!Game.getGame().getCam().isSpectating()) {
				Game.getGame().getCam().setSpectator(true);
				Game.getGame().getCam().setSpectating(true);
			}
		} else {
			if (Game.getGame().getCam().isSpectating()) {
				Game.getGame().getCam().setTarget(p);
				Game.getGame().getCam().setSpectating(false);
			}
		}

	}

	public void castAbility() {

		if (p.getSelectedAbility() == 1) {
			if (p.getAbility1CDTimer() <= 0 && p.getAbility1().canCast(getMouseX(), getMouseY())) {
				Game.getGame().getClient().sendReliableData(OPCode.OP_ADDABILITY + Res.SLOT_1 + "," + getMouseX() + "," + getMouseY());
			}
		}

		if (p.getSelectedAbility() == 2) {
			if (p.getAbility2CDTimer() <= 0 && p.getAbility2().canCast(getMouseX(), getMouseY())) {
				Game.getGame().getClient().sendReliableData(OPCode.OP_ADDABILITY + Res.SLOT_2 + "," + getMouseX() + "," + getMouseY());
			}
		}

		if (p.getSelectedAbility() == 3) {
			if (p.getAbility3CDTimer() <= 0 && p.getAbility3().canCast(getMouseX(), getMouseY())) {
				Game.getGame().getClient().sendReliableData(OPCode.OP_ADDABILITY + Res.SLOT_3 + "," + getMouseX() + "," + getMouseY());
			}
		}

		if (p.getSelectedAbility() == 4) {
			if (p.getAbility4CDTimer() <= 0 && p.getAbility4().canCast(getMouseX(), getMouseY())) {
				Game.getGame().getClient().sendReliableData(OPCode.OP_ADDABILITY + Res.SLOT_4 + "," + getMouseX() + "," + getMouseY());
			}
		}

	}

	public Ability getSelectedAbility() {

		if (p.getSelectedAbility() == 1) {
			return p.getAbility1();
		} else if (p.getSelectedAbility() == 2) {
			return p.getAbility2();
		} else if (p.getSelectedAbility() == 3) {
			return p.getAbility3();
		} else if (p.getSelectedAbility() == 4) {
			return p.getAbility4();
		}

		return p.getAbility1();
	}

	public void setDirection(byte dir) {
		if (mouseLookCDTimer <= 0) {
			currentDirection = dir;
			mouseLookCDTimer = mouseLookCDTime;
		}
	}

	public void setAimingDirection(GameContainer gc) {

		float px = p.getX() - Game.getGame().getCam().getX() + Tile.TILE_SIZE / 2;
		float py = p.getY() - Game.getGame().getCam().getY() + Tile.TILE_SIZE;

		float dx = -(px - gc.getInput().getMouseX());
		float dy = -(py - gc.getInput().getMouseY());
		
		byte nDir = 0; 
		
		if (dx < 0 && Math.abs(dx) > Math.abs(dy)) {
			nDir = EntityInfo.DIR_LEFT;
		}
		if (dx > 0 && Math.abs(dx) > Math.abs(dy)) {
			nDir = EntityInfo.DIR_RIGHT;
		}
		if (dy > 0 && Math.abs(dy) > Math.abs(dx)) {
			nDir = EntityInfo.DIR_DOWN;
		}
		if (dy < 0 && Math.abs(dy) > Math.abs(dx)) {
			nDir = EntityInfo.DIR_UP;
		}
		
		if (aimingDirectionTimer <= 0){
			currentDirection = nDir;
			aimingDirectionTimer = p.getAbilityBasicAttackCDTime();
		}
	}

	public void createInputUpdate() {
		ClientLocalInputCommand mec = new ClientLocalInputCommand(currentDirection, autoAttacking, getMouseX(), getMouseY(), currentVelocity);
		Game.getGame().getClientFunctions().getCommandHandler().addCommand(mec);
	}

	public void handleInput() {
		mouseX = (int) Math.round((gc.getInput().getMouseX() - 8 + Game.getGame().getCam().getX()));
		mouseY = (int) Math.round((gc.getInput().getMouseY() - 14 + Game.getGame().getCam().getY()));

		if (gc.getInput().isKeyDown(SettingManager.KEYBIND_MOVEUP)) {
			if (!Game.getGame().getHUDDisplay().getChatHUD().isChatting()) {
				upPressed = true;
			}
		} else {
			upPressed = false;
		}

		if (gc.getInput().isKeyDown(SettingManager.KEYBIND_MOVELEFT)) {
			if (!Game.getGame().getHUDDisplay().getChatHUD().isChatting()) {
				leftPressed = true;
			}
		} else {
			leftPressed = false;
		}

		if (gc.getInput().isKeyDown(SettingManager.KEYBIND_MOVEDOWN)) {
			if (!Game.getGame().getHUDDisplay().getChatHUD().isChatting()) {
				downPressed = true;
			}
		} else {
			downPressed = false;
		}

		if (gc.getInput().isKeyDown(SettingManager.KEYBIND_MOVERIGHT)) {
			if (!Game.getGame().getHUDDisplay().getChatHUD().isChatting()) {
				rightPressed = true;
			}
		} else {
			rightPressed = false;
		}

		if (gc.getInput().isKeyPressed(SettingManager.KEYBIND_SELECTSPELL1)) {
			if (!Game.getGame().getHUDDisplay().getChatHUD().isChatting()) {
				selectSpellOne = true;
			}
		} else {
			selectSpellOne = false;
		}

		if (gc.getInput().isKeyPressed(SettingManager.KEYBIND_SELECTSPELL2)) {
			if (!Game.getGame().getHUDDisplay().getChatHUD().isChatting()) {
				selectSpellTwo = true;
			}
		} else {
			selectSpellTwo = false;
		}

		if (gc.getInput().isKeyPressed(SettingManager.KEYBIND_SELECTSPELL3)) {
			if (!Game.getGame().getHUDDisplay().getChatHUD().isChatting()) {
				selectSpellThree = true;
			}
		} else {
			selectSpellThree = false;
		}

		if (gc.getInput().isKeyPressed(SettingManager.KEYBIND_SELECTSPELL4)) {
			if (!Game.getGame().getHUDDisplay().getChatHUD().isChatting()) {
				selectSpellFour = true;
			}
		} else {
			selectSpellFour = false;
		}

		if (gc.getInput().isKeyDown(SettingManager.KEYBIND_SHOWSCOREBOARD)) {
			if (!Game.getGame().getHUDDisplay().getChatHUD().isChatting()) {
				setScoreboardPressed(true);
			}
		} else {
			setScoreboardPressed(false);
		}

		if (gc.getInput().isKeyPressed(SettingManager.KEYBIND_CHAT)) {
			if (Game.getGame().getHUDDisplay().getChatHUD().isChatting()) {
				Game.getGame().getHUDDisplay().getChatHUD().sendChatMessage();
				Game.getGame().getHUDDisplay().getChatHUD().setChatting(false);
				clearAllInput();
			} else {
				Game.getGame().getHUDDisplay().getChatHUD().setChatting(true);
			}
		}

		if (!Game.getGame().getHUDDisplay().getChatHUD().isChatting()) {

			if (gc.getInput().isKeyPressed(SettingManager.KEYBIND_SHOWTOOLTIP)) {
				setToolTipsPressed(!isToolTipsPressed());
			}


		}

		if (gc.getInput().isKeyPressed(Input.KEY_ESCAPE)) {
			setEscapePressed(!isEscapePressed());
		}

		if (gc.getInput().isMouseButtonDown(SettingManager.KEYBIND_AUTOATTACK)) {
			autoAttackPressed = true;
		} else {
			autoAttackPressed = false;
		}

		if (gc.getInput().isMousePressed(Input.MOUSE_LEFT_BUTTON) && p.isDead() && canChangeSpectator) {
			Game.getGame().getCam().setSpectator(true);
		}
		if (gc.getInput().isMousePressed(Input.MOUSE_RIGHT_BUTTON) && p.isDead() && canChangeSpectator) {
			Game.getGame().getCam().setSpectator(false);
		}

		if (gc.getInput().isMouseButtonDown(SettingManager.KEYBIND_CASTSPELL)) {
			spellCastPressed = true;
		} else {
			spellCastPressed = false;
		}


		if (Game.getGame().getHUDDisplay().isEditingHUD()) {
			autoAttacking = false;
			setInputDisabled(true);
		} else {

			if (!Game.getGame().getHUDDisplay().getEscapeHUD().getSettingsMenuUI().isRendering()
					&& !Game.getGame().getHUDDisplay().getEscapeHUD().getKeybindUI().isRendering()) {
				setInputDisabled(false);
			} else {
				setInputDisabled(true);
			}
			
		}
		

	}

	public void clearAllInput() {
		gc.getInput().clearKeyPressedRecord();
	}

	public void moveMouseWheel(int dir) {
		if (p != null) {
			if (dir > 0) { // downward wheel
				if (p.getSelectedAbility() < 4) {
					p.setSelectedAbility(p.getSelectedAbility() + 1);
				} else {
					p.setSelectedAbility(4);
				}
			} else if (dir < 0) { // upward wheel
				if (p.getSelectedAbility() > 1) {
					p.setSelectedAbility(p.getSelectedAbility() - 1);
				} else {
					p.setSelectedAbility(1);
				}
			}
		}
	}

	public boolean isEscapePressed() {
		return escapePressed;
	}

	public void setEscapePressed(boolean escapePressed) {
		this.escapePressed = escapePressed;
	}

	public boolean isToolTipsPressed() {
		return toolTipsPressed;
	}

	public void setToolTipsPressed(boolean toolTipsPressed) {
		this.toolTipsPressed = toolTipsPressed;
	}

	public boolean isScoreboardPressed() {
		return scoreboardPressed;
	}

	public void setScoreboardPressed(boolean scoreboardPressed) {
		this.scoreboardPressed = scoreboardPressed;
	}

	public boolean isAutoAttackPressed() {
		return autoAttackPressed;
	}

	public void setAutoAttackPressed(boolean autoAttackPressed) {
		this.autoAttackPressed = autoAttackPressed;
	}

	public boolean isInputDisabled() {
		return inputDisabled;
	}

	public void setInputDisabled(boolean inputDisabled) {
		this.inputDisabled = inputDisabled;
	}

	public int getMouseX() {
		return mouseX;
	}

	public void setMouseX(int mouseX) {
		this.mouseX = mouseX;
	}

	public int getMouseY() {
		return mouseY;
	}

	public void setMouseY(int mouseY) {
		this.mouseY = mouseY;
	}

}
