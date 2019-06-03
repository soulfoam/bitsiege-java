package soulfoam.arena.entities.challengers.animation;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;

import soulfoam.arena.entities.Entity;
import soulfoam.arena.main.resources.Res;

public class ShamanAnimation extends AnimationHandler {

	public ShamanAnimation(Entity player) {
		this.player = player;
		initAnims();
	}

	public void initAnims() {
		int animSpeed = 100;
		int idleSpeed = 150;
		idleUp = new Animation(player.getSkin().getShamanSkin().getIdleUp(), idleSpeed);
		idleLeft = new Animation(player.getSkin().getShamanSkin().getIdleLeft(), idleSpeed);
		idleDown = new Animation(player.getSkin().getShamanSkin().getIdleDown(), idleSpeed);
		idleRight = new Animation(player.getSkin().getShamanSkin().getIdleRight(), idleSpeed);
		walkUp = new Animation(player.getSkin().getShamanSkin().getWalkUp(), animSpeed);
		walkLeft = new Animation(player.getSkin().getShamanSkin().getWalkLeft(), animSpeed);
		walkDown = new Animation(player.getSkin().getShamanSkin().getWalkDown(), animSpeed);
		walkRight = new Animation(player.getSkin().getShamanSkin().getWalkRight(), animSpeed);
		spin = new Animation();
		spin.addFrame(player.getSkin().getShamanSkin().getWalkUp()[0], animSpeed);
		spin.addFrame(player.getSkin().getShamanSkin().getWalkLeft()[0], animSpeed);
		spin.addFrame(player.getSkin().getShamanSkin().getWalkDown()[0], animSpeed);
		spin.addFrame(player.getSkin().getShamanSkin().getWalkRight()[0], animSpeed);
		respawn = new Animation(Res.GENERAL_RESOURCE.RESPAWN_ANIMATION, 50);

	}

	public Image getPortrait() {
		return player.getSkin().getShamanSkin().getPortrait();
	}

	public Image getDefaultPortrait() {
		return Res.SHAMAN_RESOURCE.PORTRAITS[0];
	}

}
