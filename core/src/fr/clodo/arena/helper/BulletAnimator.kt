package fr.clodo.arena.helper

import fr.clodo.arena.drawables.Bullet
import java.util.*


class BulletAnimator(val movements: LinkedList<Pair<Float, Bullet>>) {
    class Movement(val speedX: Float, val speedY: Float)

    var currentMovment = movements.pop()
    var currentTime = 0f
    fun getBullet(elapsed: Float): Bullet? {

        currentTime += elapsed
        if (currentTime > currentMovment.first) {
            if(movements.isNotEmpty()) {
                currentMovment = movements.pop()
                currentTime = 0f
            }else{
                return null
            }
        }

        return currentMovment.second
    }

}