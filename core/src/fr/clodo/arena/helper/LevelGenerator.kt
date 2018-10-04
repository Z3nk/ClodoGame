package fr.clodo.arena.helper

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.utils.async.AsyncTask
import fr.clodo.arena.drawables.Bullet
import fr.clodo.arena.enums.BulletType
import java.util.*


object LevelGenerator {
    const val TAG = "LevelGenerator"
    private val random = Random()
    private val intensity = floatArrayOf(2f, 1.8f, 1.4f, 0.8f, 0.7f, 0.6f, 0.5f)
    private val speedRange = mutableListOf(Pair(-40f, -150f), Pair(-60f, -170f), Pair(-100f, -200f), Pair(-100f, -200f), Pair(-120f, -200f), Pair(-140f, -200f), Pair(-160f, -200f))
    fun generateLevel(level: Int, x: Float, y: Float, function: (list: LinkedList<Pair<Float, Bullet>>) -> Unit): Unit {
        var asyncExecutor = AsyncTask {
            val bullets = LinkedList<Pair<Float, Bullet>>()
            val minSpeedLevel = speedRange[level - 1].first
            val maxSpeedLevel = speedRange[level - 1].second
            for (i in 0..100) {
                val timeToAdd = random.nextFloat() * intensity[level - 1]
                if (timeToAdd>0.1f) {
                    var type = getRandomBulletType()
                    var speed = minSpeedLevel
                    if(type != BulletType.GOLD){
                        speed = random.nextFloat() * (maxSpeedLevel - minSpeedLevel) + minSpeedLevel
                    }
                    bullets.add(Pair(timeToAdd, Bullet(x, y, speed, type)))
                }
            }
            function(bullets)
        }
        asyncExecutor.call()
    }

    private fun getRandomBulletType(): BulletType {
        var r = random.nextInt(9)
        return if (r == 0) BulletType.GOLD
        else {
            if (r <= 4) {
                BulletType.LEFT
            } else {
                BulletType.RIGHT
            }
        }
    }
}