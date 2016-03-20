package com.wudaozi.coder;

import com.wudaozi.coder.uniform.UniformSubclass;
import com.wudaozi.coder.uniform.UniformSuperclass;

import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;

@SupportedAnnotationTypes({
        "com.wudaozi.coder.uniform.UniformSuperclass"})
@SupportedSourceVersion(SourceVersion.RELEASE_7)
public class WudaoziAPICoderProcessor extends AbstractProcessor {
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        final Set<? extends Element> elementsWithUniformSuperclass = roundEnv.getElementsAnnotatedWith(
                UniformSuperclass.class);
        if (null == elementsWithUniformSuperclass || 0 == elementsWithUniformSuperclass.size())
            return true;
        final Element elementWithUniformSuperclass = elementsWithUniformSuperclass.iterator().next();
        UniformSubclass.generate(processingEnv, elementWithUniformSuperclass);
        return true;
    }

}
