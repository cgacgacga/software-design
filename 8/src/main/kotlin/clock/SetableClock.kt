package clock

import java.time.Instant

class SetableClock(var now: Instant) : Clock {
    override fun now(): Instant {
        return now
    }
}