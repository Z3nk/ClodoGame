package fr.clodo.arena.drawables

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.scenes.scene2d.ui.Dialog
import fr.clodo.arena.base.Clickable
import fr.clodo.arena.base.Drawable
import fr.clodo.arena.screens.GameScreen

class Narrator(): Drawable(x = 0f, y = 0f, width = 0f, height = 0f), Clickable{
    companion object {
        const val TAG = "Narrator"
    }

    override fun update(gameScreen: GameScreen, delta: Float) {
    }

    override fun draw(gameScreen: GameScreen, delta: Float) {
        gameScreen.font.draw(gameScreen.batch, "Hello world", 200f, 200f)
    }

    override fun dispose() {
    }

    override fun onClick(x: Float, y: Float) {
    }

}