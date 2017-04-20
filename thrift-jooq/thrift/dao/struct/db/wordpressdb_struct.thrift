namespace java com.moseeker.thrift.gen.dao.struct.wordpressdb
namespace py thrift_gen.gen.dao.struct.wordpressdb


struct WordpressCommentmetaDO {

	1: optional i32 metaId,	//null
	2: optional i32 commentId,	//null
	3: optional string metaKey,	//null
	4: optional string metaValue	//null

}


struct WordpressCommentsDO {

	1: optional i32 commentId,	//null
	2: optional i32 commentPostId,	//null
	3: optional string commentAuthor,	//null
	4: optional string commentAuthorEmail,	//null
	5: optional string commentAuthorUrl,	//null
	6: optional string commentAuthorIp,	//null
	7: optional string commentDate,	//null
	8: optional string commentDateGmt,	//null
	9: optional string commentContent,	//null
	10: optional i32 commentKarma,	//null
	11: optional string commentApproved,	//null
	12: optional string commentAgent,	//null
	13: optional string commentType,	//null
	14: optional i32 commentParent,	//null
	15: optional i32 userId	//null

}


struct WordpressLinksDO {

	1: optional i32 linkId,	//null
	2: optional string linkUrl,	//null
	3: optional string linkName,	//null
	4: optional string linkImage,	//null
	5: optional string linkTarget,	//null
	6: optional string linkDescription,	//null
	7: optional string linkVisible,	//null
	8: optional i32 linkOwner,	//null
	9: optional i32 linkRating,	//null
	10: optional string linkUpdated,	//null
	11: optional string linkRel,	//null
	12: optional string linkNotes,	//null
	13: optional string linkRss	//null

}


struct WordpressOptionsDO {

	1: optional i32 optionId,	//null
	2: optional string optionName,	//null
	3: optional string optionValue,	//null
	4: optional string autoload	//null

}


struct WordpressPostmetaDO {

	1: optional i32 metaId,	//null
	2: optional i32 postId,	//null
	3: optional string metaKey,	//null
	4: optional string metaValue	//null

}


struct WordpressPostsDO {

	1: optional i32 id,	//null
	2: optional i32 postAuthor,	//null
	3: optional string postDate,	//null
	4: optional string postDateGmt,	//null
	5: optional string postContent,	//null
	6: optional string postTitle,	//null
	7: optional string postExcerpt,	//null
	8: optional string postStatus,	//null
	9: optional string commentStatus,	//null
	10: optional string pingStatus,	//null
	11: optional string postPassword,	//null
	12: optional string postName,	//null
	13: optional string toPing,	//null
	14: optional string pinged,	//null
	15: optional string postModified,	//null
	16: optional string postModifiedGmt,	//null
	17: optional string postContentFiltered,	//null
	18: optional i32 postParent,	//null
	19: optional string guid,	//null
	20: optional i32 menuOrder,	//null
	21: optional string postType,	//null
	22: optional string postMimeType,	//null
	23: optional i32 commentCount	//null

}


struct WordpressTermRelationshipsDO {

	1: optional i32 objectId,	//null
	2: optional i32 termTaxonomyId,	//null
	3: optional i32 termOrder	//null

}


struct WordpressTermTaxonomyDO {

	1: optional i32 termTaxonomyId,	//null
	2: optional i32 termId,	//null
	3: optional string taxonomy,	//null
	4: optional string description,	//null
	5: optional i32 parent,	//null
	6: optional i32 count	//null

}


struct WordpressTermmetaDO {

	1: optional i32 metaId,	//null
	2: optional i32 termId,	//null
	3: optional string metaKey,	//null
	4: optional string metaValue	//null

}


struct WordpressTermsDO {

	1: optional i32 termId,	//null
	2: optional string name,	//null
	3: optional string slug,	//null
	4: optional i32 termGroup	//null

}


struct WordpressUserPostDO {

	1: optional i32 userId,	//用户编号
	2: optional i32 objectId	//文章编号

}


struct WordpressUsermetaDO {

	1: optional i32 umetaId,	//null
	2: optional i32 userId,	//null
	3: optional string metaKey,	//null
	4: optional string metaValue	//null

}


struct WordpressUsersDO {

	1: optional i32 id,	//null
	2: optional string userLogin,	//null
	3: optional string userPass,	//null
	4: optional string userNicename,	//null
	5: optional string userEmail,	//null
	6: optional string userUrl,	//null
	7: optional string userRegistered,	//null
	8: optional string userActivationKey,	//null
	9: optional i32 userStatus,	//null
	10: optional string displayName	//null

}
