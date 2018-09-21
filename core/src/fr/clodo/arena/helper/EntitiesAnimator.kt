package fr.clodo.arena.helper

import java.util.*


class EntitiesAnimator(val movements: LinkedList<Pair<Float, Movement>>) {
    class Movement(val speedX: Float, val speedY: Float)

    var currentMovment = movements.pop()
    var currentTime = 0f
    fun getMovement(elapsed: Float): Movement? {

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