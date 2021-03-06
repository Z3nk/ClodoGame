package fr.clodo.arena.entities

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import fr.clodo.arena.base.Drawable
import fr.clodo.arena.tools.ClodoWorld

class Background(spriteSheet: String) : Drawable {
    companion object {
        fun createNightBackground(): Background {
            return Background("background_night.png")
        }
    }

    private val sprite: Sprite = Sprite(Texture(spriteSheet))

    init {
        sprite.setSize(1927 * ClodoWorld.ratioX, 1081 * ClodoWorld.ratioY)
    }

    override fun update(delta: Float) {
    }

    override fun draw(batch: SpriteBatch, delta: Float) {
        update(delta)
        sprite.draw(batch)
    }

    override fun dispose() {
        sprite.texture.dispose()
    }

}