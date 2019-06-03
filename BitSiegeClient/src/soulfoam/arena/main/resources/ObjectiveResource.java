package soulfoam.arena.main.resources;

import org.newdawn.slick.Image;

import soulfoam.arena.main.gfx.SpriteSheet;
import soulfoam.arena.world.Tile;

public class ObjectiveResource {

	private SpriteSheet healthOrb;
	private SpriteSheet healthOrbHeal;

	private SpriteSheet capturePoint;
	private SpriteSheet smallCapturePoint;
	
	public Image[] HEALTH_ORB = new Image[12];
	public Image[] HEALTH_ORB_HEAL = new Image[8];

	public Image[] CAPTURE_POINT = new Image[1];
	
	public Image[] SMALL_CAPTURE_POINT = new Image[1];

	public ObjectiveResource() {

		healthOrb = new SpriteSheet("art/powerups/healthorb.png");
		healthOrbHeal = new SpriteSheet("art/powerups/healthorbfx.png");
		capturePoint = new SpriteSheet("art/maps/capturepoints.png");
		smallCapturePoint = new SpriteSheet("art/maps/smallcapturepoint.png");
		
		HEALTH_ORB = healthOrb.grabRow(0, Tile.TILE_SIZE * 2, Tile.TILE_SIZE * 2, 12);
		HEALTH_ORB_HEAL = healthOrbHeal.grabRow(0, 32, 32, 8);

		CAPTURE_POINT = capturePoint.grabRow(0, 176, 96);
		
		SMALL_CAPTURE_POINT = smallCapturePoint.grabRow(0, 176/2, 96/2);
	}
}
