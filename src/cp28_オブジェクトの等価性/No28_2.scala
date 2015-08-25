package cp28_オブジェクトの等価性
import org.omg.PortableServer.POA

/**
 * オブジェクトの等価性
 * 等価メソッドの開発
 */

object No28_2 extends App {

  import scala.collection.mutable.HashSet

  class Point(val x: Int, val y: Int) {
    override def hashCode = 41 * (41 + x) + y //hashCode再定義
    override def equals(other: Any) = other match {
      case that: Point => x == that.x && y == that.y
      case _ => false //それ以外の型との比較はすべてfalse
    }
  }

  { //等価メソッド定義の落とし穴

    val p1, p2 = new Point(10, 10)
    val p3 = new Point(15, 55)

    //↓の等価メソッドの何が問題なのだろうか？？
    println(p1 equals p2)
    println(p2 equals p3)

    //↓しかしコレクションに入れると問題が起きる
    val coll = HashSet(p1)
    //p1 equals p2 ⇒ true になるはずなのに 以下はfalseを返す
    println(coll contains p2)
    //理由は以下のようにp2をAny型とみなすため
    //Anyのequals(other:Any)が呼ばれPointクラスで定義した
    //equalsが呼ばれないところにある。(参照比較しているのでfalse)
    val p2x: Any = p2
    println(p1 equals p2x)
    //なのでPointクラスにAnyのequals(other:Any)を再定義したものを置く
    //しかしHashSetのcontainsメソッドは依然falseを返す
    //理由はHashCodeの違いによる。HashCodeが違うためfalseを返す

    //  JavaではequalsとhashCodeの再定義は同時に
    //  行わなければならないという契約があります。↓
    //  o1 equals o2 がtrueの時、o1.hashCode == o2.hashCodeもまたtrue。
    //hashCodeを上記契約を満たすように定義するとHashSetのcontainsはtrueを返す
  }

  { //ミュータブルなフィールドによってequalsを定義する落とし穴
    class Point(var x: Int, var y: Int) { //←x，yをvarに変えた
      //    def equals(other: Point) = x == other.x && y == other.y
      override def hashCode = 41 * (41 + x) + y //hashCode再定義
      override def equals(other: Any) = other match {
        case that: Point => x == that.x && y == that.y
        case _ => false //それ以外の型との比較はすべてfalse
      }
    }

    val p = new Point(1, 2)
    val coll = HashSet(p)
    println(coll contains p)
    p.x += 1 //x座標を1移動
    println(coll contains p) //加算(var xを変更)したことでhashCodeが変わった

    println(coll.iterator contains p) //しかしpは実際ある
    //以上のことから、ミュータブル(変更可能)な状態を基礎としたequalsメソッドは
    //定義すべきでない。もしも必要ならば別な名前で同機能なメソッドを定義すべきである。
  }
  {
    //数学における同値関係を表すものとしてequalsメソッドを定義できていない

    /**
     * 1.反射律を満たす: 任意のnull以外の値xについて、x.equals(x)はtrueを返さなければならない。
     * 2.対象律を満たす: 任意のnull以外の値x,yについて、x.equals(y)はy.equals(x)が
     * 	trueを返す場合に限り、trueを返さなければならない。
     * 3.推移律を満たす: 任意のnull以外の値x,y,zについて、x.equals(y)がtrueを返し、
     * 	y.equals(z)がtrueを返す場合、x.equals(z)もtrueを返さなければならない。
     * 4.首尾一貫していなければならない: 任意のnull以外の値x,yについて、x.equals(y)の
     * 	複数回の呼び出しは、equalsの比較に使われる情報が変更されない限り、一貫して
     * 	trueかfalseを返さなければならない。
     * 5.任意のnull以外の値xについてx.equals(null)はfalseを返さなければならない。
     */
    //Pointクラスのequalsメソッドは上記条件を満たしているがサブクラスを定義すると
    //話が難しくなるらしい。
    object Color extends Enumeration {
      val Red, Orage, Yellow, Green, Blue, Indigo, Violet = Value
    }
    class ColoredPoint(x: Int, y: Int, val color: Color.Value) extends Point(x, y) {
      override def equals(other: Any) = other match {
        case that: ColoredPoint =>
          this.color == that.color && super.equals(that)
        case _ => false
      }
    }
    val p = new Point(1, 2)
    val cp = new ColoredPoint(1, 2, Color.Green)
    println(p equals cp) //trueになる
    println(cp equals p) //falseになりequals契約(対称律)が破られる
    println(HashSet(p) contains cp)
    println(HashSet[Point](cp) contains p)
  }
}

