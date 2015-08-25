package cp19_型のパラメータ化

/*
 * 型のパラメータ化
 * 反変(contravariance)
 */

object No19_6 extends App {

    /**
     * ↓反変が自然な例↓
     * 例えば、OutputChannel[AnyRef]とOutputChannel[String]があった時
     * AnyRefはStringを包含しているので文字列を書き込むこともできる。
     * しかし、OutputChannel[String]は文字列しか書き込むことができない。
     * 一般にT型とU型があるとき、T型の方が"要求すること"が少なく"提供すること"が多い場合、TはUのサブ型になりうる。
     * ●ここでいう要求が少ないとは、例えばあるメソッドにおいて引数がAnyRef型の場合とString型の場合では
     *    AnyRef型のほうが受け入れる型の範囲が広く、String型はStringのみである。つまり、利用者への要求がString型の方がキツイ。
     */
    trait OutputChannel[-T] {
        def write(x: T)
    }

    /**
     * A => B と書いたときにScalaはその構文を関数トレイトFunction1へと展開する
     * 関数トレイトは変位指定アノテーションとして反変・共変の両方を使っている
     */
    trait Function1[-S, +T] {
        def apply(x: S): T
    }

    Customer //アプリケーションを呼び出す

    class Publication(val title: String)
    class Book(title: String) extends Publication(title)
    class Page(no: Int, title: String) extends Book(title) //サンプルに勝手に作ったクラス
    object Library {
        val books = Set(
            new Book("Programming in Scala"),
            new Book("Walden"),
            new Book("たんぽぽ娘"))
        //関数値infoはFunction1[Book, AnyRef]へ展開される
        def printBookList(info: Book => AnyRef) {
            for (book <- books) println(info(book))
        }
    }

    /**
     * 関数値getTitleはPublication=>Stringであり
     * Funtion1[-S,+T](この場合はFuntion1[Book,AnyRef])
     * の反変と共変の条件を満たしている。
     * 反変の条件として、引数型であるPublicationはBookのスーパーである必要がある。
     * 共変の条件として、戻り値型であるStringはAnyRefのサブである必要がある。
     * getTitleはその条件を満たしているため
     * Publication=>String (Function1[Publication,String])は
     * Book=>AnyRef (Function1[Book,AnyRef])のサブ型になりうる。
     * 反変を使うことで、関数の引数型を指定型のスーパーに制限できる。
     * そうすることで、未定義の処理をさせることを防ぐ。
     * つまり、引数型に対して共通処理しか許さない。
     * サブ型を容認すると、許容範囲外の処理(サブ型独自の定義)が行われる可能性があり、
     * 実行時例外を引き起こす可能性がある。
     */
    object Customer extends App {
        Library.printBookList((p: Publication) => p.title) //直接関数を渡す
        // Library.printBookList((p: Page) => p.no) //PageはBookのスーパー型でないので反変条件から外れ、結果的に関数値を渡せない。
    }
}
