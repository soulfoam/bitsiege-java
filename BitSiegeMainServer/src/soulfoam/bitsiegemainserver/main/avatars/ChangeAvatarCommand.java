package soulfoam.bitsiegemainserver.main.avatars;

import soulfoam.arenashared.main.ids.AvatarLibrary;
import soulfoam.arenashared.main.lobbyopcode.LobbyOPCode;
import soulfoam.arenashared.main.lobbyopcode.LobbyReturnCode;
import soulfoam.bitsiegemainserver.main.ClientThread;
import soulfoam.bitsiegemainserver.main.GameServerThread;
import soulfoam.bitsiegemainserver.main.NetworkManager;
import soulfoam.bitsiegemainserver.main.command.Command;
import soulfoam.bitsiegemainserver.main.logs.LogType;
import soulfoam.bitsiegemainserver.main.matchmaking.MatchMaker;
import soulfoam.bitsiegemainserver.main.notify.NotifyFriendsOfAvatarChangeCommand;
import soulfoam.bitsiegemainserver.main.notify.NotifyPartyOfAvatarChangeCommand;

public class ChangeAvatarCommand extends Command{
	
	private ClientThread clientThread;
	private int bgID;
	private int borderID;
	private int iconID;
	
	public ChangeAvatarCommand(ClientThread clientThread, int bgID, int borderID, int iconID){
		this.clientThread = clientThread;
		this.bgID = bgID;
		this.borderID = borderID;
		this.iconID = iconID;
	}
	
	public boolean execute() {
		
		int bgResult = NetworkManager.getManager().getAvatarManager().changeAvatar(bgID, AvatarLibrary.SLOT_BACKGROUND, clientThread.getUserAccount().getID());
		int borderResult = NetworkManager.getManager().getAvatarManager().changeAvatar(borderID, AvatarLibrary.SLOT_BORDER, clientThread.getUserAccount().getID());
		int iconResult = NetworkManager.getManager().getAvatarManager().changeAvatar(iconID, AvatarLibrary.SLOT_ICON, clientThread.getUserAccount().getID());
		
		clientThread.getOutput().println(LobbyOPCode.OP_CHANGEAVATAR);
		clientThread.getOutput().println(bgResult);
		
		if (bgResult == LobbyReturnCode.AVATAR_CHANGE_SUCCESS){
			clientThread.getUserAccount().setAvatarIDs(bgID, borderID, iconID);
			clientThread.getOutput().println(bgID + "," + borderID + "," + iconID);
			
			try {
				NetworkManager.getManager().getCommandHandler().getCommandQueue().put(new NotifyFriendsOfAvatarChangeCommand(clientThread, 
						clientThread.getUserAccount().getAvatarIDs()[AvatarLibrary.SLOT_BACKGROUND],
						clientThread.getUserAccount().getAvatarIDs()[AvatarLibrary.SLOT_BORDER],
						clientThread.getUserAccount().getAvatarIDs()[AvatarLibrary.SLOT_ICON]));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			try {
				NetworkManager.getManager().getCommandHandler().getCommandQueue().put(new NotifyPartyOfAvatarChangeCommand(clientThread, 
						clientThread.getUserAccount().getAvatarIDs()[AvatarLibrary.SLOT_BACKGROUND],
						clientThread.getUserAccount().getAvatarIDs()[AvatarLibrary.SLOT_BORDER],
						clientThread.getUserAccount().getAvatarIDs()[AvatarLibrary.SLOT_ICON]));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		return true;
	}

	public void undo() {
		
	}


}
