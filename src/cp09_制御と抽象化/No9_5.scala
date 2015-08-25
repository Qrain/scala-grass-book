package cp09_制御と抽象化
/*
 * 名前渡しパラメーター
 * ※
 */
object No9_5 {

    def main(args: Array[String]) {
        whereIsTruth(5 < 2)
    }

    def whereIsTruth(op: => Boolean) { //opは遅延評価される
        if (op)
            println("それで正しいのです！")
        else
            println("嘘だッッッツ！！！、とレナは言った。")
    }

    object FileMatcher { //サンプルとしてNo9_1から転載

        import java.io.File

        //ファイル名が引数で終わるファイルを配列で返す
        def filesEnding(query: String) = filesMatching(_.endsWith(query)) //クロージャーの使用
        //ファイル名に引数が含まれているファイルを配列で返す
        def filesContaining(query: String) = filesMatching(_.contains(query))
        //ファイル名が引数の正規表現に一致するファイルを配列で返す
        def filesRegex(query: String) = filesMatching(_.matches(query))

        def filesMatching(matcher: String => Boolean) =
            for {
                file <- makeFileList(new File(".")) //カレントディレクトリのファイルリスト
                if matcher(file.getName) //クロージャーは束縛変数(引数)としてファイル名のみをとる
            } yield {
                file
            }

        //.以下のファイルリストを作成する関数(Original)
        private def makeFileList(f: File): Array[File] =
            f.listFiles.flatMap { f =>
                if (f.isDirectory) makeFileList(f) else Array(f)
            }
    }
}

