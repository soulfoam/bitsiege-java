package soulfoam.bitsiegemainserver.main.logs;

import java.util.ArrayList;

import soulfoam.bitsiegemainserver.main.matchmaking.MatchMaker;

public class LogManager{

	public void addLog(int type, String text, String ipAddress){
		Log log = new Log(type, text, ipAddress);
		log.execute();
	}
	
	public void addLog(int type, String text){
		Log log = new Log(type, text);
		log.execute();
	}

}
