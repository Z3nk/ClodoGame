package fr.clodo.arena.drawables

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import fr.clodo.arena.base.Drawable

class Background(spriteSheet: String) : Drawable(x = 0f, y = 0f, width = 1927f, height = 1081f, spriteSheet = spriteSheet) {
    companion object {
        fun createNightBackground(): Background {
            return Background("background_night.png")
        }
    }


    init {
        sprite = Sprite(Texture(spriteSheet))
        sprite.setSize(width, height)
    }

    override fun update(delta: Float) {
    }

    override fun draw(batch: SpriteBatch, font: BitmapFont, delta: Float) {
        sprite.draw(batch)
    }

    override fun dispose() {
        sprite.texture.dispose()
    }

}