package cp07_組み込みの制御構造
/*
 * breakとcontinueを使わない方法
 * 
 */
object No7_6 {

    def main(args: Array[String]) {

        //↓var変数を使用したJava風コード
        var i = 0
        var foundIt = false
        while (i < args.size && !foundIt) {
            if (!args(i).startsWith("-"))
                if (args(i).endsWith(".scala"))
                    foundIt = true
            i += 1
        }

        //↓再帰関数を使用した関数型コード
        def searchFrom(i: Int): Int =
            if (i >= args.size) -1
            else if (args(i).startsWith("-")) searchFrom(i + 1)
            else if (args(i).endsWith(".scala")) i
            else searchFrom(i + 1)

        val idx = searchFrom(0) //目的引数のインデックス

    }
}