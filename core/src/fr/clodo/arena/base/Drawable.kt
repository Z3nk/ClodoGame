package fr.clodo.arena.base

import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch

abstract class Drawable(var x: Float = 0f, var y: Float= 0f, spriteSheet: String? = null, val width: Float= 0f, val height: Float= 0f) {
    var sprite: Sprite = Sprite()

    fun isClickOnMe(x: Float, y: Float) = x > this.x && x < this.x + this.width && y > this.y && y < this.y + this.height
    abstract fun update(delta: Float)
    abstract fun draw(batch: SpriteBatch, font: BitmapFont, delta: Float)
    abstract fun dispose()
}