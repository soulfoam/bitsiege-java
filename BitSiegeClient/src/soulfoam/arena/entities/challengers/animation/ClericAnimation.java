package soulfoam.arena.entities.challengers.animation;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;

import soulfoam.arena.entities.Entity;
import soulfoam.arena.main.resources.Res;

public class ClericAnimation extends AnimationHandler {

	public ClericAnimation(Entity player) {
		this.player = player;
		initAnims();
	}

	public void initAnims() {
		int animSpeed = 100;
		int idleSpeed = 2000;
		idleUp = new Animation(player.getSkin().getClericSkin().getIdleUp(), idleSpeed);
		idleLeft = new Animation(player.getSkin().getClericSkin().getIdleLeft(), idleSpeed);
		idleDown = new Animation(player.getSkin().getClericSkin().getIdleDown(), idleSpeed);
		idleRight = new Animation(player.getSkin().getClericSkin().getIdleRight(), idleSpeed);
		walkUp = new Animation(player.getSkin().getClericSkin().getWalkUp(), animSpeed);
		walkLeft = new Animation(player.getSkin().getClericSkin().getWalkLeft(), animSpeed);
		walkDown = new Animation(player.getSkin().getClericSkin().getWalkDown(), animSpeed);
		walkRight = new Animation(player.getSkin().getClericSkin().getWalkRight(), animSpeed);
		spin = new Animation();
		spin.addFrame(player.getSkin().getClericSkin().getWalkUp()[0], animSpeed);
		spin.addFrame(player.getSkin().getClericSkin().getWalkLeft()[0], animSpeed);
		spin.addFrame(player.getSkin().getClericSkin().getWalkDown()[0], animSpeed);
		spin.addFrame(player.getSkin().getClericSkin().getWalkRight()[0], animSpeed);
		respawn = new Animation(Res.GENERAL_RESOURCE.RESPAWN_ANIMATION, 50);

	}

	public Image getPortrait() {
		return player.getSkin().getClericSkin().getPortrait();
	}

	public Image getDefaultPortrait() {
		return Res.CLERIC_RESOURCE.PORTRAITS[0];
	}

}
