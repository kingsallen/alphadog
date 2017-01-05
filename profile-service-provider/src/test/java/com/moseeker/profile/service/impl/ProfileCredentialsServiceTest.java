/**
 * 
 */
package com.moseeker.profile.service.impl;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.moseeker.profile.constants.ValidationMessage;
import com.moseeker.profile.utils.ProfileValidation;
import com.moseeker.thrift.gen.profile.struct.Credentials;

/**
 * @author jack
 *
 */
public class ProfileCredentialsServiceTest extends ProfileCredentialsService {
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}
	
	@Test
	public void testVerifyCredential() {
		Credentials credentials = new Credentials();
		credentials.setName("");
		ValidationMessage<Credentials> vm = ProfileValidation.verifyCredential(credentials);
		assertEquals(false, vm.isPass());
	}
	
	@Test
	public void testVerifyCredential1() {
		Credentials credentials = new Credentials();
		credentials.setName("test");
		ValidationMessage<Credentials> vm = ProfileValidation.verifyCredential(credentials);
		assertEquals(true, vm.isPass());
	}
}
