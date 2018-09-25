package fr.clodo.arena.drawables

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.*
import fr.clodo.arena.base.Drawable
import fr.clodo.arena.enums.ClodoScreen
import fr.clodo.arena.helper.Animator
import fr.clodo.arena.helper.ClodoWorld
import fr.clodo.arena.screens.GameScreen

class Dwarf(val walkingAnimation: Animation<TextureRegion>, val idleingAnimation: Animation<TextureRegion>, val attackingAnimation: Animation<TextureRegion>) : Drawable() {

    companion object {
        const val TAG = "Dwarf"
        fun createDwarf(): Dwarf {
            return Dwarf(getWalkAnimation(), getIdleAnimation(), getAttackAnimation())
        }

        private const val frameDuration = 0.15f
        private const val sizeX = 64f
        private const val sizeY = 64f
        private const val startX = 20f
        private const val startY = 100f
        const val speedX = 300f

        private fun getWalkAnimation() = Animation(frameDuration, getWalkTexture(), Animation.PlayMode.LOOP)
        private fun getWalkTexture() = Animator.generateArray("spritesheet.png", 0, 2, 0, 1, 68, 67, 202, 198)
        //private fun getWalkTexture() = Animator.generateArray("Nain.png", 0, 0, 0, 11, 128, 128, 1408, 128)

        private fun getIdleAnimation() = Animation(frameDuration, getIdleTexture(), Animation.PlayMode.LOOP)
        private fun getIdleTexture() = Animator.generateArray("spritesheet_idle.png", 0, 0, 0, 4, 50, 72, 202, 72)

        private fun getAttackAnimation() = Animation(frameDuration, getAttackTexture(), Animation.PlayMode.LOOP)
        private fun getAttackTexture() = Animator.generateArray("spritesheet_attack.png", 0, 0, 0, 4, 100, 62, 400, 62)
    }

    private var stateTime: Float = 0f

    init {
        sprite.setPosition(startX, startY)
        sprite.setSize(sizeX, sizeY)
    }

    override fun update(delta: Float) {
        stateTime += delta
        when (ClodoWorld.currentScreen) {
            ClodoScreen.LOBBY -> {
            }
            ClodoScreen.IN_GAME_WAITING -> {
            }
            ClodoScreen.IN_GAME_WALKING -> {
                val fl = speedX * delta
                sprite.setPosition(sprite.x + fl, sprite.y)
//                Gdx.app.log(TAG, "Dwarf avance a la vitesse de $speedX et un delta $delta pour un total de $fl")
//                Gdx.app.log(TAG, "Position = ${sprite.x + fl}")
            }
            ClodoScreen.IN_GAME_FIGHTING -> {
            }
            ClodoScreen.DEAD -> {
            }
        }
    }

    override fun draw(batch: SpriteBatch, font: BitmapFont, delta: Float) {
        when (ClodoWorld.currentScreen) {

            ClodoScreen.LOBBY -> {
                //TODO
                sprite.setRegion(idleingAnimation.getKeyFrame(stateTime))
            }
            ClodoScreen.IN_GAME_WAITING -> {
                sprite.setRegion(idleingAnimation.getKeyFrame(stateTime))
            }
            ClodoScreen.IN_GAME_WALKING -> {
                sprite.setRegion(walkingAnimation.getKeyFrame(stateTime))
            }
            ClodoScreen.IN_GAME_FIGHTING -> {
                //TODO
                sprite.setRegion(idleingAnimation.getKeyFrame(stateTime))
            }
            ClodoScreen.DEAD -> {
                //TODO
                sprite.setRegion(idleingAnimation.getKeyFrame(stateTime))
            }
        }

        sprite.draw(batch)
    }

    override fun dispose() {
        sprite.texture.dispose()
    }

}