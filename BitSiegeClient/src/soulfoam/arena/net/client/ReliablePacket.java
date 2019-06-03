package soulfoam.arena.net.client;

public class ReliablePacket {

	private int sequenceNumber;
	private String data = "";
	
	public ReliablePacket(int sequenceNumber, String data) {
		this.sequenceNumber = sequenceNumber;
		this.data = data;
	}

	public int getSequenceNumber() {
		return sequenceNumber;
	}

	public String getData() {
		return data;
	}


}
