package soulfoam.arena.main.menu.settings;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.state.StateBasedGame;

import soulfoam.arena.main.SettingManager;
import soulfoam.arena.main.gfx.EscapeHUD;
import soulfoam.arena.main.menu.BaseMenuUI;
import soulfoam.arena.main.menu.MainMenuManager;
import soulfoam.arena.main.menu.MenuButton;
import soulfoam.arena.main.resources.Res;
import soulfoam.arenashared.main.gameinfo.GameInfo;

public class KeybindUI extends BaseMenuUI {

	public MenuButton moveUpButton;
	public MenuButton moveLeftButton;
	public MenuButton moveDownButton;
	public MenuButton moveRightButton;
	public MenuButton autoAttackButton;
	public MenuButton castAbilityButton;
	public MenuButton selectAbility1Button;
	public MenuButton selectAbility2Button;
	public MenuButton selectAbility3Button;
	public MenuButton selectAbility4Button;
	public MenuButton showScoreBoardButton;
	public MenuButton toggleToolTipsButton;
	public MenuButton chatButton;
	public MenuButton screenShotButton;
	public MenuButton smartCastButton;

	public MenuButton restoreDefaultsButton;
	public MenuButton backButton;

	public int setID;
	public boolean setMode;

	EscapeHUD eh;

	public KeybindUI(float x, float y) {
		init();
	}

	public KeybindUI(EscapeHUD eh) {
		init();
		this.eh = eh;
	}

	public void init() {
		setWidth(310);
		setHeight(200);
		setX(GameInfo.RES_WIDTH / 2 - getWidth() / 2);
		setY(GameInfo.RES_HEIGHT / 2 - getHeight() / 2 + 7);

		moveUpButton = new MenuButton("", 20, 20, 40, 11);
		moveLeftButton = new MenuButton("", 20, 20, 40, 11);
		moveDownButton = new MenuButton("", 20, 20, 40, 11);
		moveRightButton = new MenuButton("", 20, 20, 40, 11);
		autoAttackButton = new MenuButton("", 20, 20, 40, 11);
		castAbilityButton = new MenuButton("", 20, 20, 40, 11);
		selectAbility1Button = new MenuButton("", 20, 20, 40, 11);
		selectAbility2Button = new MenuButton("", 20, 20, 40, 11);
		selectAbility3Button = new MenuButton("", 20, 20, 40, 11);
		selectAbility4Button = new MenuButton("", 20, 20, 40, 11);
		showScoreBoardButton = new MenuButton("", 20, 20, 40, 11);
		toggleToolTipsButton = new MenuButton("", 20, 20, 40, 11);
		chatButton = new MenuButton("", 20, 20, 40, 11);
		smartCastButton = new MenuButton("", 20, 20, 40, 11);

		restoreDefaultsButton = new MenuButton("", 20, 20, 40, 11);
		screenShotButton = new MenuButton("", 20, 20, 40, 11);
		backButton = new MenuButton("<", 20, 20, 7, 7);

		handleSetButtons();
	}

	public void update(GameContainer gc, StateBasedGame s, int delta) {

		moveUpButton.update(gc);
		moveLeftButton.update(gc);
		moveDownButton.update(gc);
		moveRightButton.update(gc);

		autoAttackButton.update(gc);
		castAbilityButton.update(gc);

		selectAbility1Button.update(gc);
		selectAbility2Button.update(gc);
		selectAbility3Button.update(gc);
		selectAbility4Button.update(gc);

		showScoreBoardButton.update(gc);
		toggleToolTipsButton.update(gc);
		chatButton.update(gc);
		smartCastButton.update(gc);
		screenShotButton.update(gc);

		restoreDefaultsButton.update(gc);
		backButton.update(gc);

		setKeyBinds();

		if (moveUpButton.isClicked()) {
			setMode = true;
			setID = 0;
		}
		if (moveLeftButton.isClicked()) {
			setMode = true;
			setID = 1;
		}
		if (moveDownButton.isClicked()) {
			setMode = true;
			setID = 2;
		}
		if (moveRightButton.isClicked()) {
			setMode = true;
			setID = 3;
		}
		if (autoAttackButton.isClicked()) {
			setMode = true;
			setID = 4;
		}
		if (castAbilityButton.isClicked()) {
			setMode = true;
			setID = 5;
		}
		if (selectAbility1Button.isClicked()) {
			setMode = true;
			setID = 6;
		}
		if (selectAbility2Button.isClicked()) {
			setMode = true;
			setID = 7;
		}
		if (selectAbility3Button.isClicked()) {
			setMode = true;
			setID = 8;
		}
		if (selectAbility4Button.isClicked()) {
			setMode = true;
			setID = 9;
		}
		if (showScoreBoardButton.isClicked()) {
			setMode = true;
			setID = 10;
		}
		if (toggleToolTipsButton.isClicked()) {
			setMode = true;
			setID = 11;
		}
		if (chatButton.isClicked()) {
			setMode = true;
			setID = 12;
		}
		if (screenShotButton.isClicked()) {
			setMode = true;
			setID = 13;
		}
		if (smartCastButton.isClicked()) {
			SettingManager.SMART_CAST = !SettingManager.SMART_CAST;
			SettingManager.setConfigFile("smartcast", String.valueOf(SettingManager.SMART_CAST));
		}
		if (backButton.isClicked()) {
			if (MainMenuManager.getMainMenu().isInMenu()) {
				MainMenuManager.getMainMenu().setWindowUI(MainMenuManager.getMainMenu().getSettingsUI());
			} else {
				if (eh != null) {
					eh.getSettingsMenuUI().setWindow(true);
					setWindow(false);
				}
			}
		}
		if (restoreDefaultsButton.isClicked()) {
			setMode = false;
			setDefaultKeybinds();
		}

		setKeyBinds();
		handleSetButtons();
	}

	public void render(GameContainer gc, Graphics g) {

		if (isRendering()) {
			renderWindow(g);

			moveUpButton.render(g, 3, gc);
			moveLeftButton.render(g, 3, gc);
			moveDownButton.render(g, 3, gc);
			moveRightButton.render(g, 3, gc);
			autoAttackButton.render(g, 3, gc);
			castAbilityButton.render(g, 3, gc);
			selectAbility1Button.render(g, 3, gc);
			selectAbility2Button.render(g, 3, gc);
			selectAbility3Button.render(g, 3, gc);
			selectAbility4Button.render(g, 3, gc);
			showScoreBoardButton.render(g, 3, gc);
			toggleToolTipsButton.render(g, 3, gc);
			chatButton.render(g, 3, gc);
			screenShotButton.render(g, 3, gc);
			smartCastButton.render(g, 3, gc);

			backButton.render(g, 0, gc);
			restoreDefaultsButton.render(g, 3, gc);

		}
	}

	public void renderWindow(Graphics g) {
		
		Res.UI_RESOURCE.MAINMENU_KEYBIND_UI.draw(getX(), getY());
		
		
		String setkeytext = "";
		Color tc = Color.yellow;
		if (!setMode) {
			setkeytext = "Press a button to set the keybind!";
		} else {
			tc = Color.green;
			if (setID == 0) {
				setkeytext = "Setting Keybind for Move Up!";
			}
			if (setID == 1) {
				setkeytext = "Setting Keybind for Move Left!";
			}
			if (setID == 2) {
				setkeytext = "Setting Keybind for Move Down!";
			}
			if (setID == 3) {
				setkeytext = "Setting Keybind for Move Right!";
			}
			if (setID == 4) {
				setkeytext = "Setting Keybind for Auto Attack!";
			}
			if (setID == 5) {
				setkeytext = "Setting Keybind for Cast Ability!";
			}
			if (setID == 6) {
				setkeytext = "Setting Keybind for Select Ability 1!";
			}
			if (setID == 7) {
				setkeytext = "Setting Keybind for Select Ability 2!";
			}
			if (setID == 8) {
				setkeytext = "Setting Keybind for Select Ability 3!";
			}
			if (setID == 9) {
				setkeytext = "Setting Keybind for Select Ability 4!";
			}
			if (setID == 10) {
				setkeytext = "Setting Keybind for Show Scoreboard!";
			}
			if (setID == 11) {
				setkeytext = "Setting Keybind for Toggle Ability Tool Tips!";
			}
			if (setID == 12) {
				setkeytext = "Setting Keybind for Chat!";
			}
			if (setID == 13) {
				setkeytext = "Setting Keybind for Screen Shot!";
			}
		}

		Res.bitFont.drawString(getX() + getWidth() / 2 - Res.getTextCenter(setkeytext), getY() + 17, setkeytext, tc);
		
		String headerText = "Edit Keybinds";
		Res.bitFont.drawString(getX() + getWidth() / 2 - Res.getTextCenter(headerText), getY() + 3, headerText,
				Color.white);

		Res.bitFont.drawString(getX() + 5, getY() + 41, "Move Up");
		Res.bitFont.drawString(getX() + 5, getY() + 58, "Move Down");
		Res.bitFont.drawString(getX() + 5, getY() + 75, "Auto Attack");
		Res.bitFont.drawString(getX() + 5, getY() + 92, "Select Ability 1");
		Res.bitFont.drawString(getX() + 5, getY() + 109, "Select Ability 3");
		Res.bitFont.drawString(getX() + 5, getY() + 126, "Smart Cast");
		Res.bitFont.drawString(getX() + 5, getY() + 143, "Chat");
		Res.bitFont.drawString(getX() + 5, getY() + 160, "Screen Shot");

		Res.bitFont.drawString(getX() + getWidth() / 2 + 4, getY() + 41, "Move Left");
		Res.bitFont.drawString(getX() + getWidth() / 2 + 4, getY() + 58, "Move Right");
		Res.bitFont.drawString(getX() + getWidth() / 2 + 4, getY() + 75, "Cast Ability");
		Res.bitFont.drawString(getX() + getWidth() / 2 + 4, getY() + 92, "Select Ability 2");
		Res.bitFont.drawString(getX() + getWidth() / 2 + 4, getY() + 109, "Select Ability 4");
		Res.bitFont.drawString(getX() + getWidth() / 2 + 4, getY() + 126, "Toggle Tool Tips");
		Res.bitFont.drawString(getX() + getWidth() / 2 + 4, getY() + 143, "Show Scoreboard");

		Res.bitFont.drawString(getX() + getWidth() / 2 + 4, getY() + 177, "Reset Keybinds", Color.yellow);

	}

	public void handleSetButtons() {

		float leftButtons = getX() + 112;
		float rightButtons = getX() + 267;

		moveUpButton.setPosition(leftButtons, getY() + 38);
		moveLeftButton.setPosition(rightButtons, getY() + 38);
		moveDownButton.setPosition(leftButtons, getY() + 55);
		moveRightButton.setPosition(rightButtons, getY() + 55);
		autoAttackButton.setPosition(leftButtons, getY() + 72);
		castAbilityButton.setPosition(rightButtons, getY() + 72);
		selectAbility1Button.setPosition(leftButtons, getY() + 89);
		selectAbility2Button.setPosition(rightButtons, getY() + 89);
		selectAbility3Button.setPosition(leftButtons, getY() + 106);
		selectAbility4Button.setPosition(rightButtons, getY() + 106);
		smartCastButton.setPosition(leftButtons, getY() + 123);
		toggleToolTipsButton.setPosition(rightButtons, getY() + 123);
		chatButton.setPosition(leftButtons, getY() + 140);
		showScoreBoardButton.setPosition(rightButtons, getY() + 140);
		screenShotButton.setPosition(leftButtons, getY() + 157);		
		
		if (SettingManager.SMART_CAST) {
			smartCastButton.setTextColor(Color.white);
			smartCastButton.setButtonColor(new Color(9, 183, 25));
			smartCastButton.setText("On");
		} else {
			smartCastButton.setTextColor(Color.white);
			smartCastButton.setButtonColor(new Color(183, 29, 9));
			smartCastButton.setText("Off");
		}

		restoreDefaultsButton.setPosition(rightButtons, getY() + 174);
		restoreDefaultsButton.setText("Reset");
		restoreDefaultsButton.setTextColor(Color.white);
		restoreDefaultsButton.setButtonColor(new Color(183, 29, 9));

		backButton.setPosition(getX() + 2, getY() + 2);

	}

	public void setKeyBinds() {
		moveUpButton.setText(Input.getKeyName(SettingManager.KEYBIND_MOVEUP));
		moveLeftButton.setText(Input.getKeyName(SettingManager.KEYBIND_MOVELEFT));
		moveDownButton.setText(Input.getKeyName(SettingManager.KEYBIND_MOVEDOWN));
		moveRightButton.setText(Input.getKeyName(SettingManager.KEYBIND_MOVERIGHT));
		if (SettingManager.KEYBIND_AUTOATTACK == 0) {
			autoAttackButton.setText("LCLICK");
		} else {
			autoAttackButton.setText("RCLICK");
		}
		if (SettingManager.KEYBIND_CASTSPELL == 0) {
			castAbilityButton.setText("LCLICK");
		} else {
			castAbilityButton.setText("RCLICK");
		}
		selectAbility1Button.setText(Input.getKeyName(SettingManager.KEYBIND_SELECTSPELL1));
		selectAbility2Button.setText(Input.getKeyName(SettingManager.KEYBIND_SELECTSPELL2));
		selectAbility3Button.setText(Input.getKeyName(SettingManager.KEYBIND_SELECTSPELL3));
		selectAbility4Button.setText(Input.getKeyName(SettingManager.KEYBIND_SELECTSPELL4));
		showScoreBoardButton.setText(Input.getKeyName(SettingManager.KEYBIND_SHOWSCOREBOARD));
		toggleToolTipsButton.setText(Input.getKeyName(SettingManager.KEYBIND_SHOWTOOLTIP));
		screenShotButton.setText(Input.getKeyName(SettingManager.KEYBIND_SCREENSHOT));
		chatButton.setText(Input.getKeyName(SettingManager.KEYBIND_CHAT));
	}

	public void saveAllKeyBinds() {
		SettingManager.setConfigFile("moveup", Integer.toString(SettingManager.KEYBIND_MOVEUP));
		SettingManager.setConfigFile("moveleft", Integer.toString(SettingManager.KEYBIND_MOVELEFT));
		SettingManager.setConfigFile("movedown", Integer.toString(SettingManager.KEYBIND_MOVEDOWN));
		SettingManager.setConfigFile("moveright", Integer.toString(SettingManager.KEYBIND_MOVERIGHT));
		SettingManager.setConfigFile("autoattack", Integer.toString(SettingManager.KEYBIND_AUTOATTACK));
		SettingManager.setConfigFile("castspell", Integer.toString(SettingManager.KEYBIND_CASTSPELL));
		SettingManager.setConfigFile("selectspell1", Integer.toString(SettingManager.KEYBIND_SELECTSPELL1));
		SettingManager.setConfigFile("selectspell2", Integer.toString(SettingManager.KEYBIND_SELECTSPELL2));
		SettingManager.setConfigFile("selectspell3", Integer.toString(SettingManager.KEYBIND_SELECTSPELL3));
		SettingManager.setConfigFile("selectspell4", Integer.toString(SettingManager.KEYBIND_SELECTSPELL4));
		SettingManager.setConfigFile("showscoreboard", Integer.toString(SettingManager.KEYBIND_SHOWSCOREBOARD));
		SettingManager.setConfigFile("tooltipdisplay", Integer.toString(SettingManager.KEYBIND_SHOWTOOLTIP));
		SettingManager.setConfigFile("chat", Integer.toString(SettingManager.KEYBIND_CHAT));
		SettingManager.setConfigFile("screenshot", Integer.toString(SettingManager.KEYBIND_SCREENSHOT));
	}

	public void setDefaultKeybinds() {

		SettingManager.KEYBIND_MOVEUP = 17;
		SettingManager.KEYBIND_MOVELEFT = 30;
		SettingManager.KEYBIND_MOVEDOWN = 31;
		SettingManager.KEYBIND_MOVERIGHT = 32;
		SettingManager.KEYBIND_AUTOATTACK = 0;
		SettingManager.KEYBIND_CASTSPELL = 1;
		SettingManager.KEYBIND_SELECTSPELL1 = 2;
		SettingManager.KEYBIND_SELECTSPELL2 = 3;
		SettingManager.KEYBIND_SELECTSPELL3 = 4;
		SettingManager.KEYBIND_SELECTSPELL4 = 5;
		SettingManager.KEYBIND_SHOWSCOREBOARD = 42;
		SettingManager.KEYBIND_SHOWTOOLTIP = 20;
		SettingManager.KEYBIND_CHAT = 28;
		SettingManager.KEYBIND_SCREENSHOT = 88;

		SettingManager.setConfigFile("moveup", Integer.toString(SettingManager.KEYBIND_MOVEUP));
		SettingManager.setConfigFile("moveleft", Integer.toString(SettingManager.KEYBIND_MOVELEFT));
		SettingManager.setConfigFile("movedown", Integer.toString(SettingManager.KEYBIND_MOVEDOWN));
		SettingManager.setConfigFile("moveright", Integer.toString(SettingManager.KEYBIND_MOVERIGHT));
		SettingManager.setConfigFile("autoattack", Integer.toString(SettingManager.KEYBIND_AUTOATTACK));
		SettingManager.setConfigFile("castspell", Integer.toString(SettingManager.KEYBIND_CASTSPELL));
		SettingManager.setConfigFile("selectspell1", Integer.toString(SettingManager.KEYBIND_SELECTSPELL1));
		SettingManager.setConfigFile("selectspell2", Integer.toString(SettingManager.KEYBIND_SELECTSPELL2));
		SettingManager.setConfigFile("selectspell3", Integer.toString(SettingManager.KEYBIND_SELECTSPELL3));
		SettingManager.setConfigFile("selectspell4", Integer.toString(SettingManager.KEYBIND_SELECTSPELL4));
		SettingManager.setConfigFile("showscoreboard", Integer.toString(SettingManager.KEYBIND_SHOWSCOREBOARD));
		SettingManager.setConfigFile("tooltipdisplay", Integer.toString(SettingManager.KEYBIND_SHOWTOOLTIP));
		SettingManager.setConfigFile("chat", Integer.toString(SettingManager.KEYBIND_CHAT));
		SettingManager.setConfigFile("screenshot", Integer.toString(SettingManager.KEYBIND_SCREENSHOT));
	}

	public void setKeybind(int key) {
		if (setMode) {
			if (setID == 0) {
				SettingManager.KEYBIND_MOVEUP = key;
			}
			if (setID == 1) {
				SettingManager.KEYBIND_MOVELEFT = key;
			}
			if (setID == 2) {
				SettingManager.KEYBIND_MOVEDOWN = key;
			}
			if (setID == 3) {
				SettingManager.KEYBIND_MOVERIGHT = key;
			}
			if (setID == 4) {
				SettingManager.KEYBIND_AUTOATTACK = key;
			}
			if (setID == 5) {
				SettingManager.KEYBIND_CASTSPELL = key;
			}
			if (setID == 6) {
				SettingManager.KEYBIND_SELECTSPELL1 = key;
			}
			if (setID == 7) {
				SettingManager.KEYBIND_SELECTSPELL2 = key;
			}
			if (setID == 8) {
				SettingManager.KEYBIND_SELECTSPELL3 = key;
			}
			if (setID == 9) {
				SettingManager.KEYBIND_SELECTSPELL4 = key;
			}
			if (setID == 10) {
				SettingManager.KEYBIND_SHOWSCOREBOARD = key;
			}
			if (setID == 11) {
				SettingManager.KEYBIND_SHOWTOOLTIP = key;
			}
			if (setID == 12) {
				SettingManager.KEYBIND_CHAT = key;
			}
			if (setID == 13) {
				SettingManager.KEYBIND_SCREENSHOT = key;
			}

			setMode = false;
			saveAllKeyBinds();
		}
	}
}
