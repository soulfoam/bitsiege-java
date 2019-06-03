package soulfoam.arenashared.main.entityinfo;

public class EntityInfo {
	
	public static final float MOVESPEED_CAP = 0.160f;
	public static final float ATTACKSPEED_CAP = 4.00f;

	public static final byte IDLE = 0;
	public static final byte WALKING = 1;
	public static final byte SPINNING = 2;

	public static final int RANDOMCHALLENGER = -1;
	public static final int KNIGHTCHALLENGER = 0;
	public static final int WARLOCKCHALLENGER = 1;
	public static final int ARCHERCHALLENGER = 2;
	public static final int CLERICCHALLENGER = 3;	
	public static final int ILLUSIONISTCHALLENGER = 4;	
	public static final int VOIDLORDCHALLENGER = 5;	
	public static final int WATERQUEENCHALLENGER = 6;	
	public static final int SHAMANCHALLENGER = 7;
	
	public static final int TOTAL_CLASSES = 8;
	
	public static final int KNIGHTCHALLENGERBOT = 200;
	public static final int WARLOCKCHALLENGERBOT = 201;
	public static final int ARCHERCHALLENGERBOT = 202;
	public static final int CLERICCHALLENGERBOT = 203;	
	public static final int ILLUSIONISTCHALLENGERBOT = 204;	
	public static final int VOIDLORDCHALLENGERBOT = 205;	
	public static final int WATERQUEENCHALLENGERBOT = 206;	
	public static final int SHAMANCHALLENGERBOT = 207;	
	
	
	public static final int SPECTATE_PLAYER = 99999;	
	
	public static byte DIR_UP = 0;
	public static byte DIR_LEFT = 1;
	public static byte DIR_DOWN = 2;
	public static byte DIR_RIGHT = 3;
	public static byte DIR_UP_LEFT = 4;
	public static byte DIR_UP_RIGHT = 5;
	public static byte DIR_DOWN_LEFT = 6;
	public static byte DIR_DOWN_RIGHT = 7;
	public static byte DIR_NONE = 8;
	
	public static float getOverallPower(float damageAmount, float buffAmount){
		return damageAmount + (buffAmount / 100 * damageAmount);
	}
	
	public static float getAttackSpeed(float attackSpeedTime, float attackSpeedBuffAmount){
		float theSpeed = attackSpeedTime + percentOf(attackSpeedTime, (int)attackSpeedBuffAmount);

		if (theSpeed >= ATTACKSPEED_CAP){
			theSpeed = ATTACKSPEED_CAP;
		}
		return theSpeed;
	}

	public static float getAttackSpeedTimer(float attackSpeedTime){
		float timer = (1/attackSpeedTime*1000);
		return timer;
	}
	
	private static float percentOf(float value, int percent){
		return (float)(value*(percent/100.0f));
	}
	
}
