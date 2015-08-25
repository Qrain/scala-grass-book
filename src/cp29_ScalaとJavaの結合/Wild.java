package cp29_ScalaとJavaの結合;

import java.util.Collection;
import java.util.Vector;

public class Wild {
    /**
     * Collection<?>の?部分はワイルドカードで何らかの型を示す
     *
     * @return 何らかの型のコレクション
     */
    public Collection<?> contents() {
        Collection<String> stuff = new Vector<String>();
        stuff.add("a");
        stuff.add("b");
        stuff.add("see");
        return stuff;
    }
}
