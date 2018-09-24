package fr.clodo.arena.virtual

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3
import fr.clodo.arena.base.Clickable
import fr.clodo.arena.base.Drawable
import fr.clodo.arena.drawables.Background
import fr.clodo.arena.drawables.Dwarf
import fr.clodo.arena.drawables.Ennemy
import fr.clodo.arena.enums.ClodoScreen
import fr.clodo.arena.helper.ClodoWorld
import fr.clodo.arena.screens.GameScreen

class Scene(val gameScreen: GameScreen) : Drawable(), Clickable {
    companion object {
        const val TAG = "Scene"
    }

    private val background = Background.createNightBackground()
    private val dwarf = Dwarf.createDwarf()
    var ennemies = mutableListOf<Drawable>()


    init{
        for(i in 1..10){
            ennemies.add(Ennemy.createEnnemy(i, i*600f, 120f))
        }
    }

    override fun update(delta: Float) {
        background.update(delta)
        dwarf.update(delta)
        ennemies.forEach { it.update(delta) }
        Gdx.app.log(TAG, "Position unproject du current ennemie x : ${ennemies[ClodoWorld.currentLevel-1].sprite.x}: projeté x : " + gameScreen.camera.project(Vector3(Vector2(ennemies[ClodoWorld.currentLevel-1].sprite.x, ennemies[ClodoWorld.currentLevel-1].sprite.y), 0f)).toString())
        if(ClodoWorld.currentScreen == ClodoScreen.IN_GAME_WALKING && gameScreen.camera.project(Vector3(Vector2(ennemies[ClodoWorld.currentLevel-1].sprite.x, ennemies[ClodoWorld.currentLevel-1].sprite.y), 0f)).x < 1920 - 250){
            ClodoWorld.currentScreen = ClodoScreen.IN_GAME_FIGHTING
        }
    }

    override fun draw(batch: SpriteBatch, font: BitmapFont, delta: Float) {
        background.draw(batch, font, delta)
        dwarf.draw(batch, font, delta)
        ennemies.forEach { it.draw(batch, font, delta) }
    }

    override fun dispose() {
        dwarf.dispose()
        background.dispose()
        ennemies.forEach { it.dispose() }
    }

    override fun onClick(x: Float, y: Float) {

    }

}