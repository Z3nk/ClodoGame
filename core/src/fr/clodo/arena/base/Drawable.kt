package fr.clodo.arena.base

import com.badlogic.gdx.graphics.g2d.SpriteBatch

interface Drawable {
    fun update()
    fun draw(batch: SpriteBatch)
    fun dispose()
}