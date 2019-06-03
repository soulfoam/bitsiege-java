package soulfoam.bitsiegemainserver.main.matchmaking;

import soulfoam.arenashared.main.lobbyopcode.LobbyReturnCode;
import soulfoam.arenashared.main.lobbyutil.LobbyUtil;
import soulfoam.bitsiegemainserver.main.ClientThread;
import soulfoam.bitsiegemainserver.main.GameServerThread;
import soulfoam.bitsiegemainserver.main.NetworkManager;
import soulfoam.bitsiegemainserver.main.command.Command;
import soulfoam.bitsiegemainserver.main.logs.LogType;
import soulfoam.bitsiegemainserver.main.notify.NotifyPartyOfMatchMakingSearchCommand;
import soulfoam.bitsiegemainserver.main.party.PartyGroup;

public class MatchMakingCommand extends Command{

	public static final byte QUE = 0;
	public static final byte CANCEL = 1;
	
	private byte action;
	private PartyGroup party;

	private int region;
	
	public MatchMakingCommand(ClientThread clientThread, int region){
		this.party = clientThread.getParty();
		this.region = region;
		this.action = QUE;
	}
	
	public MatchMakingCommand(PartyGroup party){
		this.party = party;
		this.action = CANCEL;
	}
	
	public PartyGroup getParty(){
		return party;
	}
	
	public int getRegion(){
		return region;
	}
	
	public boolean execute() {
		
		if (party == null){
			return false;
		}
		
		if (action == QUE){
			MatchMaker matchToJoin = null;
				
				for (MatchMaker mm: NetworkManager.getManager().getCommandHandler().getActiveMatchMakers()) {
					if (mm.addPlayersToOpenTeam(party.getPartyMembers())){
						matchToJoin = mm;
						break;
					}
				}
				
				if (matchToJoin == null){
					
					GameServerThread gs = NetworkManager.getManager().getActiveGameServerManager().getAvailableServer();
					
					if (gs == null){
						NetworkManager.getManager().getLogManager().addLog(LogType.ERROR_GAMESERVERTHREAD, "No game servers available! Add more servers cause we're getting too popular :)!");
						try {
							NetworkManager.getManager().getCommandHandler().getCommandQueue().put(new NotifyPartyOfMatchMakingSearchCommand(party, LobbyReturnCode.MATCHMAKING_QUE_NOAVAILABLEGAMESERVERS));
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						return false;
					}
	
					matchToJoin = new MatchMaker();
					matchToJoin.setGameServer(gs);
					matchToJoin.addPlayersToOpenTeam(party.getPartyMembers());
					gs.setAvailable(false);
					NetworkManager.getManager().getCommandHandler().getActiveMatchMakers().add(matchToJoin);
					
			
				}
				
				matchToJoin.getParties().add(party);
				party.setCurrentMatchMaker(matchToJoin);
				try {
					NetworkManager.getManager().getCommandHandler().getCommandQueue().put(new NotifyPartyOfMatchMakingSearchCommand(party, LobbyReturnCode.MATCHMAKING_QUE_SUCCESS));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
		}
		
		if (action == CANCEL){
			party.getCurrentMatchMaker().removePartyFromMatchMaking(party);
		}
		
		return true;
	
	}


	public void undo() {
		
	}


}
