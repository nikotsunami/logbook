package logbook.shared;

import com.google.gwt.core.shared.GWT;

import logbook.shared.i18n.LogBookConstants;


public enum SkillLevels {
	SOME_PRACTICLE_EXPERIENCE, ROUTINE, NONE;

	static LogBookConstants constants;
	static {
		if(GWT.isClient()) {
			constants = com.google.gwt.core.client.GWT.create(LogBookConstants.class);
		}
	}
	
	
	private static String SkillLevelValue;

	public static String getSkillLevels(SkillLevels skillLevel) {

		switch (skillLevel) {
		case SOME_PRACTICLE_EXPERIENCE:
			SkillLevelValue = constants.somePracticleExperiance();
			break;
		case ROUTINE:
			SkillLevelValue = constants.routine();
			break;
		default:
			SkillLevelValue = constants.noSkillAcquired();
		}
		return SkillLevelValue;
	}

}
