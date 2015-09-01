package model

import org.joda.time.DateTime

case class Game(id: Long, date: DateTime, homeTeam: Team, awayTeam: Team, result: Option[Result]) {
  override def toString = result match {
    case Some(r) => "[%06d] %s %20s %3d at %20s %3d".format(id, date.toString("yyyy-MM-dd hh:mm"), awayTeam, r.awayScore, homeTeam, r.awayScore)
    case None => "[%06d] %s %20s     at %20s    ".format(id, date.toString("yyyy-MM-dd hh:mm"), awayTeam, homeTeam)
  }
}
