package soulfoam.bitsiegemainserver.main.chat;

import soulfoam.arenashared.main.lobbyopcode.LobbyOPCode;
import soulfoam.arenashared.main.lobbyopcode.LobbyReturnCode;
import soulfoam.arenashared.main.lobbyutil.LobbyUtil;
import soulfoam.bitsiegemainserver.main.ClientThread;
import soulfoam.bitsiegemainserver.main.NetworkManager;
import soulfoam.bitsiegemainserver.main.command.Command;
import soulfoam.bitsiegemainserver.main.party.PartyGroup;

public class SendWhisperCommand extends Command{
	
	private ClientThread clientThread;
	private int friendID;
	private String message = "";

	
	public SendWhisperCommand(ClientThread clientThread, int friendID, String message){
		this.clientThread = clientThread;
		this.friendID = friendID;
		this.message = message;
	}
	
	public boolean execute() {
		
		ClientThread ct = NetworkManager.getManager().getActiveAccountManager().getClientFromAccountID(friendID);
		
		if (ct != null){
			ct.getOutput().println(LobbyOPCode.OP_PRIVATECHAT);
			ct.getOutput().println(clientThread.getUserAccount().getID());
			ct.getOutput().println(message.trim());
		}
		
		return true;
	
	}

	public void undo() {
		
	}


}
