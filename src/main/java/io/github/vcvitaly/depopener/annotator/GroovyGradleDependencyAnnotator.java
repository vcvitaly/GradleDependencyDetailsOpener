package io.github.vcvitaly.depopener.annotator;

import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.Annotator;
import com.intellij.lang.annotation.HighlightSeverity;
import com.intellij.openapi.editor.colors.CodeInsightColors;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.impl.source.tree.LeafPsiElement;
import io.github.vcvitaly.depopener.resolver.ElementTypeScopeArgumentResolver;
import io.github.vcvitaly.depopener.resolver.ScopeArgumentResolver;
import io.github.vcvitaly.depopener.util.Constants;
import org.jetbrains.annotations.NotNull;

public class GroovyGradleDependencyAnnotator implements Annotator {

    private final ScopeArgumentResolver scopeArgumentResolver;

    // TODO Remove and replace with DI
    public GroovyGradleDependencyAnnotator() {
        this(new ElementTypeScopeArgumentResolver());
    }

    public GroovyGradleDependencyAnnotator(ScopeArgumentResolver scopeArgumentResolver) {
        this.scopeArgumentResolver = scopeArgumentResolver;
    }

    @Override
    public void annotate(@NotNull PsiElement element, @NotNull AnnotationHolder holder) {
        if (holder.isBatchMode()) {
            return;
        }

        final PsiFile containingFile = element.getContainingFile();
        if (containingFile.getName().endsWith(".gradle")) {

            if (element instanceof LeafPsiElement lpe) {
                if (scopeArgumentResolver.resolve(lpe)) {
                    System.out.printf("%s at %d%n", lpe.getText(), System.currentTimeMillis());
                    final String msg = getMsg();
                    final TextRange textRange = lpe.getTextRange();
                    holder.newAnnotation(HighlightSeverity.INFORMATION, msg)
                            .range(textRange)
                            .textAttributes(CodeInsightColors.INACTIVE_HYPERLINK_ATTRIBUTES)
                            .create();
                }
            }
        }
    }

    private String getMsg() {
        return Constants.TOOLTIP;
    }
}
