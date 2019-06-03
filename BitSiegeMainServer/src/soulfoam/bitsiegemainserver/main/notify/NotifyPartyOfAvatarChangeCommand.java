package soulfoam.bitsiegemainserver.main.notify;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import soulfoam.arenashared.main.ids.AvatarLibrary;
import soulfoam.arenashared.main.lobbyopcode.LobbyOPCode;
import soulfoam.arenashared.main.lobbyopcode.LobbyReturnCode;
import soulfoam.arenashared.main.lobbyutil.LobbyUtil;
import soulfoam.bitsiegemainserver.main.ClientThread;
import soulfoam.bitsiegemainserver.main.GameServerThread;
import soulfoam.bitsiegemainserver.main.NetworkManager;
import soulfoam.bitsiegemainserver.main.command.Command;
import soulfoam.bitsiegemainserver.main.db.Database;
import soulfoam.bitsiegemainserver.main.logs.LogType;
import soulfoam.bitsiegemainserver.main.matchmaking.MatchMaker;
import soulfoam.bitsiegemainserver.main.party.PartyGroup;
import soulfoam.bitsiegemainserver.main.party.PartyInvite;

public class NotifyPartyOfAvatarChangeCommand extends Command{
	
	private ClientThread clientThread;
	private int bgID;
	private int borderID;
	private int iconID;
	
	public NotifyPartyOfAvatarChangeCommand(ClientThread clientThread, int bgID, int borderID, int iconID){
		this.clientThread = clientThread;
		this.bgID = bgID;
		this.borderID = borderID;
		this.iconID = iconID;
	}
	
	
	public boolean execute() {

		for (ClientThread ct : clientThread.getParty().getPartyMembers()){
			ct.getOutput().println(LobbyOPCode.OP_LIVEUPDATE_USERAVATARCHANGE);
			ct.getOutput().println("P" + "," + clientThread.getUserAccount().getID() + "," + bgID + "," + borderID + "," + iconID);
		}
		
		return true;
	
	}


	public void undo() {
		
	}


}