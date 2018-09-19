package fr.clodo.arena.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputProcessor
import com.badlogic.gdx.Screen
import com.badlogic.gdx.math.Vector3
import fr.clodo.arena.ClodoArenaGame
import fr.clodo.arena.entities.Background
import fr.clodo.arena.entities.Button
import fr.clodo.arena.entities.Dwarf
import fr.clodo.arena.tools.ClodoWorld

class GameScreen(val game: ClodoArenaGame) : Screen, InputProcessor {

    companion object {
        const val TAG = "GameScreen"
    }


    private val background = Background.createNightBackground()
    private val dwarf = Dwarf.createDwarf()
    private val playBtn = Button.createPlayButton(ClodoWorld.WORLD_WIDTH/2f, ClodoWorld.WORLD_HEIGHT/2f){
        Gdx.app.log(TAG, "PLAY BUTTON")
    }

    override fun hide() {
    }

    override fun show() {
        Gdx.input.inputProcessor = this
    }

    override fun render(delta: Float) {
        background.draw(game.batch, delta)
        dwarf.draw(game.batch, delta)
        playBtn.draw(game.batch, delta)
    }

    override fun pause() {
    }

    override fun resume() {
    }

    override fun resize(width: Int, height: Int) {
    }

    override fun dispose() {
        playBtn.dispose()
        dwarf.dispose()
        background.dispose()
    }

    override fun mouseMoved(screenX: Int, screenY: Int): Boolean {
        return false
    }

    override fun keyTyped(character: Char): Boolean {
        return false
    }

    override fun scrolled(amount: Int): Boolean {
        return false
    }

    override fun keyUp(keycode: Int): Boolean {
        return false
    }

    override fun touchDragged(screenX: Int, screenY: Int, pointer: Int): Boolean {
        return false
    }

    override fun keyDown(keycode: Int): Boolean {
        return false
    }

    override fun touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        return false
    }

    override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        val worldCoordinates = game.camera.unproject(Vector3(screenX.toFloat(), screenY.toFloat(), 0f))
        Gdx.app.log("Mouse Event", "Projected at " + worldCoordinates.x + "," + worldCoordinates.y)
        playBtn.onClick(worldCoordinates.x, worldCoordinates.y)
        return false
    }
}