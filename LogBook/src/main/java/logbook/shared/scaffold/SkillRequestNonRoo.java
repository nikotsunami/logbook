package logbook.shared.scaffold;

import java.util.List;

import logbook.client.a_nonroo.app.client.SkillFilteredResultProxy;
import logbook.client.managed.proxy.ClassificationTopicProxy;
import logbook.client.managed.proxy.MainClassificationProxy;
import logbook.client.managed.proxy.SkillCommentProxy;
import logbook.client.managed.proxy.SkillProxy;
import logbook.client.managed.proxy.TopicProxy;
import logbook.server.domain.Skill;
import logbook.server.domain.SkillComment;

import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.RequestContext;
import com.google.web.bindery.requestfactory.shared.Service;

@Service(Skill.class)
public interface SkillRequestNonRoo extends RequestContext {
	abstract Request<List<Long>> findCountOfSkillBySkillLevel();

/*abstract Request<List<SkillProxy>> findSkillBySearchCriteria
	(int start, int max,Long studentId,Long mainClassificationId, Long classificationTopicId, Long topidId);*/

	abstract Request<SkillFilteredResultProxy> findSkillBySearchCriteria(int start, int max,Long studentId,Long mainClassificationId, Long classificationTopicId, Long topicId,String fulltextSearch,int chkAsc);
	
	abstract Request<String> retrieveHtmlFile(Long studentId,Long mainClassificationId, Long classificationTopicId, Long topicId,String fulltextSearch,int chkAsc);
	
	abstract Request<String> findProgressOfMainClassification(MainClassificationProxy mainClassification,Long studentId);
	
	abstract Request<String> findProgressOfClassificationTopic(ClassificationTopicProxy mainClassification,Long studentId);
	
	abstract Request<String> findProgressOfTopic(TopicProxy mainClassification,Long studentId);
	
	abstract Request<String> addCommnets(Long skillProxyId,Long studentId,String comment);
	

	//abstract Request<Boolean> isSkillAcquiredbyStudentAtFirstLevel(Long studentID,Long skillId, Long skillLevelID);
	
	abstract Request<String> getCommentOfStudentForSkill(Long skillId,Long studentId);
	
	

}
