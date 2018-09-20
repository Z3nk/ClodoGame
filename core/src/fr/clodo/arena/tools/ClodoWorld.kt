package fr.clodo.arena.tools

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.math.Vector3

abstract class ClodoWorld {
    companion object {
        const val WORLD_WIDTH = 1920
        const val WORLD_HEIGHT = 1080
        @JvmField
        var ratioX = Gdx.graphics.width.toFloat() / WORLD_WIDTH
        @JvmField
        var ratioY = Gdx.graphics.height.toFloat() / WORLD_HEIGHT

        var lastViewportWidth = WORLD_WIDTH + 0.0f
        var lastViewportHeight = WORLD_HEIGHT + 0.0f
        var lastPosition = Vector3(WORLD_WIDTH / 2f, WORLD_HEIGHT / 2f, 0f)
    }
}