package cp13_パッケージとインポート
/**
 * パッケージとインポート
 * アクセス修飾子
 * ※
 */
object No13_4 {
    def main(args: Array[String]) {
        //.scalaという拡張子のファイルには冒頭に以下の3つが必ずインポートされる
    }
}

class Outer { //非公開メンバ(private)
    class Inner {
        private def f() { println("f") }
        class InnerMost {
            f() //OK
        }
    }
    //(new Inner).f() //内部クラスでもprivateメンバはアクセスできない(Javaでは出来る)
}

package p { //限定公開メンバ(protected)

    class Super {
        protected def f() { println("f") }
    }

    class Sub extends Super {
        f()
    }

    class Other {
        //(new Super).f() //Superと継承関係にないのでアクセスできない(Javaだと同パッケージ内なのでアクセスできる)
    }
}

//アクセス保護のスコープ
package p_root {

    package p_1 {

        private[p_root] class Outer {

            class Inner {
                private[Outer] val v1 = 100
                private[this] val v2 = 200 //一番厳しい(this.v2形式でないとアクセス不可)

                val v2_1 = this.v2 //OK
                //val v2_2 = (new Inner).v2 //インスタンスが違うのでダメ

            }
        }
    }

    package p_2 {
        object Obj {
            private[p_2] val v = new p_1.Outer
        }
    }
}

//可視性とコンパニオンオブジェクト
//RocketクラスはRocketオブジェクトの非公開メンバにアクセスでき、またその逆も可能である。
//またシングルトンオブジェクトはサブクラスを持たないので、protected修飾子は無意味である。
class Rocket {
    import Rocket.fuel
    private def canGoHomeAgain = fuel > 20
}

object Rocket {
    private def fuel = 10
    def chooseStrategy(rocket: Rocket) {
        if (rocket.canGoHomeAgain)
            goHome()
        else
            pickAStar()
    }
    def goHome() {}
    def pickAStar() {}
}
