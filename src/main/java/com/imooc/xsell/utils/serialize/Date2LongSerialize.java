package com.imooc.xsell.utils.serialize;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.util.Date;

/**
 * @author 亚林
 * date 19/10/10,0010 17:32
 */
public class Date2LongSerialize extends JsonSerializer<Date> {
  /**
   * Method that can be called to ask implementation to serialize
   * values of type this serializer handles.
   *
   * @param value       Value to serialize; can <b>not</b> be null.
   * @param gen         Generator used to output resulting Json content
   * @param serializers Provider that can be used to get serializers for
   */
  @Override
  public void serialize(Date value, JsonGenerator gen, SerializerProvider serializers) throws IOException, JsonProcessingException {
    gen.writeNumber(value.getTime() / 1000);
  }
}
