package sean.k.performancesandbox.util

import android.util.Log
import kotlinx.coroutines.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

object ConcurrencyUtils {

    const val LOG_TAG = "perf_debug"

    fun createCoroutines(
        scope: CoroutineScope,
        dispatcher: CoroutineDispatcher = Dispatchers.Default,
        loop: Boolean = false,
        parallelCount: Int = 4,
        delayTime: Long = 1000L
    ): List<Job> {
        val output = mutableListOf<Job>()
        for (i in 0 until parallelCount) {
            val job = scope.launch {
                withContext(dispatcher) {
                    var x = 0
                    while (loop || x == 0) {
                        for (j in 0 until 1000000) {
                            for (k in 0 until 1000) {
                                x++
                            }
                        }
                        Log.d(LOG_TAG, "Thread ${i} work complete")
                        delay(delayTime)
                    }
                }
            }
            output.add(job)
        }
        return output
    }

    fun createThreadPoolThreads(
        loop: Boolean = false,
        parallelCount: Int = 4,
        threadPoolSize: Int = 4,
        delayTime: Long = 1000L
    ): ExecutorService {
        val executor = Executors.newFixedThreadPool(threadPoolSize)
        for (i in 0 until parallelCount) {
            executor.execute {
                do {
                    var interrupted = false
                    try {
                        performWork()
                        Log.d(LOG_TAG, "Thread ${i} work complete")
                        Thread.sleep(delayTime)
                    } catch (e: InterruptedException) {
                        interrupted = true
                    }
                } while (loop && !interrupted)
            }
        }

        return executor
    }

    fun createLooseThreads(
        loop: Boolean = false,
        parallelCount: Int = 4,
        delayTime: Long = 1000L
    ): List<Thread> {
        val output = mutableListOf<Thread>()
        for (i in 0 until parallelCount) {
            val thread = Thread {
                var interrupted = false
                do {
                    try {
                        performWork()
                        Thread.sleep(delayTime)
                        Log.d(LOG_TAG, "Thread ${i} work complete")
                    } catch (e: InterruptedException) {
                        interrupted = true
                    }
                } while (loop && !interrupted)
            }
            thread.start()
            output.add(thread)
        }
        return output
    }

    private fun performWork() {
        var x = 0
        for (j in 0 until 1000000) {
            for (k in 0 until 1000) {
                x++
            }
        }
    }
}