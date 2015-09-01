package model

case class Schedule(games: Map[Long, Game], teams: Map[Long, Team]) {
  def addTeam(t: Team): Either[Throwable, Schedule] = {
    if (teams.contains(t.id)) {
      Left(new RuntimeException("model.Team with id " + t.id + " exists already"))
    } else {
      Right(copy(teams = teams + (t.id -> t)))
    }
  }

  def addGame(g: Game): Either[Throwable, Schedule] = {
    if (games.contains(g.id)) {
      Left(new RuntimeException("model.Game with id " + g.id + " exists already"))
    } else {
      Right(copy(games = games + (g.id -> g)))
    }
  }
}
