package learn.liuyuefeng.test;

import sun.security.krb5.internal.crypto.Des;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public class ParseAnn {
    public static void main(String[] args) {
        try {
            //1.使用类加载器加载类
            Class c = Class.forName("learn.liuyuefeng.test.Child");
            //2.找到类上面的注解
            boolean isExist = c.isAnnotationPresent(Description.class);
            if(isExist){
                //3.拿到注解实例
                Description d = (Description)c.getAnnotation(Description.class);
                System.out.println(d.desc());
            }
            //4. 找到方法上的注解
            Method[] ms = c.getMethods();

            for(Method m : ms){
                boolean isMExist = m.isAnnotationPresent(Description.class);
                if(isMExist){
                    Description d = (Description)m.getAnnotation(Description.class);
                    System.out.println(d.desc());

                }
            }


            //另外一种解析方法
            for(Method m: ms){
                Annotation[] as = m.getAnnotations();
                for(Annotation a : as){
                    Description d = (Description)a;
                    System.out.println(d.desc());
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
