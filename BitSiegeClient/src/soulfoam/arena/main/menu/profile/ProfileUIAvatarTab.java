package soulfoam.arena.main.menu.profile;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import soulfoam.arena.main.menu.MenuButton;
import soulfoam.arena.main.resources.Res;
import soulfoam.arena.net.lobby.LobbyManager;
import soulfoam.arenashared.main.gameinfo.GameInfo;
import soulfoam.arenashared.main.ids.AvatarLibrary;

public class ProfileUIAvatarTab {

	private float x, y, width, height;
	private boolean isActive;

	private int selectedBGID;
	private int selectedBorderID;
	private int selectedIconID;

	private MenuButton backgroundBackButton;
	private MenuButton backgroundForwardButton;
	private MenuButton borderBackButton;
	private MenuButton borderForwardButton;
	private MenuButton iconBackButton;
	private MenuButton iconForwardButton;

	private MenuButton saveButton;

	public ProfileUIAvatarTab(GameContainer gc, float x, float y) {
		setWidth(380);
		setHeight(240);
		setX(GameInfo.RES_WIDTH / 2 - getWidth() / 2);
		setY(GameInfo.RES_HEIGHT / 2 - getHeight() / 2 + 9);

		setBackgroundBackButton(new MenuButton("<", getX() + 138, getY() + 107, 16, 9));
		setBackgroundForwardButton(new MenuButton(">", getX() + 227, getY() + 107, 16, 9));

		setBorderBackButton(new MenuButton("<", getX() + 138, getY() + 123, 16, 9));
		setBorderForwardButton(new MenuButton(">", getX() + 227, getY() + 123, 16, 9));

		setIconBackButton(new MenuButton("<", getX() + 138, getY() + 139, 16, 9));
		setIconForwardButton(new MenuButton(">", getX() + 227, getY() + 139, 16, 9));

		setSaveButton(new MenuButton("Apply Changes", getX() + 140, getY() + 205, 100, 10));

	}

	public void update(GameContainer gc, int delta) {

		getBackgroundBackButton().update(gc);
		getBackgroundForwardButton().update(gc);
		getBorderBackButton().update(gc);
		getBorderForwardButton().update(gc);
		getIconBackButton().update(gc);
		getIconForwardButton().update(gc);
		getSaveButton().update(gc);

		if (getBackgroundBackButton().isClicked()) {
			setSelectedBGID(false);
		}
		if (getBackgroundForwardButton().isClicked()) {
			setSelectedBGID(true);
		}

		if (getBorderBackButton().isClicked()) {
			setSelectedBorderID(false);
		}
		if (getBorderForwardButton().isClicked()) {
			setSelectedBorderID(true);
		}

		if (getIconBackButton().isClicked()) {
			setSelectedIconID(false);
		}
		if (getIconForwardButton().isClicked()) {
			setSelectedIconID(true);
		}

		if (getSaveButton().isClicked()) {
			LobbyManager.getManager().getAvatarManager().changeAvatar(
					LobbyManager.getManager().getUserAccount().getUnlockedAvatars(AvatarLibrary.SLOT_BACKGROUND)
							.get(selectedBGID).getID(),
					LobbyManager.getManager().getUserAccount().getUnlockedAvatars(AvatarLibrary.SLOT_BORDER)
							.get(selectedBorderID).getID(),
					LobbyManager.getManager().getUserAccount().getUnlockedAvatars(AvatarLibrary.SLOT_ICON)
							.get(selectedIconID).getID());
		}

	}

	public void render(GameContainer gc, Graphics g) {

		String headerText = LobbyManager.getManager().getAvatarManager().getAvatarText();
		Res.bitFont.drawString(getX() - Res.getTextCenter(headerText) + getWidth() / 2, getY() + 26, headerText,
				LobbyManager.getManager().getAvatarManager().getAvatarColor());


		LobbyManager.getManager().getUserAccount().getUnlockedAvatars(AvatarLibrary.SLOT_BACKGROUND).get(selectedBGID)
				.getImage().draw(getX() - 17 + getWidth() / 2, getY() + 61, 32, 32);

		LobbyManager.getManager().getUserAccount().getUnlockedAvatars(AvatarLibrary.SLOT_BORDER).get(selectedBorderID)
				.getImage().draw(getX() - 17 + getWidth() / 2, getY() + 61, 32, 32);

		LobbyManager.getManager().getUserAccount().getUnlockedAvatars(AvatarLibrary.SLOT_ICON).get(selectedIconID)
				.getImage().draw(getX() - 17 + getWidth() / 2, getY() + 61, 32, 32);

		String backgroundText = "Background";
		Res.bitFont.drawString(getX() - Res.getTextCenter(backgroundText) + getWidth() / 2, getY() + 109, backgroundText,
				Color.white);

		String borderText = "Border";
		Res.bitFont.drawString(getX() - Res.getTextCenter(borderText) + getWidth() / 2, getY() + 125, borderText,
				Color.white);

		String iconText = "Icon";
		Res.bitFont.drawString(getX() - Res.getTextCenter(iconText) + getWidth() / 2, getY() + 141, iconText,
				Color.white);

		String bgsUnlockedText = "Background: " + "(" + (getSelectedBGID() + 1) + "/"
				+ LobbyManager.getManager().getUserAccount().getUnlockedAvatars(AvatarLibrary.SLOT_BACKGROUND).size()
				+ ")";
		Res.bitFont.drawString(getX() - Res.getTextCenter(bgsUnlockedText) + getWidth() / 2, getY() + 163,
				bgsUnlockedText, Color.white);

		String bordersUnlockedText = "Border: " + "(" + (getSelectedBorderID() + 1) + "/"
				+ LobbyManager.getManager().getUserAccount().getUnlockedAvatars(AvatarLibrary.SLOT_BORDER).size() + ")";
		Res.bitFont.drawString(getX() - Res.getTextCenter(bordersUnlockedText) + getWidth() / 2, getY() + 173,
				bordersUnlockedText, Color.white);

		String iconsUnlockedText = "Icons: " + "(" + (getSelectedIconID() + 1) + "/"
				+ LobbyManager.getManager().getUserAccount().getUnlockedAvatars(AvatarLibrary.SLOT_ICON).size() + ")";
		Res.bitFont.drawString(getX() - Res.getTextCenter(iconsUnlockedText) + getWidth() / 2, getY() + 183,
				iconsUnlockedText, Color.white);

		getBackgroundBackButton().render(g, 1, gc);
		getBackgroundForwardButton().render(g, 1, gc);
		getBorderBackButton().render(g, 1, gc);
		getBorderForwardButton().render(g, 1, gc);
		getIconBackButton().render(g, 1, gc);
		getIconForwardButton().render(g, 1, gc);
		getSaveButton().render(g, 2, gc);

	}

	public void setAvatarPreviewToAccountAvatar() {
		for (int i = 0; i < LobbyManager.getManager().getUserAccount().getUnlockedAvatars(AvatarLibrary.SLOT_BACKGROUND)
				.size(); i++) {
			if (LobbyManager.getManager().getUserAccount().getUnlockedAvatars(AvatarLibrary.SLOT_BACKGROUND).get(i)
					.getID() == LobbyManager.getManager().getUserAccount()
							.getAvatarInfo()[AvatarLibrary.SLOT_BACKGROUND].getID()) {
				selectedBGID = i;
			}
		}

		for (int i = 0; i < LobbyManager.getManager().getUserAccount().getUnlockedAvatars(AvatarLibrary.SLOT_BORDER)
				.size(); i++) {
			if (LobbyManager.getManager().getUserAccount().getUnlockedAvatars(AvatarLibrary.SLOT_BORDER).get(i)
					.getID() == LobbyManager.getManager().getUserAccount().getAvatarInfo()[AvatarLibrary.SLOT_BORDER]
							.getID()) {
				selectedBorderID = i;
			}
		}

		for (int i = 0; i < LobbyManager.getManager().getUserAccount().getUnlockedAvatars(AvatarLibrary.SLOT_ICON)
				.size(); i++) {
			if (LobbyManager.getManager().getUserAccount().getUnlockedAvatars(AvatarLibrary.SLOT_ICON).get(i)
					.getID() == LobbyManager.getManager().getUserAccount().getAvatarInfo()[AvatarLibrary.SLOT_ICON]
							.getID()) {
				selectedIconID = i;
			}
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

		LobbyManager.getManager().getAvatarManager()
				.setAvatarText(LobbyManager.getManager().getAvatarManager().getDefaultAvatarText(), Color.yellow);
	}

	public MenuButton getBackgroundBackButton() {
		return backgroundBackButton;
	}

	public void setBackgroundBackButton(MenuButton backgroundBackButton) {
		this.backgroundBackButton = backgroundBackButton;
	}

	public MenuButton getBackgroundForwardButton() {
		return backgroundForwardButton;
	}

	public void setBackgroundForwardButton(MenuButton backgroundForwardButton) {
		this.backgroundForwardButton = backgroundForwardButton;
	}

	public MenuButton getBorderBackButton() {
		return borderBackButton;
	}

	public void setBorderBackButton(MenuButton borderBackButton) {
		this.borderBackButton = borderBackButton;
	}

	public MenuButton getBorderForwardButton() {
		return borderForwardButton;
	}

	public void setBorderForwardButton(MenuButton borderForwardButton) {
		this.borderForwardButton = borderForwardButton;
	}

	public MenuButton getIconBackButton() {
		return iconBackButton;
	}

	public void setIconBackButton(MenuButton iconBackButton) {
		this.iconBackButton = iconBackButton;
	}

	public MenuButton getIconForwardButton() {
		return iconForwardButton;
	}

	public void setIconForwardButton(MenuButton iconForwardButton) {
		this.iconForwardButton = iconForwardButton;
	}

	public int getSelectedBGID() {
		return selectedBGID;
	}

	public void setSelectedBGID(boolean add) {
		if (add) {
			selectedBGID++;
		} else {
			selectedBGID--;
		}

		if (selectedBGID < 0) {
			selectedBGID = LobbyManager.getManager().getUserAccount().getUnlockedAvatars(AvatarLibrary.SLOT_BACKGROUND)
					.size() - 1;
		} else if (selectedBGID >= LobbyManager.getManager().getUserAccount()
				.getUnlockedAvatars(AvatarLibrary.SLOT_BACKGROUND).size()) {
			selectedBGID = 0;
		}
	}

	public int getSelectedBorderID() {
		return selectedBorderID;
	}

	public void setSelectedBorderID(boolean add) {
		if (add) {
			selectedBorderID++;
		} else {
			selectedBorderID--;
		}

		if (selectedBorderID < 0) {
			selectedBorderID = LobbyManager.getManager().getUserAccount().getUnlockedAvatars(AvatarLibrary.SLOT_BORDER)
					.size() - 1;
		} else if (selectedBorderID >= LobbyManager.getManager().getUserAccount()
				.getUnlockedAvatars(AvatarLibrary.SLOT_BORDER).size()) {
			selectedBorderID = 0;
		}
	}

	public int getSelectedIconID() {
		return selectedIconID;
	}

	public void setSelectedIconID(boolean add) {
		if (add) {
			selectedIconID++;
		} else {
			selectedIconID--;
		}

		if (selectedIconID < 0) {
			selectedIconID = LobbyManager.getManager().getUserAccount().getUnlockedAvatars(AvatarLibrary.SLOT_ICON)
					.size() - 1;
		} else if (selectedIconID >= LobbyManager.getManager().getUserAccount()
				.getUnlockedAvatars(AvatarLibrary.SLOT_ICON).size()) {
			selectedIconID = 0;
		}
	}

	public MenuButton getSaveButton() {
		return saveButton;
	}

	public void setSaveButton(MenuButton saveButton) {
		this.saveButton = saveButton;
	}
}
