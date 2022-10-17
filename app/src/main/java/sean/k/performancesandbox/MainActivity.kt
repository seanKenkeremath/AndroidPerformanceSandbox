package sean.k.performancesandbox

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import sean.k.performancesandbox.databinding.ActivityMainBinding
import sean.k.performancesandbox.scenario.concurrency.CoroutinesActivity
import sean.k.performancesandbox.scenario.concurrency.LooseThreadsActivity
import sean.k.performancesandbox.scenario.concurrency.ThreadPoolActivity
import sean.k.performancesandbox.scenario.memory.MemoryLeakActivity
import sean.k.performancesandbox.scenario.memory.ObjectChurnActivity

class MainActivity : AppCompatActivity() {

    companion object {
        private const val PARALLEL_COUNT = 32
        private const val THREAD_DELAY = 2000L
        private const val THREADPOOL_SIZE = 4
    }

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setScenarioClickListeners()
    }

    private fun setScenarioClickListeners() {
        /**
         * Concurrency
         */
        binding.threads.setOnClickListener {
            startActivity(
                LooseThreadsActivity.getStartIntent(
                    this,
                    loop = false,
                    parallelCount = PARALLEL_COUNT,
                    delayTime = THREAD_DELAY,
                    leaky = false
                )
            )
        }
        binding.threadPool.setOnClickListener {
            startActivity(
                ThreadPoolActivity.getStartIntent(
                    this,
                    loop = false,
                    parallelCount = PARALLEL_COUNT,
                    threadPoolSize = THREADPOOL_SIZE,
                    delayTime = THREAD_DELAY,
                    leaky = false
                )
            )
        }
        binding.coroutines.setOnClickListener {
            startActivity(
                CoroutinesActivity.getStartIntent(
                    this,
                    loop = false,
                    parallelCount = PARALLEL_COUNT,
                    delayTime = THREAD_DELAY,
                    leaky = false
                )
            )
        }
        binding.loopingThreads.setOnClickListener {
            startActivity(
                LooseThreadsActivity.getStartIntent(
                    this,
                    loop = true,
                    parallelCount = PARALLEL_COUNT,
                    delayTime = THREAD_DELAY,
                    leaky = false
                )
            )
        }
        binding.loopingThreadPool.setOnClickListener {
            startActivity(
                ThreadPoolActivity.getStartIntent(
                    this,
                    loop = true,
                    parallelCount = PARALLEL_COUNT,
                    threadPoolSize = 4,
                    delayTime = THREAD_DELAY,
                    leaky = false
                )
            )
        }
        binding.loopingCoroutines.setOnClickListener {
            startActivity(
                CoroutinesActivity.getStartIntent(
                    this,
                    loop = true,
                    parallelCount = PARALLEL_COUNT,
                    delayTime = THREAD_DELAY,
                    leaky = false
                )
            )
        }
        binding.leakyThreads.setOnClickListener {
            startActivity(
                LooseThreadsActivity.getStartIntent(
                    this,
                    loop = true,
                    parallelCount = PARALLEL_COUNT,
                    delayTime = THREAD_DELAY,
                    leaky = true
                )
            )
        }
        binding.leakyThreadPool.setOnClickListener {
            startActivity(
                ThreadPoolActivity.getStartIntent(
                    this,
                    loop = true,
                    parallelCount = PARALLEL_COUNT,
                    threadPoolSize = 4,
                    delayTime = THREAD_DELAY,
                    leaky = true
                )
            )
        }
        binding.leakyCoroutines.setOnClickListener {
            startActivity(
                CoroutinesActivity.getStartIntent(
                    this,
                    loop = true,
                    parallelCount = PARALLEL_COUNT,
                    delayTime = THREAD_DELAY,
                    leaky = true
                )
            )
        }

        /**
         * Memory
         */
        binding.leakedActivity.setOnClickListener {
            startActivity(
                MemoryLeakActivity.getStartIntent(this)
            )
        }
        binding.objectChurnActivity.setOnClickListener {
            startActivity(
                ObjectChurnActivity.getStartIntent(this)
            )
        }
    }

}