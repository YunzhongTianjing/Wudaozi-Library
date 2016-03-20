package com.wudaozi.coder.uniform;

import com.wudaozi.coder.ComponentType;
import com.wudaozi.coder.Handle;
import com.wudaozi.coder.uniform.generator.AUniformCoder;
import com.wudaozi.coder.uniform.generator.MatrixClassCoder;
import com.wudaozi.coder.uniform.generator.VectorClassCoder;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.VariableElement;

/**
 * Created by yunzhongtianjing on 16/3/20.
 */
public class UniformSubclass {
    private UniformSubclass() {
    }

    /**
     * The commands glUniformMatrix{2|3|4}fv are used to modify a matrix or an array of matrices.
     * The numbers in the command name are interpreted as the dimensionality of the matrix.
     * The number 2 indicates a 2 × 2 matrix (i.e., 4 values), the number 3 indicates a 3 × 3 matrix (i.e., 9 values),
     * and the number 4 indicates a 4 × 4 matrix (i.e., 16 values).
     * Each matrix is assumed to be supplied in column major order.
     * The count argument indicates the number of matrices to be passed.
     * A count of 1 should be used if modifying the value of a single matrix,
     * and a count greater than 1 can be used to modify an array of matrices.
     */
    @Target(ElementType.FIELD)
    @Retention(RetentionPolicy.SOURCE)
    public @interface Matrix {
        //glUniformMatrix{2|3|4}fv

        int dimensionality();

    }

    /**
     * The commands glUniform{1|2|3|4}{f|i} are used to change the value of the uniform variable
     * specified by location using the values passed as arguments.
     * The number specified in the command should match the number of components in the data type of
     * the specified uniform variable (e.g., 1 for float, int, bool; 2 for vec2, ivec2, bvec2, etc.).
     * The suffix f indicates that floating-point values are being passed;
     * the suffix i indicates that integer values are being passed,
     * and this type should also match the data type of the specified uniform variable.
     * The i variants of this function should be used to provide values for uniform variables
     * defined as int, ivec2, ivec3, ivec4, or arrays of these.
     * The f variants should be used to provide values for uniform variables of
     * type float, vec2, vec3, vec4, or arrays of these.
     * Either the i or the f variants may be used to provide values for uniform variables
     * of type bool, bvec2, bvec3, bvec4, or arrays of these.
     * The uniform variable will be set to false if the input value is 0 or 0.0f, and it will be set to true otherwise.
     * <p/>
     * All active uniform variables defined in a program object are initialized to 0
     * when the program object is linked successfully.
     * They retain the values assigned to them by a call to glUniform until the next successful link operation occurs
     * on the program object, when they are once again initialized to 0.
     * <p/>
     * The commands glUniform{1|2|3|4}{f|i}v can be used to modify a single uniform variable or a uniform variable array.
     * These commands pass a count and a pointer to the values to be loaded into a uniform variable
     * or a uniform variable array.
     * A count of 1 should be used if modifying the value of a single uniform variable,
     * and a count of 1 or greater can be used to modify an entire array or part of an array.
     * When loading n elements starting at an arbitrary position m in a uniform variable array,
     * elements m + n - 1 in the array will be replaced with the new values.
     * If m + n - 1 is larger than the size of the uniform variable array,
     * values for all array elements beyond the end of the array will be ignored.
     * The number specified in the name of the command indicates the number of components for each element in value,
     * and it should match the number of components in the data type of the specified uniform variable
     * (e.g., 1 for float, int, bool; 2 for vec2, ivec2, bvec2, etc.).
     * The data type specified in the name of the command must match the data type for the specified uniform variable
     * as described previously for glUniform{1|2|3|4}{f|i}.
     * <p/>
     * For uniform variable arrays, each element of the array is considered to be of the type indicated
     * in the name of the command (e.g., glUniform3f or glUniform3fv can be used to
     * load a uniform variable array of type vec3).
     * The number of elements of the uniform variable array to be modified is specified by count.
     * <p/>
     */
    @Target(ElementType.FIELD)
    @Retention(RetentionPolicy.SOURCE)
    public @interface Vector {

        //glUniform{1|2|3|4}{f|i} for single value,
        // glUniform{1|2|3|4}{f|i}v for single value or an array

        int componentSize();

        ComponentType componentType();

    }

    public static void generate(ProcessingEnvironment processingEnv, Element superclass) {
        final List<? extends Element> innerElements = superclass.getEnclosedElements();
        final List<AUniformCoder> subclassCoder = new ArrayList<>();
        String handleFieldName = null;
        String sizeIfArrayFieldName = null;
        for (Element element : innerElements) {
            final UniformSubclass.Vector vector = element.getAnnotation(UniformSubclass.Vector.class);
            if (null != vector) {
                final String subclassCanonicalName = ((VariableElement) element).getConstantValue().toString();
                subclassCoder.add(new VectorClassCoder(subclassCanonicalName,
                        vector.componentType(), vector.componentSize(),false));
                subclassCoder.add(new VectorClassCoder(subclassCanonicalName+"Array",
                        vector.componentType(), vector.componentSize(),true));
            }
            final UniformSubclass.Matrix matrix = element.getAnnotation(UniformSubclass.Matrix.class);
            if (null != matrix) {
                final String subclassCanonicalName = ((VariableElement) element).getConstantValue().toString();
                subclassCoder.add(new MatrixClassCoder(subclassCanonicalName, matrix.dimensionality()));
            }
            final Handle handle = element.getAnnotation(Handle.class);
            handleFieldName = null == handle ? handleFieldName : element.getSimpleName().toString();
            final SizeIfArray sizeIfArray = element.getAnnotation(SizeIfArray.class);
            sizeIfArrayFieldName = null == sizeIfArray ? sizeIfArrayFieldName : element.getSimpleName().toString();
        }

        for (AUniformCoder coder : subclassCoder)
            coder.initialize(handleFieldName, sizeIfArrayFieldName).generate(processingEnv);
    }

}
