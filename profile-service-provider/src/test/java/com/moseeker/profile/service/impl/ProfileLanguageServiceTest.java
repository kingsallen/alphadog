package com.moseeker.profile.service.impl;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.moseeker.profile.constants.ValidationMessage;
import com.moseeker.thrift.gen.profile.struct.Language;

public class ProfileLanguageServiceTest {
	
	ProfileLanguageService languageService = new ProfileLanguageService();

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testVerifyLanguage() {
		Language language = new Language();
		language.setName("test");
		ValidationMessage<Language> vm = languageService.verifyLanguage(language);
		assertEquals(true,  vm.isPass());
	}
	
	@Test
	public void testVerifyLanguage1() {
		Language language = new Language();
		ValidationMessage<Language> vm = languageService.verifyLanguage(language);
		assertEquals(false,  vm.isPass());
	}
}
