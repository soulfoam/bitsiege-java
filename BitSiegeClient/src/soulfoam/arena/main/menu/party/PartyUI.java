package soulfoam.arena.main.menu.party;

import java.util.ArrayList;
import java.util.regex.Pattern;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

import soulfoam.arena.main.menu.BaseMenuUI;
import soulfoam.arena.main.menu.MenuButton;
import soulfoam.arena.main.resources.Res;
import soulfoam.arena.net.lobby.LobbyManager;
import soulfoam.arenashared.main.gameinfo.GameInfo;
import soulfoam.arenashared.main.lobbyutil.LobbyUtil;

public class PartyUI extends BaseMenuUI {

	private ArrayList<PartyMemberObjectPartyUI> partyMembers = new ArrayList<PartyMemberObjectPartyUI>();
	private MenuButton anyoneInviteButton;
	private boolean anyoneInvite;
	private boolean leader;

	public PartyUI() {
		setWidth(380);
		setHeight(240);
		setX(GameInfo.RES_WIDTH / 2 - getWidth() / 2);
		setY(GameInfo.RES_HEIGHT / 2 - getHeight() / 2 + 9);
		setAnyoneInviteButton(new MenuButton("Yes", getX() + 152, getY() + 59, 80, 12));
	}

	public void setWindow(boolean set) {
		setFocus(set);
		setRender(set);
		LobbyManager.getManager().getPartyManager().setPartyText(LobbyManager.getManager().getPartyManager().getDefaultPartyText(), Color.yellow);
	}

	public void update(GameContainer gc, StateBasedGame s, int delta) {

		updatePartyManagement(gc, delta);
		
	}

	public void updatePartyManagement(GameContainer gc, int delta) {
		int display = 0;

		PartyMemberObjectPartyUI[] partyMembersTempList = new PartyMemberObjectPartyUI[partyMembers.size()];
		partyMembers.toArray(partyMembersTempList);

		for (PartyMemberObjectPartyUI pmo : partyMembersTempList) {

			if (pmo.isLeader()) {
				leader = pmo.getID() == LobbyManager.getManager().getUserAccount().getID();
			}

			display += 22;
			pmo.setX(getX() + 1);
			pmo.setY(getY() + display + 69);
			pmo.setWidth(getWidth());
			pmo.setHeight(18);
			pmo.update(gc, delta);

			if (leader) {
				if (pmo.getID() != LobbyManager.getManager().getUserAccount().getID()) {
					pmo.getMakeLeaderButton().setEnabled(true);
					pmo.getDisbandButton().setText("Kick");
					pmo.getDisbandButton().setEnabled(true);
					pmo.getDisbandButton().setButtonColor(Res.COLOR_RESOURCE.TOGGLE_BUTTON_OFF);
				} else {
					pmo.getMakeLeaderButton().setEnabled(false);
					if (partyMembers.size() == 1) {
						pmo.getDisbandButton().setText("Leave");
						pmo.getDisbandButton().setEnabled(false);
					} else {
						pmo.getDisbandButton().setText("Leave");
						pmo.getDisbandButton().setEnabled(true);
						pmo.getDisbandButton().setButtonColor(Res.COLOR_RESOURCE.TOGGLE_BUTTON_OFF);
					}
				}

			} else {
				pmo.getMakeLeaderButton().setEnabled(false);

				if (pmo.getID() == LobbyManager.getManager().getUserAccount().getID()) {
					pmo.getDisbandButton().setText("Leave");
					pmo.getDisbandButton().setEnabled(true);
					pmo.getDisbandButton().setButtonColor(Res.COLOR_RESOURCE.TOGGLE_BUTTON_OFF);
				} else {
					pmo.getDisbandButton().setText("Kick");
					pmo.getDisbandButton().setEnabled(false);
				}

			}
		}

		anyoneInviteButton.update(gc);

		if (anyoneInviteButton.isClicked()) {
			setAnyoneInvite(!anyoneInvite);
			LobbyManager.getManager().getPartyManager().setAnyoneInvite(anyoneInvite);
		}

		handleButtons();
	}

	public void render(GameContainer gc, Graphics g) {

		if (isRendering()) {
			renderWindow(g);

			renderPartyManagement(gc, g);
			
		}
	}

	public void renderWindow(Graphics g) {

		Res.UI_RESOURCE.MAINMENU_PARTY_SETTINGS_UI.draw(getX(), getY());

		String playText = LobbyManager.getManager().getPartyManager().getPartyText();
		Res.bitFont.drawString(getX() - Res.getTextCenter(playText) + getWidth() / 2, getY() + 17, playText,
				LobbyManager.getManager().getPartyManager().getPartyColor());

		String headerText = "Party Options";
		Res.bitFont.drawString(getX() - Res.getTextCenter(headerText) + getWidth() / 2, getY() + 3, headerText,
				Color.white);
	}

	public void renderPartyManagement(GameContainer gc, Graphics g) {

		PartyMemberObjectPartyUI[] partyMembersTempList = new PartyMemberObjectPartyUI[partyMembers.size()];
		partyMembers.toArray(partyMembersTempList);

		for (PartyMemberObjectPartyUI pmo : partyMembersTempList) {
			pmo.render(gc, g);
		}

		anyoneInviteButton.render(g, 3, gc);

	}

	public void mousePressed(int button, int x, int y) {

	}

	public void setPartyMembers(String partyString) {

		partyMembers.clear();

		if (partyString.trim().isEmpty()) {
			return;
		}

		String[] firstSplit = partyString.split(Pattern.quote("["));

		setAnyoneInvite(LobbyUtil.stringIntToBool(firstSplit[0]));

		String[] members = firstSplit[1].split("]");

		for (int i = 0; i < members.length; i++) {
			String[] partyInfo = members[i].split(",");

			PartyMemberObjectPartyUI pmo = new PartyMemberObjectPartyUI(Integer.parseInt(partyInfo[0]), partyInfo[1],
					Integer.parseInt(partyInfo[2]), Integer.parseInt(partyInfo[3]), Integer.parseInt(partyInfo[4]),
					Integer.parseInt(partyInfo[5]), Integer.parseInt(partyInfo[6]), Integer.parseInt(partyInfo[7]));

			if (i == 0) {
				pmo.setLeader(true);
			}

			partyMembers.add(pmo);
		}

	}

	public void handleButtons() {

		if (leader) {
			anyoneInviteButton.setEnabled(true);
			if (anyoneInvite) {
				anyoneInviteButton.setText("Yes");
				anyoneInviteButton.setButtonColor(Res.COLOR_RESOURCE.TOGGLE_BUTTON_ON);
			} else {
				anyoneInviteButton.setText("No");
				anyoneInviteButton.setButtonColor(Res.COLOR_RESOURCE.TOGGLE_BUTTON_OFF);
			}
		} else {
			anyoneInviteButton.setEnabled(false);
			anyoneInviteButton.setText("???");
		}
	}

	public void setPartyMemberAvatar(int partyMemberID, int bgID, int borderID, int iconID) {
		PartyMemberObjectPartyUI pm = getPartyMemberByID(partyMemberID);
		if (pm != null) {
			pm.setBGID(bgID);
			pm.setBorderID(borderID);
			pm.setIconID(iconID);
		}
	}

	public PartyMemberObjectPartyUI getPartyMemberByID(int accountID) {

		PartyMemberObjectPartyUI[] partyMembersTempList = new PartyMemberObjectPartyUI[partyMembers.size()];
		partyMembers.toArray(partyMembersTempList);

		for (PartyMemberObjectPartyUI pmo : partyMembersTempList) {
			if (pmo.getID() == accountID) {
				return pmo;
			}
		}

		return null;
	}

	public ArrayList<PartyMemberObjectPartyUI> getPartyMembers() {
		return partyMembers;
	}

	public MenuButton getAnyoneInviteButton() {
		return anyoneInviteButton;
	}

	public void setAnyoneInviteButton(MenuButton anyoneCanInviteButton) {
		anyoneInviteButton = anyoneCanInviteButton;
	}

	public boolean canAnyoneInvite() {
		return anyoneInvite;
	}

	public void setAnyoneInvite(boolean anyoneInvite) {
		this.anyoneInvite = anyoneInvite;
	}

	public boolean isLeader() {
		return leader;
	}

	public void setLeader(boolean isLeader) {
		leader = isLeader;
	}

}
