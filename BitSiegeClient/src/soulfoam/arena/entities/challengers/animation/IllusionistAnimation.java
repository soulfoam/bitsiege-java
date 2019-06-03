package soulfoam.arena.entities.challengers.animation;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;

import soulfoam.arena.entities.Entity;
import soulfoam.arena.main.resources.Res;

public class IllusionistAnimation extends AnimationHandler {

	public IllusionistAnimation(Entity player) {
		this.player = player;
		initAnims();
	}

	public void initAnims() {
		int animSpeed = 100;
		int idleSpeed = 150;
		idleUp = new Animation(player.getSkin().getIllusionistSkin().getIdleUp(), idleSpeed);
		idleLeft = new Animation(player.getSkin().getIllusionistSkin().getIdleLeft(), idleSpeed);
		idleDown = new Animation(player.getSkin().getIllusionistSkin().getIdleDown(), idleSpeed);
		idleRight = new Animation(player.getSkin().getIllusionistSkin().getIdleRight(), idleSpeed);
		
		walkUp = new Animation(player.getSkin().getIllusionistSkin().getWalkUp(), animSpeed);
		walkLeft = new Animation(player.getSkin().getIllusionistSkin().getWalkLeft(), animSpeed);
		walkDown = new Animation(player.getSkin().getIllusionistSkin().getWalkDown(), animSpeed);
		walkRight = new Animation(player.getSkin().getIllusionistSkin().getWalkRight(), animSpeed);
		
		spin = new Animation();
		spin.addFrame(player.getSkin().getIllusionistSkin().getWalkUp()[0], animSpeed);
		spin.addFrame(player.getSkin().getIllusionistSkin().getWalkLeft()[0], animSpeed);
		spin.addFrame(player.getSkin().getIllusionistSkin().getWalkDown()[0], animSpeed);
		spin.addFrame(player.getSkin().getIllusionistSkin().getWalkRight()[0], animSpeed);
		respawn = new Animation(Res.GENERAL_RESOURCE.RESPAWN_ANIMATION, 50);

	}

	public Image getPortrait() {
		return player.getSkin().getIllusionistSkin().getPortrait();
	}
	
	public Image getDefaultPortrait() {
		return Res.ILLUSIONIST_RESOURCE.PORTRAITS[0];
	}
	
}
