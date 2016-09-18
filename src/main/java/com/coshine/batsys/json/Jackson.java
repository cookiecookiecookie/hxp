package com.coshine.batsys.json;

import java.io.IOException;
import java.io.OutputStream;

import com.fasterxml.aalto.stax.InputFactoryImpl;
import com.fasterxml.aalto.stax.OutputFactoryImpl;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyName;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.introspect.AnnotatedClass;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlAnnotationIntrospector;
import com.fasterxml.jackson.dataformat.xml.XmlFactory;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

/**
 * Created by jia on 2015/7/10.
 */
public final class Jackson {

    public static String writeJson(Object object, final String... ignoreFields) {
        return writeJson(object, null, ignoreFields);
    }

    public static String writeJson(Object object, PropertyNamingStrategy namingStrategy, final String... ignoreFields) {
        ObjectMapper om = new ObjectMapper();
        om.setAnnotationIntrospector(new JacksonAnnotationIntrospector() {
			private static final long serialVersionUID = 3816283136243101215L;
			@Override
            public String[] findPropertiesToIgnore(Annotated ac) {
                return ignoreFields;
            }
        });
        if (namingStrategy != null) {
            om.setPropertyNamingStrategy(namingStrategy);
        }
        try {
            return om.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }
    
	public static void writeJsonToStream(OutputStream out, Object object, final String... ignoreFields) {
		writeJsonToStream(out, object, null, ignoreFields);
	}

	public static void writeJsonToStream(OutputStream out, Object object, PropertyNamingStrategy namingStrategy,
			final String... ignoreFields) {
		ObjectMapper om = new ObjectMapper();
		om.setAnnotationIntrospector(new JacksonAnnotationIntrospector() {
			private static final long serialVersionUID = 3816283136243101215L;
			@Override
			public String[] findPropertiesToIgnore(Annotated ac) {
				return ignoreFields;
			}
		});
		if (namingStrategy != null) {
			om.setPropertyNamingStrategy(namingStrategy);
		}
		try {
			om.writeValue(out, object);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

    public static <T> T readJson(String jsonString, Class<T> clazz) {
        return readJson(jsonString, null, clazz);
    }
    
    public static <T> T readJson(String jsonString, PropertyNamingStrategy namingStrategy, Class<T> clazz) {
        ObjectMapper om = new ObjectMapper();
        if (namingStrategy != null) {
            om.setPropertyNamingStrategy(namingStrategy);
        }
        try {
            return om.readValue(jsonString, clazz);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static <T> T readJson(String jsonString, TypeReference<T> typeRef) {
        return readJson(jsonString, null, typeRef);
    }
    
    public static <T> T readJson(String jsonString, PropertyNamingStrategy namingStrategy, TypeReference<T> typeRef) {
        ObjectMapper om = new ObjectMapper();
        if (namingStrategy != null) {
            om.setPropertyNamingStrategy(namingStrategy);
        }
        try {
            return om.readValue(jsonString, typeRef);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String writeXml(Object object, final String... ignoreFields) {
        return writeXml(object, null, null, null, ignoreFields);
    }

    public static String writeXml(Object object, PropertyNamingStrategy namingStrategy, final String... ignoreFields) {
        return writeXml(object, namingStrategy, null, null, ignoreFields);
    }

    public static String writeXml(Object object, final PropertyName rootName, final String... ignoreFields) {
        return writeXml(object, null, rootName, null, ignoreFields);
    }

    public static String writeXml(Object object, PropertyNamingStrategy namingStrategy,
                                  final PropertyName rootName, final Include includeStrategy, final String... ignoreFields) {
        XmlFactory factory = new XmlFactory(new InputFactoryImpl(), new OutputFactoryImpl());
        XmlMapper xm = new XmlMapper(factory);
        xm.setAnnotationIntrospector(new JacksonXmlAnnotationIntrospector() {
			private static final long serialVersionUID = -979297613435992528L;

			@Override
            public String[] findPropertiesToIgnore(Annotated ac) {
                return ignoreFields;
            }

            @Override
            public PropertyName findRootName(AnnotatedClass ac) {
                return rootName == null ? super.findRootName(ac) : rootName;
            }
        });
        if (namingStrategy != null) {
            xm.setPropertyNamingStrategy(namingStrategy);
        }
        if(includeStrategy != null) {
        	xm.setSerializationInclusion(includeStrategy);
        }
        try {
            return xm.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static <T> T readXml(String xmlString, Class<T> clazz) {
        return readXml(xmlString, null, clazz);
    }

    public static <T> T readXml(String xmlString, PropertyNamingStrategy namingStrategy, Class<T> clazz) {
        XmlFactory factory = new XmlFactory(new InputFactoryImpl(), new OutputFactoryImpl());
        XmlMapper xm = new XmlMapper(factory);
        if (namingStrategy != null) {
            xm.setPropertyNamingStrategy(namingStrategy);
        }
        try {
            return xm.readValue(xmlString, clazz);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static <T> T readXml(String xmlString, TypeReference<T> typeRef) {
        return readXml(xmlString, null, typeRef);
    }
    
    public static <T> T readXml(String xmlString, PropertyNamingStrategy namingStrategy, TypeReference<T> typeRef) {
        XmlFactory factory = new XmlFactory(new InputFactoryImpl(), new OutputFactoryImpl());
        XmlMapper xm = new XmlMapper(factory);
        if (namingStrategy != null) {
            xm.setPropertyNamingStrategy(namingStrategy);
        }
        try {
            return xm.readValue(xmlString, typeRef);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
