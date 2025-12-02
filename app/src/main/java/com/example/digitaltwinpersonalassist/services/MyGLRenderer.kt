package com.example.digitaltwinpersonalassist.services

import android.opengl.GLES20
import android.opengl.GLSurfaceView
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

/**
 * Renderer OpenGL ES sederhana yang menggunakan `Model3D` untuk menggambar
 * objek 3D (saat ini berupa segitiga) di `GLSurfaceView`.
 */
class MyGLRenderer : GLSurfaceView.Renderer {

    private lateinit var model3D: Model3D

    /**
     * Dipanggil saat surface OpenGL dibuat. Mengatur warna clear dan
     * menginisialisasi model 3D.
     */
    override fun onSurfaceCreated(p0: GL10?, p1: EGLConfig?) {
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f)
        model3D = Model3D()
    }

    /**
     * Dipanggil saat ukuran surface berubah; mengatur viewport OpenGL.
     */
    override fun onSurfaceChanged(p0: GL10?, width: Int, height: Int) {
        GLES20.glViewport(0, 0, width, height)
    }

    /**
     * Dipanggil setiap frame untuk menggambar; membersihkan buffer dan
     * memanggil `model3D.draw()`.
     */
    override fun onDrawFrame(p0: GL10?) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT)
        model3D.draw()

    }
}