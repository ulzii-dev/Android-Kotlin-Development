package com.sw06d120.miuquiz

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sw06d120.miuquiz.ui.main.fragments.MainFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

//        val navController = Navigation.findNavController(this,R.id.main)
//        NavigationUI.setupActionBarWithNavController(this,navController)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }
    }

//    override fun onSupportNavigateUp(): Boolean {
//        return NavigationUI.navigateUp(Navigation.findNavController(this, R.id.quiz_nav), null)
//    }
}