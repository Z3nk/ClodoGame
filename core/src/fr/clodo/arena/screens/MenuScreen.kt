package fr.clodo.arena.screens

import com.badlogic.gdx.Screen
import fr.clodo.arena.ClodoArenaGame
import fr.clodo.arena.entities.Background
import fr.clodo.arena.entities.Dwarf

class MenuScreen(val game: ClodoArenaGame) : Screen {
    private val background = Background.createNightBackground()
    private val dwarf = Dwarf.createDwarf()

    override fun hide() {
    }

    override fun show() {

    }

    override fun render(delta: Float) {
        background.draw(game.batch, delta)
        dwarf.draw(game.batch, delta)
    }

    override fun pause() {
    }

    override fun resume() {
    }

    override fun resize(width: Int, height: Int) {
    }

    override fun dispose() {
        dwarf.dispose()
        background.dispose()
    }
}