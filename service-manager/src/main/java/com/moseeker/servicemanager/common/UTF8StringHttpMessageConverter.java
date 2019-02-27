package com.moseeker.servicemanager.common;

import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class UTF8StringHttpMessageConverter extends AbstractHttpMessageConverter<String> {

	public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");
	
	private final List<Charset> availableCharsets;

    private boolean writeAcceptCharset = true;
    
    public UTF8StringHttpMessageConverter() {
        super(new MediaType("application", "json", DEFAULT_CHARSET), MediaType.ALL);
        this.availableCharsets = new ArrayList<Charset>(Charset
                .availableCharsets().values());
    }
    
	@Override
	protected boolean supports(Class<?> clazz) {
		return String.class.equals(clazz);
	}

	@Override
	protected String readInternal(Class<? extends String> clazz, HttpInputMessage inputMessage)
			throws IOException, HttpMessageNotReadableException {
		Charset charset = getContentTypeCharset(inputMessage.getHeaders()
                .getContentType());
        return FileCopyUtils.copyToString(new InputStreamReader(inputMessage
                .getBody(), charset));
	}

	@Override
	protected void writeInternal(String s, HttpOutputMessage outputMessage)
			throws IOException, HttpMessageNotWritableException {
		if (writeAcceptCharset) {
            outputMessage.getHeaders().setAcceptCharset(getAcceptedCharsets());
        }
        Charset charset = getContentTypeCharset(outputMessage.getHeaders()
                .getContentType());
        FileCopyUtils.copy(s, new OutputStreamWriter(outputMessage.getBody(),
                charset));
	}
	
	protected List<Charset> getAcceptedCharsets() {
        return this.availableCharsets;
    }

	private Charset getContentTypeCharset(MediaType contentType) {
        if (contentType != null && contentType.getCharset() != null) {
            return contentType.getCharset();
        } else {
            return DEFAULT_CHARSET;
        }
    }
}
