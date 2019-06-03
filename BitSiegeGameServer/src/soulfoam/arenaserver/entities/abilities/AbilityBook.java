package soulfoam.arenaserver.entities.abilities;


import soulfoam.arenaserver.entities.Entity;
import soulfoam.arenaserver.entities.abilities.archer.ArcherBuff;
import soulfoam.arenaserver.entities.abilities.archer.ArcherExplodingArrow;
import soulfoam.arenaserver.entities.abilities.archer.ArcherExplodingArrowSwitch;
import soulfoam.arenaserver.entities.abilities.archer.ArcherExplosion;
import soulfoam.arenaserver.entities.abilities.archer.ArcherFireArrow;
import soulfoam.arenaserver.entities.abilities.archer.ArcherFireArrowSwitch;
import soulfoam.arenaserver.entities.abilities.archer.ArcherIceArrow;
import soulfoam.arenaserver.entities.abilities.archer.ArcherIceArrowSwitch;
import soulfoam.arenaserver.entities.abilities.cleric.ClericBasicAttack;
import soulfoam.arenaserver.entities.abilities.cleric.ClericBasicAttackHeal;
import soulfoam.arenaserver.entities.abilities.cleric.ClericBlind;
import soulfoam.arenaserver.entities.abilities.cleric.ClericHeal;
import soulfoam.arenaserver.entities.abilities.cleric.ClericSwitch;
import soulfoam.arenaserver.entities.abilities.cleric.ClericTeamHeal;
import soulfoam.arenaserver.entities.abilities.illusionist.IllusionistBasicAttack;
import soulfoam.arenaserver.entities.abilities.illusionist.IllusionistClone;
import soulfoam.arenaserver.entities.abilities.illusionist.IllusionistCloneAttack;
import soulfoam.arenaserver.entities.abilities.illusionist.IllusionistRemoteCloneSpawn;
import soulfoam.arenaserver.entities.abilities.illusionist.IllusionistSpawnClone;
import soulfoam.arenaserver.entities.abilities.illusionist.IllusionistSwapPosition;
import soulfoam.arenaserver.entities.abilities.knight.KnightDash;
import soulfoam.arenaserver.entities.abilities.knight.KnightInvincibleBuff;
import soulfoam.arenaserver.entities.abilities.knight.KnightMeleeAttack;
import soulfoam.arenaserver.entities.abilities.knight.KnightMeleeSpin;
import soulfoam.arenaserver.entities.abilities.knight.KnightShieldThrow;
import soulfoam.arenaserver.entities.abilities.shaman.ShamanBasicAttack;
import soulfoam.arenaserver.entities.abilities.shaman.ShamanBuffTotem;
import soulfoam.arenaserver.entities.abilities.shaman.ShamanDebuffTotem;
import soulfoam.arenaserver.entities.abilities.shaman.ShamanConfuseTotem;
import soulfoam.arenaserver.entities.abilities.shaman.ShamanShieldTotem;
import soulfoam.arenaserver.entities.abilities.voidlord.VoidLordAOE;
import soulfoam.arenaserver.entities.abilities.voidlord.VoidLordHole;
import soulfoam.arenaserver.entities.abilities.voidlord.VoidLordInvisible;
import soulfoam.arenaserver.entities.abilities.voidlord.VoidLordMeleeAttack;
import soulfoam.arenaserver.entities.abilities.voidlord.VoidLordPull;
import soulfoam.arenaserver.entities.abilities.warlock.WarlockBasicAttack;
import soulfoam.arenaserver.entities.abilities.warlock.WarlockLifeDrain;
import soulfoam.arenaserver.entities.abilities.warlock.WarlockStorm;
import soulfoam.arenaserver.entities.abilities.warlock.WarlockStormBit;
import soulfoam.arenaserver.entities.abilities.warlock.WarlockTeleport;
import soulfoam.arenaserver.entities.abilities.warlock.WarlockTeleportFX;
import soulfoam.arenaserver.entities.abilities.warlock.WarlockTower;
import soulfoam.arenaserver.entities.abilities.warlock.WarlockTowerAttack;
import soulfoam.arenaserver.entities.abilities.waterqueen.WaterQueenAbsorb;
import soulfoam.arenaserver.entities.abilities.waterqueen.WaterQueenBasicAttack;
import soulfoam.arenaserver.entities.abilities.waterqueen.WaterQueenBuff;
import soulfoam.arenaserver.entities.abilities.waterqueen.WaterQueenWaterBall;
import soulfoam.arenaserver.entities.abilities.waterqueen.WaterQueenWave;
import soulfoam.arenaserver.main.game.Game;
import soulfoam.arenashared.main.abilityinfo.AbilityInfo;

public class AbilityBook {
	
	public static Ability getAbilityByID(int abilityID, int gameID, float x, float y, Entity player){
        float dx = x - player.getX();
        float dy = y - player.getY();

        float dirLength = (float) Math.sqrt(dx*dx + dy*dy);
        dx = dx/dirLength;
        dy = dy/dirLength;
        
		return getAbilityByID(abilityID, gameID, x, y, dx, dy, player);
	}

	public static Ability getAbilityByID(int abilityID, int gameID, float x, float y, float dx, float dy, Entity player){
        
		if (abilityID == AbilityInfo.WARLOCKBASICATTACK){
			return new WarlockBasicAttack(gameID, dx, dy, player);
		}
		if (abilityID == AbilityInfo.WARLOCKTOWER){
			return new WarlockTower(gameID, x, y, player);
		}
		if (abilityID == AbilityInfo.WARLOCKLIFEDRAIN){
			return new WarlockLifeDrain(gameID, x, y, player);
		}
		if (abilityID == AbilityInfo.WARLOCKSTORMBIT){
			return new WarlockStormBit(gameID, x, y, player);
		}
		if (abilityID == AbilityInfo.WARLOCKSTORM){
			return new WarlockStorm(gameID, x, y, player);
		}
		if (abilityID == AbilityInfo.WARLOCKTELEPORTFX){
			return new WarlockTeleportFX(gameID, x, y, player);
		}
		if (abilityID == AbilityInfo.WARLOCKTOWERATTACK){
			return new WarlockTowerAttack(gameID, x, y, dx, dy, player);
		}
		if (abilityID == AbilityInfo.WARLOCKTELEPORT){
			return new WarlockTeleport(gameID, x, y, player);
		}
		if (abilityID == AbilityInfo.KNIGHTMELEEATTACK){
			return new KnightMeleeAttack(gameID, dx, dy, player);
		}
		if (abilityID == AbilityInfo.KNIGHTSHIELDTHROW){
			return new KnightShieldThrow(gameID, dx, dy, player);
		}
		if (abilityID == AbilityInfo.KNIGHTINVINCIBLEBUFF){
			return new KnightInvincibleBuff(gameID, player);
		}
		if (abilityID == AbilityInfo.KNIGHTMELEESPIN){
			return new KnightMeleeSpin(gameID, player);
		}
		if (abilityID == AbilityInfo.KNIGHTDASH){
			return new KnightDash(gameID, dx, dy, player);
		}
		if (abilityID == AbilityInfo.ARCHERICEARROWSWITCH){
			return new ArcherIceArrowSwitch(gameID, player);
		}
		if (abilityID == AbilityInfo.ARCHERICEARROW){
			return new ArcherIceArrow(gameID, dx, dy, player);
		}
		if (abilityID == AbilityInfo.ARCHERFIREARROWSWITCH){
			return new ArcherFireArrowSwitch(gameID, player);
		}
		if (abilityID == AbilityInfo.ARCHERFIREARROW){
			return new ArcherFireArrow(gameID, dx, dy, player);
		}
		if (abilityID == AbilityInfo.ARCHEREXPLODINGARROWSWITCH){
			return new ArcherExplodingArrowSwitch(gameID, player);
		}
		if (abilityID == AbilityInfo.ARCHEREXPLODINGARROW){
			return new ArcherExplodingArrow(gameID, dx, dy, player);
		}
		if (abilityID == AbilityInfo.ARCHEREXPLOSION){
			return new ArcherExplosion(gameID, x, y, player);
		}
		if (abilityID == AbilityInfo.ARCHERBUFF){
			return new ArcherBuff(gameID, player);
		}
		if (abilityID == AbilityInfo.CLERICBASICATTACK){
			return new ClericBasicAttack(gameID, dx, dy, player);
		}
		if (abilityID == AbilityInfo.CLERICHEAL){
			return new ClericHeal(gameID, x, y, player);
		}
		if (abilityID == AbilityInfo.CLERICBLIND){
			return new ClericBlind(gameID, dx, dy, player);
		}
		if (abilityID == AbilityInfo.CLERICSWITCH){
			return new ClericSwitch(gameID, player);
		}
		if (abilityID == AbilityInfo.CLERICBASICATTACKHEAL){
			return new ClericBasicAttackHeal(gameID, dx, dy, player);
		}
		if (abilityID == AbilityInfo.CLERICTEAMHEAL){
			return new ClericTeamHeal(gameID, player);
		}
		if (abilityID == AbilityInfo.ILLUSIONISTBASICATTACK){
			return new IllusionistBasicAttack(gameID, dx, dy, player);
		}
		if (abilityID == AbilityInfo.ILLUSIONISTSPAWNCLONE){
			return new IllusionistSpawnClone(gameID, player);
		}
		if (abilityID == AbilityInfo.ILLUSIONISTCLONEATTACK){
			return new IllusionistCloneAttack(gameID, x, y, player);
		}
		if (abilityID == AbilityInfo.ILLUSIONISTSWAPPOSITION){
			return new IllusionistSwapPosition(gameID, player);
		}
		if (abilityID == AbilityInfo.ILLUSIONISTREMOTECLONESPAWN){
			return new IllusionistRemoteCloneSpawn(gameID, x, y, player);
		}
		if (abilityID == AbilityInfo.ILLUSIONISTCLONE){
			return new IllusionistClone(gameID, x, y, false, player);
		}
		if (abilityID == AbilityInfo.ILLUSIONISTULTCLONE){
			return new IllusionistClone(gameID, x, y, true, player);
		}
		if (abilityID == AbilityInfo.VOIDLORDMELEEATTACK){
			return new VoidLordMeleeAttack(gameID, dx, dy, player);
		}
		if (abilityID == AbilityInfo.VOIDLORDPULL){
			return new VoidLordPull(gameID, dx, dy, player);
		}
		if (abilityID == AbilityInfo.VOIDLORDINVISIBLE){
			return new VoidLordInvisible(gameID, player);
		}
		if (abilityID == AbilityInfo.VOIDLORDAOE){
			return new VoidLordAOE(gameID, player);
		}
		if (abilityID == AbilityInfo.VOIDLORDHOLE){
			return new VoidLordHole(gameID, player);
		}
		if (abilityID == AbilityInfo.WATERQUEENBASICATTACK){
			return new WaterQueenBasicAttack(gameID, dx, dy, player);
		}
		if (abilityID == AbilityInfo.WATERQUEENWATERBALL){
			return new WaterQueenWaterBall(gameID, dx, dy, player);
		}
		if (abilityID == AbilityInfo.WATERQUEENWAVE){
			return new WaterQueenWave(gameID, dx, dy, player);
		}
		if (abilityID == AbilityInfo.WATERQUEENABSORB){
			return new WaterQueenAbsorb(gameID, player);
		}
		if (abilityID == AbilityInfo.WATERQUEENWATERBUFF){
			return new WaterQueenBuff(gameID, player);
		}
		if (abilityID == AbilityInfo.SHAMANBUFFTOTEM){
			return new ShamanBuffTotem(gameID, x, y, player);
		}
		if (abilityID == AbilityInfo.SHAMANBASICATTACK){
			return new ShamanBasicAttack(gameID, dx, dy, player);
		}
		if (abilityID == AbilityInfo.SHAMANSHIELDTOTEM){
			return new ShamanShieldTotem(gameID, x, y, player);
		}
		if (abilityID == AbilityInfo.SHAMANDEBUFFTOTEM){
			return new ShamanDebuffTotem(gameID, x, y, player);
		}
		if (abilityID == AbilityInfo.SHAMANCONFUSETOTEM){
			return new ShamanConfuseTotem(gameID, x, y, player);
		}

		return null;
	}
	
}
