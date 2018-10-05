package fr.clodo.arena.drawables

import com.badlogic.gdx.graphics.Camera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3
import fr.clodo.arena.base.Drawable
import fr.clodo.arena.enums.AlertScene
import fr.clodo.arena.enums.BulletType
import fr.clodo.arena.enums.CharacterState
import fr.clodo.arena.enums.ClodoScreen
import fr.clodo.arena.helper.*
import fr.clodo.arena.screens.GameScreen

class Ennemy(private val idleAnimation: Animation<TextureRegion>, private val attackingAnimation: Animation<TextureRegion>, private val hitAnimation: Animation<TextureRegion>, private val deadAnimation: Animation<TextureRegion>, val level: Int, startX: Float, startY: Float, private val alertScene: (mAlertScene: AlertScene) -> Unit) : Drawable(x = startX, y = startY, width = sizeX, height = sizeY) {

    companion object {
        fun createEnnemy(level: Int, startX: Float, startY: Float, alertScene: (mAlertScene: AlertScene) -> Unit): Ennemy {
            return Ennemy(getIdleAnimation(), getAttackAnimation(), getHitAnimation(), getDeadAnimation(), level, startX, startY, alertScene)
        }

        private const val frameIdleDuration = 0.15f
        private const val frameAttackDuration = 0.07f
        private const val sizeX = 48f
        private const val sizeY = 48f

        private val idleTexture = Texture("skeleton_idle.png")
        private val attackTexture = Texture("skeleton_attack.png")
        private val hitTexture = Texture("skeleton_hit.png")
        private val deadTexture = Texture("skeleton_dead.png")

        private fun getIdleAnimation() = Animation(frameIdleDuration, getIdleTexture(), Animation.PlayMode.LOOP)
        private fun getIdleTexture() = Animator.generateArray(idleTexture, 0, 0, 0, 11, 24, 32, 264, 32)

        private fun getAttackAnimation() = Animation(frameAttackDuration, getAttackTexture(), Animation.PlayMode.LOOP_REVERSED)
        private fun getAttackTexture() = Animator.generateArray(attackTexture, 0, 0, 0, 18, 43, 37, 774, 37)

        private fun getHitAnimation() = Animation(frameAttackDuration, getHitTexture(), Animation.PlayMode.LOOP_REVERSED)
        private fun getHitTexture() = Animator.generateArray(hitTexture, 0, 0, 0, 8, 30, 32, 240, 32)

        private fun getDeadAnimation() = Animation(frameAttackDuration, getDeadTexture(), Animation.PlayMode.REVERSED)
        private fun getDeadTexture() = Animator.generateArray(deadTexture, 0, 0, 0, 15, 33, 32, 495, 32)
    }

    private var bulletGenerator: BulletAnimator? = null
    private var bullets = mutableListOf<Bullet>()
    private var stateTime: Float = 0f
    public var state = CharacterState.IDLE
    private var health = 20f
    private val startHealth: Int
    private var alive = true

    init {
        sprite.setPosition(x, y)
        sprite.setSize(width, height)
        getBulletGenerator(x, y) {
            bulletGenerator = it
        }
        health = 20f //* level
        startHealth = 20 //* level
    }

    override fun update(gameScreen: GameScreen, delta: Float) {
        stateTime += delta

        if (isFightingAndOnScreen(gameScreen.camera)) {

            // CHARACTER
            if (state != CharacterState.DEAD) {
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
                // BULLETS
                val bulletsTmp = mutableListOf<Bullet>()
                bullets.forEach {
                    it.update(gameScreen, delta)
                    if (it.isAlive) {
                        bulletsTmp.add(it)
                    } else {
                        it.dispose()
                    }
                    if (it.haveHitten) {
                        alertScene(AlertScene.STRIKE)
                    }
                }
                bullets = bulletsTmp
            } else {
                if (alive && attackingAnimation.isAnimationFinished(stateTime)) {
                    alive = false
                    alertScene(AlertScene.DEAD)
                }
            }


        }

    }

    private fun haveNewBulletToThrow(delta: Float): Bullet? {
        val bullet = bulletGenerator?.getBullet(delta) ?: return null
        return if (!bullets.contains(bullet)) bullet else null
    }

    override fun draw(gameScreen: GameScreen, delta: Float) {
        if (alive) {
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
                CharacterState.DEAD -> {
                    sprite.setRegion(deadAnimation.getKeyFrame(stateTime))
                }
            }

            sprite.draw(gameScreen.batch)
            bullets.forEach {
                it.draw(gameScreen, delta)
            }
        }
    }

    override fun dispose() {
        sprite.texture = null
        bullets.forEach { it.dispose() }
    }

    private fun getBulletGenerator(x: Float, y: Float, function: (mBulletAnimator: BulletAnimator) -> Unit): Unit {
        LevelGenerator.generateLevel(level, x, y) {
            function(BulletAnimator(it))
        }
    }

    private fun isFightingAndOnScreen(camera: Camera): Boolean = ClodoWorld.currentScreen == ClodoScreen.IN_GAME_FIGHTING && isOnScreen(camera)
    private fun isOnScreen(camera: Camera): Boolean = camera.project(Vector3(Vector2(x, y), 0f)).x < 1920 - width
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
                        getDamage()
                    }
                    BulletType.LEFT -> {
                        if (ClodoWorld.hasClickedRight) {
                            alertScene(AlertScene.STRIKE)
                        } else {
                            getDamage()
                        }

                    }
                    BulletType.RIGHT -> {
                        if (ClodoWorld.hasClickedRight) {
                            getDamage()
                        } else {
                            alertScene(AlertScene.STRIKE)
                        }
                    }
                }
            }
        }
    }

    private fun getDamage() {
        state = CharacterState.HIT
        stateTime = 0.0f
        health -= 20f
        if (health <= 0) {
            ClodoWorld.currentHealthPourcent = 0f
            state = CharacterState.DEAD
            stateTime = 0.0f
        } else {
            ClodoWorld.currentHealthPourcent = health / startHealth
        }
    }

}