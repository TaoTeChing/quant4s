package quanter

import java.util.Date

/**
  * 
  */
object TimeSpan {
  def fromTicks(tick: Long) = new TimeSpan(tick)
  def fromSeconds(second: Long) = new TimeSpan(second * 1000)
  def fromMinutes(minute: Long) = new TimeSpan(minute * 60 *1000)
  def fromHours(hour: Long) = new TimeSpan(hour * 60 * 60 * 1000)
  def fromDays(day: Long) = new TimeSpan(day * 24 * 60 *60000)

  def minValue = new TimeSpan(Int.MinValue)
  def maxValue = new TimeSpan(Int.MaxValue)
  def zero = new TimeSpan(0)
}

class TimeSpan(ptotalMilliSeconds: Long) {
  private val _totalMilliSeconds: Long = ptotalMilliSeconds

  def this(afterDate: Date, beforeDate: Date) {
    this(afterDate.getTime() - beforeDate.getTime())
  }

  def milliSeconds: Long = _totalMilliSeconds
  def seconds = math.round(_totalMilliSeconds/1000)
  def minutes = math.round(_totalMilliSeconds/60000)
  def hours = math.round(_totalMilliSeconds/3600000)
  def totalDays : Int = (this.milliSeconds /86400000).toInt

  def >= (time: TimeSpan): Boolean = this.milliSeconds >= time.milliSeconds
  def > (time: TimeSpan): Boolean = this.milliSeconds > time.milliSeconds

  def <=(time: TimeSpan): Boolean = this.milliSeconds <= time.milliSeconds
  def <(time: TimeSpan): Boolean = this.milliSeconds < time.milliSeconds

  def +=(time:TimeSpan): TimeSpan = TimeSpan.fromTicks(this.milliSeconds + time.milliSeconds)

  def +(time:TimeSpan): TimeSpan = TimeSpan.fromTicks(this.milliSeconds + time.milliSeconds)
  def -(time:TimeSpan): TimeSpan = TimeSpan.fromTicks(this.milliSeconds - time.milliSeconds)
  def /(time:Int): TimeSpan = TimeSpan.fromTicks(this.milliSeconds / time)

  def ==(time: TimeSpan): Boolean = {
    this.milliSeconds == time.milliSeconds
  }

  override def equals(obj: scala.Any): Boolean = this == obj.asInstanceOf[TimeSpan]

}
