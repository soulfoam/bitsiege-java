package soulfoam.arena.net.lobby.managers;

import org.newdawn.slick.Color;

import soulfoam.arena.net.lobby.LobbyManager;
import soulfoam.arenashared.main.lobbyopcode.LobbyOPCode;
import soulfoam.arenashared.main.lobbyopcode.LobbyReturnCode;
import soulfoam.arenashared.main.lobbyutil.LobbyUtil;

public class PartyManager {

	private String partyText = "";
	private Color partyColor = Color.yellow;
	private int partyResult = -1;

	public void leaveParty(int accountID) {
		LobbyManager.getManager().getLobbyClient().getOutput().println(LobbyOPCode.OP_PARTYCHANGE);
		LobbyManager.getManager().getLobbyClient().getOutput().println("L" + "," + accountID);
	}

	public void makeNewLeader(int accountID) {
		LobbyManager.getManager().getLobbyClient().getOutput().println(LobbyOPCode.OP_PARTYCHANGE);
		LobbyManager.getManager().getLobbyClient().getOutput().println("M" + "," + accountID);
	}

	public void setAnyoneInvite(boolean value) {
		LobbyManager.getManager().getLobbyClient().getOutput().println(LobbyOPCode.OP_PARTYCHANGE);
		LobbyManager.getManager().getLobbyClient().getOutput().println("A" + "," + LobbyUtil.boolToIntString(value));
	}

	public String getPartyText() {
		return partyText;
	}

	public void setPartyText(int result) {

		if (result == LobbyReturnCode.PARTY_LEAVE_SUCCESS) {
			partyText = "Left the party!";
			partyColor = Color.green;
		}

		if (result == LobbyReturnCode.PARTY_MAKELEADER_SUCCESS) {
			partyText = "Made user party leader!";
			partyColor = Color.green;
		}

		if (result == LobbyReturnCode.PARTY_MADELEADER) {
			partyText = "You were made the party leader!";
			partyColor = Color.green;
		}

		if (result == LobbyReturnCode.PARTY_KICKED) {
			partyText = "You were kicked from the party!";
			partyColor = new Color(255, 150, 0);
		}

		if (result == LobbyReturnCode.PARTY_KICK_SUCCESS) {
			partyText = "Kicked user from the party!";
			partyColor = Color.green;
		}

		if (result == LobbyReturnCode.PARTY_KICK_NOPERMISSION) {
			partyText = "Failure: You can't kick users!";
			partyColor = new Color(255, 150, 0);
		}
	}

	public void setPartyText(String text, Color color) {
		partyText = text;
		partyColor = color;
	}

	public String getDefaultPartyText() {
		return "";
	}

	public Color getPartyColor() {
		return partyColor;
	}

	public void setPartyColor(Color partyColor) {
		this.partyColor = partyColor;
	}

	public int getPartyResult() {
		return partyResult;
	}

	public void setPartyResult(int result) {
		partyResult = result;
		setPartyText(result);
	}
}
