package fr.clodo.arena.drawables

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import fr.clodo.arena.base.Drawable
import fr.clodo.arena.screens.GameScreen

class Background(spriteSheet: String, x: Float = 0f, y: Float = 0f, width: Float = 1927f, height: Float = 1081f) : Drawable(x = x, y = y, width = width, height = height, spriteSheet = spriteSheet) {
    companion object {
        fun createNightBackground(x: Float, y: Float): Background {
            return Background("background_night.png", x, y)
        }
    }


    init {
        sprite = Sprite(Texture(spriteSheet))
        sprite.setPosition(x,y)
        sprite.setSize(width, height)
    }

    override fun update(gameScreen: GameScreen, delta: Float) {
    }

    override fun draw(gameScreen: GameScreen, delta: Float) {
        sprite.draw(gameScreen.batch)
    }

    override fun dispose() {
        sprite.texture.dispose()
    }

}