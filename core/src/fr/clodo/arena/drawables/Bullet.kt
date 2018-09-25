package fr.clodo.arena.drawables

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import fr.clodo.arena.base.Drawable
import fr.clodo.arena.enums.BulletType
import fr.clodo.arena.screens.GameScreen
import java.util.*

class Bullet(x: Float, y: Float, val speedX: Float, val type: BulletType) : Drawable(x = x, y = y, width = sizeX, height = sizeY) {
    companion object {

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

    private val id: UUID = UUID.randomUUID()

    init {
        sprite = Sprite(Texture(getTextureOf(type)))
        sprite.setSize(width, height)
        sprite.setPosition(x, y)
    }

    private fun getTextureOf(type: BulletType): String {
        return if (type == BulletType.ATTACK) {
            "icon_attack.png"
        } else if (type == BulletType.SHIELD) {
            "icon_def.png"
        } else {
            "icon_bomb.png"
        }
    }

    override fun update(gameScreen: GameScreen, delta: Float) {
        x += (speedX * delta)
        sprite.setPosition(x, y)
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
}