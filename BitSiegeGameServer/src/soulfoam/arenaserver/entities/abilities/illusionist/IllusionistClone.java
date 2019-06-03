package soulfoam.arenaserver.entities.abilities.illusionist;

import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

import soulfoam.arenaserver.entities.Entity;
import soulfoam.arenaserver.entities.abilities.Ability;
import soulfoam.arenaserver.entities.challengers.Illusionist;
import soulfoam.arenaserver.entities.challengers.bots.IllusionistBot;
import soulfoam.arenaserver.main.game.Game;
import soulfoam.arenaserver.world.MapLayer;
import soulfoam.arenaserver.world.Tile;
import soulfoam.arenashared.main.abilityinfo.AbilityInfo;
import soulfoam.arenashared.main.abilityinfo.illusionist.IllusionistCloneInfo;
import soulfoam.arenashared.main.entityinfo.EntityInfo;

public class IllusionistClone extends Ability{
	
	private boolean isAttacking;
	
	private float attackTimer = 0;
	private float attackTime = IllusionistCloneInfo.ATTACK_TIMER;
	
	private boolean setLastClone;
	
	private float swapTimer;

	public IllusionistClone(int gameID, float x, float y, boolean ult, Entity player){
		
		this.abilityID = AbilityInfo.ILLUSIONISTCLONE;
		this.tag = AbilityInfo.PROJECTILE;
		
		this.gameID = gameID;
		this.player = player;
		

		this.damage = IllusionistCloneInfo.DAMAGE;
		this.moveSpeed = IllusionistCloneInfo.MOVE_SPEED;
		this.destroyTimer = IllusionistCloneInfo.DESTROY_TIMER;
		this.stunDuration = IllusionistCloneInfo.STUN_DURATION;
	
		this.width = IllusionistCloneInfo.WIDTH;
		this.height = IllusionistCloneInfo.HEIGHT;
		this.hitBoxWidth = IllusionistCloneInfo.HITBOX_WIDTH;
		this.hitBoxHeight = IllusionistCloneInfo.HITBOX_HEIGHT;
		
		if (!ult){
			this.x = player.getX();
			this.y = player.getY() + 4;
		}
		else{
			this.x = x;
			this.y = y;
		}
	}

	public void update(int delta){
		
		setLastClone();
		
		if (isAttacking){
			move(delta);
		}
		
		if (attackTimer > 0){
			attackTimer -= delta;
			this.tag = AbilityInfo.PROJECTILE;
		}
		
		if (attackTimer <= 0){
			isAttacking = false;
			this.tag = AbilityInfo.NORMAL;
		}
		
		if (getSwapTimer() > 0) {
			setSwapTimer(getSwapTimer() - delta);
		}
		
		if (destroyTimer > 0) {
			destroyTimer -= delta;
		}
		
		if (destroyTimer <= 0){
			removeThis(true);
		}
		
		handleCollision();
		
		
	}
	
	public void handleCollision(){
		
		if (isAttacking){
	
			for (Entity p: Game.getGame().getPlayers()){

				if (getBounds().intersects(p.getBoundsCenterBody()) || getBounds().contains(p.getBoundsCenterBody())){
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
						collision.getPlayersCycled().add(p);
					}
				}
				removeThis(true);
			}
			
		}
		
	}
	
	public void setLastClone(){
		if (!setLastClone){
			if (player.isBot()){
				IllusionistBot b = (IllusionistBot) player;
				b.passive.cloneList.add(this);
			}
			else{
				Illusionist p = (Illusionist) player;
				p.passive.cloneList.add(this);
			}
			setLastClone = true;
		}
	}
	
	public void attack(float dx, float dy){
		this.dx = dx;
		this.dy = dy;
		isAttacking = true;
		attackTimer = attackTime;
		setSwapTimer(0);

	}

	public Shape getBounds() {
		return new Rectangle(x, y, hitBoxWidth, hitBoxHeight);
	}
	
	public boolean move(int delta) {
		
		float xm = dx * moveSpeed * delta;
		float ym = dy * moveSpeed * delta;
		
		float nY = y - 4;
		float nH = 16;
        
		for (float xa = x + xm; xa < x + xm + hitBoxWidth; xa++) {
			for (float ya = nY + ym; ya < nY + ym + nH; ya++) {
				if (xa / Tile.TILE_SIZE <= 0 || xa / Tile.TILE_SIZE >= Game.getGame().getWorld().getMap().getMapWidth() || ya / Tile.TILE_SIZE <= 0 || ya / Tile.TILE_SIZE >= Game.getGame().getWorld().getMap().getMapHeight()){
					return false;
				}
				else if (Game.getGame().getWorld().getTile(xa, ya).isBlocked()) {
					return false;
				}
				
			}
		}
		
        x += xm;
        y += ym;
        
        return true;
	}

	public float getSwapTimer() {
		return swapTimer;
	}

	public void setSwapTimer(float swapTimer) {
		this.swapTimer = swapTimer;
	}
	
}
