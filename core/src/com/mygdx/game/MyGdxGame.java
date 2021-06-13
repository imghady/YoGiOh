package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import view.graphicalmenu.Start;

public class MyGdxGame extends Game implements Screen {
	public SpriteBatch batch;
	public BitmapFont font;
	public static Music music;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		font = new BitmapFont();
		music = Gdx.audio.newMusic(Gdx.files.internal("music.mp3"));
		music.setLooping(true);
		this.setScreen(new Start(this, true));

	}

	@Override
	public void render () {
		super.render();
	}

	@Override
	public void show() {

	}

	@Override
	public void render(float delta) {

	}

	@Override
	public void hide() {

	}

	@Override
	public void dispose () {
		batch.dispose();
		font.dispose();
	}
}
