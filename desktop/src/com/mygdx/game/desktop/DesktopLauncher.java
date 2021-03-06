package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.Mola;
import controller.AppClient;
import model.Initializer;
import org.json.simple.parser.ParseException;

import java.io.IOException;

public class DesktopLauncher {
	public static void main (String[] arg) throws IOException, ParseException {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 1600;
		config.height = 960;
		AppClient.initializeNetwork();
		Initializer.initialize();
		new LwjglApplication(new Mola(), config);
	}
}
