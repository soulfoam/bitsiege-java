package soulfoam.arena.main.menu.settings;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.TextField;
import org.newdawn.slick.openal.SoundStore;
import org.newdawn.slick.state.StateBasedGame;

import soulfoam.arena.main.ClientEngine;
import soulfoam.arena.main.SettingManager;
import soulfoam.arena.main.game.Game;
import soulfoam.arena.main.gfx.EscapeHUD;
import soulfoam.arena.main.menu.BaseMenuUI;
import soulfoam.arena.main.menu.MainMenuManager;
import soulfoam.arena.main.menu.MenuButton;
import soulfoam.arena.main.resources.Res;
import soulfoam.arena.net.lobby.LobbyManager;
import soulfoam.arenashared.main.gameinfo.GameInfo;

public class SettingsMenuUI extends BaseMenuUI {

	private MenuButton displayModeButton;
	private MenuButton showPlayerNamesButton;
	private MenuButton maintainAspectButton;
	private MenuButton vSyncButton;
	private MenuButton showFPSButton;
	private MenuButton muteMenuMusicButton;
	private MenuButton windowSizeButton;

	private MenuButton backButton;
	private MenuButton keybindsButton;

	private static int selectedWindowSizeIndex;

	private EscapeHUD eh;

	public SettingsMenuUI(GameContainer gc) {
		init(gc);
	}

	public SettingsMenuUI(EscapeHUD eh, GameContainer gc) {
		this.eh = eh;
		init(gc);
		setY(GameInfo.RES_HEIGHT / 2 - getHeight() / 2);
	}

	public void init(GameContainer gc) {
		setWidth(290);
		setHeight(200);
		setX(GameInfo.RES_WIDTH / 2 - getWidth() / 2);
		setY(GameInfo.RES_HEIGHT / 2 - getHeight() / 2 + 7);

		showPlayerNamesButton = new MenuButton("", 20, 20, 80, 11);
		showFPSButton = new MenuButton("", 20, 20, 80, 11);
		muteMenuMusicButton = new MenuButton("", 20, 20, 80, 11);

		displayModeButton = new MenuButton("", 20, 20, 80, 11);
		windowSizeButton = new MenuButton("", 20, 20, 80, 11);
		vSyncButton = new MenuButton("", 20, 20, 80, 11);
		maintainAspectButton = new MenuButton("", 20, 20, 80, 11);

		keybindsButton = new MenuButton("", 20, 20, 80, 11);
		backButton = new MenuButton("<", 20, 20, 7, 7);


		String width = SettingManager.CONFIG_RES_WIDTH + "";
		String height = SettingManager.CONFIG_RES_HEIGHT + "";
		String theSize = width + "x" + height;

		for (int i = 0; i < Res.WINDOW_SIZE_LIST.size(); i++) {
			if (Res.WINDOW_SIZE_LIST.get(i).equalsIgnoreCase(theSize)) {
				selectedWindowSizeIndex = i;
			}
		}

		handleSetButtons();
	}

	public void update(GameContainer gc, StateBasedGame s, int delta) {
		
		displayModeButton.update(gc);
		vSyncButton.update(gc);
		maintainAspectButton.update(gc);

		showPlayerNamesButton.update(gc);
		showFPSButton.update(gc);
		muteMenuMusicButton.update(gc);

		backButton.update(gc);

		keybindsButton.update(gc);

		if (displayModeButton.isClicked()) {
			SettingManager.CONFIG_FULL_SCREEN++;
			if (SettingManager.CONFIG_FULL_SCREEN >= 3) {
				SettingManager.CONFIG_FULL_SCREEN = 0;
			}
			saveSettings(gc);
		}
		if (vSyncButton.isClicked()) {
			SettingManager.CONFIG_VSYNC = !SettingManager.CONFIG_VSYNC;
			saveSettings(gc);
		}
		if (maintainAspectButton.isClicked()) {
			SettingManager.CONFIG_MAINTAIN_ASPECT_RATIO = !SettingManager.CONFIG_MAINTAIN_ASPECT_RATIO;
			saveSettings(gc);
		}
		if (showFPSButton.isClicked()) {
			SettingManager.CONFIG_SHOWFPS = !SettingManager.CONFIG_SHOWFPS;
			saveSettings(gc);
		}
		if (showPlayerNamesButton.isClicked()) {
			SettingManager.CONFIG_SHOW_PLAYER_NAMES = !SettingManager.CONFIG_SHOW_PLAYER_NAMES;
			saveSettings(gc);
			if (eh != null) {
				Game.getGame().getClientSettings()
						.setShowPlayerNames(!Game.getGame().getClientSettings().isShowPlayerNames());
			}
		}
		if (muteMenuMusicButton.isClicked()) {
			SettingManager.MUTE_MENU_MUSIC = !SettingManager.MUTE_MENU_MUSIC;
			saveSettings(gc);
			if (MainMenuManager.getMainMenu().isInMenu()) {
				SoundStore.get().setMusicOn(!SettingManager.MUTE_MENU_MUSIC);
				SoundStore.get().setSoundsOn(!SettingManager.MUTE_MENU_MUSIC);
			}
		}

		if (SettingManager.CONFIG_FULL_SCREEN != 1 && SettingManager.CONFIG_FULL_SCREEN != 2) {
			windowSizeButton.update(gc);
			if (windowSizeButton.isClicked()) {
				selectedWindowSizeIndex++;
				if (selectedWindowSizeIndex >= Res.WINDOW_SIZE_LIST.size()) {
					selectedWindowSizeIndex = 0;
				}
				String selected = Res.WINDOW_SIZE_LIST.get(selectedWindowSizeIndex);
				String[] windowString;
				windowString = selected.split("x");
				SettingManager.CONFIG_RES_WIDTH = Integer.parseInt(windowString[0]);
				SettingManager.CONFIG_RES_HEIGHT = Integer.parseInt(windowString[1]);
				saveSettings(gc);
			}
		}

		if (backButton.isClicked()) {
			if (MainMenuManager.getMainMenu().isInMenu()) {
				if (!LobbyManager.getManager().getUserAccount().isLoggedIn()) {
					MainMenuManager.getMainMenu().getLoginUI().setWindow(true);
					setWindow(false);
				}
			}
			if (eh != null) {
				setWindow(false);
			}
		}

		if (keybindsButton.isClicked()) {
			if (MainMenuManager.getMainMenu().isInMenu()) {
				MainMenuManager.getMainMenu().setWindowUI(MainMenuManager.getMainMenu().getKeybindUI());
			} else {
				if (eh != null) {
					eh.getKeybindUI().setWindow(true);
					setWindow(false);
				}
			}
		}

		handleSetButtons();
	}

	public void render(GameContainer gc, Graphics g) {

		if (isRendering()) {
			renderWindow(g);

			displayModeButton.render(g, 3, gc);
			vSyncButton.render(g, 3, gc);
			maintainAspectButton.render(g, 3, gc);
			displayModeButton.render(g, 3, gc);
			showPlayerNamesButton.render(g, 3, gc);
			showFPSButton.render(g, 3, gc);
			muteMenuMusicButton.render(g, 3, gc);

			backButton.render(g, 0, gc);
			
			keybindsButton.render(g, 3, gc);

			if (SettingManager.CONFIG_FULL_SCREEN == 1 || SettingManager.CONFIG_FULL_SCREEN == 2) {
				windowSizeButton.renderHalf(g, 3);
			} else {
				windowSizeButton.render(g, 3, gc);
			}

		}
	}

	public void renderWindow(Graphics g) {

		Res.UI_RESOURCE.MAINMENU_SETTINGS_UI.draw(getX(), getY());
		
		String headerText = "Settings";
		Res.bitFont.drawString(getX() - Res.getTextCenter(headerText) + getWidth() / 2, getY() + 3, headerText, Color.white);

		Res.bitFont.drawString(getX() + 5, getY() + 30, "Display Mode");
		Res.bitFont.drawString(getX() + 5, getY() + 47, "V-Sync");
		Res.bitFont.drawString(getX() + 5, getY() + 64, "Maintain Aspect Ratio");
		Res.bitFont.drawString(getX() + 5, getY() + 81, "Show FPS");
		Res.bitFont.drawString(getX() + 5, getY() + 98, "Show Player Names");
		Res.bitFont.drawString(getX() + 5, getY() + 115, "Mute Menu Sounds");

		Color windowTextColor = Color.white;
		if (SettingManager.CONFIG_FULL_SCREEN == 1 || SettingManager.CONFIG_FULL_SCREEN == 2) {
			windowTextColor = Color.gray;
		}

		Res.bitFont.drawString(getX() + 5, getY() + 132, "Window Size", windowTextColor);
		
		
		Res.bitFont.drawString(getX() + 5, getY() + 149, "Game HUD");
		
		Res.bitFont.drawString(getX() + 5, getY() + 182, "Edit Keybindings");

		
	}

	public void saveSettings(GameContainer gc) {

		SettingManager.setConfigFile("fullscreen", String.valueOf(SettingManager.CONFIG_FULL_SCREEN));
		SettingManager.setConfigFile("maintainaspectratio",
				String.valueOf(SettingManager.CONFIG_MAINTAIN_ASPECT_RATIO));
		SettingManager.setConfigFile("vsync", String.valueOf(SettingManager.CONFIG_VSYNC));
		SettingManager.setConfigFile("showfps", String.valueOf(SettingManager.CONFIG_SHOWFPS));
		SettingManager.setConfigFile("showplayernames", String.valueOf(SettingManager.CONFIG_SHOW_PLAYER_NAMES));
		SettingManager.setConfigFile("mutemenumusic", String.valueOf(SettingManager.MUTE_MENU_MUSIC));
		SettingManager.setConfigFile("resolution", String.valueOf(SettingManager.CONFIG_RES_WIDTH) + "x"
				+ String.valueOf(SettingManager.CONFIG_RES_HEIGHT));
		SettingManager.setConfigFile("maxping", String.valueOf(SettingManager.CONFIG_MAX_PING));

		try {
			ClientEngine.resetDisplay(gc);
		} catch (SlickException e) {
			e.printStackTrace();
		}

	}

	public void handleSetButtons() {

		float centerForButtons = getX() + 196;

		displayModeButton.setPosition(centerForButtons, getY() + 27);

		vSyncButton.setPosition(centerForButtons, getY() + 44);

		if (SettingManager.CONFIG_VSYNC) {
			vSyncButton.setText("On");
			vSyncButton.setTextColor(Color.white);
			vSyncButton.setButtonColor(new Color(9, 183, 25));
		} else {
			vSyncButton.setText("Off");
			vSyncButton.setTextColor(Color.white);
			vSyncButton.setButtonColor(new Color(183, 29, 9));
		}

		maintainAspectButton.setPosition(centerForButtons, getY() + 61);


		if (SettingManager.CONFIG_MAINTAIN_ASPECT_RATIO) {
			maintainAspectButton.setText("On");
			maintainAspectButton.setTextColor(Color.white);
			maintainAspectButton.setButtonColor(new Color(9, 183, 25));
		} else {
			maintainAspectButton.setText("Off");
			maintainAspectButton.setTextColor(Color.white);
			maintainAspectButton.setButtonColor(new Color(183, 29, 9));
		}

		showFPSButton.setPosition(centerForButtons, getY() + 78);

		if (SettingManager.CONFIG_SHOWFPS) {
			showFPSButton.setText("On");
			showFPSButton.setTextColor(Color.white);
			showFPSButton.setButtonColor(new Color(9, 183, 25));
		} else {
			showFPSButton.setText("Off");
			showFPSButton.setTextColor(Color.white);
			showFPSButton.setButtonColor(new Color(183, 29, 9));
		}

		showPlayerNamesButton.setPosition(centerForButtons, getY() + 95);


		if (SettingManager.CONFIG_SHOW_PLAYER_NAMES) {
			showPlayerNamesButton.setText("On");
			showPlayerNamesButton.setTextColor(Color.white);
			showPlayerNamesButton.setButtonColor(new Color(9, 183, 25));
		} else {
			showPlayerNamesButton.setText("Off");
			showPlayerNamesButton.setTextColor(Color.white);
			showPlayerNamesButton.setButtonColor(new Color(183, 29, 9));
		}

		muteMenuMusicButton.setPosition(centerForButtons, getY() + 112);



		if (SettingManager.MUTE_MENU_MUSIC) {
			muteMenuMusicButton.setText("Muted");
			muteMenuMusicButton.setTextColor(Color.white);
			muteMenuMusicButton.setButtonColor(new Color(9, 183, 25));
		} else {
			muteMenuMusicButton.setText("Unmuted");
			muteMenuMusicButton.setTextColor(Color.white);
			muteMenuMusicButton.setButtonColor(new Color(183, 29, 9));
		}
		
		
		if (SettingManager.CONFIG_FULL_SCREEN == 1) {
			displayModeButton.setText("Borderless");
			windowSizeButton.setButtonColor(Color.gray);
		} else if (SettingManager.CONFIG_FULL_SCREEN == 2) {
			displayModeButton.setText("Fullscreen");
			windowSizeButton.setButtonColor(Color.gray);
		} else {
			displayModeButton.setText("Windowed");
			windowSizeButton.setButtonColor(Res.COLOR_RESOURCE.BLUE_BUTTON);
		}

		windowSizeButton.setText(SettingManager.CONFIG_RES_WIDTH + "x" + SettingManager.CONFIG_RES_HEIGHT);
		windowSizeButton.setPosition(centerForButtons, getY() + 129);

		keybindsButton.setText("Edit");
		keybindsButton.setPosition(centerForButtons, getY() + 179);
		
		
		backButton.setPosition(getX() + 2, getY() + 2);

	}

	public void mousePressed(int button, int x, int y) {

	}
}
