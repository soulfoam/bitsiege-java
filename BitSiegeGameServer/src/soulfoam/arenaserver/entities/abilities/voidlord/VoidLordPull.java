package soulfoam.arenaserver.entities.abilities.voidlord;

import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

import soulfoam.arenaserver.entities.Entity;
import soulfoam.arenaserver.entities.abilities.Ability;
import soulfoam.arenaserver.main.command.server.SpawnAbilityCommand;
import soulfoam.arenaserver.main.game.Game;
import soulfoam.arenashared.main.abilityinfo.AbilityInfo;
import soulfoam.arenashared.main.abilityinfo.voidlord.VoidLordPullInfo;


public class VoidLordPull extends Ability{

	public boolean setInvis;
	
	public VoidLordPull(int gameID, float dx, float dy, Entity player){

		this.abilityID = AbilityInfo.VOIDLORDPULL;
		this.tag = AbilityInfo.PROJECTILE;
		
		this.gameID = gameID;
		this.dx = dx;
		this.dy = dy;

		this.player = player;
		
		
		this.coolDown = VoidLordPullInfo.COOLDOWN;
		this.moveSpeed = VoidLordPullInfo.MOVE_SPEED;
		this.destroyTimer = VoidLordPullInfo.DESTROY_TIMER;

		this.width = VoidLordPullInfo.WIDTH;
		this.height = VoidLordPullInfo.HEIGHT;
		
		this.hitBoxWidth = VoidLordPullInfo.HITBOX_WIDTH;
		this.hitBoxHeight = VoidLordPullInfo.HITBOX_HEIGHT;

		this.x = player.getX() - 4;
		this.y = player.getY() + 2;
		
	}

	public void update(int delta) {

		handleMovement(delta);
		handleCollision();	
		
		if (destroyTimer > 0) {
			destroyTimer -= delta;
		}
		
		if (destroyTimer <= 0)
		{
			removeThis(false);
		}

	}

	public void handleMovement(int delta){
		move(delta, false);
	}
	
	public void handleCollision(){

		for (Entity p: Game.getGame().getPlayers()){
			if (getBounds().intersects(p.getBounds()) || getBounds().contains(p.getBounds()))
			{
				if (!collision.getPlayersHit().contains(p) && !p.isDead() && p.getPlayerID() != player.getPlayerID()){
					collision.getPlayersHit().add(p);
				}
			}
		}
		if (collision.getPlayersHit().size() > 0){
			for (Entity p: collision.getPlayersHit()){
				if (!collision.getPlayersCycled().contains(p) && collision.getPlayersCycled().size() < playersToDamage){
					pullAction(p);
					collision.getPlayersCycled().add(p);
				}
			}
			removeThis(true);
		}

	}

	public void pullAction(Entity targetPlayer){
	
		VoidLordPullAction vpa = new VoidLordPullAction(Game.getGame().getIdPool().getAvailableAbilityID(), player, targetPlayer);
		Game.getGame().getServerFunctions().getCommandHandler().addCommand(new SpawnAbilityCommand(vpa, false));
		
	}
	

	public Shape getBounds() {
		
		return new Rectangle(x, y, hitBoxWidth, hitBoxHeight);

	}
	
}