package soulfoam.bitsiegemainserver.main.notify;

import soulfoam.arenashared.main.lobbyopcode.LobbyOPCode;
import soulfoam.arenashared.main.lobbyopcode.LobbyReturnCode;
import soulfoam.bitsiegemainserver.main.ClientThread;
import soulfoam.bitsiegemainserver.main.GameServerThread;
import soulfoam.bitsiegemainserver.main.NetworkManager;
import soulfoam.bitsiegemainserver.main.command.Command;
import soulfoam.bitsiegemainserver.main.logs.LogType;
import soulfoam.bitsiegemainserver.main.matchmaking.MatchMaker;
import soulfoam.bitsiegemainserver.main.party.PartyGroup;
import soulfoam.bitsiegemainserver.main.party.PartyInvite;

public class NotifyPartyOfMatchMakingSearchCommand extends Command{
	
	private PartyGroup party;
	private int result = -1;
	
	public NotifyPartyOfMatchMakingSearchCommand(PartyGroup partyGroup, int result){
		this.party = partyGroup;
		this.result = result;
	}
	
	public boolean execute() {
		
		if (party == null){
			return false;
		}

		for (ClientThread ct : party.getPartyMembers()){

			ct.getOutput().println(LobbyOPCode.OP_MATCHMAKING);
			ct.getOutput().println(result);
			
		}
		
		return true;
	
	}


	public void undo() {
		
	}


}