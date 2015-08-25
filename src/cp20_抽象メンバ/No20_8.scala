package cp20_抽象メンバ

/*
 * 抽象メンバ
 * 列挙(enumeration)
 */

object No20_8 extends App {

    object Color extends Enumeration { //色を定義している列挙
        val Red = Value
        val Green = Value
        val Blue = Value
        val Yellow, Orange, Black = Value //←短縮して定義できる
    }

    object Direction extends Enumeration { //方角を定義している列挙
        val North = Value("North")
        val East = Value("East")
        val South = Value("South")
        val West = Value("West")
    }

    /*
   * Color.ValueとDirection.Valueは共にパス依存型であり、
   * パス部分が互いに違うので、型の性質は全く別物である。
   */

    println(Color.Red)
    //valuesメソッドでValueSetインスタンス取らんと反復処理できないじゃないか！!
    for (d <- Direction.values) println(d.id+": "+d)
    for (c <- Color.values) println(c.id+": "+c)
}

