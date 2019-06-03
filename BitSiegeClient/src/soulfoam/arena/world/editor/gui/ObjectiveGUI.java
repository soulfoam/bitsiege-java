package soulfoam.arena.world.editor.gui;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

import soulfoam.arena.entities.objectives.CapturePoint;
import soulfoam.arena.entities.objectives.HealthObjective;
import soulfoam.arena.entities.objectives.Objective;
import soulfoam.arena.entities.objectives.SmallCapturePoint;
import soulfoam.arena.main.game.Game;
import soulfoam.arena.world.Tile;
import soulfoam.arena.world.editor.MapEditor;
import soulfoam.arenashared.main.objectiveinfo.ObjectiveInfo;

public class ObjectiveGUI extends BaseEditorGUI {

	public ArrayList<Objective> guiObjectives = new ArrayList<Objective>();
	public Objective selectedObjective;

	public ObjectiveGUI(float x, float y, MapEditor editor) {
		this.x = x;
		this.y = y;
		this.editor = editor;
		title = "Objectives";
		defaultRenderLayer = -1;
		width = Tile.TILE_SIZE * 26;
		height = Tile.TILE_SIZE * 22;

		HealthObjective ho = new HealthObjective(x + 10, y + 10, ObjectiveInfo.HEALTHPICKUP_OBJECTIVE);
		CapturePoint cpor = new CapturePoint(x + 95, y + 45);
		SmallCapturePoint cpob = new SmallCapturePoint(x + 95, y + 130);

		guiObjectives.add(ho);
		guiObjectives.add(cpor);
		guiObjectives.add(cpob);
	}

	public void update(GameContainer gc, int delta) {
		mouseX = (int) (gc.getInput().getMouseX() + Game.getGame().getCam().getX());
		mouseY = (int) (gc.getInput().getMouseY() + Game.getGame().getCam().getY());

		checkForPickUp(gc);

	}

	public void render(GameContainer gc, Graphics g) {

		if (!hidden) {
			g.setColor(new Color(8, 103, 170, 150));
			g.fillRect(x, y, width, height);

			g.setColor(Color.white);
			g.drawRect(x, y, width, height);

			for (Objective o : guiObjectives) {
				if (o.getObjectiveTeam() == ObjectiveInfo.CAPTURE_POINT
						|| o.getObjectiveTeam() == ObjectiveInfo.SMALL_CAPTURE_POINT) {
					o.render(g, x + o.getX() - 88, y + o.getY() - 50);
				}
				if (o.getObjectiveTeam() == ObjectiveInfo.HEALTHPICKUP_OBJECTIVE) {
					o.render(g, x + o.getX() - 10, y + o.getY() - 10);
				}
			}
		}

		if (selectedObjective != null) {
			selectedObjective.render(g, gc.getInput().getMouseX() - selectedObjective.getWidth() / 2,
					gc.getInput().getMouseY() - selectedObjective.getHeight() / 2);
		}

	}

	public void spawnObjective(Objective selectedObjective, float x, float y) {

		if (selectedObjective != null) {
			if (selectedObjective.getObjectiveTeam() == ObjectiveInfo.CAPTURE_POINT) {
				CapturePoint cpo = new CapturePoint(x, y);
				cpo.setRemoteX(cpo.getX());
				cpo.setRemoteY(cpo.getY());
				Game.getGame().getObjectives().add(cpo);
			} else if (selectedObjective.getObjectiveTeam() == ObjectiveInfo.SMALL_CAPTURE_POINT) {
				SmallCapturePoint cpo = new SmallCapturePoint(x, y);
				cpo.setRemoteX(cpo.getX());
				cpo.setRemoteY(cpo.getY());
				Game.getGame().getObjectives().add(cpo);
			} else if (selectedObjective.getObjectiveTeam() == ObjectiveInfo.HEALTHPICKUP_OBJECTIVE) {
				HealthObjective ho = new HealthObjective(x, y, ObjectiveInfo.HEALTHPICKUP_OBJECTIVE);
				ho.setRemoteX(ho.getX());
				ho.setRemoteY(ho.getY());
				Game.getGame().getObjectives().add(ho);
			}
		}
	}

	public void mousePressed(int button, int x, int y) {
		if (!editor.clickingOnGUI(x, y)) {
			if (button == Input.MOUSE_LEFT_BUTTON) {
				if (selectedObjective != null) {
					spawnObjective(selectedObjective, mouseX, mouseY);
				}
			}
			if (button == Input.MOUSE_RIGHT_BUTTON) {
				Objective[] tempList = new Objective[Game.getGame().getObjectives().size()];
				Game.getGame().getObjectives().toArray(tempList);
				for (Objective o : tempList) {
					if (o.getBounds().contains(mouseX, mouseY)) {
						Game.getGame().getObjectives().remove(o);
					}
				}
			}
		} else {
			if (button == Input.MOUSE_LEFT_BUTTON) {
				for (Objective o : guiObjectives) {
					if (o.getBounds().contains(x, y)) {
						selectedObjective = o;
					}
				}
			}
		}
	}

	public void keyReleased(int key, char c) {
		// TODO Auto-generated method stub

	}

	public void mouseWheelMoved(int m) {
		// TODO Auto-generated method stub

	}

}
