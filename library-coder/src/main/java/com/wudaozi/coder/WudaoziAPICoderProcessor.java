package com.wudaozi.coder;

import com.wudaozi.coder.uniform.UniformMatrixClassCoder;
import com.wudaozi.coder.uniform.UniformVectorClassCoder;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;

@SupportedAnnotationTypes({
        "com.wudaozi.coder.uniform.UniformVectorClassCoder",
        "com.wudaozi.coder.uniform.UniformMatrixClassCoder"})
@SupportedSourceVersion(SourceVersion.RELEASE_7)
public class WudaoziAPICoderProcessor extends AbstractProcessor {
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        final Set<? extends Element> elementsWithUniformVectorClassCoder = roundEnv.
                getElementsAnnotatedWith(UniformVectorClassCoder.class);
        dealElementsWithUniformVectorClassCoder(elementsWithUniformVectorClassCoder, roundEnv);

        final Set<? extends Element> elementsWithUniformMatrixClassCoder = roundEnv.
                getElementsAnnotatedWith(UniformMatrixClassCoder.class);
        dealElementsWithUniformMatrixClassCoder(elementsWithUniformMatrixClassCoder, roundEnv);
        return true;
    }

    private void dealElementsWithUniformMatrixClassCoder(
            Set<? extends Element> elementsWithUniformMatrixClassCoder, RoundEnvironment roundEnv) {
        for (Element element : elementsWithUniformMatrixClassCoder) {
            processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "MatrixCoder", element);
        }

    }

    private void dealElementsWithUniformVectorClassCoder(
            Set<? extends Element> elementsWithUniformVectorClassCoder, RoundEnvironment roundEnv) {
        for (Element element : elementsWithUniformVectorClassCoder) {
            final String log = String.format("VectorCoder.kind{%s}", element.getKind());
            processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, log
                    , element);

        }
    }
}
