package cp07_組み込みの制御構造
/**
 * 組み込み制御構造
 * if文編
 */
object No7_1 {

    def main(args: Array[String]) {

        //Scalaのif文は値を返してくれる。それにより、関数型プログラミングをサポートする。

        //命令型スタイル
        var filename1 = "default.txt"
        if (!args.isEmpty)
            filename1 = args.head //第一引数をファイル名とする

        //関数型スタイル
        val filename2 = if (!args.isEmpty) args.head else "default.txt"

        //上記の最大の差は、変数がvarかvalかである。はやり関数型ではvalが推奨される。
    }

}
