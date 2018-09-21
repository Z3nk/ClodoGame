package fr.clodo.arena.helper

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.utils.Array

abstract class Animator{
    companion object {
        const val TAG = "Animator"

        fun generateArray(spriteSheet: String, startRow: Int, endRow: Int, startCol: Int, endCol: Int, frameX: Int, frameY: Int, width: Int, height: Int): Array<TextureRegion> {

            val nbCol = width / frameX
            val nbRow = height / frameY
            val tmp = TextureRegion.split(Texture(spriteSheet), frameX, frameY)


            var currentRow = startRow
            var currentCol = startCol
            var list = Array<TextureRegion>()

            while (currentCol < endCol || currentRow < endRow) {
                list.add(tmp[currentRow][currentCol])
                currentCol++
                if (currentCol > nbCol) {
                    currentCol = 0
                    currentRow++
                }
                if(currentRow > nbRow){
                    Gdx.app.error(TAG, "SHIT HAPPENS")
                    break
                }
            }

            return list
        }
    }
}