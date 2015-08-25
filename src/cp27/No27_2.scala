package cp27

/*
 * オブジェクトを使ったモジュラープログラミング
 * 「レシピ」アプリケーション
 */

object No27_2 extends App {
    import org.stairwaybook.recipe._

    val apple = SimpleDatabase.foodNamed("Apple").get
    println(apple)

    println(SimpleBrowser.recipesUsing(apple))

    for (category <- SimpleDatabase.allCategories)
        SimpleBrowser.displayCategory(category)
}

package org.stairwaybook.recipe {

    abstract class Food(val name: String) { //食材の抽象スーパークラス
        override def toString = name
    }

    class Recipe( //レシピのスーパークラス
            val name: String, //名前
            val ingredients: List[Food], //材料リスト
            val instructions: String //作り方
            ) {
        override def toString = name
    }

    object Apple extends Food("Apple")
    object Orange extends Food("Orange")
    object Cream extends Food("Cream")
    object Sugar extends Food("Sugar")

    object FruitSalad extends Recipe(
        "fruit salad",
        List(Apple, Orange, Cream, Sugar),
        "Stir it all together.")

    //////////////////////////////////////////////////簡単なデータベース
    //↓ミックスインのみで構成されたデータベース
    object SimpleDatabase extends Database with SimpleFoods with SimpleRecipes

    //////////////////////////////////////////////////簡単なブラウザ
    object SimpleBrowser extends Browser {
        val database = SimpleDatabase
    }

}
