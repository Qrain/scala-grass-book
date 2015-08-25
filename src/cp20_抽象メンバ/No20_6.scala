package cp20_抽象メンバ

/**
 * 抽象メンバ
 * 抽象型
 */
object No20_6 extends App {

    /**
     * 抽象型を適切に使う例
     */
    class Food //食料
    abstract class Animal { //動物
        type SuitableFood <: Food //抽象型を宣言しFood型という上限境界を定める
        def eat(food: SuitableFood)
    }

    class Grass extends Food
    class Cow extends Animal {
        type SuitableFood = Grass
        def eat(food: Grass) {}
    }

    class Fish extends Food

    val cow = new Cow
    cow eat new Grass
    //  cow eat new Fish//牛に魚を食べさせようとすると･･･
}

