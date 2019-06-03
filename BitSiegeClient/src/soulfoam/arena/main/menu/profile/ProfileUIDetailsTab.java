package soulfoam.arena.main.menu.profile;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import soulfoam.arena.main.menu.MenuButton;
import soulfoam.arena.main.resources.Res;
import soulfoam.arena.net.lobby.LobbyManager;
import soulfoam.arenashared.main.gameinfo.GameInfo;

public class ProfileUIDetailsTab {

	private float x, y, width, height;
	private boolean isActive;
	private MenuButton invisibleButton;

	public ProfileUIDetailsTab(GameContainer gc, float x, float y) {
		setWidth(380);
		setHeight(240);
		setX(GameInfo.RES_WIDTH / 2 - getWidth() / 2);
		setY(GameInfo.RES_HEIGHT / 2 - getHeight() / 2 + 9);

		setInvisibleButton(new MenuButton("No", getX() + 148, getY() + 174, 82, 13));

	}

	public void update(GameContainer gc, int delta) {

		getInvisibleButton().update(gc);

		if (getInvisibleButton().isClicked()) {
			LobbyManager.getManager().getUserAccount()
					.setInvisible(!LobbyManager.getManager().getUserAccount().isInvisible());
			LobbyManager.getManager().getAccountManager()
					.changeAppearOffline(LobbyManager.getManager().getUserAccount().isInvisible());
		}

		handleButtons();
	}

	public void render(GameContainer gc, Graphics g) {

		String usernameText = "Username:";
		Res.bitFont.drawString(getX() - Res.getTextCenter(usernameText) + getWidth() / 2, getY() + 42, usernameText);

		String username = LobbyManager.getManager().getUserAccount().getUsername();
		Res.bitFont.drawString(getX() - Res.getTextCenter(username) + getWidth() / 2, getY() + 56, username,
				LobbyManager.getManager().getUserAccount().getNameColor());

		String creationDateText = "Creation Date:";
		Res.bitFont.drawString(getX() - Res.getTextCenter(creationDateText) + getWidth() / 2, getY() + 83,
				creationDateText);

		Date lld = new Date(LobbyManager.getManager().getUserAccount().getCreationDate());
		DateFormat df = new SimpleDateFormat("MMMM dd, yyyy HH:mm:ss", Locale.US);

		String creationDate = df.format(lld);
		Res.bitFont.drawString(getX() - Res.getTextCenter(creationDate) + getWidth() / 2, getY() + 97, creationDate,
				Color.green);


		String appearOfflineText = "Appear Offline:";
		Res.bitFont.drawString(getX() - Res.getTextCenter(appearOfflineText) + getWidth() / 2, getY() + 163,
				appearOfflineText);

		getInvisibleButton().render(g, 3, gc);

	}

	public void handleButtons() {
		if (LobbyManager.getManager().getUserAccount().isInvisible()) {
			getInvisibleButton().setText("Yes");
		} else {
			getInvisibleButton().setText("No");
		}
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public MenuButton getInvisibleButton() {
		return invisibleButton;
	}

	public void setInvisibleButton(MenuButton invisButton) {
		invisibleButton = invisButton;
	}

}
