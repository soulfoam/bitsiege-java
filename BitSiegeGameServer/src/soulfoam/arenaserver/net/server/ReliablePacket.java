package soulfoam.arenaserver.net.server;

import java.net.InetAddress;

public class ReliablePacket {
	
	private int sequenceNumber;
	private String data = "";
	private InetAddress ip;
	private int port;
	
	public ReliablePacket(InetAddress ip, int port, int sequenceNumber, String data) {
		this.ip = ip;
		this.port = port;
		this.sequenceNumber = sequenceNumber;
		this.data = data;
	}

	public int getSequenceNumber() {
		return sequenceNumber;
	}

	public String getData() {
		return data;
	}

	public InetAddress getIP() {
		return ip;
	}

	public int getPort() {
		return port;
	}

}
