package cp12_トレイト

/*
 * トレイト
 * 短形オブジェクト
 * ※
 */
object No12_3 {
    def main(args: Array[String]) {
        val rect = new Rectangle(new Point(10, 10), new Point(255, 350))

        println(rect.height)
    }

    def rectMerge(rect1: Rectangular, rect2: Rectangular): Rectangular =
        new Rectangular {
            val topLeft = rect1.topLeft
            val bottomRight = rect2.bottomRight
        }
}

class Point(val x: Int, val y: Int) //点クラス

class Rectangle(val topLeft: Point, val bottomRight: Point) extends Rectangular {
    //もっと多くの幾何学メソッド
}

/**
 * このクラスは↓のトレイトRectangularをミックスインするだけで
 * トレイトの提供する全メソッドを利用することができる。
 */
abstract class Component extends Rectangular

trait Rectangular {
    ///////////////////////抽象メンバ
    def topLeft: Point
    def bottomRight: Point
    ///////////////////////具象メンバ
    def left = topLeft.x
    def right = bottomRight.x
    def top = topLeft.y
    def bottom = bottomRight.y
    def width = right - left
    def height = bottom - top
    def bounds = height * width //面積的な意味
    //もっと多くの幾何学メソッド
}
