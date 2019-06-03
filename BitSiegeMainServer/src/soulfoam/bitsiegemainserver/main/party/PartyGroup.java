package soulfoam.bitsiegemainserver.main.party;

import java.util.ArrayList;

import soulfoam.bitsiegemainserver.main.ClientThread;
import soulfoam.bitsiegemainserver.main.matchmaking.GameLobby;
import soulfoam.bitsiegemainserver.main.matchmaking.MatchMaker;

public class PartyGroup {
	
	private int id;
	private ClientThread partyLeader;
	private boolean anyoneInvite;
	private MatchMaker currentMatchMaker;
	private GameLobby currentGameLobby;
	
	private ArrayList<ClientThread> partyMembers = new ArrayList<ClientThread>();
	
	public PartyGroup(int id, ClientThread clientThread){
		setPartyLeader(clientThread);
		setID(id);
		partyMembers.add(clientThread);
	}
	
	public void removePartyMember(ClientThread clientThread){

		if (currentMatchMaker != null){
			currentMatchMaker.removePartyFromMatchMaking(this);
		}
		
		partyMembers.remove(clientThread);
		
		if (getPartyLeader().getUserAccount().getID() == clientThread.getUserAccount().getID()){
			if (!partyMembers.isEmpty()){
				setPartyLeader(partyMembers.get(0));
			}
		}
	}
	
	public boolean isUserInParty(int accountID){

		for (ClientThread ct : partyMembers){
			if (ct.getUserAccount().getID() == accountID){
				return true;
			}
		}
		
		return false;
		
	}
	
	public void addPartyMember(ClientThread thread){
		partyMembers.add(thread);
	}
	
	public ArrayList<ClientThread> getPartyMembers() {
		return partyMembers;
	}

	public int getID() {
		return id;
	}

	public void setID(int id) {
		this.id = id;
	}

	public boolean canAnyoneInvite() {
		return anyoneInvite;
	}

	public void setAnyoneInvite(boolean anyoneCanInvite) {
		this.anyoneInvite = anyoneCanInvite;
	}

	public ClientThread getPartyLeader() {
		return partyLeader;
	}

	public void setPartyLeader(ClientThread partyLeader) {
		this.partyLeader = partyLeader;
	}
	
	public void setNewPartyLeader(int partyLeaderID){
		int index = partyMembers.indexOf(partyLeaderID);
		if (index == -1) return;
		partyMembers.remove(index);
		partyMembers.add(0, partyLeader);
		setPartyLeader(partyLeader);
	}

	public MatchMaker getCurrentMatchMaker() {
		return currentMatchMaker;
	}

	public void setCurrentMatchMaker(MatchMaker currentMatchMaker) {
		this.currentMatchMaker = currentMatchMaker;
	}

	public GameLobby getCurrentGameLobby() {
		return currentGameLobby;
	}

	public void setCurrentGameLobby(GameLobby currentGameLobby) {
		this.currentGameLobby = currentGameLobby;
	}
	
}
