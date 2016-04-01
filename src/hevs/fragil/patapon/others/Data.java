package hevs.fragil.patapon.others;


import hevs.fragil.patapon.music.Drum;

public class Data {
	//TODO put all of this class content in new classes in a static way ! 
	//This class normally doesn't need to exist !
	
	//Command references
	public static final Drum[] WALK = {Drum.HE, Drum.HE, Drum.HE, Drum.S};
	public static final Drum[] ATTACK = {Drum.S, Drum.S, Drum.HE, Drum.S};
	public static final Drum[] DEFEND = {Drum.SO, Drum.SO, Drum.HE, Drum.S};
	public static final Drum[] MIRACLE = {Drum.YES, Drum.YES, Drum.YES, Drum.YES, Drum.YES};
	public static final Drum[] RETREAT = {Drum.S, Drum.HE, Drum.S, Drum.HE};
	public static final Drum[] CHARGE = {Drum.S, Drum.S, Drum.SO, Drum.SO};
	
	//Game constants
	public static final int LIFE_BASE = 10;
	public static final int BAR = 500;
	public static final int UNIT_WIDTH = 20;
	public static final int SECTION_KEEPOUT = 30;
	public static final int FLOOR_DEPTH = 30;
	public static final int FRAME_DURATION = 10;
	
	//Sound Flags TODO should we move it to the Map class ?
	public static int soundFlag = 1;
	public static int soundEnable = 0;
	public static boolean soundChange = false;
	public static boolean snapFlag = false;
	public static boolean snapEnable = false;
	public static boolean snapChange = false;	
}
