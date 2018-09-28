package fr.clodo.arena.drawables

import com.badlogic.gdx.graphics.Camera
import com.badlogic.gdx.graphics.g2d.*
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3
import fr.clodo.arena.base.Drawable
import fr.clodo.arena.enums.BulletType
import fr.clodo.arena.enums.ClodoScreen
import fr.clodo.arena.enums.CharacterState
import fr.clodo.arena.helper.Animator
import fr.clodo.arena.helper.BulletAnimator
import fr.clodo.arena.helper.ClodoWorld
import fr.clodo.arena.helper.LevelGenerator
import fr.clodo.arena.screens.GameScreen

class Ennemy(private val idleAnimation: Animation<TextureRegion>, private val attackingAnimation: Animation<TextureRegion>, private val hitAnimation: Animation<TextureRegion>, val level: Int, startX: Float, startY: Float, private val hasHitDwarf: () -> Unit) : Drawable(x = startX, y = startY, width = sizeX, height = sizeY) {

    companion object {
        fun createEnnemy(level: Int, startX: Float, startY: Float, hasHitDwarf: () -> Unit): Ennemy {
            return Ennemy(getIdleAnimation(), getAttackAnimation(), getHitAnimation(), level, startX, startY, hasHitDwarf)
        }

        private const val frameIdleDuration = 0.15f
        private const val frameAttackDuration = 0.07f
        private const val sizeX = 48f
        private const val sizeY = 48f

        private fun getIdleAnimation() = Animation(frameIdleDuration, getIdleTexture(), Animation.PlayMode.LOOP)
        private fun getIdleTexture() = Animator.generateArray("skeleton_idle.png", 0, 0, 0, 11, 24, 32, 264, 32)

        private fun getAttackAnimation() = Animation(frameAttackDuration, getAttackTexture(), Animation.PlayMode.LOOP_REVERSED)
        private fun getAttackTexture() = Animator.generateArray("skeleton_attack.png", 0, 0, 0, 18, 43, 37, 774, 37)

        private fun getHitAnimation() = Animation(frameAttackDuration, getHitTexture(), Animation.PlayMode.LOOP_REVERSED)
        private fun getHitTexture() = Animator.generateArray("skeleton_hit.png", 0, 0, 0, 8, 30, 32, 240, 32)
    }

    private var bulletGenerator: BulletAnimator? = null
    private var bullets = mutableListOf<Bullet>()
    private var alive = true
    private var stateTime: Float = 0f
    private var state = CharacterState.IDLE
    private var health: Int = 20

    init {
        sprite.setPosition(x, y)
        sprite.setSize(width, height)
        getBulletGenerator(x, y) {
            bulletGenerator = it
        }
        health = 20 * level
    }

    override fun update(gameScreen: GameScreen, delta: Float) {
        stateTime += delta

        if (isFightingAndAliveAndOnScreen(gameScreen.camera)) {
            if ((state == CharacterState.ATTACK && attackingAnimation.isAnimationFinished(stateTime)) || (state == CharacterState.HIT && hitAnimation.isAnimationFinished(stateTime))) {
                state = CharacterState.IDLE
                stateTime = 0.0f
            }
            if (state != CharacterState.HIT) {
                (haveNewBulletToThrow(delta))?.let {
                    if (state != CharacterState.ATTACK) {
                        state = CharacterState.ATTACK
                        stateTime = 0.0f
                    }
                    bullets.add(it)
                }
            }


            var bulletsTmp = mutableListOf<Bullet>()
            bullets.forEach {
                it.update(gameScreen, delta)
                if (it.isAlive) {
                    bulletsTmp.add(it)
                }
                if(it.haveHitten){
                    hasHitDwarf()
                }
            }
            bullets = bulletsTmp
        }


    }

    private fun haveNewBulletToThrow(delta: Float): Bullet? {
        val bullet = bulletGenerator?.getBullet(delta) ?: return null
        return if (!bullets.contains(bullet)) bullet else null
    }

    override fun draw(gameScreen: GameScreen, delta: Float) {

        when (state) {
            CharacterState.IDLE -> {
                sprite.setRegion(idleAnimation.getKeyFrame(stateTime))
            }
            CharacterState.RUN -> {
                // NOT POSSIBLE
            }
            CharacterState.HIT -> {
                sprite.setRegion(hitAnimation.getKeyFrame(stateTime))
            }
            CharacterState.ATTACK -> {
                sprite.setRegion(attackingAnimation.getKeyFrame(stateTime))
            }
            CharacterState.DEAD -> TODO()
        }

        sprite.draw(gameScreen.batch)
        bullets.forEach {
            it.draw(gameScreen, delta)
        }
    }

    override fun dispose() {
        sprite.texture.dispose()
        bullets.forEach { it.dispose() }
    }

    private fun getBulletGenerator(x: Float, y: Float, function: (mBulletAnimator: BulletAnimator) -> Unit): Unit {
        LevelGenerator.generateLevel(level, x, y) {
            function(BulletAnimator(it))
        }
    }

    private fun isFightingAndAliveAndOnScreen(camera: Camera): Boolean = ClodoWorld.currentScreen == ClodoScreen.IN_GAME_FIGHTING && isAliveAndOnScreen(camera)
    private fun isAliveAndOnScreen(camera: Camera): Boolean = alive && camera.project(Vector3(Vector2(x, y), 0f)).x < 1920 - width
    fun onClick(x: Float, y: Float, callback: (mBulletType: BulletType) -> Unit) {
        bullets.forEach {
            it.onClick(x, y) {
                when (it) {
                    BulletType.BOMB -> {
                        callback(it)
                    }
                    BulletType.SHIELD -> {

                    }
                    BulletType.ATTACK -> {
                        state = CharacterState.HIT
                        stateTime = 0.0f
                        health -= 20
                    }
                }
            }
        }
    }

}