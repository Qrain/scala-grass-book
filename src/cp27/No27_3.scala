package cp27

/*
 * オブジェクトを使ったモジュラープログラミング
 * 抽象化
 */

object No27_3 extends App {
    import org.stairwaybook.recipe._

    //改良後も前回と同じ操作ができる
    val apple = SimpleDatabase.foodNamed("Apple").get
    println(apple)

    println(SimpleBrowser.recipesUsing(apple))

    for (category <- SimpleDatabase.allCategories)
        SimpleBrowser.displayCategory(category)
}

package org.stairwaybook.recipe { //No27_２で定義したパッケージを改良

    abstract class Browser { //No27_2で定義したSimpleBrowserに継承させる
        val database: Database
        def recipesUsing(food: Food) = database.allRecipes.filter(_.ingredients.contains(food))
        def displayCategory(category: database.FoodCategory) = println(category)
    }

    abstract class Database extends FoodCategories { //No27_2で定義したSimpleDatabaseに継承させる
        def allFoods: List[Food]
        def allRecipes: List[Recipe]
        def foodNamed(name: String) = allFoods.find(_.name == name)
    }

    object StudentDatabase extends Database { //第二のモックデータベース
        object FrozenFood extends Food("FrozenFood")
        object HeatItUp extends Recipe(
            "heat it up",
            List(FrozenFood),
            "Microwave the 'food' for 10 minutes.")
        def allFoods = List(FrozenFood)
        def allRecipes = List(HeatItUp)
        def allCategories = List(FoodCategory("edible", List(FrozenFood)))
    }

    object StudentBrowser extends Browser { //上記データベース用のブラウザ
        val database = StudentDatabase
    }
}
