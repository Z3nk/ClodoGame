package fr.clodo.arena.drawables

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.*
import fr.clodo.arena.base.Drawable
import fr.clodo.arena.enums.ClodoScreen
import fr.clodo.arena.helper.Animator
import fr.clodo.arena.helper.ClodoWorld
import fr.clodo.arena.screens.GameScreen

class Dwarf(val walkingAnimation: Animation<TextureRegion>) : Drawable() {

    companion object {
        const val TAG = "Dwarf"
        fun createDwarf(): Dwarf {
            return Dwarf(getWalkAnimation())
        }

        private const val frameDuration = 0.25f
        private const val sizeX = 64f
        private const val sizeY = 64f
        private const val startX = 20f
        private const val startY = 100f
        const val speedX = 300f

        private fun getWalkAnimation() = Animation(frameDuration, getWalkTexture(), Animation.PlayMode.LOOP)
        private fun getWalkTexture() = Animator.generateArray("Nain.png", 0, 0, 0, 11, 128, 128, 1408, 128)
    }

    private var stateTime: Float = 0f

    init {
        sprite.setPosition(startX, startY)
        sprite.setSize(sizeX , sizeY )
    }

    override fun update(delta: Float) {
        stateTime += delta
        when(ClodoWorld.currentScreen){
            ClodoScreen.IN_GAME_WALKING -> {
                val fl = speedX * delta
                sprite.setPosition(sprite.x + fl, sprite.y)
//                Gdx.app.log(TAG, "Dwarf avance a la vitesse de $speedX et un delta $delta pour un total de $fl")
//                Gdx.app.log(TAG, "Position = ${sprite.x + fl}")
            }
        }
    }

    override fun draw(batch: SpriteBatch, font: BitmapFont, delta: Float) {
        sprite.setRegion(walkingAnimation.getKeyFrame(stateTime))
        sprite.draw(batch)
    }

    override fun dispose() {
        sprite.texture.dispose()
    }

}