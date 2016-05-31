# application-service-provider
java -classpath application-service-provider.jar com.moseeker.application.server.JobApplicationServer -port 19400 &

# company-service-provider
java -classpath company-service-provider.jar com.moseeker.company.server.CompanyServer -port 19200 &

# dict-service-provider
java -classpath dict-service-provider.jar com.moseeker.dict.server.CityServer  -port 19210 &
java -classpath dict-service-provider.jar com.moseeker.dict.server.CollegeServer  -port 19211 &
java -classpath dict-service-provider.jar com.moseeker.dict.server.DictConstantServer  -port 19212 &

# position-service-provider
java -classpath position-service-provider.jar com.moseeker.position.server.PositionServer -port 19300 &

# profile-service-provider
java -classpath profile-service-provider.jar com.moseeker.profile.server.ProfileWholeProfileServer -port 19100 &
java -classpath profile-service-provider.jar com.moseeker.profile.server.ProfileAttachmentServer -port 19101 &
java -classpath profile-service-provider.jar com.moseeker.profile.server.ProfileAwardsServer -port 19102 &
java -classpath profile-service-provider.jar com.moseeker.profile.server.ProfileBasicServer -port 19103 &
java -classpath profile-service-provider.jar com.moseeker.profile.server.ProfileCredentialsServer -port 19104 &
java -classpath profile-service-provider.jar com.moseeker.profile.server.ProfileCustomizeResumeServer -port 19105 &
java -classpath profile-service-provider.jar com.moseeker.profile.server.ProfileEducationServer -port 19106 &
java -classpath profile-service-provider.jar com.moseeker.profile.server.ProfileImportServer -port 19107 &
java -classpath profile-service-provider.jar com.moseeker.profile.server.ProfileIntentionServer -port 19108 &
java -classpath profile-service-provider.jar com.moseeker.profile.server.ProfileLanguageServer -port 19109 &
java -classpath profile-service-provider.jar com.moseeker.profile.server.ProfileProjectExpServer -port 19110 &
java -classpath profile-service-provider.jar com.moseeker.profile.server.ProfileServer -port 19111 &
java -classpath profile-service-provider.jar com.moseeker.profile.server.ProfileSkillServer -port 19112 &
java -classpath profile-service-provider.jar com.moseeker.profile.server.ProfileWorkExpServer -port 19113 &
java -classpath profile-service-provider.jar com.moseeker.profile.server.ProfileWorksServer -port 19114 &
java -classpath profile-service-provider.jar com.moseeker.profile.server.ProfileCustomizeResumeServer -port 19115 &

# useraccounts-service-provider
java -classpath useraccounts-service-provider.jar com.moseeker.useraccounts.server.UseraccountsServer -port 19000 &
java -classpath useraccounts-service-provider.jar com.moseeker.useraccounts.server.UsersettingsServer -port 19001 &

