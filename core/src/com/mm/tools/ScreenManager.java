package com.mm.tools;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.utils.IntMap;
import com.mm.main.MegaManGame;
import com.mm.screens.PlayScreen;

public final class ScreenManager {
	private static ScreenManager instance;
	private static Game game;
	private IntMap<com.badlogic.gdx.Screen> screens;
	
	
	
	private ScreenManager(){
		screens = new IntMap<com.badlogic.gdx.Screen>();
	}
	
	
	
	/**========================================================================
	 * 
	 *
	 *=========================================================================
	 */
	public enum Screen{
	
		PLAYSCREEN{
			@Override
			protected com.badlogic.gdx.Screen getScreenInstance(){
				//create settings for play screen to be loaded here
				PlayScreen ps1 = new PlayScreen((MegaManGame)game, "custom_A.tmx", 0);
				ps1.initPlayer(205, 25); //init player starting coordinates
				return ps1;
			}
		},
		PLAYSCREEN2{
			@Override
			protected com.badlogic.gdx.Screen getScreenInstance(){
				PlayScreen ps2 = new PlayScreen((MegaManGame)game, "custom_C.tmx", 1);
				ps2.initPlayer(15, 25);
				return ps2;
			}
		},
		PLAYSCREEN3{
			@Override
			protected com.badlogic.gdx.Screen getScreenInstance(){
				PlayScreen ps3 = new PlayScreen((MegaManGame)game, "custom_D.tmx", 2);
				ps3.initPlayer(15,  25);
				return ps3;
			}
		},
		PLAYSCREEN4{
			@Override
			protected com.badlogic.gdx.Screen getScreenInstance(){
				PlayScreen ps3 = new PlayScreen((MegaManGame)game, "mapC.tmx", 3);
				ps3.initPlayer(45,  25);
				return ps3;
			}
		};
		
		protected abstract com.badlogic.gdx.Screen getScreenInstance();
	}
	

	
	public static ScreenManager getInstance(){
		if(null == instance){
			instance = new ScreenManager();
		}
		return instance;
	}
	
	
	
	public void initialize(MegaManGame game){
		this.game = game;
	}
	
	
	
	public void show(Screen screen){
		if(null == game)return; //exit game
		if(!screens.containsKey(screen.ordinal())){
			screens.put(screen.ordinal(), (com.badlogic.gdx.Screen) screen.getScreenInstance());
		}
		game.setScreen((com.badlogic.gdx.Screen) screens.get(screen.ordinal()));
	}
	
	 public void dispose(Screen screen) {
	        if (!screens.containsKey(screen.ordinal())) return;
	        ((com.badlogic.gdx.Screen) screens.remove(screen.ordinal())).dispose();
	    }
	
	 //dispose of all screens
	 public void dispose() {
	        for (com.badlogic.gdx.Screen screen : screens.values()) {
	            ((com.badlogic.gdx.Screen) screen).dispose();
	        }
	        screens.clear();
	        instance = null;
	    } 
	
	
}
