package fr.clodo.arena.helper

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.utils.Array

abstract class Animator {
    companion object {
        const val TAG = "Animator"

        fun generateArray(spriteSheet: String, startRow: Int, endRow: Int, startCol: Int, endCol: Int, frameX: Int, frameY: Int, width: Int, height: Int): Array<TextureRegion> {

            val tmp = TextureRegion.split(Texture(spriteSheet), frameX, frameY)


            var currentRow = startRow //3
            var currentCol = startCol //2
            var list = Array<TextureRegion>()
            //  4 1
            while (currentRow < endRow || currentCol < endCol) {
                try {
                    list.add(tmp[currentRow][currentCol])
                } catch (e: Exception) {
                    var i = 2
                }
                currentCol++
                if (currentRow == endRow && currentCol == endCol) {
                    Gdx.app.error(TAG, "Done")
                    return list
                }
                if (currentCol >= tmp[currentRow].size) {
                    currentCol = 0
                    currentRow++
                }
            }

            return list

        }
    }
}