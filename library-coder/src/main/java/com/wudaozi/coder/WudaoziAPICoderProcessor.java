package com.wudaozi.coder;

import com.wudaozi.coder.uniform.UniformMatrixClassCoder;
import com.wudaozi.coder.uniform.UniformVectorClassCoder;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
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
        for (Element element : elementsWithUniformVectorClassCoder) {
            if (!checkElementLegality(element))
                return true;
            dealElementsWithUniformVectorClassCoder(element, roundEnv);
        }

        final Set<? extends Element> elementsWithUniformMatrixClassCoder = roundEnv.
                getElementsAnnotatedWith(UniformMatrixClassCoder.class);
        for (Element element : elementsWithUniformMatrixClassCoder) {
            if (!checkElementLegality(element))
                return true;
            dealElementWithUniformMatrixClassCoder(element, roundEnv);
        }
        return true;
    }

    private boolean dealElementWithUniformMatrixClassCoder(
            Element element, RoundEnvironment roundEnv) {
        //processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "MatrixCoder", element);
        return true;
    }

    private boolean dealElementsWithUniformVectorClassCoder(
            Element element, RoundEnvironment roundEnv) {
        final String log = String.format("VectorCoder.kind{%s}.name{%s}.outerElement{%s}",
                element.getKind(),
                element.getSimpleName(),
                element.getEnclosingElement()
        );
        processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, log, element);


        return true;
    }

    private boolean checkElementLegality(Element element) {
        if (element.getKind() != ElementKind.ENUM_CONSTANT) {
            processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "You must annotate a constant of enumeration!",
                    element);
            return false;
        }
        return true;
    }
}
