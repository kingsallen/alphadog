package com.moseeker.servicemanager.web.controller.company;

import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.util.StringUtils;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.servicemanager.common.ParamUtils;
import com.moseeker.servicemanager.common.ResponseLogNotification;
import com.moseeker.servicemanager.web.controller.util.Params;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.company.service.TalentpoolNewServices;
import com.moseeker.thrift.gen.company.struct.TalentpoolCompanyTagDO;
import com.moseeker.thrift.gen.company.struct.TalentpoolHrAutomaticTagDO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 *
 * 人才库接口
 * <p>Company: MoSeeker</P>
 * <p>date: Nov 2, 2016</p>
 * <p>Email: wjf2255@gmail.com</p>
 * @author wjf
 * @version
 */
@Controller
@CounterIface
public class TalentpoolNewController {

    Logger logger = LoggerFactory.getLogger(TalentpoolNewController.class);

    TalentpoolNewServices.Iface service = ServiceManager.SERVICE_MANAGER.getService(TalentpoolNewServices.Iface.class);


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
            int page_size = params.getInt("page_size",10);
            if(StringUtils.isNullOrEmpty(hrId)||"0".equals(hrId)){
                return ResponseLogNotification.fail(request,"hr_id不可以为空或者为0");
            }
            if(StringUtils.isNullOrEmpty(companyId)||"0".equals(hrId)){
                return ResponseLogNotification.fail(request,"company_id不可以为空或者为0");
            }
            Response result = service.getCompanyTagList(Integer.parseInt(hrId),Integer.parseInt(companyId), page_number, page_size);
            return ResponseLogNotification.success(request, result);
        }catch(Exception e){
            return ResponseLogNotification.fail(request, e);
        }
    }

    /*
    删除企业标签列表
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
            String  company_tags = (String)params.get("company_tags");
            List<Integer> company_tagList = ParamUtils.convertIntList(company_tags);
            if(company_tagList.size()<1){
                return ResponseLogNotification.fail(request,"company_tags长度不可以为0");
            }
            Response result = service.deleteCompanyTagByIds(Integer.parseInt(hrId),Integer.parseInt(companyId),company_tagList);
            return ResponseLogNotification.success(request, result);
        }catch(Exception e){
            return ResponseLogNotification.fail(request, e);
        }
    }


    /*
     获取具体的企业标签信息
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
            if(StringUtils.isNullOrEmpty(companyId)||"0".equals(companyId)){
                return ResponseLogNotification.fail(request,"company_id不可以为空或者为0");
            }
            if(StringUtils.isNullOrEmpty(company_tag_id)||"0".equals(company_tag_id)){
                return ResponseLogNotification.fail(request,"company_tag_id不可以为空或者为0");
            }

            Response result = service.getCompanyIdInfo(Integer.parseInt(hrId),Integer.parseInt(companyId),Integer.parseInt(company_tag_id));
            return ResponseLogNotification.success(request, result);
        }catch(Exception e){
            return ResponseLogNotification.fail(request, e);
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
            return ResponseLogNotification.fail(request, e);
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
            if(data.get("hr_id") == null || StringUtils.isNullOrEmpty(hrId)||"0".equals(hrId)){
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
            return ResponseLogNotification.fail(request, e);
        }
    }


    /**
     * 通过申请编号增加备注
     *
     * @param request
     * @param response
     *
     * @return
     */
    @RequestMapping(value = "/api/talentpool/content/application", method = RequestMethod.POST)
    @ResponseBody
    public String addProfileContent(HttpServletRequest request,
                                    HttpServletResponse response) {
        try {
            Params<String, Object> params = ParamUtils.parseRequestParam(request);
            String content = params.getString("content", "");
            int userId = params.getInt("user_id", 0);
            int accountId = params.getInt("account_id",0);
            if (StringUtils.isNotNullOrEmpty(content) && userId > 0 && accountId>0) {
                Response res = service.addProfileContent(userId, accountId, content);
                return ResponseLogNotification.success(request, res);
            } else {
                return ResponseLogNotification.fail(request, "请求参数出错！");
            }

        } catch (Exception e) {
            return ResponseLogNotification.fail(request, e);
        }
    }
    /*
       添加hr自动标签
      */
    @RequestMapping(value = "/api/talentpool/hr/autotag", method = RequestMethod.POST)
    @ResponseBody
    public String addHrAutoTag(HttpServletRequest request,
                                    HttpServletResponse response) {
        try {
            Params<String, Object> params = ParamUtils.parseRequestParam(request);
            String companyId=String.valueOf(params.get("company_id"));
            if(StringUtils.isNullOrEmpty(companyId)||"0".equals(companyId)){
                return ResponseLogNotification.fail(request,"hr_id不可以为空或者为0");
            }
            TalentpoolHrAutomaticTagDO tagDO = ParamUtils.initModelForm(params, TalentpoolHrAutomaticTagDO.class);
            Response res = service.addHrAutoMaticTag(tagDO, Integer.parseInt(companyId));
            return ResponseLogNotification.success(request, res);
        } catch (Exception e) {
            return ResponseLogNotification.fail(request, e);
        }
    }
    /*
      更新hr自动标签
     */
    @RequestMapping(value = "/api/talentpool/hr/autotag", method = RequestMethod.PUT)
    @ResponseBody
    public String updateHrAutoTag(HttpServletRequest request,
                               HttpServletResponse response) {
        try {
            Params<String, Object> params = ParamUtils.parseRequestParam(request);
            String companyId=String.valueOf(params.get("company_id"));
            if(StringUtils.isNullOrEmpty(companyId)||"0".equals(companyId)){
                return ResponseLogNotification.fail(request,"hr_id不可以为空或者为0");
            }
            TalentpoolHrAutomaticTagDO tagDO = ParamUtils.initModelForm(params, TalentpoolHrAutomaticTagDO.class);
            Response res = service.updateHrAutoMaticTag(tagDO, Integer.parseInt(companyId));
            return ResponseLogNotification.success(request, res);
        } catch (Exception e) {
            return ResponseLogNotification.fail(request, e);
        }
    }
    /*
      删除hr自动标签列表
     */
    @RequestMapping(value = "/api/talentpool/hr/autotag", method = RequestMethod.DELETE)
    @ResponseBody
    public String deleteHrAutoTag(HttpServletRequest request) throws Exception {
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
            if(params.get("tags") ==null ){
                return ResponseLogNotification.fail(request,"tags不可以为空");
            }
            String  tags = (String)params.get("tags");
            List<Integer> tagList = ParamUtils.convertIntList(tags);
            if(tagList.size()<1){
                return ResponseLogNotification.fail(request,"ctags长度不可以为0");
            }

            Response result = service.deleteHrAutoMaticTagByIds(Integer.parseInt(hrId),Integer.parseInt(companyId),tagList);
            return ResponseLogNotification.success(request, result);
        }catch(Exception e){
            return ResponseLogNotification.fail(request, e);
        }
    }
    /*
      获取hr自动标签列表
     */
    @RequestMapping(value = "/api/talentpool/hr/autotag/list", method = RequestMethod.GET)
    @ResponseBody
    public String getHrAutoTagList(HttpServletRequest request) throws Exception {
        try {
            Params<String, Object> params = ParamUtils.parseRequestParam(request);
            String hrId=String.valueOf(params.get("hr_id"));
            String companyId=String.valueOf(params.get("company_id"));
            int page_number = params.getInt("page_number", 1);
            int page_size = params.getInt("page_size",10);
            if(StringUtils.isNullOrEmpty(hrId)||"0".equals(hrId)){
                return ResponseLogNotification.fail(request,"hr_id不可以为空或者为0");
            }
            if(StringUtils.isNullOrEmpty(companyId)||"0".equals(hrId)){
                return ResponseLogNotification.fail(request,"company_id不可以为空或者为0");
            }
            Response result = service.getHrAutoMaticTagList(Integer.parseInt(hrId),Integer.parseInt(companyId), page_number, page_size);
            return ResponseLogNotification.success(request, result);
        }catch(Exception e){
            return ResponseLogNotification.fail(request, e);
        }
    }
    /*
     获取hr自动标签的具体内容
    */
    @RequestMapping(value = "/api/talentpool/hr/autotag", method = RequestMethod.GET)
    @ResponseBody
    public String getHrAutoTagSingle(HttpServletRequest request) throws Exception {
        try {
            Params<String, Object> params = ParamUtils.parseRequestParam(request);
            String hrId=String.valueOf(params.get("hr_id"));
            String companyId=String.valueOf(params.get("company_id"));
            String id=String.valueOf(params.get("id"));

            if(StringUtils.isNullOrEmpty(hrId)||"0".equals(hrId)){
                return ResponseLogNotification.fail(request,"hr_id不可以为空或者为0");
            }
            if(StringUtils.isNullOrEmpty(companyId)||"0".equals(hrId)){
                return ResponseLogNotification.fail(request,"company_id不可以为空或者为0");
            }
            if(StringUtils.isNullOrEmpty(id)||"0".equals(id)){
                return ResponseLogNotification.fail(request,"id不可以为空或者为0");
            }
            Response result = service.getHrAutoMaticTagSingle(Integer.parseInt(hrId),Integer.parseInt(companyId),Integer.parseInt(id));
            return ResponseLogNotification.success(request, result);
        }catch(Exception e){
            return ResponseLogNotification.fail(request, e);
        }
    }
}
