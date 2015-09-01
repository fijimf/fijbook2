package model

import org.joda.time.DateTime

case class Order(bettor: Bettor, side: HomeAway, price: Int, size: Int, timestamp: DateTime) extends Ordered[Order] {

  def matchBet(other: Bettor): Order = copy(bettor = other, side = side.opp)

  override def toString: String = bettor + " " + side + " " + size + " @ " + price + " [" + timestamp.toString("HH:MM:ss.SSS") + "]"

  override def compare(that: Order): Int = {
    import scala.math.Ordered.orderingToOrdered
    (this.side, this.price, this.timestamp.getMillis, this.size, this.bettor).compare((that.side, that.price, that.timestamp.getMillis, that.size, that.bettor))
  }
}
