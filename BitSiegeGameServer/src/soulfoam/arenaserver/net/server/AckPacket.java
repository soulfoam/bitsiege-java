package soulfoam.arenaserver.net.server;

import java.net.InetAddress;

public class AckPacket extends ReliablePacket{

	public AckPacket(InetAddress ip, int port, int sequenceNumber) {
		super(ip, port, sequenceNumber, "");
	}

}
