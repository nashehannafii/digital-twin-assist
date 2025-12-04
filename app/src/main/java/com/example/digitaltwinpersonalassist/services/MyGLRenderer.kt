package com.example.digitaltwinpersonalassist.services

import android.opengl.GLES20
import android.opengl.GLSurfaceView
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

/**
 * Simple OpenGL ES renderer that uses `Model3D` to draw a 3D object (currently
 * a triangle) on a `GLSurfaceView`.
 */
class MyGLRenderer : GLSurfaceView.Renderer {

    private lateinit var model3D: Model3D

    /**
     * Called when the OpenGL surface is created. Sets the clear color and
     * initializes the 3D model.
     */
    override fun onSurfaceCreated(p0: GL10?, p1: EGLConfig?) {
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f)
        model3D = Model3D()
    }

    /**
     * Called when the surface size changes; sets the OpenGL viewport.
     */
    override fun onSurfaceChanged(p0: GL10?, width: Int, height: Int) {
        GLES20.glViewport(0, 0, width, height)
    }

    /**
     * Called each frame to draw; clears buffers and calls `model3D.draw()`.
     */
    override fun onDrawFrame(p0: GL10?) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT)
        model3D.draw()

    }
}