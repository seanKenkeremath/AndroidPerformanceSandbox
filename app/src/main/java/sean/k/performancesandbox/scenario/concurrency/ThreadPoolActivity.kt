package sean.k.performancesandbox.scenario.concurrency

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import sean.k.performancesandbox.R
import sean.k.performancesandbox.util.ConcurrencyUtils
import java.util.concurrent.ExecutorService

class ThreadPoolActivity : AppCompatActivity() {
    companion object {
        private const val EXTRA_LOOP = "extra_loop"
        private const val EXTRA_PARALLEL_COUNT = "extra_parallel_count"
        private const val EXTRA_THREADPOOL_SIZE = "extra_threadpool_size"
        private const val EXTRA_DELAY_TIME = "extra_delay_time"
        private const val EXTRA_LEAKY = "extra_leaky"

        fun getStartIntent(
            context: Context,
            loop: Boolean,
            parallelCount: Int,
            threadPoolSize: Int,
            delayTime: Long = 1000L,
            leaky: Boolean = false,
        ): Intent {
            return Intent(context, ThreadPoolActivity::class.java)
                .putExtra(EXTRA_LOOP, loop)
                .putExtra(EXTRA_PARALLEL_COUNT, parallelCount)
                .putExtra(EXTRA_THREADPOOL_SIZE, threadPoolSize)
                .putExtra(EXTRA_DELAY_TIME, delayTime)
                .putExtra(EXTRA_LEAKY, leaky)
        }
    }

    private var threadpool: ExecutorService? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loose_threads)

        threadpool = ConcurrencyUtils.createThreadPoolThreads(
            loop = intent.getBooleanExtra(
                EXTRA_LOOP,
                false
            ),
            parallelCount = intent.getIntExtra(EXTRA_PARALLEL_COUNT, 8),
            threadPoolSize = intent.getIntExtra(EXTRA_THREADPOOL_SIZE, 4),
            delayTime = intent.getLongExtra(EXTRA_DELAY_TIME, 1000L)
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        if (threadpool?.isShutdown != true && !intent.getBooleanExtra(EXTRA_LEAKY, false)) {
            threadpool?.shutdownNow()
        }
    }
}