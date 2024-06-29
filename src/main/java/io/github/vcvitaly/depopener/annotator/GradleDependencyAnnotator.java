package io.github.vcvitaly.depopener.annotator;

import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.Annotator;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;

public class GradleDependencyAnnotator implements Annotator {
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
            System.out.println("Ok");
        }
    }
}
