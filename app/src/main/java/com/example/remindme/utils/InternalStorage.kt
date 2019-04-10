package com.example.remindme.utils

import android.content.Context
import java.io.IOException
import java.io.ObjectInputStream
import java.io.ObjectOutputStream

@Throws(IOException::class)
fun writeObject(context: Context, key: String, curr_object: Any) {
    val file_out = context.openFileOutput(key, Context.MODE_PRIVATE)
    val object_stream = ObjectOutputStream(file_out)

    object_stream.writeObject(curr_object)
    object_stream.close()
    file_out.close()
}

@Throws(IOException::class, ClassNotFoundException::class)
fun readObject(context: Context, key: String): Any {
    val file_in = context.openFileInput(key)
    val file_out = ObjectInputStream(file_in)

    return file_out.readObject()
}