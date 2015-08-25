package cp21_暗黙の型変換とパラメーター

/*
 * 暗黙の型変換とパラメーター
 * 可視境界(view bound)
 */

object No21_6 extends App {

    //前回21_5のサンプルでは暗黙のパラメータを使わず、明示的にorder関数を使用していた。
    //しかし、以下のように暗黙機能を活用することもできる。
    def maxList[T](elem: List[T])(implicit order: T => Ordered[T]): T = elem match {
        case Nil      => throw new IllegalArgumentException("empty list!")
        case x :: Nil => x
        case x :: rest =>
            val maxRest = maxList(rest) //maxListの第2引数リストはimplicit宣言のため再帰時にも省略可能
            if (x > maxRest) x else maxRest //if文条件式のxにも、暗黙的にorderが適用されるため省略可能
    }
    println(maxList(List(1, 2, 3, 5, 2, 7646)))
    println(maxList(List("apple", "banana", "pine", "melon")))
    //↑のmaxListメソッドはメソッド本体内で関数値orderを1度も明示的に使用していない。
    //つまり、関数値の名前はorderでなくても何でも良いはずである。
    //例えば･･･
    def maxListCat[T](elem: List[T])(implicit cat: T => Ordered[T]): T = elem match {
        case Nil       => throw new IllegalArgumentException("Nyaaaa~~~oO!!")
        case x :: Nil  => x
        case x :: rest => ((max: T) => if (x > max) x else max)(maxListCat(rest))
    }
    println(maxListCat(List(1, 2, 3, 5, 2, 7646)))
    println(maxListCat(List("apple", "banana", "pine", "melon")))
    //のように、関数名がcatであっても何ら問題は無いはずであり、実際問題は無い。
    //暗黙的に型変換関数が挿入されるため関数名が意味を成さない以上のようなパターンは
    //よく見られるため、Scalaには可視境界(view bound)という関数名を省略しコードを簡略化できる機能がある。
    def maxListViewBound[T <% Ordered[T]](elem: List[T]): T = elem match {
        case Nil      => error("Vieeeewww BooOOOund!!!")
        case x :: Nil => x
        case x :: rest =>
            ((max: T) => {
                if (x > max) x else max //暗黙的にorderer(x)に変更される
            })(maxListViewBound(rest)) //暗黙的に(orderer)が追加される
    }
    println(maxListViewBound(List(1, 2, 3, 5, 2, 7646)))
    println(maxListViewBound(List("apple", "banana", "pine", "melon")))
    //上記のメソッドたちの処理内容は全て同じだが、下に行くほど簡潔になっている。
    //[T <% Ordered[T]]は、「Ordered[T]として扱える任意のTを使える」と読むことができる。
    //これにより直接Orderedトレイトのサブ型ではないInt型も、このメソッドへと渡すことができる。
    implicit def toOption[T](x: T) = Some(x)

    println(test("AA"))

    def test[T <% Option[T]](x: T) = {
        if (x == None) error("内容がないよ～う") else x.get
    }
}

