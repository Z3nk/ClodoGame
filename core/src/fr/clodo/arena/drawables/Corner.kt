package fr.clodo.arena.drawables

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3
import fr.clodo.arena.base.Drawable
import fr.clodo.arena.enums.ClodoScreen
import fr.clodo.arena.helper.ClodoWorld
import fr.clodo.arena.screens.GameScreen

class Corner : Drawable(width = 200f, height = 1087f) {
    var leftCorner = Background("left_click.png", 0f, 0f, 50f, 1087f)
    var rightCorner = Background("right_click.png", 1920f, 0f, 50f, 1087f)

    override fun update(gameScreen: GameScreen, delta: Float) {
        val v = gameScreen.camera.unproject(Vector3(Vector2(leftCorner.x, 1080f), 0f))
        leftCorner.sprite.setPosition(v.x, v.y)

        val v2 = gameScreen.camera.unproject(Vector3(Vector2(rightCorner.x, 1080f), 0f))
        rightCorner.sprite.setPosition(v2.x - rightCorner.width, v2.y)
    }

    override fun draw(gameScreen: GameScreen, delta: Float) {
        if (ClodoWorld.currentScreen == ClodoScreen.IN_GAME_FIGHTING) {
            leftCorner.draw(gameScreen, delta)
            rightCorner.draw(gameScreen, delta)
        }
    }

    override fun dispose() {
        leftCorner.dispose()
        rightCorner.dispose()
    }

}