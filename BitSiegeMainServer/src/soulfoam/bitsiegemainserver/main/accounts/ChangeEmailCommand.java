package soulfoam.bitsiegemainserver.main.accounts;

import soulfoam.arenashared.main.ids.AvatarLibrary;
import soulfoam.arenashared.main.lobbyopcode.LobbyOPCode;
import soulfoam.arenashared.main.lobbyopcode.LobbyReturnCode;
import soulfoam.arenashared.main.lobbyutil.LobbyUtil;
import soulfoam.bitsiegemainserver.main.ClientThread;
import soulfoam.bitsiegemainserver.main.GameServerThread;
import soulfoam.bitsiegemainserver.main.NetworkManager;
import soulfoam.bitsiegemainserver.main.command.Command;
import soulfoam.bitsiegemainserver.main.logs.LogType;
import soulfoam.bitsiegemainserver.main.matchmaking.MatchMaker;
import soulfoam.bitsiegemainserver.main.notify.NotifyFriendsOfAvatarChangeCommand;
import soulfoam.bitsiegemainserver.main.notify.NotifyPartyOfAvatarChangeCommand;

public class ChangeEmailCommand extends Command{
	
	private ClientThread clientThread;
	private String[] emailInfo;
	
	public ChangeEmailCommand(ClientThread clientThread, String[] passwordInfo){
		this.clientThread = clientThread;
		this.emailInfo = passwordInfo;
	}
	
	public boolean execute() {
		
		int result = NetworkManager.getManager().getAccountManager().changeAccountEmail(emailInfo[0], emailInfo[1], emailInfo[2], clientThread.getUserAccount().getID(), clientThread.getSocket().getInetAddress().getHostAddress());
		clientThread.getOutput().println(LobbyOPCode.OP_CHANGEEMAIL);
		clientThread.getOutput().println(result);
		if (result == LobbyReturnCode.ACCOUNT_CHANGEEMAIL_SUCCESS){
			clientThread.getUserAccount().setEmail(emailInfo[1]);
			clientThread.getOutput().println(clientThread.getUserAccount().getEmail());
		}
		
		return true;
	}

	public void undo() {
		
	}


}
