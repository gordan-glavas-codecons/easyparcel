package net.globulus.easyparcel.processor;

import net.globulus.easyparcel.processor.codegen.FieldCodeGen;
import net.globulus.easyparcel.processor.codegen.SupportedTypes;
import net.globulus.easyparcel.processor.util.CodeGenInfo;
import net.globulus.easyparcel.processor.util.ProcessorLog;

import java.util.List;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

/**
 * Created by gordanglavas on 30/09/16.
 */
public class ParcelableField {

	private String mFieldName;
	private String type;
	private Element element;
	private FieldCodeGen codeGenerator;
	private TypeMirror genericsTypeArgument;

	public ParcelableField(VariableElement element, Elements elementUtils, Types typeUtils) {
		this.element = element;
		mFieldName = element.getSimpleName().toString();
		type = element.asType().toString();

		CodeGenInfo res = SupportedTypes.getCodeGenInfo(element, elementUtils, typeUtils);
			codeGenerator = res.getCodeGenerator();
			genericsTypeArgument = res.getGenericsType();

			// Check if type is supported
			if (codeGenerator == null) {
				ProcessorLog.error(element,
						"Unsupported type %s for field %s.",
						element.asType().toString(), element.getSimpleName());
			}
	}

	/**
	 * Checks if a class is public
	 */
	private boolean isPublicClass(DeclaredType type) {
		Element element = type.asElement();

		return element.getModifiers().contains(javax.lang.model.element.Modifier.PUBLIC);
	}

	/**
	 * Checks if an public empty constructor is available
	 */
	private boolean hasPublicEmptyConstructor(DeclaredType type) {
		Element element = type.asElement();

		List<? extends Element> containing = element.getEnclosedElements();

		for (Element e : containing) {
			if (e.getKind() == ElementKind.CONSTRUCTOR) {
				ExecutableElement c = (ExecutableElement) e;

				if ((c.getParameters() == null || c.getParameters().isEmpty()) && c.getModifiers()
						.contains(javax.lang.model.element.Modifier.PUBLIC)) {
					return true;
				}
			}
		}

		return false;
	}

	public Element getElement() {
		return element;
	}

	public String getFieldName() {
		return mFieldName;
	}

	public String getType() {
		return type;
	}

	public FieldCodeGen getCodeGenerator() {
		return codeGenerator;
	}

	public TypeMirror getGenericsTypeArgument() {
		return genericsTypeArgument;
	}
}
