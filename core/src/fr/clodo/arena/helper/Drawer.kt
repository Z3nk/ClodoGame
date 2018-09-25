package fr.clodo.arena.helper

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType
import com.badlogic.gdx.math.Matrix4
import com.badlogic.gdx.math.Vector2


abstract class Drawer{
    companion object {
        fun DrawDebugLine(projectionMatrix: Matrix4) {
            val sr = ShapeRenderer()
            sr.color = Color.BLACK
            sr.projectionMatrix = projectionMatrix

            sr.begin(ShapeType.Filled)
            sr.rectLine(0f, 0f,
                   100f, 100f, 2f)
            sr.end()
        }

        fun DrawDebugLine(start: Vector2, end: Vector2, projectionMatrix: Matrix4) {
            Gdx.gl.glLineWidth(8f)
            val debugRenderer = ShapeRenderer()
            debugRenderer.projectionMatrix = projectionMatrix
            debugRenderer.begin(ShapeRenderer.ShapeType.Line)
            debugRenderer.color = Color.WHITE
            debugRenderer.line(start, end)
            debugRenderer.end()
            Gdx.gl.glLineWidth(1f)
        }
    }
}