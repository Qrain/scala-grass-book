package cp23_for式の再説

/*
 * for式の再説
 * プロローグ
 */

object No23_0 extends App {

    case class Person(name: String, isMale: Boolean, children: Person*) //子供は可変長パラメータ

    val lara = Person("Lara", false)
    val bob = Person("Bob", true)
    val julie = Person("Julie", false, lara, bob)
    val persons = List(lara, bob, julie)

    //リストから母と子の全てのペアを探し名前を表示する

    //map,flatMap,filter編
    println {
        persons filter (!_.isMale) flatMap (p => p.children map (c => (p.name, c.name)))
    }

    //for式
    println {
        for (p <- persons; if !p.isMale; c <- p.children) yield (p.name, c.name)
    }

}

