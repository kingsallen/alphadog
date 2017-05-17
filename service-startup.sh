#!/usr/bin/env bash

# kill all

ps -ef | grep service-provider | awk '{print $2}' | xargs kill -9


# application-service-provider
java -classpath application-service-provider.jar com.moseeker.application.server.JobApplicationServer -port 19400 &

sleep 10

# company-service-provider
java -classpath company-service-provider.jar CompanyServer -port 19200 &

sleep 10

# dict-service-provider
java -classpath dict-service-provider.jar CityServer  -port 19210 &

sleep 10

java -classpath dict-service-provider.jar com.moseeker.dict.server.CollegeServer  -port 19211 &

sleep 10


java -classpath dict-service-provider.jar com.moseeker.dict.server.DictConstantServer  -port 19212 &

sleep 10


# position-service-provider
java -classpath position-service-provider.jar com.moseeker.position.server.PositionServer -port 19300 &

sleep 10


# profile-service-provider
java -classpath profile-service-provider.jar com.moseeker.profile.server.ProfileWholeProfileServer -port 19100 &


sleep 10


java -classpath profile-service-provider.jar com.moseeker.profile.server.ProfileAttachmentServer -port 19101 &

sleep 10

java -classpath profile-service-provider.jar com.moseeker.profile.server.ProfileAwardsServer -port 19102 &

sleep 10

java -classpath profile-service-provider.jar com.moseeker.profile.server.ProfileBasicServer -port 19103 &

sleep 10

java -classpath profile-service-provider.jar com.moseeker.profile.server.ProfileCredentialsServer -port 19104 &

sleep 10


java -classpath profile-service-provider.jar com.moseeker.profile.server.ProfileEducationServer -port 19106 &

sleep 10

java -classpath profile-service-provider.jar com.moseeker.profile.server.ProfileImportServer -port 19107 &

sleep 10

java -classpath profile-service-provider.jar com.moseeker.profile.server.ProfileIntentionServer -port 19108 &

sleep 10

java -classpath profile-service-provider.jar com.moseeker.profile.server.ProfileLanguageServer -port 19109 &

sleep 10

java -classpath profile-service-provider.jar com.moseeker.profile.server.ProfileProjectExpServer -port 19110 &

sleep 10


java -classpath profile-service-provider.jar com.moseeker.profile.server.ProfileServer -port 19111 &

sleep 10


java -classpath profile-service-provider.jar com.moseeker.profile.server.ProfileSkillServer -port 19112 &

sleep 10

java -classpath profile-service-provider.jar com.moseeker.profile.server.ProfileWorkExpServer -port 19113 &

sleep 10

java -classpath profile-service-provider.jar com.moseeker.profile.server.ProfileWorksServer -port 19114 &

sleep 10

java -classpath profile-service-provider.jar com.moseeker.profile.server.ProfileCustomizeResumeServer -port 19115 &

sleep 10
# useraccounts-service-provider
java -classpath useraccounts-service-provider.jar UseraccountsServer -port 19000 &

sleep 10

java -classpath useraccounts-service-provider.jar com.moseeker.useraccounts.server.UsersettingsServer -port 19001 &

sleep 10

java -classpath useraccounts-service-provider.jar  com.moseeker.useraccounts.server.UserHrAccountServer -port  19003 &

