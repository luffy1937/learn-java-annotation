# learn-java-annotation


#### 注解的分类，按照运行机制分：
1. 源码注解	注解只在源码中存在，编译成.class文件就不存在了

2. 编译时注解	注解在源码和.class文件中都存在
  * @Overwrite
  * @Deprecated
  * @Suppvisewarnings
3. 运行时注解	在运行阶段还起作用，甚至会影响运行逻辑的注解
  * @Autowired

#### 按照注解来源分类：
1. 来自JDK
2. 来自第三方
3. 自己的注解
4. 元注解

#### 自定义注解语法要求：
```java
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Description {
    String desc();
    String author();

    int age() default 18;
}
```
1. 使用 @interface 关键字定义注解
2. 成员以无参无异常方式声明， 可以用 default 指定缺省值
3. 成员类型包括原始类型及String、Class	Annotation Enumeration
4. 注解只有一个成员，必须为 value(),使用时可以忽略成员名和赋值号
5. 注解类可以没有成员，称为标识注解

#### 使用注解的语法：
@Description(desc = "i am name", author = "liu", age = 18)


#### 元注解
  * @Target 注解作用域： CONSTRUCTOR FIELD LOCAL_VARIABLE METHOD PACKAGE PARAMETER TYPE
  * @Retention 注解声明周期：SOURCE（只在源码显示，编译时丢弃） CLASS(编译时会记录到class中，运行时忽略) RUNTIME (运行时存在，可以通过反射读取)
  * @Inherited 允许子类继承
  * @Documented 生成Javadoc 时会包含注解

#### 解析注解：
  * 概念：通过反射获取类、函数或成员上的运行时注解信息，从而实现动态控制程序运行的逻辑

