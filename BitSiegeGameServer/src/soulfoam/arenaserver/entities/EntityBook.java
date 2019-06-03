package soulfoam.arenaserver.entities;

import soulfoam.arenaserver.entities.challengers.Archer;
import soulfoam.arenaserver.entities.challengers.Cleric;
import soulfoam.arenaserver.entities.challengers.Illusionist;
import soulfoam.arenaserver.entities.challengers.Knight;
import soulfoam.arenaserver.entities.challengers.Shaman;
import soulfoam.arenaserver.entities.challengers.VoidLord;
import soulfoam.arenaserver.entities.challengers.Warlock;
import soulfoam.arenaserver.entities.challengers.WaterQueen;
import soulfoam.arenaserver.entities.challengers.bots.ArcherBot;
import soulfoam.arenaserver.entities.challengers.bots.Bot;
import soulfoam.arenaserver.entities.challengers.bots.ClericBot;
import soulfoam.arenaserver.entities.challengers.bots.IllusionistBot;
import soulfoam.arenaserver.entities.challengers.bots.KnightBot;
import soulfoam.arenaserver.entities.challengers.bots.ShamanBot;
import soulfoam.arenaserver.entities.challengers.bots.VoidLordBot;
import soulfoam.arenaserver.entities.challengers.bots.WarlockBot;
import soulfoam.arenaserver.entities.challengers.bots.WaterQueenBot;
import soulfoam.arenaserver.main.game.Game;
import soulfoam.arenashared.main.entityinfo.EntityInfo;

public class EntityBook {

	public static Entity getChallengerByID(int challengerID, float x, float y, int skinID, int underglowID){

		if (challengerID == EntityInfo.KNIGHTCHALLENGER)
		{
			return new Knight(x, y, skinID, underglowID);
		}
		
		if (challengerID == EntityInfo.WARLOCKCHALLENGER)
		{
			return new Warlock(x, y, skinID, underglowID);
		}
		
		if (challengerID == EntityInfo.ARCHERCHALLENGER)
		{
			return new Archer(x, y, skinID, underglowID);
		}
		
		if (challengerID == EntityInfo.CLERICCHALLENGER)
		{
			return new Cleric(x, y, skinID, underglowID);
		}
		
		if (challengerID == EntityInfo.ILLUSIONISTCHALLENGER)
		{
			return new Illusionist(x, y, skinID, underglowID);
		}
		
		if (challengerID == EntityInfo.VOIDLORDCHALLENGER)
		{
			return new VoidLord(x, y, skinID, underglowID);
		}
		
		if (challengerID == EntityInfo.WATERQUEENCHALLENGER)
		{
			return new WaterQueen(x, y, skinID, underglowID);
		}
		
		if (challengerID == EntityInfo.SHAMANCHALLENGER)
		{
			return new Shaman(x, y, skinID, underglowID);
		}

		if (challengerID == Bot.KNIGHTCLASSBOT)
		{
			return new KnightBot(x, y);
		}
		
		if (challengerID == Bot.WARLOCKCLASSBOT)
		{
			return new WarlockBot(x, y);
		}
		
		if (challengerID == Bot.ARCHERCLASSBOT)
		{
			return new ArcherBot(x, y);
		}
		
		if (challengerID == Bot.CLERICCLASSBOT)
		{
			return new ClericBot(x, y);
		}
		
		if (challengerID == Bot.ILLUSIONISTCLASSBOT)
		{
			return new IllusionistBot(x, y);
		}
		
		if (challengerID == Bot.VOIDLORDCLASSBOT)
		{
			return new VoidLordBot(x, y);
		}
		
		if (challengerID == Bot.WATERQUEENCLASSBOT)
		{
			return new WaterQueenBot(x, y);
		}
		
		if (challengerID == Bot.SHAMANCLASSBOT)
		{
			return new ShamanBot(x, y);
		}
		
		return null;
	}
	
}
