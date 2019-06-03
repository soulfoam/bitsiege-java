package soulfoam.bitsiegemainserver.main.chat;

import java.awt.image.RescaleOp;

import soulfoam.arenashared.main.lobbyopcode.LobbyOPCode;
import soulfoam.arenashared.main.lobbyutil.LobbyUtil;
import soulfoam.bitsiegemainserver.main.ClientThread;
import soulfoam.bitsiegemainserver.main.NetworkManager;
import soulfoam.bitsiegemainserver.main.accounts.BanCommand;
import soulfoam.bitsiegemainserver.main.command.Command;

public class SendGlobalChatCommand extends Command{
	
	private ClientThread clientThread;
	private String message = "";

	
	public SendGlobalChatCommand(ClientThread clientThread, String message){
		this.clientThread = clientThread;
		this.message = message;
	}
	
	public boolean execute() {
		
		if (message.startsWith("/")){
			if (handleAdminCommands()){
				return true;
			}
		}
		
		for (ClientThread ct : NetworkManager.getManager().getActiveAccountManager().getClients()){
			if (ct.getUserAccount().isLoggedIn()){
				ct.getOutput().println(LobbyOPCode.OP_GLOBALCHAT);
				ct.getOutput().println(clientThread.getUserAccount().getUsername() + ": " + message.trim());
				ct.getOutput().println(clientThread.getUserAccount().getNameColor());
			}
		}
		
		return true;
	
	}
	
	private boolean handleAdminCommands(){

		if (clientThread.getUserAccount().getID() == 19){
			String[] splitMessage = message.split("~");
			if (message.startsWith("/banaccount")){
				String username = splitMessage[1];
				int accountID = NetworkManager.getManager().getAccountManager().getAccountIDFromUsername(username);
				int hours = LobbyUtil.parseInt(splitMessage[2]);
				String reason = splitMessage[3];
				
				ClientThread ct = NetworkManager.getManager().getActiveAccountManager().getClientFromAccountID(accountID);
				
				try {
					if (ct != null){
						NetworkManager.getManager().getCommandHandler().getCommandQueue().put(new BanCommand(ct, hours, reason));
					}
					else{
						NetworkManager.getManager().getCommandHandler().getCommandQueue().put(new BanCommand(accountID, hours, reason));
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}	
			}
			if (message.startsWith("/banip")){
				String ipAddress = splitMessage[1];
				int hours = LobbyUtil.parseInt(splitMessage[2]);
				String reason = splitMessage[3];
				
				try {
					NetworkManager.getManager().getCommandHandler().getCommandQueue().put(new BanCommand(ipAddress, hours, reason));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}	
			}
			
			if (message.startsWith("/banipusername")){
				String username = splitMessage[1];
				int hours = LobbyUtil.parseInt(splitMessage[2]);
				String reason = splitMessage[3];
				
				ClientThread ct = NetworkManager.getManager().getActiveAccountManager().getClientFromUsername(username);
				
				if (ct != null){
					try {
						NetworkManager.getManager().getCommandHandler().getCommandQueue().put(new BanCommand(clientThread, hours, reason, true));
					} catch (InterruptedException e) {
						e.printStackTrace();
					}	
				}
			}
			return true;
		}
		
		return false;
	}

	public void undo() {
		
	}


}
