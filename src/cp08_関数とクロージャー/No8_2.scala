package cp08_関数とクロージャー
/*
 * ローカル関数(内部関数)
 * メソッド内に定義した関数をローカル関数という。
 */
object No8_2 {

    def main(args: Array[String]) {

        val argsList = List("50", "./src/cp6/Rational.scala") //仮コマンドライン引数
        val width = argsList.head.toInt
        for (arg <- argsList.drop(1))
            LongLines.processFile(arg, width)
    }

    object LongLines { //公開メソッド内にローカル関数を持つLongLines
        import scala.io.Source

        def processFile(filename: String, width: Int) {
            def processLine(line: String) { //←ローカル関数
                if (line.size > width)
                    println(filename+" :  "+line.trim)
            }
            val source = Source.fromFile(filename)
            for (line <- source.getLines)
                processLine(line)
        }
    }
}
