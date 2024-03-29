package io.github.edadma.sxfs

@main def run(): Unit =
  def logBase2(x: Double): Double = math.log(x) / math.log(2)

  def logBlockSize(size: Int): Option[Int] =
    logBase2(size / 512) match
      case s if s.isWhole => Some(s.toInt)
      case _              => None

  println((System.currentTimeMillis / 1000).isValidInt)
