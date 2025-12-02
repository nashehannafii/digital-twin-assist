package com.example.digitaltwinpersonalassist.services.models

import java.io.Serializable

/**
 * Model data sederhana untuk respon hello dari API.
 *
 * @property message Pesan string yang dikembalikan server.
 */
data class HelloModel (
    var message: String?
) : Serializable
