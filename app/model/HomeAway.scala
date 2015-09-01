package model

sealed trait HomeAway extends Ordered[HomeAway] {
  override def compare(that: HomeAway): Int = ordinal.compare(that.ordinal)

  def ordinal: Int

  def opp: HomeAway

}

case object Home extends HomeAway {
  override def ordinal: Int = 1

  override def opp: HomeAway = Away
}

case object Away extends HomeAway {
  override def ordinal: Int = 2

  override def opp: HomeAway = Home
}
