package com.example.digitaltwinpersonalassist.services

import android.content.Context
import com.example.digitaltwinpersonalassist.R
import org.rajawali3d.lights.DirectionalLight
import org.rajawali3d.loader.LoaderOBJ
import org.rajawali3d.loader.ParsingException
import org.rajawali3d.math.vector.Vector3
import android.os.Bundle
import org.rajawali3d.view.SurfaceView
//import org.rajawali3d.renderer.RajawaliRenderer
import org.rajawali3d.scene.Scene
import org.rajawali3d.materials.Material
import org.rajawali3d.primitives.Sphere


//class Heart3DRenderer(context: Context) : RajawaliRenderer(context) {
//    override fun initScene() {
//        // Tambahkan pencahayaan
//        val light = DirectionalLight(1.0, 0.2, -1.0)
//        light.setPower(2.0)
//        currentScene.addLight(light)
//
//        // Muat model 3D jantung
//        try {
//            val loader = LoaderOBJ(context.resources, textureManager, R.raw.heart_model) // ganti sesuai model Anda
//            loader.parse()
//            val heartObject = loader.parsedObject
//            heartObject.position = Vector3(0.0, 0.0, -5.0)
//            heartObject.rotation.y = 180.0
//            currentScene.addChild(heartObject)
//
//            // Tambahkan kontrol kamera untuk rotasi
//            val camera = currentCamera
//            camera.setLookAt(heartObject.position)
//        } catch (e: ParsingException) {
//            e.printStackTrace()
//        }
//    }

//}