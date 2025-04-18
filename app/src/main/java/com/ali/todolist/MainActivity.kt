package com.ali.todolist

import android.content.DialogInterface
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.appcompat.widget.Toolbar


class MainActivity : AppCompatActivity() {
    data class ToDoItem(
        val task: String,
        var isChecked: Boolean = false  // Default unchecked
    )
//change 1
    class MainActivity : AppCompatActivity() {
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_main)

            // Find the toolbar
            val toolbar: Toolbar = findViewById(R.id.toolbar)
            setSupportActionBar(toolbar)

            // Optionally, enable back button
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setHomeAsUpIndicator(android.R.drawable.ic_menu_close_clear_cancel)
        }
    }

    lateinit var item : EditText
    lateinit var add : Button
    lateinit var listview : ListView

    var itemList = ArrayList<String>()
    var fileHelper = FileHelper()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        item=findViewById(R.id.edittext)
        add=findViewById(R.id.button)
        listview=findViewById(R.id.list)

         itemList= fileHelper.readData(this)
        var arrayAdapter =ArrayAdapter(this , android.R.layout.simple_list_item_1, android.R.id.text1,itemList)
        listview.adapter = arrayAdapter
        add.setOnClickListener {
            var itemName : String = item.text.toString()
itemList.add(itemName)
        item.setText("")
            fileHelper.writeData(itemList,applicationContext)
            arrayAdapter.notifyDataSetChanged()
        }
listview.setOnItemClickListener { adapterView, view, position, l ->
    var alert = AlertDialog.Builder(this)
    alert.setTitle("Delete")
    alert.setMessage("Do you want to delete this item from the list?")
    alert.setCancelable(false)
    alert.setNegativeButton("No",DialogInterface.OnClickListener{dialogInterface, i ->
        dialogInterface.cancel()
    })
    alert.setPositiveButton("Yes",DialogInterface.OnClickListener{dialogInterface, i ->
        itemList.removeAt(position)
        arrayAdapter.notifyDataSetChanged()
fileHelper.writeData(itemList,applicationContext)
    })
    alert.create().show()
}

    }
}