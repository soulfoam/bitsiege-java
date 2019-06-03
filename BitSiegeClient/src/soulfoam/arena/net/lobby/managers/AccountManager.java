package soulfoam.arena.net.lobby.managers;

import org.newdawn.slick.Color;

import soulfoam.arena.net.lobby.LobbyManager;
import soulfoam.arenashared.main.lobbyopcode.LobbyOPCode;
import soulfoam.arenashared.main.lobbyopcode.LobbyReturnCode;
import soulfoam.arenashared.main.lobbyutil.LobbyUtil;

public class AccountManager {

	private String passwordText = getDefaultPasswordText();
	private Color passwordColor = Color.yellow;
	private int passwordResult = -1;

	private String emailText = getDefaultEmailText();
	private Color emailColor = Color.yellow;
	private int emailResult = -1;

	public void changePassword(String currentPassword, String newPassword, String newConfirmedPassword) {
		LobbyManager.getManager().getLobbyClient().getOutput().println(LobbyOPCode.OP_CHANGEPASSWORD);
		LobbyManager.getManager().getLobbyClient().getOutput()
				.println(currentPassword + "," + newPassword + "," + newConfirmedPassword);
	}

	public void changeEmail(String currentPassword, String newEmail, String newConfirmedEmail) {
		LobbyManager.getManager().getLobbyClient().getOutput().println(LobbyOPCode.OP_CHANGEEMAIL);
		LobbyManager.getManager().getLobbyClient().getOutput()
				.println(currentPassword + "," + newEmail + "," + newConfirmedEmail);
	}

	public void changeAppearOffline(boolean value) {
		LobbyManager.getManager().getLobbyClient().getOutput().println(LobbyOPCode.OP_PROFILECHANGE);
		LobbyManager.getManager().getLobbyClient().getOutput().println("A" + "," + LobbyUtil.boolToIntString(value));
	}

	public void setAccountText(int result) {

		if (result == LobbyReturnCode.ACCOUNT_CHANGEPASSWORD_SUCCESS) {
			passwordText = "Password has been changed!";
			passwordColor = Color.green;
		}
		if (result == LobbyReturnCode.ACCOUNT_CHANGEPASSWORD_CURRENTPASSWORDISWRONG) {
			passwordText = "Failure: Current Password is wrong...";
			passwordColor = new Color(255, 150, 0);
		}
		if (result == LobbyReturnCode.ACCOUNT_CHANGEPASSWORD_PASSWORDSDONTMATCH) {
			passwordText = "Failure: New Passwords don't match...";
			passwordColor = new Color(255, 150, 0);
		}
		if (result == LobbyReturnCode.ACCOUNT_CHANGEPASSWORD_PASSWORDTOOSHORT) {
			passwordText = "Failure: New Password too short...";
			passwordColor = new Color(255, 150, 0);
		}
		
	}

	public void setEmailText(int result) {
		if (result == LobbyReturnCode.ACCOUNT_CHANGEEMAIL_SUCCESS) {
			emailText = "Email has been changed successfully!";
			emailColor = Color.green;
		}
		if (result == LobbyReturnCode.ACCOUNT_CHANGEEMAIL_INVALIDEMAIL) {
			emailText = "Failure: Invalid Email...";
			emailColor = new Color(255, 150, 0);
		}
		if (result == LobbyReturnCode.ACCOUNT_CHANGEEMAIL_CURRENTPASSWORDISWRONG) {
			emailText = "Failure: Current Password is wrong...";
			emailColor = new Color(255, 150, 0);
		}
		if (result == LobbyReturnCode.ACCOUNT_CHANGEEMAIL_EMAILSDONTMATCH) {
			emailText = "Failure: New Emails don't match...";
			emailColor = new Color(255, 150, 0);
		}
	}

	public void setEmailText(String emailText, Color emailColor) {
		this.emailText = emailText;
		this.emailColor = emailColor;
	}

	public void setPasswordText(String passwordText, Color passwordColor) {
		this.passwordText = passwordText;
		this.passwordColor = passwordColor;
	}

	public void setPasswordResult(int result) {
		passwordResult = result;
		setAccountText(result);
	}

	public int getPasswordResult() {
		return passwordResult;
	}

	public String getPasswordText() {
		return passwordText;
	}

	public Color getPasswordColor() {
		return passwordColor;
	}

	public String getEmailText() {
		return emailText;
	}

	public Color getEmailColor() {
		return emailColor;
	}

	public void setEmailColor(Color emailColor) {
		this.emailColor = emailColor;
	}

	public int getEmailResult() {
		return emailResult;
	}

	public void setEmailResult(int result) {
		emailResult = result;
		setEmailText(result);
	}

	public String getDefaultPasswordText() {
		return "Change your password below...";
	}

	public String getDefaultEmailText() {
		return "Change your email below...";
	}
}
