package cp09_制御と抽象化
/*
 * 重複コードの削減
 * ※
 */
object No9_1 {

    def main(args: Array[String]) {

        //FileMatcherFinal.filesContaining("AB") foreach println
        FileMatcherFinal.filesEnding(".scala") foreach println
        //FileMatcherFinal.filesRegex("N_*") foreach println

    }

    object FileMatcherFinal { //FileMatcher最終版

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
            f.listFiles.flatMap(f => if (f.isDirectory) makeFileList(f) else Array(f))
    }

    object FileMatcherAfter { //重複除去後

        //↓カレントディレクトリ内のファイルリスト
        private def filesHere = new java.io.File(".").listFiles //非公開ヘルパーメソッド

        //ファイル名が引数で終わるファイルを配列で返す
        def filesEnding(query: String) = {
            filesMatching(query, _.endsWith(_)) //記法1(プレースホルダー構文) 
            //filesMatching(query, (fileName, query) => fileName.endsWith(query)) //記法2(通常構文) 
            //filesMatching(_.endsWith(query)) //記法3(クロージャーの使用) 
        }

        //ファイル名に引数が含まれているファイルを配列で返す
        def filesContaining(query: String) = filesMatching(query, _.contains(_))

        //ファイル名が引数の正規表現に一致するファイルを配列で返す
        def filesRegex(query: String) = filesMatching(query, _.matches(_))

        def filesMatching(query: String, matcher: (String, String) => Boolean) = //共通部分を抽出したヘルパー関数
            for {
                file <- filesHere
                if matcher(file.getName, query) //引数関数でフィルタリングをかける
            } yield file

    }

    object FileMatcherBefore { //重複除去前

        //↓カレントディレクトリ内のファイルリスト
        private def filesHere = new java.io.File(".").listFiles //非公開ヘルパーメソッド

        def filesEnding(query: String) = for { //ファイル名が引数で終わるファイルを配列で返す
            file <- filesHere
            if file.getName.endsWith(query)
        } yield file

        def filesContaining(query: String) = for { //ファイル名に引数が含まれているファイルを配列で返す
            file <- filesHere
            if file.getName.contains(query) //	ファイル名にqueryが含まれていればTRUE
        } yield file

        def filesRegex(query: String) = for { //ファイル名が引数の正規表現に一致するファイルを配列で返す
            file <- filesHere
            if file.getName.matches(query)
        } yield file
    }
}

