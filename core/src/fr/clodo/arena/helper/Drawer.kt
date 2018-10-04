package fr.clodo.arena.helper

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType
import com.badlogic.gdx.math.Matrix4
import com.badlogic.gdx.math.Vector2


abstract class Drawer{
    companion object {
        val debugRenderer = ShapeRenderer()

        fun DrawDebugLine(start: Vector2, end: Vector2, white: Color, projectionMatrix: Matrix4) {
            Gdx.gl.glLineWidth(8f)
            debugRenderer.projectionMatrix = projectionMatrix
            debugRenderer.begin(ShapeRenderer.ShapeType.Line)
            debugRenderer.color = white
            debugRenderer.line(start, end)
            debugRenderer.end()
            Gdx.gl.glLineWidth(1f)
        }
    }
}