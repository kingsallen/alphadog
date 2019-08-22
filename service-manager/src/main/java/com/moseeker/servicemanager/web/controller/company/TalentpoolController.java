package com.moseeker.servicemanager.web.controller.company;

import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.util.StringUtils;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.servicemanager.common.ParamUtils;
import com.moseeker.servicemanager.common.ResponseLogNotification;
import com.moseeker.servicemanager.web.controller.util.Params;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.company.service.TalentpoolServices;
import com.moseeker.thrift.gen.company.struct.ActionForm;
import com.moseeker.thrift.gen.company.struct.TalentpoolCompanyTagDO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by zztaiwll on 17/12/7.
 */
@Controller
@CounterIface
public class TalentpoolController {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    TalentpoolServices.Iface service= ServiceManager.SERVICE_MANAGER.getService(TalentpoolServices.Iface.class);

    @RequestMapping(value = "/api/talentpool/talents", method = RequestMethod.POST)
    @ResponseBody
    public String addTalent(HttpServletRequest request, HttpServletResponse response) {
        try {
            Map<String, Object> data = ParamUtils.parseRequestParam(request);
            int hrId=(int) data.get("hr_id");
            int companyId=(int) data.get("company_id");
            Integer flag=(Integer) data.get("flag");
            int isGdpr= (int) data.getOrDefault("is_gdpr",0);
            if(flag==null||flag==0){
                List<Integer> userIdList=(List<Integer>)data.get("user_ids");
                if(StringUtils.isEmptyList(userIdList)){
                    return  ResponseLogNotification.fail(request,"userId不能为空");
                }
                Response result = service.batchAddTalent(hrId,userIdList,companyId,isGdpr);
                return ResponseLogNotification.success(request, result);
            }else{
                Map<String,String> params=new HashMap<>();
                if(data==null||data.isEmpty()){
                    return ResponseLogNotification.fail(request, "参数不能为空");
                }
                for(String key:data.keySet()){
                    params.put(key,String.valueOf(data.get(key)));
                }
                service.addAllTalent(hrId,params,companyId,isGdpr);
                Response res= ResponseUtils.success("");
                return ResponseLogNotification.success(request, res);
            }

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
            String flag=(String) data.get("flag");
            int isGdpr= Integer.parseInt(String.valueOf(data.getOrDefault("is_gdpr","0")));
            if(StringUtils.isNullOrEmpty(flag)||Integer.parseInt(flag)==0){
                List<Integer> userIdList = ParamUtils.convertIntList(String.valueOf(data.get("user_ids")));
                if (StringUtils.isEmptyList(userIdList)) {
                    return ResponseLogNotification.fail(request, "userId不能为空");
                }
                Response result = service.batchCancelTalent(hrId, userIdList, companyId,isGdpr);
                return ResponseLogNotification.success(request, result);
            }else{
                Map<String,String> params=new HashMap<>();
                if(data==null||data.isEmpty()){
                    return ResponseLogNotification.fail(request, "参数不能为空");
                }
                for(String key:data.keySet()){
                    params.put(key,String.valueOf(data.get(key)));
                }
                service.cancleAllTalent(hrId,params,companyId,isGdpr);
                Response res= ResponseUtils.success("");
                return ResponseLogNotification.success(request, res);
            }
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
            String page=(String)data.get("page_number");
            int pageNum=0;
            if(StringUtils.isNotNullOrEmpty(page)){
                pageNum=Integer.parseInt(page);
            }
            String size=(String)data.get("page_size");
            int pageSize=10;
            if(StringUtils.isNotNullOrEmpty(size)){
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
            List<Integer> tagIdList=(List<Integer>)data.get("tag_ids");
            Integer flag=(Integer) data.get("flag");
            if(flag==null||flag==0){
                List<Integer> userIdList=(List<Integer>)data.get("user_ids");
                if(StringUtils.isEmptyList(userIdList)){
                    return  ResponseLogNotification.fail(request,"userId不能为空");
                }
                Response result = service.batchNewAddTalentTag(hrId,userIdList,tagIdList,companyId);
                return ResponseLogNotification.success(request, result);
            }else{
                Map<String,String> params=new HashMap<>();
                if(data==null||data.isEmpty()){
                    return ResponseLogNotification.fail(request, "参数不能为空");
                }
                for(String key:data.keySet()){
                    params.put(key,String.valueOf(data.get(key)));
                }
                service.addAllTalentTag(params,tagIdList,companyId,hrId);
                Response res= ResponseUtils.success("");
                return ResponseLogNotification.success(request, res);
            }

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
            Integer flag=(Integer) data.get("flag");
            int isGdpr= (int) data.getOrDefault("is_gdpr",0);
            if(flag==null||flag==0){
                List<Integer> userIdList = (List<Integer>) data.get("user_ids");
                if (StringUtils.isEmptyList(userIdList)) {
                    return ResponseLogNotification.fail(request, "userId不能为空");
                }
                Response result = service.batchAddPublicTalent(hrId, companyId, userIdList,isGdpr);
                return ResponseLogNotification.success(request, result);
            }else{
                Map<String,String> params=new HashMap<>();
                if(data==null||data.isEmpty()){
                    return ResponseLogNotification.fail(request, "参数不能为空");
                }
                for(String key:data.keySet()){
                    params.put(key,String.valueOf(data.get(key)));
                }
                service.addAllTalentPublic(hrId,params,companyId,isGdpr);
                Response res= ResponseUtils.success("");
                return ResponseLogNotification.success(request, res);
            }
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
            String page=(String)data.get("page_number");
            int pageNum=0;
            if(StringUtils.isNotNullOrEmpty(page)){
                pageNum=Integer.parseInt(page);

            }
            String size=(String)data.get("page_size");
            int pageSize=10;
            if(StringUtils.isNotNullOrEmpty(size)){
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
            String flag=(String) data.get("flag");
            if(StringUtils.isNullOrEmpty(flag)||Integer.parseInt(flag)==0){
                List<Integer> userIdList = ParamUtils.convertIntList(String.valueOf(data.get("user_ids")));
                if (StringUtils.isEmptyList(userIdList)) {
                    return ResponseLogNotification.fail(request, "userId不能为空");
                }
                Response result = service.batchCancelPublicTalent(hrId, companyId, userIdList);
                return ResponseLogNotification.success(request, result);
            }else{
                Map<String,String> params=new HashMap<>();
                if(data==null||data.isEmpty()){
                    return ResponseLogNotification.fail(request, "参数不能为空");
                }
                for(String key:data.keySet()){
                    params.put(key,String.valueOf(data.get(key)));
                }
                service.addAllTalentPrivate(hrId,params,companyId);
                Response res= ResponseUtils.success("");
                return ResponseLogNotification.success(request, res);
            }
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
            int pageNum=0;
            if(data.get("page_number") !=null) {
                String page = String.valueOf(data.get("page_number"));
                if(StringUtils.isNotNullOrEmpty(page)){
                    pageNum=Integer.parseInt(page);

                }
            }
            int pageSize=10;
            if(data.get("page_size") !=null) {
                String size = String.valueOf(data.get("page_size"));
                if(StringUtils.isNotNullOrEmpty(size)){
                    pageSize=Integer.parseInt(size);
                }
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
    @RequestMapping(value = "/api/talentpool/past", method = RequestMethod.GET)
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
            Response result = service.getPositionOrCompanyPast(Integer.parseInt(companyId),Integer.parseInt(type),Integer.parseInt(flag));
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
    public String addPast(HttpServletRequest request) throws Exception {
        try {
            Params<String, Object> params = ParamUtils.parseRequestParam(request);
            String type=String.valueOf(params.get("type"));
            String companyId=String.valueOf(params.get("company_id"));
            String flag=String.valueOf(params.get("flag"));
            String name= (String) params.get("name");
            if(StringUtils.isNullOrEmpty(companyId)){
                ResponseLogNotification.fail(request,"company_id不可以为空或者为0");
            }
            if(StringUtils.isNullOrEmpty(name)){
                ResponseLogNotification.fail(request,"曾任职位的名称或者公司不能为空");
            }
            Response result = service.addPositionOrCompanyPast(Integer.parseInt(companyId),Integer.parseInt(type),Integer.parseInt(flag),name);
            return ResponseLogNotification.success(request, result);
        }catch(Exception e){
            logger.info(e.getMessage(),e);
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }


    /*
       获取人才标签列表
    */
    @RequestMapping(value = "/api/talentpool/talenttag", method = RequestMethod.GET)
    @ResponseBody
    public String getTalentTag(HttpServletRequest request) throws Exception {
        try {
            Params<String, Object> params = ParamUtils.parseRequestParam(request);
            String hrId=String.valueOf(params.get("hr_id"));
            String companyId=String.valueOf(params.get("company_id"));
            int page_number = params.getInt("page_number", 1);
            int page_size = params.getInt("page_size",0);
            if(StringUtils.isNullOrEmpty(hrId)||"0".equals(hrId)){
                ResponseLogNotification.fail(request,"hr_id不可以为空或者为0");
            }
            if(StringUtils.isNullOrEmpty(companyId)||"0".equals(hrId)){
                ResponseLogNotification.fail(request,"company_id不可以为空或者为0");
            }
            Response result = service.getTalentTagList(Integer.parseInt(hrId),Integer.parseInt(companyId), page_number, page_size);
            return ResponseLogNotification.success(request, result);
        }catch(Exception e){
            logger.info(e.getMessage(),e);
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }



    /*
       获取企业筛选规则列表
      */
    @RequestMapping(value = "/api/talentpool/profilefilter/list", method = RequestMethod.GET)
    @ResponseBody
    public String getProfileFilterList(HttpServletRequest request) throws Exception {
        try {
            Params<String, Object> params = ParamUtils.parseRequestParam(request);
            String hrId=String.valueOf(params.get("hr_id"));
            String companyId=String.valueOf(params.get("company_id"));
            int page_number = params.getInt("page_number", 1);
            int page_size = params.getInt("page_size",10);
            if(params.get("hr_id") ==null || StringUtils.isNullOrEmpty(hrId)||"0".equals(hrId)){
                return ResponseLogNotification.fail(request,"hr_id不可以为空或者为0");
            }
            if(params.get("company_id") == null || StringUtils.isNullOrEmpty(companyId)||"0".equals(hrId)){
                return ResponseLogNotification.fail(request,"company_id不可以为空或者为0");
            }
            Response result = service.getProfileFilterList(Integer.parseInt(hrId),Integer.parseInt(companyId), page_number, page_size);
            return ResponseLogNotification.success(request, result);
        }catch(Exception e){
            logger.info(e.getMessage(),e);
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }

    /*
    删除简历筛选列表
   */
    @RequestMapping(value = "/api/talentpool/profilefilter", method = RequestMethod.DELETE)
    @ResponseBody
    public String deleteProfileFilterList(HttpServletRequest request) throws Exception {
        try {
            Params<String, Object> params = ParamUtils.parseRequestParam(request);
            int hrId = params.getInt("hr_id", 0);
            int companyId=params.getInt("company_id",0);
            if(hrId < 1){
                ResponseLogNotification.fail(request,"hr_id不可以为空或者为0");
            }
            if(companyId < 1){
                ResponseLogNotification.fail(request,"company_id不可以为空或者为0");
            }
            if(params.get("filter_ids") ==null ){
                return ResponseLogNotification.fail(request,"filter_ids不可以为空");
            }
            String  filter_ids = (String)params.get("filter_ids");
            List<Integer> filter_idList = ParamUtils.convertIntList(filter_ids);
            if(filter_idList.size()<1){
                return ResponseLogNotification.fail(request,"filter_ids长度不可以为0");
            }
            Response result = service.handerProfileFilterByIds(hrId, companyId, 0, filter_idList);
            return ResponseLogNotification.success(request, result);
        }catch(Exception e){
            logger.info(e.getMessage(),e);
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }
    /*
       将筛选项置位不可用
    */
    @RequestMapping(value = "/api/talentpool/profilefilter", method = RequestMethod.PATCH)
    @ResponseBody
    public String handerProfileFilterListClose(HttpServletRequest request) throws Exception {
        try {
            Params<String, Object> params = ParamUtils.parseRequestParam(request);
            int hrId = params.getInt("hr_id", 0);
            int companyId=params.getInt("company_id",0);
            if(hrId < 1){
                ResponseLogNotification.fail(request,"hr_id不可以为空或者为0");
            }
            if(companyId < 1){
                ResponseLogNotification.fail(request,"company_id不可以为空或者为0");
            }

            if(params.get("filter_ids") ==null ){
                return ResponseLogNotification.fail(request,"filter_ids不可以为空");
            }
            List<Integer> filter_idList = (List<Integer>)params.get("filter_ids");
            if(filter_idList.size()<1){
                return ResponseLogNotification.fail(request,"filter_ids长度不可以为0");
            }
            int disable = params.getInt("disable");
            Response result = service.handerProfileFilterByIds(hrId, companyId , disable, filter_idList);
            return ResponseLogNotification.success(request, result);
        }catch(Exception e){
            logger.info(e.getMessage(),e);
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }

    /*
   获取企业标签信息
  */
    @RequestMapping(value = "/api/talentpool/profilefilter", method = RequestMethod.GET)
    @ResponseBody
    public String getProfileFilterInfo(HttpServletRequest request) throws Exception {
        try {
            Params<String, Object> params = ParamUtils.parseRequestParam(request);
            int hrId=params.getInt("hr_id", 0);
            int companyId=params.getInt("company_id",0);
            int filter_id = params.getInt("filter_id",0);
            if(hrId < 1){
                return ResponseLogNotification.fail(request,"hr_id不可以为空或者为0");
            }
            if(companyId < 1){
                return ResponseLogNotification.fail(request,"company_id不可以为空或者为0");
            }
            if(filter_id < 1){
                return ResponseLogNotification.fail(request,"filter_id不可以为空或者为0");
            }

            Response result = service.getProfileFilterInfo(hrId, companyId, filter_id);
            return ResponseLogNotification.success(request, result);
        }catch(Exception e){
            logger.info(e.getMessage(),e);
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }

    /*
    保存企业标签信息
    */
    @RequestMapping(value = "/api/talentpool/profilefilter", method = RequestMethod.POST)
    @ResponseBody
    public String handerProfileFilterList(HttpServletRequest request) throws Exception {
        try {
            Params<String, Object> data = ParamUtils.parseRequestParam(request);
            String hrId=String.valueOf(data.get("hr_id"));
            if(StringUtils.isNullOrEmpty(hrId)||"0".equals(hrId)){
                return ResponseLogNotification.fail(request,"hr_id不可以为空或者为0");
            }
            TalentpoolCompanyTagDO profileFilterDO = new TalentpoolCompanyTagDO();
            if(data.get("profile_filter") != null) {
                profileFilterDO = ParamUtils.initModelForm((Map<String, Object>) data.get("profile_filter"), TalentpoolCompanyTagDO.class);
            }
            if(profileFilterDO.getCompany_id()<=0){
                return ResponseLogNotification.fail(request,"company_id不可以为空或者为0");
            }
            List<ActionForm> actionFormList = new ArrayList<>();
            if(data.get("action") != null) {
                List<Map<String, Object>> actionMapList = (List<Map<String, Object>>) data.get("action");
                if(actionMapList != null && actionMapList.size()>0) {
                    actionFormList = actionMapList.stream().map(m -> {
                        ActionForm form = new ActionForm();
                        try {
                            form = ParamUtils.initModelForm(m, ActionForm.class);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return form;
                    }).collect(Collectors.toList());
                }else{
                    return  ResponseLogNotification.fail(request, "可执行操作不能为空");
                }
            }else{
                return  ResponseLogNotification.fail(request, "可执行操作不能为空");
            }
            List<Integer> positionIdList = new ArrayList<>();
            int position_total = data.getInt("position_total",0);
            if(position_total!=1) {
                if (data.get("position") != null) {
                    positionIdList = ( List<Integer>) data.get("position");
                    if (positionIdList == null || positionIdList.size() < 1) {
                        return ResponseLogNotification.fail(request, "至少绑定一个职位选择一个");
                    }
                } else {
                    return ResponseLogNotification.fail(request, "至少绑定一个职位选择一个");
                }
            }
            Response result = service.addProfileFilter(profileFilterDO, actionFormList, positionIdList, Integer.parseInt(hrId), position_total);
            return ResponseLogNotification.success(request, result);
        }catch(Exception e){
            logger.error(e.getMessage(),e);
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }
    /*
    修改企业标签信息
    */
    @RequestMapping(value = "/api/talentpool/profilefilter", method = RequestMethod.PUT)
    @ResponseBody
    public String updateProfileFilterList(HttpServletRequest request) throws Exception {
        try {
            Params<String, Object> data = ParamUtils.parseRequestParam(request);
            String hrId=String.valueOf(data.get("hr_id"));
            if(StringUtils.isNullOrEmpty(hrId)||"0".equals(hrId)){
                return ResponseLogNotification.fail(request,"hr_id不可以为空或者为0");
            }
            TalentpoolCompanyTagDO profileFilterDO = new TalentpoolCompanyTagDO();
            if(data.get("profile_filter") != null) {
                profileFilterDO = ParamUtils.initModelForm((Map<String, Object>) data.get("profile_filter"), TalentpoolCompanyTagDO.class);
            }
            if(profileFilterDO.getCompany_id()<=0){
                return ResponseLogNotification.fail(request,"company_id不可以为空或者为0");
            }
            if(profileFilterDO.getId()<=0){
                return ResponseLogNotification.fail(request,"id不可以为空或者为0");
            }
            List<ActionForm> actionFormList = new ArrayList<>();
            if(data.get("action") != null) {
                List<Map<String, Object>> actionMapList = (List<Map<String, Object>>) data.get("action");
                if(actionMapList != null && actionMapList.size()>0) {
                    actionFormList = actionMapList.stream().map(m -> {
                        ActionForm form = new ActionForm();
                        try {
                            form = ParamUtils.initModelForm(m, ActionForm.class);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return form;
                    }).collect(Collectors.toList());
                }else{
                    return  ResponseLogNotification.fail(request, "可执行操作不能为空");
                }
            }else{
                return  ResponseLogNotification.fail(request, "可执行操作不能为空");
            }
            int position_total = data.getInt("position_total",0);
            List<Integer> positionIdList = new ArrayList<>();
            if(position_total!=1) {
                if (data.get("position") != null) {
                    positionIdList = ( List<Integer>) data.get("position");
                    if (positionIdList == null || positionIdList.size() < 1) {
                        return ResponseLogNotification.fail(request, "至少绑定一个职位选择一个");
                    }
                } else {
                    return ResponseLogNotification.fail(request, "至少绑定一个职位选择一个");
                }
            }
            Response result = service.updateProfileFilter(profileFilterDO, actionFormList, positionIdList, Integer.parseInt(hrId), position_total);
            return ResponseLogNotification.success(request, result);
        }catch(Exception e){
            logger.error(e.getMessage(),e);
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }

    /*
  获取企业标签信息
 */
    @RequestMapping(value = "/api/position/talent/count", method = RequestMethod.GET)
    @ResponseBody
    public String getTalentCountByPosition(HttpServletRequest request) throws Exception {
        try {
            Params<String, Object> params = ParamUtils.parseRequestParam(request);
            int hrId=params.getInt("hr_id", 0);
            int companyId=params.getInt("company_id",0);
            int position_id = params.getInt("position_id",0);
            if(hrId < 1){
                return ResponseLogNotification.fail(request,"hr_id不可以为空或者为0");
            }
            if(companyId < 1){
                return ResponseLogNotification.fail(request,"company_id不可以为空或者为0");
            }
            if(position_id < 1){
                return ResponseLogNotification.fail(request,"position_id不可以为空或者为0");
            }

            Response result = service.getTalentCountByPositionFilter(hrId, companyId, position_id);
            return ResponseLogNotification.success(request, result);
        }catch(Exception e){
            logger.info(e.getMessage(),e);
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }

    /*
 获取企业邮箱剩余额度
*/
    @RequestMapping(value = "/api/talentpool/email/availaability", method = RequestMethod.GET)
    @ResponseBody
    public String getEmailBalance(HttpServletRequest request) throws Exception {
        try {
            Params<String, Object> params = ParamUtils.parseRequestParam(request);
            int hrId=params.getInt("hr_id", 0);
            int companyId=params.getInt("company_id",0);
            if(hrId < 1){
                return ResponseLogNotification.fail(request,"hr_id不可以为空或者为0");
            }
            if(companyId < 1){
                return ResponseLogNotification.fail(request,"company_id不可以为空或者为0");
            }

            Response result = service.getEmailBalance(hrId, companyId);
            return ResponseLogNotification.success(request, result);
        }catch(Exception e){
            logger.info(e.getMessage(),e);
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }

    /*
 获取企业邮箱剩余额度
*/
    @RequestMapping(value = "/api/talentpool/email/template", method = RequestMethod.GET)
    @ResponseBody
    public String getEmailTemplateList(HttpServletRequest request) throws Exception {
        try {
            Params<String, Object> params = ParamUtils.parseRequestParam(request);
            int hrId=params.getInt("hr_id", 0);
            int companyId=params.getInt("company_id",0);
            if(hrId < 1){
                return ResponseLogNotification.fail(request,"hr_id不可以为空或者为0");
            }
            if(companyId < 1){
                return ResponseLogNotification.fail(request,"company_id不可以为空或者为0");
            }

            Response result = service.getEmailTemplateList(hrId, companyId);
            return ResponseLogNotification.success(request, result);
        }catch(Exception e){
            logger.info(e.getMessage(),e);
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }

    /*
获取邮件详情
*/
    @RequestMapping(value = "/api/talentpool/email/info", method = RequestMethod.GET)
    @ResponseBody
    public String getEmailInfo(HttpServletRequest request) throws Exception {
        try {
            Params<String, Object> params = ParamUtils.parseRequestParam(request);
            int hrId=params.getInt("hr_id", 0);
            int companyId=params.getInt("company_id",0);
            int type = params.getInt("type",0);
            if(hrId < 1){
                return ResponseLogNotification.fail(request,"hr_id不可以为空或者为0");
            }
            if(companyId < 1){
                return ResponseLogNotification.fail(request,"company_id不可以为空或者为0");
            }
            if(type < 1){
                return ResponseLogNotification.fail(request,"hr_id不可以为空或者为0");
            }
            Response result = service.getEmailInfo(hrId, companyId, type);
            return ResponseLogNotification.success(request, result);
        }catch(Exception e){
            logger.info(e.getMessage(),e);
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }

    /*
    获取邮件详情
    */
    @RequestMapping(value = "/api/talentpool/email/info", method = RequestMethod.PATCH)
    @ResponseBody
    public String updateEmailInfo(HttpServletRequest request) throws Exception {
        try {
            Params<String, Object> params = ParamUtils.parseRequestParam(request);
            int hrId=params.getInt("hr_id", 0);
            int companyId=params.getInt("company_id",0);
            int type = params.getInt("type",0);
            int status = params.getInt("status", -1);
            String text = params.getString("text","");
            String sign = params.getString("sign", "");
            if(hrId < 1){
                return ResponseLogNotification.fail(request,"hr_id不可以为空或者为0");
            }
            if(companyId < 1){
                return ResponseLogNotification.fail(request,"company_id不可以为空或者为0");
            }
            if(type < 1){
                return ResponseLogNotification.fail(request,"hr_id不可以为空或者为0");
            }
            Response result = service.updateCompanyEmailInfo(hrId, companyId, type, status, text, sign);
            return ResponseLogNotification.success(request, result);
        }catch(Exception e){
            logger.info(e.getMessage(),e);
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }
    /*
       扣除邮件点数
     */
    @RequestMapping(value = "/api/talentpool/email/balance", method = RequestMethod.PUT)
    @ResponseBody
    public String updateEmailInfoBalance(HttpServletRequest request) throws Exception {
        try {
            Map<String, Object> params = ParamUtils.parseRequestParam(request);
            int companyId=Integer.parseInt((String)params.get("company_id"));
            int balance=Integer.parseInt((String)params.get("balance"));
            Response result=service.updateCompanyEmailBalance(companyId,balance);
            return ResponseLogNotification.success(request, result);
        }catch(Exception e){
            logger.info(e.getMessage(),e);
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }

    /*
       转发简历
     */
    @RequestMapping(value = "/api/talentpool/forward/resume", method = RequestMethod.POST)
    @ResponseBody
    public String sendResumeEmail(HttpServletRequest request) throws Exception {
        try {
            Map<String, Object> params = ParamUtils.parseRequestParam(request);
            int companyId=(int)params.get("company_id");
            Map<String,Object> conditionInfo=(Map<String,Object>)params.get("filter");
            Map<String,String> condition=new HashMap<>();
            if(!StringUtils.isEmptyMap(conditionInfo)){
                for(String key:conditionInfo.keySet()){
                    condition.put(key,String.valueOf(conditionInfo.get(key))) ;
                }
            }
            int hrId=(int)params.get("hr_id");
            int flag=0;
            if(params.get("user_all")!=null){
                boolean userAll=(boolean)params.get("user_all");
                if(userAll){
                    flag=1;
                }
            }
            List<Integer> idList=( List<Integer>)params.get("employee");
            List<Integer> userIdList=( List<Integer>)params.get("user_ids");
            List<String>  emailList=(List<String>)params.get("emails");
            if(StringUtils.isEmptyList(userIdList)&&StringUtils.isEmptyList(emailList)){
                return ResponseLogNotification.fail(request,"接收邮件的人不能为空");
            }
            if(StringUtils.isEmptyList(userIdList)){
                userIdList=new ArrayList<>();
            }
            if(StringUtils.isEmptyList(emailList)){
                emailList=new ArrayList<>();
            }
            Response result=service.sendResumeEmail(condition,userIdList,idList,companyId,hrId,flag,emailList);
            return ResponseLogNotification.success(request, result);
        }catch(Exception e){
            logger.info(e.getMessage(),e);
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }
    /*
       邀请投递简历
     */
    @RequestMapping(value = "/api/talentpool/application/invitation", method = RequestMethod.POST)
    @ResponseBody
    public String sendInviteEmail(HttpServletRequest request) throws Exception {
        try {
            Map<String, Object> params = ParamUtils.parseRequestParam(request);
            int companyId=(int)params.get("company_id");
            Map<String,Object> conditionInfo=(Map<String,Object>)params.get("filter");
            Map<String,String> condition=new HashMap<>();
            if(!StringUtils.isEmptyMap(conditionInfo)){
                for(String key:conditionInfo.keySet()){
                    condition.put(key,String.valueOf(conditionInfo.get(key))) ;
                }
            }
            int hrId=(int)params.get("hr_id");

            int flag=0;
            if(params.get("user_all")!=null){
            boolean userAll=(boolean)params.get("user_all");
            if(userAll){
                flag=1;
            }
            }
            int positionFlag=0;
            if(params.get("position_all")!=null){
            boolean positionAll=(boolean)params.get("position_all");
            if(positionAll){
                positionFlag=1;
            }
            }
            List<Integer> positionIdList=( List<Integer>)params.get("position_ids");
            List<Integer> userIdList=( List<Integer>)params.get("user_ids");
            Response result=service.sendInviteEmail(condition,userIdList,positionIdList,companyId,hrId,flag,positionFlag);
            return ResponseLogNotification.success(request, result);
        }catch(Exception e){
            logger.info(e.getMessage(),e);
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }
    /*
      职位邀请投递
     */
    @RequestMapping(value = "/api/position/application/invitation", method = RequestMethod.POST)
    @ResponseBody
    public String sendPositionInviteEmail(HttpServletRequest request) throws Exception {
        try {
            Map<String, Object> params = ParamUtils.parseRequestParam(request);
            int companyId=(int)params.get("company_id");
            int hrId=(int)params.get("hr_id");
            int positionId=(int)params.get("position_id");
            Response result=service.sendPositionInviteEmail(hrId,positionId,companyId);
            return ResponseLogNotification.success(request, result);
        }catch(Exception e){
            logger.info(e.getMessage(),e);
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }

    /*
      职位邀请投递
     */
    @RequestMapping(value = "/api/talent/comment/list", method = RequestMethod.POST)
    @ResponseBody
    public String getCommentList(HttpServletRequest request) throws Exception {
        try {
            Map<String, Object> params = ParamUtils.parseRequestParam(request);
            int companyId=(Integer)params.get("company_id");
            List<Integer> userIdList= (List<Integer>) params.get("user_ids");
            Response result=service.getCompanyCommentByUserIdList(companyId,userIdList);
            return ResponseLogNotification.success(request, result);
        }catch(Exception e){
            logger.info(e.getMessage(),e);
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }
}
