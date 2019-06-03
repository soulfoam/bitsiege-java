package soulfoam.arena.entities.challengers.animation;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import soulfoam.arena.entities.Entity;
import soulfoam.arena.main.game.Game;
import soulfoam.arena.main.resources.Res;

public class ArcherAnimation extends AnimationHandler {

	public ArcherAnimation(Entity player) {
		this.player = player;
		initAnims();
	}

	public void initAnims() {
		int animSpeed = 100;
		int idleSpeed = 150;
		
		idleUp = new Animation(player.getSkin().getArcherSkin().getIdleUp(), idleSpeed);
		idleLeft = new Animation(player.getSkin().getArcherSkin().getIdleLeft(), idleSpeed);
		idleDown = new Animation(player.getSkin().getArcherSkin().getIdleDown(), idleSpeed);
		idleRight = new Animation(player.getSkin().getArcherSkin().getIdleRight(), idleSpeed);
		
		walkUp = new Animation(player.getSkin().getArcherSkin().getWalkUp(), animSpeed);
		walkLeft = new Animation(player.getSkin().getArcherSkin().getWalkLeft(), animSpeed);
		walkDown = new Animation(player.getSkin().getArcherSkin().getWalkDown(), animSpeed);
		walkRight = new Animation(player.getSkin().getArcherSkin().getWalkRight(), animSpeed);
		
		spin = new Animation();
		spin.addFrame(player.getSkin().getArcherSkin().getWalkUp()[0], 50);
		spin.addFrame(player.getSkin().getArcherSkin().getWalkLeft()[0], 50);
		spin.addFrame(player.getSkin().getArcherSkin().getWalkDown()[0], 50);
		spin.addFrame(player.getSkin().getArcherSkin().getWalkRight()[0], 50);
		respawn = new Animation(Res.GENERAL_RESOURCE.RESPAWN_ANIMATION, 50);

	}

	public Image getPortrait() {
		return player.getSkin().getArcherSkin().getPortrait();
	}

	public Image getDefaultPortrait() {
		return Res.ARCHER_RESOURCE.PORTRAITS[0];
	}

}
