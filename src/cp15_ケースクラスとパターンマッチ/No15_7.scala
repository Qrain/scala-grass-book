package cp15_ケースクラスとパターンマッチ
/*
 * ケースクラスとパターンマッチ
 * どこでもパターンを
 * ※パターンはmatch式以外の場所でも使えるよｂ
 */

object No15_7 {
    def main(args: Array[String]) {

        { //変数定義におけるパターン
            val tuple = (123, "abc", Map(1 -> "a")) //まずは(Int,String,Map)のタプルを定義する
            val (num, str, map) = tuple //タプルの各要素を各変数へ代入
            println(num)
            println(str)
            println(map)

            val expr = BinOp("*", Number(3), Number(5))
            val BinOp(op, left, right) = expr
            println(op)
            println(left)
            println(right)
        }

        { //部分関数としてケースシーケンス
            val pattern1: List[Int] => String = {
                //空リストと要素数3以下のリストにマッチする部分関数(それ以外はMatchError)
                case Nil                => "List is Nil..."
                case List(x)            => "1 element : "+x.toString
                case ls @ List(x, y)    => "2 elements : "+(ls mkString ", ")
                case ls @ List(x, y, z) => "3 elements : "+(ls mkString ", ")
                //case _ => "Non match... Must be elements <= 3."
            }

            println(pattern1(List(2, 4, 6)))

            val pattern2: PartialFunction[List[Int], String] = { //部分関数であることを明示的に示す
                //↓パターンが網羅的でなくても警告が表示されない
                case List(x)            => "1 element : "+x.toString
                case ls @ List(x, y)    => "2 elements : "+(ls mkString ", ")
                case ls @ List(x, y, z) => "3 elements : "+(ls mkString ", ")
            }

            println {
                if (pattern2.isDefinedAt(Nil)) //部分関数型にすると自動的にisDefinedAtメソッドが追加される
                    //このメソッドは部分関数への引数が、その関数の定義域内か否かを事前チェックするためにある
                    pattern2(Nil)
                else if (pattern2.isDefinedAt(null))
                    pattern2(null)
                else
                    pattern2(List(1, 2))
            }
        }
    }

    /*
   * 一般に、可能ならば全関数にした方がよいとされている。
   * 部分関数は多かれ少なかれ、ランタイムエラーの余地を残してしまうからである。
   */

    { //for式内のパターン

        val capitals = Map("France" -> "Paris", "Japan" -> "Tokyo")
        for ((country, city) <- capitals) //ジェネレーターによってパターンマッチをする
            println("The capital of "+country+" is "+city)

        val results = List(Some("apple"), None, Some("orange"))
        for (Some(fruit) <- results) //NoneはマッチしないがMatchErrorは起きず値は捨てられる
            println(fruit)
    }

}
