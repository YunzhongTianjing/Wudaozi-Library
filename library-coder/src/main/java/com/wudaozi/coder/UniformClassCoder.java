package com.wudaozi.coder;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;

@SupportedAnnotationTypes("com.wudaozi.coder.AAAAA")
@SupportedSourceVersion(SourceVersion.RELEASE_7)
public class UniformClassCoder extends AbstractProcessor {
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "heheheXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
//        final File file = new File("D:\\aaa.txt");
//        try {
//            file.createNewFile();
//            final FileWriter writer = new FileWriter(file);
//            writer.write("xxx");
//            writer.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        return false;
    }
}
