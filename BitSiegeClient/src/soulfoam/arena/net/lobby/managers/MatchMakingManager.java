package soulfoam.arena.net.lobby.managers;

import org.newdawn.slick.Color;

import soulfoam.arena.net.lobby.LobbyManager;
import soulfoam.arenashared.main.lobbyopcode.LobbyOPCode;
import soulfoam.arenashared.main.lobbyopcode.LobbyReturnCode;

public class MatchMakingManager {

	private String matchMakingText = "";
	private Color matchMakingColor = Color.yellow;
	private int matchMakingResult = -1;
	private boolean searching;
	private int searchTimer;
	private boolean foundGame;

	public void joinMatchMaking(int region) {
		LobbyManager.getManager().getLobbyClient().getOutput().println(LobbyOPCode.OP_MATCHMAKING);
		LobbyManager.getManager().getLobbyClient().getOutput().println(region);
	}

	public void cancelMatchMaking() {
		LobbyManager.getManager().getLobbyClient().getOutput().println(LobbyOPCode.OP_MATCHMAKING);
		LobbyManager.getManager().getLobbyClient().getOutput().println("C");
	}

	public void setMatchMakingText(int result) {
		if (result == LobbyReturnCode.MATCHMAKING_QUE_SUCCESS) {
			matchMakingText = "Searching for game...";
			matchMakingColor = Color.green;
		}
		if (result == LobbyReturnCode.MATCHMAKING_QUE_NOAVAILABLEGAMESERVERS) {
			matchMakingText = "No Game Servers available...";
			matchMakingColor = new Color(255, 150, 0);
		}
		if (result == LobbyReturnCode.MATCHMAKING_QUE_SEARCHINGSTOPPED) {
			matchMakingText = "Stopped searching...";
			matchMakingColor = new Color(255, 150, 0);
		}
		if (result == LobbyReturnCode.MATCHMAKING_QUE_GAMEFOUND) {
			matchMakingText = "Game Found!";
			matchMakingColor = Color.green;
		}
		if (result == LobbyReturnCode.MATCHMAKING_QUE_SERVERERROR) {
			matchMakingText = "Server Error occurred while trying to find a game...";
			matchMakingColor = new Color(255, 150, 0);
		}
		if (result == LobbyReturnCode.MATCHMAKING_QUE_YOUARENOTPARTYLEADER) {
			matchMakingText = "You are not party leader!";
			matchMakingColor = new Color(255, 150, 0);
		}
	}

	public Color getMatchMakingColor() {
		return matchMakingColor;
	}

	public int getMatchMakingResult() {
		return matchMakingResult;
	}

	public void setMatchMakingResult(int matchMakingResult) {
		this.matchMakingResult = matchMakingResult;
		setMatchMakingText(matchMakingResult);
		if (matchMakingResult == LobbyReturnCode.MATCHMAKING_QUE_SUCCESS) {
			searching = true;
		}
		if (matchMakingResult == LobbyReturnCode.MATCHMAKING_QUE_GAMEFOUND
				|| matchMakingResult == LobbyReturnCode.MATCHMAKING_QUE_SEARCHINGSTOPPED) {
			searching = false;
			searchTimer = 0;
			if (matchMakingResult == LobbyReturnCode.MATCHMAKING_QUE_GAMEFOUND) {
				matchMakingText = "";
			}
		}
	}

	public String getMatchMakingText() {
		return matchMakingText;
	}

	public boolean isSearching() {
		return searching;
	}

	public void setSearching(boolean searching) {
		this.searching = searching;
	}

	public int getSearchTimer() {
		return searchTimer / 1000;
	}

	public void updateSearchTimer(int delta) {
		searchTimer += delta;
	}

	public boolean isFoundGame() {
		return foundGame;
	}

	public void setFoundGame(boolean foundGame) {
		this.foundGame = foundGame;
	}
}
