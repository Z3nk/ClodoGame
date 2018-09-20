package fr.clodo.arena.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputProcessor
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.Camera
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.utils.viewport.StretchViewport
import com.badlogic.gdx.utils.viewport.Viewport
import fr.clodo.arena.ClodoArenaGame
import fr.clodo.arena.entities.Background
import fr.clodo.arena.entities.Button
import fr.clodo.arena.entities.Dwarf
import fr.clodo.arena.tools.ClodoWorld
import java.util.*
import kotlin.concurrent.timerTask

class GameScreen(val game: ClodoArenaGame) : Screen, InputProcessor {

    companion object {
        const val TAG = "GameScreen"
    }


    lateinit var batch: SpriteBatch
    lateinit var font: BitmapFont
    lateinit var camera: Camera
    lateinit var viewport: Viewport

    private val background = Background.createNightBackground()
    private val dwarf = Dwarf.createDwarf()
    private var gameStarted = false
    private var cameraInitialised = false
    private var scaling = 0f
    private var playBtn = Button.createPlayButton(ClodoWorld.WORLD_WIDTH / 2f, ClodoWorld.WORLD_HEIGHT / 2f) {
        Gdx.app.log(TAG, "PLAY BUTTON")
        Timer().schedule(timerTask {
            gameStarted = true
        }, 1000)
    }

    override fun show() {
        batch = SpriteBatch()
        camera = OrthographicCamera()
        viewport = StretchViewport(ClodoWorld.WORLD_WIDTH.toFloat(), ClodoWorld.WORLD_HEIGHT.toFloat(), camera)
        viewport.apply()
        font = BitmapFont()
        Gdx.input.inputProcessor = this
    }

    override fun resume() {
        initCamera()
    }

    override fun render(delta: Float) {

        updateCamera()
        batch.begin()
        background.draw(batch, delta)
        dwarf.draw(batch, delta)
        playBtn.draw(batch, delta)
        batch.end()
    }

    override fun pause() {
        ClodoWorld.lastViewportWidth = camera.viewportWidth
        ClodoWorld.lastViewportHeight = camera.viewportHeight
        ClodoWorld.lastPosition = camera.position
    }

    override fun hide() {
    }

    override fun resize(width: Int, height: Int) {
        viewport.update(width, height)
        initCamera()
    }

    override fun dispose() {
        batch.dispose()
        font.dispose()
        playBtn.dispose()
        dwarf.dispose()
        background.dispose()
    }

    override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        val worldCoordinates = camera.unproject(Vector3(screenX.toFloat(), screenY.toFloat(), 0f))
        Gdx.app.log("Mouse Event", "Projected at " + worldCoordinates.x + "," + worldCoordinates.y)
        playBtn.onClick(worldCoordinates.x, worldCoordinates.y)
        return false
    }


    private fun initCamera() {
        camera.viewportWidth = ClodoWorld.lastViewportWidth
        camera.viewportHeight = ClodoWorld.lastViewportHeight
        camera.position.set(ClodoWorld.lastPosition)
    }

    private fun updateCamera() {
        if (gameStarted && !cameraInitialised) {
            scaling += 0.001f
            camera.viewportWidth = camera.viewportWidth / (1 + scaling)
            camera.viewportHeight = camera.viewportHeight / (1 + scaling)
            camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2 + (80 * (scaling * 20.4081745106f)), 0f)
            if (camera.viewportWidth < 600) {
                cameraInitialised = true
            }
        }

        camera.update()
        batch.projectionMatrix = camera.combined
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
}