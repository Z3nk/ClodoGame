package fr.clodo.arena.tools

import com.badlogic.gdx.Gdx

abstract class ClodoWorld{
    companion object {
        const val WORLD_WIDTH = 1920
        const val WORLD_HEIGHT = 1080
        @JvmField
        var ratioX = Gdx.graphics.width.toFloat() / WORLD_WIDTH
        @JvmField
        var ratioY = Gdx.graphics.height.toFloat() / WORLD_HEIGHT
    }
}