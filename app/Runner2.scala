import model._
import org.joda.time.DateTime

import scala.collection.SortedSet

object Runner2 {
   def main(args: Array[String]) {
     var lim = OrderBook(SortedSet.empty[Order], SortedSet.empty[Order])
     var es: List[Bet] = Nil
     println(lim.currentMarket() + " : " + es)
     val jb: Bettor = Bettor(0L, "Jim", 10000)
     val zb: Bettor = Bettor(1L, "Zeke", 10000)
     var tup = lim.placeOrder(Order(jb, Home, 100, 100, new DateTime()))
     lim = tup._1
     println(tup._2)
     tup = lim.placeOrder(Order(zb, Away, 110, 100, new DateTime()))
     lim = tup._1
     println(tup._2)
     println(lim.currentMarket() + " : " + es)
     tup = lim.placeOrder(Order(jb, Home, 95, 200, new DateTime()))
     lim = tup._1
     tup = lim.placeOrder(Order(zb, Home, 95, 200, new DateTime()))
     lim = tup._1
     tup = lim.placeOrder(Order(zb, Home, 95, 75, new DateTime()))
     println(tup._2)
     lim = tup._1
     println(lim.currentMarket() + " : " + es)
     tup = lim.placeOrder(Order(jb, Home, 105, 200, new DateTime()))
     lim = tup._1
     println(lim.currentMarket() + " : " + es)
     println(tup._2)
     println(lim)

     tup = lim.placeOrder(Order(jb, Away, 105, 100, new DateTime()))
     println(tup._1)
     println(tup._2)
   }
 }
