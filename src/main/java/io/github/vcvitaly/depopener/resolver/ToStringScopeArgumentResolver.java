package io.github.vcvitaly.depopener.resolver;

import io.github.vcvitaly.depopener.enumeration.ScopeArgumentResolutionType;
import java.util.List;

public class ToStringScopeArgumentResolver extends BaseScopeArgumentResolver {

    public ToStringScopeArgumentResolver() {
        super(
                "Method call",
                ScopeArgumentResolutionType.TO_STRING,
                List.of("Literal", "Command arguments", "Call expression")
        );
    }
}
