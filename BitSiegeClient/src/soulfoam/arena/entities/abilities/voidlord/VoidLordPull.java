package soulfoam.arena.entities.abilities.voidlord;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

import soulfoam.arena.entities.Entity;
import soulfoam.arena.entities.abilities.Ability;
import soulfoam.arena.main.resources.Res;
import soulfoam.arenashared.main.abilityinfo.AbilityInfo;
import soulfoam.arenashared.main.abilityinfo.voidlord.VoidLordPullInfo;

public class VoidLordPull extends Ability {

	public VoidLordPull(int gameID, float x, float y, float dx, float dy, Entity player) {

		abilityID = AbilityInfo.VOIDLORDPULL;
		tag = AbilityInfo.PROJECTILE;

		this.gameID = gameID;
		this.x = x;
		this.y = y;
		this.dx = dx;
		this.dy = dy;

		this.player = player;

		coolDown = VoidLordPullInfo.COOLDOWN;
		moveSpeed = VoidLordPullInfo.MOVE_SPEED;
		destroyTimer = VoidLordPullInfo.DESTROY_TIMER;

		width = VoidLordPullInfo.WIDTH;
		height = VoidLordPullInfo.HEIGHT;

		hitBoxWidth = VoidLordPullInfo.HITBOX_WIDTH;
		hitBoxHeight = VoidLordPullInfo.HITBOX_HEIGHT;

		skillIcon = Res.VOIDLORD_RESOURCE.VOIDLORD_HOTBAR_ICONS[1];
		soundEffect = Res.VOIDLORD_RESOURCE.SFX_VOIDLORD[2];

		animation = new Animation(player.getSkin().getVoidLordSkin().getPull(), animSpeed);

	}

	public void update(int delta) {

		handleMovement(delta);

		if (destroyTimer > 0) {
			destroyTimer -= delta;
		}

		if (destroyTimer <= 0) {
			removeThis();
		}

	}

	public void render(Graphics g) {
		animation.setRotation(rotation);
		animation.draw(getRenderX(), getRenderY(), width, height);
		animation.setRotation(rotation);
	}

	public void handleMovement(int delta) {
		move(delta, false);
	}

	public float getRenderX() {
		return Res.roundForRendering(x - 4);
	}

	public float getRenderY() {
		return Res.roundForRendering(y - 4);
	}

	public Shape getBounds() {
		return new Rectangle(x, y, hitBoxWidth, hitBoxHeight);
	}

	public float sortByY() {
		return y + hitBoxHeight;
	}

}