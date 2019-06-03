package soulfoam.arena.net.lobby.managers;

import org.newdawn.slick.Color;

import soulfoam.arena.net.lobby.LobbyManager;
import soulfoam.arenashared.main.lobbyopcode.LobbyOPCode;
import soulfoam.arenashared.main.lobbyopcode.LobbyReturnCode;
import soulfoam.arenashared.main.lobbyopcode.RequestType;

public class RequestManager {

	private String requestText = getDefaultRequestText();
	private Color requestColor = Color.yellow;
	private int requestResult = -1;

	public void acceptFriendRequest(int requestID) {
		LobbyManager.getManager().getLobbyClient().getOutput().println(LobbyOPCode.OP_ACCEPTREQUEST);
		LobbyManager.getManager().getLobbyClient().getOutput().println(RequestType.FRIEND_REQUEST + "," + requestID);
	}

	public void declineFriendRequest(int requestID) {
		LobbyManager.getManager().getLobbyClient().getOutput().println(LobbyOPCode.OP_DECLINEREQUEST);
		LobbyManager.getManager().getLobbyClient().getOutput().println(RequestType.FRIEND_REQUEST + "," + requestID);
	}

	public void acceptPartyRequest(int partyID, int senderID) {
		LobbyManager.getManager().getLobbyClient().getOutput().println(LobbyOPCode.OP_ACCEPTREQUEST);
		LobbyManager.getManager().getLobbyClient().getOutput()
				.println(RequestType.PARTY_REQUEST + "," + partyID + "," + senderID);
	}

	public void declinePartyRequest(int partyID, int senderID) {
		LobbyManager.getManager().getLobbyClient().getOutput().println(LobbyOPCode.OP_DECLINEREQUEST);
		LobbyManager.getManager().getLobbyClient().getOutput()
				.println(RequestType.PARTY_REQUEST + "," + partyID + "," + senderID);
	}

	public void setRequestText(int result) {

		if (result == LobbyReturnCode.REQUEST_FRIEND_ACCEPT_SUCCESS) {
			requestText = "Friend request accepted!";
			requestColor = Color.green;
		}

		if (result == LobbyReturnCode.REQUEST_FRIEND_DECLINE_SUCCESS) {
			requestText = "Friend request declined!";
			requestColor = Color.green;
		}

		if (result == LobbyReturnCode.REQUEST_FRIEND_REQUESTDOESNTEXIST) {
			requestText = "Failure: Request doesn't exist!";
			requestColor = new Color(255, 150, 0);
		}

		if (result == LobbyReturnCode.REQUEST_PARTY_ACCEPT_SUCCESS) {
			requestText = "Party invite accepted!";
			requestColor = Color.green;
		}

		if (result == LobbyReturnCode.REQUEST_PARTY_DECLINE_SUCCESS) {
			requestText = "Party invite declined!";
			requestColor = Color.green;
		}

		if (result == LobbyReturnCode.REQUEST_PARTY_REQUESTDOESNTEXIST) {
			requestText = "Failure: Party invite doesn't exist...";
			requestColor = new Color(255, 150, 0);
		}

		if (result == LobbyReturnCode.REQUEST_PARTY_FULL) {
			requestText = "Failure: Party is full...";
			requestColor = new Color(255, 150, 0);
		}

		if (result == LobbyReturnCode.REQUEST_PARTY_PRIVATEPARTY) {
			requestText = "Failure: Party is no longer public...";
			requestColor = new Color(255, 150, 0);
		}

		if (result == LobbyReturnCode.REQUEST_PARTY_ALREADYINPARTY) {
			requestText = "Failure: You are already in this party...";
			requestColor = new Color(255, 150, 0);
		}
	}

	public void setRequestText(String requestText, Color requestColor) {
		this.requestText = requestText;
		this.requestColor = requestColor;
	}

	public void setRequestResult(int result) {
		requestResult = result;
		setRequestText(result);
	}

	public int getRequestResult() {
		return requestResult;
	}

	public String getRequestText() {
		return requestText;
	}

	public Color getRequestColor() {
		return requestColor;
	}

	public String getDefaultRequestText() {
		return "Manage your requests below...";
	}
}
