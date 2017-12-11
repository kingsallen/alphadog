package com.moseeker.servicemanager.web.controller.company;

import com.moseeker.baseorm.util.BeanUtils;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.util.StringUtils;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.servicemanager.common.ParamUtils;
import com.moseeker.servicemanager.common.ResponseLogNotification;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.company.service.TalentpoolServices;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrTeamDO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * Created by zztaiwll on 17/12/7.
 */
@Controller
@CounterIface
public class TalentpoolController {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    TalentpoolServices.Iface service= ServiceManager.SERVICEMANAGER.getService(TalentpoolServices.Iface.class);

    @RequestMapping(value = "/api/talentpool/talents", method = RequestMethod.POST)
    @ResponseBody
    public String addTalent(HttpServletRequest request, HttpServletResponse response) {
        try {
            Map<String, Object> data = ParamUtils.parseRequestParam(request);
            int hrId=(int) data.get("hr_id");
            int companyId=(int) data.get("company_id");
            List<Integer> userIdList=(List<Integer>)data.get("user_ids");
            Response result = service.batchAddTalent(hrId,userIdList,companyId);
            return ResponseLogNotification.success(request, result);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseLogNotification.fail(request, e.getMessage());
        } finally {
            // do nothing
        }
    }

    @RequestMapping(value = "/api/talentpool/talents", method = RequestMethod.DELETE)
    @ResponseBody
    public String cancelTalent(HttpServletRequest request, HttpServletResponse response) {
        try {
            Map<String, Object> data = ParamUtils.parseRequestParam(request);
            int hrId=Integer.parseInt(String.valueOf( data.get("hr_id")));
            int companyId=Integer.parseInt(String.valueOf(data.get("company_id")));
            List<Integer> userIdList=ParamUtils.convertIntList(String.valueOf(data.get("user_ids")));
            Response result = service.batchCancelTalent(hrId,userIdList,companyId);
            return ResponseLogNotification.success(request, result);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseLogNotification.fail(request, e.getMessage());
        } finally {
            // do nothing
        }
    }

    @RequestMapping(value = "/api/talentpool/tag", method = RequestMethod.POST)
    @ResponseBody
    public String addTag(HttpServletRequest request, HttpServletResponse response) {
        try {
            Map<String, Object> data = ParamUtils.parseRequestParam(request);
            int hrId=(int) data.get("hr_id");
            int companyId=(int) data.get("company_id");
            String name=(String)data.get("name");
            Response result = service.hrAddTag(hrId,companyId,name);
            return ResponseLogNotification.success(request, result);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseLogNotification.fail(request, e.getMessage());
        } finally {
            // do nothing
        }
    }

    @RequestMapping(value = "/api/talentpool/tag", method = RequestMethod.DELETE)
    @ResponseBody
    public String delTag(HttpServletRequest request, HttpServletResponse response) {
        try {
            Map<String, Object> data = ParamUtils.parseRequestParam(request);
            int hrId=Integer.parseInt(String.valueOf(data.get("hr_id")));
            int companyId=Integer.parseInt(String.valueOf(data.get("company_id")));
            int tagId=Integer.parseInt(String.valueOf( data.get("tag_id")));
            Response result = service.hrDelTag(hrId,companyId,tagId);
            return ResponseLogNotification.success(request, result);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseLogNotification.fail(request, e.getMessage());
        } finally {
            // do nothing
        }
    }

    @RequestMapping(value = "/api/talentpool/tag", method = RequestMethod.PATCH)
    @ResponseBody
    public String updateTag(HttpServletRequest request, HttpServletResponse response) {
        try {
            Map<String, Object> data = ParamUtils.parseRequestParam(request);
            int hrId=Integer.parseInt(String.valueOf( data.get("hr_id")));
            int companyId=Integer.parseInt(String.valueOf( data.get("company_id")));
            int tagId=Integer.parseInt(String.valueOf( data.get("tag_id")));
            String name=String.valueOf(data.get("name"));
            Response result = service.hrUpdateTag(hrId,companyId,tagId,name);
            return ResponseLogNotification.success(request, result);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseLogNotification.fail(request, e.getMessage());
        } finally {
            // do nothing
        }
    }

    @RequestMapping(value = "/api/talentpool/tag", method = RequestMethod.GET)
    @ResponseBody
    public String getTag(HttpServletRequest request, HttpServletResponse response) {
        try {
            Map<String, Object> data = ParamUtils.parseRequestParam(request);
            int hrId=Integer.parseInt(String.valueOf(data.get("hr_id"))) ;
            int companyId=Integer.parseInt(String.valueOf(data.get("company_id")));
            String page=String.valueOf(data.get("page_number"));
            int pageNum=0;
            if(StringUtils.isNullOrEmpty(page)){
                pageNum=Integer.parseInt(page);
            }
            String size=String.valueOf(data.get("page_size"));
            int pageSize=10;
            if(StringUtils.isNullOrEmpty(size)){
                pageSize=Integer.parseInt(size);

            }
            Response result = service.getHrTag(hrId,companyId,pageNum,pageSize);
            return ResponseLogNotification.success(request, result);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseLogNotification.fail(request, e.getMessage());
        } finally {
            // do nothing
        }
    }
    @RequestMapping(value = "/api/talentpool/usertags", method = RequestMethod.POST)
    @ResponseBody
    public String batchAddTalent(HttpServletRequest request, HttpServletResponse response) {
        try {
            Map<String, Object> data = ParamUtils.parseRequestParam(request);
            int hrId=(int) data.get("hr_id");
            int companyId=(int) data.get("company_id");
            List<Integer> userIdList=(List<Integer>)data.get("user_ids");
            List<Integer> tagIdList=(List<Integer>)data.get("tag_ids");
            Response result = service.batchAddTalentTag(hrId,userIdList,tagIdList,companyId);
            return ResponseLogNotification.success(request, result);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseLogNotification.fail(request, e.getMessage());
        } finally {
            // do nothing
        }
    }

    @RequestMapping(value = "/api/talentpool/usertags/new", method = RequestMethod.POST)
    @ResponseBody
    public String batchNewAddTalent(HttpServletRequest request, HttpServletResponse response) {
        try {
            Map<String, Object> data = ParamUtils.parseRequestParam(request);
            int hrId=(int) data.get("hr_id");
            int companyId=(int) data.get("company_id");
            List<Integer> userIdList=(List<Integer>)data.get("user_ids");
            List<Integer> tagIdList=(List<Integer>)data.get("tag_ids");
            Response result = service.batchNewAddTalentTag(hrId,userIdList,tagIdList,companyId);
            return ResponseLogNotification.success(request, result);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseLogNotification.fail(request, e.getMessage());
        } finally {
            // do nothing
        }
    }
    @RequestMapping(value = "/api/talentpool/usertags", method = RequestMethod.DELETE)
    @ResponseBody
    public String batchDelTalent(HttpServletRequest request, HttpServletResponse response) {
        try {
            Map<String, Object> data = ParamUtils.parseRequestParam(request);
            int hrId=Integer.parseInt(String.valueOf( data.get("hr_id")));
            int companyId=Integer.parseInt(String.valueOf( data.get("company_id")));
            List<Integer> userIdList=ParamUtils.convertIntList(String.valueOf(data.get("user_ids")));
            List<Integer> tagIdList=ParamUtils.convertIntList(String.valueOf(data.get("tag_ids")));
            Response result = service.batchCancleTalentTag(hrId,userIdList,tagIdList,companyId);
            return ResponseLogNotification.success(request, result);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseLogNotification.fail(request, e.getMessage());
        } finally {
            // do nothing
        }
    }

    @RequestMapping(value = "/api/talentpool/publics", method = RequestMethod.POST)
    @ResponseBody
    public String batchPublicTalent(HttpServletRequest request, HttpServletResponse response) {
        try {
            Map<String, Object> data = ParamUtils.parseRequestParam(request);
            int hrId=(int) data.get("hr_id");
            int companyId=(int) data.get("company_id");
            List<Integer> userIdList=(List<Integer>)data.get("user_ids");
            Response result = service.batchAddPublicTalent(hrId,companyId,userIdList);
            return ResponseLogNotification.success(request, result);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseLogNotification.fail(request, e.getMessage());
        } finally {
            // do nothing
        }
    }
    @RequestMapping(value = "/api/talentpool/publics", method = RequestMethod.GET)
    @ResponseBody
    public String getPublicTalent(HttpServletRequest request, HttpServletResponse response) {
        try {
            Map<String, Object> data = ParamUtils.parseRequestParam(request);
            int hrId=Integer.parseInt(String.valueOf(data.get("hr_id")));
            int companyId=Integer.parseInt(String.valueOf( data.get("company_id")));
            String page=String.valueOf(data.get("page_number"));
            int pageNum=0;
            if(StringUtils.isNullOrEmpty(page)){
                pageNum=Integer.parseInt(page);

            }
            String size=String.valueOf(data.get("page_size"));
            int pageSize=10;
            if(StringUtils.isNullOrEmpty(size)){
                pageSize=Integer.parseInt(size);

            }
            Response result = service.getCompanyPulicTalent(hrId,companyId,pageNum,pageSize);
            return ResponseLogNotification.success(request, result);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseLogNotification.fail(request, e.getMessage());
        } finally {
            // do nothing
        }
    }

    @RequestMapping(value = "/api/talentpool/publics", method = RequestMethod.DELETE)
    @ResponseBody
    public String batchCancelPublicTalent(HttpServletRequest request, HttpServletResponse response) {
        try {
            Map<String, Object> data = ParamUtils.parseRequestParam(request);
            int hrId=Integer.parseInt(String.valueOf(data.get("hr_id")));
            int companyId=Integer.parseInt(String.valueOf( data.get("company_id")));
            List<Integer> userIdList=ParamUtils.convertIntList(String.valueOf(data.get("user_ids")));
            Response result = service.batchCancelPublicTalent(hrId,companyId,userIdList);
            return ResponseLogNotification.success(request, result);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseLogNotification.fail(request, e.getMessage());
        } finally {
            // do nothing
        }
    }
    @RequestMapping(value = "/api/talentpool/comment", method = RequestMethod.POST)
    @ResponseBody
    public String addTalentComment(HttpServletRequest request, HttpServletResponse response) {
        try {
            Map<String, Object> data = ParamUtils.parseRequestParam(request);
            int hrId=(int) data.get("hr_id");
            int companyId=(int) data.get("company_id");
            int userId=(int)data.get("user_id");
            String content=(String)data.get("content");
            Response result = service.hrAddComment(hrId,companyId,userId,content);
            return ResponseLogNotification.success(request, result);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseLogNotification.fail(request, e.getMessage());
        } finally {
            // do nothing
        }
    }
    @RequestMapping(value = "/api/talentpool/comment", method = RequestMethod.DELETE)
    @ResponseBody
    public String delTalentComment(HttpServletRequest request, HttpServletResponse response) {
        try {
            Map<String, Object> data = ParamUtils.parseRequestParam(request);
            int hrId=Integer.parseInt(String.valueOf( data.get("hr_id")));
            int companyId=Integer.parseInt(String.valueOf( data.get("company_id")));
            int commentId=Integer.parseInt(String.valueOf(data.get("comment_id")));
            Response result = service.hrDelComment(hrId,companyId,commentId);
            return ResponseLogNotification.success(request, result);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseLogNotification.fail(request, e.getMessage());
        } finally {
            // do nothing
        }
    }
    @RequestMapping(value = "/api/talentpool/comment", method = RequestMethod.GET)
    @ResponseBody
    public String getTalentComment(HttpServletRequest request, HttpServletResponse response) {
        try {
            Map<String, Object> data = ParamUtils.parseRequestParam(request);
            int hrId=Integer.parseInt(String.valueOf(data.get("hr_id")));
            int companyId=Integer.parseInt(String.valueOf(data.get("company_id")));
            int userId=Integer.parseInt(String.valueOf(data.get("user_id")));
            String page=String.valueOf(data.get("page_number"));
            int pageNum=0;
            if(StringUtils.isNullOrEmpty(page)){
                pageNum=Integer.parseInt(page);

            }
            String size=String.valueOf(data.get("page_size"));
            int pageSize=10;
            if(StringUtils.isNullOrEmpty(size)){
                pageSize=Integer.parseInt(size);

            }
            Response result = service.getTalentAllComment(hrId,companyId,userId,pageNum,pageSize);
            return ResponseLogNotification.success(request, result);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseLogNotification.fail(request, e.getMessage());
        } finally {
            // do nothing
        }
    }
    @RequestMapping(value = "/api/talentpool/stats", method = RequestMethod.GET)
    @ResponseBody
    public String getTalentStat(HttpServletRequest request, HttpServletResponse response) {
        try {
            Map<String, Object> data = ParamUtils.parseRequestParam(request);
            int hrId=Integer.parseInt(String.valueOf(data.get("hr_id")));
            int companyId=Integer.parseInt(String.valueOf(data.get("company_id")));
            int type=Integer.parseInt(String.valueOf(data.get("type")));
            Response result = service.getTalentStat(hrId,companyId,type);
            return ResponseLogNotification.success(request, result);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseLogNotification.fail(request, e.getMessage());
        } finally {
            // do nothing
        }
    }

    @RequestMapping(value = "/api/talentpool/user/tag", method = RequestMethod.GET)
    @ResponseBody
    public String getHrUserTag(HttpServletRequest request, HttpServletResponse response) {
        try {
            Map<String, Object> data = ParamUtils.parseRequestParam(request);
            int hrId=Integer.parseInt(String.valueOf(data.get("hr_id")));
            int companyId=Integer.parseInt(String.valueOf(data.get("company_id")));
            int userId=Integer.parseInt(String.valueOf(data.get("user_id")));
            Response result = service.getHrUserTag(hrId,companyId,userId);
            return ResponseLogNotification.success(request, result);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseLogNotification.fail(request, e.getMessage());
        } finally {
            // do nothing
        }
    }

    @RequestMapping(value = "/api/talentpool/user/public", method = RequestMethod.GET)
    @ResponseBody
    public String getCompanyUserPublic(HttpServletRequest request, HttpServletResponse response) {
        try {
            Map<String, Object> data = ParamUtils.parseRequestParam(request);
            int hrId=Integer.parseInt(String.valueOf(data.get("hr_id")));
            int companyId=Integer.parseInt(String.valueOf(data.get("company_id")));
            int userId=Integer.parseInt(String.valueOf(data.get("user_id")));
            Response result = service.getCompanyUserPublic(hrId,companyId,userId);
            return ResponseLogNotification.success(request, result);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseLogNotification.fail(request, e.getMessage());
        } finally {
            // do nothing
        }
    }

    @RequestMapping(value = "/api/talentpool/user/talent", method = RequestMethod.GET)
    @ResponseBody
    public String getUserHrTag(HttpServletRequest request, HttpServletResponse response) {
        try {
            Map<String, Object> data = ParamUtils.parseRequestParam(request);
            int hrId=Integer.parseInt(String.valueOf(data.get("hr_id")));
            int companyId=Integer.parseInt(String.valueOf(data.get("company_id")));
            int userId=Integer.parseInt(String.valueOf(data.get("user_id")));
            Response result = service.getCompanyTalent(hrId,companyId,userId);
            return ResponseLogNotification.success(request, result);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseLogNotification.fail(request, e.getMessage());
        } finally {
            // do nothing
        }
    }

}
