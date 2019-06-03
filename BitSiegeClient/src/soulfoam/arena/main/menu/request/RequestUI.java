package soulfoam.arena.main.menu.request;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

import soulfoam.arena.main.menu.BaseMenuUI;
import soulfoam.arena.main.menu.MenuButton;
import soulfoam.arena.main.resources.Res;
import soulfoam.arena.net.lobby.LobbyManager;
import soulfoam.arenashared.main.gameinfo.GameInfo;

public class RequestUI extends BaseMenuUI {

	private ArrayList<RequestUIPage> requestPages = new ArrayList<RequestUIPage>();
	private int pageIndex = 0;

	private MenuButton previousButton;
	private MenuButton nextButton;

	public RequestUI() {

		setWidth(380);
		setHeight(240);
		setX(GameInfo.RES_WIDTH / 2 - getWidth() / 2);
		setY(GameInfo.RES_HEIGHT / 2 - getHeight() / 2 + 9);

		previousButton = new MenuButton("<", getX() + 3, getY() + 228, 12, 9);
		nextButton = new MenuButton(">", getX() + 18, getY() + 228, 12, 9);

		requestPages.add(new RequestUIPage(getX(), getY() + 10));

	}

	public void update(GameContainer gc, StateBasedGame s, int delta) {

		previousButton.update(gc);
		nextButton.update(gc);

		if (previousButton.isClicked()) {
			setPageIndex(false);
		}

		if (nextButton.isClicked()) {
			setPageIndex(true);
		}

		requestPages.get(pageIndex).update(gc, delta);

	}

	public void setWindow(boolean set) {
		setFocus(set);
		setRender(set);
		LobbyManager.getManager().getRequestManager().setRequestText(LobbyManager.getManager().getRequestManager().getDefaultRequestText(), Color.yellow);
	}

	public void render(GameContainer gc, Graphics g) {

		if (isRendering()) {
			renderWindow(g);

			String indexText = "Page: " + (pageIndex + 1) + "/" + requestPages.size();
			Res.bitFont.drawString(getX() + 36, getY() + 230, indexText, Color.white);

			requestPages.get(pageIndex).render(gc, g);

			previousButton.render(g, 1, gc);
			nextButton.render(g, 1, gc);

		}
	}

	public void renderWindow(Graphics g) {
		
		Res.UI_RESOURCE.MAINMENU_REQUEST_UI.draw(getX(), getY());

		String headerText = "Requests (" + LobbyManager.getManager().getUserAccount().getRequests().size() + ")";
		Res.bitFont.drawString(getX() - Res.getTextCenter(headerText) + getWidth() / 2, getY() + 3, headerText,
				Color.white);

		Res.bitFont.drawString(
				getX() - Res.getTextCenter(LobbyManager.getManager().getRequestManager().getRequestText())
						+ getWidth() / 2,
				getY() + 17, LobbyManager.getManager().getRequestManager().getRequestText(),
				LobbyManager.getManager().getRequestManager().getRequestColor());

	}

	public void mousePressed(int button, int x, int y) {

	}

	public void mouseWheelMoved(int m) {
		if (m > 0) { // downward wheel
			setPageIndex(true);
		} else if (m < 0) { // upward wheel
			setPageIndex(false);
		}
	}

	public void sortPages() {

		for (RequestUIPage page : requestPages) {
			page.sortRequests();
		}

	}

	public ArrayList<RequestUIPage> getRequestPages() {
		return requestPages;
	}

	public void setRequestPages() {

		requestPages.clear();

		RequestObject[] tempList = new RequestObject[LobbyManager.getManager().getUserAccount().getRequests().size()];
		LobbyManager.getManager().getUserAccount().getRequests().toArray(tempList);

		int count = 0;
		int index = 0;

		requestPages.add(new RequestUIPage(getX(), getY() + 10));

		for (RequestObject request : tempList) {
			count++;
			if (count == 10) {
				count = 1;
				index++;
				requestPages.add(new RequestUIPage(getX(), getY() + 10));
			}

			requestPages.get(index).getRequestItems().add(request);
		}
	}

	public void setPageIndex(boolean add) {
		if (add) {
			pageIndex++;
			if (pageIndex >= requestPages.size() - 1) {
				pageIndex = requestPages.size() - 1;
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

}
