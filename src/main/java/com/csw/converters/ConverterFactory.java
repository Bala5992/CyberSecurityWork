package com.csw.converters;

import com.csw.converters.impl.XMLJSONConverterIImpl;

/**
 * Factory class for creating instances of {@link XMLJSONConverterI}.
 */
public final class ConverterFactory {

    /**
     * You should implement this method having it return your version of
     * {@link com.csw.converters.XMLJSONConverterI}.
     *
     * @return {@link com.csw.converters.XMLJSONConverterI} implementation you created.
     */
    public static final XMLJSONConverterI createXMLJSONConverter() {
       // Todo: Implement this method please.
        return new XMLJSONConverterIImpl();
    }
}
