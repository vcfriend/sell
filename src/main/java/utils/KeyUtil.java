package utils;

import java.util.Random;

/**
 * @author 向亚林
 * 2018/3/27 9:31
 */
public class KeyUtil {
    /**
     * 生成唯一主键
     * 格式: 时间+随机数
     * @return
     */
    public static synchronized String generateUniqueKey() {
        Random random = new Random();
        Integer integer = random.nextInt(900000) + 100000;

        return System.currentTimeMillis() + String.valueOf(integer);
    }
}
