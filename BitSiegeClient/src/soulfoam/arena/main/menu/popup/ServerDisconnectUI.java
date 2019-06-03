package soulfoam.arena.main.menu.popup;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.state.StateBasedGame;

import soulfoam.arena.main.menu.BaseMenuUI;
import soulfoam.arena.main.menu.MenuButton;
import soulfoam.arena.main.resources.Res;
import soulfoam.arena.net.lobby.LobbyManager;
import soulfoam.arenashared.main.gameinfo.GameInfo;

public class ServerDisconnectUI extends BaseMenuUI {

	public MenuButton reconnectButton;
	public MenuButton backButton;

	public String messageText = "Sorry, the server disconnected :'(";
	public Color messageColor = new Color(255, 150, 0);

	public ServerDisconnectUI() {
		setWidth(265);
		setHeight(61);
		setX(GameInfo.RES_WIDTH / 2 - getWidth() / 2);
		setY(GameInfo.RES_HEIGHT / 2 - getHeight() / 2);
		
		reconnectButton = new MenuButton("Attempt Reconnect", getX() + 54, getY() + 26, 154, 10);
		backButton = new MenuButton("Exit Game", getX() + 54, getY() + 45, 154, 10);

	}

	public void update(GameContainer gc, StateBasedGame s, int delta) {

		reconnectButton.update(gc);
		backButton.update(gc);

		if (reconnectButton.isClicked()) {
			if (LobbyManager.getManager().initLobbyClient()) {
				LobbyManager.getManager().logoutUserAccount();
				messageText = "Successfully reconnected...!";
			} else {
				messageText = "Failed to reconnect...";
			}
		}

		if (backButton.isClicked()) {
			gc.exit();
		}

	}

	public void render(GameContainer gc, Graphics g) {

		if (isRendering()) {
			renderWindow(g);

			reconnectButton.render(g, 2, gc);
			backButton.render(g, 2, gc);
		}
	}

	public void renderWindow(Graphics g) {
		
		Res.UI_RESOURCE.MAINMENU_SERVER_DISCONNECT_UI.draw(getX(), getY());
		
		Res.bitFont.drawString(getX() + getWidth() / 2 - Res.getTextCenter(messageText), getY() + 6, messageText, new Color(255, 150, 0));
	}
	public void mousePressed(int button, int x, int y) {
		if (button == Input.MOUSE_LEFT_BUTTON) {

		}
	}

	public void resetMessageText() {
		messageText = "Sorry, the server disconnected :'(";
		messageColor = new Color(255, 150, 0);
	}
}
