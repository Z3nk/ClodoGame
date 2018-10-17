package fr.clodo.arena.drawables

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.*
import fr.clodo.arena.base.Clickable
import fr.clodo.arena.base.Drawable
import fr.clodo.arena.enums.ClodoScreen
import fr.clodo.arena.enums.CharacterState
import fr.clodo.arena.helper.Animator
import fr.clodo.arena.helper.ClodoWorld
import fr.clodo.arena.screens.GameScreen

class Dwarf(val walkingAnimation: Animation<TextureRegion>, val idleingAnimation: Animation<TextureRegion>, val attackingAnimation: Animation<TextureRegion>, val hitAnimation: Animation<TextureRegion>, val deadAnimation: Animation<TextureRegion>) : Drawable(x = startX, y = startY, width = sizeX, height = sizeY), Clickable {

    companion object {
        const val TAG = "Dwarf"
        fun createDwarf(): Dwarf {
            return Dwarf(getWalkAnimation(), getIdleAnimation(), getAttackAnimation(), getHitAnimation(), getDeadAnimation())
        }

        private const val frameDuration = 0.15f
        private const val sizeX = 64f
        private const val sizeY = 64f
        private const val startX = 20f
        private const val startY = 120f
        const val speedX = 300f

        private val walkTexture = Texture("spritesheet.png")
        private val idleTexture = Texture("spritesheet_idle.png")
        private val hitTexture = Texture("spritesheet_hit.png")
        private val attackTexture = Texture("spritesheet_attack.png")
        private val deadTexture = Texture("spritesheet_dead.png")

        private fun getWalkAnimation() = Animation(frameDuration, getWalkTexture(), Animation.PlayMode.LOOP)
        private fun getWalkTexture() = Animator.generateArray(walkTexture, 0, 2, 0, 1, 67, 66, 202, 198)
        //private fun getWalkTexture() = Animator.generateArray("Nain.png", 0, 0, 0, 11, 128, 128, 1408, 128)

        private fun getIdleAnimation() = Animation(frameDuration, getIdleTexture(), Animation.PlayMode.LOOP)
        private fun getIdleTexture() = Animator.generateArray(idleTexture, 0, 0, 0, 4, 50, 72, 202, 72)
        //private fun getWalkTexture() = Animator.generateArray("Nain.png", 0, 0, 0, 11, 128, 128, 1408, 128)

        private fun getHitAnimation() = Animation(frameDuration, getHitTexture(), Animation.PlayMode.LOOP)
        private fun getHitTexture() = Animator.generateArray(hitTexture, 0, 0, 0, 4, 50, 72, 202, 72)

        private fun getAttackAnimation() = Animation(frameDuration, getAttackTexture(), Animation.PlayMode.NORMAL)
        private fun getAttackTexture() = Animator.generateArray(attackTexture, 0, 0, 0, 4, 100, 62, 400, 62)

        private fun getDeadAnimation() = Animation(frameDuration, getDeadTexture(), Animation.PlayMode.LOOP)
        private fun getDeadTexture() = Animator.generateArray(deadTexture, 0, 1, 0, 2, 67, 99, 202, 198)
    }

    private var health = 20f
    private val startHealth: Int
    private var stateTime: Float = 0f
    private var state = CharacterState.IDLE
    private var alive = true

    init {
        health = 100f
        startHealth = 100
        sprite.setPosition(x, y)
        sprite.setSize(width, height)
    }

    override fun update(gameScreen: GameScreen, delta: Float) {
        stateTime += delta
        x = sprite.x
        y = sprite.y
        when (ClodoWorld.currentScreen) {
            ClodoScreen.LOBBY -> {
                stateTime = 0.0f
                state = CharacterState.IDLE
            }
            ClodoScreen.IN_GAME_WAITING -> {
                stateTime = 0.0f
                state = CharacterState.IDLE
            }
            ClodoScreen.IN_GAME_WALKING -> {
                state = CharacterState.RUN
                x += (speedX * delta)
                sprite.setPosition(x, y)
            }
            ClodoScreen.IN_GAME_FIGHTING -> {
                if ((state == CharacterState.ATTACK && attackingAnimation.isAnimationFinished(stateTime)) || (state == CharacterState.HIT && hitAnimation.isAnimationFinished(stateTime))) {
                    stateTime = 0.0f
                    state = CharacterState.IDLE
                } else if (state == CharacterState.DEAD && deadAnimation.isAnimationFinished(stateTime)) {
                    alive = false
                } else if (state == CharacterState.RUN) {
                    stateTime = 0.0f
                    state = CharacterState.IDLE
                }
            }
            ClodoScreen.DEAD -> {
                stateTime = 0.0f
                state = CharacterState.DEAD
            }
        }
    }


    override fun onClick(x: Float, y: Float) {
        if (state != CharacterState.DEAD) {
            stateTime = 0.0f
            state = CharacterState.ATTACK
        }
    }

    override fun draw(gameScreen: GameScreen, delta: Float) {

        when (state) {
            CharacterState.IDLE -> {
                sprite.setRegion(idleingAnimation.getKeyFrame(stateTime))
            }
            CharacterState.RUN -> {
                sprite.setRegion(walkingAnimation.getKeyFrame(stateTime))
            }
            CharacterState.HIT -> {
                sprite.setRegion(hitAnimation.getKeyFrame(stateTime))
            }
            CharacterState.ATTACK -> {
                sprite.setRegion(attackingAnimation.getKeyFrame(stateTime))
            }
            CharacterState.DEAD -> {
                sprite.setRegion(deadAnimation.getKeyFrame(stateTime))
            }
        }

        if (alive) {
            sprite.draw(gameScreen.batch)
        }
    }

    override fun dispose() {
        sprite.texture.dispose()
    }

    fun isHit() {
        if (state != CharacterState.DEAD) {
            stateTime = 0.0f
            state = CharacterState.HIT
            health -= 20f
            if (health <= 0) {
                ClodoWorld.currentPlayerHealthPourcent = 0f
                state = CharacterState.DEAD
                stateTime = 0.0f
            } else {
                ClodoWorld.currentPlayerHealthPourcent = health / startHealth
            }
        }
    }

}