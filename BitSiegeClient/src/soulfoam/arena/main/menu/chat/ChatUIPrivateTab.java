package soulfoam.arena.main.menu.chat;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import com.sun.corba.se.pept.transport.Connection;

import soulfoam.arena.main.menu.MenuButton;
import soulfoam.arena.main.resources.Res;
import soulfoam.arena.net.lobby.LobbyManager;
import soulfoam.arenashared.main.lobbyopcode.ConnectionStatus;

public class ChatUIPrivateTab {

	private float x, y, width, height;
	private boolean isActive;

	private ArrayList<ChatUIPrivatePage> privatePages = new ArrayList<ChatUIPrivatePage>();
	private int pageIndex = 0;

	private MenuButton previousButton;
	private MenuButton nextButton;

	public ChatUIPrivateTab(float x, float y) {
		setX(x);
		setY(y);
		setWidth(380);
		setHeight(240);

		getPrivatePages().add(new ChatUIPrivatePage(getX(), getY() + 10));
	}

	public void update(GameContainer gc, int delta) {

		getPreviousButton().update(gc);
		getNextButton().update(gc);

		if (getPreviousButton().isClicked()) {
			setPageIndex(false);
		}

		if (getNextButton().isClicked()) {
			setPageIndex(true);
		}

		getPrivatePages().get(getPageIndex()).update(gc, delta);

	}

	public void render(GameContainer gc, Graphics g) {

		String headerText = LobbyManager.getManager().getFriendManager().getInteractionText();
		Res.bitFont.drawString(getX() - Res.getTextCenter(headerText) + getWidth() / 2, getY() + 9, headerText, LobbyManager.getManager().getFriendManager().getInteractionColor());

		String indexText = "Page: " + (pageIndex + 1) + "/" + getPrivatePages().size();
		Res.bitFont.drawString(getX() + 36, getY() + 213, indexText, Color.white);

		getPrivatePages().get(getPageIndex()).render(gc, g);

		getPreviousButton().render(g, 1, gc);
		getNextButton().render(g, 1, gc);

	}

	public void sortPages() {

		for (ChatUIPrivatePage page : privatePages) {
			page.sortPrivateItems();
		}

	}

	public void mouseWheelMoved(int m) {
		if (m > 0) { // downward wheel
			setPageIndex(true);
		} else if (m < 0) { // upward wheel
			setPageIndex(false);
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

	public ArrayList<ChatUIPrivatePage> getPrivatePages() {
		return privatePages;
	}

	public void setPrivatePages() {

		getPrivatePages().clear();

		ChatUIPrivateObject[] tempList = new ChatUIPrivateObject[LobbyManager.getManager().getUserAccount().getPrivateChats().size()];
		LobbyManager.getManager().getUserAccount().getPrivateChats().toArray(tempList);

		int count = 0;
		int index = 0;

		getPrivatePages().add(new ChatUIPrivatePage(getX(), getY() + 10));

		for (ChatUIPrivateObject privateItem : tempList) {
			if (privateItem.getConnectionStatus() == ConnectionStatus.ONLINE){
				count++;
				if (count == 11) {
					count = 1;
					index++; 
					getPrivatePages().add(new ChatUIPrivatePage(getX(), getY() + 10));
				}
	
				getPrivatePages().get(index).getPrivateItems().add(privateItem);	
			}
		}

	}

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(boolean add) {
		if (add) {
			pageIndex++;
			if (pageIndex >= getPrivatePages().size() - 1) {
				pageIndex = getPrivatePages().size() - 1;
			}
		} else {
			pageIndex--;
			if (pageIndex <= 0) {
				pageIndex = 0;
			}
		}
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public MenuButton getNextButton() {
		if (nextButton == null) {
			setNextButton(new MenuButton(">", getX() + 18, getY() + 211, 12, 9));
		}
		return nextButton;
	}

	public void setNextButton(MenuButton nextButton) {
		this.nextButton = nextButton;
	}

	public MenuButton getPreviousButton() {
		if (previousButton == null) {
			setPreviousButton(new MenuButton("<", getX() + 3, getY() + 211, 12, 9));
		}
		return previousButton;
	}

	public void setPreviousButton(MenuButton previousButton) {
		this.previousButton = previousButton;
	}

}
