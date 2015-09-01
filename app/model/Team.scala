package model

case class Team(id: Long, name: String) {
  override def toString = name
}
