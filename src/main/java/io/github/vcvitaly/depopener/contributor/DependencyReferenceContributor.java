package io.github.vcvitaly.depopener.contributor;

import com.intellij.patterns.PlatformPatterns;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;
import com.intellij.psi.PsiReferenceContributor;
import com.intellij.psi.PsiReferenceProvider;
import com.intellij.psi.PsiReferenceRegistrar;
import com.intellij.psi.impl.source.tree.LeafPsiElement;
import com.intellij.util.ProcessingContext;
import io.github.vcvitaly.depopener.resolver.ElementTypeScopeArgumentResolver;
import io.github.vcvitaly.depopener.resolver.ScopeArgumentResolver;
import org.jetbrains.annotations.NotNull;

public class DependencyReferenceContributor extends PsiReferenceContributor {

    private final ScopeArgumentResolver scopeArgumentResolver;

    public DependencyReferenceContributor() {
        this(new ElementTypeScopeArgumentResolver());
    }

    public DependencyReferenceContributor(ScopeArgumentResolver scopeArgumentResolver) {
        this.scopeArgumentResolver = scopeArgumentResolver;
    }

    @Override
    public void registerReferenceProviders(@NotNull PsiReferenceRegistrar registrar) {
        registrar.registerReferenceProvider(
                PlatformPatterns.psiElement(LeafPsiElement.class),
                new PsiReferenceProvider() {
                    @Override
                    public PsiReference @NotNull [] getReferencesByElement(@NotNull PsiElement element,
                                                                           @NotNull ProcessingContext context) {
                        return new PsiReference[0];
                    }
                }
        );
    }
}
