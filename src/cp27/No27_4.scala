package cp27

/*
 * オブジェクトを使ったモジュラープログラミング
 * モジュールのトレイトへの分割
 */

object No27_4 extends App {
    import cp27.org.stairwaybook.recipe._
}

package org.stairwaybook.recipe { //No27_２で定義したパッケージを引続き改良

    trait FoodCategories { //Databaseクラスにミックスインするトレイト
        //既存のDatabaseクラスのメンバの一部をこのトレイトで定義する
        //    def allCategories: List[FoodCategory]
        case class FoodCategory(name: String, foods: List[Food])
    }

    trait SimpleFoods { //SimpleDatabaseにミックスインするトレイト
        object Pear extends Food("Pear")
        def allFoods = List(Apple, Pear)
        def allCategories = Nil
    }

    trait SimpleRecipes { //SimpleDatabaseにミックスインするトレイト
        this: SimpleFoods => //自分型をSimpleRecipes with SimpleFoodsとする
        //このトレイトをミックスインするサブクラスは必然的にSimpleFoodsもミックスインしなければならない
        object FruitSalad extends Recipe(
            "fruit salad",
            List(Apple, Pear),
            "Mix it all together.")
        def allRecipes = List(FruitSalad)
    }

}
