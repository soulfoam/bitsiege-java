package soulfoam.arena.entities.challengers.animation;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;

import soulfoam.arena.entities.Entity;
import soulfoam.arena.main.resources.Res;

public class KnightAnimation extends AnimationHandler {

	public KnightAnimation(Entity player) {
		this.player = player;
		initAnims();
	}

	public void initAnims() {
		int animSpeed = 100;
		int idleSpeed = 150;
		idleUp = new Animation(player.getSkin().getKnightSkin().getIdleUp(), idleSpeed);
		idleLeft = new Animation(player.getSkin().getKnightSkin().getIdleLeft(), idleSpeed);
		idleDown = new Animation(player.getSkin().getKnightSkin().getIdleDown(), idleSpeed);
		idleRight = new Animation(player.getSkin().getKnightSkin().getIdleRight(), idleSpeed);
		walkUp = new Animation(player.getSkin().getKnightSkin().getWalkUp(), animSpeed);
		walkLeft = new Animation(player.getSkin().getKnightSkin().getWalkLeft(), animSpeed);
		walkDown = new Animation(player.getSkin().getKnightSkin().getWalkDown(), animSpeed);
		walkRight = new Animation(player.getSkin().getKnightSkin().getWalkRight(), animSpeed);
		spin = new Animation();
		spin.addFrame(player.getSkin().getKnightSkin().getWalkUp()[0], animSpeed);
		spin.addFrame(player.getSkin().getKnightSkin().getWalkLeft()[0], animSpeed);
		spin.addFrame(player.getSkin().getKnightSkin().getWalkDown()[0], animSpeed);
		spin.addFrame(player.getSkin().getKnightSkin().getWalkRight()[0], animSpeed);
		respawn = new Animation(Res.GENERAL_RESOURCE.RESPAWN_ANIMATION, 50);

	}

	public Image getPortrait() {
		return player.getSkin().getKnightSkin().getPortrait();
	}


	public Image getDefaultPortrait() {
		return Res.KNIGHT_RESOURCE.PORTRAITS[0];
	}

}
