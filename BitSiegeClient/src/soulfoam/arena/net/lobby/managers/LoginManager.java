package soulfoam.arena.net.lobby.managers;

import org.newdawn.slick.Color;

import soulfoam.arena.net.lobby.LobbyManager;
import soulfoam.arenashared.main.lobbyopcode.LobbyOPCode;
import soulfoam.arenashared.main.lobbyopcode.LobbyReturnCode;

public class LoginManager {

	private String loginText = "Enter your login info...";
	private Color loginColor = Color.yellow;
	private int loginResult = -1;

	public void loginAccount(String username, String password) {
		LobbyManager.getManager().getLobbyClient().getOutput().println(LobbyOPCode.OP_LOGIN);
		LobbyManager.getManager().getLobbyClient().getOutput().println(username + "," + password);
	}

	public void setLoginText(String loginText, Color loginColor) {
		this.loginText = loginText;
		this.loginColor = loginColor;
	}

	public void setLoginText(int result) {
		if (result == LobbyReturnCode.ACCOUNT_LOGIN_SUCCESS) {
			loginText = "Logging in...";
			loginColor = Color.green;
		}
		if (result == LobbyReturnCode.ACCOUNT_LOGIN_USERDOESNTEXIST) {
			loginText = "Failure: Username doesn't exist...";
			loginColor = new Color(255, 150, 0);
		}
		if (result == LobbyReturnCode.ACCOUNT_LOGIN_SERVERERROR) {
			loginText = "Failure: Server error, try again later...";
			loginColor = new Color(255, 150, 0);
		}
		if (result == LobbyReturnCode.ACCOUNT_LOGIN_INVALIDPASSWORD) {
			loginText = "Failure: Invalid password...";
			loginColor = new Color(255, 150, 0);
		}
		if (result == LobbyReturnCode.ACCOUNT_LOGIN_ALREADYLOGGEDIN) {
			loginText = "Failure: Account is already logged in...";
			loginColor = new Color(255, 150, 0);
		}
	}

	public void setBannedMessage(int result, String banMessage) {
		if (result == LobbyReturnCode.ACCOUNT_LOGIN_BANNED) {
			loginText = "Account banned until " + banMessage;
			loginColor = new Color(255, 150, 0);
		}
		if (result == LobbyReturnCode.ACCOUNT_LOGIN_BANNED_IP) {
			loginText = "IP banned until " + banMessage;
			loginColor = new Color(255, 150, 0);
		}
	}

	public void setLoginResult(int result) {
		loginResult = result;
		setLoginText(result);
	}

	public int getLoginResult() {
		return loginResult;
	}

	public String getLoginText() {
		return loginText;
	}

	public Color getLoginColor() {
		return loginColor;
	}

	public String getDefaultLoginText() {
		return "Enter your login info...";
	}
}
