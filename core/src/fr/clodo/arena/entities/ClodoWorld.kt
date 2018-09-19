package fr.clodo.arena.entities

import com.badlogic.gdx.Gdx

class ClodoWorld(){
    companion object {

        const val WORLD_WIDTH = 1920
        const val WORLD_HEIGHT = 1080
        @JvmField
        var ratioX = Gdx.graphics.width.toFloat() / ClodoWorld.WORLD_WIDTH
        @JvmField
        var ratioY = Gdx.graphics.height.toFloat() / ClodoWorld.WORLD_HEIGHT
    }
}