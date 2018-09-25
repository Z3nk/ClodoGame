package fr.clodo.arena.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputProcessor
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.Camera
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.utils.viewport.StretchViewport
import com.badlogic.gdx.utils.viewport.Viewport
import fr.clodo.arena.ClodoArenaGame
import fr.clodo.arena.drawables.Dwarf
import fr.clodo.arena.enums.ClodoScreen
import fr.clodo.arena.helper.ClodoWorld
import fr.clodo.arena.helper.Preferences
import fr.clodo.arena.virtual.Menu
import fr.clodo.arena.virtual.Scene

class GameScreen(val game: ClodoArenaGame) : Screen, InputProcessor {

    companion object {
        const val TAG = "GameScreen"
    }


    lateinit var batch: SpriteBatch
    lateinit var font: BitmapFont
    lateinit var camera: Camera
    lateinit var viewport: Viewport


    private val menu = Menu(this)
    private val scene = Scene(this)

    var cameraInitialised = false
    private var scaling = 0f

    override fun show() {
        ClodoWorld.tutorialIsDone = Preferences.tutorialIsDone()
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

    private fun update(delta: Float) {
        updateCamera(delta)
        scene.update(delta)
        menu.update(delta)
    }

    override fun render(delta: Float) {
        update(delta)
        batch.begin()
        scene.draw(batch, font, delta)
        menu.draw(batch, font, delta)
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
        scene.dispose()
        menu.dispose()
    }

    override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        val worldCoordinates = camera.unproject(Vector3(screenX.toFloat(), screenY.toFloat(), 0f))
        Gdx.app.log(TAG, "Click at " + worldCoordinates.x + "," + worldCoordinates.y)
        scene.onClick(worldCoordinates.x, worldCoordinates.y)
        menu.onClick(worldCoordinates.x, worldCoordinates.y)
        return false
    }


    private fun initCamera() {
        camera.viewportWidth = ClodoWorld.lastViewportWidth
        camera.viewportHeight = ClodoWorld.lastViewportHeight
        camera.position.set(ClodoWorld.lastPosition)
    }

    private fun updateCamera(delta: Float) {
        if (ClodoWorld.currentScreen == ClodoScreen.IN_GAME_WAITING && !cameraInitialised) {
            scaling += 0.001f
            camera.viewportWidth = camera.viewportWidth / (1 + scaling)
            camera.viewportHeight = camera.viewportHeight / (1 + scaling)
            camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2 + (80 * (scaling * 20.4081745106f)), 0f)
            if (camera.viewportWidth < 600) {
                Gdx.app.log(TAG, "Camera initialisé ; width : ${camera.viewportWidth}, height : ${camera.viewportHeight}, position : ${camera.position}")
                ClodoWorld.currentScreen = ClodoScreen.IN_GAME_WALKING
                cameraInitialised = true
            }
        } else if (ClodoWorld.currentScreen == ClodoScreen.IN_GAME_WALKING) {
            val fl = Dwarf.speedX * delta
            camera.position.set(Vector2(camera.position.x + fl, camera.position.y), 0f)
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