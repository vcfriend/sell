package utils;

/**
 * @author 向亚林
 * 2018/5/2 11:50
 */
public class MathUtil {

    /**
     * 比较两个金额是否相等,精度为0.01
     * @param d1
     * @param d2
     * @return
     */
    public static Boolean moneyEquals(Double d1, Double d2) {
        return Math.abs(d1 - d2) < 0.01;
    }
}
