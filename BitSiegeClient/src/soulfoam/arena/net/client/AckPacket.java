package soulfoam.arena.net.client;

public class AckPacket extends ReliablePacket {

	public AckPacket(int sequenceNumber) {
		super(sequenceNumber, "");
	}
	
	
}
