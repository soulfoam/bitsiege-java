package soulfoam.arena.entities.challengers.animation;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import soulfoam.arena.entities.Entity;
import soulfoam.arena.main.game.Game;
import soulfoam.arenashared.main.entityinfo.EntityInfo;


public abstract class AnimationHandler {

	protected Entity player;

	public abstract void initAnims();

	public abstract Image getPortrait();
	public abstract Image getDefaultPortrait();
	
	protected Animation walkUp = new Animation();
	protected Animation walkLeft = new Animation();
	protected Animation walkDown = new Animation();
	protected Animation walkRight = new Animation();
	protected Animation idleUp = new Animation();
	protected Animation idleLeft = new Animation();
	protected Animation idleDown = new Animation();
	protected Animation idleRight = new Animation();
	protected Animation spin = new Animation();
	protected Animation respawn = new Animation();

	
	public void update(int delta) {
		if (player != null) {
			if (player.getCurrentAction() == EntityInfo.IDLE) {
				if (player.getCurrentDirection() != EntityInfo.DIR_UP) {
					idleUp.restart();
				}
				if (player.getCurrentDirection() != EntityInfo.DIR_LEFT) {
					idleLeft.restart();
				}
				if (player.getCurrentDirection() != EntityInfo.DIR_DOWN) {
					idleDown.restart();
				}
				if (player.getCurrentDirection() != EntityInfo.DIR_RIGHT) {
					idleRight.restart();
				}
			} 
			else {
				idleUp.restart();
				idleLeft.restart();
				idleDown.restart();
				idleRight.restart();
			}

			if (player.getCurrentAction() == EntityInfo.WALKING) {
				if (player.getCurrentDirection() != EntityInfo.DIR_UP) {
					walkUp.restart();
				}
				if (player.getCurrentDirection() != EntityInfo.DIR_LEFT) {
					walkLeft.restart();
				}
				if (player.getCurrentDirection() != EntityInfo.DIR_DOWN) {
					walkDown.restart();
				}
				if (player.getCurrentDirection() != EntityInfo.DIR_RIGHT) {
					walkRight.restart();
				}
			} 
			else {
				walkUp.restart();
				walkLeft.restart();
				walkDown.restart();
				walkRight.restart();
			}
		}


		calculateAnimationSpeeds();
	}

	public void render(Graphics g) {
		Color playerColor = new Color(255, 255, 255);
		
		if (!player.isDead()) {
			//player.getUnderglow().render(g);
			if (!player.isStunned()) {
				
				if (player.isSlowed()) {
					playerColor = new Color(51, 182, 255, 255);
					Game.getGame().getParticleSystem().addSlowParticles(player.getX(), player.getY(), 1, 2);
				} 
				if (!player.canBeDamaged()) {
					playerColor = new Color(150, 150, 0);
				} 
				

				if (!player.isInvisible()) {
					getCurrentActionAnimation().draw(player.getRenderX(), player.getRenderY(), player.getWidth(), player.getHeight(), playerColor);
				} 
				else { 
					playerColor = new Color(255, 255, 255, 100);
					if (Game.getGame().getPlayer().getTeam() == player.getTeam()) {
						getCurrentActionAnimation().draw(player.getRenderX(), player.getRenderY(), player.getWidth(), player.getHeight(), playerColor);
					}
				}
				

			} 
			else {
				getWalkAnimation().getImage(0).drawFlash(player.getRenderX(), player.getRenderY(), player.getWidth(), player.getHeight(), Color.white);
			}

			if (player.isSlowed()) {
				Game.getGame().getParticleSystem().addSlowParticles(player.getX(), player.getY(), 1, 2);
			} 
//				getRespawnAnimation().draw(player.getRenderX(), player.getRenderY(), player.getWidth(),
//						player.getHeight());
			
			player.renderShield(g);
		}
	}
	
	private void calculateAnimationSpeeds(){
		
		int walkTime = (int)Math.floor(115 + 0.469f * (64 - (player.getMoveSpeed()*1000)));

		walkUp.setAllFrameDurations(walkTime);
		walkLeft.setAllFrameDurations(walkTime);
		walkDown.setAllFrameDurations(walkTime);
		walkRight.setAllFrameDurations(walkTime);
		
	}

	public Animation getSpinAnimation() {
		return spin;
	}

	public Animation getRespawnAnimation() {
		return respawn;
	}

	public Animation getCurrentActionAnimation() {

		if (player.getCurrentAction() == EntityInfo.IDLE) {
			return getIdleAnimation();
		}
		if (player.getCurrentAction() == EntityInfo.WALKING) {
			return getWalkAnimation();
		}
		if (player.getCurrentAction() == EntityInfo.SPINNING) {
			return getSpinAnimation();
		}

		return getIdleAnimation();

	}

	public Animation getIdleAnimation() {

		if (player.getCurrentDirection() == EntityInfo.DIR_UP) {
			return idleUp;
		}
		if (player.getCurrentDirection() == EntityInfo.DIR_LEFT) {
			return idleLeft;
		}
		if (player.getCurrentDirection() == EntityInfo.DIR_DOWN) {
			return idleDown;
		}
		if (player.getCurrentDirection() == EntityInfo.DIR_RIGHT) {
			return idleRight;
		}

		return idleDown;
	}

	public Animation getWalkAnimation() {

		if (player.getCurrentDirection() == EntityInfo.DIR_UP) {
			return walkUp;
		}
		if (player.getCurrentDirection() == EntityInfo.DIR_LEFT) {
			return walkLeft;
		}
		if (player.getCurrentDirection() == EntityInfo.DIR_DOWN) {
			return walkDown;
		}
		if (player.getCurrentDirection() == EntityInfo.DIR_RIGHT) {
			return walkRight;
		}

		return walkDown;
	}

	public Entity getPlayer() {
		return player;
	}

	public Animation getWalkUp() {
		return walkUp;
	}

	public Animation getWalkLeft() {
		return walkLeft;
	}

	public Animation getWalkDown() {
		return walkDown;
	}

	public Animation getWalkRight() {
		return walkRight;
	}

	public Animation getIdleUp() {
		return idleUp;
	}

	public Animation getIdleLeft() {
		return idleLeft;
	}

	public Animation getIdleDown() {
		return idleDown;
	}

	public Animation getIdleRight() {
		return idleRight;
	}

	public Animation getSpin() {
		return spin;
	}

	public Animation getRespawn() {
		return respawn;
	}
}
