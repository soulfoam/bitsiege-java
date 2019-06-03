package soulfoam.bitsiegemainserver.main.command;

import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;

import soulfoam.arenashared.main.gameinfo.GameInfo;
import soulfoam.arenashared.main.lobbyopcode.LobbyReturnCode;
import soulfoam.arenashared.main.lobbyutil.BSTimer;
import soulfoam.arenasharedserver.main.opcode.ServerOPCode;
import soulfoam.bitsiegemainserver.main.ClientThread;
import soulfoam.bitsiegemainserver.main.GameServerThread;
import soulfoam.bitsiegemainserver.main.NetworkManager;
import soulfoam.bitsiegemainserver.main.matchmaking.GameLobby;
import soulfoam.bitsiegemainserver.main.matchmaking.GameLobbyClient;
import soulfoam.bitsiegemainserver.main.matchmaking.MatchMaker;
import soulfoam.bitsiegemainserver.main.matchmaking.MatchMakingCommand;
import soulfoam.bitsiegemainserver.main.party.PartyGroup;

public class CommandHandler extends Thread{

	public boolean isRunning = false;
	
	private ArrayList<MatchMaker> activeMatchMakers = new ArrayList<MatchMaker>();
	private ArrayList<GameLobby> activeGameLobbies = new ArrayList<GameLobby>();
	
	private ArrayList<MatchMaker> matchMakersToRemove = new ArrayList<MatchMaker>();
	private ArrayList<GameLobby> gameLobbiesToRemove = new ArrayList<GameLobby>();
	
	private LinkedBlockingQueue <Command> commandQueue = new LinkedBlockingQueue<Command>();
	private ArrayList<Command> commands = new ArrayList<Command>();

	public CommandHandler(){
		init();
	}
	
	public void init(){
		isRunning = true;
	}

	
	public void run() {
		while (isRunning){

			commandQueue.drainTo(commands);
			
			for (Command command : commands){
				command.execute();
			}
			
			for (MatchMaker mm : activeMatchMakers) {
				mm.update();
				if (mm.getTotalUsers() <= 0){
					System.out.println("mm 0 users removed");
					mm.getGameServer().setAvailable(true);
					mm.setPartiesMatchMakerToNull();
					matchMakersToRemove.add(mm);
					break;
				}
				if (mm.isFinished()){
					System.out.println("mm finished with : " + mm.getTotalUsers());
					mm.setPartiesMatchMakerToNull();
					activeGameLobbies.add(new GameLobby(mm.getGameServer(), mm.getTeamAUsers(), mm.getTeamDUsers()));
					matchMakersToRemove.add(mm);
				}
				
			}
			
			for (GameLobby gl : activeGameLobbies) {
				gl.update();
				if (gl.getClients().size() <= 0){
					System.out.println("gamelobby 0 users removed");
					gl.getGameServer().setAvailable(true);
					gl.setPartiesGameLobbyToNull();
					gameLobbiesToRemove.add(gl);
					break;
				}
				if (gl.isFinished()){
					gl.setPartiesGameLobbyToNull();
					sendLobbyToGameServer(gl);
					gameLobbiesToRemove.add(gl);
				}
			}
			
			activeMatchMakers.removeAll(matchMakersToRemove);
			matchMakersToRemove.clear();
			
			activeGameLobbies.removeAll(gameLobbiesToRemove);
			gameLobbiesToRemove.clear();
			
			commands.clear();

			try { Thread.sleep(5); } catch (InterruptedException e) {}
		}
	}
	
	public void sendLobbyToGameServer(GameLobby gl){
		
		String teamAString = "";
		String teamDString = "";
		
		for (GameLobbyClient glc : gl.getClients()){
			if (glc.getTeam() == GameInfo.TEAM_A){
				teamAString += glc.getClientThread().getUserAccount().getID() + "~" +
				glc.getClientThread().getUserAccount().getUsername() + "~" +
				glc.getClientThread().getUserAccount().getNameColor() + "~" +
				glc.getChallengerPick() + "~" + 
				glc.getSkinPick() + "~" +
				glc.getUnderglowPick() + "]";
			}
			if (glc.getTeam() == GameInfo.TEAM_D){
				teamDString += glc.getClientThread().getUserAccount().getID() + "~" +
				glc.getClientThread().getUserAccount().getUsername() + "~" +
				glc.getClientThread().getUserAccount().getNameColor() + "~" +
				glc.getChallengerPick() + "~" + 
				glc.getSkinPick() + "~" +
				glc.getUnderglowPick() + "]";
			}
			
		}
		
		GameServerThread gs = gl.getGameServer();
		gs.getOutput().println(ServerOPCode.OP_STARTGAME);
		gs.getOutput().println(gl.getMapID());
		gs.getOutput().println(teamAString);
		gs.getOutput().println(teamDString);
		
		gl.broadcastGameStart();
	}
	
	public ArrayList<MatchMaker> getActiveMatchMakers() {
		return activeMatchMakers;
	}

	public ArrayList<GameLobby> getActiveGameLobbies() {
		return activeGameLobbies;
	}

	public LinkedBlockingQueue<Command> getCommandQueue() {
		return commandQueue;
	}

	public ArrayList<Command> getCommands() {
		return commands;
	}
	
}
