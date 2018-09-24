package fr.clodo.arena.base

import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch

abstract class Drawable {
    var sprite: Sprite = Sprite()
    abstract fun update(delta: Float)
    abstract fun draw(batch: SpriteBatch, font: BitmapFont, delta: Float)
    abstract fun dispose()
}