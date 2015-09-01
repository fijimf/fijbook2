import model._
import org.joda.time.DateTime
import play.api.Logger

import scala.collection.SortedSet

trait ScheduleContext                                   {
 }

trait BookContext

object Runner extends ScheduleContext with BookContext {
  private val logger: Logger = Logger("runner")
  private var teamId = 0L
  private var gameId = 0L
  private var book = Map.empty[Game, OrderBook]
  private var schedule = Schedule(Map.empty[Long,Game], Map.empty[Long, Team])

  def main(args: Array[String]) {

    val g = createTeam("Georgetown").get
    val v = createTeam("Villanova").get

    val m = createGame(new DateTime("2015-12-31T22:00"), g, v).get
    val n = createGame(new DateTime("2016-02-05T14:00"), v, g).get
    println(g)
    println(v)
    println(m)
    println(n)
  }

  private def createTeam(name: String): Option[Team] = {
    teamId = teamId + 1
    val team: Team = Team(teamId, name)
    schedule.addTeam(team) match {
      case Left(x) =>
        logger.warn("Failed to create team for name: "+name, x)
        None
      case Right(s) =>
        logger.info("Created team: "+team)
        schedule = s;
        Some(team)
    }
  }

  private def createGame(date: DateTime, ht: Team, at: Team): Option[Game] = {
    gameId = gameId + 1
    val game: Game = Game(gameId, date, ht, at, None)
    schedule.addGame(game) match {
      case Left(x) =>
        println(x);
        None
      case Right(s) =>
        schedule = s;
        book = book+(game->OrderBook(SortedSet.empty[Order],SortedSet.empty[Order]))
        Some(game)
    }
  }

}
