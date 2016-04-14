package hevs.fragil.patapon.others;

import com.badlogic.gdx.graphics.Color;

public abstract class Param {
	//Game parameters only
	//TODO all of customizable elements should be here !
	
	//Units related variables
	public static final int LIFE_BASE = 10;
	
	//TODO These should be gotten from sprite width **********
	public static final int UNIT_WIDTH = 20;
	public static final int SECTION_KEEPOUT = 30;
	//********************************************************
	
	//Style 
	public static final int FLOOR_DEPTH = 30;
	public static final int FRAME_DEGRADE_STEPS = 10;
	//Break the color in case of emergency #wedontneedthatbutitwassocolorfulwhenyoutouchme
	public static final Color Type1 = getFloatColor(243, 146, 0);
	public static final Color Type2 = getFloatColor(227, 6, 19);
	public static final Color Type3 = getFloatColor(230, 0, 126);
	public static final Color Type4 = getFloatColor(0, 159, 227);
	public static final Color Type5 = getFloatColor(58, 170, 53);
	
	//Timer periods
	public static final int MUSIC_BAR = 500;
	public static final int ACTIONS_BAR = 10;
	
	//Shifting width
	public static final int WALK_WIDTH = 200;
	public static final int RETREAT_WIDTH = 100;
	
	//Shifting time
	public static final int WALK_TIME = 2000;
	public static final int RETREAT_TIME = 2000;
	
	//Shifting time bonus (value at max fever score)
	public static final int WALK_TIME_BONUS = 500;
	public static final int RETREAT_TIME_BONUS = 100;
	
	//Colors
	public static Color BACKGROUND = new Color(1.000f, 0.871f, 0.678f, 0.1f);
	private static Color getFloatColor(int r, int g, int b){
		Color temp = new Color();
		temp.r = (float)(r/255.0);
		temp.g = (float)(g/255.0);
		temp.b = (float)(b/255.0);
		return temp;
	}
}
