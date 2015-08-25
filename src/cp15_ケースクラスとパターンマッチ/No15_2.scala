package cp15_ケースクラスとパターンマッチ
/**
 * ケースクラスとパターンマッチ
 * パターンの種類
 * ※ついにキタよッッ、パターンマッチング！！！！！！！(＾A＾)Ⅱ
 */
object No15_2 {
    def main(args: Array[String]) {

        val any: Any = List()

        println {
            any match { //定数パターン
                case 5       => "Five"
                case true    => "Truth"
                case "hello" => "hi!"
                case Nil     => "The empty list"
                case _       => "Something else"
            }
        }

        println {
            any match { //変数パターン(_と違いオブジェクトに変数を束縛する)
                case 0             => "Zero!!"
                case somethingElse => "Not zero : " + somethingElse
            }
        }

        println { //変数？定数？
            import Math.{ E, PI } //円周率とネイピア数
            val pi = PI //変数piに円周率を入れる
            E match {
                case PI   => "変な数学Ⅰ･･･  Pi = " + PI //大文字から始まってると定数と解釈する
                case `pi` => "小文字ローカル変数へのマッチ Pi = " + pi //バッククォートで囲むと定数パターンと認識する
                case pi   => "変な数学Ⅱ･･･  Pi = " + pi //小文字だとパターン変数と解釈する(↑で定義したpiにマッチしない)
            }
        }

        println { //コンストラクタパターン(深いパターンマッチ)
            import No15_1._
            val expr = BinOp("+", Var("m"), Number(0))
            expr match {
                case BinOp("+", e, Number(0)) => "A deep match" //BinOpだけでなくNumber(0)まで判定する
                case _                        => //ワイルドカードパターン(Default処理)
            }
        }

        println { //シーケンスパターン(任意の要素数でマッチング)
            val seq = List(0, (), "?")
            seq match {
                case List(0, _, _) => "固定長シーケンス" //要素数3で先頭要素が0のリストにマッチ(固定長シーケンス	)
                case List(0, _*)   => "可変長シーケンス" //先頭要素が0の任意の長さのリストにマッチ
                case _             =>
            }
        }

        println { //タプルパターン
            val tuple = (Nil, " ←Nil? ", Math.E)
            tuple match {
                case (a, b, c) => "Matched " + a + b + c
                case _         =>
            }
        }

        println { //型付きパターン
            val x: Any = Map(0 -> "z", 1 -> "o", 2 -> "t")

            //型テスト+型キャストを行う場合は、isInstanceOf[T]やasInstanceOf[T]
            //を使うのではなく、型付きパターンを使用したほうが良い･･･らしいよ。
            x match { //各型にマッチすれば自動でキャストしてくれる
                case s: String    => s.size
                case m: Map[_, v] => m.size //_以外に小文字の型変数も使える
                case _            => -1
            }
        }

        { //型消去(type erasure)
            println { //Javaと同じ消去モデルのため、実行時にMapの型まで判別できない。
                //val x: Any = Map(0 -> 0, 1 -> 1, 2 -> 11, 3 -> 111)
                val x: Any = Map("a" -> "A") //String -> String でもtrueを返してしまう。
                x match {
                    case m: Map[Int, Int] => true //Int -> Int のマップかどうか
                    case _                => false
                }
            }
            println { //しかし、配列はJavaでもScalaでも特別な扱いを受けている。
                val ary = Array("String")
                ary match {
                    case a: Array[String] => "Yes"
                    case _                => "No"
                }
            }
        }

        println { //変数の束縛
            val expr = UnOp("abs", UnOp("abs", Var("x"))) //変数xに絶対値演算を2度適用している
            expr match {
                case UnOp("abs", e @ UnOp("abs", _)) => e //部分的に変数に束縛している
                case _                               =>
            }
        }

    }
}
