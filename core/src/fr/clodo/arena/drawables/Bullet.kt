package fr.clodo.arena.drawables

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import fr.clodo.arena.base.Drawable
import fr.clodo.arena.enums.BulletType

class Bullet(x: Float, y: Float, val speedX: Float, val type: BulletType) : Drawable(x = x, y = y) {
    companion object {

        private const val sizeX = 64f
        private const val sizeY = 64f
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

    override fun update(delta: Float) {
        x += (speedX * delta)
        sprite.setPosition(x, y)
    }

    override fun draw(batch: SpriteBatch, font: BitmapFont, delta: Float) {
        sprite.draw(batch)
    }

    override fun dispose() {
    }

}