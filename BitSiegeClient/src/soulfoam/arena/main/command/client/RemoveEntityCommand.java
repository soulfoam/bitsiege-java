package soulfoam.arena.main.command.client;

import java.util.ArrayList;

import org.newdawn.slick.Color;

import soulfoam.arena.entities.Entity;
import soulfoam.arena.main.command.Command;
import soulfoam.arena.main.game.Game;
import soulfoam.arena.main.gfx.MessageBox;
import soulfoam.arenashared.main.entityinfo.EntityInfo;


public class RemoveEntityCommand extends Command{

	private ArrayList<Entity> entityList = new ArrayList<Entity>();
	private ArrayList<Entity> entitiesToRemove = new ArrayList<Entity>();
	
	public RemoveEntityCommand(int playerID){
		super(Command.COMMAND_PACKET_REMOVE_ENTITY, playerID);
	}
	
	public RemoveEntityCommand(ArrayList<Entity> entityList){
		super(Command.COMMAND_PACKET_REMOVE_ENTITY);
		this.entityList = entityList;
	}
	
	public boolean execute(int delta) {
		
		player = Game.getGame().getPlayerObject(playerID);
		
		if (player != null){
			removePlayer(player);
		}
		else{
			if (!entityList.isEmpty()){
				for (Entity pe : Game.getGame().getPlayers()){
					if (!entityList.contains(pe)){
						removePlayer(pe);
					}
				}
			}
		}
		
		Game.getGame().getPlayers().removeAll(entitiesToRemove);
		
		return true;

	}
	
	private void removePlayer(Entity player){

		if (Game.getGame().getPlayers().contains(player)) {
			
			Game.getGame().removeEntityAbilities(player);

			if (!player.isBot() && player.getChallengerType() != EntityInfo.SPECTATE_PLAYER) {
				Game.getGame().getHUDDisplay().getNotificationLog().getTextMessageList()
						.add(new MessageBox(
								player.getUsername() + " from Team "
										+ Game.getGame().getUtil().getTeamName(player.getTeam()) + " has left!",
								Color.white, 3));
			}
			
			entitiesToRemove.add(player);

		}
		
	}
	
	public void undo() {
		
	}
}