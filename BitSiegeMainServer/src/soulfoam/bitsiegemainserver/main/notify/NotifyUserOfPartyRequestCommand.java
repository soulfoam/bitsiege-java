package soulfoam.bitsiegemainserver.main.notify;

import soulfoam.arenashared.main.ids.AvatarLibrary;
import soulfoam.arenashared.main.lobbyopcode.LobbyOPCode;
import soulfoam.arenashared.main.lobbyopcode.LobbyReturnCode;
import soulfoam.arenashared.main.lobbyutil.LobbyUtil;
import soulfoam.bitsiegemainserver.main.ClientThread;
import soulfoam.bitsiegemainserver.main.GameServerThread;
import soulfoam.bitsiegemainserver.main.NetworkManager;
import soulfoam.bitsiegemainserver.main.command.Command;
import soulfoam.bitsiegemainserver.main.logs.LogType;
import soulfoam.bitsiegemainserver.main.matchmaking.MatchMaker;
import soulfoam.bitsiegemainserver.main.party.PartyGroup;
import soulfoam.bitsiegemainserver.main.party.PartyInvite;

public class NotifyUserOfPartyRequestCommand extends Command{
	
	private PartyInvite invite;
	
	public NotifyUserOfPartyRequestCommand(PartyInvite invite){
		this.invite = invite;
	}
	
	public boolean execute() {
		
		if (invite == null){
			return false;
		}
		
		ClientThread senderThread = NetworkManager.getManager().getActiveAccountManager().getClientFromAccountID(invite.getSenderID());
		ClientThread receiverThread = NetworkManager.getManager().getActiveAccountManager().getClientFromAccountID(invite.getReceiverID());
		
		if (senderThread != null && receiverThread != null){
			
			String[] nameColor = senderThread.getUserAccount().getNameColor().split(",");
			
			receiverThread.getOutput().println(LobbyOPCode.OP_LIVEUPDATE_PARTYINVITE);
			receiverThread.getOutput().println(
					invite.getPartyGroupID() + "," + senderThread.getUserAccount().getID() + "," + senderThread.getUserAccount().getUsername()
					+ "," + senderThread.getUserAccount().getAvatarIDs()[AvatarLibrary.SLOT_BACKGROUND]
					+ "," + senderThread.getUserAccount().getAvatarIDs()[AvatarLibrary.SLOT_BORDER]
					+ "," + senderThread.getUserAccount().getAvatarIDs()[AvatarLibrary.SLOT_ICON]
					+ "," + nameColor[0] + "," + nameColor[1] + "," + nameColor[2]
					);
		}
		
		return true;
	
	}


	public void undo() {
		
	}


}