package com.moseeker.function.service.wordpress;

import java.util.ArrayList;
import java.util.List;

import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.moseeker.common.util.ConfigPropertiesUtil;
import com.moseeker.common.util.StringUtils;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.thrift.gen.dao.service.UserHrAccountDao;
import com.moseeker.thrift.gen.dao.service.WordpressDao;
import com.moseeker.thrift.gen.dao.struct.PostExt;
import com.moseeker.thrift.gen.dao.struct.WordpressPosts;
import com.moseeker.thrift.gen.foundation.wordpress.struct.NewsletterData;
import com.moseeker.thrift.gen.foundation.wordpress.struct.NewsletterForm;

@Service
public class WordpressServiceImpl {

	Logger logger = LoggerFactory.getLogger(WordpressServiceImpl.class);

	WordpressDao.Iface wordpressDao = ServiceManager.SERVICEMANAGER.getService(WordpressDao.Iface.class);

	UserHrAccountDao.Iface hraccountDao = ServiceManager.SERVICEMANAGER.getService(UserHrAccountDao.Iface.class);

	/**
	 * 查询用户的新版本通知消息
	 * 
	 * @param newsletter
	 * @return
	 */
	public NewsletterData getNewsletter(NewsletterForm newsletter) {
		//
		NewsletterData data = new NewsletterData();
		try {
			// todo 缺少其他平台的查询
			/*WordpressTermRelationships relationships = wordpressDao
					.getLastRelationships(Constant.WORDPRESS_NEWSLETTER_VALUE);*/
			WordpressPosts post = wordpressDao.getReleaseVersionPost();
			if (post != null && post.getId() > 0) {
				long readedPostId = wordpressDao.getReadedPostId(newsletter.getAccount_id());
				//如果用户之前读的文章的编号小于最新文章编号，则表示用户未读过新版本
				if(readedPostId < post.getId()) {
					data.setShow_new_version((byte) 1);
					//更新用户浏览过的版本更新文章
					wordpressDao.upsertUserPost(newsletter.getAccount_id(), post.getId());
				} else {
					data.setShow_new_version((byte) 0);
				}
				data.setUpdate_time(post.getPostDate());
				List<String> updatList = createUpdateList(post.getPostContent());
				data.setUpdate_list(updatList);
				ConfigPropertiesUtil configUtils = ConfigPropertiesUtil.getInstance();
				try {
					configUtils.loadResource("chaos.properties");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				String domain = configUtils.get("wordpress.domain", String.class);
				data.setUrl(domain + post.getPostName());
				data.setTitle(post.getPostTitle());
				PostExt postExt = wordpressDao.getPostExt(post.getId());
				if (postExt != null && postExt.getObjectId() > 0) {
					data.setVersion(postExt.getVersion());
				}
				return data;
			}
		} catch (TException e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		} finally {
			// do nothing
		}
		return data;
	}

	/**
	 * 抽取出摘要
	 * 
	 * @param postContent
	 * @return
	 */
	private List<String> createUpdateList(String postContent) {
		if (postContent != null) {
			int index = postContent.indexOf("<!--more-->");
			if (index > 0) {
				String summary = postContent.substring(0, index);
				return createUpdateList(summary);
			} else {
				List<String> summaries = new ArrayList<>();
				String[] summary = postContent.split("\n");
				if (summary.length > 0) {
					for (int i = 0; i < summary.length; i++) {
						String s = refineSummary(summary[i]);
						if (StringUtils.isNotNullOrEmpty(s)) {
							summaries.add(s);
						}
					}
				} else {
					String s = refineSummary(summary[0]);
					if (StringUtils.isNotNullOrEmpty(s)) {
						summaries.add(s);
					}
				}
				return summaries;
			}
		}
		return null;
	}

	/**
	 * 替换html标签
	 * 
	 * @param summary
	 * @return
	 */
	private String refineSummary(String summary) {
		if (StringUtils.isNotNullOrEmpty(summary)) {
			return summary.trim().replace("<del>", "").replace("</del>", "").replace("<blockquote>", "")
					.replace("</blockquote>", "").replace("<hr />", "").replace("<strong>", "").replace("</strong>", "")
					.trim();
		}
		return null;
	}

}
