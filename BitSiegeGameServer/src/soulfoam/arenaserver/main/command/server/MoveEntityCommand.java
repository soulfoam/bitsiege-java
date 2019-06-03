package soulfoam.arenaserver.main.command.server;

import soulfoam.arenaserver.entities.Entity;
import soulfoam.arenaserver.main.command.Command;
import soulfoam.arenaserver.main.game.Game;
import soulfoam.arenaserver.main.resources.Res;
import soulfoam.arenashared.main.entityinfo.EntityInfo;
import soulfoam.arenashared.main.opcode.OPCode;

public class MoveEntityCommand extends Command{

	private float oldX, oldY;
	private byte oldVelocity;
	private float speed;

	public byte moveID;
	
	private float moveX;
	private float moveY;
	private byte currentVelocity;
	
	private float teleportX;
	private float teleportY;

	private float dx;
	private float dy;
	
	private boolean knockBack;
	private boolean stopOnWall;
	
	public MoveEntityCommand(int playerID){
		super(Command.COMMAND_MOVE_ENTITY, playerID);
		this.moveID = 0;
	}
	
	public MoveEntityCommand(int playerID, float dx, float dy, float speed){
		super(Command.COMMAND_MOVE_ENTITY, playerID);
		this.dx = dx;
		this.dy = dy;

		this.speed = speed;
		this.moveID = 1;
	}
	
	public MoveEntityCommand(int playerID, float dx, float dy, float speed, boolean knockBack, boolean stopOnWall){
		super(Command.COMMAND_MOVE_ENTITY, playerID);
		this.dx = dx;
		this.dy = dy;
		
		this.speed = speed;
		this.moveID = 1;
		this.knockBack = knockBack;
		this.stopOnWall = stopOnWall;
	}
	
	public MoveEntityCommand(int playerID, float x, float y){
		super(Command.COMMAND_MOVE_ENTITY, playerID);
		this.moveID = 2;
		this.teleportX = x;
		this.teleportY = y;
	}

	public boolean execute(int delta) {
		
		player = Game.getGame().getPlayerObject(playerID);
		
		if (player == null){
			return true;
		}
		
		if (moveID == 0){
			speed = player.getMoveSpeed();
			currentVelocity = player.getCurrentVelocity();
		}
		
		setOldValues();
		
		this.storedDelta = delta;

		if (moveID == 0){
			if (player.isStunned()){
				return true;
			}
		}
		if (moveID != 2){
			if (!knockBack){
				if (player.isConfused()){
					player.setCurrentVelocity(getConfusedVelocity());
					player.setCurrentDirection(getConfusedDirection());
				}
				else{
					player.setCurrentVelocity(currentVelocity);
				}
			}
			else{
				player.setCurrentVelocity(currentVelocity);
			}
			
			if (moveID == 1){
				if (stopOnWall){
					player.moveVelocityStopOnWall(dx, dy, speed, delta);
				}
				else{
					player.moveVelocity(dx, dy, speed, delta);
				}
			}
			else{
				this.moveX = getMoveX();
				this.moveY = getMoveY();
				player.move(moveX, moveY, delta);
			}
			
			
		}
		else{
			player.setX(teleportX);
			player.setY(teleportY);
		}
		
		if (!player.isBot()){
			Game.getGame().getServer().sendData(OPCode.OP_PLAYERPOSITIONUPDATE + Res.roundForNetwork(player.getX()) + "," + Res.roundForNetwork(player.getY()), 
					player.getIpAddress(), player.getPort());
		}
		
		return true;
	
	}

	public void setOldValues(){
		this.oldX = player.getX();
		this.oldY = player.getY();
		this.oldVelocity = player.getCurrentVelocity();
	}

	public void undo() {

		player.setX(oldX);
		player.setY(oldY);
		player.setCurrentVelocity(oldVelocity);
		
	}

	public float getMoveX(){
		
		float theX = 0;
		

		if (player.getCurrentVelocity() == EntityInfo.DIR_LEFT) {
			theX += -speed;
		}
		if (player.getCurrentVelocity() == EntityInfo.DIR_RIGHT) {
			theX += speed;
		}
		if (player.getCurrentVelocity() == EntityInfo.DIR_UP_LEFT) {
			theX += -speed;
		}
		if (player.getCurrentVelocity() == EntityInfo.DIR_UP_RIGHT) {
			theX += speed;
		}
		if (player.getCurrentVelocity() == EntityInfo.DIR_DOWN_LEFT) {
			theX += -speed;
		}
		if (player.getCurrentVelocity() == EntityInfo.DIR_DOWN_RIGHT) {
			theX += speed;
		}
		
		return theX;
	}
	
	public float getMoveY(){
		
		float theY = 0;

		if (player.getCurrentVelocity() == EntityInfo.DIR_UP) {
			theY += -speed;
		}
		if (player.getCurrentVelocity() == EntityInfo.DIR_DOWN) {
			theY += speed;
		}
		if (player.getCurrentVelocity() == EntityInfo.DIR_UP_LEFT) {
			theY += -speed;
		}
		if (player.getCurrentVelocity() == EntityInfo.DIR_UP_RIGHT) {
			theY += -speed;
		}
		if (player.getCurrentVelocity() == EntityInfo.DIR_DOWN_LEFT) {
			theY += speed;
		}
		if (player.getCurrentVelocity() == EntityInfo.DIR_DOWN_RIGHT) {
			theY += speed;
		}
		
		return theY;
	}

	public byte getConfusedVelocity(){
		if (currentVelocity == EntityInfo.DIR_UP) {
			return EntityInfo.DIR_DOWN_LEFT;
		}
		if (currentVelocity == EntityInfo.DIR_LEFT) {
			return EntityInfo.DIR_DOWN_RIGHT;
		}
		if (currentVelocity == EntityInfo.DIR_DOWN) {
			return EntityInfo.DIR_UP_RIGHT;
		}
		if (currentVelocity == EntityInfo.DIR_RIGHT) {
			return EntityInfo.DIR_UP;
		}
		if (currentVelocity == EntityInfo.DIR_UP_LEFT) {
			return EntityInfo.DIR_RIGHT;
		}
		if (currentVelocity == EntityInfo.DIR_UP_RIGHT) {
			return EntityInfo.DIR_DOWN;
		}
		if (currentVelocity == EntityInfo.DIR_DOWN_LEFT) {
			return EntityInfo.DIR_UP_LEFT;
		}
		if (currentVelocity == EntityInfo.DIR_DOWN_RIGHT) {
			return EntityInfo.DIR_LEFT;
		}
		
		return EntityInfo.DIR_DOWN_LEFT;
	}
	
	public byte getConfusedDirection(){
		if (player.getCurrentVelocity() == EntityInfo.DIR_UP) {
			return EntityInfo.DIR_UP;
		}
		if (player.getCurrentVelocity() == EntityInfo.DIR_LEFT) {
			return EntityInfo.DIR_LEFT;
		}
		if (player.getCurrentVelocity() == EntityInfo.DIR_DOWN) {
			return EntityInfo.DIR_DOWN;
		}
		if (player.getCurrentVelocity() == EntityInfo.DIR_RIGHT) {
			return EntityInfo.DIR_RIGHT;
		}
		if (player.getCurrentVelocity() == EntityInfo.DIR_UP_LEFT || player.getCurrentVelocity() == EntityInfo.DIR_UP_RIGHT) {
			return EntityInfo.DIR_UP;
		}
		if (player.getCurrentVelocity() == EntityInfo.DIR_DOWN_LEFT || player.getCurrentVelocity() == EntityInfo.DIR_DOWN_RIGHT) {
			return EntityInfo.DIR_DOWN;
		}
		
		return EntityInfo.DIR_DOWN;
	}
}
