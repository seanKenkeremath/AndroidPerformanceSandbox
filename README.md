# AndroidPerformanceSandbox

Various scenarios to explore with Android performance profiling tools

# Purpose

This repository can be used to learn and teach Android performance optimization. For example:

* Observe the effect various performance issues can have on the end user
* Practice using profiling tools (systrace, perfetto, Android Profiler, etc) to observe issues in various scenarios
* Tinker with parameters or logic to gain a better understanding of Android
* Benchmark to compare various optimization strategies
* Compare how different devices react to performance issues

# Scenarios

More will be added soon. For now there are several different concurrency related scenarios.

## Concurrency

Examples are provided of Activities executing the same work in parallel in various ways.

* `Threads` - X threads are created and executed. In the default case these are interrupted in `onDestroy`
* `Thread Pool` - X callables are executed on a Thread Pool of size Y. In the default case the pool is shut down in `onDestroy`
* `Coroutines` - X coroutines are launched using the default dispatcher. In the default case they are canceled in `onDestory`

Scenarios marked as `Looping` will execute threads/coroutines that loop forever with a delay between work. These will still be cleaned up when the Activity is destroyed.
Scenarios marked as `Leaky` will loop and will not be torn down in `onDestroy`

## Memory

`Leaked Activity` will instantiate a new activity and added a reference to the Application subclass, thus creating a memory leak.

`Object Churn` is a scenario that allows you to create a large number of churned objects to trigger garbage collection. An indeterminate progress bar is rendered to help visualize UI thread choppiness from GC.

# TODO

* Profileable flag
* Display stats and descriptions for each scenario
* Complex view hierarchy scenario
* RV scenario
* Compose scenarios
* Android Macro/Micro benchmarking library
