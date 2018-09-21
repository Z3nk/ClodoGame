package fr.clodo.arena.base

import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch

interface Drawable {
    fun update(delta: Float)
    fun draw(batch: SpriteBatch, font: BitmapFont, delta: Float)
    fun dispose()
}