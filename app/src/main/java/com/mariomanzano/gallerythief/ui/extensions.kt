package com.mariomanzano.gallerythief.ui

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Environment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.net.URL

suspend fun Context.saveImageToDevice(url: String) = withContext(Dispatchers.Default){
    var bitmap : Bitmap? = null
    try {
        val urlBuild = URL(url)
        bitmap = BitmapFactory.decodeStream(urlBuild.openConnection().getInputStream())
    } catch (e: IOException) {
        e.printStackTrace()
    }
    var outputStream: FileOutputStream? = null
    val file = this@saveImageToDevice.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
    val dir = File(file?.absolutePath + "/ImagesFromGalleryThief")
    dir.mkdirs()
    val filename = String.format("%d.png", System.currentTimeMillis())
    val outFile = File(dir, filename)
    try {
        outputStream = FileOutputStream(outFile)
    } catch (e: Exception) {
        e.printStackTrace()
    }
    bitmap?.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
    try {
        outputStream?.flush()
    } catch (e: Exception) {
        e.printStackTrace()
    }
    try {
        outputStream?.close()
    } catch (e: Exception) {
        e.printStackTrace()
    }
}