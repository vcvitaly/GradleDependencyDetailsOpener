package io.github.vcvitaly.depopener.resolver;

import io.github.vcvitaly.depopener.enumeration.PsiElementResolutionType;
import io.github.vcvitaly.depopener.util.ToStringResolverHelperUtil;
import java.util.stream.Stream;

public class ToStringScopeArgumentResolver extends BaseScopeArgumentResolver {

    public ToStringScopeArgumentResolver() {
        super(
                ToStringResolverHelperUtil.addElementWrapperText("Method call"),
                PsiElementResolutionType.TO_STRING,
                Stream.of("Literal", "Command arguments", "Call expression")
                        .map(ToStringResolverHelperUtil::addElementWrapperText)
                        .toList()
        );
    }
}
