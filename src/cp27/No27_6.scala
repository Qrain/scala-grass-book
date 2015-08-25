package cp27

/*
 * オブジェクトを使ったモジュラープログラミング
 * モジュールインスタンスの管理
 */

object No27_6 extends App {
    import cp27.org.stairwaybook.recipe._

    val category = StudentDatabase.allCategories.head
    println(category)
    //  SimpleBrowser.displayCategory(category) //同名のFoodCategoryクラスではあるが実質的な型は違うためエラーが発生する
}


//package org.stairwaybook.recipe { //No27_2で定義したパッケージを引続き改良
//}
