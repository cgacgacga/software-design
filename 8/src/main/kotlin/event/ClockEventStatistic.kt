package event

import clock.Clock

class ClockEventStatistic(private val clock: Clock) : EventsStatistics {
    private val events: MutableMap<String, MutableList<Long>> = mutableMapOf()

    companion object {
        private const val MINUTES_IN_HOUR: Long = 60
        private const val SECONDS_IN_HOUR: Long = 3600
    }

    override fun incEvent(name: String) {
        val now = clock.now()
        if (!events.containsKey(name)) {
            events[name] = mutableListOf()
        }
        events[name]!!.add(now.epochSecond)
    }

    override fun getEventStatisticsByName(name: String): Double {
        if (!events.containsKey(name)) {
            return 0.0
        }
        val now = clock.now().epochSecond
        val eventsByName: List<Long> = events[name]!!
        val hourAgo = (now - SECONDS_IN_HOUR).coerceAtLeast(0L)
        val eventsByNameLastHourCount = eventsByName.count { it in hourAgo..now }
        return eventsByNameLastHourCount / MINUTES_IN_HOUR.toDouble()
    }

    override fun getAllEventStatistics() = events.map { (key, _) -> key to getEventStatisticsByName(key) }.toMap()

    override fun printStatistics() {
        events.forEach { (key, value) ->
            val correctEventsCount = value.count { t: Long -> t >= 0 }
            System.out.printf("Event: %s, rpm: %f%n", key, correctEventsCount / MINUTES_IN_HOUR.toDouble())
        }
    }
}