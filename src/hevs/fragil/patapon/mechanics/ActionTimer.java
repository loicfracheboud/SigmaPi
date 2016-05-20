package hevs.fragil.patapon.mechanics;

import com.badlogic.gdx.math.Interpolation;

import hevs.fragil.patapon.music.Note;
import hevs.fragil.patapon.units.Company;
import hevs.fragil.patapon.units.Section;
import hevs.fragil.patapon.units.Unit;

public abstract class ActionTimer{
	private static int step = 0;
	private static float deltaTime;
	private static float start, progression, end;
	
	public static void run(float dt, Company c) {
		deltaTime = dt;
		switchAction(c.getAction(), c);
	}
	private static void switchAction(Action a, Company c){
		boolean finished = false;
		if(a != null){
			switch(a){
				case WALK : 	finished = walk(c);
								break;
				case ATTACK : 	finished = attack(c);
								break;
				case DEFEND : 	finished = defend(c);
								break;
				case MIRACLE : 	finished = miracle(c);
								break;
				case RETREAT : 	finished = retreat(c);
								break;
				case CHARGE : 	finished = charge(c);
								break;
				case STOP :		finished = stop(c);
								break;
				default : 		
								break;
			}	
			if(finished)
				c.actionFinished();
		}
	}
	private static boolean charge(Company c) {
		//increase attack skills for this time
		return wait(Param.CHARGE_TIME, c);
	}
	private static boolean miracle(Company c) {
		//TODO new screen launch !
		return true;
	}
	private static boolean defend(Company c) {
		//TODO increase defend skills for this time
		return wait(Param.DEFEND_TIME, c);
	}
	private static boolean shift(float totalTime, int distance, Company c){
		if(progression == 0){
			start = c.globalPosition;
			end = start + distance;
			System.out.println("company will shift from/to : " + start + " / "+ end);
		}
		
		progression += deltaTime/totalTime;
		c.moveAbsolute( (int) Interpolation.fade.apply(start, end, progression), deltaTime);
		if(progression >= 1){
			progression = 0;
			return true;
		}
		else return false;
	}
	private static boolean wait(double time, Company c){
		if(progression == 0){
			System.out.println("**Wait routine : wait " + time + " ms");
		}
		
		progression += deltaTime;
		
		if(progression >= time ){
			System.out.println("**->Wait routine finished");
			progression = 0;
			return true ;
		}
		
		else return false ;
		
	}
	private static boolean walk(Company c){
		float time = Param.WALK_TIME;
		
		//add bonus time (faster move with fever)
		time -= Param.WALK_TIME_BONUS/100.0 * Note.getFever();
		
		return shift(time, Param.WALK_WIDTH, c);
	}
	private static boolean retreat(Company c){
		float time = Param.RETREAT_TIME;
		float bonus = (float) (Param.RETREAT_TIME_BONUS/100.0 * Note.getFever());
		if (step == 0) {
			if(shift(time/4-bonus, -Param.RETREAT_WIDTH, c))
				step++;
		}
		
		else if(step == 1){
			if(wait(time/2+bonus, c))
				step++;
		}
		
		else if (shift(time/4, Param.RETREAT_WIDTH, c)){
			step = 0;
			return true;
		}
		
		return false;
	}
	private static boolean attack(Company c){
		progression += deltaTime;

		for (Section s : c.sections) {
			for (Unit u : s.units) {
					u.attack(deltaTime);
			}
		}
		
		//action ended
		if(progression >= Param.ATTACK_TIME) {
			progression = 0;
			return true;
		}
		return false;
	}
	private static boolean stop(Company c){
		end = start;
		progression = 0;
		step = 0;
		deltaTime = 0;
		return true;
	}
}
