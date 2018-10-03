package fr.clodo.arena.helper

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.utils.async.AsyncTask
import fr.clodo.arena.drawables.Bullet
import fr.clodo.arena.enums.BulletType
import java.util.*


object LevelGenerator {
    private val random = Random()
    private val intensity = floatArrayOf(5f, 0.6f, 0.7f, 0.8f, 1.0f, 1.30f, 2.6f, 3.9f, 6.1f, 9.4f)
    private val speedRange = mutableListOf(Pair(-40f, -100f), Pair(-20f, -120f), Pair(-30f, -130f), Pair(-50f, -150f), Pair(-80f, -180f), Pair(-80f, -230f), Pair(-80f, -310f), Pair(-80f, -440f), Pair(-80f, -550f), Pair(-80f, -890f))
    fun generateLevel(level: Int, x: Float, y: Float, function: (list: LinkedList<Pair<Float, Bullet>>) -> Unit): Unit {
        var asyncExecutor = AsyncTask {
            val bullets = LinkedList<Pair<Float, Bullet>>()

            val timingList = mutableListOf<Float>()
            val minSpeedLevel = speedRange[level - 1].first
            val maxSpeedLevel = speedRange[level - 1].second
            for (minTime in 0..25000 step 5000) {
                val maxTime = minTime + 5000f
                var nbAdds = 0
                val timesAdded = mutableListOf<Float>()
                do {
                    val timeToAdd = random.nextFloat() * 5000f + minTime
                    if (timesAdded.isEmpty() || !toMuchCloseIn(timesAdded, timeToAdd)) {
                        timesAdded.add(timeToAdd)
                        timingList.add(timeToAdd)
                        bullets.add(Pair(timeToAdd / 1000, Bullet(x, y, random.nextFloat() * (maxSpeedLevel - minSpeedLevel) + minSpeedLevel, getRandomBulletType())))
                        nbAdds++
                    }
                } while (!isEnoughIntense(timingList, intensity[level - 1], nbAdds))

            }
            function(bullets)
        }
        asyncExecutor.call()
    }

    private fun getRandomBulletType(): BulletType {
        var r = random.nextInt(2)
        return if (r == 0) BulletType.LEFT
        else {
            if (r == 1) {
                BulletType.RIGHT
            } else {
                BulletType.LEFT
            }
        }
    }

    private fun toMuchCloseIn(timesAdded: MutableList<Float>, timeToAdd: Float): Boolean {
        timesAdded.forEach { if (Math.abs(it - timeToAdd) < 0.2) return true }
        return false
    }

    val index = 0
    val currentIntensity = 0f
    private fun isEnoughIntense(timingList: MutableList<Float>, intensity: Float, nbAdds: Int): Boolean {
        if (nbAdds < 5 * intensity) return false
        return true

    }


}