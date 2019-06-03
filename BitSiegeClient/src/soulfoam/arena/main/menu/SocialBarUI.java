package soulfoam.arena.main.menu;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.StateBasedGame;

import soulfoam.arena.main.resources.Res;
import soulfoam.arena.net.lobby.LobbyManager;
import soulfoam.arenashared.main.gameinfo.GameInfo;
import soulfoam.arenashared.main.ids.AvatarLibrary;

public class SocialBarUI extends BaseMenuUI {

	private Rectangle requestsButton;
	private Rectangle friendsButton;
	private Rectangle chatButton;
	private Rectangle profileButton;
	private Rectangle leaderboardButton;
	private Rectangle buySiegePointsButton;
	
	public SocialBarUI() {

		setWidth(38);
		setHeight(GameInfo.RES_HEIGHT);
		setX(GameInfo.RES_WIDTH - getWidth());
		setY(0);
		
		requestsButton = new Rectangle(getX() - 4, getY() + 45, 40, 16);
		friendsButton = new Rectangle(getX() - 4, getY() + 63, 40, 16);
		chatButton = new Rectangle(getX() - 4, getY() + 81, 40, 16);
		profileButton = new Rectangle(getX() - 4, getY() + 99, 40, 16);
		buySiegePointsButton = new Rectangle(getX() - 3, getY() + 219, 38, 20);
		
	}

	public void update(GameContainer gc, StateBasedGame s, int delta) {
	}

	public void render(GameContainer gc, Graphics g) {

		if (isRendering()) {
			
			if (LobbyManager.getManager().getUserAccount() != null
					&& LobbyManager.getManager().getUserAccount().getAvatarInfo() != null
					&& LobbyManager.getManager().getUserAccount().getAvatarInfo().length > 0) {

				Res.AVATAR_RESOURCE
						.getAvatarImage(LobbyManager.getManager().getUserAccount().getAvatarInfo()[0].getID(),
								AvatarLibrary.SLOT_BACKGROUND)
						.draw(getX(), getY() + 4, 32, 32);
				Res.AVATAR_RESOURCE
						.getAvatarImage(LobbyManager.getManager().getUserAccount().getAvatarInfo()[1].getID(),
								AvatarLibrary.SLOT_BORDER)
						.draw(getX(), getY() + 4, 32, 32);
				Res.AVATAR_RESOURCE
						.getAvatarImage(LobbyManager.getManager().getUserAccount().getAvatarInfo()[2].getID(),
								AvatarLibrary.SLOT_ICON)
						.draw(getX(), getY() + 4, 32, 32);

			}

			
			Res.bitFont.drawString((getX() - 3) + getWidth()/2 - Res.getTextCenter(LobbyManager.getManager().getUserAccount().getSiegePoints() + ""), getY() + 170, 
					LobbyManager.getManager().getUserAccount().getSiegePoints() + "", Res.COLOR_RESOURCE.ORANGE_TEXT);
			Res.bitFont.drawString((getX() - 3) + getWidth()/2 - Res.getTextCenter(LobbyManager.getManager().getUserAccount().getBits() + ""), getY() + 203, 
					LobbyManager.getManager().getUserAccount().getBits() + "", Res.COLOR_RESOURCE.GREEN_TEXT);
			
			
			if (!LobbyManager.getManager().getUserAccount().getRequests().isEmpty()) {
				Res.UI_RESOURCE.NEW_NOTIFICATION_BUTTON.draw(requestsButton.getX(), requestsButton.getY());
			}
			if (MainMenuManager.getMainMenu().getChatUI().hasUnreadMessages()){
				Res.UI_RESOURCE.NEW_CHAT_BUTTON.draw(chatButton.getX(), chatButton.getY());
			}
			
			if (requestsButton.contains(gc.getInput().getMouseX(), gc.getInput().getMouseY())) {
				Res.UI_RESOURCE.MAINMENU_BUTTON_SELECT.draw(requestsButton.getX(), requestsButton.getY());
			}
			if (friendsButton.contains(gc.getInput().getMouseX(), gc.getInput().getMouseY())) {
				Res.UI_RESOURCE.MAINMENU_BUTTON_SELECT.draw(friendsButton.getX(), friendsButton.getY());
			}
			if (chatButton.contains(gc.getInput().getMouseX(), gc.getInput().getMouseY())) {
				Res.UI_RESOURCE.MAINMENU_BUTTON_SELECT.draw(chatButton.getX(), chatButton.getY());
			}
			if (profileButton.contains(gc.getInput().getMouseX(), gc.getInput().getMouseY())) {
				Res.UI_RESOURCE.MAINMENU_BUTTON_SELECT.draw(profileButton.getX(), profileButton.getY());
			}
			if (buySiegePointsButton.contains(gc.getInput().getMouseX(), gc.getInput().getMouseY())) {
				Res.UI_RESOURCE.MAINMENU_BUTTON_SELECT.draw(buySiegePointsButton.getX(), buySiegePointsButton.getY(), 38, 20);
			}
		}
	}


	public void mousePressed(int button, int x, int y) {
		if (button == Input.MOUSE_LEFT_BUTTON){
			if (requestsButton.contains(x, y)) {
				MainMenuManager.getMainMenu().setWindowUI(MainMenuManager.getMainMenu().getRequestUI());
				MainMenuManager.getMainMenu().setSelectedButton(requestsButton);
			}
			if (friendsButton.contains(x, y)) {
				MainMenuManager.getMainMenu().setWindowUI(MainMenuManager.getMainMenu().getFriendUI());
				MainMenuManager.getMainMenu().setSelectedButton(friendsButton);
			}
			if (chatButton.contains(x, y)) {
				MainMenuManager.getMainMenu().setWindowUI(MainMenuManager.getMainMenu().getChatUI());
				MainMenuManager.getMainMenu().setSelectedButton(chatButton);
			}
			if (profileButton.contains(x, y)) {
				MainMenuManager.getMainMenu().setWindowUI(MainMenuManager.getMainMenu().getProfileUI());
				MainMenuManager.getMainMenu().setSelectedButton(profileButton);
			}
			if (buySiegePointsButton.contains(x, y)){
				Res.openURL("bitsiege.com/purchase.php");
				MainMenuManager.getMainMenu().setWindowUI(MainMenuManager.getMainMenu().getStoreUI());
				MainMenuManager.getMainMenu().setSelectedButton(MainMenuManager.getMainMenu().getNavigationBarUI().getStoreButton());
				LobbyManager.getManager().getStoreManager().setStoreText("Purchase page has been opened in your browser...", Color.green);
			}
		}
	}
}
