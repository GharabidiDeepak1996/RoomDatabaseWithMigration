package com.example.roomdatabase

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.roomdatabase.database.UserDatabase
import com.example.roomdatabase.databinding.ActivityMainBinding
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.toast
import org.jetbrains.anko.uiThread

class MainActivity : AppCompatActivity(), LifecycleOwner {
    private val TAG: String="MainActivity"
    private lateinit var bindingMainBinding: ActivityMainBinding
    private lateinit var userDataBase: UserDatabase
    private var mUserDataList=ArrayList<UserData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingMainBinding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(bindingMainBinding.root)

        // Initialize the room database
        userDataBase = UserDatabase.getInstance(applicationContext)

        bindingMainBinding.floatingButton.setOnClickListener(onFloatingButtonClick)

        doAsync {
            //get user data
           mUserDataList.addAll( userDataBase.userDataDao().getAll())
        }

        bindingMainBinding.recyclerView.layoutManager=LinearLayoutManager(this)
        bindingMainBinding.recyclerView.adapter=UserDataAdapter(this@MainActivity,mUserDataList)
    }

    private var onFloatingButtonClick: View.OnClickListener=View.OnClickListener {
        //Inflate the dialog with custom view
        val mDialogView = LayoutInflater.from(this).inflate(R.layout.add_data_dialog, null)
        val mBuilder = AlertDialog.Builder(this)
            .setView(mDialogView)
            .setTitle("Data Form")
        //show dialog
        val  mAlertDialog=mBuilder.show()

        //button submit
        val submitButton=mDialogView.findViewById<Button>(R.id.dialogLoginBtn)
        submitButton.setOnClickListener {
            val name=mDialogView.findViewById<EditText>(R.id.dialogNameEt)
            val gender=mDialogView.findViewById<EditText>(R.id.dialogGenderEt)

            doAsync {
                // Put the userdata in database
                userDataBase.userDataDao().insert(UserData(id = null,name.text.toString(),gender.text.toString()))

                mUserDataList.add(UserData(id = null,name.text.toString(),gender.text.toString()))
                bindingMainBinding.recyclerView.adapter=UserDataAdapter(this@MainActivity,mUserDataList)

                uiThread {
                    toast("One record inserted.")
                }
            }

            Log.d(TAG, "onCreate12: ${mUserDataList.size}")
            mAlertDialog.dismiss()
        }
        //button cancel
        val cancelButton=mDialogView.findViewById<Button>(R.id.dialogCancelBtn)
        cancelButton.setOnClickListener {
            //dismiss dialog
            mAlertDialog.dismiss()
        }
        //cancel button click of custom layout
    }
}