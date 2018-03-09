package com.moseeker.position.service.position.jobsdb.pojo;

import com.moseeker.position.service.position.qianxun.Degree;
import com.moseeker.position.service.position.qianxun.WorkType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JobsDBTransferStrategy {
    private static Logger logger= LoggerFactory.getLogger(JobsDBTransferStrategy.class);

    public enum EmploymentType{
        /*
        社招	全职	Full Time
        社招	兼职	Part Time
        社招	合同工	Contract
        社招	实习	Internship
        社招	其他	Freelance

        校招	全职	Full Time
        校招	兼职	Part Time
        校招	合同工	Contract
        校招	实习	Internship
        校招	其他	Freelance*/

        FULLTIME(1,"全职", WorkType.fullTime),
        PARTTIME(2,"兼职",WorkType.partTime),
        CONTRACT(3,"合同工",WorkType.contract),
        INTERNSHIP(4,"实习",WorkType.practice),
        FREELANCE(5,"其他",WorkType.other)
        ;
        /**/

        EmploymentType(int code, String text, WorkType employment_type) {
            this.code = code;
            this.text = text;
            this.employment_type = employment_type;
        }

        public static int moseekerToOther(int employment_type ){
            for(EmploymentType t:values()){
                if(t.employment_type.getValue()==employment_type){
                    return t.code;
                }
            }
            logger.info("no matched WorkMode");
            return 0;
        }

        private int code;
        private String text;
        private WorkType employment_type;
    }
}
