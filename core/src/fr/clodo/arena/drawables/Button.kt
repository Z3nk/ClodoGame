package fr.clodo.arena.drawables

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import fr.clodo.arena.base.Clickable
import fr.clodo.arena.base.Drawable
import fr.clodo.arena.helper.EntitiesAnimator
import java.util.*

class Button(x: Float, y: Float, spriteSheet: String, width: Float, height: Float, private val entitiesAnimator: EntitiesAnimator, private val callback: () -> Unit) : Drawable(x = x, y = y, width = width, spriteSheet = spriteSheet, height = height), Clickable {

    companion object {
        const val TAG = "Button"

        fun createPlayButton(x: Float, y: Float, callback: () -> Unit): Button {
            Gdx.app.log(TAG, "[PLAY]")
            val width = 400f
            val height = 200f
            val list = LinkedList<Pair<Float, EntitiesAnimator.Movement>>()
            list.add(Pair(0.4f, EntitiesAnimator.Movement(0f, -100f)))
            list.add(Pair(3f, EntitiesAnimator.Movement(0f, 1000f)))
            return Button(x - width / 2, y - height / 2, "play_normal.png", width, height, EntitiesAnimator(list), callback)
        }
    }


    private var isAnimed = false
    private var isAlive = true

    init {
        sprite = Sprite(Texture(spriteSheet))
        sprite.setSize(width, height)
        sprite.setPosition(x, y)
    }

    override fun onClick(x: Float, y: Float) {
        if (isClickOnMe(x, y)) {
            callback()
            isAnimed = true
        }
    }


    override fun update(delta: Float) {
        if (isAnimed && isAlive) {
            val movement = entitiesAnimator.getMovement(delta)
            if (movement == null) {
                isAlive = false
                return
            } else {
                sprite.setPosition(sprite.x + movement.speedX * delta, sprite.y + movement.speedY * delta)
            }
        }
    }

    override fun draw(batch: SpriteBatch, font: BitmapFont, delta: Float) {
        if (isAlive) {
            sprite.draw(batch)
        }
    }

    override fun dispose() {
        sprite.texture.dispose()
    }
}