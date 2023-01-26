package com.nopalsoft.dragracer.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.nopalsoft.dragracer.Assets;
import com.nopalsoft.dragracer.MainStreet;
import com.nopalsoft.dragracer.Settings;
import com.nopalsoft.dragracer.game.GameScreen;
import com.nopalsoft.dragracer.shop.ShopScreen;

public class MainMenuScreen extends Screens {

    Image titulo;

    Label lbTienda;
    Label lbJugar;
    Label lbTOP10;
    Label lbInstrucciones;

    Button btMusica;

    public MainMenuScreen(final MainStreet game) {
        super(game);

        titulo = new Image(Assets.titulo);
        titulo.setPosition(SCREEN_WIDTH / 2f - titulo.getWidth() / 2f, 520);
        titulo.getColor().a = 0;
        titulo.addAction(Actions.sequence(Actions.fadeIn(.5f),
                Actions.run(new Runnable() {

                    @Override
                    public void run() {
                        stage.addActor(lbJugar);
                        stage.addActor(lbInstrucciones);
                        stage.addActor(lbTOP10);
                        stage.addActor(lbTienda);
                        stage.addActor(btMusica);
                    }
                })));

        lbJugar = new Label("Jugar", Assets.labelStyleGrande);
        lbJugar.setPosition(500, 575);
        lbJugar.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                changeScreenWithFadeOut(GameScreen.class, game);
            }
        });

        lbInstrucciones = new Label("Instrucciones", Assets.labelStyleGrande);
        lbInstrucciones.setPosition(500, 275);
        lbInstrucciones.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.reqHandler.showRater();
            }
        });

        lbTienda = new Label("Tienda", Assets.labelStyleGrande);
        lbTienda.setPosition(500, 425);
        lbTienda.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                changeScreenWithFadeOut(ShopScreen.class, game);
            }
        });

        lbTOP10 = new Label("TOP 10", Assets.labelStyleGrande);
        lbTOP10.setPosition(500, 125);
        lbTOP10.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (game.gameServiceHandler.isSignedIn())
                    game.gameServiceHandler.getLeaderboard();
                else
                    game.gameServiceHandler.signIn();
            }
        });

        btMusica = new Button(Assets.styleButtonMusica);
        btMusica.setPosition(5, 5);
        btMusica.setChecked(!Settings.isMusicOn);
        Gdx.app.log("Musica", Settings.isMusicOn + "");
        btMusica.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Settings.isMusicOn = !Settings.isMusicOn;
                btMusica.setChecked(!Settings.isMusicOn);
                if (Settings.isMusicOn)
                    Assets.music.play();
                else
                    Assets.music.stop();
                super.clicked(event, x, y);
            }
        });

        entranceAction(lbJugar, lbJugar.getY(), .25f);
        entranceAction(lbInstrucciones, lbInstrucciones.getY(), .5f);
        entranceAction(lbTienda, lbTienda.getY(), .75f);
        entranceAction(lbTOP10, lbTOP10.getY(), 1f);

        setAnimationChangeColor(lbTienda);
        setAnimationChangeColor(lbInstrucciones);
        setAnimationChangeColor(lbTOP10);
        setAnimationChangeColor(lbJugar);

        stage.addActor(titulo);

    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void draw(float delta) {
        batcher.begin();
        batcher.draw(Assets.calle, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT * 2);
        batcher.end();
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Keys.ESCAPE || keycode == Keys.BACK)
            Gdx.app.exit();
        return true;

    }

}
