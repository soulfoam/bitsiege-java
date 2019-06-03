package soulfoam.bitsiegemainserver.main.party;

public class PartyInvite {
	
	private int senderID;
	private int receiverID;
	private int partyGroupID;
	
	public PartyInvite(int partyGroupID, int senderID, int receiverID){
		setPartyGroupID(partyGroupID);
		setSender(senderID);
		setReceiver(receiverID);
	}
	
	public int getSenderID() {
		return senderID;
	}
	
	public void setSender(int senderID) {
		this.senderID = senderID;
	}

	public int getReceiverID() {
		return receiverID;
	}

	public void setReceiver(int receiverID) {
		this.receiverID = receiverID;
	}

	public int getPartyGroupID() {
		return partyGroupID;
	}

	public void setPartyGroupID(int partyGroupID) {
		this.partyGroupID = partyGroupID;
	}

}
