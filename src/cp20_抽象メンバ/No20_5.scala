package cp20_抽象メンバ

/*
 * 抽象メンバ
 * 抽象valの初期化
 */

object No20_5 extends App {

    /*
   *
   */
    trait RationalTrait {
        val numerArg: Int
        val denomArg: Int
        require(denomArg != 0) //←具象定義前に評価され0なので例外が起きる
        private val g = gcd(numerArg, denomArg)
        private def gcd(a: Int, b: Int): Int = if (b == 0) a else gcd(b, a % b)
        val numer = numerArg / g
        val denom = denomArg / g
        override def toString = numerArg + "/" + denomArg
    }

    trait LazyRationalTrait {
        val numerArg: Int
        val denomArg: Int
        private val g = {
            require(denomArg != 0)
            gcd(numerArg, denomArg)
        }
        private def gcd(a: Int, b: Int): Int = if (b == 0) a else gcd(b, a % b)
        lazy val numer = numerArg / g
        lazy val denom = denomArg / g
        override def toString = numerArg + "/" + denomArg
    }

    println {
        //    new RationalTrait { //トレイトのインスタンス化
        //      val numerArg = 1
        //      val denomArg = 2
        //    }
    }

    println {
        new { //事前初期化済みフィールド(無名クラスにMix-inしている)
            val numerArg = 1
            val denomArg = 2
        } with RationalTrait
    }

    class RC(n: Int, d: Int) extends { //事前初期化済みフィールド(無名クラスにMix-inしている)
        val numerArg = n
        val denomArg = d
    } with RationalTrait {
        def +(that: RC) = new RC(numer * that.denom + that.numer * denom, denom * that.denom)
    }

    object RO extends {
        val numerArg = 2
        val denomArg = 3
    } with RationalTrait

    println(new RC(1, 12) + new RC(5, 8))
    println(RO)
}

