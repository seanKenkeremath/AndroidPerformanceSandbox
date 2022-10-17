package sean.k.performancesandbox.scenario.memory

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import sean.k.performancesandbox.R
import sean.k.performancesandbox.data.Thing
import sean.k.performancesandbox.databinding.ActivityObjectChurnBinding

class ObjectChurnActivity : AppCompatActivity() {

    companion object {
        private const val CHURN_SIZE = 1_000_000

        fun getStartIntent(
            context: Context,
        ): Intent {
            return Intent(context, ObjectChurnActivity::class.java)
        }
    }

    private lateinit var binding: ActivityObjectChurnBinding
    private var churnCount = 0L
    private val activeObjects = mutableListOf<Thing>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityObjectChurnBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.churnDescription.text =
            getString(R.string.object_churn_activity_description_format, CHURN_SIZE)

        createObjects()

        binding.churnButton.setOnClickListener {
            createObjects()
        }
    }

    private fun createObjects() {
        activeObjects.clear()
        for (i in 0 until CHURN_SIZE) {
            activeObjects.add(
                Thing(
                    name = "A Thing",
                    description = "This is a thing",
                    weight = i * 1.01
                )
            )
            churnCount++
        }
        binding.churnCount.text = getString(R.string.object_churn_activity_count_format, churnCount)
    }
}