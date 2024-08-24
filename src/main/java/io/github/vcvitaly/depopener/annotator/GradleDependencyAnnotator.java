package io.github.vcvitaly.depopener.annotator;

import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.Annotator;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.impl.source.tree.LeafPsiElement;
import io.github.vcvitaly.depopener.resolver.ScopeArgumentResolver;
import io.github.vcvitaly.depopener.resolver.ScopeArgumentResolverImpl;
import org.jetbrains.annotations.NotNull;

public class GradleDependencyAnnotator implements Annotator {

    private final ScopeArgumentResolver scopeArgumentResolver;

    // TODO Remove and replace with DI
    public GradleDependencyAnnotator() {
        this(new ScopeArgumentResolverImpl());
    }

    public GradleDependencyAnnotator(ScopeArgumentResolver scopeArgumentResolver) {
        this.scopeArgumentResolver = scopeArgumentResolver;
    }

    @Override
    public void annotate(@NotNull PsiElement element, @NotNull AnnotationHolder holder) {
        if (holder.isBatchMode()) {
            return;
        }

        final PsiFile containingFile = element.getContainingFile();
        if (containingFile.getName().endsWith(".gradle")) {
            try {
                Thread.sleep(0, 100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            /*if (!element.getText().equals("'org.springframework.boot:spring-boot-starter-web'")) {
                return;
            }*/
            if (element instanceof LeafPsiElement lpe) {
                if (scopeArgumentResolver.resolve(lpe)) {
                    System.out.println(lpe.getText());
                }
            }
            /*if (element.getText().equals("'org.springframework.boot:spring-boot-starter-web'")) {
                System.out.println("Ok");
            }*/
        }
    }
}
