package soulfoam.arena.entities.abilities.knight;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

import soulfoam.arena.entities.Entity;
import soulfoam.arena.entities.abilities.Ability;
import soulfoam.arena.main.resources.Res;
import soulfoam.arenashared.main.abilityinfo.AbilityInfo;
import soulfoam.arenashared.main.abilityinfo.knight.KnightMeleeAttackInfo;

public class KnightMeleeAttack extends Ability {

	public KnightMeleeAttack(int gameID, float x, float y, float dx, float dy, Entity player) {

		abilityID = AbilityInfo.KNIGHTMELEEATTACK;
		tag = AbilityInfo.MELEE;

		this.gameID = gameID;
		this.x = x;
		this.y = y;
		this.dx = dx;
		this.dy = dy;

		this.player = player;

		coolDown = KnightMeleeAttackInfo.COOLDOWN;
		damage = KnightMeleeAttackInfo.DAMAGE;
		destroyTimer = KnightMeleeAttackInfo.DESTROY_TIMER;

		width = KnightMeleeAttackInfo.WIDTH;
		height = KnightMeleeAttackInfo.HEIGHT;
		hitBoxWidth = KnightMeleeAttackInfo.HITBOX_WIDTH;
		hitBoxHeight = KnightMeleeAttackInfo.HITBOX_HEIGHT;

		animSpeed = 50;
		soundEffect = Res.KNIGHT_RESOURCE.SFX_KNIGHT[0];

		this.x = player.getX() - 4 + dx * 8;
		this.y = player.getY() + dy * 8;
		animation = new Animation(player.getSkin().getKnightSkin().getMelee(), animSpeed);

	}

	public void update(int delta) {

		if (destroyTimer > 0) {
			destroyTimer -= delta;
		}

		if (destroyTimer <= 0) {
			removeThis();
		}

		x = player.getX() - 4 + dx * 8;
		y = player.getY() + dy * 8;

	}

	public void render(Graphics g) {
		animation.setRotation(rotation);
		animation.draw(getRenderX(), getRenderY(), width, height);
		animation.setRotation(rotation);
	}

	public float getRenderX() {
		return Res.roundForRendering(x - 4);
	}

	public float getRenderY() {
		return Res.roundForRendering(y - 3);
	}

	public Shape getBounds() {
		return new Rectangle(x, y, hitBoxWidth, hitBoxHeight);
	}
}
