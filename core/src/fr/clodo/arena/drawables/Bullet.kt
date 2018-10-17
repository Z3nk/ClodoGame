package fr.clodo.arena.drawables

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import fr.clodo.arena.base.Drawable
import fr.clodo.arena.enums.BulletType
import fr.clodo.arena.screens.GameScreen
import java.util.*

class Bullet(x: Float, y: Float, val speedX: Float, val type: BulletType) : Drawable(x = x, y = y, width = sizeX, height = sizeY) {
    companion object {
        //        private var attackTexture = Texture("icon_attack.png")
//        private var bombTexture = Texture("icon_bomb.png")
//        private var shieldTexture = Texture("icon_def.png")
        private var goldTexture = Texture("icon_gold.png")
        private var rightTexture = Texture("icon_right.png")
        private var leftTexture = Texture("icon_left.png")
        private var goldTextureHover = Texture("icon_gold_hover.png")
        private var rightTextureHover = Texture("icon_right_hover.png")
        private var leftTextureHover = Texture("icon_left_hover.png")
        private const val sizeX = 32f
        private const val sizeY = 32f
        fun createGoldBullet(x: Float, y: Float, speedX: Float): Bullet {
            return Bullet(x, y, speedX, BulletType.GOLD)
        }

        fun createRightBullet(x: Float, y: Float, speedX: Float): Bullet {
            return Bullet(x, y, speedX, BulletType.RIGHT)
        }

        fun createLeftBullet(x: Float, y: Float, speedX: Float): Bullet {
            return Bullet(x, y, speedX, BulletType.LEFT)
        }

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
            BulletType.GOLD -> goldTexture
            BulletType.RIGHT -> rightTexture
            BulletType.LEFT -> leftTexture
            else -> goldTexture
        }
    }

    private fun getTextureHoverOf(type: BulletType): Texture {
        return when (type) {
            BulletType.GOLD -> goldTextureHover
            BulletType.RIGHT -> rightTextureHover
            BulletType.LEFT -> leftTextureHover
            else -> goldTexture
        }
    }

    override fun update(gameScreen: GameScreen, delta: Float) {
        x += (speedX * delta)
        sprite.setPosition(x, y)
        if (x <= UI.getUnprojectXOfShotBar(gameScreen)) {
            sprite.texture = getTextureHoverOf(type)
        }
        if (x + width < UI.getUnprojectXOfShotBar(gameScreen)) {
            haveHitten = true
            isAlive = false
        }
    }

    override fun draw(gameScreen: GameScreen, delta: Float) {
        sprite.draw(gameScreen.batch)
    }

    override fun dispose() {
        this.sprite.texture = null
    }

    override fun equals(other: Any?): Boolean {
        return id == (other as Bullet).id
    }

    override fun hashCode(): Int {
        return super.hashCode()
    }

    fun onClick(x: Float, y: Float, callback: (mBulletType: BulletType) -> Unit) {
        if (isClickOnMe(x, y)) {
            isAlive = false
            callback(type)
        }
    }
}