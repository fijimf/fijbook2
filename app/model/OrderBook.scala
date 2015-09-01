package model

import scala.collection.SortedSet

case class OrderBook(bids: SortedSet[Order], asks: SortedSet[Order]) {


  override def toString: String = "Home\n----\n" + formatBookSide(bids) + "\nAway\n----\n" + formatBookSide(asks)

  def formatBookSide(side: SortedSet[Order]): String = {
    side.toList.map("\t" + _).mkString("\n")
  }

  def bestBid: Option[Int] = bids.lastOption.map(_.price)

  def bestAsk: Option[Int] = asks.headOption.map(_.price)

  def currentMarket(): (Option[Int], Option[Int]) = (bestBid, bestAsk)

  def executeBuyOrder(o: Option[Order], os: SortedSet[Order], es: List[Bet]): (Option[Order], SortedSet[Order], List[Bet]) = {
    o match {
      case Some(p) =>
        os.headOption match {
          case Some(q) =>
            if (p.size == q.size) {
              (None, os.tail, Bet(p.bettor, q.bettor, q.price, q.size) :: es)
            } else if (p.size > q.size) {
              executeBuyOrder(Some(p.copy(size = p.size - q.size)), os.tail, Bet(p.bettor, q.bettor, q.price, q.size) :: es)
            } else {
              (None, os.tail + q.copy(size = q.size - p.size), Bet(p.bettor, q.bettor, q.price, p.size) :: es)
            }
          case None => (Some(p), os, es)
        }
      case None => (None, os, es)
    }
  }

  def executeSellOrder(o: Option[Order], os: SortedSet[Order], es: List[Bet]): (Option[Order], SortedSet[Order], List[Bet]) = {
    o match {
      case Some(p) =>
        os.headOption match {
          case Some(q) =>
            if (p.size == q.size) {
              (None, os.tail, Bet(q.bettor, p.bettor, q.price, q.size) :: es)
            } else if (p.size > q.size) {
              executeBuyOrder(Some(p.copy(size = p.size - q.size)), os.tail, Bet(q.bettor, p.bettor, q.price, q.size) :: es)
            } else {
              (None, os.tail + q.copy(size = q.size - p.size), Bet(q.bettor, p.bettor, q.price, p.size) :: es)
            }
          case None => (Some(p), os, es)
        }
      case None => (None, os, es)
    }
  }

  def placeOrder(o: Order): (OrderBook, List[Bet]) = {
    o.side match {
      case Home =>
        val (matched, unmatched) = asks.partition(_.price <= o.price)
        val (p, os, es) = executeBuyOrder(Some(o), matched, List.empty[Bet])
        p match {
          case Some(p1) => (copy(bids = bids + p1, asks = unmatched ++ os), es)
          case None => (copy(asks = unmatched ++ os), es)
        }
      case Away =>
        val (matched, unmatched) = bids.partition(_.price >= o.price)
        val (p, os, es) = executeSellOrder(Some(o), matched, List.empty[Bet])
        p match {
          case Some(p1) => (copy(asks = asks + p1, bids = unmatched ++ os), es)
          case None => (copy(bids = unmatched ++ os), es)
        }
    }
  }
}

