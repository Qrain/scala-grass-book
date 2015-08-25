package cp07_組み込みの制御構造
/*
 * 命令型⇒関数型へのリファクタリング
 */
object No7_8 {

    def main(args: Array[String]) {
        //↓2つとも出力結果は同じ
        printMultiTable()
        Console.err.println(multiTable)
    }

    //関数型コードの九九表示メソッド
    private def makeRow(row: Int) = { //1段分を文字列として返す
        for (col <- 1 to 10) yield {
            val prod = (row * col).toString //乗算した値
            val padding = "  " * (4 - prod.size)
            padding + prod
        }
    }.mkString

    private def multiTable = {
        for (row <- 1 to 10) yield makeRow(row)
    }.mkString("\n") //改行で区切った文字列

    private def printMultiTable() { //命令型コードの九九表示メソッド
        var i = 1

        while (i <= 10) {
            var j = 1

            while (j <= 10) {
                val prod = (i * j).toString
                var k = prod.size

                while (k < 4) {
                    print("  ")
                    k += 1
                }
                print(prod)
                j += 1
            }
            println()
            i += 1
        }
    }
}