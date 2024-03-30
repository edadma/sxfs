package io.github.edadma.sxfs

abstract class Disk:
  val blockSize: Int
  val diskSize: Int

  def read(blocknum: Int, data: Array[Byte]): Unit
  def write(blocknum: Int, data: Array[Byte]): Unit

//  def write(blocknum: Int, data: Array[Byte], offset: Int, length: Int): Unit =
//    require(0 <= blocknum && blocknum < diskSize, "block number out of range")
//    require(0 <= offset && offset < blockSize, "offset out of range")
//    require(0 <= length && length < blockSize && length < data.length, "length out of range")
//    Array.copy(data, offset, disk, blocknum * blockSize + offset, length)
//
//  def write(blocknum: Int, data: Array[Byte], soffset: Int, doffset: Int, length: Int): Unit =
//    require(0 <= blocknum && blocknum < diskSize, "block number out of range")
//    require(0 <= soffset && soffset < data.length, "source offset out of range")
//    require(0 <= doffset && doffset < blockSize, "destination offset out of range")
//    require(0 <= length && length < blockSize && length < data.length, "length out of range")
//    Array.copy(data, soffset, disk, blocknum * blockSize + doffset, length)

class RamDisk(val blockSize: Int, val diskSize: Int) extends Disk:
  require(blockSize > 0, "block size must be positive")
  require(diskSize > 0, "disk size must be positive")
  require(blockSize.toLong * diskSize > Int.MaxValue, s"RamDisk has a maximum size of ${Int.MaxValue} bytes")

  private val disk = new Array[Byte](blockSize * diskSize)

  def read(blocknum: Int, data: Array[Byte]): Unit =
    require(0 <= blocknum && blocknum < diskSize, "block number out of range")
    require(data.length >= blockSize, "array not large enough")
    Array.copy(disk, blocknum * blockSize, data, 0, blockSize)

  def write(blocknum: Int, data: Array[Byte]): Unit =
    require(0 <= blocknum && blocknum < diskSize, "block number out of range")
    require(data.length >= blockSize, "array not large enough")
    Array.copy(data, 0, disk, blocknum * blockSize, blockSize)
