package soulfoam.arena.net.lobby.managers;

import org.newdawn.slick.Color;

import soulfoam.arena.main.menu.MainMenuManager;
import soulfoam.arena.net.lobby.LobbyManager;
import soulfoam.arenashared.main.lobbyopcode.LobbyOPCode;
import soulfoam.arenashared.main.lobbyopcode.LobbyReturnCode;

public class RegisterManager {

	private String registerText = "Enter your desired account details...";
	private Color registerColor = Color.yellow;
	private int registerResult = -1;

	public void registerAccount(String username, String password, String confirmPassword, String email) {
		if (username.isEmpty()) {
			setRegisterText("Failure: Username can not be empty...", new Color(255, 150, 0));
			return;
		}
		if (password.isEmpty()) {
			setRegisterText("Failure: Password can not be empty...", new Color(255, 150, 0));
			return;
		}
		if (confirmPassword.isEmpty()) {
			setRegisterText("Failure: Confirm Password can not be empty...", new Color(255, 150, 0));
			return;
		}
		if (email.isEmpty()) {
			setRegisterText("Failure: Email can not be empty...", new Color(255, 150, 0));
			return;
		}

		LobbyManager.getManager().getLobbyClient().getOutput().println(LobbyOPCode.OP_REGISTER);
		LobbyManager.getManager().getLobbyClient().getOutput().println(username + "," + password + "," + confirmPassword + "," + email);
	}

	public void setRegisterText(int result) {

		if (result == LobbyReturnCode.ACCOUNT_CREATE_SUCCESS) {
			registerText = "Account created, you may now login!";
			registerColor = Color.green;
		}
		if (result == LobbyReturnCode.ACCOUNT_CREATE_USEREXISTS) {
			registerText = "Failure: Username already exists...";
			registerColor = new Color(255, 150, 0);
		}
		if (result == LobbyReturnCode.ACCOUNT_CREATE_SERVERERROR) {
			registerText = "Failure: Server error, try again later...";
			registerColor = new Color(255, 150, 0);
		}
		if (result == LobbyReturnCode.ACCOUNT_CREATE_PASSWORDSDONTMATCH) {
			registerText = "Failure: Passwords don't match...";
			registerColor = new Color(255, 150, 0);
		}
		if (result == LobbyReturnCode.ACCOUNT_CREATE_PASSWORDTOOSHORT) {
			registerText = "Failure: Password too short...";
			registerColor = new Color(255, 150, 0);
		}
		if (result == LobbyReturnCode.ACCOUNT_CREATE_USERNAMETOOLONG) {
			registerText = "Failure: Username too long...";
			registerColor = new Color(255, 150, 0);
		}
		if (result == LobbyReturnCode.ACCOUNT_CREATE_INVALIDEMAIL) {
			registerText = "Failure: Invalid email address...";
			registerColor = new Color(255, 150, 0);
		}
		if (result == LobbyReturnCode.ACCOUNT_CREATE_BANNED) {
			registerText = "Failure: You are banned from creating accounts...";
			registerColor = new Color(255, 150, 0);
		}
		if (result == LobbyReturnCode.ACCOUNT_CREATE_USERNAMECONTAINSSPECIALCHARACTERS) { 
			registerText = "Failure: Username contains special characters...";
			registerColor = new Color(255, 150, 0);
		}
		if (result == LobbyReturnCode.ACCOUNT_CREATE_USERNAMEEMPTY) {
			registerText = "Failure: Username is empty...";
			registerColor = new Color(255, 150, 0);
		}

		
	}

	public void setRegisterText(String text, Color color) {
		registerText = text;
		registerColor = color;
	}

	public void setRegisterResult(int result) {
		registerResult = result;
		setRegisterText(result);
		if (result == LobbyReturnCode.ACCOUNT_CREATE_SUCCESS){
			LobbyManager.getManager().getLoginManager().loginAccount(MainMenuManager.getMainMenu().getRegisterUI().getUsernameField().getText().trim(), MainMenuManager.getMainMenu().getRegisterUI().getPasswordField().getText().trim());
		}
	}

	public int getRegisterResult() {
		return registerResult;
	}

	public String getRegisterText() {
		return registerText;
	}

	public Color getRegisterColor() {
		return registerColor;
	}
}
