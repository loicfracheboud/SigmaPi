package hevs.fragil.patapon.graphics;

import com.badlogic.gdx.graphics.Color;

import hevs.gdx2d.components.audio.SoundSample;
import hevs.gdx2d.lib.GdxGraphics;
import hevs.gdx2d.lib.PortableApplication;

import java.util.Timer;
import java.util.Vector;

import com.badlogic.gdx.Input.Keys;

import hevs.fragil.patapon.music.Drum;
import hevs.fragil.patapon.music.Note;
import hevs.fragil.patapon.music.Sequence;
import hevs.fragil.patapon.music.Tempo;
import hevs.fragil.patapon.others.Param;
import hevs.fragil.patapon.units.*;

public class Map extends PortableApplication {
	private int width;
	private static Vector<Company> companies = new Vector<Company>();
	private static Vector<SoundSample> drums = new Vector<SoundSample>();
	private static Vector<SoundSample> tracks = new Vector<SoundSample>();
	private static SoundSample snap;
	private static Frame f;
	private static Timer timer = new Timer();
	public static Color backColor = Color.ORANGE;
	
	public static void main(String[] args) {
		new Map(1000);
		companies.add(newSampleCompany(4,3,3));
	}
	/**
	 * @author loicg
	 * @param nb1 : number of archers
	 * @param nb2 : number of swordmans
	 * @param nb3 : number of shields
	 * @return a sample company that contains {@code nb1} archers,
	 * {@code nb2} swordmans and {@code nb3}shields.
	 */
	private static Company newSampleCompany(int nb1, int nb2, int nb3){
		Company comp = new Company("Patapons");
		
		for(int i = 0 ; i < 3; i++){
			comp.add(new Section(Integer.toString(i)));
		}
		for(int i = 0 ; i < nb1; i++){
			comp.sections.elementAt(0).add(new Archer());
		}
		for(int i = 0 ; i < nb2; i++){
			comp.sections.elementAt(1).add(new Swordman());
		}
		for(int i = 0 ; i < nb3; i++){
			comp.sections.elementAt(2).add(new Shield());
		}
		
		int initialPos = comp.getWidth()/2 + 50;
		comp.moveAbsolute(initialPos);
		return comp;
	}
	public Map(int w){
		this(w, 500);
	}
	public Map(int w, int h){
		super(w, h);
		this.width = w;
	}
	public static int getNbTracks(){
		return tracks.size();
	}
	public static void nextTrack(){
		for (SoundSample track :  tracks)
			track.stop();
		tracks.elementAt(Tempo.soundEnable).loop();
	}
	public static void snapToggle(){
		if(Tempo.snapEnable)snap.loop();
		else snap.stop();
	}
	public void add (Company c){
		companies.add(c);
	}
	@Override
	public void onDispose() {
		super.onDispose();
		for (SoundSample note : drums) {
			note.dispose();
		}
		for (SoundSample track : tracks) {
			track.dispose();
		}
		snap.dispose();
	}
	@Override
	public void onInit() {
		setTitle("Test Map Patapons H-E-S! - by FraGil 2016");
		//Load the sound files
		//TODO Pas propre, on devrait plutot créer 4 notes...
		drums.add(new SoundSample("data/music/HE.wav"));
		drums.add(new SoundSample("data/music/S.wav"));
		drums.add(new SoundSample("data/music/SO.wav"));
		drums.add(new SoundSample("data/music/YES.wav"));

		snap = new SoundSample("data/music/loop2.wav");
		
		tracks.add(new SoundSample("data/music/loop1.wav"));
		tracks.add(new SoundSample("data/music/loop3.wav"));
		tracks.add(new SoundSample("data/music/loop4.wav"));
		tracks.add(new SoundSample("data/music/loop5.wav"));
		tracks.add(new SoundSample("data/music/loop6.wav"));
		
		timer.schedule(new Tempo(), 0, Param.BAR);

		//Load the image files
		Archer.setImgPath("data/images/Archer.png");
		Swordman.setImgPath("data/images/Swordman.png");
		Shield.setImgPath("data/images/Shield.png");

		f = new Frame();
	}
	@Override
	public void onKeyDown(int keycode) {
		super.onKeyDown(keycode);
		if (keycode == Keys.NUM_1){
			//TODO Pas propre, on devrait plutot créer 4 notes...
			//Du genre HE.play()
			drums.elementAt(0).play();
			
			switchAction(Sequence.add(new Note(Drum.HE)));
		}
		if (keycode == Keys.NUM_2){
			drums.elementAt(1).play();
			switchAction(Sequence.add(new Note(Drum.S)));		
		}
		if (keycode == Keys.NUM_3){
			drums.elementAt(2).play();
			switchAction(Sequence.add(new Note(Drum.SO)));		
		}
		if (keycode == Keys.NUM_4){
			drums.elementAt(3).play();
			switchAction(Sequence.add(new Note(Drum.YES)));		
		}

		if (keycode == Keys.SPACE)
			for (SoundSample note : drums)
				note.setPitch(2);
		if (keycode == Keys.ENTER)
			for (SoundSample note : drums) 
				note.setPitch(1);
		
		if (keycode == Keys.A)
			Tempo.snapFlag = !Tempo.snapFlag;
		if (keycode == Keys.D)
			Tempo.soundFlag++ ;
		if (keycode == Keys.LEFT)
			companies.firstElement().moveRelative(-10);
		if (keycode == Keys.RIGHT)
			companies.firstElement().moveRelative(+10);
	}
	public void onGraphicRender(GdxGraphics g) {		
		//clear the screen
		g.clear(backColor);
		
		//write help
		g.drawStringCentered(490f, "Touche A pour activer/désactiver les claps");
		g.drawStringCentered(470f, "Flèches pour bouger la companie");
		g.drawStringCentered(450f, "Touches 1 à 4 pour jouer les sons");
		g.drawStringCentered(430f, "Touche D pour changer de loop sonore");
		
		
		float fY = Param.FLOOR_DEPTH;
		
		//draw floor
		g.drawFilledRectangle(width/2, 0, width, fY, 0,Color.DARK_GRAY);
		
		//draw centers
//		float highPos = -10f;
		g.setColor(Color.BLACK);
		
		for (Company c : companies) {
//			float cGP = c.globalPosition;
//			//vertical line on the company center
//			g.drawLine(cGP, fY + 60f, cGP, fY + 160f);
//			g.drawString(cGP, fY + 180f,"Company " + c.name, 3);
			for (Section s : c.sections) {
				//1 time up, 1 time down
//				highPos *= -1;
//				float sGP = s.globalPosition;
//				//vertical line on the section center
//				g.drawLine(sGP, fY + 50f, sGP, fY+highPos+120f);
//				g.drawString(sGP, fY + highPos + 135f, "Section " + s.name, 3);
				for (Unit u : s.units) {
					u.draw(g);
				}
			}
		}
		
		//oh yeah
		g.drawSchoolLogoUpperRight();
		//draw the frame to show the rythm
		f.draw(g);
	}
	private static void switchAction (Action a){
		if(a != null)
		switch(a){
			case WALK : 	walk();
							break;
			case ATTACK : 	System.out.println("Units are attacking");
							break;
			case DEFEND : 	System.out.println("Units are defending");
							break;
			case MIRACLE : 	System.out.println("Units are *miracling*");
							break;
			case RETREAT : 	retreat();
							break;
			case CHARGE : 	System.out.println("Units are charging");
							break;
			default : 		System.out.println("I don't know what units are doing !");
							break;
		}	
	}
	private static void walk(){
		//TODO add delay and speed
		companies.elementAt(0).moveRelative(50);
	}
	private static void retreat(){
		//TODO add delay and speed
		companies.elementAt(0).moveRelative(-50);
	}
}
