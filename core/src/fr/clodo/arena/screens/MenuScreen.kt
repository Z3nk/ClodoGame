package fr.clodo.arena.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import fr.clodo.arena.ClodoArenaGame
import fr.clodo.arena.entities.Background
import fr.clodo.arena.entities.ClodoWorld

class MenuScreen(val game: ClodoArenaGame): Screen {
    private val background = Background("background_night.png")

    override fun hide() {
    }

    override fun show() {

    }

    override fun render(delta: Float) {
        background.draw(game.batch)
    }

    override fun pause() {
    }

    override fun resume() {
    }

    override fun resize(width: Int, height: Int) {
    }

    override fun dispose() {
       background.dispose()
    }
}