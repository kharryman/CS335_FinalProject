package com.mm.main;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mm.tools.ScreenManager;
import com.mm.tools.ScreenManager.Screen;

/**
 * 
 * 
 *
 */
public class MegaManGame extends Game {
	public static final int V_WIDTH = 400;
	public static final int V_HEIGHT  = 208;
	public final static float PPM = 90; //pixels per meter
	public SpriteBatch batch;
	
	
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		ScreenManager.getInstance().initialize(this);
		ScreenManager.getInstance().show(Screen.PLAYSCREEN);
	}
	
	/*
	 * */
	public void changeScreen(int selection){
		dispose();
		
		//select a new screen
		switch(selection){
			case 0:
				ScreenManager.getInstance().show(Screen.PLAYSCREEN);
				break;
			case 1:
				ScreenManager.getInstance().show(Screen.PLAYSCREEN2);
				break;
			case 2:
				ScreenManager.getInstance().show(Screen.PLAYSCREEN3);
				break;
			case 3:
				ScreenManager.getInstance().show(Screen.PLAYSCREEN4);
		}
		
		
	}

	@Override
	public void render () {
		super.render();
	}
	
	public void dispose(){
		super.dispose();
		try{
			ScreenManager.getInstance().dispose();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
	}
	

}
