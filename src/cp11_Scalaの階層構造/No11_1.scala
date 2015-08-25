package cp11_Scalaの階層構造

/*
 * Scalaの階層構造
 * どんな構成か・・・？
 * ※
 */
object No11_1 {

    /*
   * Scalaにおける全クラスのスーパー型はAny型。
   * そして、Intなどの全値クラスはAny⇒AnyValという型がスーパーである。
   * それ以外の全参照型はAny⇒AnyRef(java.lang.Object)を継承する。
   * ユーザーが新規に作成したクラスもAnyRefをデフォルトで継承する。
   */

    def main(args: Array[String]) {
        //↓当たり前だが全て真を返す
        println((new Default1).isInstanceOf[AnyRef])
        println((new Default2).isInstanceOf[AnyRef])
        println(Double.isInstanceOf[AnyRef])
    }

    class Default1
    class Default2 extends AnyRef

}

