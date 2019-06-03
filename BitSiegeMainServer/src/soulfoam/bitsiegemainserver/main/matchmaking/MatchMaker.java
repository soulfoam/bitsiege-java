package soulfoam.bitsiegemainserver.main.matchmaking;

import java.util.ArrayList;

import soulfoam.arenashared.main.gameinfo.GameInfo;
import soulfoam.arenashared.main.lobbyopcode.LobbyOPCode;
import soulfoam.arenashared.main.lobbyopcode.LobbyReturnCode;
import soulfoam.arenashared.main.lobbyutil.BSTimer;
import soulfoam.bitsiegemainserver.main.ClientThread;
import soulfoam.bitsiegemainserver.main.GameServerThread;
import soulfoam.bitsiegemainserver.main.NetworkManager;
import soulfoam.bitsiegemainserver.main.notify.NotifyPartyOfMatchMakingSearchCommand;
import soulfoam.bitsiegemainserver.main.party.PartyGroup;

public class MatchMaker {
	
	private BSTimer searchTimer;
	private GameServerThread gameServer;
	private ArrayList<ClientThread> teamAUsers = new ArrayList<ClientThread>();
	private ArrayList<ClientThread> teamDUsers = new ArrayList<ClientThread>();
	private ArrayList<PartyGroup> parties = new ArrayList<PartyGroup>();
	
	private boolean finished;
	
	public MatchMaker(){
		init();
	}
	
	public void init(){
		
		searchTimer = new BSTimer(5000);

	}
	
	public void update(){
		
		if (searchTimer.update() || getTotalUsers() >= GameInfo.MAX_GAME_SIZE){
			finishSearching();
		}

	}
	
	public boolean addPlayersToOpenTeam(ArrayList<ClientThread> players){

		int teamASlotsAvailable = GameInfo.MAX_TEAM_SIZE - teamAUsers.size();
		int teamDSlotsAvailable = GameInfo.MAX_TEAM_SIZE - teamDUsers.size();
		
		if (players.size() <= teamASlotsAvailable){
			teamAUsers.addAll(players);
			return true;
		}
		if (players.size() <= teamDSlotsAvailable){
			teamDUsers.addAll(players);
			return true;
		}
		
		return false;
	}
	
//	public boolean addPlayersToOpenTeam(ArrayList<ClientThread> players){
//
//		teamAUsers.add(players.get(0));
//		teamDUsers.add(players.get(1));
//		return true;
//
//	}
	
	public void finishSearching(){
		
		for (ClientThread ct : teamAUsers){
			ct.getOutput().println(LobbyOPCode.OP_MATCHMAKING);
			ct.getOutput().println(LobbyReturnCode.MATCHMAKING_QUE_GAMEFOUND);
		}
		
		for (ClientThread ct : teamDUsers){
			ct.getOutput().println(LobbyOPCode.OP_MATCHMAKING);
			ct.getOutput().println(LobbyReturnCode.MATCHMAKING_QUE_GAMEFOUND);
		}
		
		finished = true;
		
	}
	
	public void removePartyFromMatchMaking(PartyGroup party){
		
		for (ClientThread ct : party.getPartyMembers()){
			if (teamAUsers.contains(ct)){
				teamAUsers.remove(ct);
			}
			else{	
				if (teamDUsers.contains(ct)){
					teamDUsers.remove(ct);
				}
			}
		}
		
		party.setCurrentMatchMaker(null);
		parties.remove(party);
		try {
			NetworkManager.getManager().getCommandHandler().getCommandQueue().put(new NotifyPartyOfMatchMakingSearchCommand(party, LobbyReturnCode.MATCHMAKING_QUE_SEARCHINGSTOPPED));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void setPartiesMatchMakerToNull(){
		for (PartyGroup pg : parties){
			pg.setCurrentMatchMaker(null);
		}
	}
	
	public int getTotalUsers(){
		return teamAUsers.size() + teamDUsers.size();
	}
	
	public boolean isFinished() {
		return finished;
	}

	public void setFinished(boolean finished) {
		this.finished = finished;
	}

	public ArrayList<ClientThread> getTeamAUsers() {
		return teamAUsers;
	}

	public ArrayList<ClientThread> getTeamDUsers() {
		return teamDUsers;
	}

	public GameServerThread getGameServer() {
		return gameServer;
	}

	public void setGameServer(GameServerThread gameServer) {
		this.gameServer = gameServer;
	}

	public ArrayList<PartyGroup> getParties() {
		return parties;
	}

}
