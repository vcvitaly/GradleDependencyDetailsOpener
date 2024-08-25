package io.github.vcvitaly.depopener.enumeration;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum PsiElementType {
    LITERAL("Literal"),
    APPLICATION_ARGUMENT_LIST("Command arguments"),
    APPLICATION_EXPRESSION("Call expression"),
    METHOD_CALL_EXPRESSION("Method call");

    private final String camelName;

    PsiElementType(String camelName) {
        this.camelName = camelName;
    }

    private static final Map<String, PsiElementType> lookup = Arrays.stream(values())
            .collect(Collectors.toMap(v -> v.camelName, Function.identity()));

    public static PsiElementType ofCamelName(String camelName) {
        final PsiElementType type = lookup.get(camelName);
        if (type == null) {
            throw new IllegalArgumentException("Unknown camel name: " + camelName);
        }
        return type;
    }
}
