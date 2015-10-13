package com.mm.main.desktop;

import java.awt.Dimension;
import java.awt.Toolkit;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mm.main.MegaManGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		config.height = (int)screenSize.height;
		config.width = (int)screenSize.width / 2; //remove the "/ 2" if you are only using 1 monitor.
		config.fullscreen = true;
		config.vSyncEnabled = true;

		new LwjglApplication(new MegaManGame(), config);
	}
}
