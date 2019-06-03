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

public class RegisterAccountCommand extends Command{
	
	private ClientThread clientThread;
	private String[] registerInfo;
	
	public RegisterAccountCommand(ClientThread clientThread, String[] registerInfo){
		this.clientThread = clientThread;
		this.registerInfo = registerInfo;
	}
	
	public boolean execute() {
		
		int result = NetworkManager.getManager().getAccountManager().createAccount(registerInfo[0], registerInfo[1], registerInfo[2], registerInfo[3], clientThread.getSocket().getInetAddress().getHostAddress());
		clientThread.getOutput().println(LobbyOPCode.OP_REGISTER);
		clientThread.getOutput().println(result);
		
		return true;
	
	}

	public void undo() {
		
	}


}
