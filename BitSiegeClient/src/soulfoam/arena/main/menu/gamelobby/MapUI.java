package soulfoam.arena.main.menu.gamelobby;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.StateBasedGame;

import soulfoam.arena.main.resources.Res;
import soulfoam.arena.world.Map;
import soulfoam.arena.world.World;

public class MapUI {

	public float x, y, width = 60, height = 60;
	private Map map;
	public Rectangle mapPopUpRect;

	public MapUI(float x, float y) {
		this.x = x;
		this.y = y;

		map = Res.MAP_RESOURCE.maps.get(0);

		mapPopUpRect = new Rectangle(x, y, width, height);
	}

	public void enter() {

	}

	public void update(int delta, GameContainer gc, StateBasedGame s) {
		setMap();
		width = 56;
		height = 56;
	}

	public void setMap() {
		if (map.getMapID() != World.MAP_ID) {
			map = Res.MAP_RESOURCE.getMap(World.MAP_ID);
		}
	}

	public void render(Graphics g, GameContainer gc) {

		map.getMapPreview().draw(x + 2, y + 2, width, height);

		renderMapOverlay(g, gc);

	}

	public void renderMapOverlay(Graphics g, GameContainer gc) {
		if (mapPopUpRect.contains(gc.getInput().getMouseX(), gc.getInput().getMouseY())) {

			map.getMapPreview().draw(x - 122, y - 60, 120, 120);

			g.setColor(new Color(0, 0, 0, 220));
			g.fillRect(x - 122, y - 71, 120, 11);

			g.setColor(new Color(255, 255, 255));
			g.drawRect(x - 122, y - 71, 120, 131);

			int centerOfMapName = (int) Res.bitFont.getWidth("Map Name: " + map.getMapName());
			Res.bitFont.drawString(x - 122 / 2 - centerOfMapName, y - 68, "Map Name: " + map.getMapName());
		}
	}
}
