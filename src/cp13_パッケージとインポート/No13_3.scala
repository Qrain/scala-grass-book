package cp13_パッケージとインポート
/**
 * パッケージとインポート
 * 暗黙のインポート
 * ※
 */
object No13_3 {
    def main(args: Array[String]) {
        //.scalaという拡張子のファイルには冒頭に以下の3つが必ずインポートされる
        import java.lang._
        import scala._
        import Predef._;
    }
}
