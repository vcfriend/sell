package utils;

import com.imooc.sell.enums.CodeEnum;

/**
 * @author 向亚林
 * 2018/5/17 11:04
 */
public class EnumUtil {

    public static <T extends CodeEnum> T getByCode(Integer code, Class<T> enumClass) {
        for (T each : enumClass.getEnumConstants()) {
            if (code.equals(each.getCode())) {
                return each;
            }
        }
        return null;
    }
}
