package learn.liuyuefeng.demo;

import javafx.scene.control.Tab;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Test {
    public static void main(String[] args) {
        Filter f1 = new Filter();
        f1.setId(10);//查询ID为10的用户
        Filter f2 = new Filter();
        f2.setUserName("liu");//模糊查询用户名为LIU的用户
        Filter f3 = new Filter();
        f3.setEmail("liu@qq.com");//查询// 邮箱为其中任意一个

        String sql1 = query(f1);
        String sql2 = query(f2);
        String sql3 = query(f3);

        System.out.println(sql1);
        System.out.println(sql2);
        System.out.println(sql3);
    }
    private static String query(Object f){
        StringBuilder sb = new StringBuilder();
        //1.获取到class
        Class c = f.getClass();
        //2.获取到table的名字
        boolean exist = c.isAnnotationPresent(Table.class);
        if(! exist){
            return null;
        }
        Table t = (Table)c.getAnnotation(Table.class);
        String tableName = t.value();
        sb.append("select * from ").append(tableName).append( " where 1=1");
        //3.遍历所有字段
        Field[] fAray = c.getDeclaredFields();

        for(Field field: fAray){
            //4.处理每个字段对应的sql
            //4.1 拿到字段名
            boolean fExists = field.isAnnotationPresent(Column.class);
            if(! fExists){
                continue;
            }
            Column column = field.getAnnotation(Column.class);
            String columnName = column.value();
            //4.2 拿到字段值
            String fieldName = field.getName();
            String getMethodName = "get" + fieldName.substring(0,1).toUpperCase() + fieldName.substring(1);
            Object fieldValue = null;
            try {
                Method getMethod = c.getMethod(getMethodName);
                fieldValue = getMethod.invoke(f);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            //4.3 拼装sql
            if(fieldValue == null || (fieldValue instanceof Integer && (Integer)fieldValue == 0)){
                continue;
            }
            if(fieldValue instanceof  String){
                if(((String)fieldValue).contains(",")){
                    String[] values = ((String)fieldValue).split(",");
                    StringBuilder emailset = new StringBuilder();
                    emailset.append("(");
                    for( String value : values){
                        emailset.append("'").append(value).append("'").append(",");
                    }
                    emailset.deleteCharAt(emailset.length() - 1);
                    emailset.append(")");
                    sb.append(" and ").append(fieldName).append(" in ").append(emailset);
                    continue;
                }
                fieldValue = "'" + fieldValue + "'";
            }
            sb.append(" and ").append(fieldName).append("=").append(fieldValue);
        }
        return sb.toString();
    }
}
