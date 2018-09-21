package fr.clodo.arena

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import fr.clodo.arena.screens.GameScreen

class ClodoArenaGame : Game() {

    override fun create() {
        this.setScreen(GameScreen(this))
    }

    override fun render() {

        Gdx.gl.glClearColor(0f, 0f, 0.2f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        super.render()
    }

    override fun dispose() {
        this.getScreen().dispose()
    }
}