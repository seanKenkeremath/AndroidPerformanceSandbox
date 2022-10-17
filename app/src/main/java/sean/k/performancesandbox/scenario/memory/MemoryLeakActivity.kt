package sean.k.performancesandbox.scenario.memory

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import sean.k.performancesandbox.PerformanceSandboxApplication
import sean.k.performancesandbox.R

class MemoryLeakActivity : AppCompatActivity() {

    companion object {
        fun getStartIntent(
            context: Context,
        ): Intent {
            return Intent(context, MemoryLeakActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_memory_leak)
        (application as PerformanceSandboxApplication).leakedActivities.add(this)
    }
}