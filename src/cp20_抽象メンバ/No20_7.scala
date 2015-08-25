package cp20_抽象メンバ

/*
 * 抽象メンバ
 * パス依存型
 */

object No20_7 extends App {

    /*
   *
   */

    class Food //食料
    abstract class Animal { //動物
        type SuitableFood <: Food //抽象型を宣言しFood型という上限境界を定める
        def eat(food: SuitableFood)
    }

    class Grass extends Food
    class Cow extends Animal {
        type SuitableFood = Grass
        override def eat(food: Grass) {}
    }

    class DogFood extends Food
    class Dog extends Animal {
        type SuitableFood = DogFood
        override def eat(food: DogFood) {}
    }

    val cow = new Cow
    val dog = new Dog

    //下のようにobject.Typeでアクセスされる型をパス依存型という
    dog eat new dog.SuitableFood //犬に犬用の餌を与えると勿論たべる
    //  dog.eat(new cow.SuitableFood) //しかし犬に牛が食う餌を与えると･･･

}

