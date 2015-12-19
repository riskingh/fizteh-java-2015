package ru.fizteh.fivt.students.riskingh.MiniORM;

import com.google.common.base.CaseFormat;

class NameResolver {
    static final String REGEX = "[A-Za-z0-9_-]*";
    public static Boolean isGood(String name) {
        return name.matches(REGEX);
    }
    static String convertCamelToUnderscore(String name) {
        if (Character.isLowerCase(name.charAt(0))) {
            return CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, name);
        }
        return CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, name);
    }
}
