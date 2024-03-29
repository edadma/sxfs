package io.github.edadma.sxfs

abstract class Disk:
  val blockSize: Int
  val diskSize: Int

  def read(blocknum: Int, data: Array[Byte]): Unit
  def write(blocknum: Int, data: Array[Byte]): Unit

class RamDisk(val blockSize: Int, val diskSize: Int) extends Disk:
  require(blockSize > 0, "block size must be positive")
  require(diskSize > 0, "disk size must be positive")

  private val disk = new Array[Byte](blockSize * diskSize)

  override def read(blocknum: Int, data: Array[Byte]): Unit =
    require(0 <= blocknum && blocknum < diskSize, "block number out of range")
    Array.copy(disk, blocknum * blockSize, data, 0, blockSize)

  override def write(blocknum: Int, data: Array[Byte]): Unit =
    require(0 <= blocknum && blocknum < diskSize, "block number out of range")
    Array.copy(data, 0, disk, blocknum * blockSize, blockSize)
