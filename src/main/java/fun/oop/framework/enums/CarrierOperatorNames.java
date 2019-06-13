package fun.oop.framework.enums;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * Created by xiaoming on 16-3-3.
 */
public enum CarrierOperatorNames {
    CMCC("CMCC", "中国移动"),
    CUCC("CUCC", "中国联通"),
    CTCC("CTCC", "中国电信"),
    VIRTUAL("VIRTUAL","虚拟运营商");

    public final String name;
    public final String desc;
    private static List<String> names = Lists.newArrayList();
    static {
        for(CarrierOperatorNames e: values())
            names.add(e.name);
    }

    CarrierOperatorNames(String name, String desc) {
        this.name = name;
        this.desc = desc;
    }

    public static boolean valid(String name) {
        return names.contains(name);
    }

    public static CarrierOperatorNames valueOfName(String name){
        if(!valid(name)) return null;
        else return valueOf(name);
    }

    public static String desc(String name){
        if(!valid(name)) return "";
        else return valueOf(name).desc;
    }
}
