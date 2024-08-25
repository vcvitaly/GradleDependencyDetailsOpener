package io.github.vcvitaly.depopener.util;

import io.github.vcvitaly.depopener.enumeration.PsiElementType;

public final class ToStringResolverHelperUtil {

    private ToStringResolverHelperUtil() {
    }

    public static String addElementWrapperText(String s) {
        return "Element(%s)".formatted(PsiElementType.ofCamelName(s));
    }
}
