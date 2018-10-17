package fr.clodo.arena.drawables

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3
import fr.clodo.arena.enums.ClodoScreen
import fr.clodo.arena.helper.ClodoWorld
import fr.clodo.arena.helper.Drawer
import fr.clodo.arena.screens.GameScreen

class UI : fr.clodo.arena.base.Drawable(x = startX, y = startY, height = height) {
    companion object {
        private const val startX = 400f
        private const val startY = 800f
        private const val height = 150f

        fun getUnprojectXOfShotBar(gameScreen: GameScreen) = gameScreen.camera.unproject(Vector3(Vector2(startX, startY), 0f)).x
    }


    override fun update(gameScreen: GameScreen, delta: Float) {
    }

    override fun draw(gameScreen: GameScreen, delta: Float) {
        if (ClodoWorld.currentScreen == ClodoScreen.IN_GAME_FIGHTING) {
            val v = gameScreen.camera.unproject(Vector3(Vector2(startX, startY), 0f))
            x = v.x
            y = v.y
            val y2 = gameScreen.camera.unproject(Vector3(Vector2(startX, startY + UI.height), 0f)).y
            Drawer.DrawDebugLine(Vector2(x, y), Vector2(x, y2), Color.WHITE, gameScreen.camera.combined)


            // HP BOSS
            var hpBoss1 = gameScreen.camera.unproject(Vector3(Vector2(1920f - 200f, 970f), 0f))
            val hpBoss2 = gameScreen.camera.unproject(Vector3(Vector2(1920f - 200 + (100f * ClodoWorld.currentHealthPourcent), 970f), 0f))
            Drawer.DrawDebugLine(Vector2(hpBoss1.x, hpBoss1.y), Vector2(hpBoss2.x, hpBoss2.y), Color.GREEN, gameScreen.camera.combined)

            // HP PLAYER
            var hpPlayer1 = gameScreen.camera.unproject(Vector3(Vector2(150f, 970f), 0f))
            val hpPlayer2 = gameScreen.camera.unproject(Vector3(Vector2(150f + (100f * ClodoWorld.currentPlayerHealthPourcent), 970f), 0f))
            Drawer.DrawDebugLine(Vector2(hpPlayer1.x, hpPlayer1.y), Vector2(hpPlayer2.x, hpPlayer2.y), Color.GREEN, gameScreen.camera.combined)
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