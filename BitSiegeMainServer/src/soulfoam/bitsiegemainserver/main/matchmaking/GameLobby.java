package soulfoam.bitsiegemainserver.main.matchmaking;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

import soulfoam.arenashared.main.entityinfo.ChallengerInfo;
import soulfoam.arenashared.main.entityinfo.EntityInfo;
import soulfoam.arenashared.main.gameinfo.GameInfo;
import soulfoam.arenashared.main.lobbyopcode.LobbyOPCode;
import soulfoam.arenashared.main.lobbyutil.BSTimer;
import soulfoam.arenashared.main.lobbyutil.LobbyUtil;
import soulfoam.bitsiegemainserver.main.ClientThread;
import soulfoam.bitsiegemainserver.main.GameServerThread;
import soulfoam.bitsiegemainserver.main.NetworkManager;
import soulfoam.bitsiegemainserver.main.party.PartyGroup;

public class GameLobby {
	
	private ArrayList<GameLobbyClient> clients = new ArrayList<GameLobbyClient>();
	
	private BSTimer countdownTimer;
	private boolean checkedAllClientsReady;
	
	private int mapID;
	private boolean finished;
	
	private GameServerThread gameServer;
	
	public GameLobby(GameServerThread gameServer, ArrayList<ClientThread> teamA, ArrayList<ClientThread> teamD){	
		
		this.gameServer = gameServer;
		
		countdownTimer = new BSTimer(100000000);
//		mapID = LobbyUtil.randInt(0, 2);
		mapID = 0;
	
		for (ClientThread ct : teamA){
			ct.getParty().setCurrentGameLobby(this);
			GameLobbyClient glc = new GameLobbyClient(ct);
			glc.setTeamPick(GameInfo.TEAM_A);
			clients.add(glc);
		}
		
		for (ClientThread ct : teamD){
			ct.getParty().setCurrentGameLobby(this);
			GameLobbyClient glc = new GameLobbyClient(ct);
			glc.setTeamPick(GameInfo.TEAM_D);
			clients.add(glc);
		}
		
		broadcastLobbyAddUsers();
		broadcastStartTimer();
		broadcastMap();
	}
	
	public void update(){
		
		if (countdownTimer.update()){
			finished = true;
		}	
		
		if (!checkedAllClientsReady && countdownTimer.getElapsedTime() >= (countdownTimer.getTimerDuration() - 5000)){
			checkAllClientsReady();
			checkedAllClientsReady = true;
		}
		
	}
	
	private void broadcastMap(){
		for (GameLobbyClient glc : clients){
			glc.getClientThread().getOutput().println(LobbyOPCode.OP_LOBBYUPDATE);
			glc.getClientThread().getOutput().println("M~" + mapID);
		}
	}
	
	private void checkAllClientsReady(){
		for (GameLobbyClient glc : clients){
			if (!glc.isReady()){
				lockInChallenger(EntityInfo.RANDOMCHALLENGER, glc.getClientThread().getUserAccount().getID());
			}
		}
	}
	
	private void broadcastStartTimer(){
		for (GameLobbyClient glc : clients){
			glc.getClientThread().getOutput().println(LobbyOPCode.OP_LOBBYUPDATE);
			glc.getClientThread().getOutput().println("T~" + countdownTimer.getTimerDuration());
		}
	}
	
	private void broadcastLobbyAddUsers(){
		String userString = "";
		for (GameLobbyClient glc : clients){
			userString += glc.getClientThread().getUserAccount().getID() + "," + glc.getClientThread().getUserAccount().getUsername() + "," +
					glc.getClientThread().getUserAccount().getNameColor() + "," + glc.getTeam() + "," + glc.getChallengerPick() + "]";
		}
		for (GameLobbyClient glc : clients){
			glc.getClientThread().getOutput().println(LobbyOPCode.OP_LOBBYUPDATE);
			glc.getClientThread().getOutput().println("A~" + userString);
		}

	}
	
	private void broadcastLobbyRemoveUser(int id){
		for (GameLobbyClient glc : clients){
			glc.getClientThread().getOutput().println(LobbyOPCode.OP_LOBBYUPDATE);
			glc.getClientThread().getOutput().println("R~" + id);
		}

	}
	
	public void broadcastGameStart(){
		for (GameLobbyClient glc : clients){
			glc.getClientThread().getOutput().println(LobbyOPCode.OP_GAME);
			glc.getClientThread().getOutput().println(gameServer.getSocket().getInetAddress().getHostAddress() + "," + gameServer.getPort());
		}
	}
	
	public GameLobbyClient getClientInLobby(int accountID){
		for (GameLobbyClient glc : clients){
			if (glc.getClientThread().getUserAccount().getID() == accountID){
				return glc;
			}
		}
		
		return null;
	}
	
	public void setPartiesGameLobbyToNull(){
		for (GameLobbyClient glc : clients){
			glc.getClientThread().getParty().setCurrentGameLobby(null);
		}
	}
	
	public void removeClient(ClientThread ct){
		if (ct == null){
			return;
		}
		clients.remove(getClientInLobby(ct.getUserAccount().getID()));
		broadcastLobbyRemoveUser(ct.getUserAccount().getID());
	}
	
	public void lockInChallenger(int challengerID, int accountID){
		GameLobbyClient localglc = getClientInLobby(accountID);
		
		if (localglc != null){
			
			if (localglc.isReady()){
				return;
			}
			
			if (challengerID == EntityInfo.RANDOMCHALLENGER){
				ArrayList<Integer> takenChallengers = new ArrayList<Integer>();
				ArrayList<Integer> availableChallengers = new ArrayList<Integer>();
				
				for (GameLobbyClient glc : clients){
					if (glc.getTeam() == localglc.getTeam() && glc.isReady()){
						takenChallengers.add(glc.getChallengerPick());
					}
				}
				
				for (ChallengerInfo ci : localglc.getClientThread().getUserAccount().getUnlockedChallengers()){
					availableChallengers.add(ci.getID());
				}
				
				availableChallengers.removeAll(takenChallengers);
				
				Random random = new Random();
				int randIndex = random.nextInt(availableChallengers.size());
				
				int randomChallengerID = availableChallengers.get(randIndex);
				
				lockInAndBroadcast(localglc, randomChallengerID);
			}
			else{
				boolean canPick = true;
				
				if (localglc.getClientThread().getUserAccount().hasChallengerUnlocked(challengerID)){
					for (GameLobbyClient glc : clients){
						if (glc.getTeam() == localglc.getTeam()){
							if (challengerID == glc.getChallengerPick()){
								canPick = false;
							}
						}
					}
				}
				else{
					canPick = false;
				}
				
				if (canPick){
					lockInAndBroadcast(localglc, challengerID);
				}
			}
		
		}
	}
	
	private void lockInAndBroadcast(GameLobbyClient localglc, int challengerID){
		localglc.setChallengerPick(challengerID);
		localglc.setReady(true);
		
		for (GameLobbyClient glc : clients){
			glc.getClientThread().getOutput().println(LobbyOPCode.OP_LOBBYUPDATE);
			if (glc.getTeam() == localglc.getTeam()){
				if (localglc.getClientThread().getUserAccount().getID() == glc.getClientThread().getUserAccount().getID()){
					glc.getClientThread().getOutput().println("LL~" + challengerID); //local lock in
				}
				else{
					glc.getClientThread().getOutput().println("LT~" + challengerID + "~" + localglc.getClientThread().getUserAccount().getID());
				}
			}
			else{
				glc.getClientThread().getOutput().println("LE~" + challengerID + "~" + localglc.getClientThread().getUserAccount().getID());
			}
		}
	}

	public void pickSkin(int skinID, int accountID){
		GameLobbyClient localglc = getClientInLobby(accountID);
		
		if (localglc != null){
			
			if (!localglc.isReady()){
				return;
			}
			
			if (localglc.getClientThread().getUserAccount().hasSkinUnlocked(localglc.getChallengerPick(), skinID)){
				localglc.setSkinPick(skinID);
			}
			
		}
	}
	
	public void pickUnderglow(int underglowID, int accountID){
		GameLobbyClient localglc = getClientInLobby(accountID);
		
		if (localglc != null){
			
			if (localglc.getClientThread().getUserAccount().hasUnderglowUnlocked(underglowID)){
				localglc.setUnderglowPick(underglowID);
			}
			
		}
	}
	
	public void submitChatMessage(int accountID, String message, boolean allChat){
		GameLobbyClient glc = getClientInLobby(accountID);
		
		if (glc == null){
			return;
		}
		
		message = message.trim();
		
		if (message.isEmpty()){
			return;
		}
		
		processChatMessage(glc.getClientThread().getUserAccount().getUsername(), message, allChat, glc.getTeam());
	}
	
	private void processChatMessage(String username, String message, boolean allChat, byte team){
		for (GameLobbyClient glc : clients){
			String messageToSend = username + ": " + message;
			if (allChat){
				glc.getClientThread().getOutput().println(LobbyOPCode.OP_LOBBYUPDATE);
				glc.getClientThread().getOutput().println("MA" + messageToSend);
			}
			else{
				if (glc.getTeam() == team){
					glc.getClientThread().getOutput().println(LobbyOPCode.OP_LOBBYUPDATE);
					glc.getClientThread().getOutput().println("MT" + messageToSend);
				}
			}
		}
	}
	
	public ArrayList<GameLobbyClient> getClients() {
		return clients;
	}

	public BSTimer getCountdownTimer() {
		return countdownTimer;
	}

	public void setCountdownTimer(BSTimer countdownTimer) {
		this.countdownTimer = countdownTimer;
	}

	public int getMapID() {
		return mapID;
	}

	public boolean isFinished() {
		return finished;
	}

	public GameServerThread getGameServer() {
		return gameServer;
	}
	
}

