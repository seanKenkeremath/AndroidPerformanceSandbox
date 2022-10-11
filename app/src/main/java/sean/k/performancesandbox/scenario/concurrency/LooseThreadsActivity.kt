package sean.k.performancesandbox.scenario.concurrency

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import sean.k.performancesandbox.R
import sean.k.performancesandbox.util.ConcurrencyUtils

class LooseThreadsActivity : AppCompatActivity() {

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
            return Intent(context, LooseThreadsActivity::class.java)
                .putExtra(EXTRA_LOOP, loop)
                .putExtra(EXTRA_PARALLEL_COUNT, parallelCount)
                .putExtra(EXTRA_DELAY_TIME, delayTime)
                .putExtra(EXTRA_LEAKY, leaky)
        }
    }

    private val threads = mutableListOf<Thread>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loose_threads)

        threads.addAll(
            ConcurrencyUtils.createLooseThreads(
                loop = intent.getBooleanExtra(
                    EXTRA_LOOP,
                    false
                ), parallelCount = intent.getIntExtra(EXTRA_PARALLEL_COUNT, 8),
                delayTime = intent.getLongExtra(EXTRA_DELAY_TIME, 1000L)
            )
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        if (!intent.getBooleanExtra(EXTRA_LEAKY, false)) {
            for (t in threads) {
                t.interrupt()
            }
        }
    }
}