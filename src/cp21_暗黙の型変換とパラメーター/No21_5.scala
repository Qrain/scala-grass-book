package cp21_暗黙の型変換とパラメーター

/**
 * 暗黙の型変換とパラメーター
 * 暗黙のパラメータ
 */

object No21_5 extends App {

    /*
   * とどのつまり暗黙の型変換関数の変数版ということだな！
   */

    class PreferredPrompt(val preference: String)
    class PreferredDrink(val preference: String)

    object Greeter {
        //    def greet(name: String)(implicit prompt: PreferredPrompt) {
        //      println("Welcome, " + name + ". The system is ready.")
        //      println(prompt.preference)
        //    }

        def greet(name: String)(implicit prompt: PreferredPrompt, drink: PreferredDrink) {
            println("Welcome, " + name + ". The system is ready.")
            println("But while you work, why not enjoy a cup of " + drink.preference + "?")
            println(prompt.preference)
        }
    }

    object UserPrefs { //このシングルトンオブジェクト内で暗黙のパラメータを定義する
        implicit val prompt = new PreferredPrompt("Yes, master> ")
        implicit val drink = new PreferredDrink("green tea")
    }

    import UserPrefs._ //インポートすることで単一の識別子として参照でき、暗黙のパラメータとして機能する
    val pp = new PreferredPrompt("relax> ")
    val pd = new PreferredDrink("coke")
    Greeter.greet("Qrine")(pp, pd)
    Greeter.greet("Qrine") //省略したパラメータリストは暗黙のパラメータで補完される

    //通常、他の型との競合を防ぐために暗黙のパラメータには特別な型を使う。

    println(maxList(List(1, 2, 3, 5, 2, 7646)))
    println(maxList(List("apple", "banana", "pine", "melon")))

    def maxList[T](elem: List[T])(implicit order: T => Ordered[T]): T =
        //(T => Ordered[T])型の暗黙パラメータのパターンは高頻度で使われるため
        //Scala標準ライブラリではさまざまな共通型向けのorderedメソッドを提供している。
        //なのでわざわざ新たにメソッドを定義しなくてもキチンと機能する。
        elem match {
            case Nil      => throw new IllegalArgumentException("empty list!")
            case x :: Nil => x
            case x :: rest =>
                val maxRest = maxList(rest)(order)
                if (order(x) > maxRest) x else maxRest
        }
    def maxListPoorStyle[T](elem: List[T])(implicit order: (T, T) => Boolean): T = {
        //(T, T) => Boolean　では、等価テストなのか大小テストなのか、はたまた別のテストなのか
        //ハッキリとせずに混乱してしまうため、対応する関数は標準ライブラリには定義されていない。
        //暗黙のパラメータを使う際は、パラメータの型の使命を明確にする必要がある。
        error("※関数値orderの使命が不明瞭な例")
    }

}

