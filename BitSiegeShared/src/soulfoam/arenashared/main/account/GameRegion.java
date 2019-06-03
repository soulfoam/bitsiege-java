package soulfoam.arenashared.main.account;

public class GameRegion {

	public static final int REGION_COUNT = 3;
	
	public static final int NA = 0;
	public static final int EU = 1;
	public static final int AU = 2;
	
	public static String getRegionName(int region){
		
		if (region == NA){
			return "North America";
		}
		if (region == EU){
			return "Europe";
		}
		if (region == AU){
			return "Australia";
		}
		
		return "??";
	}
}
