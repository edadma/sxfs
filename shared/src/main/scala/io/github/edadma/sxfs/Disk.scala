package io.github.edadma.sxfs

abstract class Disk:
  def read(blocknum: Int, data: Array[Byte]): Unit
  def write(blocknum: Int, data: Array[Byte]): Unit
