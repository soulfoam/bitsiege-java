package soulfoam.arena.main.menu.play;

import java.awt.Font;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.state.StateBasedGame;

import soulfoam.arena.main.SettingManager;
import soulfoam.arena.main.menu.BaseMenuUI;
import soulfoam.arena.main.menu.MainMenuManager;
import soulfoam.arena.main.menu.MenuButton;
import soulfoam.arena.main.resources.Res;
import soulfoam.arena.net.lobby.LobbyManager;
import soulfoam.arenashared.main.account.GameRegion;
import soulfoam.arenashared.main.gameinfo.GameInfo;

public class PlayUI extends BaseMenuUI {

	private int regionPick = SettingManager.SEARCH_REGION;

	private MenuButton searchButton;
	private MenuButton previousRegionButton;
	private MenuButton nextRegionButton;

	public PlayUI() {

		setWidth(199);
		setHeight(100);
		setX(GameInfo.RES_WIDTH / 2 - getWidth() / 2);
		setY(GameInfo.RES_HEIGHT / 2 - getHeight() / 2);
		
		previousRegionButton = new MenuButton("<", getX() + 60, getY() + 23, 16, 9);
		nextRegionButton = new MenuButton(">", getX() + 123, getY() + 23, 16, 9);
		searchButton = new MenuButton("Search", getX() + 50, getY() + 76, 100, 11);

	}

	public void update(GameContainer gc, StateBasedGame s, int delta) {

		searchButton.update(gc);
		previousRegionButton.update(gc);
		nextRegionButton.update(gc);

		MainMenuManager.getMainMenu().getPartyUI().updatePartyManagement(gc, delta);

		if (LobbyManager.getManager().getMatchMakingManager().isSearching()) {
			searchButton.setEnabled(true);
			searchButton.setText("Cancel");
		} else {
			if (MainMenuManager.getMainMenu().getPartyUI().isLeader()) {
				searchButton.setEnabled(true);
				searchButton.setText("Search");
			} else {
				searchButton.setEnabled(false);
				searchButton.setText("Search");
			}
		}

		if (searchButton.isClicked()) {
			if (!LobbyManager.getManager().getMatchMakingManager().isSearching()) {
				LobbyManager.getManager().getMatchMakingManager().joinMatchMaking(regionPick);
			} else {
				LobbyManager.getManager().getMatchMakingManager().cancelMatchMaking();
			}
		}

		if (previousRegionButton.isClicked()) {
			setRegionPick(false);
		}

		if (nextRegionButton.isClicked()) {
			setRegionPick(true);
		}

	}

	public void render(GameContainer gc, Graphics g) {

		if (isRendering()) {
			renderWindow(g);
			getSearchButton().render(g, 2, gc);
			getPreviousRegionButton().render(g, 1, gc);
			getNextRegionButton().render(g, 1, gc);

		}

	}

	public void renderWindow(Graphics g) {
		
		Res.UI_RESOURCE.MAINMENU_PLAY_UI.draw(getX(), getY());
		
		String headerText = "Play";
		Res.bitFont.drawString(getX() - Res.getTextCenter(headerText) + getWidth() / 2 - 1, getY() + 3, headerText, Color.white);

		Res.bitFont.drawString(getX() - Res.getTextCenter(GameRegion.getRegionName(regionPick)) + getWidth() / 2 - 1,
				getY() + 38, GameRegion.getRegionName(regionPick), Color.orange);
	}

	public void mousePressed(int button, int x, int y) {

	}

	public int getRegionPick() {
		return regionPick;
	}

	public void setRegionPick(boolean add) {

		if (add) {
			regionPick++;
		} else {
			regionPick--;
		}

		if (regionPick < 0) {
			regionPick = GameRegion.REGION_COUNT - 1;
		} else if (regionPick >= GameRegion.REGION_COUNT) {
			regionPick = 0;
		}

	}

	public MenuButton getSearchButton() {
		return searchButton;
	}

	public MenuButton getPreviousRegionButton() {
		return previousRegionButton;
	}

	public MenuButton getNextRegionButton() {
		return nextRegionButton;
	}

}
