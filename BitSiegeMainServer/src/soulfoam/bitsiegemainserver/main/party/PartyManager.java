package soulfoam.bitsiegemainserver.main.party;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import soulfoam.arenashared.main.lobbyopcode.LobbyOPCode;
import soulfoam.arenashared.main.lobbyopcode.LobbyReturnCode;
import soulfoam.bitsiegemainserver.main.ClientThread;
import soulfoam.bitsiegemainserver.main.NetworkManager;


public class PartyManager{
	
	private ArrayList<PartyInvite> partyInvites = new ArrayList<PartyInvite>();
	private ArrayList<PartyGroup> partyGroups = new ArrayList<PartyGroup>();
	
	public void removeAllInvitesFromParty(int partyID, int accountID){
		ArrayList<PartyInvite> invitesToRemove = new ArrayList<PartyInvite>();
		for (PartyInvite pi: partyInvites) {
			if (pi.getPartyGroupID() == partyID && pi.getSenderID() == accountID){
				ClientThread ct = NetworkManager.getManager().getActiveAccountManager().getClientFromAccountID(pi.getReceiverID());
				if (ct != null){
					ct.getOutput().println(LobbyOPCode.OP_DELETEREQUEST);
					ct.getOutput().println(pi.getPartyGroupID() + "," + accountID);
				}
				invitesToRemove.add(pi);
			}
		}
		
		partyInvites.removeAll(invitesToRemove);
	}
	
	public PartyInvite getInviteFromSenderID(int senderID){
		for (PartyInvite pi: partyInvites) {
			if (pi.getSenderID() == senderID){
				return pi;
			}
		}
		
		return null;
	}

	public PartyInvite getInviteFromSenderIDAndReceiverID(int senderID, int receiverID){
		for (PartyInvite pi: partyInvites) {

			if (pi.getSenderID() == senderID && pi.getReceiverID() == receiverID){
				return pi;
			}
		}
		
		return null;
	}
	
	public PartyInvite getInviteFromAllInfo(int partyID, int senderID, int receiverID){
		for (PartyInvite pi: partyInvites) {
			if (pi.getPartyGroupID() == partyID && pi.getSenderID() == senderID && pi.getReceiverID() == receiverID){
				return pi;
			}
		}
		
		return null;
	}
	
	public int getPartyIDForUser(ClientThread clientThread){
		for (PartyGroup pg: partyGroups) {
			for (ClientThread ct : pg.getPartyMembers()){
				if (ct.getUserAccount().getID() == clientThread.getUserAccount().getID()){
					return pg.getID();
				}
			}
		}
		
		return -1;
	}
	
	public PartyGroup getPartyGroupByID(int partyID){

		for (PartyGroup pg: partyGroups) {
			
			if (pg.getID() == partyID){
				return pg;
			}
		}
		return null;
	}
	
	public PartyGroup getPartyGroupByUserID(ClientThread clientThread){
		for (PartyGroup pg: partyGroups) {
		
			for (ClientThread ct : pg.getPartyMembers()){
				if (ct.getUserAccount().getID() == clientThread.getUserAccount().getID()){
					return pg;
				}
			}
		}
		
		return null;
	}
	
	public ArrayList<PartyGroup> getPartyGroups() {
		return partyGroups;
	}

	public ArrayList<PartyInvite> getPartyInvites() {
		return partyInvites;
	}

}
