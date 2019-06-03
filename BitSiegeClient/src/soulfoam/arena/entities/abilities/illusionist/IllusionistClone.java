package soulfoam.arena.entities.abilities.illusionist;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

import soulfoam.arena.entities.Entity;
import soulfoam.arena.entities.abilities.Ability;
import soulfoam.arena.entities.challengers.Illusionist;
import soulfoam.arena.entities.challengers.underglows.CosmeticBook;
import soulfoam.arena.main.game.Game;
import soulfoam.arena.main.resources.Res;
import soulfoam.arena.world.Tile;
import soulfoam.arenashared.main.abilityinfo.AbilityInfo;
import soulfoam.arenashared.main.abilityinfo.illusionist.IllusionistCloneInfo;
import soulfoam.arenashared.main.entityinfo.CosmeticLibrary;
import soulfoam.arenashared.main.entityinfo.EntityInfo;

public class IllusionistClone extends Ability {

	private boolean isAttacking;

	private float attackTimer = 0;
	private float attackTime = IllusionistCloneInfo.ATTACK_TIMER;

	private Animation walkUp;
	private Animation walkLeft;
	private Animation walkDown;
	private Animation walkRight;

	private Animation idleUp;
	private Animation idleLeft;
	private Animation idleDown;
	private Animation idleRight;

	private Animation ug;

	private boolean setLastClone;
	private byte currentDirection;

	public IllusionistClone(int gameID, float x, float y, Entity player) {

		abilityID = AbilityInfo.ILLUSIONISTCLONE;
		tag = AbilityInfo.PROJECTILE;

		this.gameID = gameID;
		this.x = x;
		this.y = y;

		this.player = player;

		damage = IllusionistCloneInfo.DAMAGE;
		moveSpeed = IllusionistCloneInfo.MOVE_SPEED;
		destroyTimer = IllusionistCloneInfo.DESTROY_TIMER;
		stunDuration = IllusionistCloneInfo.STUN_DURATION;

		width = IllusionistCloneInfo.WIDTH;
		height = IllusionistCloneInfo.HEIGHT;
		hitBoxWidth = IllusionistCloneInfo.HITBOX_WIDTH;
		hitBoxHeight = IllusionistCloneInfo.HITBOX_HEIGHT;

		animSpeed = 50;

		currentDirection = player.getCurrentDirection();

		if (currentDirection == EntityInfo.DIR_UP) {
			animation = new Animation(player.getSkin().getIllusionistSkin().getCloneSpawnUp(), animSpeed);
			animation.setLooping(false);
		}

		if (currentDirection == EntityInfo.DIR_LEFT) {
			animation = new Animation(player.getSkin().getIllusionistSkin().getCloneSpawnLeft(), animSpeed);
			animation.setLooping(false);
		}

		if (currentDirection == EntityInfo.DIR_DOWN) {
			animation = new Animation(player.getSkin().getIllusionistSkin().getCloneSpawnDown(), animSpeed);
			animation.setLooping(false);
		}

		if (currentDirection == EntityInfo.DIR_RIGHT) {
			animation = new Animation(player.getSkin().getIllusionistSkin().getCloneSpawnRight(), animSpeed);
			animation.setLooping(false);
		}

		walkUp = new Animation(player.getSkin().getIllusionistSkin().getWalkUp(), animSpeed);
		idleUp = new Animation(player.getSkin().getIllusionistSkin().getIdleUp(), 3000);
		walkLeft = new Animation(player.getSkin().getIllusionistSkin().getWalkLeft(), animSpeed);
		idleLeft = new Animation(player.getSkin().getIllusionistSkin().getIdleLeft(), 3000);
		walkDown = new Animation(player.getSkin().getIllusionistSkin().getWalkDown(), animSpeed);
		idleDown = new Animation(player.getSkin().getIllusionistSkin().getIdleDown(), 3000);
		walkRight = new Animation(player.getSkin().getIllusionistSkin().getWalkRight(), animSpeed);
		idleRight = new Animation(player.getSkin().getIllusionistSkin().getIdleRight(), 3000);

		if (player.getUnderglow().getUnderglowID() != CosmeticLibrary.UNDERGLOW_NONE) {
			ug = CosmeticBook.getUnderglowAnimation(player.getUnderglow().getUnderglowID());
		}

	}

	public void update(int delta) {

		setLastClone();

		if (isAttacking){
			move(delta);
		}
		
		if (destroyTimer > 0) {
			destroyTimer -= delta;
		}

		if (destroyTimer <= 0) {
			removeThis();
		}

		if (attackTimer > 0) {
			attackTimer -= delta;
		}

		if (attackTimer <= 0) {
			isAttacking = false;
		}

		if (Math.abs(dx) > Math.abs(dy)) {
			if (dx <= 0) {
				currentDirection = EntityInfo.DIR_LEFT;
			}
			if (dx > 0) {
				currentDirection = EntityInfo.DIR_RIGHT;
			}
		} else if (Math.abs(dy) > Math.abs(dx)) {
			if (dy <= 0) {
				currentDirection = EntityInfo.DIR_UP;
			}
			if (dy > 0) {
				currentDirection = EntityInfo.DIR_DOWN;
			}
		}

	}

	public float getRenderX() {
		return Res.roundForRendering(x - 8);
	}

	public float getRenderY() {
		return Res.roundForRendering(y - 8);
	}

	public void render(Graphics g) {
		if (animation != null) {
			renderShield(g);

			if (!player.isDead() && ug != null) {
				ug.draw(getRenderX() + 4, getRenderY() + 4, new Color(255, 255, 255, Game.getGame().getPlayer().getUnderglow().getGlowTimer()));
			}

			if (Game.getGame().getClientSettings().isShowHealthBars()) {
				if (player != null && !player.isDead()) {

				}
			}

			if (Game.getGame().getClientSettings() != null
					&& Game.getGame().getClientSettings().isShowPlayerNames()) {
				float centerOfName = Res.bitFont.getWidth(player.getUsername());
				Res.bitFont.drawString(Res.roundForRendering(x - centerOfName + hitBoxWidth), Res.roundForRendering(y - 16), player.getUsername());
			}

			if (animation.isStopped()) {
				if (isAttacking) {
					if (currentDirection == EntityInfo.DIR_UP) {
						walkUp.draw(getRenderX(), getRenderY(), width, height);
					}

					if (currentDirection == EntityInfo.DIR_LEFT) {
						walkLeft.draw(getRenderX(), getRenderY(), width, height);
					}

					if (currentDirection == EntityInfo.DIR_DOWN) {
						walkDown.draw(getRenderX(), getRenderY(), width, height);
					}

					if (currentDirection == EntityInfo.DIR_RIGHT) {
						walkRight.draw(getRenderX(), getRenderY(), width, height);
					}
				} else {
					if (currentDirection == EntityInfo.DIR_UP) {
						idleUp.draw(getRenderX(), getRenderY(), width, height);
					}

					if (currentDirection == EntityInfo.DIR_LEFT) {
						idleLeft.draw(getRenderX(), getRenderY(), width, height);
					}

					if (currentDirection == EntityInfo.DIR_DOWN) {
						idleDown.draw(getRenderX(), getRenderY(), width, height);
					}

					if (currentDirection == EntityInfo.DIR_RIGHT) {
						idleRight.draw(getRenderX(), getRenderY(), width, height);
					}
				}
			} else {
				animation.draw(getRenderX(), getRenderY(), width, height);
			}
		}
	}

	public void setLastClone() {
		if (!setLastClone) {

			Illusionist p = (Illusionist) player;
			p.passive.getCloneList().add(this);

			setLastClone = true;
		}
	}

	public void attack(float dx, float dy) {
		isAttacking = true;
		attackTimer = attackTime;
		this.dx = dx;
		this.dy = dy;
	}

	public void renderShield(Graphics g) {
		if (player.getShield() > 0) {
			Res.GENERAL_RESOURCE.SHIELD_ANIMATION[0].draw(Res.roundForRendering(x - 12), Res.roundForRendering(y - 12));
		}
	}

	public Shape getBounds() {
		return new Rectangle(x, y, hitBoxWidth, hitBoxHeight);
	}
	
	public float sortByY() {
		return y + 12;
	}
	
	public boolean move(int delta) {
		
		float xm = dx * moveSpeed * delta;
		float ym = dy * moveSpeed * delta;
		
		float nY = y - 4;
		float nH = 16;
        
		for (float xa = x + xm; xa < x + xm + hitBoxWidth; xa++) {
			for (float ya = nY + ym; ya < nY + ym + nH; ya++) {
				if (xa / Tile.TILE_SIZE <= 0 || xa / Tile.TILE_SIZE >= Game.getGame().getWorld().getMap().getMapWidth() || ya / Tile.TILE_SIZE <= 0 || ya / Tile.TILE_SIZE >= Game.getGame().getWorld().getMap().getMapHeight()){
					return false;
				}
				else if (Game.getGame().getWorld().getTile(xa, ya).isBlocked()) {
					return false;
				}
				
			}
		}
		
        x += xm;
        y += ym;
        
        return true;
	}

	public byte getCurrentDirection() {
		return currentDirection;
	}

	public void setCurrentDirection(byte currentDirection) {
		this.currentDirection = currentDirection;
	}
}
