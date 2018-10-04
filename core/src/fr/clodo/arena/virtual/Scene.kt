package fr.clodo.arena.virtual

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3
import fr.clodo.arena.base.Clickable
import fr.clodo.arena.base.Drawable
import fr.clodo.arena.drawables.*
import fr.clodo.arena.enums.AlertScene
import fr.clodo.arena.enums.BulletType
import fr.clodo.arena.enums.ClodoScreen
import fr.clodo.arena.helper.ClodoWorld
import fr.clodo.arena.screens.GameScreen

class Scene() : Drawable(), Clickable {
    companion object {
        const val TAG = "Scene"
    }

    private val background = Background.createNightBackground()
    private val dwarf = Dwarf.createDwarf()
    //private val ui = UI(gameScreen)
    var ennemies = mutableListOf<Ennemy>()


    init {
        for (i in 1..ClodoWorld.maxLevel) {
            ennemies.add(Ennemy.createEnnemy(i, i * 600f, 120f) {
                if (it == AlertScene.STRIKE) {
                    dwarf.isHit()
                } else {
                    if (ClodoWorld.currentScreen != ClodoScreen.IN_GAME_WALKING) {
                        if (ClodoWorld.currentLevel < ClodoWorld.maxLevel) {
                            ClodoWorld.currentScreen = ClodoScreen.IN_GAME_WALKING
                            ClodoWorld.currentLevel++
                        }
                    }
                }
            })
        }
    }

    override fun update(gameScreen: GameScreen, delta: Float) {
        background.update(gameScreen, delta)
        dwarf.update(gameScreen, delta)
        ennemies.forEach { it.update(gameScreen, delta) }
        //ui.update(gameScreen, delta)
        //Gdx.app.log(TAG, "Position unproject du current ennemie x : ${ennemies[ClodoWorld.currentLevel-1].sprite.x}: projeté x : " + gameScreen.camera.project(Vector3(Vector2(ennemies[ClodoWorld.currentLevel-1].sprite.x, ennemies[ClodoWorld.currentLevel-1].sprite.y), 0f)).toString())
        if (ClodoWorld.currentScreen == ClodoScreen.IN_GAME_WALKING && haveWalkedToNextBoss(gameScreen)) {
            ClodoWorld.currentScreen = ClodoScreen.IN_GAME_FIGHTING
            ClodoWorld.currentHealthPourcent = 1.0f
        }
    }

    private fun haveWalkedToNextBoss(gameScreen: GameScreen): Boolean {
        try {
            return gameScreen.camera.project(Vector3(Vector2(ennemies[ClodoWorld.currentLevel - 1].sprite.x, ennemies[ClodoWorld.currentLevel - 1].sprite.y), 0f)).x < 1920 - 250
        } catch (e: Exception) {
            throw RuntimeException()
        }
    }

    override fun draw(gameScreen: GameScreen, delta: Float) {
        background.draw(gameScreen, delta)
        dwarf.draw(gameScreen, delta)
        ennemies.forEach { it.draw(gameScreen, delta) }
        //ui.draw(batch, font, delta)
    }

    override fun dispose() {
        dwarf.dispose()
        background.dispose()
        ennemies.forEach { it.dispose() }
        //ui.dispose()
    }

    override fun onClick(x: Float, y: Float) {
        dwarf.onClick(x, y)
        ennemies[ClodoWorld.currentLevel - 1].onClick(x, y) {
            when (it) {
                BulletType.BOMB -> {
                }
                BulletType.SHIELD -> {

                }
                BulletType.ATTACK -> {

                }
            }
        }
    }

}