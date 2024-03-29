package io.github.edadma.sxfs

import java.nio.ByteBuffer

object FileSystem:
  def format(disk: Disk): Boolean =
    val data = new Array[Byte](disk.blockSize)
    val buf = ByteBuffer.wrap(data)

    buf.putInt()
  end format

  def check(disk: Disk): Boolean = false

type Inode = Int

class FileSystem(disk: Disk):
  private val data = new Array[Byte](disk.blockSize)
  private val buf = ByteBuffer.wrap(data)

//  def create: Inode
//  def remove(inode: Inode): Boolean
//  def stat(inode: Inode): Long
//
//  def read(inode: Inode, data: Array[Byte], length: Long, offset: Long): Int
//  def write(inode: Inode, data: Array[Byte], length: Long, offset: Long): Int
