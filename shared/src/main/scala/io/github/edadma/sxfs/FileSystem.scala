package io.github.edadma.sxfs

import java.nio.ByteBuffer
import scala.math.ceil

object FileSystem:
  def format(disk: Disk): Boolean =
    def logBase2(x: Double): Double = math.log(x) / math.log(2)
    def unixTime                    = (System.currentTimeMillis / 1000).toInt

    val data = new Array[Byte](disk.blockSize)
    val buf  = ByteBuffer.wrap(data)

    val s_inodes_count = disk.blockSize * disk.diskSize * 10 / 100
    val s_log_block_size =
      logBase2(disk.blockSize / 512) match
        case s if s.isWhole => s.toInt
        case _              => return false
    val s_blocks_count = disk.diskSize
    val blockBitmapSize =
      ceil(s_blocks_count / 8 / (512 << s_log_block_size)).toInt
    val inodeBitmapSize =
      ceil(s_inodes_count / 8 / (512 << s_log_block_size)).toInt
    val inodeTableSize =
      ceil(s_inodes_count * 64 / (512 << s_log_block_size)).toInt
    val s_first_data_block =
      1 + blockBitmapSize + inodeBitmapSize + inodeTableSize

    buf
      .putInt(s_inodes_count)
      .putInt(s_blocks_count)
      .putInt(s_blocks_count - s_first_data_block) // s_free_blocks_count
      .putInt(s_inodes_count)                      // s_free_inodes_count
      .putInt(s_first_data_block)
      .putInt(s_log_block_size)
      .putInt(0)        // s_mtime
      .putInt(unixTime) // s_wtime
      .putShort(0)      // s_mnt_count
      .putShort(100)    // s_max_mnt_count
      .putShort(0x1234) // s_magic
      .putShort(0)      // s_state
      .putShort(0)      // s_lastcheck
      .putShort(0)      // s_checkinterval
      .putShort(10)     // s_creator_os
      .putShort(0)      // s_rev_level
    true
  end format

  def check(disk: Disk): Boolean = false

type Inode = Int

class FileSystem(disk: Disk):
  private val data = new Array[Byte](disk.blockSize)
  private val buf  = ByteBuffer.wrap(data)

//  def create: Inode
//  def remove(inode: Inode): Boolean
//  def stat(inode: Inode): Long
//
//  def read(inode: Inode, data: Array[Byte], length: Long, offset: Long): Int
//  def write(inode: Inode, data: Array[Byte], length: Long, offset: Long): Int
