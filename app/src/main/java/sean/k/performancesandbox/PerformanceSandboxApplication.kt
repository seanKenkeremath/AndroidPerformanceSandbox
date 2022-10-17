package sean.k.performancesandbox

import android.app.Activity
import android.app.Application

class PerformanceSandboxApplication: Application() {
    val leakedActivities = mutableSetOf<Activity>()
}