package fr.clodo.arena.drawables

import com.badlogic.gdx.graphics.Camera
import com.badlogic.gdx.graphics.g2d.*
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3
import fr.clodo.arena.base.Drawable
import fr.clodo.arena.enums.ClodoScreen
import fr.clodo.arena.enums.HeroState
import fr.clodo.arena.helper.Animator
import fr.clodo.arena.helper.BulletAnimator
import fr.clodo.arena.helper.ClodoWorld
import fr.clodo.arena.screens.GameScreen
import java.util.*

class Ennemy(private val idleAnimation: Animation<TextureRegion>, private val attackingAnimation: Animation<TextureRegion>, val level: Int, startX: Float, startY: Float) : Drawable(x = startX, y = startY, width = sizeX, height = sizeY) {

    companion object {
        fun createEnnemy(level: Int, startX: Float, startY: Float): Ennemy {
            return Ennemy(getIdleAnimation(), getAttackAnimation(), level, startX, startY)
        }

        private const val frameIdleDuration = 0.15f
        private const val frameAttackDuration = 0.07f
        private const val sizeX = 48f
        private const val sizeY = 48f

        private fun getIdleAnimation() = Animation(frameIdleDuration, getIdleTexture(), Animation.PlayMode.LOOP)
        private fun getIdleTexture() = Animator.generateArray("skeleton_idle.png", 0, 0, 0, 11, 24, 32, 264, 32)

        private fun getAttackAnimation() = Animation(frameAttackDuration, getAttackTexture(), Animation.PlayMode.LOOP_REVERSED)
        private fun getAttackTexture() = Animator.generateArray("skeleton_attack.png", 0, 0, 0, 18, 43, 37, 774, 37)
    }

    private var bulletGenerator: BulletAnimator
    private var bullets = mutableListOf<Bullet>()
    private var alive = true
    private var stateTime: Float = 0f
    private var state = HeroState.IDLE

    init {
        sprite.setPosition(x, y)
        sprite.setSize(width, height)
        bulletGenerator = getBulletGenerator(x, y)

    }

    override fun update(gameScreen: GameScreen, delta: Float) {
        stateTime += delta

        if (isFightingAndAliveAndOnScreen(gameScreen.camera)) {
            if (state == HeroState.ATTACK && attackingAnimation.isAnimationFinished(stateTime)) {
                state = HeroState.IDLE
                stateTime = 0.0f
            }
            (haveNewBulletToThrow(delta))?.let {
                if (state != HeroState.ATTACK) {
                    state = HeroState.ATTACK
                    stateTime = 0.0f
                }
                bullets.add(it)
            }
            bullets.forEach { it.update(gameScreen, delta) }
        }


    }

    private fun haveNewBulletToThrow(delta: Float): Bullet? {
        val bullet = bulletGenerator.getBullet(delta)?:return null
        return if (!bullets.contains(bullet)) bullet else null
    }

    override fun draw(gameScreen: GameScreen, delta: Float) {

        when (state) {
            HeroState.IDLE -> {
                sprite.setRegion(idleAnimation.getKeyFrame(stateTime))
            }
            HeroState.RUN -> {
                // NOT POSSIBLE
            }
            HeroState.ATTACK -> {
                sprite.setRegion(attackingAnimation.getKeyFrame(stateTime))
            }
            HeroState.DEAD -> TODO()
        }

        sprite.draw(gameScreen.batch)
        bullets.forEach { it.draw(gameScreen, delta) }
    }

    override fun dispose() {
        sprite.texture.dispose()
        bullets.forEach { it.dispose() }
    }

    private fun getBulletGenerator(x: Float, y: Float): BulletAnimator {
        val list = LinkedList<Pair<Float, Bullet>>()
        val v1 = Vector3(Vector2(x, y), 0f)
        val v2 = Vector3(Vector2(x, y), 0f)
        list.add(Pair(3f, Bullet.createAttackBullet(v1.x, v1.y, -100f)))
        list.add(Pair(3f, Bullet.createBombBullet(v2.x, v2.y, -150f)))
        list.add(Pair(2f, Bullet.createShieldBullet(v2.x, v2.y, -200f)))
        return BulletAnimator(list)
    }

    private fun isFightingAndAliveAndOnScreen(camera: Camera): Boolean = ClodoWorld.currentScreen == ClodoScreen.IN_GAME_FIGHTING && isAliveAndOnScreen(camera)
    private fun isAliveAndOnScreen(camera: Camera): Boolean = alive && camera.project(Vector3(Vector2(x, y), 0f)).x < 1920 - width

}