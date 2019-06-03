package soulfoam.arenaserver.main.command.server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import soulfoam.arenaserver.entities.Entity;
import soulfoam.arenaserver.main.ServerFunctions;
import soulfoam.arenaserver.main.command.Command;
import soulfoam.arenaserver.main.game.Game;
import soulfoam.arenaserver.net.server.AckPacket;
import soulfoam.arenaserver.net.server.GameServer;
import soulfoam.arenaserver.net.server.ReliablePacket;


public class ServerCommandHandler {

	private ArrayList<Command> commandLogs = new ArrayList<>();
	private ArrayList<Command> commands = new ArrayList<>();
	private ArrayList<Command> commandsToAdd = new ArrayList<>();
	
	private ArrayList<ReliablePacket> reliablePacketsHistory = new ArrayList<ReliablePacket>();
	private ArrayList<Integer> reliablePacketsToRemove = new ArrayList<Integer>();
	
	public float logClearTime = 2000;
	public float logClearTimer = logClearTime;
	
	private float resendTime = 250;
	private float resendTimer = resendTime;
	
	private BlockingQueue<Command> commandQueue = new ArrayBlockingQueue<Command>(960);
	private BlockingQueue<ReliablePacket> reliablePacketsQueue = new ArrayBlockingQueue<ReliablePacket>(960);
	
	public void update(int delta){
		
		commands.addAll(commandsToAdd);
		commandsToAdd.clear();
		
		commandQueue.drainTo(commands);
		reliablePacketsQueue.drainTo(reliablePacketsHistory);
		
		for (ReliablePacket rp : reliablePacketsHistory){
			if (rp instanceof AckPacket){
				reliablePacketsToRemove.add(rp.getSequenceNumber());
			}
		}
		
		for (Iterator<ReliablePacket> it = reliablePacketsHistory.iterator(); it.hasNext(); ) {
			ReliablePacket rp = it.next();
			if (reliablePacketsToRemove.contains(rp.getSequenceNumber())){
				it.remove();
			}
		}
		
		reliablePacketsToRemove.clear();
		
		if (resendTimer > 0) {
			resendTimer -= delta;
		}
		if (resendTimer <= 0) {
			for (ReliablePacket rp : reliablePacketsHistory){
				Game.getGame().getServer().resendReliableData(rp.getData(), rp.getSequenceNumber(), rp.getIP(), rp.getPort());
			}
			resendTimer = resendTime;
		}
		
		for (Command c : commands){
			if (c.execute(delta)){
				commandLogs.add(c);
			}
		}
			
		commands.clear();
		
		if (logClearTimer > 0){
			logClearTimer -= delta;
		}
		if (logClearTimer <= 0){
			commandLogs.clear();
			reliablePacketsHistory.clear();
			logClearTimer = logClearTime;
		}
		
	}
	
	public void addCommand(Command command){
		commandsToAdd.add(command);
	}
	
	public BlockingQueue<ReliablePacket> getReliablePacketsQueue() {
		return reliablePacketsQueue;
	}


	public ArrayList<Command> getCommandLogs() {
		return commandLogs;
	}


	public ArrayList<Command> getCommands() {
		return commands;
	}


	public BlockingQueue<Command> getCommandQueue() {
		return commandQueue;
	}

}
