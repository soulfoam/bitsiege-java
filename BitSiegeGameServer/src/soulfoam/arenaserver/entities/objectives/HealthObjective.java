package soulfoam.arenaserver.entities.objectives;

import java.util.ArrayList;

import org.newdawn.slick.geom.Ellipse;

import soulfoam.arenaserver.entities.Entity;
import soulfoam.arenaserver.main.game.Game;
import soulfoam.arenaserver.main.resources.Res;
import soulfoam.arenaserver.world.Tile;
import soulfoam.arenashared.main.entityinfo.EntityInfo;
import soulfoam.arenashared.main.objectiveinfo.ObjectiveInfo;

public class HealthObjective extends Objective {
	
	public boolean moveUp;
	public float moveUpTime = 0.3f * 1000;
	public float moveUpTimer = moveUpTime;
	public float y1, y2;

	public float respawnTime;
	public float respawnTimer;
	
	public float playerOrbDestroyTimer = 2 * 1000;
	
	public boolean needsRespawned;

	public ArrayList<Entity> playersHealed = new ArrayList<Entity>();
	
	public int healAmount = 50;
	
	public HealthObjective(float x, float y, byte objectiveTeam){
		
		this.objectiveTeam = objectiveTeam;
		
		this.x = x;
		this.y = y;
		this.width = Tile.TILE_SIZE * 2;
		this.height = Tile.TILE_SIZE * 2;
		this.hitBoxWidth = 4;
		this.hitBoxHeight = 4;
		
		this.health = 600;
		this.baseHealth = 600;

		this.y1 = y;
		this.y2 = y - 5;
		
		if (objectiveTeam == ObjectiveInfo.HEALTHPICKUP_OBJECTIVE){
			this.healAmount = 50;
			this.respawnTime = 40 * 1000;
			this.respawnTimer = respawnTime;
			this.width = Tile.TILE_SIZE * 2;
			this.height = Tile.TILE_SIZE * 2;
		}
		if (objectiveTeam == ObjectiveInfo.HEALTHPICKUPSMALL_OBJECTIVE){
			this.healAmount = 25;
			this.respawnTime = 40 * 1000;
			this.respawnTimer = respawnTime;
		}
		if (objectiveTeam == ObjectiveInfo.HEALTHPICKUPPLAYER_OBJECTIVE){
			this.healAmount = 75;
			this.respawnTime = 20 * 1000;
			this.respawnTimer = respawnTime;
		}

	}

	public void update(int delta){

		if (isBeingHeld){
			if (objectiveTeam == ObjectiveInfo.HEALTHPICKUPPLAYER_OBJECTIVE){
				if (playerOrbDestroyTimer > 0){
					playerOrbDestroyTimer -= delta;
				}
				if (playerOrbDestroyTimer <= 0){
					needsRemoved = true;
				}
			}
			else{
				if (respawnTimer > 0){
					respawnTimer-= delta;
				}
				if (respawnTimer <= 0){
					isBeingHeld = false;
					objectiveHolderID = -1;
					respawnTimer = respawnTime;
				}
			}
		}
		
		if (objectiveTeam == ObjectiveInfo.HEALTHPICKUPPLAYER_OBJECTIVE){
			if (respawnTimer > 0){
				respawnTimer-= delta;
			}
			if (respawnTimer <= 0){
				needsRemoved = true;
			}
		}
		
		if (playersHealed.size() > 1){
			Entity pl = playersHealed.get(0);
			playersHealed.clear();
			playersHealed.add(pl);
		}
		
		if (playersHealed.size() == 1){
			isBeingHeld = true;
			objectiveHolderID = playersHealed.get(0).getPlayerID();
			playersHealed.get(0).takeHeal(healAmount + Res.percentOf((int)playersHealed.get(0).getBaseHealth(), 15));
			playersHealed.clear();
		}
		
		handleMovement(delta);
		handleCollision();
		
	}
	
	public void handleCollision(){
		
		if (!isBeingHeld){
			for (Entity p: Game.getGame().getPlayers()) {
				if (p.getBounds().intersects(getBounds()) && p.getChallengerType() != EntityInfo.SPECTATE_PLAYER && !p.isDead() && !playersHealed.contains(p)){
					playersHealed.add(p);
				}
			}
		}
		
	}
	
	public void handleMovement(int delta){
		
		if (moveUpTimer > 0){
			moveUpTimer-= delta;
		}
		
		if (moveUpTimer <= 0){
			moveUp = !moveUp;
			moveUpTimer = moveUpTime;
		}
		
		if (moveUp){
			y = y1;
		}
		else{
			y = y2;
		}

	}

	public Ellipse getBounds() {
		return new Ellipse(x, y, hitBoxWidth, hitBoxHeight);
	}

}
