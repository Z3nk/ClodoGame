package fr.clodo.arena.entities

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import fr.clodo.arena.base.Drawable

class Background(img: String): Drawable{


    val sprite: Sprite = Sprite(Texture(img))
    init{
        sprite.setSize(1927 * ClodoWorld.ratioX, 1081 * ClodoWorld.ratioY)
    }

    override fun update() {
    }

    override fun draw(batch: SpriteBatch) {
        sprite.draw(batch)
    }

    override fun dispose() {
        sprite.texture.dispose()
    }

}