package fr.clodo.arena.virtual

import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import fr.clodo.arena.base.Clickable
import fr.clodo.arena.base.Drawable
import fr.clodo.arena.drawables.Background
import fr.clodo.arena.drawables.Dwarf
import fr.clodo.arena.screens.GameScreen

class Scene(val gameScreen: GameScreen) : Drawable, Clickable {
    companion object {
        const val SCENE = "Scene"
    }

    private val background = Background.createNightBackground()
    private val dwarf = Dwarf.createDwarf()

    override fun update(delta: Float) {
        background.update(delta)
        dwarf.update(delta)
    }

    override fun draw(batch: SpriteBatch, font: BitmapFont, delta: Float) {
        background.draw(batch, font, delta)
        dwarf.draw(batch, font, delta)
    }

    override fun dispose() {
        dwarf.dispose()
        background.dispose()
    }

    override fun onClick(x: Float, y: Float) {

    }

}