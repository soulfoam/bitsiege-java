package soulfoam.arena.net.client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;

import org.newdawn.slick.Color;

import soulfoam.arena.entities.Entity;
import soulfoam.arena.entities.EntityBook;
import soulfoam.arena.entities.objectives.CapturePoint;
import soulfoam.arena.entities.objectives.HealthObjective;
import soulfoam.arena.entities.objectives.Objective;
import soulfoam.arena.entities.objectives.SmallCapturePoint;
import soulfoam.arena.main.command.client.MoveEntityCommand;
import soulfoam.arena.main.command.client.RemoveAbilityCommand;
import soulfoam.arena.main.command.client.RemoveEntityCommand;
import soulfoam.arena.main.command.client.SpawnAbilityCommand;
import soulfoam.arena.main.command.client.SpawnLocalPlayerCommand;
import soulfoam.arena.main.command.client.SpawnPlayersCommand;
import soulfoam.arena.main.command.client.UpdateEntityCommand;
import soulfoam.arena.main.command.client.UpdateEntityMiscCommand;
import soulfoam.arena.main.game.Game;
import soulfoam.arena.main.gfx.MessageBox;
import soulfoam.arena.main.misc.ChatMessage;
import soulfoam.arena.main.resources.Res;
import soulfoam.arena.main.util.GameUtil;
import soulfoam.arena.world.Tile;
import soulfoam.arenashared.main.lobbyutil.LobbyUtil;
import soulfoam.arenashared.main.objectiveinfo.ObjectiveInfo;
import soulfoam.arenashared.main.opcode.OPCode;

public class GameClient extends Thread {

	private InetAddress ipAddress;
	private int serverPort;
	private DatagramSocket socket;

	private boolean isRunning;
	
	private int sequenceNumber = 0;
	
	public GameClient(String ipAddress, int port) {

		isRunning = true;
		serverPort = port;
		
		try {
			this.socket = new DatagramSocket();
		} catch (SocketException e) {
			e.printStackTrace();
		}

		try {
			this.ipAddress = InetAddress.getByName(ipAddress);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}

	}

	public void run() {

		while (isRunning) {
			byte[] data = new byte[1024];
			DatagramPacket packet = new DatagramPacket(data, data.length);

			try {
				getSocket().receive(packet);
			} catch (IOException e) {
				e.printStackTrace();
			}

			String message;
			message = new String(packet.getData()).trim();

			Game.getGame().getClientFunctions().setClientPacketBytes(Game.getGame().getClientFunctions().getClientPacketBytes() + packet.getLength());
			Game.getGame().getClientFunctions().setServerResponding(true);
			
			if (!message.trim().isEmpty() && message.length() >= 2){
				
				String reliableMessage = message;
				
				if (message.trim().substring(0, 2).equals(OPCode.OP_RELIABLEDATA)){
					int pos = message.lastIndexOf("~");
					String payload = message.trim().substring(0, pos);
					int seqNumber = LobbyUtil.parseInt(message.trim().substring(pos + 1));
					
					reliableMessage = payload.substring(2).trim(); 
					
					System.out.println("CLIENT ACKED: " + reliableMessage + " " + seqNumber);
					
					sendData(OPCode.OP_ACKFORRELIABLEDATA + seqNumber);
				}
				
				if (message.trim().substring(0, 2).equals(OPCode.OP_ACKFORRELIABLEDATA)){
					int seqNumber = LobbyUtil.parseInt(message.trim().substring(2));
					try {
						Game.getGame().getClientFunctions().getCommandHandler().getReliablePacketQueue().put(new AckPacket(seqNumber));
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
				}
				
				processPacket(reliableMessage, packet);

			}
			
		}
	}
	
	public void processPacket(String message, DatagramPacket packet){

		String[] messageData = message.substring(2).split(",");

		if (message.trim().substring(0, 2).equals(OPCode.OP_ADDPLAYERS)) {
			ArrayList<Entity> players = new ArrayList<Entity>();
			String[] playerDataSet = message.substring(2).trim().split("]");

			for (int i = 0; i < playerDataSet.length; i++) {

				String[] playerData = playerDataSet[i].trim().split(",");
				
				if (playerData.length == 12) {

					int puid = Integer.parseInt(playerData[0]);
					float px = Float.parseFloat(playerData[1]);
					float py = Float.parseFloat(playerData[2]);
					byte pcd = Byte.parseByte(playerData[3]);
					int pc = Integer.parseInt(playerData[4]);
					byte pt = Byte.parseByte(playerData[5]);
					String pu = playerData[6];
					int psi = Integer.parseInt(playerData[7]);
					int pui = Integer.parseInt(playerData[8]);
					int puncr = Integer.parseInt(playerData[9]);
					int puncg = Integer.parseInt(playerData[10]);
					int puncb = Integer.parseInt(playerData[11]);
					
					Entity newPlayer = EntityBook.getChallengerByID(pc, px, py, psi, pui);

					newPlayer.setCurrentDirection(pcd);
					newPlayer.setPlayerID(puid);
					newPlayer.setUsername(pu);
					newPlayer.setTeam(pt);
					newPlayer.setUsernameColor(new Color(puncr, puncg, puncb));
					players.add(newPlayer);
				
				}
			}
			
			try {
				Game.getGame().getClientFunctions().getCommandHandler().getCommandQueue().put(new SpawnPlayersCommand(players));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		if (!Game.getGame().isLoaded()) {
			if (message.trim().substring(0, 2).equals(OPCode.OP_SPAWNREQUEST)) {
				if (messageData.length == 10) {
					if (!Game.getGame().getClientFunctions().gotRespawn()) {
						int id = Integer.parseInt(messageData[0]);
						float x = Float.parseFloat(messageData[1]);
						float y = Float.parseFloat(messageData[2]);
						byte team = Byte.parseByte(messageData[3]);
						int classPick = Integer.parseInt(messageData[4]);
						int skinPick = Integer.parseInt(messageData[5]);
						int underglowPick = Integer.parseInt(messageData[6]);
						int r = Integer.parseInt(messageData[7]);
						int g = Integer.parseInt(messageData[8]);
						int b = Integer.parseInt(messageData[9]);
						
						try {
							Game.getGame().getClientFunctions().getCommandHandler().getCommandQueue().put(new SpawnLocalPlayerCommand(id, x, y, team, classPick, skinPick, underglowPick, r, g, b));
						} catch (InterruptedException e) {
							e.printStackTrace();
						}

					}
				}
			}
		} 
		else {

			if (message.trim().substring(0, 2).equals(OPCode.OP_PLAYERUPDATE)) {

				String[] playerDataSet = message.substring(2).trim().split("]");

				for (int i = 0; i < playerDataSet.length; i++) {

					String[] playerData = playerDataSet[i].trim().split(",");
					
					if (playerData.length == 13){
						int puid = Integer.parseInt(playerData[0]);
						float px = Float.parseFloat(playerData[1]);
						float py = Float.parseFloat(playerData[2]);
						byte pcd = Byte.parseByte(playerData[3]);
						byte pca = Byte.parseByte(playerData[4]);
						float ph = Float.parseFloat(playerData[5]);
						boolean pis = GameUtil.byteToBool(Byte.parseByte(playerData[6]));
						boolean pib = GameUtil.byteToBool(Byte.parseByte(playerData[7]));
						boolean pii = GameUtil.byteToBool(Byte.parseByte(playerData[8]));
						float ps = Float.parseFloat(playerData[9]);
						boolean pic = GameUtil.byteToBool(Byte.parseByte(playerData[10]));
						byte pcv = Byte.parseByte(playerData[11]);
						boolean pid = GameUtil.byteToBool(Byte.parseByte(playerData[12]));

						try {

							if (Game.getGame().getPlayer().getPlayerID() != puid){
								Game.getGame().getClientFunctions().getCommandHandler().getCommandQueue().put(new MoveEntityCommand(puid, px, py, pcv, pcd, pca));
							}
							else{
								Game.getGame().getPlayer().setCurrentDirection(pcd);
							}
							
							Game.getGame().getClientFunctions().getCommandHandler().getCommandQueue().put(new UpdateEntityCommand(puid, ph, ps, pis, pib, pii, pic, pid));
							
						} catch (InterruptedException e) {
							e.printStackTrace();
						}									
					
					}
				
				}
			}

			if (message.trim().substring(0, 2).equals(OPCode.OP_PLAYERUPDATEMISC)) {
				String[] playerDataSet = message.substring(2).trim().split("]");

				for (int i = 0; i < playerDataSet.length; i++) {

					String[] playerData = playerDataSet[i].trim().split(",");

					if (playerData.length == 19) {
						int puid = Integer.parseInt(playerData[0]);
						float baseHealth = Float.parseFloat(playerData[1]);
						float moveSpeed = Float.parseFloat(playerData[2]);
						int playerKills = Integer.parseInt(playerData[3]);
						int playerDeaths = Integer.parseInt(playerData[4]);
						int playerCaptures = Integer.parseInt(playerData[5]);
						int playerAssists = Integer.parseInt(playerData[6]);
						float attackSpeedBuffAmount = Float.parseFloat(playerData[7]);
						float damageBuffAmount = Float.parseFloat(playerData[8]);
						float moveSpeedBuffAmount = Float.parseFloat(playerData[9]);
						int gold = Integer.parseInt(playerData[10]);
						byte pt = Byte.parseByte(playerData[11]);
						float baseShield = Float.parseFloat(playerData[12]);
						int a1cd = Integer.parseInt(playerData[13]);
						int a2cd = Integer.parseInt(playerData[14]);
						int a3cd = Integer.parseInt(playerData[15]);
						int a4cd = Integer.parseInt(playerData[16]);
						int ping = Integer.parseInt(playerData[17]);
						float abacd = Float.parseFloat(playerData[18]);
						
						try {
							Game.getGame().getClientFunctions().getCommandHandler().getCommandQueue().put(new UpdateEntityMiscCommand(puid, baseHealth, baseShield, moveSpeed, playerKills, playerDeaths, playerCaptures, playerAssists,
									attackSpeedBuffAmount, damageBuffAmount, moveSpeedBuffAmount, gold, pt, a1cd, a2cd, a3cd,
									a4cd, ping, abacd));
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
							
			
					}
				}
			}

			if (message.trim().substring(0, 2).equals(OPCode.OP_ABILITYPOSITIONUPDATE)) {
//				if (messageData.length == 7) {
//					int gid = Integer.parseInt(messageData[0]);
//					int aid = Integer.parseInt(messageData[1]);
//					float ax = Float.parseFloat(messageData[2]);
//					float ay = Float.parseFloat(messageData[3]);
//					float dx = Float.parseFloat(messageData[4]);
//					float dy = Float.parseFloat(messageData[5]);
//					boolean interp = Boolean.parseBoolean(messageData[6]);
//
//					Ability a = GameHolder.getGame().getAbilityObject(gid);
//
//					if (GameHolder.getGame().getAbilities().contains(a)) {
//						if (a != null) {
//							if (a.getAbilityID() == aid) {
//								if (!interp) {
//									a.setX(ax);
//									a.setY(ay);
//								} else {
//									a.setInterpTimer(a.getInterpTime());
//									a.setRemoteX(ax);
//									a.setRemoteY(ay);
//								}
//								a.setDX(dx);
//								a.setDY(dy);
//							} else {
//								GameHolder.getGame().getClientFunctions().syncGame();
//							}
//						}
//					} else {
//						GameHolder.getGame().getClientFunctions().syncGame();
//					}
//				}
			}

			if (message.trim().substring(0, 2).equals(OPCode.OP_CHATMESSAGE)) {
				String chatMessage = message.trim().substring(3);
				byte chatColorID = Byte.parseByte(message.trim().substring(2, 3));

				Color chatColor = Color.green;
				if (chatColorID == 1) {
					chatColor = Color.orange;
				}

				if (Game.getGame().getHUDDisplay().getChatHUD().getChatList().size() >= 23) {
					Game.getGame().getHUDDisplay().getChatHUD().getChatList().clear();
				}
				if (Res.bitFont.getWidth(chatMessage,Game.getGame().getHUDDisplay().getChatHUD().getTextScale()) >= Game.getGame().getHUDDisplay().getChatHUD().getWidth()) {
					int charLimit = (int) (Game.getGame().getHUDDisplay().getChatHUD().getWidth() / (Tile.TILE_SIZE * Game.getGame().getHUDDisplay().getChatHUD().getTextScale()) - Tile.TILE_SIZE
									* Game.getGame().getHUDDisplay().getChatHUD().getTextScale());
					for (String chatTextPart : GameUtil.getStringParts(chatMessage, charLimit)) {
						if (chatTextPart != null) {
							Game.getGame().getHUDDisplay().getChatHUD().getChatList()
									.add(new ChatMessage(chatTextPart.trim(), chatColor));
						}
					}
				} else {
					Game.getGame().getHUDDisplay().getChatHUD().getChatList()
							.add(new ChatMessage(chatMessage.trim(), chatColor));
				}

				if (chatMessage.trim().startsWith("/")) {
					Game.getGame().getClientSettings().handleCommand(chatMessage.trim());
				}
			}

			if (message.trim().substring(0, 2).equals(OPCode.OP_REMOVEPLAYER)) {
				if (messageData.length == 1) {
					if (!messageData[0].isEmpty()) {
						int pu = Integer.parseInt(messageData[0]);

						try {
							Game.getGame().getClientFunctions().getCommandHandler().getCommandQueue().put(new RemoveEntityCommand(pu));
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						
					}
				}
			}

			if (message.trim().substring(0, 2).equals(OPCode.OP_PINGINGAME)) {
				sendData(OPCode.OP_PINGINGAME);
			}

			if (message.trim().substring(0, 2).equals(OPCode.OP_PLAYERPOSITIONUPDATE)) {
				if (messageData.length == 2) {
					float px = Float.parseFloat(messageData[0]);
					float py = Float.parseFloat(messageData[1]);

					try {
						Game.getGame().getClientFunctions().getCommandHandler().getCommandQueue().put(new MoveEntityCommand(Game.getGame().getPlayer().getPlayerID(), px, py));
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

				}
			}

			if (message.trim().substring(0, 2).equals(OPCode.OP_ADDABILITY)) {
				// System.out.println("CLIENT Got add ability CLIENT: "
				// + message);
				if (messageData.length == 7) {
					int puid = Integer.parseInt(messageData[0]);
					int abilityID = Integer.parseInt(messageData[1]);
					int gameID = Integer.parseInt(messageData[2]);
					float x = Float.parseFloat(messageData[3]);
					float y = Float.parseFloat(messageData[4]);
					float dx = Float.parseFloat(messageData[5]);
					float dy = Float.parseFloat(messageData[6]);
					
					try {
						Game.getGame().getClientFunctions().getCommandHandler().getCommandQueue().put(new SpawnAbilityCommand(puid, abilityID, gameID, x, y, dx, dy));
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
				}
			}

			if (message.trim().substring(0, 2).equals(OPCode.OP_OBJECTIVEUPDATECAPTUREPOINT)) {

				String[] objectiveDataSet = message.substring(2).trim().split("]");

				for (int i = 0; i < objectiveDataSet.length; i++) {

					String[] objectiveData = objectiveDataSet[i].trim().split(",");

					if (objectiveData.length != 7) {
						break;
					}

					int ogid = Integer.parseInt(objectiveData[0]);
					byte ot = Byte.parseByte(objectiveData[1]);
					float ox = Float.parseFloat(objectiveData[2]);
					float oy = Float.parseFloat(objectiveData[3]);
					float oh = Float.parseFloat(objectiveData[4]);
					float obh = Float.parseFloat(objectiveData[5]);
					byte oct = Byte.parseByte(objectiveData[6]);

					Objective o = Game.getGame().getObjectiveObject(ogid);

					if (Game.getGame().getObjectives().contains(o)) {
						o.setRemoteX(ox);
						o.setRemoteY(oy);
						o.setHealth(oh);
						o.setBaseHealth(obh);
						o.setCaptureTeam(oct);
					}

					if (!Game.getGame().getObjectives().contains(o)) {
						Objective receivedObjective = null;
						
						if (ot == ObjectiveInfo.CAPTURE_POINT){
							receivedObjective = new CapturePoint(ox, oy);
						}
						if (ot == ObjectiveInfo.SMALL_CAPTURE_POINT){
							receivedObjective = new SmallCapturePoint(ox, oy);
						}
						receivedObjective.setObjectiveGameID(ogid);
						receivedObjective.setHealth(oh);
						if (!Game.getGame().getMapEditor().editMode) {
							Game.getGame().getObjectives().add(receivedObjective);
						}
					}
				}

			}

			if (message.trim().substring(0, 2).equals(OPCode.OP_OBJECTIVEUPDATEHEALTHORB)) {

				String[] objectiveDataSet = message.substring(2).trim().split("]");

				for (int i = 0; i < objectiveDataSet.length; i++) {

					String[] objectiveData = objectiveDataSet[i].trim().split(",");

					if (objectiveData.length != 7) {
						break;
					}

					int ogid = Integer.parseInt(objectiveData[0]);
					float ox = Float.parseFloat(objectiveData[1]);
					float oy = Float.parseFloat(objectiveData[2]);
					byte ot = Byte.parseByte(objectiveData[3]);
					boolean oiho = GameUtil.byteToBool(Byte.parseByte(objectiveData[4]));
					int oph = Integer.parseInt(objectiveData[5]);
					boolean onr = GameUtil.byteToBool(Byte.parseByte(objectiveData[6]));

					Objective o = Game.getGame().getObjectiveObject(ogid);

					if (Game.getGame().getObjectives().contains(o)) {
						o.setRemoteX(ox);
						o.setRemoteY(oy);
						o.setBeingHeld(oiho);
						o.setObjectiveHolderID(oph);

						if (onr) {
							HealthObjective oho = (HealthObjective) o;
							oho.playAnimation();
							Game.getGame().getObjectives().remove(o);
						}
					}

					if (!Game.getGame().getObjectives().contains(o) && !onr) {

						Objective receivedObjective = null;

						if (ot == ObjectiveInfo.HEALTHPICKUP_OBJECTIVE) {
							receivedObjective = new HealthObjective(ox, oy, ObjectiveInfo.HEALTHPICKUP_OBJECTIVE);
							receivedObjective.setObjectiveGameID(ogid);
						}

						if (ot == ObjectiveInfo.HEALTHPICKUPSMALL_OBJECTIVE) {
							receivedObjective = new HealthObjective(ox, oy,
									ObjectiveInfo.HEALTHPICKUPSMALL_OBJECTIVE);
							receivedObjective.setObjectiveGameID(ogid);
						}

						if (ot == ObjectiveInfo.HEALTHPICKUPPLAYER_OBJECTIVE) {
							receivedObjective = new HealthObjective(ox, oy,
									ObjectiveInfo.HEALTHPICKUPPLAYER_OBJECTIVE);
							receivedObjective.setObjectiveGameID(ogid);
						}
						
						if (!Game.getGame().getMapEditor().editMode) {
							Game.getGame().getObjectives().add(receivedObjective);
						}
					}
				}

			}

			if (message.trim().substring(0, 2).equals(OPCode.OP_OBJECTIVEUPDATEHEALTHORBPLAYER)) {

				String[] objectiveDataSet = message.substring(2).trim().split("]");

				for (int i = 0; i < objectiveDataSet.length; i++) {

					String[] objectiveData = objectiveDataSet[i].trim().split(",");

					if (objectiveData.length != 6) {
						break;
					}

					int ogid = Integer.parseInt(objectiveData[0]);
					float ox = Float.parseFloat(objectiveData[1]);
					float oy = Float.parseFloat(objectiveData[2]);
					boolean oiho = GameUtil.byteToBool(Byte.parseByte(objectiveData[3]));
					int oph = Integer.parseInt(objectiveData[4]);
					boolean onr = GameUtil.byteToBool(Byte.parseByte(objectiveData[5]));

					Objective o = Game.getGame().getObjectiveObject(ogid);

					if (Game.getGame().getObjectives().contains(o)) {
						o.setRemoteX(ox);
						o.setRemoteY(oy);
						o.setBeingHeld(oiho);
						o.setObjectiveHolderID(oph);

						if (onr) {
							HealthObjective oho = (HealthObjective) o;
							oho.playAnimation();
							Game.getGame().getObjectives().remove(o);

						}
					}

					if (!Game.getGame().getObjectives().contains(o) && !onr) {

						Objective receivedObjective = null;

						receivedObjective = new HealthObjective(ox, oy, ObjectiveInfo.HEALTHPICKUPPLAYER_OBJECTIVE);
						receivedObjective.setObjectiveGameID(ogid);

						Game.getGame().getObjectives().add(receivedObjective);

					}
				}

			}

			if (message.trim().substring(0, 2).equals(OPCode.OP_GAMEINFOUPDATE)) {
				if (messageData.length == 3) {
					int t0p = Integer.parseInt(messageData[0]);
					int t1p = Integer.parseInt(messageData[1]);
					int gc = Integer.parseInt(messageData[2]);

					Game.getGame().getClientFunctions().setTeamDPoints(t0p);
					Game.getGame().getClientFunctions().setTeamAPoints(t1p);
					Game.getGame().getClientFunctions().setGameClock(gc);
				}
			}


			if (message.trim().substring(0, 2).equals(OPCode.OP_REMOVEABILITY)) {
				String[] abilityRemoveDataSet = message.substring(2).trim().split("]");

				for (int i = 0; i < abilityRemoveDataSet.length; i++) {

					int agid = Integer.parseInt(abilityRemoveDataSet[i]);

					try {
						Game.getGame().getClientFunctions().getCommandHandler().getCommandQueue().put(new RemoveAbilityCommand(agid));
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}

			if (message.trim().substring(0, 2).equals(OPCode.OP_REMOVEOBJECTIVE)) {
				int ogid = Integer.parseInt(messageData[0]);

				if (Game.getGame().getObjectiveObject(ogid) != null) {
					if (Game.getGame().getObjectives()
							.contains(Game.getGame().getObjectiveObject(ogid))) {
						Game.getGame().getObjectiveObject(ogid).removeObjectiveClient();
						// System.out.println("contained");
					} else {
						// System.out.println("doesnt even exist");
					}
				}

			}

			if (message.trim().substring(0, 2).equals(OPCode.OP_GOTALLABILITIES)) {
				// System.out.println("got all abilites");

			}

			if (message.trim().substring(0, 2).equals(OPCode.OP_ADDABILITYWITHTIMER)) {

				// System.out.println("got on client: " + message);
//				if (messageData.length == 8) {
//					int puid = Integer.parseInt(messageData[0]);
//					int abilityID = Integer.parseInt(messageData[1]);
//					int gameID = Integer.parseInt(messageData[2]);
//					float x = Float.parseFloat(messageData[3]);
//					float y = Float.parseFloat(messageData[4]);
//					float dx = Float.parseFloat(messageData[5]);
//					float dy = Float.parseFloat(messageData[6]);
//					float destroyTimer = Float.parseFloat(messageData[7]);
//
//					Entity abilityOwner = GameHolder.getGame().getPlayerObject(puid);
//					Ability newAbility = GameHolder.getGame().getAbilityByID(abilityID, gameID, x, y, dx, dy,
//							abilityOwner);
//					Ability a = GameHolder.getGame().getAbilityObject(gameID);
//
//					if (newAbility != null) {
//						if (!GameHolder.getGame().getAbilities().contains(a)) {
//							newAbility.setDestroyTimer(destroyTimer);
//							GameHolder.getGame().addAbility(newAbility);
//						} else {
//							if (a.getAbilityID() != abilityID) {
//								a.removeThis();
//								newAbility.setDestroyTimer(destroyTimer);
//								GameHolder.getGame().addAbility(newAbility);
//							}
//						}
//					}
//				}
			}

			if (message.trim().substring(0, 2).equals(OPCode.OP_NOTIFICATIONADD)) {
				if (messageData.length == 4) {
					int id = Integer.parseInt(messageData[0]);
					int type = Integer.parseInt(messageData[1]);
					int killerID = Integer.parseInt(messageData[2]);
					int killedID = Integer.parseInt(messageData[3]);

					Entity killer = Game.getGame().getPlayerObject(killerID);
					Entity killed = Game.getGame().getPlayerObject(killedID);

					if (killer != null && killed != null) {
						if (!Game.getGame().getHUDDisplay().getNotificationLog().getMessageList()
								.contains(Game.getGame().getHUDDisplay().getNotificationLog()
										.getNotification(id))) {
							Game.getGame().getHUDDisplay().getNotificationLog().getMessageList()
									.add(new MessageBox(id, killer, killed, type));
						}
					}
				}
			}

		}
	}
	
	public void sendReliableData(String stringData){
		String packetString = OPCode.OP_RELIABLEDATA;
		packetString += stringData += "~" + sequenceNumber;
		
		System.out.println("CLIENT : sending reliable data " + stringData + "  -  " + sequenceNumber);
		
		try {
			Game.getGame().getClientFunctions().getCommandHandler().getReliablePacketQueue().put(new ReliablePacket(sequenceNumber, stringData));
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		
		sequenceNumber++;
		
		byte[] data = packetString.getBytes();
		
		DatagramPacket packet = new DatagramPacket(data, data.length, ipAddress, serverPort);
		
		try {
			if (socket != null) socket.send(packet);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void resendReliableData(String stringData, int sequenceNumber){
		String packetString = OPCode.OP_RELIABLEDATA;
		packetString += stringData;

		System.out.println("CLIENT : resending reliable data " + stringData + "  -  " + sequenceNumber);
		
		byte[] data = packetString.getBytes();
		
		DatagramPacket packet = new DatagramPacket(data, data.length, ipAddress, serverPort);
		
		try {
			if (socket != null) socket.send(packet);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void sendData(String stringData) {

		byte[] data = stringData.getBytes();

		DatagramPacket packet = new DatagramPacket(data, data.length, ipAddress, serverPort);

		try {
			if (socket != null) {
				socket.send(packet);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void sendDataBlast(String stringData, int blastTimes) {

		byte[] data = stringData.getBytes();

		DatagramPacket packet = new DatagramPacket(data, data.length, ipAddress, serverPort);

		try {
			for (int i = 0; i < blastTimes; i++) {
				if (socket != null) {
					socket.send(packet);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean isRunning() {
		return isRunning;
	}

	public void setRunning(boolean isRunning) {
		this.isRunning = isRunning;
	}

	public DatagramSocket getSocket() {
		return socket;
	}

	public void setSocket(DatagramSocket socket) {
		this.socket = socket;
	}

}
