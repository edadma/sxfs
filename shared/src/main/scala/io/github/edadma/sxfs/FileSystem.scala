package io.github.edadma.sxfs

object FileSystem:
  def format(disk: Disk): Boolean = false
  def check(disk: Disk): Boolean = false

type Inode = Int

abstract class FileSystem:
  def create: Inode
  def remove(inode: Inode): Boolean
  def stat(inode: Inode): Long

  def read(inode: Inode, data: Array[Byte], length: Long, offset: Long): Int
  def write(inode: Inode, data: Array[Byte], length: Long, offset: Long): Int
