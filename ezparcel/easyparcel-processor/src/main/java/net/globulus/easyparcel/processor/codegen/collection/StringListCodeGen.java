package net.globulus.easyparcel.processor.codegen.collection;

import net.globulus.easyparcel.processor.ParcelableField;
import net.globulus.easyparcel.processor.codegen.FieldCodeGen;

import java.io.IOException;

import javawriter.JavaWriter;

import static net.globulus.easyparcel.processor.util.FrameworkUtil.PARAM_PARCEL;
import static net.globulus.easyparcel.processor.util.FrameworkUtil.PARAM_SOURCE;
import static net.globulus.easyparcel.processor.util.FrameworkUtil.PARAM_TARGET;

public class StringListCodeGen implements FieldCodeGen {

  @Override
  public void generateWriteToParcel(ParcelableField field, JavaWriter jw) throws IOException  {
    jw.emitStatement("%s.writeStringList(%s.%s)", PARAM_PARCEL, PARAM_SOURCE,
        field.getmFieldName());
  }

  @Override
  public void generateReadFromParcel(ParcelableField field, JavaWriter jw) throws IOException {
    jw.emitStatement("%s.%s = %s.createStringArrayList()", PARAM_TARGET, field.getmFieldName(), PARAM_PARCEL);
  }
}