package com.moseeker.profile.service.impl.retriveprofile;

import com.alibaba.fastjson.JSON;
import com.moseeker.common.constants.ChannelType;
import com.moseeker.profile.config.AppConfig;
import com.moseeker.profile.service.impl.retriveprofile.executor.Coupler;
import com.moseeker.profile.service.impl.retriveprofile.flow.aliapyflow.AliPayRetrievalParam;
import com.moseeker.profile.service.impl.retriveprofile.flow.aliapyflow.ApplicationTaskParamUtil;
import com.moseeker.profile.service.impl.retriveprofile.flow.aliapyflow.CreatePasswordTaskParamUtil;
import com.moseeker.profile.service.impl.retriveprofile.flow.aliapyflow.JobResumeTaskParamUtil;
import com.moseeker.profile.service.impl.retriveprofile.flow.aliapyflow.ProfileTaskParamUtil;
import com.moseeker.profile.service.impl.retriveprofile.flow.aliapyflow.UserAliTaskParamUtil;
import com.moseeker.profile.service.impl.retriveprofile.flow.aliapyflow.UserTaskAliPaySource;
import com.moseeker.profile.service.impl.retriveprofile.flow.aliapyflow.UserTaskParamUtil;
import com.moseeker.profile.service.impl.retriveprofile.tasks.ApplicationTask;
import com.moseeker.profile.service.impl.retriveprofile.tasks.CreatePasswordTask;
import com.moseeker.profile.service.impl.retriveprofile.tasks.JobResumeTask;
import com.moseeker.profile.service.impl.retriveprofile.tasks.ProfileTask;
import com.moseeker.profile.service.impl.retriveprofile.tasks.UserAliTask;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Map;

/**
 * Created by jack on 11/07/2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class RetriveProfileTest {

    @Autowired
    private RetriveProfile retriveProfile;

    @Autowired
    private UserTaskAliPaySource userTask;

    @Autowired
    private UserAliTask userAliTask;

    @Autowired
    private CreatePasswordTask passwordTask;

    @Autowired
    private ProfileTask profileTask;

    @Autowired
    private ApplicationTask applicationTask;

    @Autowired
    private JobResumeTask jobResumeTask;

    @Autowired
    private UserTaskParamUtil userTaskParamUtil;

    @Autowired
    private CreatePasswordTaskParamUtil passwordParamUtil;

    @Autowired
    private UserAliTaskParamUtil userAliTaskParamUtil;

    @Autowired
    private ProfileTaskParamUtil profileTaskParamUtil;

    @Autowired
    private ApplicationTaskParamUtil applicationTaskParamUtil;

    @Autowired
    private JobResumeTaskParamUtil jobResumeTaskParamUtil;


    @Test
    public void testRetrieve() {
        boolean result = retriveProfile.retrieve(PARAMETER);
        System.out.println(result);
    }


    /**
     * aliPayRetrievalParam 参数测试
     */
    public ExecutorParam executorParamTest() {
        Map<String, Object> paramMap = JSON.parseObject(PARAMETER);

        int channel = (Integer) paramMap.get("channel");
        ChannelType channelType = ChannelType.instaceFromInteger(channel);

        AliPayRetrievalParam aliPayRetrievalParam = new AliPayRetrievalParam();
        aliPayRetrievalParam.parseParameter(paramMap, channelType);

        return aliPayRetrievalParam;
    }


    /**
     * userTask
     */
    @Test
    public void testUserTask() {
        Coupler coupler = new Coupler(userTask, userTaskParamUtil);
        ExecutorParam executorParam = executorParamTest();
        Object tempParam = null;
        System.out.println(coupler.execute(tempParam, executorParam));
    }


    /**
     * passwordTask
     */
    @Test
    public void testPasswordTask() {
        Coupler coupler = new Coupler(passwordTask, passwordParamUtil);
        // 创建用户生成的ID
        Integer id = 1197007;
        coupler.execute(id, executorParamTest());
    }


    /**
     * UserAiTask
     */
    @Test
    public void testUserAiTask() {
        Coupler coupler = new Coupler(userAliTask, userAliTaskParamUtil);
        coupler.execute(true, executorParamTest());

    }

    /**
     * ProfileTask
     */
    @Test
    public void testProfileTask() {
        Coupler coupler = new Coupler(profileTask, profileTaskParamUtil);
        coupler.execute(true, executorParamTest());
    }

    /**
     * ApplicationTask
     */
    @Test
    public void testApplicationTask() {
        Coupler coupler = new Coupler(applicationTask, applicationTaskParamUtil);
        coupler.execute(true, executorParamTest());
    }


    /**
     * JobResumeTask
     */
    @Test
    public void testJobResumeTask() {
        Coupler coupler = new Coupler(jobResumeTask, jobResumeTaskParamUtil);
        coupler.execute(true, executorParamTest());
    }


    public static final String PARAMETER = "{'appid':0,'channel':1,'positionId':1400,'jobResumeOther':{'resumeFileParser:':'hello','test1':'helll'},'profile':{'profile':{},'skills': [{'level': 3, 'month': 0, 'name': 'Metrics'}, {'level': 4, 'month': 0, 'name': 'WindowsNT/2000'}, {'level': 3, 'month': 0, 'name': 'SQLServer'}, {'level': 4, 'month': 0, 'name': 'ProjectManager'}, {'level': 3, 'month': 0, 'name': 'SQA'}, {'level': 3, 'month': 0, 'name': 'QualityControl'}, {'level': 3, 'month': 0, 'name': 'VBScript'}, {'level': 4, 'month': 0, 'name': 'Technology'}, {'level': 1, 'month': 0, 'name': 'SQL'}, {'level': 1, 'month': 0, 'name': 'Sybase'}, {'level': 3, 'month': 0, 'name': 'Windows95'}, {'level': 3, 'month': 0, 'name': 'LotusNotes'}, {'level': 3, 'month': 0, 'name': 'VisualBasic'}, {'level': 3, 'month': 0, 'name': 'FinancialServices'}, {'level': 3, 'month': 0, 'name': 'Network'}, {'level': 1, 'month': 0, 'name': 'FoWindowsXPro'}, {'level': 3, 'month': 0, 'name': 'IIS'}, {'level': 3, 'month': 0, 'name': 'MicrosoftProject'}, {'level': 3, 'month': 0, 'name': 'C/C++'}, {'level': 4, 'month': 0, 'name': 'Windows98'}, {'level': 3, 'month': 0, 'name': 'MSOffice'}, {'level': 4, 'month': 0, 'name': 'PC/Desktop'}, {'level': 1, 'month': 0, 'name': 'CORBA'}, {'level': 3, 'month': 0, 'name': 'MSVISIO'}, {'level': 3, 'month': 0, 'name': 'Oracle'}, {'level': 4, 'month': 0, 'name': 'VisualBasic'}, {'level': 3, 'month': 0, 'name': 'ISO'}, {'level': 4, 'month': 0, 'name': 'ASP'}, {'level': 1, 'month': 0, 'name': 'PHP'}, {'level': 1, 'month': 0, 'name': 'JavaScript'}, {'level': 1, 'month': 0, 'name': 'PowerBuilder'}], 'educations': [{'start_date': '1994-09-01', 'end_until_now': 0, 'college_name': '上海大学', 'description': '毕业设计为日本住友银行的一个量贩系统，全日文环境开发，开发工具是HOLONAB', 'major_name': '计算机应用', 'degree': '5', 'end_date': '1998-07-01'}, {'start_date': '1991-09-01', 'end_until_now': 0, 'college_name': '上海市卢湾中学', 'description': '', 'major_name': '', 'degree': '0', 'end_date': '1994-07-01'}, {'start_date': '1988-09-01', 'end_until_now': 0, 'college_name': '上海市打浦中学', 'description': '', 'major_name': '', 'degree': '0', 'end_date': '1991-07-01'}], 'projectexps': [{'start_date': '2012-01-01', 'end_until_now': 1, 'responsibility': '作为项目实施经理，负责现场协调、实施安排、成本控制、风险识别等，也参与了全程的需求调研及分析。', 'description': '为客户构建新的财务处理流程，提供单据扫描、识别分类、自动流转、工作流处理、财务对账等一系列与财务原始单据有关的影像管理。项目规模约1000K++', 'end_date': '', 'company_name': '', 'name': '锦江之星财务影像管理系统'}, {'start_date': '2011-05-01', 'end_until_now': 1, 'responsibility': '作为项目经理及全国范围的项目协调，从Case挖掘、客户关系建立及加强、Solution构建、方案设计到现场实施安排和部署协调等全程进行管理，把1个单酒店项目做大成中国区（甚至可能是客户亚太或全球范围的）的整体合作项目。', 'description': 'DocuWorks、FAXServer(开发工具)为酒店客房预订部，提供针对FAX订单的工作流流转、处理、归档及检索，并提供Email或其他电子文档的导入。在酒店客房预订业务中，树立了新的BenchMark。已获得客户高层认可，已在其中国30多家酒店展开部署，并正在与其亚太及亚太外的国外酒店进行推广协商中。项目规模3000K++', 'end_date': '', 'company_name': '', 'name': 'Shangri-La酒店订单管理系统'}, {'start_date': '2011-03-01', 'end_until_now': 0, 'responsibility': '作为项目实施经理，负责现场协调、实施部署、验收测试、需求变更控制等。通过控制项目范围和规范变更，为客户2期上线提供了充实的项目支持，并额外为公司争得超过预期100%的新需求单子', 'description': '为TNT新建的总部财务管理中心，提供单据扫描、识别分类、自动流转、工作流处理、财务对账等一系列与财务原始单据有关的影像管理。分成2期实施，项目总规模约有1500K', 'end_date': '2012-03-01', 'company_name': '', 'name': 'TNT财务中心影像管理系统'}, {'start_date': '2008-05-01', 'end_until_now': 0, 'responsibility': '作为项目经理，控制项目分阶段实施，分析项目风险，进行项目培训、验收和结项', 'description': 'VB，DocuWorks(开发工具)为全球知名的运动服装品牌迪卡侬，提供亚洲地区的单据扫描归档服务（全球均由富士施乐分区负责）。\n通过单据扫描识别，将单据信息关联到对应的扫描电子文档属性中，并提供可以上传到客户ERP的接口。', 'end_date': '2009-03-01', 'company_name': '', 'name': '迪卡侬扫描归档系统'}, {'start_date': '2008-01-01', 'end_until_now': 0, 'responsibility': '因为项目成本亏损严重，后期才中途加入项目；作为项目经理，重新控制调整了项目实施，通过加强与客户沟通，避免了客户的不信任；在接手的半年内，不仅收回了全部投入成本，还重新获得了客户新单子，顺利完成项目大逆转。', 'description': 'DocuWorks(开发工具)将客户通过FAX和Email收到的船运订单，通过统一的DocuWorks客户端平台，实现订单自动分发和工作流流转', 'end_date': '2008-12-01', 'company_name': '', 'name': '商船三井订单管理系统'}, {'start_date': '2007-10-01', 'end_until_now': 0, 'responsibility': '作为项目经理，控制项目实施，分析项目风险，进行项目培训。也参与了主体设计分析，并通过项目来培养Group内的ProjectLeader', 'description': 'VB，PDF，DocuWorks(开发工具)杨子BASF的行政办公室需要对500平米的档案库进行档案电子化：通过扫描识别，将档案信息关联到指定的扫描电子文档属性中，并按照其要求的目录结构，定期地制作成可检索的档案光盘。', 'end_date': '2008-05-01', 'company_name': '', 'name': 'BASF扫描归档项目'}, {'start_date': '2004-06-01', 'end_until_now': 0, 'responsibility': '控制项目实施，分析项目风险，进行项目培训。', 'description': 'C/S，VB，ASP(开发工具)\nIBMRS6000M85,IBMX350,Dell6500(硬件环境)\nWIN2003AdvanceServer，IIS，Oracle(软件环境)公安局综合信息管理系统项目规模3500万，综合治安管理信息系统项目规模300万。该项目是“金盾工程”中关键和重要的内容，涉及济南市公安局各警种的基础业务工作，这个系统的建立将为全市派出所搭建统一的系统平台，为全局各部门深入开展打、防、控提供强有力的基础工作手段', 'end_date': '2005-04-01', 'company_name': '', 'name': '山东省济南市公安局综合信息管理系统和综合治安管理信息系统'}, {'start_date': '2003-09-01', 'end_until_now': 0, 'responsibility': '项目经理，负责现场软件的安装、调试、预验，参与了整个系统的联调、验收。', 'description': '为崇明教育局远程教学项目提供产品部署（MediaCast4.0&VSchool4.2)', 'end_date': '2003-12-01', 'company_name': '', 'name': '崇明教育局远程教学'}, {'start_date': '2003-06-01', 'end_until_now': 0, 'responsibility': '作为技术支持工程师，进行系统调研及演示工作；为客户的培训工作展开提供了长时间充分的技术支撑。', 'description': '为MBA培训提供网上教学（流媒体）培训', 'end_date': '2003-11-01', 'company_name': '', 'name': '复旦大学管理学院MBA办公室远程教育'}, {'start_date': '2003-05-01', 'end_until_now': 0, 'responsibility': '作为技术支持工程师，负责组织团队到各院校安装、调试软件。并对使用者提供维护及支持。', 'description': '将公司为抗“非典”捐赠的远程教学软件进行安装、部署', 'end_date': '2003-12-01', 'company_name': '', 'name': '复旦光华抗“非典”软件捐赠项目实施'}, {'start_date': '2001-09-01', 'end_until_now': 0, 'responsibility': '前期作为技术支持专家，为对应用、系统等部分对象提供技术支撑及咨询服务；后期接手项目经理岗位，进行项目管理职责，作为项目总包单位的项目经理引导各分项目实施、融合。', 'description': '市检察系统信息化建设', 'end_date': '2003-03-01', 'company_name': '', 'name': '上海市检察院项目'}, {'start_date': '2000-05-01', 'end_until_now': 0, 'responsibility': '作为项目经理，对系统建设工作协调管理；组织监理团队对实施单位的工作从技术、管理等方面进行监督；保障了政府对计算机系统建设的投资和使用。', 'description': '作为政府项目，被邀请对市民政局计算机信息系统建设实施监理，为市民政局对信息化建设把关。', 'end_date': '2003-04-01', 'company_name': '', 'name': '上海市民政局计算机信息系统建设监理'}, {'start_date': '1999-09-01', 'end_until_now': 0, 'responsibility': '作为分项目主管，负责该小组的开发、实施、协调及部署。', 'description': '解决市检察院系统Y2K问题，并将其终端系统转换为一套BS结构的系统使用。', 'end_date': '2000-09-01', 'company_name': '', 'name': '上海市检察院项目Y2K问题解决项目'}, {'start_date': '1999-01-01', 'end_until_now': 0, 'responsibility': '作为系统分析，参与了前期系统需求调研、分析、设计。', 'description': '通过银行卡上的所携带IC芯片内容的管理，实现大学校园内的一卡通', 'end_date': '1999-05-01', 'company_name': '', 'name': '复旦大学农行银行IC卡入校工程'}, {'start_date': '1998-12-01', 'end_until_now': 0, 'responsibility': '作为系统分析员，参与了前期所有的现场调研、技术论证讨论等，并自主完成了公司方对社会保障IC卡实施项目所提交的系统设计书。', 'description': '参与对社会保障IC卡的实施进行的前期论证、调研、设计等工作', 'end_date': '1999-10-01', 'company_name': '', 'name': '上海市社会保障IC卡工程'}, {'start_date': '1998-05-01', 'end_until_now': 0, 'responsibility': '由公司外派，参与上海启明软件公司（日资）外包项目，作为软件开发工程师参与编码、测试等；顺利完成第一期任务，并得到参与项目组人员的好评。', 'description': 'NEC公司的HOLON开发工具(开发工具)\n日文WIN95/ORACLE7.3(软件环境)分期开发用于日本便利店的可图形化销售系统', 'end_date': '1998-12-01', 'company_name': '', 'name': '日本外包的某便利店（CVS）系统'}, {'start_date': '1998-03-01', 'end_until_now': 0, 'responsibility': '作为测试人员，参与后期测试及部分修改工作。', 'description': '日文WIN95(软件环境)使用NEC的HOLONAB进行开发', 'end_date': '1998-05-01', 'company_name': '', 'name': '日本住友银行外包的1个量贩系统'}], 'user': {'mobile': '18600164078', 'email': 'losset@189.cn', 'uid':'2312','name': '翁剑飞'}, 'basic': {'gender': '1', 'birth': '1975-12-27', 'city_name': '上海', 'self_introduction': '资深项目经理，14年以上IT工作经验，懂英语和日语，从事项目管理、质量管理、第三方供应商管理和信息系统建设监理,历经软件开发、系统分析及设计、项目经理、技术支持、质量管理、Solution管理及支持等岗位,有12年以上的丰富的主管经验和4年质量管理经验，熟悉组建和培训一个项目的团队，熟悉大型项目/项目群推进和管理。', 'name': '周磊'}, 'workexps': [{'company': {'company_name': '富士施乐实业发展(上海)有限公司', 'company_property': 1, 'company_industry': '办公用品及设备', 'company_scale': 5}, 'start_date': '2010-05-01', 'end_until_now': 1, 'description': '协助日本总部筹建Solution小组，并协同进行专题的Solution推广和售前支持工作；负责进行客户需求挖掘、系统分析、Solution构建及推荐；负责大型项目的项目管理、现场实施、售后维护等，管理项目团队及协调内部项目组成员。\n参与FX的改善活动，改善了项目团队的工作流程，利用“科学思维方法”在2012年4月获得FXCL改善活动第二名', 'job': '系统分析师', 'department_name': '华东区系统分析部', 'end_date': ''}, {'company': {'company_name': '富士施乐实业发展(上海)有限公司', 'company_property': 1, 'company_industry': '计算机软件', 'company_scale': 4}, 'start_date': '2007-06-01', 'end_until_now': 0, 'description': '构建Group的标准化解决方案处理流程；负责与销售公司的协调和解决方案推广的售前管理、项目实施、售后管理等；管理Group项目资源和第三方供应商。', 'job': '项目经理', 'department_name': '中国软件开发中心-SolutionGroup', 'end_date': '2010-05-01'}, {'company': {'company_name': '富士施乐实业发展(上海)有限公司', 'company_property': 1, 'company_industry': '计算机软件', 'company_scale': 4}, 'start_date': '2005-04-01', 'end_until_now': 0, 'description': '1、作为SQAteamLeader负责SQA工程师的团队领导，并在兼着本中心项目的SQA的同时，还着手SPISQA工作：\n1）计划SPISQAPLAN，组织安排ExternalAudit(FXAP范围)、CrossQAAudit、SQAAudit、Validation等定期活动；\n2）根据实际情况，独立建立SQACONSULTGUIDE，协助建立CriticalQualityProcess，引进FX的指标管理表建立SeniorManagementReviewRule以及时向本中心高级经理们汇报；\n2、负责本中心所有软件项目的流程控制、监督，保障项目按CMM3要求实施：\n1）计划项目SQAPLAN，安排weeklychecking(includereview、effort、size、schedul,etc)、ARanalysis、Milestonereview(orSeniorManagementreview)tracking、customersuvery等活动，并定期与项目组或PM/PL交流；\n2）按照项目的实际情况，进行tailor或waive；\n3）协助项目减少不必要、重复的步骤，优化定制PROCESS，以提高项目效率；\n3、作为TPGLeader负责安排和实施本中心关于CMM流程控制等的OSSP相关培训：\n1）计划SPITrainingPLAN，组织安排培训；\n2）跟踪培训实际效果，收集各类培训需求；\n3）建立Rolebasedsystemfortraining，以适应各岗位要求；\n4）组织TPG月会，协调trainer及加强培训效果；\n4、长期协助部门主管建立metrics库以汇总分析中心所有质量数据：\n1）收集分析项目质量数据，如defectdensity、CoQ、productivity、developmentraito、reviewstatistics、Non_ComplianceIssuetracking等；\n2）收集分析SQA质量数据，如SQAstatistics、SQAresponserate等；\n3）收集分析培训质量数据，如年度的lessonslearned、bestpractices等。\n5、组织并参与FX的改善活动，提高了团队的效率，利用“科学思维方法”分别在2005、2006实施2个主题：\n2005：通过加强SPIGroup对多功能机产品知识的掌握，以提高SPIGroup工作效率来更好地协助项目重要品质问题的预防；\n2006：改善SPIGroup在中心Effort数据收集分析中的效率。', 'job': 'SPISpecialist/SQAEngineer', 'department_name': '中国软件开发中心-SPIGroup', 'end_date': '2007-06-01'}, {'company': {'company_name': '上海金硅科技发展有限公司', 'company_property': 5, 'company_industry': '计算机软件', 'company_scale': 0}, 'start_date': '2004-06-01', 'end_until_now': 0, 'description': '负责项目实际实施和进度安排', 'job': '项目经理', 'department_name': '工程部', 'end_date': '2005-04-01'}, {'company': {'company_name': '上海复旦光华信息科技股份有限公司', 'company_property': 3, 'company_industry': '计算机软件', 'company_scale': 0}, 'start_date': '2003-06-01', 'end_until_now': 0, 'description': '负责对公司项目的应用部分进行技术支持、售后支撑等', 'job': '技术支持/维护工程师', 'department_name': '客户服务部', 'end_date': '2004-04-01'}, {'company': {'company_name': '上海复旦光华信息科技股份有限公司', 'company_property': 3, 'company_industry': '计算机软件', 'company_scale': 0}, 'start_date': '2001-11-01', 'end_until_now': 0, 'description': '根据部门工作流程制定了ISO流程及文档，负责对部门项目要求ISO标准化；参与了公司ISO标准的内审、外审工作。', 'job': 'ISO9001：2000标准内审员', 'department_name': '系统集成部', 'end_date': '2004-04-01'}, {'company': {'company_name': '上海复旦光华信息科技股份有限公司', 'company_property': 3, 'company_industry': '计算机软件', 'company_scale': 0}, 'start_date': '2000-05-01', 'end_until_now': 0, 'description': '代理部门经理。\n负责政府等方面的计算机信息系统项目建设的实施和管理；负责计算机信息系统项目建设的监理；指导和管理项目团队；与政府客户长期保持了良好合作关系。\n（专业方面主要侧重于系统及应用方面）', 'job': '项目经理', 'department_name': '系统集成部', 'end_date': '2003-05-01'}, {'company': {'company_name': '上海复旦光华信息科技股份有限公司', 'company_property': 3, 'company_industry': '计算机软件', 'company_scale': 0}, 'start_date': '1999-01-01', 'end_until_now': 0, 'description': '负责进行系统分析、设计、需求调研等，并据此编写系统分析书或总体设计书。', 'job': '系统分析员', 'department_name': '系统集成部', 'end_date': '2000-04-01'}, {'company': {'company_name': '上海复旦光华信息科技股份有限公司', 'company_property': 3, 'company_industry': '计算机软件', 'company_scale': 0}, 'start_date': '1998-03-01', 'end_until_now': 0, 'description': '参与项目的编码、测试等工作，参与了多个日本外包项目。', 'job': '软件工程师', 'department_name': '研发部', 'end_date': '1999-01-01'}], 'credentials': [{'get_date': '2004-05-01', 'name': '上海紧缺人才培训工程高级信息经理岗位资格证书'}], 'intentions': [{'worktype': 1, 'tag': '项目管理#IT', 'workstate': '2', 'cities': [{'city_name': '上海'}, {'city_name': '香港'}], 'positions': [{'position_name': '信息技术经理/主管'}, {'position_name': '技术总监/经理'}, {'position_name': '项目经理'}, {'position_name': '品质经理'}, {'position_name': '项目总监'}], 'icanstart': '4', 'industries': [{'industry_name': '计算机软件'}, {'industry_name': '专业服务(咨询、人力资源、财会)'}], 'salary_code': '8'}], 'languages': []}}";

}