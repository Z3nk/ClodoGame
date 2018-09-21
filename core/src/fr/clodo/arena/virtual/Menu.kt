package fr.clodo.arena.virtual

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import fr.clodo.arena.base.Clickable
import fr.clodo.arena.base.Drawable
import fr.clodo.arena.drawables.Button
import fr.clodo.arena.drawables.Narrator
import fr.clodo.arena.enums.ClodoScreen
import fr.clodo.arena.helper.ClodoWorld
import fr.clodo.arena.screens.GameScreen
import java.util.*
import kotlin.concurrent.timerTask

class Menu(val gameScreen: GameScreen): Drawable, Clickable{
    companion object {
        const val TAG = "Menu"
    }

    private var narrator = Narrator()
    private var playBtn = Button.createPlayButton(ClodoWorld.WORLD_WIDTH / 2f, ClodoWorld.WORLD_HEIGHT / 2f) {
        Timer().schedule(timerTask {
            Gdx.app.log(TAG, "Demarrage de la partie")
            gameScreen.currentScreen = ClodoScreen.IN_GAME
        }, 1000)
    }

    override fun update(delta: Float) {
        playBtn.update(delta)

    }

    override fun draw(batch: SpriteBatch, font: BitmapFont, delta: Float) {
        playBtn.draw(batch, font, delta)
    }

    override fun dispose() {
        playBtn.dispose()
    }

    override fun onClick(x: Float, y: Float) {
        playBtn.onClick(x, y)
    }

}