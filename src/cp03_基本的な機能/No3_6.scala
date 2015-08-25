package cp03_基本的な機能
/**
 *
 */
object No3_6 {

    def main(args: Array[String]): Unit = {
        import scala.io.Source
        import java.io.File

        val filename = "src/cp3/No3_6.scala"

        if (new File(filename).exists()) {

            val lines = Source.fromFile(filename).getLines.toList

            //println(maxWidth1(lines))
            val maxWidth = maxWidth2(lines)

            for (line <- lines) {

                val numSpaces = maxWidth - widthOfLength(line) //
                val padding = " " * numSpaces
                println(padding + line.size+"\t| "+line)
            }
        } else {
            sys.error("File Not Found")
        }
    }

    private def maxWidth1(lines: List[String]): Int = {
        var maxWidth = 0
        for (line <- lines)
            maxWidth = maxWidth.max(widthOfLength(line))
        maxWidth
    }

    private def maxWidth2(lines: List[String]): Int = {
        val longestLine = lines.reduceLeft((a, b) => if (a.size > b.size) a else b)
        widthOfLength(longestLine)
    }

    private def widthOfLength(s: String) = s.size.toString.size
}
