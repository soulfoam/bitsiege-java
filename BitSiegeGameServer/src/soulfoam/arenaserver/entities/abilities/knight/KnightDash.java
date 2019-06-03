package soulfoam.arenaserver.entities.abilities.knight;

import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

import soulfoam.arenaserver.entities.Entity;
import soulfoam.arenaserver.entities.abilities.Ability;
import soulfoam.arenaserver.main.command.server.MoveEntityCommand;
import soulfoam.arenaserver.main.game.Game;
import soulfoam.arenashared.main.abilityinfo.AbilityInfo;
import soulfoam.arenashared.main.abilityinfo.knight.KnightDashInfo;
import soulfoam.arenashared.main.entityinfo.EntityInfo;


public class KnightDash extends Ability{
	
	public boolean canMoveForward;
	
	private float dashTimer = 0;
	private float dashTime = 1;
	
	public KnightDash(int gameID, float dx, float dy, Entity player){
		
		this.abilityID = AbilityInfo.KNIGHTDASH;
		this.tag = AbilityInfo.DASHSTOPONHIT;
		
		this.gameID = gameID;
		this.dx = dx;
		this.dy = dy;

		this.player = player;
		
		
		this.coolDown = KnightDashInfo.COOLDOWN; 
		this.damage = KnightDashInfo.DAMAGE; 
		this.moveSpeed = KnightDashInfo.MOVE_SPEED;
		this.destroyTimer = KnightDashInfo.DESTROY_TIMER;
		this.stunDuration = KnightDashInfo.STUN_DURATION;

		this.width = KnightDashInfo.WIDTH;
		this.height = KnightDashInfo.HEIGHT;
		this.hitBoxWidth = KnightDashInfo.HITBOX_WIDTH;
		this.hitBoxHeight = KnightDashInfo.HITBOX_HEIGHT;
		

		this.x = player.getX();
		this.y = player.getY();
		

	}
	
	public void update(int delta) {


		x = player.getX();
		y = player.getY();
		
		
		handleCollision();	
		handleTimers(delta);
		
		if (destroyTimer > 0) {
			destroyTimer -= delta;
		}
		
		if (destroyTimer <= 0)
		{
			removeThis(false);
		}
		
	}

	public void handleTimers(int delta){
		
		if (dashTimer > 0) {
			dashTimer-= delta;
			canMoveForward = false;
		}
		
		if (dashTimer <= 0)
		{
			canMoveForward = true;
			dashTimer = dashTime;
		}
		
		if (canMoveForward){
			Game.getGame().getServerFunctions().getCommandHandler().addCommand(new MoveEntityCommand(player.getPlayerID(), dx, dy, moveSpeed));
		}
		
	}
	
	public void handleCollision(){

		for (Entity p: Game.getGame().getPlayers()){

			if (getBounds().intersects(p.getBounds()) || getBounds().contains(p.getBounds()))
			{
				if (!collision.getPlayersHit().contains(p) && !p.isDead() && p.getTeam() != player.getTeam()){
					collision.getPlayersHit().add(p);
				}
			}
			
		}

		if (collision.getPlayersHit().size() > 0){
			for (Entity p: collision.getPlayersHit()){
				if (!collision.getPlayersCycled().contains(p) && collision.getPlayersCycled().size() < playersToDamage){
					p.takeDamage(damage, player);
					p.takeStun(stunDuration, player);
					p.takeKnockback(dx, dy, KnightDashInfo.KNOCKBACK_DURATION, moveSpeed, true, player);
					collision.getPlayersCycled().add(p);
				}
			}
			removeThis(true);
		}
			
	}

	public Shape getBounds() {
		return new Rectangle(x, y, hitBoxWidth, hitBoxHeight);
	}


}
