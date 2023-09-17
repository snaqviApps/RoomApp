package learn.ghar.androidapps.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import learn.ghar.androidapps.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}