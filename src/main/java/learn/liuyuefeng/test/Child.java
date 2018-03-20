package learn.liuyuefeng.test;


@Description(desc = "use in class", author = "liu")
public class Child implements Person {
    @Description(desc = "use in method", author = "liu", age = 18)
    public String name() {
        return null;
    }

    public int age() {
        return 0;
    }
    public void sing() {

    }
}
