/**
 * This class is generated by jOOQ
 */
package com.moseeker.db.profiledb;


import com.moseeker.db.profiledb.tables.ProfileAttachment;
import com.moseeker.db.profiledb.tables.ProfileBasic;
import com.moseeker.db.profiledb.tables.ProfileEducation;
import com.moseeker.db.profiledb.tables.ProfileEducationExt;
import com.moseeker.db.profiledb.tables.ProfileExt;
import com.moseeker.db.profiledb.tables.ProfileImport;
import com.moseeker.db.profiledb.tables.ProfileIntention;
import com.moseeker.db.profiledb.tables.ProfileInternship;
import com.moseeker.db.profiledb.tables.ProfileLanguage;
import com.moseeker.db.profiledb.tables.ProfileProfile;
import com.moseeker.db.profiledb.tables.ProfileProjectexp;
import com.moseeker.db.profiledb.tables.ProfileReward;
import com.moseeker.db.profiledb.tables.ProfileSchooljob;
import com.moseeker.db.profiledb.tables.ProfileSkill;
import com.moseeker.db.profiledb.tables.ProfileTraining;
import com.moseeker.db.profiledb.tables.ProfileWorkexp;
import com.moseeker.db.profiledb.tables.ProfileWorks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Table;
import org.jooq.impl.SchemaImpl;


/**
 * This class is generated by jOOQ.
 */
@Generated(
	value = {
		"http://www.jooq.org",
		"jOOQ version:3.7.3"
	},
	comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Profiledb extends SchemaImpl {

	private static final long serialVersionUID = 1984124122;

	/**
	 * The reference instance of <code>profileDB</code>
	 */
	public static final Profiledb PROFILEDB = new Profiledb();

	/**
	 * No further instances allowed
	 */
	private Profiledb() {
		super("profileDB");
	}

	@Override
	public final List<Table<?>> getTables() {
		List result = new ArrayList();
		result.addAll(getTables0());
		return result;
	}

	private final List<Table<?>> getTables0() {
		return Arrays.<Table<?>>asList(
			ProfileAttachment.PROFILE_ATTACHMENT,
			ProfileBasic.PROFILE_BASIC,
			ProfileEducation.PROFILE_EDUCATION,
			ProfileEducationExt.PROFILE_EDUCATION_EXT,
			ProfileExt.PROFILE_EXT,
			ProfileImport.PROFILE_IMPORT,
			ProfileIntention.PROFILE_INTENTION,
			ProfileInternship.PROFILE_INTERNSHIP,
			ProfileLanguage.PROFILE_LANGUAGE,
			ProfileProfile.PROFILE_PROFILE,
			ProfileProjectexp.PROFILE_PROJECTEXP,
			ProfileReward.PROFILE_REWARD,
			ProfileSchooljob.PROFILE_SCHOOLJOB,
			ProfileSkill.PROFILE_SKILL,
			ProfileTraining.PROFILE_TRAINING,
			ProfileWorkexp.PROFILE_WORKEXP,
			ProfileWorks.PROFILE_WORKS);
	}
}
