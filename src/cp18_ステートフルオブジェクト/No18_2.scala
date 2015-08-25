package cp18_ステートフルオブジェクト

/*
 * ステートフルオブジェクト(状態的な、つまり変遷していくオブジェクト)
 * 再代入可能な変数とプロパティ
 */

object No18_2 extends App {

    //再代入可能な変数には通常GetterとSetterが定義される
    //Scalaではvarを定義すると自動的にそれらのメソッドが生成される

    //客観的には全く同じ
    val t1 = new Time1
    t1.hour = 22
    t1.minute = 11
    val t2 = new Time2
    t1.hour = 22
    t1.minute = 1

    val t = new Thermometer
    println(t)
    t.fahrenheit = -2.7f
    println(t) //ま、そーゆーことだ･･･
}

class Time1 { //2つの公開varを持つクラス
    var hour = 12
    var minute = 45
}

class Time2 { //Time1の定義と同じ意味
    private[this] var h = 12
    private[this] var m = 45
    def hour = h
    def hour_=(x: Int) { h = x }
    def minute = m
    def minute_=(x: Int) { m = x }
}

class Time3 { //アクセスメソッドに独自の処理を加え入力値を制限する
    private[this] var h = 12
    private[this] var m = 45
    def hour = h
    def hour_=(x: Int) { //時間は0～23まで
        require(x >= 0 && x < 24)
        h = x
    }
    def minute = m
    def minute_=(x: Int) {
        require(x >= 0 && x < 60)
        m = x
    }
}

class Thermometer { //アクセスメソッドに独自の処理を加え入力値を制限する
    var celsius: Float = _ //通常の公開変数(摂氏を管理)
    def fahrenheit = celsius * 9 / 5 + 32 //celsiusを華氏に変換して返す
    def fahrenheit_=(f: Float) { celsius = (f - 32) * 5 / 9 } //引数をcelsiusに摂氏に変換して代入する
    override def toString = fahrenheit+"F/"+celsius+"C"
}

