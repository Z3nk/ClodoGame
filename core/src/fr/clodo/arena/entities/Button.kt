package fr.clodo.arena.entities

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import fr.clodo.arena.base.Drawable
import fr.clodo.arena.tools.EntitiesAnimator
import java.util.*

class Button(val x: Float, val y: Float, spriteSheet: String, val width: Float, val height: Float, val entitiesAnimator: EntitiesAnimator, val callback: () -> Unit) : Drawable {

    companion object {
        fun createPlayButton(x: Float, y: Float, callback: () -> Unit): Button {
            val width = 400f
            val height = 200f
            val list = LinkedList<Pair<Float, EntitiesAnimator.Movement>>()
            list.add(Pair(0.4f, EntitiesAnimator.Movement(0f, -100f)))
            list.add(Pair(3f, EntitiesAnimator.Movement(0f, 1000f)))
            return Button(x - width / 2, y - height / 2, "play_normal.png", width, height, EntitiesAnimator(list), callback)
        }
    }

    private val sprite: Sprite = Sprite(Texture(spriteSheet))
    private var isAnimed = false

    init {
        sprite.setSize(width, height)
        sprite.setPosition(x, y)
    }

    fun onClick(x: Float, y: Float) {
        if (x > this.x && x < this.x + this.width && y > this.y && y < this.y + this.height) {
            callback()
            isAnimed = true
        }
    }

    override fun update(delta: Float) {
        if (isAnimed) {
            val movement = entitiesAnimator.getMovement(delta)?:return
            sprite.setPosition(sprite.x + movement.speedX*delta, sprite.y + movement.speedY*delta)
        }
    }

    override fun draw(batch: SpriteBatch, delta: Float) {
        update(delta)
        sprite.draw(batch)
    }

    override fun dispose() {
        sprite.texture.dispose()
    }
}