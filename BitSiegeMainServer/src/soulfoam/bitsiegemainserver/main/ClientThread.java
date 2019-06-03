package soulfoam.bitsiegemainserver.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import soulfoam.arenashared.main.ids.AvatarLibrary;
import soulfoam.arenashared.main.lobbyopcode.ConnectionStatus;
import soulfoam.arenashared.main.lobbyopcode.LobbyOPCode;
import soulfoam.arenashared.main.lobbyopcode.RequestType;
import soulfoam.arenashared.main.lobbyutil.BSTimer;
import soulfoam.arenashared.main.lobbyutil.LobbyUtil;
import soulfoam.bitsiegemainserver.main.accounts.AccountLoginCommand;
import soulfoam.bitsiegemainserver.main.accounts.ActiveAccountChangeCommand;
import soulfoam.bitsiegemainserver.main.accounts.ChangeEmailCommand;
import soulfoam.bitsiegemainserver.main.accounts.ChangePasswordCommand;
import soulfoam.bitsiegemainserver.main.accounts.MultiplayerAccount;
import soulfoam.bitsiegemainserver.main.accounts.RegisterAccountCommand;
import soulfoam.bitsiegemainserver.main.avatars.ChangeAvatarCommand;
import soulfoam.bitsiegemainserver.main.chat.SendGlobalChatCommand;
import soulfoam.bitsiegemainserver.main.chat.SendWhisperCommand;
import soulfoam.bitsiegemainserver.main.friends.AcceptFriendRequestCommand;
import soulfoam.bitsiegemainserver.main.friends.AddFriendCommand;
import soulfoam.bitsiegemainserver.main.friends.DeleteFriendRequestCommand;
import soulfoam.bitsiegemainserver.main.friends.FriendSearchCommand;
import soulfoam.bitsiegemainserver.main.logs.LogType;
import soulfoam.bitsiegemainserver.main.matchmaking.GameLobbyUpdateCommand;
import soulfoam.bitsiegemainserver.main.matchmaking.MatchMakingCommand;
import soulfoam.bitsiegemainserver.main.notify.NotifyFriendsOfConnectionChangeCommand;
import soulfoam.bitsiegemainserver.main.party.AcceptPartyInviteCommand;
import soulfoam.bitsiegemainserver.main.party.CreatePartyCommand;
import soulfoam.bitsiegemainserver.main.party.DeletePartyInviteCommand;
import soulfoam.bitsiegemainserver.main.party.LeavePartyCommand;
import soulfoam.bitsiegemainserver.main.party.PartyGroup;
import soulfoam.bitsiegemainserver.main.party.PartyInviteCommand;
import soulfoam.bitsiegemainserver.main.party.PartySettingChangeCommand;
import soulfoam.bitsiegemainserver.main.party.RemoveUserFromPartyCommand;
import soulfoam.bitsiegemainserver.main.store.StoreCommand;

public class ClientThread extends Thread{
	
	private boolean isRunning = false;

	private Socket socket;
	private BufferedReader input;
	private PrintWriter output;

	private MultiplayerAccount userAccount = new MultiplayerAccount();
	
	private PartyGroup party;
	
	private BSTimer connectionCheckTimer;

	public ClientThread(Socket socket){
		init(socket);
	}
	
	public void init(Socket socket){

		this.socket = socket;
		
		try {
			
			input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			output = new PrintWriter(socket.getOutputStream(), true);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		connectionCheckTimer = new BSTimer(30000);
		
		isRunning = true;
	}
	
	public void run() {
		
		while (isRunning){
			try {
				
				connectionCheck();
				
				if (input.ready()){
	
					String OPCode = input.readLine().trim();

					if (OPCode.startsWith(String.valueOf(LobbyOPCode.OP_LOGIN))){
						String[] loginInfo = input.readLine().trim().split(",");
						NetworkManager.getManager().getCommandHandler().getCommandQueue().put(new AccountLoginCommand(this, loginInfo));
					}

					if (OPCode.startsWith(String.valueOf(LobbyOPCode.OP_LOGOUT))){
						logOutAccount();
					}

					if (OPCode.startsWith(String.valueOf(LobbyOPCode.OP_REGISTER))){
						String[] registerInfo = input.readLine().trim().split(",");
						NetworkManager.getManager().getCommandHandler().getCommandQueue().put(new RegisterAccountCommand(this, registerInfo));
					}
					
					if (OPCode.startsWith(String.valueOf(LobbyOPCode.OP_MATCHMAKING))){
						String matchMakingInfo = input.readLine().trim();
						if (matchMakingInfo.equalsIgnoreCase("C")){ 
							NetworkManager.getManager().getCommandHandler().getCommandQueue().put(new MatchMakingCommand(party));
						}
						else{
							NetworkManager.getManager().getCommandHandler().getCommandQueue().put(new MatchMakingCommand(this, LobbyUtil.parseInt(matchMakingInfo)));
						}
					}
					
					if (OPCode.startsWith(String.valueOf(LobbyOPCode.OP_CHANGEAVATAR))){
						String[] avatarInfo = input.readLine().trim().split(",");
						int bgID = LobbyUtil.parseInt(avatarInfo[AvatarLibrary.SLOT_BACKGROUND]);
						int borderID = LobbyUtil.parseInt(avatarInfo[AvatarLibrary.SLOT_BORDER]);
						int iconID = LobbyUtil.parseInt(avatarInfo[AvatarLibrary.SLOT_ICON]);
						
						NetworkManager.getManager().getCommandHandler().getCommandQueue().put(new ChangeAvatarCommand(this, bgID, borderID, iconID));
					}
					
					if (OPCode.startsWith(String.valueOf(LobbyOPCode.OP_CHANGEPASSWORD))){
						String[] passwordInfo = input.readLine().trim().split(",");
						NetworkManager.getManager().getCommandHandler().getCommandQueue().put(new ChangePasswordCommand(this, passwordInfo));
					}
					
					if (OPCode.startsWith(String.valueOf(LobbyOPCode.OP_CHANGEEMAIL))){
						String[] emailInfo = input.readLine().trim().split(",");
						NetworkManager.getManager().getCommandHandler().getCommandQueue().put(new ChangeEmailCommand(this, emailInfo));
					}
					
					if (OPCode.startsWith(String.valueOf(LobbyOPCode.OP_PROFILECHANGE))){
						String[] profileInfo = input.readLine().trim().split(",");
						if (profileInfo[0].equalsIgnoreCase("A")){
							boolean invis = LobbyUtil.stringIntToBool(profileInfo[1]);
							NetworkManager.getManager().getAccountManager().changeInvisibleStatus(userAccount.getID(), invis);
							int newConnectionStatus = invis ? ConnectionStatus.INVISIBLE : ConnectionStatus.ONLINE;
							userAccount.setConnectionStatus(newConnectionStatus);
							NetworkManager.getManager().getCommandHandler().getCommandQueue().put(new NotifyFriendsOfConnectionChangeCommand(this));
						}
					}
					
					if (OPCode.startsWith(String.valueOf(LobbyOPCode.OP_FRIENDSEARCH))){
						String friendUsername = input.readLine().trim();
						NetworkManager.getManager().getCommandHandler().getCommandQueue().put(new FriendSearchCommand(this, friendUsername));
					}
					
					if (OPCode.startsWith(String.valueOf(LobbyOPCode.OP_FRIENDADD))){
						String friend = input.readLine().trim();
						NetworkManager.getManager().getCommandHandler().getCommandQueue().put(new AddFriendCommand(this, friend));
					}
					
					if (OPCode.startsWith(String.valueOf(LobbyOPCode.OP_PRIVATECHAT))) {
						int friendID = LobbyUtil.parseInt(input.readLine().trim());
						String message = input.readLine().trim();
						NetworkManager.getManager().getCommandHandler().getCommandQueue().put(new SendWhisperCommand(this, friendID, message));
					}
					
					if (OPCode.startsWith(String.valueOf(LobbyOPCode.OP_GLOBALCHAT))) {
						String message = input.readLine().trim();
						NetworkManager.getManager().getCommandHandler().getCommandQueue().put(new SendGlobalChatCommand(this, message));
					}
					
					if (OPCode.startsWith(String.valueOf(LobbyOPCode.OP_ACCEPTREQUEST))){
						String[] requestInfo = input.readLine().trim().split(",");
						int requestType = LobbyUtil.parseInt(requestInfo[0]);
						
						if (requestType == RequestType.FRIEND_REQUEST){
							output.println(LobbyOPCode.OP_ACCEPTREQUEST);
							int requestID = LobbyUtil.parseInt(requestInfo[1]);
							int senderID = NetworkManager.getManager().getFriendManager().getSenderIDFromRequestID(requestID);
							NetworkManager.getManager().getCommandHandler().getCommandQueue().put(new AcceptFriendRequestCommand(this, requestID, senderID));
						}
						
						if (requestType == RequestType.PARTY_REQUEST){
							int partyID = LobbyUtil.parseInt(requestInfo[1]);
							int senderID = LobbyUtil.parseInt(requestInfo[2]);
							NetworkManager.getManager().getCommandHandler().getCommandQueue().put(new AcceptPartyInviteCommand(partyID, this, senderID));
	
						}
						
					}
					
					if (OPCode.startsWith(String.valueOf(LobbyOPCode.OP_DECLINEREQUEST))){
						
						String[] requestInfo = input.readLine().trim().split(",");
						int requestType = LobbyUtil.parseInt(requestInfo[0]);
						
						if (requestType == RequestType.FRIEND_REQUEST){
							output.println(LobbyOPCode.OP_DECLINEREQUEST);
							int requestID = LobbyUtil.parseInt(requestInfo[1]);
							NetworkManager.getManager().getCommandHandler().getCommandQueue().put(new DeleteFriendRequestCommand(this, requestID));	
						}
						
						if (requestType == RequestType.PARTY_REQUEST){
							int partyID = LobbyUtil.parseInt(requestInfo[1]);
							int senderID = LobbyUtil.parseInt(requestInfo[2]);
							NetworkManager.getManager().getCommandHandler().getCommandQueue().put(new DeletePartyInviteCommand(partyID, this, senderID, true));	
						}
					}
				
					if (OPCode.startsWith(String.valueOf(LobbyOPCode.OP_PARTYINVITE))){
						int friendID = LobbyUtil.parseInt(input.readLine().trim());
						NetworkManager.getManager().getCommandHandler().getCommandQueue().put(new PartyInviteCommand(this, friendID));

					}
					
					if (OPCode.startsWith(String.valueOf(LobbyOPCode.OP_PARTYCHANGE))){
						String[] changeInfo = input.readLine().trim().split(",");

						if (changeInfo[0].equalsIgnoreCase("L")){
							NetworkManager.getManager().getCommandHandler().getCommandQueue().put(new LeavePartyCommand(this, LobbyUtil.parseInt(changeInfo[1])));
						}
						if (changeInfo[0].equalsIgnoreCase("M")){
							NetworkManager.getManager().getCommandHandler().getCommandQueue().put(new PartySettingChangeCommand(this, LobbyUtil.parseInt(changeInfo[1])));
						}
						if (changeInfo[0].equalsIgnoreCase("A")){
							NetworkManager.getManager().getCommandHandler().getCommandQueue().put(new PartySettingChangeCommand(this,  LobbyUtil.stringIntToBool(changeInfo[1])));
						}
					}
					
					if (OPCode.startsWith(String.valueOf(LobbyOPCode.OP_LOBBYUPDATE))){
						String lobbyString = input.readLine().trim();
						String[] lobbyInfo = lobbyString.split("~");
						NetworkManager.getManager().getCommandHandler().getCommandQueue().put(new GameLobbyUpdateCommand(this, lobbyString, lobbyInfo));
					}
					
					if (OPCode.startsWith(String.valueOf(LobbyOPCode.OP_STORE))){
						String storeString = input.readLine().trim();
						String[] storeInfo = storeString.split("~");
						NetworkManager.getManager().getCommandHandler().getCommandQueue().put(new StoreCommand(this, storeString, storeInfo));
					}
					
				}

				
			} 
			catch (IOException e) {
				NetworkManager.getManager().getLogManager().addLog(LogType.ERROR_CLIENTTHREAD, e.getMessage());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			 try { Thread.sleep(10); } catch (InterruptedException e) {}
		}
		
		try {
			input.close();
			output.close();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	} 
	
	public void connectionCheck(){
		if (connectionCheckTimer.update()){
			output.println("");
			if (output.checkError()){
				logOutAccount();
				try {
					NetworkManager.getManager().getCommandHandler().getCommandQueue().put(new ActiveAccountChangeCommand(this, ActiveAccountChangeCommand.REMOVE));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				isRunning = false;
			}
			else{
				output.println(LobbyOPCode.OP_SERVERINFO);
				output.println("P" + "," + NetworkManager.getManager().getActiveAccountManager().getClients().size());
				connectionCheckTimer.reset();
			}
		}
	}
	
	public void logOutAccount(){
		if (userAccount.isLoggedIn()){
			if (userAccount.getDisplayConnectionStatus() != ConnectionStatus.OFFLINE){
				try {
					NetworkManager.getManager().getCommandHandler().getCommandQueue().put(new NotifyFriendsOfConnectionChangeCommand(this, ConnectionStatus.OFFLINE));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			NetworkManager.getManager().getLogManager().addLog(LogType.LOGOUT, userAccount.getUsername() + " logged out.", socket.getInetAddress().getHostAddress());
			try {
				NetworkManager.getManager().getCommandHandler().getCommandQueue().put(new RemoveUserFromPartyCommand(this));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}	

			if (party.getCurrentGameLobby() != null){
				party.getCurrentGameLobby().removeClient(this);
			}
		}
		
		userAccount = new MultiplayerAccount();
	}
	
	public MultiplayerAccount getUserAccount(){
		return userAccount;
	}

	public PrintWriter getOutput() {
		return output;
	}

	public Socket getSocket() {
		return socket;
	}

	public PartyGroup getParty() {
		if (party == null){
			try {
				System.out.println("get party null ct");
				NetworkManager.getManager().getCommandHandler().getCommandQueue().put(new CreatePartyCommand(this));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return party;
	}

	public void setParty(PartyGroup party) {
		this.party = party;
	}
}