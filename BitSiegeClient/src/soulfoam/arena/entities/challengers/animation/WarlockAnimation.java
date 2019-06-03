package soulfoam.arena.entities.challengers.animation;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;

import soulfoam.arena.entities.Entity;
import soulfoam.arena.main.resources.Res;

public class WarlockAnimation extends AnimationHandler {

	public WarlockAnimation(Entity player) {
		this.player = player;
		initAnims();
	}

	public void initAnims() {
		int animSpeed = 100;
		int idleSpeed = 150;
		idleUp = new Animation(player.getSkin().getWarlockSkin().getIdleUp(), idleSpeed);
		idleLeft = new Animation(player.getSkin().getWarlockSkin().getIdleLeft(), idleSpeed);
		idleDown = new Animation(player.getSkin().getWarlockSkin().getIdleDown(), idleSpeed);
		idleRight = new Animation(player.getSkin().getWarlockSkin().getIdleRight(), idleSpeed);
		walkUp = new Animation(player.getSkin().getWarlockSkin().getWalkUp(), animSpeed);
		walkLeft = new Animation(player.getSkin().getWarlockSkin().getWalkLeft(), animSpeed);
		walkDown = new Animation(player.getSkin().getWarlockSkin().getWalkDown(), animSpeed);
		walkRight = new Animation(player.getSkin().getWarlockSkin().getWalkRight(), animSpeed);
		spin = new Animation();
		spin.addFrame(player.getSkin().getWarlockSkin().getWalkUp()[0], animSpeed);
		spin.addFrame(player.getSkin().getWarlockSkin().getWalkLeft()[0], animSpeed);
		spin.addFrame(player.getSkin().getWarlockSkin().getWalkDown()[0], animSpeed);
		spin.addFrame(player.getSkin().getWarlockSkin().getWalkRight()[0], animSpeed);
		respawn = new Animation(Res.GENERAL_RESOURCE.RESPAWN_ANIMATION, 50);

	}

	public Image getPortrait() {
		return player.getSkin().getWarlockSkin().getPortrait();
	}

	public Image getDefaultPortrait() {
		return Res.WARLOCK_RESOURCE.PORTRAITS[0];
	}
}
