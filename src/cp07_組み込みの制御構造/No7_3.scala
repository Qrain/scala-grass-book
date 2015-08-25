package cp07_組み込みの制御構造
object No7_3 {
    import java.io.File
    import scala.io.Source

    def main(args: Array[String]) {

        //filesListup(new File("."))

        grep(new File("."), ".*class.*") foreach println
    }

    //↓のメソッドはテスト用なので、戻り値がUnitである。
    def filesListup(file: File) = //カレントディレクトリ以下のファイルのみをリストアップする
        for (f <- file.listFiles if f.isFile) yield f

    //指定ディレクトリ以下のScalaファイルから、パターンに一致する行をリストアップする。
    def grep(file: File, pattern: String) =
        for { //ジェネレーターを複数定義して、for文をネストする。
            f <- makeFileList(file) //x <- xx のような構文をジェネレーターという。
            if f.getName.endsWith(".scala") //フィルター追加
            line <- Source.fromFile(f).getLines.toList
            trimmed = line.trim //中間結果を変数へ束縛(バインド)する。この時、valキーワードは要らない。
            if trimmed.matches(pattern) //行頭行末の空白を除去して、正規表現に一致すればTRUE
        } yield { //yieldを付けることでfor文が値を返すことができる。
            f+"\t:  "+trimmed
        }

    //.以下のファイルリストを作成する関数
    def makeFileList(f: File): List[File] = f.listFiles.toList.flatMap { f =>
        if (f.isDirectory) makeFileList(f) else List(f)
    }
}