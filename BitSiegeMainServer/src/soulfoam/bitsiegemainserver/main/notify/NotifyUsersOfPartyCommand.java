package soulfoam.bitsiegemainserver.main.notify;

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
import soulfoam.bitsiegemainserver.main.party.PartyGroup;
import soulfoam.bitsiegemainserver.main.party.PartyInvite;

public class NotifyUsersOfPartyCommand extends Command{
	
	private PartyGroup party;
	
	public NotifyUsersOfPartyCommand(PartyGroup partyGroup){
		this.party = partyGroup;
	}
	
	public boolean execute() {

		if (party == null){
			return false;
		}
		
		String partyInfoString = LobbyUtil.boolToIntString(party.canAnyoneInvite()) + "[";
		
		for (ClientThread ct : party.getPartyMembers()){
			partyInfoString += ct.getUserAccount().getID() + "," + ct.getUserAccount().getUsername() 
			+ "," + ct.getUserAccount().getAvatarIDs()[AvatarLibrary.SLOT_BACKGROUND]
			+ "," + ct.getUserAccount().getAvatarIDs()[AvatarLibrary.SLOT_BORDER]
			+ "," + ct.getUserAccount().getAvatarIDs()[AvatarLibrary.SLOT_ICON]
			+ "," + ct.getUserAccount().getNameColor()
			+ "]";
		}
		
		for (ClientThread ct : party.getPartyMembers()){

			ct.getOutput().println(LobbyOPCode.OP_PARTYINFO);
			ct.getOutput().println(partyInfoString);
		}
		
		return true;
	
	}


	public void undo() {
		
	}


}