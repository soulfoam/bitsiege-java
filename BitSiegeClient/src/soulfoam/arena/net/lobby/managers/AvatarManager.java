package soulfoam.arena.net.lobby.managers;

import org.newdawn.slick.Color;

import soulfoam.arena.net.lobby.LobbyManager;
import soulfoam.arenashared.main.lobbyopcode.LobbyOPCode;
import soulfoam.arenashared.main.lobbyopcode.LobbyReturnCode;

public class AvatarManager {

	private String avatarText = getDefaultAvatarText();
	private Color avatarColor = Color.yellow;
	private int avatarResult = -1;

	public void changeAvatar(int bgID, int borderID, int iconID) {
		LobbyManager.getManager().getLobbyClient().getOutput().println(LobbyOPCode.OP_CHANGEAVATAR);
		LobbyManager.getManager().getLobbyClient().getOutput().println(bgID + "," + borderID + "," + iconID);
	}

	public void setAvatarText(int result) {

		if (result == LobbyReturnCode.AVATAR_CHANGE_SUCCESS) {
			avatarText = "Avatar saved!";
			avatarColor = Color.green;
		}
		if (result == LobbyReturnCode.AVATAR_CHANGE_NOTUNLOCKED) {
			avatarText = "Failure: You do not have this avatar unlocked...";
			avatarColor = new Color(255, 150, 0);
		}

	}

	public void setAvatarText(String avatarText, Color avatarColor) {
		this.avatarText = avatarText;
		this.avatarColor = avatarColor;
	}

	public void setAvatarResult(int result) {
		avatarResult = result;
		setAvatarText(result);
	}

	public int getAvatarResult() {
		return avatarResult;
	}

	public String getAvatarText() {
		return avatarText;
	}

	public Color getAvatarColor() {
		return avatarColor;
	}

	public String getDefaultAvatarText() {
		return "Customize your avatar below...";
	}
}
