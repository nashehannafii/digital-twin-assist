package com.example.digitaltwinpersonalassist.services

import android.opengl.GLES20
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer

/**
 * Model3D sederhana yang menggambar sebuah segitiga menggunakan OpenGL ES 2.0.
 *
 * Kelas ini menyiapkan shader, buffer vertex, dan menyediakan fungsi `draw()`
 * untuk menggambar segitiga berwarna.
 */
class Model3D {

    private val triangleCoords = floatArrayOf(
        0.0f, 0.5f, 0.0f,  // Atas
        -0.5f, -0.5f, 0.0f, // Kiri
        0.5f, -0.5f, 0.0f   // Kanan
    )

    // Warna RGBA untuk segitiga
    private val color = floatArrayOf(0.63671875f, 0.76953125f, 0.22265625f, 1.0f)

    private val vertexBuffer: FloatBuffer = ByteBuffer.allocateDirect(triangleCoords.size * 4).run {
        order(ByteOrder.nativeOrder())
        asFloatBuffer().apply {
            put(triangleCoords)
            position(0)
        }
    }

    private val vertexShaderCode =
        """
        attribute vec4 vPosition;
        void main() {
            gl_Position = vPosition;
        }
        """.trimIndent()

    private val fragmentShaderCode =
        """
        precision mediump float;
        uniform vec4 vColor;
        void main() {
            gl_FragColor = vColor;
        }
        """.trimIndent()

    private val program: Int

    init {
        val vertexShader = loadShader(GLES20.GL_VERTEX_SHADER, vertexShaderCode)
        val fragmentShader = loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode)

        // Membuat program OpenGL ES
        program = GLES20.glCreateProgram().also {
            GLES20.glAttachShader(it, vertexShader)
            GLES20.glAttachShader(it, fragmentShader)
            GLES20.glLinkProgram(it)
        }
    }

    /**
     * Menggambar segitiga ke layar menggunakan program/shader yang telah dibuat.
     */
    fun draw() {
        // Gunakan program yang sudah dibuat
        GLES20.glUseProgram(program)

        // Dapatkan lokasi atribut posisi
        val positionHandle = GLES20.glGetAttribLocation(program, "vPosition").also {
            GLES20.glEnableVertexAttribArray(it)
            GLES20.glVertexAttribPointer(it, 3, GLES20.GL_FLOAT, false, 12, vertexBuffer)
        }

        // Set warna ke uniform
        val colorHandle = GLES20.glGetUniformLocation(program, "vColor").also {
            GLES20.glUniform4fv(it, 1, color, 0)
        }

        // Gambar segitiga
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, 3)

        // Nonaktifkan atribut posisi
        GLES20.glDisableVertexAttribArray(positionHandle)
    }

    /**
     * Membuat dan meng-compile shader dari source code.
     *
     * @param type Tipe shader (vertex/fragment), mis. `GLES20.GL_VERTEX_SHADER`.
     * @param shaderCode Source code shader GLSL.
     * @return handle shader yang telah dibuat.
     */
    private fun loadShader(type: Int, shaderCode: String): Int {
        return GLES20.glCreateShader(type).also { shader ->
            GLES20.glShaderSource(shader, shaderCode)
            GLES20.glCompileShader(shader)
        }
    }

}