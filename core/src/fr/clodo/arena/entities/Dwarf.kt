package fr.clodo.arena.entities

import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import fr.clodo.arena.base.Drawable
import fr.clodo.arena.tools.Animator
import fr.clodo.arena.tools.ClodoWorld

class Dwarf(val walkingAnimation: Animation<TextureRegion>) : Drawable {

    companion object {
        fun createDwarf(): Dwarf {
            return Dwarf(getWalkAnimation())
        }

        private const val frameDuration = 0.25f
        private const val sizeX = 64f
        private const val sizeY = 64f
        private const val startX = 50f
        private const val startY = 100f

        private fun getWalkAnimation() = Animation(frameDuration, getWalkTexture(), Animation.PlayMode.LOOP)
        private fun getWalkTexture() = Animator.generateArray("Nain.png", 0, 0, 0, 11, 128, 128, 1408, 128)
    }

    private val sprite: Sprite = Sprite()
    private var stateTime: Float = 0f

    init {
        sprite.setPosition(startX, startY)
        sprite.setSize(sizeX , sizeY )
    }

    override fun update(delta: Float) {
        stateTime += delta
    }

    override fun draw(batch: SpriteBatch, delta: Float) {
        update(delta)
        sprite.setRegion(walkingAnimation.getKeyFrame(stateTime))
        sprite.draw(batch)
    }

    override fun dispose() {
        sprite.texture.dispose()
    }

}