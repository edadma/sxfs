Simplix File System (sxfs)
==========================

Definitions
-----------

### Blocks

An *sxfs* formatted device or partition is divided into small groups of sectors called “blocks”. The size of the blocks is determined when formatting and is encoded in the *superblock* as `s_log_block_size`. Block sizes are powers of 2 times 512.

Disk Organization
-----------------

### Superblock

#### Superblock Structure

| Offset (bytes) | Size (bytes) | Description         |
|----------------|--------------|---------------------|
| 0              | 4            | s_inodes_count      |
| 4              | 4            | s_blocks_count      |
| 8              | 4            | s_free_blocks_count |
| 12             | 4            | s_free_inodes_count |
| 16             | 4            | s_first_data_block  |
| 20             | 4            | s_log_block_size    |
| 24             | 4            | s_mtime             |
| 28             | 4            | s_wtime             |
| 32             | 2            | s_mnt_count         |
| 34             | 2            | s_max_mnt_count     |
| 36             | 2            | s_magic             |
| 38             | 2            | s_state             |
| 40             | 4            | s_lastcheck         |
| 44             | 4            | s_checkinterval     |
| 48             | 4            | s_creator_os        |
| 52             | 4            | s_rev_level         |

### Block Bitmap

The number of blocks in the block bitmap is `ceil(s_blocks_count/8/(512<<s_log_block_size))`.

### Inode Bitmap

The number of blocks in the inode bitmap is `ceil(s_inodes_count/8/(512<<s_log_block_size))`.

### Inode Table

The inode table uses 1-based indexing so that 0 can be used as a sentinel value.

The first inode (index 1) is the "bad block" file. The second inode (index 2) is the root directory.

The number of blocks in the inode table is `ceil(s_inodes_count*64/(512<<s_log_block_size))`.

#### Inode Structure

| Offset (bytes) | Size (bytes) | Description   |
|----------------|--------------|---------------|
| 0              | 2            | i_mode        |
| 2              | 2            | i_uid         |
| 4              | 4            | i_size        |
| 8              | 4            | i_atime       |
| 12             | 4            | i_ctime       |
| 16             | 4            | i_mtime       |
| 20             | 4            | i_dtime       |
| 24             | 2            | i_gid         |
| 26             | 2            | i_links_count |
| 28             | 4            | i_blocks      |
| 32             | 8 x 4        | i_block       |

#### i_mode

| Constant      | Value  | Description          |
|---------------|--------|----------------------|
| SXFS_S_IFSOCK | 0xC000 | socket               |
| SXFS_S_IFLNK  | 0xA000 | symbolic link        |
| SXFS_S_IFREG  | 0x8000 | regular file         |
| SXFS_S_IFBLK  | 0x6000 | block device         |
| SXFS_S_IFDIR  | 0x4000 | directory            |
| SXFS_S_IFCHR  | 0x2000 | character device     |
| SXFS_S_IFIFO  | 0x1000 | fifo                 |
| SXFS_S_ISUID  | 0x0800 | Set process User ID  |
| SXFS_S_ISGID  | 0x0400 | Set process Group ID |
| SXFS_S_ISVTX  | 0x0200 | sticky bit           |
| SXFS_S_IRUSR  | 0x0100 | user read            |
| SXFS_S_IWUSR  | 0x0080 | user write           |
| SXFS_S_IXUSR  | 0x0040 | user execute         |
| SXFS_S_IRGRP  | 0x0020 | group read           |
| SXFS_S_IWGRP  | 0x0010 | group write          |
| SXFS_S_IXGRP  | 0x0008 | group execute        |
| SXFS_S_IROTH  | 0x0004 | others read          |
| SXFS_S_IWOTH  | 0x0002 | others write         |
| SXFS_S_IXOTH  | 0x0001 | others execute       |

#### i_block

The first six are direct block pointers. The seventh is a (singly) indirect pointer. The eighth is a doubly indirect
pointer.

### Data Blocks

The remaining blocks are data blocks for files.

The number of data blocks is `s_blocks_count - s_first_data_block`.

Directory Structure
-------------------

A directory file is a linked list of directory entry structures. Each structure contains the name of the entry, the
inode associated with the data of this entry, and the distance within the directory file to the next entry.

| Offset (bytes) | Size (bytes) | Description |
|----------------|--------------|-------------|
| 0              | 4            | inode       |
| 4              | 2            | rec_len     |
| 6              | 1            | name_len    |
| 7              | 1            | file_type   |
| 8              | 0-255        | name        |
