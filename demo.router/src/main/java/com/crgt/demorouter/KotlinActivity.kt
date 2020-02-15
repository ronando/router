package com.crgt.demorouter

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.crgt.router.RouterPath


/**
 * Created by lujie on 2020/2/15.
 * jesse.lu@crgecent.com
 */
@RouterPath(path = ["app_kotlin_activity"])
class KotlinActivity : AppCompatActivity(){


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}