package soulfoam.bitsiegemainserver.main.store;

import soulfoam.arenashared.main.lobbyopcode.LobbyOPCode;
import soulfoam.arenashared.main.lobbyopcode.LobbyReturnCode;
import soulfoam.arenashared.main.lobbyutil.LobbyUtil;
import soulfoam.bitsiegemainserver.main.ClientThread;
import soulfoam.bitsiegemainserver.main.NetworkManager;
import soulfoam.bitsiegemainserver.main.command.Command;
import soulfoam.bitsiegemainserver.main.party.PartyGroup;

public class StoreCommand extends Command{
	
	private ClientThread clientThread;
	private String storeString = "";
	private String[] storeInfo;
	
	public StoreCommand(ClientThread clientThread, String lobbyString, String[] lobbyInfo){
		this.clientThread = clientThread;
		this.storeString = lobbyString;
		this.storeInfo = lobbyInfo;
	}
	
	public boolean execute() {
		
		if (storeInfo[0].equalsIgnoreCase("C")){ 
			int challengerID = LobbyUtil.parseInt(storeInfo[1]);
			int currency = LobbyUtil.parseInt(storeInfo[2]);
			int result = NetworkManager.getManager().getStoreManager().buyChallenger(clientThread, challengerID, currency);
			clientThread.getOutput().println(LobbyOPCode.OP_STORE);
			clientThread.getOutput().println(result);
			if (result == LobbyReturnCode.STORE_BOUGHT_SUCCESS_CHALLENGER){
				clientThread.getOutput().println(challengerID + "," + currency);
			}
		}
		else if (storeInfo[0].equalsIgnoreCase("S")){ 
			int challengerID = LobbyUtil.parseInt(storeInfo[1]);
			int skinID = LobbyUtil.parseInt(storeInfo[2]);
			int result = NetworkManager.getManager().getStoreManager().buySkin(clientThread, challengerID, skinID);
			clientThread.getOutput().println(LobbyOPCode.OP_STORE);
			clientThread.getOutput().println(result);
			if (result == LobbyReturnCode.STORE_BOUGHT_SUCCESS_SKIN){
				clientThread.getOutput().println(challengerID + "," + skinID);
			}
		}
		else if (storeInfo[0].equalsIgnoreCase("U")){ 
			int underglowID = LobbyUtil.parseInt(storeInfo[1]);
			int result = NetworkManager.getManager().getStoreManager().buyUnderglow(clientThread, underglowID);
			clientThread.getOutput().println(LobbyOPCode.OP_STORE);
			clientThread.getOutput().println(result);
			if (result == LobbyReturnCode.STORE_BOUGHT_SUCCESS_UNDERGLOW){
				clientThread.getOutput().println(underglowID);
			}
		}
		else if (storeInfo[0].equalsIgnoreCase("A")){ 
			int avatarID = LobbyUtil.parseInt(storeInfo[1]);
			int slotID = LobbyUtil.parseInt(storeInfo[2]);
			int currency = LobbyUtil.parseInt(storeInfo[3]);
			int result = NetworkManager.getManager().getStoreManager().buyAvatar(clientThread, avatarID, slotID, currency);
			clientThread.getOutput().println(LobbyOPCode.OP_STORE);
			clientThread.getOutput().println(result);
			if (result == LobbyReturnCode.STORE_BOUGHT_SUCCESS_AVATAR_BG 
					|| result == LobbyReturnCode.STORE_BOUGHT_SUCCESS_AVATAR_BORDER 
					|| result == LobbyReturnCode.STORE_BOUGHT_SUCCESS_AVATAR_ICON){
				clientThread.getOutput().println(avatarID + "," + currency);
			}
		}
		
		return true;
	
	}

	public void undo() {
		
	}


}
