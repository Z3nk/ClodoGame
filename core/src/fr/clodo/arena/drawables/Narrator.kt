package fr.clodo.arena.drawables

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.scenes.scene2d.ui.Dialog
import fr.clodo.arena.base.Clickable
import fr.clodo.arena.base.Drawable
import fr.clodo.arena.screens.GameScreen

class Narrator(): Drawable(x = 0f, y = 0f, width = 0f, height = 0f), Clickable{
    companion object {
        const val TAG = "Narrator"
    }

    init{
        sprite = Sprite(Texture("tutorial2.png"))
    }
    override fun update(gameScreen: GameScreen, delta: Float) {
        val v = gameScreen.camera.unproject(Vector3(Vector2(0f, 1080f), 0f))
        sprite.setPosition(v.x, v.y)
    }

    override fun draw(gameScreen: GameScreen, delta: Float) {
        //gameScreen.font.draw(gameScreen.batch, "Hello world", 200f, 200f)
        sprite.draw(gameScreen.batch)
    }

    override fun dispose() {
        sprite.texture.dispose()
    }

    override fun onClick(x: Float, y: Float) {
    }

}