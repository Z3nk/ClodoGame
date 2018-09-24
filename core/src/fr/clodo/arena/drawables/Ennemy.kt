package fr.clodo.arena.drawables

import com.badlogic.gdx.graphics.g2d.*
import fr.clodo.arena.base.Drawable
import fr.clodo.arena.helper.Animator

class Ennemy(val walkingAnimation: Animation<TextureRegion>, val level: Int, startX: Float, startY: Float) : Drawable() {

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
        sprite.setPosition(startX, startY)
        sprite.setSize(sizeX, sizeY)
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