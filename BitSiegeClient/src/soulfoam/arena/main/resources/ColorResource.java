package soulfoam.arena.main.resources;

import org.newdawn.slick.Color;

public class ColorResource {

	public Color BLUE_BUTTON = new Color(8, 103, 170);
	public Color WINDOW_BACKGROUND = new Color(0, 0, 0);
	public Color WINDOW_OUTLINE = new Color(53, 146, 212);
	public Color WINDOW_BLUE_STRIP = new Color(1, 16, 27);
	public Color WINDOW_BLUE_STRIP_BORDER = new Color(2, 30, 49);
	public Color WINDOW_BLUE_TABOUTLINE = new Color(30, 61, 93);

	public Color TOGGLE_BUTTON_ON = new Color(9, 183, 25);
	public Color TOGGLE_BUTTON_OFF = new Color(183, 29, 9);

	public Color BUTTON_DISABLED = Color.gray;

	public Color DEFENDERS = new Color(0, 185, 255);
	public Color ATTACKERS = new Color(255, 70, 0);

	public Color SIEGE_POINTS_BG = new Color(120, 49, 11);
	public Color BITS_BG = new Color(11, 80, 49);
	
	public Color GREEN_TEXT = new Color(40, 184, 92);
	public Color RED_TEXT = new Color(184, 47, 40);
	public Color ORANGE_TEXT = new Color(202, 124, 51);
	
	public Color TOOL_TIP_COOLDOWN = Color.red;
	public Color TOOL_TIP_DESCRIPTION = Color.white;
	public Color TOOL_TIP_DAMAGE = Color.orange;
	public Color TOOL_TIP_HEAL = Color.green;
	public Color TOOL_TIP_SLOW = new Color(255, 100, 100);
	public Color TOOL_TIP_STUNNED = Color.gray;
	public Color TOOL_TIP_DURATION = Color.white;
	
	public Color getDefendersColor(int opacity) {
		return new Color(0, 185, 255, opacity);
	}

	public Color getAttackersColor(int opacity) {
		return new Color(255, 70, 0, opacity);
	}

}
