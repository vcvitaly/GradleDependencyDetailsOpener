package io.github.vcvitaly.depopener.resolver;

import io.github.vcvitaly.depopener.enumeration.PsiElementResolutionType;
import java.util.List;

public class ElementTypeScopeArgumentResolver extends BaseScopeArgumentResolver {

    public ElementTypeScopeArgumentResolver() {
        super(
                "METHOD_CALL_EXPRESSION",
                PsiElementResolutionType.ELEMENT_TYPE,
                List.of("LITERAL", "APPLICATION_ARGUMENT_LIST", "APPLICATION_EXPRESSION")
        );
    }
}
