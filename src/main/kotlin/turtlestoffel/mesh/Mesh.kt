package turtlestoffel.mesh

import org.lwjgl.opengl.GL11.GL_TRIANGLES
import org.lwjgl.opengl.GL11.GL_UNSIGNED_INT
import org.lwjgl.opengl.GL11.glDrawElements
import org.lwjgl.opengl.GL20.glDisableVertexAttribArray
import org.lwjgl.opengl.GL20.glEnableVertexAttribArray
import org.lwjgl.opengl.GL30.glBindVertexArray

class Mesh private constructor(
    private val vao: Int,
    private val vertexCount: Int,
) : IMesh {
    companion object {
        fun build(rawMesh: RawMesh): Mesh {
            var denormalizedMesh =
                RawMesh(
                    vertices = rawMesh.indices.map { rawMesh.vertices[it] },
                    indices = rawMesh.indices.indices.toList(),
                )
            denormalizedMesh = generateFlatShadingMesh(denormalizedMesh)

            val vao = generateVao(denormalizedMesh.vertices, denormalizedMesh.indices)
            return Mesh(vao, denormalizedMesh.indices.size)
        }
    }

    override fun render() {
        glBindVertexArray(vao)
        glEnableVertexAttribArray(0)
        glDrawElements(GL_TRIANGLES, vertexCount, GL_UNSIGNED_INT, 0)
        glDisableVertexAttribArray(0)
        glBindVertexArray(0)
    }
}
