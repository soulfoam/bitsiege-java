package soulfoam.arena.entities.challengers.animation;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;

import soulfoam.arena.entities.Entity;
import soulfoam.arena.main.resources.Res;

public class WaterQueenAnimation extends AnimationHandler {

	public WaterQueenAnimation(Entity player) {
		this.player = player;
		initAnims();
	}

	public void initAnims() {
		int animSpeed = 100;
		int idleSpeed = 2000;
		idleUp = new Animation(player.getSkin().getWaterQueenSkin().getIdleUp(), idleSpeed);
		idleLeft = new Animation(player.getSkin().getWaterQueenSkin().getIdleLeft(), idleSpeed);
		idleDown = new Animation(player.getSkin().getWaterQueenSkin().getIdleDown(), idleSpeed);
		idleRight = new Animation(player.getSkin().getWaterQueenSkin().getIdleRight(), idleSpeed);
		walkUp = new Animation(player.getSkin().getWaterQueenSkin().getWalkUp(), animSpeed);
		walkLeft = new Animation(player.getSkin().getWaterQueenSkin().getWalkLeft(), animSpeed);
		walkDown = new Animation(player.getSkin().getWaterQueenSkin().getWalkDown(), animSpeed);
		walkRight = new Animation(player.getSkin().getWaterQueenSkin().getWalkRight(), animSpeed);
		spin = new Animation();
		spin.addFrame(player.getSkin().getWaterQueenSkin().getWalkUp()[0], animSpeed);
		spin.addFrame(player.getSkin().getWaterQueenSkin().getWalkLeft()[0], animSpeed);
		spin.addFrame(player.getSkin().getWaterQueenSkin().getWalkDown()[0], animSpeed);
		spin.addFrame(player.getSkin().getWaterQueenSkin().getWalkRight()[0], animSpeed);
		respawn = new Animation(Res.GENERAL_RESOURCE.RESPAWN_ANIMATION, 50);

	}

	public Image getPortrait() {
		return player.getSkin().getWaterQueenSkin().getPortrait();
	}


	public Image getDefaultPortrait() {
		return Res.WATERQUEEN_RESOURCE.PORTRAITS[0];
	}

}
