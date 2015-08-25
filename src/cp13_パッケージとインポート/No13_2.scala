package cp13_パッケージとインポート
/**
 * パッケージとインポート
 * インポート
 * ※
 */
object No13_2 {

    import bobsdelights.Fruit //単一型インポート
    import bobsdelights._ //オンデマンドインポート
    import bobsdelights.Fruits._ //静的クラスフィールドインポート

    //{}インポートセレクター節を付けることで、より柔軟なインポートが可能になる。
    import Fruits.{ Apple, Orange } //Fruits内のAppleとOrangeのみインポート
    import Fruits.{ Pear => 梨 } //Fruits.Pearか梨でアクセスできる。
    import java.sql.{ Date => SDate } //こうすることで↓のDateクラスと共存させられる。
    import java.util.Date
    import Fruits.{ Apple => McIntosh, _ } //Fruitsのメンバを全てインポートすると同時にAppleをMcIntoshに名称変更する
    import Fruits.{ Pear => _, _ } //Pear以外をインポートする。X => _ とすることでXを隠すという意味になる。

    def main(args: Array[String]) {
        showFruit(Pear)

        import java.util.regex //このようにパッケージもインポートできる
        val pat = regex.Pattern.compile("a*b")
    }

    def showFruit(f: Fruit) {
        //オブジェクトフィールドを動的にインポートすることでメソッド内でのフィールド記述を簡略化できる
        import f._
        println(name + "s are " + color)
    }
}

package bobsdelights {

    abstract class Fruit(val name: String, val color: String)

    object Fruits {
        val menu = List(Apple, Orange, Pear)
        object Apple extends Fruit("apple", "red")
        object Orange extends Fruit("orange", "orange")
        object Pear extends Fruit("pear", "yellowish")
    }
}

