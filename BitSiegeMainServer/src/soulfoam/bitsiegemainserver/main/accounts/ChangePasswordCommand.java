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

public class ChangePasswordCommand extends Command{
	
	private ClientThread clientThread;
	private String[] passwordInfo;
	
	public ChangePasswordCommand(ClientThread clientThread, String[] passwordInfo){
		this.clientThread = clientThread;
		this.passwordInfo = passwordInfo;
	}
	
	public boolean execute() {
		
		int result = NetworkManager.getManager().getAccountManager().changeAccountPassword(passwordInfo[0], passwordInfo[1], passwordInfo[2], clientThread.getUserAccount().getID(), clientThread.getSocket().getInetAddress().getHostAddress());
		clientThread.getOutput().println(LobbyOPCode.OP_CHANGEPASSWORD);
		clientThread.getOutput().println(result);
		if (result == LobbyReturnCode.ACCOUNT_CHANGEPASSWORD_SUCCESS){
			clientThread.getUserAccount().setPassword(LobbyUtil.sha256(passwordInfo[1]));
		}
		
		return true;
	}

	public void undo() {
		
	}


}
