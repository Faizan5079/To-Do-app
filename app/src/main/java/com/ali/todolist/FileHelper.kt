package com.ali.todolist

import android.content.Context
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream

class FileHelper {
    val FILENAME = "listinfo.dat"
    fun writeData(item: ArrayList<String>,context: Context)
    {
        var fos : FileOutputStream= context.openFileOutput(FILENAME,Context.MODE_PRIVATE)
        var oas = ObjectOutputStream(fos)
        oas.writeObject(item)
        oas.close()
    }
    fun readData(context: Context):ArrayList<String>
    {
        var itemlist : ArrayList<String>
        try {
            var fis : FileInputStream = context.openFileInput(FILENAME)
            var ois = ObjectInputStream(fis)
            itemlist= ois.readObject() as ArrayList<String>
        }catch (e:FileNotFoundException){
            itemlist=ArrayList()
        }

        return itemlist

    }
}