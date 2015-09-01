package model

case class Bettor(id: Long, name: String, balance: Double) extends Ordered[Bettor] {
  override def toString = "[%06d] %20s     %12.2f    ".format(id, name, balance)

  override def compare(that: Bettor): Int = {
    import scala.math.Ordered.orderingToOrdered
    (id, name, balance).compare(that.id, that.name, that.balance)
  }
}
