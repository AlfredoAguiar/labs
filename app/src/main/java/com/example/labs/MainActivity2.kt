package com.example.labs

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast

class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        val buttonClick= findViewById<Button>(R.id.button2)
        buttonClick.setOnClickListener {
            val toast = Toast.makeText(this, "Existing", Toast.LENGTH_SHORT)
            toast.show()

            finish()
        }
    }
}