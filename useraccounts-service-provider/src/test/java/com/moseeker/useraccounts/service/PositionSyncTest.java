package com.moseeker.useraccounts.service;

import java.util.ArrayList;

public class PositionSyncTest {
    public static void main(String arg[]){
        ArrayList<String> list=new ArrayList<>();
        /*out:for(String o:old){
            for(String n:now){
                if(n.equals(o)){
                    System.out.println(n);
                    continue out;
                }
            }
            list.add(o);
        }*/
        out:for(String n: now){
            for(String o:old){
                if(n.equals(o)){
                    System.out.println(n+" | "+n);
                    continue out;
                }
            }
            list.add(n);
        }
        System.out.println("=========================");
        list.stream().forEach(t->System.out.println(" | "+t));
    }

    static String old[]={
            "salary_top",
            "salary_bottom",
            "salary_month",
            "count",
            "address",
            "use_company_address",
            "occupation",
            "channel",
            "third_party_account_id",
            "department",
            "feedback_period",
            "salary_discuss",
            "practice_salary",
            "practice_per_week",
            "practice_salary_unit"
    };

    static String now[]={
            "position_id",
            "channels",
            "salary_top",
            "salary_bottom",
            "occupation",
            "salary_month",
            "feedback_period",
            "department_id",
            "department_name",
            "salary_discuss",
            "company_id",
            "company_name",
            "address_id",
            "address_name",
            "count",
            "practice_salary",
            "practice_salary_unit",
            "practice_per_week"
    };
}
