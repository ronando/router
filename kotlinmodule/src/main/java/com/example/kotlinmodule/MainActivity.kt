package com.example.kotlinmodule
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.crgt.router.RouterPath

@RouterPath(path = ["kotlinmodule/mainpage"])
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
