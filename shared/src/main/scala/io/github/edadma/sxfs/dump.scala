package io.github.edadma.sxfs

def dump(array: Array[Byte], start: Int = 0, end: Int = Int.MaxValue): Unit =
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
    end for

    print("  ")

    for i <- 0 until length do
      val ch = array(index + i).toChar

      print(if i < offset || ch < ' ' || ch > '~' then " " else ch)
    end for

    println
  end line

  var firstLine = true

  while index < end && index < array.length do
    line(index, if firstLine then offset else 0, if end < index + 8 then end - index else 8)
    firstLine = false
    index += 8
end dump
