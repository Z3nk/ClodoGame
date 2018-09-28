package fr.clodo.arena.helper

import fr.clodo.arena.drawables.Bullet
import java.util.*


class BulletAnimator(val movements: LinkedList<Pair<Float, Bullet>>) {
    class Movement(val speedX: Float, val speedY: Float)

    var currentMovment = movements.pop()
    var firstTimeCalled = true
    var currentTime = 0f
    fun getBullet(elapsed: Float): Bullet? {
        if (currentMovment == null) {
            return null
        }
        if (firstTimeCalled) {
            currentTime = 0f
            firstTimeCalled = false
        }
        currentTime += elapsed
        if (currentTime > currentMovment.first) {
            val toReturn = currentMovment
            if (movements.isNotEmpty()) {
                currentMovment = movements.pop()
                currentTime = 0f
            } else {
                currentMovment = null
            }
            return toReturn.second
        }
        return null

    }

}