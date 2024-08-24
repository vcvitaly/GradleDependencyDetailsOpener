package io.github.vcvitaly.depopener.resolver;

import io.github.vcvitaly.depopener.enumeration.ScopeArgumentResolutionType;
import java.util.List;

public class ElementTypeScopeArgumentResolver extends BaseScopeArgumentResolver {

    public ElementTypeScopeArgumentResolver() {
        super(
                "METHOD_CALL_EXPRESSION",
                ScopeArgumentResolutionType.ELEMENT_TYPE,
                List.of("LITERAL", "APPLICATION_ARGUMENT_LIST", "APPLICATION_EXPRESSION")
        );
    }
}
