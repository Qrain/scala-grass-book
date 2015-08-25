package cp05_基本型と演算子
/**
 * Scalaのリテラル(コードに直接数値等を書くこと)
 */
object No5_2 {

  def main(args: Array[String]) {
    /**
     * 整数リテラル
     */
    val hex = 0xBadBed //悪い寝床16進数
    //val oct = 0765        //8進数
    val dec = 1192 //10進数

    /**
     * 浮動小数点数リテラル
     */
    val big = 1.234
    val bigger = 1.234e1 //1.234 × 10の1乗
    val biggest = 1.234E45 //1.234 × 10の45乗
    val float = 0.24f
    val floater = 0.24e1f
    val floatest = 0.24E15F

    /**
     * 文字リテラル
     */
    val a1 = 'A'
    val a2 = '\101' //Aは8進数で101
    val d = '\u0044' //Unicode16進表記でD
    val f = '\u0046' //Unicode16進表記でF
    val escape = '\\'

    /**
     * 文字列リテラル
     */
    println("\"黄桃\"は\\250ですが、" +
      "\'栗\'は\\100です。") //通常文字列
    println(""""黄桃"は\250ですが、
                                 '栗'は\100です。""") //生文字列
    println("""|"黄桃"は\250ですが、
    				|'栗'は\100です。""".stripMargin) //生文字列整列(デフォは行頭に|)
    println("""$"黄桃"は\250ですが、
    				$'栗'は\100です。""".stripMargin('$')) //生文字列整列+α

    /**
     * シンボルリテラル
     */
    val sym1 = '識別子
    val sym2 = Symbol("識別子")
    println(sym1 == sym2) //参照オブジェクトは同一
  }
}
