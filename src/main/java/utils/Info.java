package utils;

import javax.persistence.Tuple;
import java.util.List;

/**
 * @author 向亚林
 * 2018/3/25 11:31
 */
public class Info {

    static public <T> void print(T result) {
        System.out.println("\n\n\n 打印查询结果: ");
        if (result != null) {
            System.out.println(result);
        }
        System.out.println();
    }
    static public <T> void print(List<T> results) {
        System.out.println("\n\n\n 打印查询结果: ");
        if (null == results ) {
            return;
        }
        for (T result : results) {
            System.out.print(result);
        }
        System.out.println();
    }


//    static public void print(List<Tuple> results, Expression... infos) {
//        System.out.println("\n\n\n 打印查询结果: ");
//        if (null == results) return;
//        for (Tuple result : results) {
//            for (Expression info : infos) {
//                System.out.print("   "+info.toString()+"   "+result.get(info).toString());
//            }
//            System.out.println();
//        }
//        System.out.println();
//    }

}
