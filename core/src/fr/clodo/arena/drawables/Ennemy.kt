package fr.clodo.arena.drawables

import com.badlogic.gdx.graphics.g2d.*
import fr.clodo.arena.base.Drawable
import fr.clodo.arena.helper.Animator

class Ennemy(private val walkingAnimation: Animation<TextureRegion>, val level: Int, startX: Float, startY: Float) : Drawable(x = startX, y = startY, width = sizeX, height = sizeY) {

    companion object {
        fun createEnnemy(level: Int, startX: Float, startY: Float): Ennemy {
            return Ennemy(getWalkAnimation(), level, startX, startY)
        }

        private const val frameDuration = 0.25f
        private const val sizeX = 48f
        private const val sizeY = 48f

        private fun getWalkAnimation() = Animation(frameDuration, getWalkTexture(), Animation.PlayMode.LOOP)
        private fun getWalkTexture() = Animator.generateArray("bandit.png", 0, 0, 4, 8, 48, 48, 384, 336)
    }

    private var stateTime: Float = 0f

    init {
        sprite.setPosition(x, y)
        sprite.setSize(width, height)
    }

    override fun update(delta: Float) {
        stateTime += delta
    }

    override fun draw(batch: SpriteBatch, font: BitmapFont, delta: Float) {
        update(delta)
        sprite.setRegion(walkingAnimation.getKeyFrame(stateTime))
        sprite.draw(batch)
    }

    override fun dispose() {
        sprite.texture.dispose()
    }

}