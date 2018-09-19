package fr.clodo.arena

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Camera
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.utils.viewport.StretchViewport
import com.badlogic.gdx.utils.viewport.Viewport
import fr.clodo.arena.tools.ClodoWorld
import fr.clodo.arena.screens.MenuScreen

class ClodoArenaGame : Game() {

    lateinit var batch: SpriteBatch
    lateinit var font: BitmapFont
    lateinit var camera: Camera
    lateinit var viewport: Viewport

    override fun create() {
        batch = SpriteBatch()
        camera = OrthographicCamera()
        viewport = StretchViewport(ClodoWorld.WORLD_WIDTH.toFloat(), ClodoWorld.WORLD_HEIGHT.toFloat(), camera)
        viewport.apply()
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0f)
        font = BitmapFont()
        this.setScreen(MenuScreen(this))
    }

    override fun render() {
        camera.update()
        Gdx.gl.glClearColor(0f, 0f, 0.2f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        batch.projectionMatrix = camera.combined
        batch.begin()
        super.render()
        batch.end()
    }

    override fun dispose() {
        this.getScreen().dispose()
        batch.dispose()
        font.dispose()
    }

    override fun resize(width: Int, height: Int) {

        viewport.update(width, height)
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0f)
        super.resize(width, height)
    }
}