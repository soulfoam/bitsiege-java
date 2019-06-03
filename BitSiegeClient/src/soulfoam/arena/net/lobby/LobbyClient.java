package soulfoam.arena.net.lobby;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import org.newdawn.slick.Color;

import soulfoam.arena.entities.LobbyEntity;
import soulfoam.arena.main.ClientEngine;
import soulfoam.arena.main.menu.MainMenuManager;
import soulfoam.arena.main.menu.PregameLobbyManager;
import soulfoam.arena.main.misc.LobbyChatMessage;
import soulfoam.arena.main.resources.Res;
import soulfoam.arena.main.states.States;
import soulfoam.arena.main.util.GameUtil;
import soulfoam.arena.world.World;
import soulfoam.arenashared.main.gameinfo.GameInfo;
import soulfoam.arenashared.main.ids.AvatarLibrary;
import soulfoam.arenashared.main.lobbyopcode.ConnectionStatus;
import soulfoam.arenashared.main.lobbyopcode.LobbyOPCode;
import soulfoam.arenashared.main.lobbyopcode.LobbyReturnCode;
import soulfoam.arenashared.main.lobbyutil.BSTimer;
import soulfoam.arenashared.main.store.Currency;
import soulfoam.arenashared.main.store.StorePrice;

public class LobbyClient extends Thread {

	private boolean isRunning = false;

	private String serverIP = GameInfo.MAIN_SERVER_IP;
	private int serverPort = 2147;

	private Socket socket;
	private BufferedReader input;
	private PrintWriter output;

	private BSTimer connectionCheckTimer;

	public boolean init() {
		try {
			InetAddress iNetAddress = InetAddress.getByName(serverIP);

			if (!iNetAddress.isReachable(5000)) {
				return false;
			}

			socket = new Socket(serverIP, serverPort);
			input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			output = new PrintWriter(socket.getOutputStream(), true);
		}

		catch (UnknownHostException e) {
			return false;
			// e.printStackTrace();
		} catch (IOException e) {
			return false;
			// e.printStackTrace();
		}

		connectionCheckTimer = new BSTimer(30000);
		isRunning = true;

		return true;
	}

	public void run() {

		while (isRunning) {
			try {

				connectionCheck();

				if (input.ready()) {

					String OPCode = input.readLine().trim();

					if (OPCode.startsWith(String.valueOf(LobbyOPCode.OP_REGISTER))) {
						LobbyManager.getManager().getRegisterManager()
								.setRegisterResult(Integer.parseInt(input.readLine().trim()));
					}

					if (OPCode.startsWith(String.valueOf(LobbyOPCode.OP_LOGIN))) {
						int result = Integer.parseInt(input.readLine().trim());
						LobbyManager.getManager().getLoginManager().setLoginResult(result);

						if (result == LobbyReturnCode.ACCOUNT_LOGIN_SUCCESS) {
							String[] accountInfo = input.readLine().trim().split(",");
							LobbyManager.getManager().getUserAccount().setLoggedIn(true);
							LobbyManager.getManager().getUserAccount().setID(Integer.parseInt(accountInfo[0]));
							LobbyManager.getManager().getUserAccount().setUsername(accountInfo[1]);
							LobbyManager.getManager().getUserAccount().setEmail(accountInfo[2]);
							LobbyManager.getManager().getUserAccount().setBits(Integer.parseInt(accountInfo[3]));
							LobbyManager.getManager().getUserAccount().setSiegePoints(Integer.parseInt(accountInfo[4]));
							LobbyManager.getManager().getUserAccount().setLevel(Integer.parseInt(accountInfo[5]));
							LobbyManager.getManager().getUserAccount().setEXP(Integer.parseInt(accountInfo[6]));
							LobbyManager.getManager().getUserAccount().setNameColor(Integer.parseInt(accountInfo[7]),
									Integer.parseInt(accountInfo[8]), Integer.parseInt(accountInfo[9]));

							LobbyManager.getManager().getUserAccount().setAvatarInfo(
									Res.AVATAR_RESOURCE.getAvatarInfo(Integer.parseInt(accountInfo[10]),
											AvatarLibrary.SLOT_BACKGROUND),
									Res.AVATAR_RESOURCE.getAvatarInfo(Integer.parseInt(accountInfo[11]),
											AvatarLibrary.SLOT_BORDER),
									Res.AVATAR_RESOURCE.getAvatarInfo(Integer.parseInt(accountInfo[12]),
											AvatarLibrary.SLOT_ICON));
							LobbyManager.getManager().getUserAccount().setCreationDate(Long.parseLong(accountInfo[13]));
							LobbyManager.getManager().getUserAccount().setInvisible(
									Integer.parseInt(accountInfo[14]) == ConnectionStatus.INVISIBLE ? true : false);

							LobbyManager.getManager().getUserAccount().setUnlockedChallengers(input.readLine().trim());
							LobbyManager.getManager().getUserAccount().setUnlockedSkins(input.readLine().trim());
							LobbyManager.getManager().getUserAccount().setUnlockedUnderglows(input.readLine().trim());
							LobbyManager.getManager().getUserAccount().setUnlockedAvatars(input.readLine().trim());
							LobbyManager.getManager().getUserAccount().setFriendRequests(input.readLine().trim());
							LobbyManager.getManager().getUserAccount().setFriends(input.readLine().trim());

							MainMenuManager.getMainMenu().showMultiplayerUI();

						}

						if (result == LobbyReturnCode.ACCOUNT_LOGIN_BANNED
								|| result == LobbyReturnCode.ACCOUNT_LOGIN_BANNED_IP) {
							LobbyManager.getManager().getLoginManager().setBannedMessage(result,
									input.readLine().trim());
						}
					}

					if (OPCode.startsWith(String.valueOf(LobbyOPCode.OP_MATCHMAKING))) {
						int result = Integer.parseInt(input.readLine().trim());
						LobbyManager.getManager().getMatchMakingManager().setMatchMakingResult(result);
						if (result == LobbyReturnCode.MATCHMAKING_QUE_GAMEFOUND) {
							MainMenuManager.getMainMenu().setChangeState(States.PREGAMELOBBY);
						}

					}

					if (OPCode.startsWith(String.valueOf(LobbyOPCode.OP_CHANGEAVATAR))) {
						int result = Integer.parseInt(input.readLine().trim());
						LobbyManager.getManager().getAvatarManager().setAvatarResult(result);
						if (result == LobbyReturnCode.AVATAR_CHANGE_SUCCESS) {
							String[] avatarInfo = input.readLine().trim().split(",");
							LobbyManager.getManager().getUserAccount().setAvatarInfo(
									Res.AVATAR_RESOURCE.getAvatarInfo(
											Integer.parseInt(avatarInfo[AvatarLibrary.SLOT_BACKGROUND]),
											AvatarLibrary.SLOT_BACKGROUND),
									Res.AVATAR_RESOURCE.getAvatarInfo(
											Integer.parseInt(avatarInfo[AvatarLibrary.SLOT_BORDER]),
											AvatarLibrary.SLOT_BORDER),
									Res.AVATAR_RESOURCE.getAvatarInfo(
											Integer.parseInt(avatarInfo[AvatarLibrary.SLOT_ICON]),
											AvatarLibrary.SLOT_ICON));
						}
					}

					if (OPCode.startsWith(String.valueOf(LobbyOPCode.OP_CHANGEPASSWORD))) {
						int result = Integer.parseInt(input.readLine().trim());
						LobbyManager.getManager().getAccountManager().setPasswordResult(result);
						if (result == LobbyReturnCode.ACCOUNT_CHANGEPASSWORD_SUCCESS) {
							MainMenuManager.getMainMenu().getProfileUI().getPasswordTab()
									.clearTextFields();
						}
					}

					if (OPCode.startsWith(String.valueOf(LobbyOPCode.OP_CHANGEEMAIL))) {
						int result = Integer.parseInt(input.readLine().trim());
						LobbyManager.getManager().getAccountManager().setEmailResult(result);
						if (result == LobbyReturnCode.ACCOUNT_CHANGEEMAIL_SUCCESS) {
							LobbyManager.getManager().getUserAccount().setEmail(input.readLine().trim());
							MainMenuManager.getMainMenu().getProfileUI().getEmailTab()
									.clearTextFields();
						}
					}

					if (OPCode.startsWith(String.valueOf(LobbyOPCode.OP_FRIENDSEARCH))) {
						int result = Integer.parseInt(input.readLine().trim());
						LobbyManager.getManager().getFriendManager().setFriendResult(result);
						if (result == LobbyReturnCode.FRIEND_SEARCH_SUCCESS) {
							String[] friendInfo = input.readLine().trim().split(",");
							MainMenuManager.getMainMenu().getFriendUI().getAddFriendTab().setFriendInfo(friendInfo[0],
									Integer.parseInt(friendInfo[1]), Integer.parseInt(friendInfo[2]),
									Integer.parseInt(friendInfo[3]), Long.parseLong(friendInfo[4]),
									Long.parseLong(friendInfo[5]), Integer.parseInt(friendInfo[6]),
									Integer.parseInt(friendInfo[7]), Integer.parseInt(friendInfo[8]));
						}
					}

					if (OPCode.startsWith(String.valueOf(LobbyOPCode.OP_FRIENDADD))) {
						int result = Integer.parseInt(input.readLine().trim());
						LobbyManager.getManager().getFriendManager().setFriendResult(result);
					}

					if (OPCode.startsWith(String.valueOf(LobbyOPCode.OP_LIVEUPDATE_NEWFRIENDREQUEST))) {
						LobbyManager.getManager().getUserAccount().setFriendRequests(input.readLine().trim());
					}

					if (OPCode.startsWith(String.valueOf(LobbyOPCode.OP_LIVEUPDATE_NEWFRIEND))) {
						LobbyManager.getManager().getUserAccount().setFriends(input.readLine().trim());
					}

					if (OPCode.startsWith(String.valueOf(LobbyOPCode.OP_LIVEUPDATE_FRIENDSTATUSCHANGE))) {
						String[] statusInfo = input.readLine().trim().split(",");
						LobbyManager.getManager().getUserAccount().setFriendConnectionStatus(
								Integer.parseInt(statusInfo[0]), Integer.parseInt(statusInfo[1]));
					}

					if (OPCode.startsWith(String.valueOf(LobbyOPCode.OP_LIVEUPDATE_USERAVATARCHANGE))) {
						String[] avatarInfo = input.readLine().trim().split(",");
						if (avatarInfo[0].equalsIgnoreCase("F")) {
							LobbyManager.getManager().getUserAccount().setFriendAvatar(Integer.parseInt(avatarInfo[1]),
									Integer.parseInt(avatarInfo[2]), Integer.parseInt(avatarInfo[3]),
									Integer.parseInt(avatarInfo[4]));
						}
						if (avatarInfo[0].equalsIgnoreCase("P")) {
							MainMenuManager.getMainMenu().getTopBarUI().setPartyMemberAvatar(
									Integer.parseInt(avatarInfo[1]), Integer.parseInt(avatarInfo[2]),
									Integer.parseInt(avatarInfo[3]), Integer.parseInt(avatarInfo[4]));
							MainMenuManager.getMainMenu().getPartyUI().setPartyMemberAvatar(
									Integer.parseInt(avatarInfo[1]), Integer.parseInt(avatarInfo[2]),
									Integer.parseInt(avatarInfo[3]), Integer.parseInt(avatarInfo[4]));
						}
					}

					if (OPCode.startsWith(String.valueOf(LobbyOPCode.OP_LIVEUPDATE_PARTYINVITE))) {
						LobbyManager.getManager().getUserAccount().addPartyRequest(input.readLine().trim());
					}

					if (OPCode.startsWith(String.valueOf(LobbyOPCode.OP_PARTYINVITE))) {
						LobbyManager.getManager().getFriendManager()
								.setInteractionResult(Integer.parseInt(input.readLine().trim()));
					}

					if (OPCode.startsWith(String.valueOf(LobbyOPCode.OP_SERVERINFO))) {
						String[] serverInfo = input.readLine().trim().split(",");
						if (serverInfo[0].equalsIgnoreCase("P")) {
							LobbyManager.getManager().getServerInfoManager()
									.setPlayersOnline(Integer.parseInt(serverInfo[1]));
						}
					}

					if (OPCode.startsWith(String.valueOf(LobbyOPCode.OP_PARTYINFO))) {
						String partyMemberInfo = input.readLine().trim();
						MainMenuManager.getMainMenu().getTopBarUI().setPartyMembers(partyMemberInfo);
						MainMenuManager.getMainMenu().getPartyUI().setPartyMembers(partyMemberInfo);
					}

					if (OPCode.startsWith(String.valueOf(LobbyOPCode.OP_DELETEREQUEST))) {
						String[] deleteInfo = input.readLine().trim().split(",");

						LobbyManager.getManager().getUserAccount().getRequests()
								.remove(LobbyManager.getManager().getUserAccount().getRequestObjectByPartyIDAndSenderID(
										Integer.parseInt(deleteInfo[0]), Integer.parseInt(deleteInfo[1])));
						MainMenuManager.getMainMenu().getRequestUI().setRequestPages();
					}
					
					if (OPCode.startsWith(String.valueOf(LobbyOPCode.OP_BANNED))) {
						ClientEngine.app.exit();
					}
					
					if (OPCode.startsWith(String.valueOf(LobbyOPCode.OP_PRIVATECHAT))) {
						int friendID = Integer.parseInt(input.readLine().trim());
						String message = input.readLine().trim();
						MainMenuManager.getMainMenu().getChatUI().addPrivateChatMessage(friendID, message);
					}
					
					if (OPCode.startsWith(String.valueOf(LobbyOPCode.OP_GLOBALCHAT))) {
						String message = input.readLine().trim();
						String[] rgb = input.readLine().trim().split(",");
						MainMenuManager.getMainMenu().getChatUI().addGlobalChatMessage(message, Integer.parseInt(rgb[0]), Integer.parseInt(rgb[1]), Integer.parseInt(rgb[2]));
					}

					if (OPCode.startsWith(String.valueOf(LobbyOPCode.OP_PARTYCHANGE))) {
						String[] changeInfo = input.readLine().trim().split(",");
						if (changeInfo[0].equalsIgnoreCase("L")) {
							LobbyManager.getManager().getPartyManager().setPartyResult(Integer.parseInt(changeInfo[1]));
							MainMenuManager.getMainMenu().getTopBarUI().getPartyMembers().clear();
							MainMenuManager.getMainMenu().getPartyUI().getPartyMembers().clear();
						}
						if (changeInfo[0].equalsIgnoreCase("M")) {
							LobbyManager.getManager().getPartyManager().setPartyResult(Integer.parseInt(changeInfo[1]));
						}
						if (changeInfo[0].equalsIgnoreCase("K")) {
							LobbyManager.getManager().getPartyManager().setPartyResult(Integer.parseInt(changeInfo[1]));
						}
					}

					if (OPCode.startsWith(String.valueOf(LobbyOPCode.OP_LOBBYUPDATE))) {
						String lobbyString = input.readLine().trim();

						String[] lobbyInfo = lobbyString.split("~");
						if (lobbyInfo[0].equalsIgnoreCase("A")) { // add player
							String[] players = lobbyInfo[1].trim().split("]");
							for (String player : players) {
								String[] playerInfo = player.trim().split(",");
								int playerID = Integer.parseInt(playerInfo[0]);
								String playerName = playerInfo[1];
								int r = Integer.parseInt(playerInfo[2]);
								int g = Integer.parseInt(playerInfo[3]);
								int b = Integer.parseInt(playerInfo[4]);
								byte playerTeam = Byte.parseByte(playerInfo[5]);
								int playerChallenger = Integer.parseInt(playerInfo[6]);

								LobbyEntity le = new LobbyEntity(playerID, playerName, r, g, b, playerTeam,
										playerChallenger, false);

								if (playerTeam == Res.TEAM_A) {
									PregameLobbyManager.getLobby().getTeamAPlayers().add(le);
								} else {
									PregameLobbyManager.getLobby().getTeamDPlayers().add(le);
								}

							}
						}
						if (lobbyInfo[0].equalsIgnoreCase("R")) { // remove
																	// player
							PregameLobbyManager.getLobby().removeEntity(Integer.parseInt(lobbyInfo[1]));
						}
						if (lobbyInfo[0].equalsIgnoreCase("T")) {
							PregameLobbyManager.getLobby().getGameStartTimerUI()
									.setTimer(Integer.parseInt(lobbyInfo[1]));
						}
						if (lobbyInfo[0].equalsIgnoreCase("M")) {
							World.MAP_ID = Integer.parseInt(lobbyInfo[1]);
						}
						if (lobbyInfo[0].equalsIgnoreCase("LT")) { // lock in
																	// challenger
																	// team
							int challengerID = Integer.parseInt(lobbyInfo[1]);
							PregameLobbyManager.getLobby().getChallengerSelectUI().setLockedInChallenger(challengerID,
									false);
							LobbyEntity le = PregameLobbyManager.getLobby()
									.getLobbyEntity(Integer.parseInt(lobbyInfo[2]));
							if (le != null) {
								le.lockIn(challengerID);
							}

						}
						if (lobbyInfo[0].equalsIgnoreCase("LE")) { // lock in
																	// challenger
																	// enemy
							int challengerID = Integer.parseInt(lobbyInfo[1]);
							LobbyEntity le = PregameLobbyManager.getLobby().getLobbyEntity(Integer.parseInt(lobbyInfo[2]));
							if (le != null) {
								le.lockIn(challengerID);
							}

						}
						if (lobbyInfo[0].equalsIgnoreCase("LL")) { // local lockin challenger
							int challengerID = Integer.parseInt(lobbyInfo[1]);
							PregameLobbyManager.getLobby().getChallengerSelectUI().setLockedInChallenger(challengerID, true);
							LobbyEntity le = PregameLobbyManager.getLobby().getLobbyEntity(LobbyManager.getManager().getUserAccount().getID());
							if (le != null) {
								le.lockIn(challengerID);
							}
						}

						if (lobbyString.substring(0, 2).equalsIgnoreCase("MA")) { // chat message all
							PregameLobbyManager.getLobby().getChatUI().addNewChatMessage(lobbyString.substring(2).trim(), true);
						}
						if (lobbyString.substring(0, 2).equalsIgnoreCase("MT")) { // chat message team
							PregameLobbyManager.getLobby().getChatUI().addNewChatMessage(lobbyString.substring(2).trim(), false);
						}
					}

					if (OPCode.startsWith(String.valueOf(LobbyOPCode.OP_ACCEPTREQUEST))) {

						int result = Integer.parseInt(input.readLine().trim());

						LobbyManager.getManager().getRequestManager().setRequestText(result);

						if (result == LobbyReturnCode.REQUEST_FRIEND_ACCEPT_SUCCESS) {
							int requestID = Integer.parseInt(input.readLine().trim());
							String friendInfo = input.readLine().trim();
							LobbyManager.getManager().getUserAccount().getRequests().remove(
									LobbyManager.getManager().getUserAccount().getRequestObjectByRequestID(requestID));
							LobbyManager.getManager().getUserAccount().setFriends(friendInfo);
							MainMenuManager.getMainMenu().getRequestUI().setRequestPages();
						}

						if (result == LobbyReturnCode.REQUEST_PARTY_ACCEPT_SUCCESS) {
							int partyID = Integer.parseInt(input.readLine().trim());
							LobbyManager.getManager().getUserAccount().getRequests().remove(
									LobbyManager.getManager().getUserAccount().getRequestObjectByPartyID(partyID));
							MainMenuManager.getMainMenu().getRequestUI().setRequestPages();
						}

					}

					if (OPCode.startsWith(String.valueOf(LobbyOPCode.OP_DECLINEREQUEST))) {

						int result = Integer.parseInt(input.readLine().trim());
						LobbyManager.getManager().getRequestManager().setRequestText(result);

						if (result == LobbyReturnCode.REQUEST_FRIEND_DECLINE_SUCCESS) {
							LobbyManager.getManager().getUserAccount().getRequests()
									.remove(LobbyManager.getManager().getUserAccount()
											.getRequestObjectByRequestID(Integer.parseInt(input.readLine().trim())));
							MainMenuManager.getMainMenu().getRequestUI().setRequestPages();
						}
						if (result == LobbyReturnCode.REQUEST_PARTY_DECLINE_SUCCESS) {
							LobbyManager.getManager().getUserAccount().getRequests().remove(LobbyManager.getManager().getUserAccount().getRequestObjectByPartyID(Integer.parseInt(input.readLine().trim())));
							MainMenuManager.getMainMenu().getRequestUI().setRequestPages();
						}
					}

					if (OPCode.startsWith(String.valueOf(LobbyOPCode.OP_STORE))) {
						int result = Integer.parseInt(input.readLine().trim());

						LobbyManager.getManager().getStoreManager().setStoreResult(result);

						if (result == LobbyReturnCode.STORE_BOUGHT_SUCCESS_CHALLENGER) {
							String extraInfoString = input.readLine().trim();
							String[] extraInfo = extraInfoString.split(",");
							int challengerID = Integer.parseInt(extraInfo[0]);
							int currency = Integer.parseInt(extraInfo[1]);
							LobbyManager.getManager().getStoreManager().setStoreResult(result,
									StorePrice.getPrices().getChallengerStoreItem(challengerID), currency);
						}
						if (result == LobbyReturnCode.STORE_BOUGHT_SUCCESS_SKIN) {
							String extraInfoString = input.readLine().trim();
							String[] extraInfo = extraInfoString.split(",");
							int challengerID = Integer.parseInt(extraInfo[0]);
							int skinID = Integer.parseInt(extraInfo[1]);
							LobbyManager.getManager().getStoreManager().setStoreResult(result,
									StorePrice.getPrices().getSkinStoreItem(challengerID, skinID),
									Currency.SIEGE_POINTS);
						}
						if (result == LobbyReturnCode.STORE_BOUGHT_SUCCESS_UNDERGLOW) {
							String extraInfoString = input.readLine().trim();
							int underglowID = Integer.parseInt(extraInfoString);
							LobbyManager.getManager().getStoreManager().setStoreResult(result,
									StorePrice.getPrices().getUnderglowStoreItem(underglowID), Currency.SIEGE_POINTS);
						}
						if (result == LobbyReturnCode.STORE_BOUGHT_SUCCESS_AVATAR_BG
								|| result == LobbyReturnCode.STORE_BOUGHT_SUCCESS_AVATAR_BORDER
								|| result == LobbyReturnCode.STORE_BOUGHT_SUCCESS_AVATAR_ICON) {
							int slotID = AvatarLibrary.SLOT_BACKGROUND;

							if (result == LobbyReturnCode.STORE_BOUGHT_SUCCESS_AVATAR_BORDER) {
								slotID = AvatarLibrary.SLOT_BORDER;
							} else if (result == LobbyReturnCode.STORE_BOUGHT_SUCCESS_AVATAR_ICON) {
								slotID = AvatarLibrary.SLOT_ICON;
							}
							String extraInfoString = input.readLine().trim();
							String[] extraInfo = extraInfoString.split(",");
							int avatarID = Integer.parseInt(extraInfo[0]);
							int currency = Integer.parseInt(extraInfo[1]);
							LobbyManager.getManager().getStoreManager().setStoreResult(result,
									StorePrice.getPrices().getAvatarStoreItem(avatarID, slotID), currency);
						}
					}

					if (OPCode.startsWith(String.valueOf(LobbyOPCode.OP_GAME))) {
						String serverInfoString = input.readLine().trim();
						String[] serverInfo = serverInfoString.split(",");
						PregameLobbyManager.getLobby().setServerIP(serverInfo[0]);
						PregameLobbyManager.getLobby().setServerPort(Integer.parseInt(serverInfo[1]));
						PregameLobbyManager.getLobby().setChangeToGameState(true);
					}

				}
			} catch (IOException e) {
				// e.printStackTrace();
			}

		}

		try {
			input.close();
			output.close();
			socket.close();
		} catch (IOException e) {
			// e.printStackTrace();
		}

	}

	private void connectionCheck() {
		if (connectionCheckTimer.update()) {
			output.println("");
			if (output.checkError()) {
				MainMenuManager.getMainMenu().getServerDisconnectUI().resetMessageText();
				LobbyManager.getManager().setLobbyClientAlive(false);
				isRunning = false;
			} else {
				connectionCheckTimer.reset();
			}
		}
	}

	public Socket getSocket() {
		return socket;
	}

	public BufferedReader getInput() {
		return input;
	}

	public PrintWriter getOutput() {
		return output;
	}

	public BSTimer getConnectionCheckTimer() {
		return connectionCheckTimer;
	}
}
