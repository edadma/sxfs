package io.github.edadma.sxfs

@main def run(): Unit =
  val array = Array[Byte](
    0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07,
    0x10, 0x11, 0x12, 0x13, 0x14, 0x15, 0x16, 0x17,
    0x20, 0x21, 0x22, 0x23, 0x24, 0x25, 0x26, 0x27,
  )

  def dump(array: Array[Byte], start: Int, end: Int): Unit =
    val offset = start % 8
    var index  = start - offset

    def line(index: Int, offset: Int, length: Int): Unit =
      print("%04x  ".format(index))

      for i <- 0 until length do
        print(
          if i < offset then "   "
          else "%02x ".format(array(index + i)).toUpperCase,
        )

        if i == 3 then print(" ")

      println
    end line

    var firstLine = true

    while index < end do
      line(index, if firstLine then offset else 0, if end < index + 8 then end - index else 8)
      firstLine = false
      index += 8
  end dump

  dump(array, 2, 0x0f)
