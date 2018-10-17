package fr.clodo.arena.helper

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.math.Vector3
import fr.clodo.arena.enums.ClodoScreen

abstract class ClodoWorld {
    companion object {
        const val WORLD_WIDTH = 1920
        const val WORLD_HEIGHT = 1080
        var lastViewportWidth = WORLD_WIDTH + 0.0f
        var lastViewportHeight = WORLD_HEIGHT + 0.0f
        var lastPosition = Vector3(WORLD_WIDTH / 2f, WORLD_HEIGHT / 2f, 0f)
        var currentLevel = 1
        var tutorialIsDone = false
        var currentScreen = ClodoScreen.LOBBY
        var hasClickedRight = false
        var currentPlayerHealthPourcent = 1.0f
        var currentHealthPourcent = 1.0f
        var maxLevel = 6
    }
}