package cp13_パッケージとインポート {
    /**
     * パッケージとインポート
     * パッケージ
     * ※Java風とC#風の2つの指定方法がある
     */
    object No13_1 {
        def main(args: Array[String]) {
            /**
             * このソースはC#的な入れ子構造でのパッケージ指定方法を示している。
             * なお、Java風の方法ではほとんどJavaと一緒である。
             */
            new sample.bobsrockets.launch.Booster
        }
    }

    package sample { //cp13.sample.PackageSample

        class PackageSample

        package bobsrockets {

            package navigation {
                class Navigator {
                    val ps = new PackageSample
                }
            }

            package launch {
                /**
                 * ルートパッケージは_root_で指定する。
                 * ユーザーが指定可能なトップレベルパッケージは全て_root_のメンバ(_root_の下)である。
                 */
                class Booster {
                    //入れ子構造のため、相対パス？的な指定でよい。
                    val navi = new navigation.Navigator
                    println(navi.ps)
                    val r06 = new _root_.cp06_関数型スタイルのオブジェクト.Rational(2, 7)

                }
            }
        }
    }
}
