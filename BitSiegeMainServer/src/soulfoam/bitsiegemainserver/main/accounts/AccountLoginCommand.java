package soulfoam.bitsiegemainserver.main.accounts;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import soulfoam.arenashared.main.ids.AvatarLibrary;
import soulfoam.arenashared.main.lobbyopcode.ConnectionStatus;
import soulfoam.arenashared.main.lobbyopcode.LobbyOPCode;
import soulfoam.arenashared.main.lobbyopcode.LobbyReturnCode;
import soulfoam.arenashared.main.lobbyopcode.RequestType;
import soulfoam.bitsiegemainserver.main.ClientThread;
import soulfoam.bitsiegemainserver.main.NetworkManager;
import soulfoam.bitsiegemainserver.main.command.Command;
import soulfoam.bitsiegemainserver.main.db.Database;
import soulfoam.bitsiegemainserver.main.notify.NotifyFriendsOfConnectionChangeCommand;
import soulfoam.bitsiegemainserver.main.notify.NotifyUserOfFriendRequestCommand;
import soulfoam.bitsiegemainserver.main.notify.NotifyUsersOfPartyCommand;
import soulfoam.bitsiegemainserver.main.party.CreatePartyCommand;

public class AccountLoginCommand extends Command{
	
	private ClientThread clientThread;
	private String[] loginInfo;
	
	public AccountLoginCommand(ClientThread clientThread, String[] loginInfo){
		this.clientThread = clientThread;
		this.loginInfo = loginInfo;
	}
	
	public boolean execute() {
		
		if (loginInfo.length == 2){
			
			int result = NetworkManager.getManager().getAccountManager().loadAccount(loginInfo[0], loginInfo[1], clientThread.getSocket().getInetAddress().getHostAddress(), clientThread.getUserAccount());
			clientThread.getOutput().println(LobbyOPCode.OP_LOGIN);
			clientThread.getOutput().println(result);
			if (result == LobbyReturnCode.ACCOUNT_LOGIN_SUCCESS){
				clientThread.getOutput().println(clientThread.getUserAccount().getID() + "," + clientThread.getUserAccount().getUsername() + "," + clientThread.getUserAccount().getEmail() 
						+ "," + clientThread.getUserAccount().getBits() + "," + clientThread.getUserAccount().getSiegePoints() 
						+ "," + clientThread.getUserAccount().getLevel() + "," + clientThread.getUserAccount().getEXP()
						+ "," + clientThread.getUserAccount().getNameColor()
						+ "," + clientThread.getUserAccount().getAvatarIDs()[AvatarLibrary.SLOT_BACKGROUND] 
						+ "," + clientThread.getUserAccount().getAvatarIDs()[AvatarLibrary.SLOT_BORDER] 
						+ "," + clientThread.getUserAccount().getAvatarIDs()[AvatarLibrary.SLOT_ICON]
						+ "," + clientThread.getUserAccount().getCreationDate().getTime()
						+ "," + clientThread.getUserAccount().getConnectionStatus()
						);
				clientThread.getOutput().println(NetworkManager.getManager().getChallengerManager().getUnlockedChallengers(clientThread.getUserAccount().getID()));
				clientThread.getOutput().println(NetworkManager.getManager().getSkinManager().getUnlockedSkins(clientThread.getUserAccount().getID()));
				clientThread.getOutput().println(NetworkManager.getManager().getUnderglowManager().getUnlockedUnderglows(clientThread.getUserAccount().getID()));
				clientThread.getOutput().println(NetworkManager.getManager().getAvatarManager().getUnlockedAvatars(clientThread.getUserAccount().getID()));
				clientThread.getOutput().println(NetworkManager.getManager().getFriendManager().getFriendRequests(clientThread.getUserAccount().getID()));
				clientThread.getOutput().println(NetworkManager.getManager().getFriendManager().getFriends(clientThread.getUserAccount().getID()));
				if (clientThread.getUserAccount().getDisplayConnectionStatus() != ConnectionStatus.OFFLINE){
					try {
						NetworkManager.getManager().getCommandHandler().getCommandQueue().put(new NotifyFriendsOfConnectionChangeCommand(clientThread));
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				clientThread.getOutput().println(LobbyOPCode.OP_SERVERINFO);
				clientThread.getOutput().println("P" + "," + NetworkManager.getManager().getActiveAccountManager().getClients().size());
				
				try {
					NetworkManager.getManager().getCommandHandler().getCommandQueue().put(new CreatePartyCommand(clientThread));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

		
			}
			if (result == LobbyReturnCode.ACCOUNT_LOGIN_BANNED || result == LobbyReturnCode.ACCOUNT_LOGIN_BANNED_IP){
				clientThread.getOutput().println(clientThread.getUserAccount().getBannedExpireDate());
			}
		}
		
		return true;
	
	}

	public void undo() {
		
	}


}
