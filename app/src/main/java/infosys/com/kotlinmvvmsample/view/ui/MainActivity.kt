package infosys.com.kotlinmvvmsample.view.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import infosys.com.kotlinmvvmsample.R
import infosys.com.kotlinmvvmsample.extensions.addFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val factFragment = FactListFragment()
        // Added Fragment
        addFragment(factFragment, R.id.fragment_container)
    }
}
