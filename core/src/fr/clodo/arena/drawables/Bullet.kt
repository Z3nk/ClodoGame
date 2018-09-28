package fr.clodo.arena.drawables

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import fr.clodo.arena.base.Drawable
import fr.clodo.arena.enums.BulletType
import fr.clodo.arena.screens.GameScreen
import java.util.*

class Bullet(x: Float, y: Float, val speedX: Float, val type: BulletType) : Drawable(x = x, y = y, width = sizeX, height = sizeY) {
    companion object {
        private var attackTexture = Texture("icon_attack.png")
        private var bombTexture = Texture("icon_bomb.png")
        private var shieldTexture = Texture("icon_def.png")
        private const val sizeX = 32f
        private const val sizeY = 32f
        fun createAttackBullet(x: Float, y: Float, speedX: Float): Bullet {
            return Bullet(x, y, speedX, BulletType.ATTACK)
        }

        fun createShieldBullet(x: Float, y: Float, speedX: Float): Bullet {
            return Bullet(x, y, speedX, BulletType.SHIELD)
        }

        fun createBombBullet(x: Float, y: Float, speedX: Float): Bullet {
            return Bullet(x, y, speedX, BulletType.BOMB)
        }
    }


    var isAlive: Boolean = true
    var haveHitten: Boolean = false
    private val id: UUID = UUID.randomUUID()

    init {
        sprite = Sprite(getTextureOf(type))
        sprite.setSize(width, height)
        sprite.setPosition(x, y)
    }

    private fun getTextureOf(type: BulletType): Texture {
        return when (type) {
            BulletType.ATTACK -> attackTexture
            BulletType.SHIELD -> shieldTexture
            else -> bombTexture
        }
    }

    override fun update(gameScreen: GameScreen, delta: Float) {
        x += (speedX * delta)
        sprite.setPosition(x, y)
        if (x + width < UI.getUnprojectX(gameScreen)) {
            haveHitten = true
            isAlive = false
        }
    }

    override fun draw(gameScreen: GameScreen, delta: Float) {
        sprite.draw(gameScreen.batch)
    }

    override fun dispose() {
    }

    override fun equals(other: Any?): Boolean {
        return id == (other as Bullet).id
    }

    override fun hashCode(): Int {
        return super.hashCode()
    }

    fun onClick(x: Float, y: Float, callback: (mBulletType: BulletType) -> Unit) {
        Gdx.app.log("Bullet", "$x $y  <=> " + this.x + " " + this.y)
        if (isClickOnMe(x, y)) {
            isAlive = false
            callback(type)
        }
    }
}