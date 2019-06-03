package soulfoam.arena.main.menu.news;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

import soulfoam.arena.main.menu.BaseMenuUI;
import soulfoam.arena.main.resources.Res;
import soulfoam.arena.net.lobby.LobbyManager;
import soulfoam.arenashared.main.gameinfo.GameInfo;

public class NewsUI extends BaseMenuUI {

	public NewsUI() {
		setWidth(380);
		setHeight(240);
		setX(GameInfo.RES_WIDTH / 2 - getWidth() / 2);
		setY(GameInfo.RES_HEIGHT / 2 - getHeight() / 2 + 9);
	}

	public void update(GameContainer gc, StateBasedGame s, int delta) {

	}

	public void render(GameContainer gc, Graphics g) {

		if (isRendering()) {
			renderWindow(g);
			
			
			String welcomeText = "Hello, " + LobbyManager.getManager().getUserAccount().getUsername() + "!";
			Res.drawNewLineText(getX() - Res.getTextCenter(welcomeText) + getWidth() / 2, getY() + 10, welcomeText, Color.green);
			
			String headerText = "Welcome to Bit Siege Open Beta v0.0.0.1";
			Res.drawNewLineText(getX() - Res.getTextCenter(headerText) + getWidth() / 2, getY() + 22, headerText, Color.green);
			
			
			Res.drawNewLineText(getX() + 8, getY() + 54,
					"Thank you so much for playing Bit Siege!",
					Color.white);
			
			Res.drawNewLineText(getX() + 8, getY() + 76,
					"This is an open beta, so there may be crashing and bugs,\nplease report any issues you find."
					+ "\n\nYou can report any issues as well as leave feedback on the\nforums :). Thank you!", Color.white);

			Res.drawNewLineText(getX() + 8, getY() + 134,
					"Nothing is final, everything is subject to change. \nMany new features and mechanics are being implemented.",
					Color.white);
			
			Res.drawNewLineText(getX() + 8, getY() + 210,
					"Enjoy your Bit Siege experience! Thanks again for playing!",
					Color.white);
		}
	}

	public void renderWindow(Graphics g) {
		
		Res.UI_RESOURCE.MAINMENU_NEWS_UI.draw(getX(), getY());

		String headerText = "News";
		Res.bitFont.drawString(getX() - Res.getTextCenter(headerText) + getWidth() / 2, getY() + 3, headerText, Color.white);
		
	}

	public void mousePressed(int button, int x, int y) {

	}
}
