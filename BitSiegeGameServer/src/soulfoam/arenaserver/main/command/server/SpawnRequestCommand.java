package soulfoam.arenaserver.main.command.server;

import java.net.InetAddress;
import java.sql.Timestamp;

import soulfoam.arenaserver.entities.Entity;
import soulfoam.arenaserver.entities.EntityBook;
import soulfoam.arenaserver.main.command.Command;
import soulfoam.arenaserver.main.game.Game;
import soulfoam.arenaserver.main.resources.Res;
import soulfoam.arenaserver.net.NetworkManager;
import soulfoam.arenaserver.net.mainserver.LobbyEntity;
import soulfoam.arenashared.main.opcode.OPCode;


public class SpawnRequestCommand extends Command{

	private int accountID;
	private InetAddress ipAddress;
	private int port;
	
	public SpawnRequestCommand(int accountID, InetAddress ip, int port){
		super(Command.COMMAND_SPAWN_REQUEST);
		
		this.accountID = accountID;
		this.ipAddress = ip;
		this.port = port;
		
		
	}


	public boolean execute(int delta) {

		LobbyEntity lobbyEntity = NetworkManager.getNetworkManager().getPlayer(accountID);
		
		if (lobbyEntity != null){
			if (!lobbyEntity.isInitialized()){

				Entity playerEntity = EntityBook.getChallengerByID(lobbyEntity.getPlayerChallenger(), 0, 0, lobbyEntity.getPlayerSkin(), lobbyEntity.getPlayerUnderglow());
				playerEntity.setPlayerID(lobbyEntity.getGameID());
				playerEntity.setAccountID(lobbyEntity.getAccountID());
				playerEntity.setUsername(lobbyEntity.getPlayerName());
				playerEntity.setPlayerTeam(lobbyEntity.getPlayerTeam());
				playerEntity.setUsernameColor(lobbyEntity.getUsernameColor());

				float px = 0, py = 0;
				if (playerEntity.getTeam() == Res.TEAM_D){
					px = Game.getGame().getUtil().chooseRandomSpawn(Res.TEAM_D, true);
					py = Game.getGame().getUtil().chooseRandomSpawn(Res.TEAM_D, false);
				}
				else if (playerEntity.getTeam() == Res.TEAM_A){
					px = Game.getGame().getUtil().chooseRandomSpawn(Res.TEAM_A, true);
					py = Game.getGame().getUtil().chooseRandomSpawn(Res.TEAM_A, false);
				}
				
				playerEntity.setX(px);
				playerEntity.setY(py);
				
				playerEntity.setIpAddress(ipAddress);
				playerEntity.setPort(port);
				
				boolean alreadyConnected = false;

				for (Entity p: Game.getGame().getPlayers()){
	
					if (playerEntity.getPlayerID() == p.getPlayerID()){
						if (p.getIpAddress() == null){
							p.setIpAddress(playerEntity.getIpAddress());
						}
						if (p.getPort() == -1){
							p.setPort(playerEntity.getPort());
						}
						
						alreadyConnected = true;
					} 
					
				}
					
				if (!alreadyConnected){
					playerEntity.setJoinedTimeStamp(new Timestamp(System.currentTimeMillis()));
					Game.getGame().getServerFunctions().replaceBotWithPlayer(playerEntity);
					Game.getGame().getPlayers().add(playerEntity);
				}
				
				String spawnPacket = new String(OPCode.OP_SPAWNREQUEST + playerEntity.getPlayerID() + "," + Res.roundForNetwork(playerEntity.getX()) + "," + Res.roundForNetwork(playerEntity.getY()) 
				+ "," + playerEntity.getTeam() + "," + playerEntity.getChallengerType() + "," + playerEntity.getSkinID() + "," + playerEntity.getUnderglowID() + "," + playerEntity.getUsernameColor());
				
				Game.getGame().getServer().sendReliableData(spawnPacket, ipAddress, port);
				
				lobbyEntity.setInitialized(true); 
				
				Game.getGame().getServerFunctions().serverBroadcastPlayers();
			}
		}
		
		
		return true;
	
	}

	public void undo() {

	}


}
