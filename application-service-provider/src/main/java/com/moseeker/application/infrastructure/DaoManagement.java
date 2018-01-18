package com.moseeker.application.infrastructure;

import org.jooq.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by jack on 16/01/2018.
 */
@Component
public class DaoManagement {

    private final Configuration configuration;
    private JobApplicationJOOQDao jobApplicationDao;
    private UserHRAccountJOOQDao userHrAccountDao;
    private JobPositionJOOQDao positionJOOQDao;
    private HrOperationJOOQDao hrOperationJOOQDao;

    @Autowired
    public DaoManagement(Configuration configuration) {
        this.configuration = configuration;
        jobApplicationDao = new JobApplicationJOOQDao(configuration);
        userHrAccountDao = new UserHRAccountJOOQDao(configuration);
        positionJOOQDao = new JobPositionJOOQDao(configuration);
        hrOperationJOOQDao = new HrOperationJOOQDao(configuration);
    }

    public JobApplicationJOOQDao getJobApplicationDao() {
        return jobApplicationDao;
    }

    public UserHRAccountJOOQDao getUserHrAccountDao() {
        return userHrAccountDao;
    }

    public JobPositionJOOQDao getPositionJOOQDao() {
        return positionJOOQDao;
    }

    public HrOperationJOOQDao getHrOperationJOOQDao() {
        return hrOperationJOOQDao;
    }
}
