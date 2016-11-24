/**
 * This class is generated by jOOQ
 */
package com.moseeker.baseorm.db.profiledb;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Table;
import org.jooq.impl.SchemaImpl;

import com.moseeker.baseorm.db.profiledb.tables.ProfileAttachment;
import com.moseeker.baseorm.db.profiledb.tables.ProfileAwards;
import com.moseeker.baseorm.db.profiledb.tables.ProfileBasic;
import com.moseeker.baseorm.db.profiledb.tables.ProfileCompleteness;
import com.moseeker.baseorm.db.profiledb.tables.ProfileCredentials;
import com.moseeker.baseorm.db.profiledb.tables.ProfileEducation;
import com.moseeker.baseorm.db.profiledb.tables.ProfileImport;
import com.moseeker.baseorm.db.profiledb.tables.ProfileIntention;
import com.moseeker.baseorm.db.profiledb.tables.ProfileIntentionCity;
import com.moseeker.baseorm.db.profiledb.tables.ProfileIntentionIndustry;
import com.moseeker.baseorm.db.profiledb.tables.ProfileIntentionPosition;
import com.moseeker.baseorm.db.profiledb.tables.ProfileLanguage;
import com.moseeker.baseorm.db.profiledb.tables.ProfileOther;
import com.moseeker.baseorm.db.profiledb.tables.ProfileProfile;
import com.moseeker.baseorm.db.profiledb.tables.ProfileProjectexp;
import com.moseeker.baseorm.db.profiledb.tables.ProfileSkill;
import com.moseeker.baseorm.db.profiledb.tables.ProfileWorkexp;
import com.moseeker.baseorm.db.profiledb.tables.ProfileWorks;
import com.moseeker.baseorm.db.profiledb.tables.SchemaMigrations;


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

	private static final long serialVersionUID = 253108219;

	/**
	 * The reference instance of <code>profiledb</code>
	 */
	public static final Profiledb PROFILEDB = new Profiledb();

	/**
	 * No further instances allowed
	 */
	private Profiledb() {
		super("profiledb");
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
			ProfileAwards.PROFILE_AWARDS,
			ProfileBasic.PROFILE_BASIC,
			ProfileCompleteness.PROFILE_COMPLETENESS,
			ProfileCredentials.PROFILE_CREDENTIALS,
			ProfileEducation.PROFILE_EDUCATION,
			ProfileImport.PROFILE_IMPORT,
			ProfileIntention.PROFILE_INTENTION,
			ProfileIntentionCity.PROFILE_INTENTION_CITY,
			ProfileIntentionIndustry.PROFILE_INTENTION_INDUSTRY,
			ProfileIntentionPosition.PROFILE_INTENTION_POSITION,
			ProfileLanguage.PROFILE_LANGUAGE,
			ProfileOther.PROFILE_OTHER,
			ProfileProfile.PROFILE_PROFILE,
			ProfileProjectexp.PROFILE_PROJECTEXP,
			ProfileSkill.PROFILE_SKILL,
			ProfileWorkexp.PROFILE_WORKEXP,
			ProfileWorks.PROFILE_WORKS,
			SchemaMigrations.SCHEMA_MIGRATIONS);
	}
}
