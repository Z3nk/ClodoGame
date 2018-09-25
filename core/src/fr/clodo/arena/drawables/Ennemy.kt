package fr.clodo.arena.drawables

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Camera
import com.badlogic.gdx.graphics.g2d.*
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3
import fr.clodo.arena.base.Drawable
import fr.clodo.arena.enums.ClodoScreen
import fr.clodo.arena.helper.Animator
import fr.clodo.arena.helper.BulletAnimator
import fr.clodo.arena.helper.ClodoWorld
import fr.clodo.arena.screens.GameScreen
import java.util.*

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
    private var bulletGenerator: BulletAnimator
    private var bullets = mutableSetOf<Bullet>()
    private var alive = true

    init {
        sprite.setPosition(x, y)
        sprite.setSize(width, height)
        bulletGenerator = getBulletGenerator(x, y)

    }

    override fun update(gameScreen: GameScreen, delta: Float) {
        stateTime += delta
        if (isFightingAndAliveAndOnScreen(gameScreen.camera)) {
            val newBullet = bulletGenerator.getBullet(delta)
            if (newBullet != null) {
                bullets.add(newBullet)
            }
            bullets.forEach { it.update(gameScreen, delta) }
        }
    }

    override fun draw(gameScreen: GameScreen, delta: Float) {
        sprite.setRegion(walkingAnimation.getKeyFrame(stateTime))
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
        list.add(Pair(0.4f, Bullet.createAttackBullet(v1.x, v1.y, -100f)))
        list.add(Pair(1f, Bullet.createBombBullet(v2.x, v2.y, -150f)))
        list.add(Pair(2f, Bullet.createShieldBullet(v2.x, v2.y, -200f)))
        return BulletAnimator(list)
    }

    private fun isFightingAndAliveAndOnScreen(camera: Camera): Boolean = ClodoWorld.currentScreen == ClodoScreen.IN_GAME_FIGHTING && isAliveAndOnScreen(camera)
    private fun isAliveAndOnScreen(camera: Camera): Boolean = alive && camera.project(Vector3(Vector2(x, y), 0f)).x < 1920 - width

}