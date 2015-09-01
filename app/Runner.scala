import model.{Game, Schedule, Team}
import org.joda.time.DateTime

object Runner {
  private var teamId = 0L
  private var gameId = 0L
  private var book = Book(Schedule(Map(), Map()), Map(), List(), Map())

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
    book.addTeam(team) match {
      case Left(x) =>
        println(x);
        None
      case Right(b) =>
        book = b;
        Some(team)
    }
  }

  private def createGame(date: DateTime, ht: Team, at: Team): Option[Game] = {
    gameId = gameId + 1
    val game: Game = Game(gameId, date, ht, at, None)
    book.addGame(game) match {
      case Left(x) =>
        println(x);
        None
      case Right(b) =>
        book = b;
        Some(game)
    }
  }

}
