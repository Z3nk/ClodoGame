package fr.clodo.arena.base

import com.badlogic.gdx.graphics.g2d.SpriteBatch

interface Drawable {
    fun update(delta: Float)
    fun draw(batch: SpriteBatch, delta: Float)
    fun dispose()
}