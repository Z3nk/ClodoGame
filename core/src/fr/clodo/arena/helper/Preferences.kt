package fr.clodo.arena.helper

import com.badlogic.gdx.Gdx

abstract class Preferences{
    companion object {
        const val NAME = "clodo"

        fun tutorialIsDone(): Boolean = Gdx.app.getPreferences(Preferences.NAME).getBoolean("tutorialDone", false)
        fun setTutorialIsDone() = Gdx.app.getPreferences(Preferences.NAME).putBoolean("tutorialDone", true)
    }
}