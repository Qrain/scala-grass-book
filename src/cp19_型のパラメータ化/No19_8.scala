package cp19_型のパラメータ化
import scala.math.Ordered

/*
 * 型のパラメータ化
 * 上限境界(upper bounds)
 */

object No19_8 extends App {

    /*
   *
   */
    class Person(val firstName: String, val lastName: String) extends Ordered[Person] {
        def compare(that: Person) = {
            val lastNameComparison = lastName.compareToIgnoreCase(that.lastName)
            if (lastNameComparison != 0)
                lastNameComparison
            else
                firstName.compareToIgnoreCase(that.firstName)
        }
        override def toString = firstName+" "+lastName
    }

    val 顕示 = new Person("Kenji", "Oyama")
    val 沙理 = new Person("Sari", "Nomura")
    val 忠男 = new Person("Tadao", "Kaneda")

    println(顕示 < 沙理)
    println(顕示 > 忠男)

    val personList = 顕示 :: 沙理 :: 忠男 :: Nil
    orderedMergeSort(personList) foreach println

    def orderedMergeSort[T <: Ordered[T]](xs: List[T]): List[T] = {
        def merge(xs: List[T], ys: List[T]): List[T] = {
            (xs, ys) match {
                case (Nil, _) => ys
                case (_, Nil) => xs
                case (x :: xsa, y :: ysa) =>
                    if (x < y)
                        x :: merge(xsa, ys)
                    else
                        y :: merge(xs, ysa)
            }
        }
        val n = xs.size / 2
        if (n == 0)
            xs
        else {
            val (ys, zs) = xs.splitAt(n)
            merge(orderedMergeSort(ys), orderedMergeSort(zs))
        }
    }

}

