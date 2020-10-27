package com.gmail2548sov.sqlitecontact

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.OnClickListener
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*

class MainActivity : AppCompatActivity(), OnClickListener {
    val LOG_TAG: String = "myLogs"
    lateinit var dbHelper: DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        dbHelper = DBHelper(this);
        Log.d(LOG_TAG, "---")
        btnAdd.setOnClickListener(this)
        btnRead.setOnClickListener(this)
        btnClear.setOnClickListener(this)


    }

    override fun onClick(v: View?) {
        val name: String = etName.text.toString()
        val email: String = etEmail.text.toString()
        val cv: ContentValues = ContentValues()
        val db: SQLiteDatabase = dbHelper.writableDatabase

        when (v?.id) {
            btnAdd.id -> {
                Log.d(LOG_TAG, "--- Insert in mytable: ---")
                cv.put("name", name)
                cv.put("email", email)
                val rowId: Long = db.insert("mytable", null, cv)
                Log.d(LOG_TAG, "row inserted, ID = $rowId")


            }
            btnRead.id -> {
                Log.d(LOG_TAG, "--- Rows in mytable: ---")
                val c: Cursor = db.query("MyTable", null, null, null, null, null, null)
                if (c.moveToFirst()) {
                    val idColIndex: Int = c.getColumnIndex("id")
                    val nameColIndex: Int = c.getColumnIndex("name")
                    val emailColIndex: Int = c.getColumnIndex("email")
                    do {
                        Log.d(LOG_TAG, "ID = " + c.getInt(idColIndex) + ", name = " + c.getString(nameColIndex) + ", email = " + c.getString(emailColIndex))

                    } while (c.moveToNext());

                } else Log.d(LOG_TAG, "0 rows")

            }
            btnClear.id -> {
                Log.d(LOG_TAG, "--- Clear mytable: ---")
                val clearCount = db.delete("mytable", null, null)
                Log.d(LOG_TAG, "deleted rows count = " + clearCount)

            }
        }
        dbHelper.close()
    }


    inner class DBHelper(context: Context) : SQLiteOpenHelper(context, "myDB", null, 1) {
        override fun onCreate(db: SQLiteDatabase?) {
            Log.d(LOG_TAG, "--- onCreate database ---")
            db?.execSQL("create table myTable "
                    + "(" + "id integer primary key autoincrement,"
                    + "name text, "
                    + "email text"
                    + ");")
        }

        override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
            TODO("Not yet implemented")
        }

    }

}


