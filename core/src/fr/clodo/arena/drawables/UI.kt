package fr.clodo.arena.drawables

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Camera
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.scenes.scene2d.utils.Drawable
import fr.clodo.arena.base.Clickable
import fr.clodo.arena.enums.ClodoScreen
import fr.clodo.arena.helper.ClodoWorld
import fr.clodo.arena.helper.Drawer
import fr.clodo.arena.screens.GameScreen

class UI : fr.clodo.arena.base.Drawable(x = startX, y = startY) {
    companion object {
        private const val startX = 400f
        private const val startY = 850f
        private const val height = 150f

        fun getUnprojectX(gameScreen: GameScreen) = gameScreen.camera.unproject(Vector3(Vector2(startX, startY), 0f)).x
    }


    override fun update(gameScreen: GameScreen, delta: Float) {
    }

    override fun draw(gameScreen: GameScreen, delta: Float) {
        if (ClodoWorld.currentScreen == ClodoScreen.IN_GAME_FIGHTING) {
            val v = gameScreen.camera.unproject(Vector3(Vector2(startX, startY), 0f))
            x = v.x
            y = v.y
            val y2 = gameScreen.camera.unproject(Vector3(Vector2(startX, startY + UI.height), 0f)).y
            Drawer.DrawDebugLine(Vector2(x, y), Vector2(x, y2), gameScreen.camera.combined)
        }

//        for(y in 0..1080 step 100){
//            var v1 = gameScreen.camera.unproject(Vector3(Vector2(0.0f, y+0f), 0f))
//            var v2 = gameScreen.camera.unproject(Vector3(Vector2(1920.0f, y+0f), 0f))
//            Drawer.DrawDebugLine(Vector2(v1.x, v1.y), Vector2(v2.x, v2.y), gameScreen.camera.combined)
//
//        }
//        for(x in 0..1920 step 100){
//            var v1 = gameScreen.camera.unproject(Vector3(Vector2(x+0.0f, 0f), 0f))
//            var v2 = gameScreen.camera.unproject(Vector3(Vector2(x+0.0f, 1080f), 0f))
//            Drawer.DrawDebugLine(Vector2(v1.x, v1.y), Vector2(v2.x, v2.y), gameScreen.camera.combined)
//
//        }
    }

    override fun dispose() {
    }

}