package cp29_ScalaとJavaの結合

/*
 * ScalaとJavaの結合
 * アノテーション
 */

object No29_2 extends App {

    //独自のアノテーション開発
    object Tests {
        //import java.lang.annotation.*;
        //@Retention(RetentionPolicy.RUNTIME)
        //@Target(ElementType.METHOD)
        //public @interface Ignore {
        //}
        //既に上記Javaコードをコンパイルしてある前提
        @Ignore
        def testData = 0 :: 1 :: -1 :: 5 :: -5 :: Nil
        def test1() {
            assert(testData == testData.head :: testData.tail)
        }
        def test2() {
            assert(testData.contains(testData.head))
        }
    }

    for { //フィルタfor文
        method <- Tests.getClass.getMethods
        if method.getName.startsWith("test") //メソッド名が"test"で始まるメソッド
        if method.getAnnotation(classOf[Ignore]) == null //Ignoreアノテーションがついてないメッソド
    } println("found a test method: " + method)

}
