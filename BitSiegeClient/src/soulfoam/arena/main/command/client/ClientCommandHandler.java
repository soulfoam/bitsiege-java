package soulfoam.arena.main.command.client;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import soulfoam.arena.main.command.Command;
import soulfoam.arena.main.command.client.local.ClientLocalInputCommand;
import soulfoam.arena.main.game.Game;
import soulfoam.arena.net.client.AckPacket;
import soulfoam.arena.net.client.ReliablePacket;

public class ClientCommandHandler {

	private ArrayList<Command> commandLogs = new ArrayList<Command>();
	private ArrayList<Command> commands = new ArrayList<Command>();
	private ArrayList<Command> commandsToAdd = new ArrayList<>();
	
	private ArrayList<ReliablePacket> reliablePacketsHistory = new ArrayList<ReliablePacket>();
	private ArrayList<Integer> reliablePacketsToRemove = new ArrayList<Integer>();
	
	private BlockingQueue<Command> commandQueue = new ArrayBlockingQueue<Command>(120);
	private BlockingQueue<ReliablePacket> reliablePacketQueue = new ArrayBlockingQueue<ReliablePacket>(120);
	
	private float resendTime = 250;
	private float resendTimer = resendTime;
	
	private float clearTime = 2000;
	private float clearTimer = clearTime;

	
	public void update(int delta) {
		
		commands.addAll(commandsToAdd);
		commandsToAdd.clear();
		
		commandQueue.drainTo(commands);
		reliablePacketQueue.drainTo(reliablePacketsHistory);
		
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
				Game.getGame().getClient().resendReliableData(rp.getData(), rp.getSequenceNumber());
			}
			resendTimer = resendTime;
		}
		
		
		for (Command c : commands) {
			if (c.execute(delta)) {
				commandLogs.add(c);
			}
		}
		
		commands.clear();
		
		if (clearTimer > 0) {
			clearTimer -= delta;
		}
		if (clearTimer <= 0) {
			commandLogs.clear();
			reliablePacketsHistory.clear();
			clearTimer = clearTime;
		}
		
		
	}
	
	public void addCommand(Command command){
		commandsToAdd.add(command);
	}
	
	public void clearLocalMoveCommands() {
		for (Command c : commandLogs) {
			if (c != null && c.getCommandID() == Command.COMMAND_LOCAL_MOVE_ENTITY) {
				commandLogs.remove(c);
			}
		}
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

	public BlockingQueue<ReliablePacket> getReliablePacketQueue() {
		return reliablePacketQueue;
	}


}
