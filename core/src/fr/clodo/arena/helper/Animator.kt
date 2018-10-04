package fr.clodo.arena.helper

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.utils.Array

abstract class Animator {
    companion object {
        const val TAG = "Animator"

        fun generateArray(spriteSheet: Texture, startRow: Int, endRow: Int, startCol: Int, endCol: Int, frameX: Int, frameY: Int, width: Int, height: Int): Array<TextureRegion> {

            val tmp = TextureRegion.split(spriteSheet, frameX, frameY)


            var currentRow = startRow
            var currentCol = startCol
            var list = Array<TextureRegion>()
            //  4 1
            while (currentRow < endRow || currentCol < endCol) {
                list.add(tmp[currentRow][currentCol])
                currentCol++
                if (currentRow == endRow && currentCol == endCol) {
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