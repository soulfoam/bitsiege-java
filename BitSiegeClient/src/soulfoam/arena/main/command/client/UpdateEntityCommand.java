package soulfoam.arena.main.command.client;

import soulfoam.arena.entities.Entity;
import soulfoam.arena.main.command.Command;
import soulfoam.arena.main.game.Game;

public class UpdateEntityCommand extends Command {

	private float oldHealth;
	private float oldShield;
	private boolean oldIsStunned;
	private boolean oldIsBlind;
	private boolean oldIsInvisible;
	private boolean oldIsConfused;
	private boolean oldIsDead;

	private float health;
	private float shield;
	private boolean isStunned;
	private boolean isBlind;
	private boolean isInvisible;
	private boolean isConfused;
	private boolean isDead;

	public UpdateEntityCommand(int playerID, float health, float shield, boolean isStunned, boolean isBlind, boolean isInvisible, boolean isConfused, boolean isDead) {
		super(Command.COMMAND_PACKET_UPDATE_ENTITY, playerID);

		this.health = health;
		this.shield = shield;
		this.isStunned = isStunned;
		this.isBlind = isBlind;
		this.isInvisible = isInvisible;
		this.isConfused = isConfused;
		this.isDead = isDead;
	}

	public boolean execute(int delta) {
		
		player = Game.getGame().getPlayerObject(playerID);
		
		if (player == null){
			Game.getGame().getClientFunctions().syncGame();
			return true;
		}

		setOldValues();
		
		storedDelta = delta;


		if (health < player.getHealth()) {
			player.takeDamageClient(null, player.getHealth() - health);
		}
		if (health > player.getHealth()) {
			player.takeHealClient(null, health - player.getHealth());
		}
		player.setHealth(health);
		player.setShield(shield);
		player.setStunned(isStunned);
		player.setBlind(isBlind);
		player.setInvisible(isInvisible);
		player.setConfused(isConfused);
		player.setDead(isDead);
		

		return true;
	}

	private void setOldValues(){
		oldHealth = player.getHealth();
		oldShield = player.getShield();
		oldIsStunned = player.isStunned();
		oldIsBlind = player.isBlind();
		oldIsInvisible = player.isInvisible();
		oldIsConfused = player.isConfused();
		oldIsDead = player.isDead();
	}
	
	public void undo() {
		if (player != null) {
			player.setHealth(oldHealth);
			player.setShield(oldShield);
			player.setStunned(oldIsStunned);
			player.setBlind(oldIsBlind);
			player.setInvisible(oldIsInvisible);
			player.setConfused(oldIsConfused);
			player.setDead(oldIsDead);
		}
	}

}
