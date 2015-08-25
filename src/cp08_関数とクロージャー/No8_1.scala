package cp08_関数とクロージャー
/*
 * メソッド(Method)
 * オブジェクトメンバとして定義された関数をメソッドという。
 */
object No8_1 {

    def main(args: Array[String]) {

        val argsList = List("20", "", "", "", "") //仮コマンドライン引数
        val width = args.head.toInt
        for (arg <- args.drop(1))
            LongLines.processFile(arg, width)
    }

    object LongLines { //非公開メソッドを持つLongLines
        import scala.io.Source

        def processFile(filename: String, width: Int) { //オブジェクトメンバなのでメソッドという
            val source = Source.fromFile(filename)
            for (line <- source.getLines)
                processLine(filename, width, line)
        }

        private def processLine(filename: String, width: Int, line: String) {
            if (line.size > width)
                println(filename+" :  "+line.trim)
        }
    }
}
