package soulfoam.arena.net.lobby.managers;

import org.newdawn.slick.Color;

import soulfoam.arena.net.lobby.LobbyManager;
import soulfoam.arenashared.main.lobbyopcode.LobbyOPCode;
import soulfoam.arenashared.main.lobbyopcode.LobbyReturnCode;

public class FriendManager {

	private String friendText = getDefaultFriendText();
	private Color friendColor = Color.yellow;
	private int friendResult = -1;

	private String interactionText = "";
	private Color interactionColor = Color.yellow;
	private int interactionResult = -1;

	public void searchForFriend(String username) {
		LobbyManager.getManager().getLobbyClient().getOutput().println(LobbyOPCode.OP_FRIENDSEARCH);
		LobbyManager.getManager().getLobbyClient().getOutput().println(username);
	}

	public void addFriend(String username) {
		LobbyManager.getManager().getLobbyClient().getOutput().println(LobbyOPCode.OP_FRIENDADD);
		LobbyManager.getManager().getLobbyClient().getOutput().println(username);
	}

	public void inviteFriendToParty(int friendID) {
		LobbyManager.getManager().getLobbyClient().getOutput().println(LobbyOPCode.OP_PARTYINVITE);
		LobbyManager.getManager().getLobbyClient().getOutput().println(friendID);
	}

	public void setFriendText(int result) {

		if (result == LobbyReturnCode.FRIEND_SEARCH_USERDOESNOTEXIST) {
			friendText = "Failure: Username doesn't exist...";
			friendColor = new Color(255, 150, 0);
		}
		if (result == LobbyReturnCode.FRIEND_SEARCH_SUCCESS) {
			friendText = "Found user!";
			friendColor = Color.green;
		}
		if (result == LobbyReturnCode.FRIEND_ADD_USERDOESNOTEXIST) {
			friendText = "Failure: Username doesn't exist...";
			friendColor = new Color(255, 150, 0);
		}
		if (result == LobbyReturnCode.FRIEND_ADD_SUCCESS) {
			friendText = "Friend request has been sent to user!";
			friendColor = Color.green;
		}
		if (result == LobbyReturnCode.FRIEND_ADD_REQUESTALREADYSENT) {
			friendText = "Failure: A friend request already exists between users...";
			friendColor = new Color(255, 150, 0);
		}
		if (result == LobbyReturnCode.FRIEND_ADD_ALREADYFRIENDS) {
			friendText = "Failure: You're already friends with this user...";
			friendColor = new Color(255, 150, 0);
		}
		if (result == LobbyReturnCode.FRIEND_ADD_USERISYOU) {
			friendText = "Failure: You can not friend request yourself...";
			friendColor = new Color(255, 150, 0);
		}
	}

	public void setFriendText(String text, Color color) {
		friendText = text;
		friendColor = color;
	}

	public void setInteractionText(String text, Color color) {
		interactionText = text;
		interactionColor = color;
	}

	public void setInteractionText(int result) {
		if (result == LobbyReturnCode.PARTY_INVITE_SUCCESS) {
			interactionText = "Party invite sent!";
			interactionColor = Color.green;
		}
		if (result == LobbyReturnCode.PARTY_INVITE_YOUARENOTFRIENDS) {
			interactionText = "Failure: You are not friends with this user...";
			interactionColor = new Color(255, 150, 0);
		}
		if (result == LobbyReturnCode.PARTY_INVITE_INVITEALREADYEXISTS) {
			interactionText = "Failure: A party invite already exists...";
			interactionColor = new Color(255, 150, 0);
		}
		if (result == LobbyReturnCode.PARTY_INVITE_USEROFFLINE) {
			interactionText = "Failure: User is offline...";
			interactionColor = new Color(255, 150, 0);
		}
		if (result == LobbyReturnCode.PARTY_INVITE_NOPERMISSION) {
			interactionText = "Failure: Only party leader can invite...";
			interactionColor = new Color(255, 150, 0);
		}
		if (result == LobbyReturnCode.PARTY_INVITE_USERALREADYINPARTY) {
			interactionText = "Failure: User is already in your party...";
			interactionColor = new Color(255, 150, 0);
		}
	}

	public String getDefaultFriendText() {
		return "Search for friends username and press enter...";
	}

	public String getDefaultInteractionText() {
		return "";
	}

	public String getFriendText() {
		return friendText;
	}

	public Color getFriendColor() {
		return friendColor;
	}

	public void setFriendColor(Color searchFriendColor) {
		friendColor = searchFriendColor;
	}

	public int getFriendResult() {
		return friendResult;
	}

	public void setFriendResult(int result) {
		friendResult = result;
		setFriendText(result);
	}

	public String getInteractionText() {
		return interactionText;
	}

	public Color getInteractionColor() {
		return interactionColor;
	}

	public void setInteractionColor(Color interactionColor) {
		this.interactionColor = interactionColor;
	}

	public int getInteractionResult() {
		return interactionResult;
	}

	public void setInteractionResult(int result) {
		interactionResult = result;
		setInteractionText(result);
	}

}
