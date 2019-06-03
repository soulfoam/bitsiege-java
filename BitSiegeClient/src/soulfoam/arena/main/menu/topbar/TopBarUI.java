package soulfoam.arena.main.menu.topbar;

import java.util.ArrayList;
import java.util.regex.Pattern;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.StateBasedGame;

import soulfoam.arena.main.menu.BaseMenuUI;
import soulfoam.arena.main.menu.MainMenuManager;
import soulfoam.arena.main.menu.MenuButton;
import soulfoam.arena.main.resources.Res;
import soulfoam.arena.net.lobby.LobbyManager;
import soulfoam.arenashared.main.gameinfo.GameInfo;

public class TopBarUI extends BaseMenuUI {

	private ArrayList<PartyMemberObjectTopBar> partyMembers = new ArrayList<PartyMemberObjectTopBar>();
	private Rectangle partySettingsButton;

	public TopBarUI() {
		setWidth(GameInfo.RES_WIDTH);
		setHeight(18);
		setX(GameInfo.RES_WIDTH / 2 - getWidth() / 2);
		setY(0);
		
		partySettingsButton = new Rectangle(getX() + 130, getY() + 5, 9, 10);
		
	}

	public void update(GameContainer gc, StateBasedGame s, int delta) {

		int display = 0;

		PartyMemberObjectTopBar[] partyMembersTempList = new PartyMemberObjectTopBar[getPartyMembers().size()];
		getPartyMembers().toArray(partyMembersTempList);

		for (PartyMemberObjectTopBar pmo : partyMembersTempList) {
			display += 21;
			pmo.setX(getX() + display + 27);
			pmo.setY(getY() + 2);
			pmo.update(gc, delta);
		}

		if (LobbyManager.getManager().getMatchMakingManager().isSearching()) {
			LobbyManager.getManager().getMatchMakingManager().updateSearchTimer(delta);
		}
	}

	public void render(GameContainer gc, Graphics g) {

		if (isRendering()) {
			
			PartyMemberObjectTopBar[] partyMembersTempList = new PartyMemberObjectTopBar[getPartyMembers().size()];
			getPartyMembers().toArray(partyMembersTempList);

			for (PartyMemberObjectTopBar pmo : partyMembersTempList) {
				pmo.render(gc, g);
			}

			for (PartyMemberObjectTopBar pmo : partyMembersTempList) {
				if (pmo.getBounds().contains(gc.getInput().getMouseX(), gc.getInput().getMouseY())) {
					g.setColor(new Color(0, 0, 0, 230));
					g.fillRect(pmo.getX() - 3, pmo.getY() + 3, Res.bitFont.getWidth(pmo.getUsername()) + 7, 9);
					Res.bitFont.drawString(pmo.getX(), pmo.getY() + 5, pmo.getUsername(), pmo.getNameColor());
				}
			}
			
			String matchMakerText = LobbyManager.getManager().getMatchMakingManager().getMatchMakingText();
			Res.bitFont.drawString((getX() + 146) + 251 / 2 - Res.getTextCenter(matchMakerText), getY() + 7,
					matchMakerText, LobbyManager.getManager().getMatchMakingManager().getMatchMakingColor());
			
	
			if (LobbyManager.getManager().getMatchMakingManager().isSearching()) {
				int gcm = (int) Math.floor(LobbyManager.getManager().getMatchMakingManager().getSearchTimer() / 60);
				int gcs = LobbyManager.getManager().getMatchMakingManager().getSearchTimer() - gcm * 60;
				Res.bitFont.drawString(getX() + 401, getY() + 7,
						String.format("%02d", gcm) + ":" + String.format("%02d", gcs), Color.yellow);
			}

			if (partySettingsButton.contains(gc.getInput().getMouseX(), gc.getInput().getMouseY())) {
				Res.UI_RESOURCE.MAINMENU_PARTY_SETTINGS_HOVER.draw(partySettingsButton.getX(), partySettingsButton.getY());
			}
		}
	}

	public void mousePressed(int button, int x, int y) {
		if (button == Input.MOUSE_LEFT_BUTTON) {
			if (partySettingsButton.contains(x, y)) {
				if (!getPartyMembers().isEmpty()) {
					MainMenuManager.getMainMenu().setWindowUI(MainMenuManager.getMainMenu().getPartyUI());
				}
			}
		}
	}

	public void setPartyMembers(String partyMembers) {

		getPartyMembers().clear();

		if (partyMembers.trim().isEmpty()) {
			return;
		}

		String[] firstSplit = partyMembers.split(Pattern.quote("["));

		String[] members = firstSplit[1].split("]");

		for (int i = 0; i < members.length; i++) {
			String[] partyInfo = members[i].split(",");

			getPartyMembers().add(new PartyMemberObjectTopBar(Integer.parseInt(partyInfo[0]), partyInfo[1],
					Integer.parseInt(partyInfo[2]), Integer.parseInt(partyInfo[3]), Integer.parseInt(partyInfo[4]),
					Integer.parseInt(partyInfo[5]), Integer.parseInt(partyInfo[6]), Integer.parseInt(partyInfo[7])));
		}

	}

	public void setPartyMemberAvatar(int partyMemberID, int bgID, int borderID, int iconID) {
		PartyMemberObjectTopBar pm = getPartyMemberByID(partyMemberID);
		if (pm != null) {
			pm.setBGID(bgID);
			pm.setBorderID(borderID);
			pm.setIconID(iconID);
		}
	}

	public PartyMemberObjectTopBar getPartyMemberByID(int accountID) {

		for (PartyMemberObjectTopBar pmo : getPartyMembers()) {
			if (pmo.getID() == accountID) {
				return pmo;
			}
		}

		return null;
	}

	public ArrayList<PartyMemberObjectTopBar> getPartyMembers() {
		return partyMembers;
	}


}
