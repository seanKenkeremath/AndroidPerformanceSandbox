package sean.k.performancesandbox.scenario.concurrency

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import sean.k.performancesandbox.R
import sean.k.performancesandbox.util.ConcurrencyUtils

class CoroutinesActivity : AppCompatActivity() {

    companion object {
        private const val EXTRA_LOOP = "extra_loop"
        private const val EXTRA_PARALLEL_COUNT = "extra_parallel_count"
        private const val EXTRA_DELAY_TIME = "extra_delay_time"
        private const val EXTRA_LEAKY = "extra_leaky"

        fun getStartIntent(
            context: Context,
            loop: Boolean,
            parallelCount: Int,
            delayTime: Long = 1000L,
            leaky: Boolean = false,
        ): Intent {
            return Intent(context, CoroutinesActivity::class.java)
                .putExtra(EXTRA_LOOP, loop)
                .putExtra(EXTRA_PARALLEL_COUNT, parallelCount)
                .putExtra(EXTRA_DELAY_TIME, delayTime)
                .putExtra(EXTRA_LEAKY, leaky)
        }
    }

    private val jobs = mutableListOf<Job>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coroutines)
        jobs.addAll(
            ConcurrencyUtils.createCoroutines(
                //Use GlobalScope instead of lifecycleScope so we can force a leak
                scope = GlobalScope,
                dispatcher = Dispatchers.Default,
                loop = intent.getBooleanExtra(EXTRA_LOOP, false),
                delayTime = intent.getLongExtra(EXTRA_DELAY_TIME, 1000L),
                parallelCount = intent.getIntExtra(
                    EXTRA_PARALLEL_COUNT, 8
                )
            )
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        if (!intent.getBooleanExtra(EXTRA_LEAKY, false)) {
            for (job in jobs) {
                job.cancel()
            }
        }
    }
}