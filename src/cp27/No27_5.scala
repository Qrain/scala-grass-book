package cp27

/*
 * オブジェクトを使ったモジュラープログラミング
 * 実行時リンク
 */

object No27_5 extends App {
    import org.stairwaybook.recipe._

    class GotApples(args: String) { //データベースをダイナミックに選択する
        val apple = SimpleDatabase.foodNamed("Apple").get
        object browser extends Browser {
            val database =
                if (args == "student")
                    StudentDatabase
                else
                    SimpleDatabase
        }
        for (recipe <- browser.recipesUsing(apple)) println(recipe)
    }

    //↓パラメータによってデータベースを選択する例
    new GotApples("student") //リンゴのレシピは見付からない
    new GotApples("simple") //フルーツサラダが該当する
}

//package org.stairwaybook.recipe { //No27_２で定義したパッケージを引続き改良
//}
