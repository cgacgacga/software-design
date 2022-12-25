package event

import clock.SetableClock
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import java.time.Instant
import kotlin.math.abs


class ClockEventStatisticsTest {
    private fun equals(left: Double, right: Double): Boolean {
        return abs(left - right) <= EPSILON
    }

    private fun prepare() = ClockEventStatistic(SetableClock(Instant.ofEpochSecond(0L))) to "event"

    @Test
    fun `clock event statistic fixed time no events`() {
        val (statsManager, eventName) = prepare()

        assertTrue(equals(statsManager.getEventStatisticsByName(eventName), 0.0))
        assertEquals(0, statsManager.getAllEventStatistics().size.toLong())
    }

    @Test
    fun `clock event statistic fixed time one event stats for non existing`() {
        val (statsManager, eventName) = prepare()
        val anotherEventName = "other event"
        statsManager.incEvent(eventName)

        assertTrue(equals(statsManager.getEventStatisticsByName(anotherEventName), 0.0))
        assertEquals(1, statsManager.getAllEventStatistics().size.toLong())
    }

    @Test
    fun `clock event statistic fixed time one event one increment`() {
        val (statsManager, eventName) = prepare()
        statsManager.incEvent(eventName)

        assertTrue(equals(statsManager.getEventStatisticsByName(eventName), 1.0 / MINUTES_IN_HOUR.toDouble()))
        assertEquals(1, statsManager.getAllEventStatistics().size.toLong())
    }

    @Test
    fun `clock event statistic fixed time one event many increments`() {
        val (statsManager, eventName) = prepare()
        for (i in 1..500) {
            statsManager.incEvent(eventName)

            assertTrue(equals(statsManager.getEventStatisticsByName(eventName), i / 60.0))
            assertEquals(1, statsManager.getAllEventStatistics().size.toLong())
        }
    }

    @Test
    fun `clock event statistic changing time no events`() {
        val clock = SetableClock(Instant.ofEpochSecond(0L))
        val statsManager = ClockEventStatistic(clock)
        val eventName = "event"

        assertTrue(equals(statsManager.getEventStatisticsByName(eventName), 0.0))
        assertEquals(0, statsManager.getAllEventStatistics().size.toLong())

        clock.now = Instant.ofEpochSecond(123)
        assertTrue(equals(statsManager.getEventStatisticsByName(eventName), 0.0))
        assertEquals(0, statsManager.getAllEventStatistics().size.toLong())

        clock.now = Instant.ofEpochSecond(3800)
        assertTrue(equals(statsManager.getEventStatisticsByName(eventName), 0.0))
        assertEquals(0, statsManager.getAllEventStatistics().size.toLong())
    }

    @Test
    fun `clock event statistic changing time one event stat for non existing`() {
        val clock = SetableClock(Instant.ofEpochSecond(0L))
        val statsManager = ClockEventStatistic(clock)
        val eventName = "event"
        val anotherEventName = "other event"
        statsManager.incEvent(eventName)

        assertTrue(equals(statsManager.getEventStatisticsByName(anotherEventName), 0.0))
        assertEquals(1, statsManager.getAllEventStatistics().size.toLong())

        clock.now = Instant.ofEpochSecond(123)
        assertTrue(equals(statsManager.getEventStatisticsByName(anotherEventName), 0.0))
        assertEquals(1, statsManager.getAllEventStatistics().size.toLong())

        clock.now = Instant.ofEpochSecond(3800)
        assertTrue(equals(statsManager.getEventStatisticsByName(anotherEventName), 0.0))
        assertEquals(1, statsManager.getAllEventStatistics().size.toLong())
    }

    @Test
    fun `clock event statistic changing time one event one increment`() {
        val clock = SetableClock(Instant.ofEpochSecond(0L))
        val statsManager = ClockEventStatistic(clock)
        val eventName = "event"
        statsManager.incEvent(eventName)

        assertTrue(equals(statsManager.getEventStatisticsByName(eventName), 1.0 / MINUTES_IN_HOUR.toDouble()))
        assertEquals(1, statsManager.getAllEventStatistics().size.toLong())

        clock.now = Instant.ofEpochSecond(123)
        assertTrue(equals(statsManager.getEventStatisticsByName(eventName), 1.0 / MINUTES_IN_HOUR.toDouble()))
        assertEquals(1, statsManager.getAllEventStatistics().size.toLong())
    }

    @Test
    fun `clock event statistic changing time one event one increment hour passed`() {
        val clock = SetableClock(Instant.ofEpochSecond(0L))
        val statsManager = ClockEventStatistic(clock)
        val eventName = "event"
        statsManager.incEvent(eventName)

        assertTrue(equals(statsManager.getEventStatisticsByName(eventName), 1.0 / MINUTES_IN_HOUR.toDouble()))
        assertEquals(1, statsManager.getAllEventStatistics().size.toLong())

        clock.now = Instant.ofEpochSecond(123)
        assertTrue(equals(statsManager.getEventStatisticsByName(eventName), 1.0 / MINUTES_IN_HOUR.toDouble()))
        assertEquals(1, statsManager.getAllEventStatistics().size.toLong())

        clock.now = Instant.ofEpochSecond(3600)
        assertTrue(equals(statsManager.getEventStatisticsByName(eventName), 1.0 / MINUTES_IN_HOUR.toDouble()))
        assertEquals(1, statsManager.getAllEventStatistics().size.toLong())

        clock.now = Instant.ofEpochSecond(3601)
        assertTrue(equals(statsManager.getEventStatisticsByName(eventName), 0.0))
        assertEquals(1, statsManager.getAllEventStatistics().size.toLong())
    }

    @Test
    fun `clock event statistic changing time one event few increment with in hour`() {
        val clock = SetableClock(Instant.ofEpochSecond(0L))
        val statsManager = ClockEventStatistic(clock)
        val eventName = "event"
        statsManager.incEvent(eventName)

        assertTrue(equals(statsManager.getEventStatisticsByName(eventName), 1.0 / MINUTES_IN_HOUR.toDouble()))
        assertEquals(1, statsManager.getAllEventStatistics().size.toLong())

        clock.now = Instant.ofEpochSecond(123)
        assertTrue(equals(statsManager.getEventStatisticsByName(eventName), 1.0 / MINUTES_IN_HOUR.toDouble()))
        assertEquals(1, statsManager.getAllEventStatistics().size.toLong())

        clock.now = Instant.ofEpochSecond(3600)
        assertTrue(equals(statsManager.getEventStatisticsByName(eventName), 1.0 / MINUTES_IN_HOUR.toDouble()))
        assertEquals(1, statsManager.getAllEventStatistics().size.toLong())

        statsManager.incEvent(eventName)
        clock.now = Instant.ofEpochSecond(3600)
        assertTrue(equals(statsManager.getEventStatisticsByName(eventName), 2.0 / MINUTES_IN_HOUR.toDouble()))
        assertEquals(1, statsManager.getAllEventStatistics().size.toLong())

        clock.now = Instant.ofEpochSecond(3601)
        assertTrue(equals(statsManager.getEventStatisticsByName(eventName), 1.0 / MINUTES_IN_HOUR.toDouble()))
        assertEquals(1, statsManager.getAllEventStatistics().size.toLong())
    }

    @Test
    fun `clock event statistic changing time many event many increments`() {
        val clock = SetableClock(Instant.ofEpochSecond(0L))
        val statsManager = ClockEventStatistic(clock)
        val events = arrayOf("0", "1", "2", "3", "4", "5", "6", "7")
        val periods = intArrayOf(10, 11, 24, 31, 47, 53, 66, 79)
        val count = 500
        for (i in 1..count) {
            for (j in events.indices) {
                val eventName = events[j]
                val period = periods[j]
                clock.now = Instant.ofEpochSecond(i * period.toLong())
                statsManager.incEvent(eventName)
            }
        }

        assertEquals(events.size, statsManager.getAllEventStatistics().size)

        clock.now = Instant.ofEpochSecond(0L)
        for (eventName in events) {
            assertTrue(equals(statsManager.getEventStatisticsByName(eventName), 0.0))
        }

        val max = count * periods[periods.size - 1]
        for (i in 0 until max) {
            clock.now = Instant.ofEpochSecond(i.toLong())
            for (j in events.indices) {
                val eventName = events[j]
                val period = periods[j]
                if (i < count * period) {
                    var first = period * ((i - SECONDS_IN_HOUR) / period)
                    if ((i - SECONDS_IN_HOUR) % period != 0) {
                        first += period
                    }
                    val start = period.coerceAtLeast(first)
                    val cnt = 0.coerceAtLeast(i - start + period) / period

                    assertTrue(equals(statsManager.getEventStatisticsByName(eventName), cnt / MINUTES_IN_HOUR.toDouble()))
                }
            }
        }
    }

    @Test
    fun `clock event statistics changing time many event many increment time goes backwards`() {
        val clock = SetableClock(Instant.ofEpochSecond(0L))
        val statsManager = ClockEventStatistic(clock)
        val events = arrayOf("0", "1", "2", "3", "4", "5", "6", "7")
        val periods = intArrayOf(10, 11, 24, 31, 47, 53, 66, 79)
        val count = 500
        for (i in count downTo 1) {
            for (j in events.indices) {
                val eventName = events[j]
                val period = periods[j]
                clock.now = Instant.ofEpochSecond(i * period.toLong())
                statsManager.incEvent(eventName)
            }
        }
        assertEquals(events.size, statsManager.getAllEventStatistics().size)
        clock.now = Instant.ofEpochSecond(0L)
        for (eventName in events) {
            assertTrue(equals(statsManager.getEventStatisticsByName(eventName), 0.0))
        }
        val max = count * periods[periods.size - 1]
        for (i in 0 until max) {
            clock.now = Instant.ofEpochSecond(i.toLong())
            for (j in events.indices) {
                val eventName = events[j]
                val period = periods[j]
                if (i < count * period) {
                    var first = period * ((i - SECONDS_IN_HOUR) / period)
                    if ((i - SECONDS_IN_HOUR) % period != 0) {
                        first += period
                    }
                    val start = period.coerceAtLeast(first)
                    val cnt = 0.coerceAtLeast(i - start + period) / period
                    assertTrue(equals(statsManager.getEventStatisticsByName(eventName), cnt / MINUTES_IN_HOUR.toDouble()))
                }
            }
        }
    }

    companion object {
        private const val EPSILON = 1e-15
        private const val MINUTES_IN_HOUR: Int = 60
        private const val SECONDS_IN_HOUR: Int = 3600
    }
}