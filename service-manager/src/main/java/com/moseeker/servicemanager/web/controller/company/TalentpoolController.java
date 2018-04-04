package com.moseeker.servicemanager.web.controller.company;

import com.moseeker.baseorm.db.talentpooldb.tables.pojos.TalentpoolCompanyTag;
import com.moseeker.baseorm.util.BeanUtils;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.util.StringUtils;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.servicemanager.common.ParamUtils;
import com.moseeker.servicemanager.common.ResponseLogNotification;
import com.moseeker.servicemanager.web.controller.util.Params;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.company.service.TalentpoolServices;
import com.moseeker.thrift.gen.company.struct.TalentpoolCompanyTagDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrTeamDO;
import java.util.ArrayList;
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
            if(StringUtils.isEmptyList(userIdList)){
                return  ResponseLogNotification.fail(request,"userId不能为空");
            }
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
            if(StringUtils.isEmptyList(userIdList)){
                return  ResponseLogNotification.fail(request,"userId不能为空");
            }
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
//            if(StringUtils.isEmptyList(tagIdList)){
//                return  ResponseLogNotification.fail(request,"标签不能为空");
//            }
            if(StringUtils.isEmptyList(userIdList)){
                return  ResponseLogNotification.fail(request,"userId不能为空");
            }
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
//            if(StringUtils.isEmptyList(tagIdList)){
//                return  ResponseLogNotification.fail(request,"标签不能为空");
//            }
            if(StringUtils.isEmptyList(userIdList)){
                return  ResponseLogNotification.fail(request,"userId不能为空");
            }
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
//            if(StringUtils.isEmptyList(tagIdList)){
//                return  ResponseLogNotification.fail(request,"标签不能为空");
//            }
            if(StringUtils.isEmptyList(userIdList)){
                return  ResponseLogNotification.fail(request,"userId不能为空");
            }
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
            if(StringUtils.isEmptyList(userIdList)){
                return  ResponseLogNotification.fail(request,"userId不能为空");
            }
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
            if(StringUtils.isEmptyList(userIdList)){
                return  ResponseLogNotification.fail(request,"userId不能为空");
            }
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
    @RequestMapping(value = "/api/talentpool/user/origin", method = RequestMethod.GET)
    @ResponseBody
    public String getUserOrigin(HttpServletRequest request, HttpServletResponse response) {
        try {
            Map<String, Object> data = ParamUtils.parseRequestParam(request);
            int hrId=Integer.parseInt(String.valueOf(data.get("hr_id")));
            int companyId=Integer.parseInt(String.valueOf(data.get("company_id")));
            int userId=Integer.parseInt(String.valueOf(data.get("user_id")));
            Response result = service.getUserOrigin(hrId,companyId,userId);
            return ResponseLogNotification.success(request, result);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseLogNotification.fail(request, e.getMessage());
        } finally {
            // do nothing
        }
    }
    @RequestMapping(value = "/api/talentpool/talentpublic", method = RequestMethod.POST)
    @ResponseBody
    public String getTalentPublicByUserId(HttpServletRequest request, HttpServletResponse response) {
        try {
            Map<String, Object> data = ParamUtils.parseRequestParam(request);
            int hrId=Integer.parseInt(String.valueOf(data.get("hr_id")));
            int companyId=Integer.parseInt(String.valueOf(data.get("company_id")));
            List<Integer> userIdList=(List<Integer>)data.get("user_ids");;
            Response result = service.getTalentAndPublicHr(hrId,companyId,userIdList);
            return ResponseLogNotification.success(request, result);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseLogNotification.fail(request, e.getMessage());
        } finally {
            // do nothing
        }
    }
    /*
    添加人才库开启申请记录
    */
    @RequestMapping(value = "/api/talentpool/open_request", method = RequestMethod.POST)
    @ResponseBody
    public String upsertTalentpoolApp(HttpServletRequest request) throws Exception {
        try {
            Params<String, Object> params = ParamUtils.parseRequestParam(request);
            String hrId=String.valueOf(params.get("hr_id"));
            String companyId=String.valueOf(params.get("company_id"));
            String type=String.valueOf(params.get("type"));
            if(StringUtils.isNullOrEmpty(hrId)||"0".equals(hrId)){
                return ResponseLogNotification.fail(request,"hr_id不可以为空或者为0");
            }
            if(StringUtils.isNullOrEmpty(companyId)||"0".equals(hrId)){
                return ResponseLogNotification.fail(request,"company_id不可以为空或者为0");
            }
            if(StringUtils.isNullOrEmpty(type)){
                type="0";
            }
            Response result = service.upsertTalentPoolApp(Integer.parseInt(hrId),Integer.parseInt(companyId),Integer.parseInt(type));
            return ResponseLogNotification.success(request, result);
        }catch(Exception e){
            logger.info(e.getMessage(),e);
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }

    /*
    获取简历筛选项或者企业标签的曾任职务或者曾任公司
    */
    @RequestMapping(value = "/api/talentpool/past", method = RequestMethod.POST)
    @ResponseBody
    public String getPast(HttpServletRequest request) throws Exception {
        try {
            Params<String, Object> params = ParamUtils.parseRequestParam(request);
            String type=String.valueOf(params.get("type"));
            String companyId=String.valueOf(params.get("company_id"));
            String flag=String.valueOf(params.get("flag"));
            if(StringUtils.isNullOrEmpty(companyId)){
                ResponseLogNotification.fail(request,"company_id不可以为空或者为0");
            }
            Response result = service.upsertTalentPoolApp(Integer.parseInt(companyId),Integer.parseInt(type),Integer.parseInt(flag));
            return ResponseLogNotification.success(request, result);
        }catch(Exception e){
            logger.info(e.getMessage(),e);
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }

    /*
    获取企业标签列表
   */
    @RequestMapping(value = "/api/talent/companytag/list", method = RequestMethod.GET)
    @ResponseBody
    public String getCompanyTagList(HttpServletRequest request) throws Exception {
        try {
            Params<String, Object> params = ParamUtils.parseRequestParam(request);
            String hrId=String.valueOf(params.get("hr_id"));
            String companyId=String.valueOf(params.get("company_id"));
            int page_number = params.getInt("page_number", 1);
            int page_size = params.getInt("page_size",0);
            if(StringUtils.isNullOrEmpty(hrId)||"0".equals(hrId)){
                return ResponseLogNotification.fail(request,"hr_id不可以为空或者为0");
            }
            if(StringUtils.isNullOrEmpty(companyId)||"0".equals(hrId)){
                return ResponseLogNotification.fail(request,"company_id不可以为空或者为0");
            }
            Response result = service.getCompanyTagList(Integer.parseInt(hrId),Integer.parseInt(companyId), page_number, page_size);
            return ResponseLogNotification.success(request, result);
        }catch(Exception e){
            logger.info(e.getMessage(),e);
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }

    /*
    获取企业标签列表
   */
    @RequestMapping(value = "/api/talentpool/companytag", method = RequestMethod.DELETE)
    @ResponseBody
    public String deleteCompanyTag(HttpServletRequest request) throws Exception {
        try {
            Params<String, Object> params = ParamUtils.parseRequestParam(request);
            String hrId=String.valueOf(params.get("hr_id"));
            String companyId=String.valueOf(params.get("company_id"));

            if(StringUtils.isNullOrEmpty(hrId)||"0".equals(hrId)){
                return ResponseLogNotification.fail(request,"hr_id不可以为空或者为0");
            }
            if(StringUtils.isNullOrEmpty(companyId)||"0".equals(hrId)){
                return ResponseLogNotification.fail(request,"company_id不可以为空或者为0");
            }
            if(params.get("company_tags") ==null ){
                return ResponseLogNotification.fail(request,"company_tags不可以为空");
            }
            List<Integer>  company_tags = (List<Integer>)params.get("company_tags");
            if(company_tags.size()<1){
                return ResponseLogNotification.fail(request,"company_tags长度不可以为0");
            }
            Response result = service.deleteCompanyTagByIds(Integer.parseInt(hrId),Integer.parseInt(companyId),company_tags);
            return ResponseLogNotification.success(request, result);
        }catch(Exception e){
            logger.info(e.getMessage(),e);
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }

    /*
   获取企业标签信息
  */
    @RequestMapping(value = "/api/talentpool/companytag", method = RequestMethod.GET)
    @ResponseBody
    public String getCompanyTag(HttpServletRequest request) throws Exception {
        try {
            Params<String, Object> params = ParamUtils.parseRequestParam(request);
            String hrId=String.valueOf(params.get("hr_id"));
            String companyId=String.valueOf(params.get("company_id"));
            String company_tag_id = String.valueOf(params.get("company_tag_id"));
            if(StringUtils.isNullOrEmpty(hrId)||"0".equals(hrId)){
                return ResponseLogNotification.fail(request,"hr_id不可以为空或者为0");
            }
            if(StringUtils.isNullOrEmpty(companyId)||"0".equals(hrId)){
                return ResponseLogNotification.fail(request,"company_id不可以为空或者为0");
            }
            if(StringUtils.isNullOrEmpty(company_tag_id)||"0".equals(company_tag_id)){
                return ResponseLogNotification.fail(request,"company_tag_id不可以为空或者为0");
            }

            Response result = service.getCompanyIdInfo(Integer.parseInt(hrId),Integer.parseInt(companyId),Integer.parseInt(company_tag_id));
            return ResponseLogNotification.success(request, result);
        }catch(Exception e){
            logger.info(e.getMessage(),e);
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }

    /*
    保存企业标签信息
    */
    @RequestMapping(value = "/api/talentpool/companytag", method = RequestMethod.POST)
    @ResponseBody
    public String handerCompanyTag(HttpServletRequest request) throws Exception {
        try {
            Params<String, Object> data = ParamUtils.parseRequestParam(request);
            String hrId=String.valueOf(data.get("hr_id"));
            if(StringUtils.isNullOrEmpty(hrId)||"0".equals(hrId)){
                return ResponseLogNotification.fail(request,"hr_id不可以为空或者为0");
            }
            TalentpoolCompanyTagDO companyTagDO = ParamUtils.initModelForm(data, TalentpoolCompanyTagDO.class);
            if(companyTagDO.getCompany_id()<=0){
                return ResponseLogNotification.fail(request,"company_id不可以为空或者为0");
            }
            Response result = service.addCompanyTag(companyTagDO, Integer.parseInt(hrId));
            return ResponseLogNotification.success(request, result);
        }catch(Exception e){
            logger.error(e.getMessage(),e);
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }
    /*
    修改企业标签信息
    */
    @RequestMapping(value = "/api/talentpool/companytag", method = RequestMethod.PUT)
    @ResponseBody
    public String updateCompanyTag(HttpServletRequest request) throws Exception {
        try {
            Params<String, Object> data = ParamUtils.parseRequestParam(request);
            String hrId=String.valueOf(data.get("hr_id"));
            if(StringUtils.isNullOrEmpty(hrId)||"0".equals(hrId)){
                return ResponseLogNotification.fail(request,"hr_id不可以为空或者为0");
            }
            TalentpoolCompanyTagDO companyTagDO = ParamUtils.initModelForm(data, TalentpoolCompanyTagDO.class);
            if(companyTagDO.getCompany_id()<=0){
                return ResponseLogNotification.fail(request,"company_id不可以为空或者为0");
            }
            if(companyTagDO.getId()<=0){
                return ResponseLogNotification.fail(request,"id不可以为空或者为0");
            }
            Response result = service.updateCompanyTag(companyTagDO, Integer.parseInt(hrId));
            return ResponseLogNotification.success(request, result);
        }catch(Exception e){
            logger.error(e.getMessage(),e);
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }
}
