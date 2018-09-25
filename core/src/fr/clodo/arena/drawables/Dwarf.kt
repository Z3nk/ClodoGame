package fr.clodo.arena.drawables

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.*
import fr.clodo.arena.base.Clickable
import fr.clodo.arena.base.Drawable
import fr.clodo.arena.enums.ClodoScreen
import fr.clodo.arena.enums.HeroState
import fr.clodo.arena.helper.Animator
import fr.clodo.arena.helper.ClodoWorld
import fr.clodo.arena.screens.GameScreen
import fr.clodo.arena.virtual.Menu
import java.util.*
import kotlin.concurrent.timerTask

class Dwarf(val walkingAnimation: Animation<TextureRegion>, val idleingAnimation: Animation<TextureRegion>, val attackingAnimation: Animation<TextureRegion>) : Drawable(x = startX, y = startY, width = sizeX, height = sizeY), Clickable {

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
        private fun getWalkTexture() = Animator.generateArray("spritesheet.png", 0, 2, 0, 1, 67, 66, 202, 198)
        //private fun getWalkTexture() = Animator.generateArray("Nain.png", 0, 0, 0, 11, 128, 128, 1408, 128)

        private fun getIdleAnimation() = Animation(frameDuration, getIdleTexture(), Animation.PlayMode.LOOP)
        private fun getIdleTexture() = Animator.generateArray("spritesheet_idle.png", 0, 0, 0, 4, 50, 72, 202, 72)

        private fun getAttackAnimation() = Animation(frameDuration, getAttackTexture(), Animation.PlayMode.NORMAL)
        private fun getAttackTexture() = Animator.generateArray("spritesheet_attack.png", 0, 0, 0, 4, 100, 62, 400, 62)
    }

    private var stateTime: Float = 0f
    private var state = HeroState.IDLE

    init {
        sprite.setPosition(x, y)
        sprite.setSize(width, height)
    }

    override fun update(delta: Float) {
        stateTime += delta
        x = sprite.x
        y = sprite.y
        when (ClodoWorld.currentScreen) {
            ClodoScreen.LOBBY -> {
                state = HeroState.IDLE
            }
            ClodoScreen.IN_GAME_WAITING -> {
                state = HeroState.IDLE
            }
            ClodoScreen.IN_GAME_WALKING -> {
                state = HeroState.RUN
                x += (speedX * delta)
                sprite.setPosition(x, y)
            }
            ClodoScreen.IN_GAME_FIGHTING -> {
                if (state == HeroState.ATTACK && attackingAnimation.isAnimationFinished(stateTime)) {
                    state = HeroState.IDLE
                } else if (state == HeroState.DEAD) {
                    //TODO
                } else if (state == HeroState.RUN) {
                    state = HeroState.IDLE
                }
            }
            ClodoScreen.DEAD -> {
                state = HeroState.DEAD
            }
        }
    }


    override fun onClick(x: Float, y: Float) {
        if (isClickOnMe(x, y)) {
            stateTime = 0.0f
            state = HeroState.ATTACK
        }
    }

    override fun draw(batch: SpriteBatch, font: BitmapFont, delta: Float) {

        when (state) {
            HeroState.IDLE -> {
                sprite.setRegion(idleingAnimation.getKeyFrame(stateTime))
            }
            HeroState.RUN -> {
                sprite.setRegion(walkingAnimation.getKeyFrame(stateTime))
            }
            HeroState.ATTACK -> {
                sprite.setRegion(attackingAnimation.getKeyFrame(stateTime))
            }
            HeroState.DEAD -> {
                sprite.setRegion(idleingAnimation.getKeyFrame(stateTime))
            }
        }

        sprite.draw(batch)
    }

    override fun dispose() {
        sprite.texture.dispose()
    }

}