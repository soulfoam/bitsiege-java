package soulfoam.arena.entities.abilities.misc;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

import soulfoam.arena.entities.Entity;
import soulfoam.arena.entities.abilities.Ability;
import soulfoam.arenashared.main.abilityinfo.AbilityInfo;

public class Knockback extends Ability {

	public Knockback(int gameID, float knockBackTime, float knockBackSpeed, byte currentDirection, Entity player) {

		abilityID = AbilityInfo.GENERALKNOCKBACK;
		this.gameID = gameID;
		width = 0;
		height = 0;
		hitBoxWidth = width;
		hitBoxHeight = height;
		x = 0;
		y = 0;
		moveSpeed = 0;
		damage = 0;
		coolDown = 0;

		destroyTimer = knockBackTime * 1000;
		this.player = player;

	}

	public void update(int delta) {

	}

	public void render(Graphics g) {

		// animation.drawAnimation(g, x, y, width, height);
	}

	public Shape getBounds() {
		return new Rectangle(x, y, hitBoxWidth, hitBoxHeight);
	}

	public void displayAbilityToolTip(Graphics g) {

	}

	public void playSoundEffect() {

	}

}
