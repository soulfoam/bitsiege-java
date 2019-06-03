package soulfoam.arena.entities.challengers.animation;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;

import soulfoam.arena.entities.Entity;
import soulfoam.arena.main.resources.Res;

public class VoidLordAnimation extends AnimationHandler {

	public VoidLordAnimation(Entity player) {
		this.player = player;
		initAnims();
	}

	public void initAnims() {
		int animSpeed = 100;
		int idleSpeed = 200;
		idleUp = new Animation(player.getSkin().getVoidLordSkin().getIdleUp(), idleSpeed);
		idleLeft = new Animation(player.getSkin().getVoidLordSkin().getIdleLeft(), idleSpeed);
		idleDown = new Animation(player.getSkin().getVoidLordSkin().getIdleDown(), idleSpeed);
		idleRight = new Animation(player.getSkin().getVoidLordSkin().getIdleRight(), idleSpeed);
		walkUp = new Animation(player.getSkin().getVoidLordSkin().getWalkUp(), animSpeed);
		walkLeft = new Animation(player.getSkin().getVoidLordSkin().getWalkLeft(), animSpeed);
		walkDown = new Animation(player.getSkin().getVoidLordSkin().getWalkDown(), animSpeed);
		walkRight = new Animation(player.getSkin().getVoidLordSkin().getWalkRight(), animSpeed);
		spin = new Animation();
		spin.addFrame(player.getSkin().getVoidLordSkin().getWalkUp()[0], animSpeed);
		spin.addFrame(player.getSkin().getVoidLordSkin().getWalkLeft()[0], animSpeed);
		spin.addFrame(player.getSkin().getVoidLordSkin().getWalkDown()[0], animSpeed);
		spin.addFrame(player.getSkin().getVoidLordSkin().getWalkRight()[0], animSpeed);
		respawn = new Animation(Res.GENERAL_RESOURCE.RESPAWN_ANIMATION, 50);

	}

	public Image getPortrait() {
		return player.getSkin().getVoidLordSkin().getPortrait();
	}

	@Override
	public Image getDefaultPortrait() {
		return Res.VOIDLORD_RESOURCE.PORTRAITS[0];
	}

}
