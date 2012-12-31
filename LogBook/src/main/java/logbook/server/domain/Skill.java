package logbook.server.domain;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.EntityManager;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.validation.constraints.Size;

import logbook.shared.SkillFilteredResult;
import logbook.shared.SkillLevels;

import org.apache.commons.io.FileUtils;
import org.hibernate.Query;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

import com.allen_sauer.gwt.log.client.Log;
import com.google.web.bindery.requestfactory.server.RequestFactoryServlet;

@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class Skill {

    @Size(max = 1024)
    private String description;

    @Size(max = 255)
    private String shortcut;

    @ManyToOne
    private Topic topic;

    @ManyToOne
    private SkillLevel skillLevel;
    
    @Size(max = 255)
    private String german_text;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "skill")
    private Set<SkillAcquired> skillsAcquired = new HashSet<SkillAcquired>();
    
    @ManyToMany(/*cascade = CascadeType.ALL,*/ mappedBy = "skill")
    private Set<Keyword> keywords = new HashSet<Keyword>();
    
    public static List<Long> findCountOfSkillBySkillLevel() 
    {
    	List<Long> listOfSkillBySkillLevel=new ArrayList<Long>();
    	listOfSkillBySkillLevel.add(findTotalSkillByLevel(1l));
    	listOfSkillBySkillLevel.add(findTotalSkillByLevel(2l));
    	return listOfSkillBySkillLevel;
         
    }
    public static Long findTotalSkillByLevel(long skillLevel)
    {
    	/*EntityManager em = entityManager();
    	String query="select count(s) from Skill as s where s.skillLevel.levelNumber= "+skillLevel;
        TypedQuery<Long> q = em.createQuery("select count(s) from Skill as s where s.skillLevel.levelNumber= "+skillLevel, Long.class);
        System.out.println("Total Skill Count Query String: " + query);
        return q.getSingleResult();		*/
    	
    	CriteriaBuilder criteriaBuilder = entityManager().getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
		Root<Skill> from = criteriaQuery.from(Skill.class);
					
		criteriaQuery.select(criteriaBuilder.count(from));
		
		Predicate skillLevelPredicate = criteriaBuilder.equal(from.get("skillLevel").get("levelNumber"),skillLevel);
		criteriaQuery.where(skillLevelPredicate);
		
		TypedQuery<Long> q = entityManager().createQuery(criteriaQuery);
		//System.out.println("Total Skill Count Query : " + q.unwrap(Query.class).getQueryString());
        return q.getSingleResult();		
		
    }
  public static SkillFilteredResult findSkillBySearchCriteria(int start, int max,Long studentId,Long mainClassificationId, Long classificationTopicId, Long topicId,String fulltextSearch,int chkAsc)
	{
	  
	  	//select s from Skill s,Topic t,ClassificationTopic c,MainClassification m  where s.topic=t.id and t.classificationTopic=c.id and c.mainClassification=m.id order by m.description,c.description,t.topicDescription,s.description
		/*EntityManager em = entityManager();
		String sql = "SELECT s FROM Skill As s where";
		
		if(topicId != null) //Topic
			sql = sql + " s.topic.id = " + topicId +" AND  " ;
		
		if(classificationTopicId != null) //ClassificationTopic
			sql = sql + " s.topic.classificationTopic.id = " + classificationTopicId +" AND  " ;
		
		if(mainClassificationId != null) // main classification
			sql = sql + " s.topic.classificationTopic.mainClassification.id = " + mainClassificationId +" AND  " ;

		sql = sql.substring(0, sql.length()-5);
		//sql = sql + " order by s.topic,s.topic.classificationTopic,s.topic.classificationTopic.mainClassification";
		sql = sql + " order by s.topic";
		//System.out.println("QUERY : " + sql);
		
		System.out.println("Query :" + sql);
		
		TypedQuery<Skill> result = em.createQuery(sql, Skill.class);
		result.setFirstResult(start);
		result.setMaxResults(max);
		List<Skill> response = result.getResultList();
		return response; */
		
	  Log.info("Asc :" + chkAsc);
	  Log.info("Start :" + start);
	  Log.info("Max :" + max);
	  
		
		CriteriaBuilder criteriaBuilder = entityManager().getCriteriaBuilder();
		CriteriaQuery<Skill> criteriaQuery = criteriaBuilder.createQuery(Skill.class);
		Root<Skill> from = criteriaQuery.from(Skill.class);
		CriteriaQuery<Skill> select = criteriaQuery.select(from);
		
		Join<Skill, Topic> join1 = from.join("topic");
		Join<Topic, ClassificationTopic> join2 = join1.join("classificationTopic");
		Join<ClassificationTopic, MainClassification> join3 = join2.join("mainClassification");
		
		
		//ListJoin<Skill, Topic> test = from.join("");
		
		//select.groupBy(from.get("topic").get("id"));
		select.orderBy(criteriaBuilder.asc(join3.get("description")), criteriaBuilder.asc(join2.get("description")), criteriaBuilder.asc(join1.get("topicDescription")),criteriaBuilder.asc(from.get("description")));
		
		/*if (chkAsc == 0)
			select.orderBy(criteriaBuilder.asc(join3.get("description")), criteriaBuilder.asc(join2.get("description")), criteriaBuilder.asc(join1.get("topicDescription")), criteriaBuilder.asc(from.get("description")), criteriaBuilder.asc(from.get("shortcut")));
		else if (chkAsc == 1)
			select.orderBy(criteriaBuilder.desc(from.get("shortcut")), criteriaBuilder.asc(join3.get("description")), criteriaBuilder.asc(join2.get("description")), criteriaBuilder.asc(join1.get("topicDescription")), criteriaBuilder.asc(from.get("description")));*/
		
		List<Predicate> predicateList = new ArrayList<Predicate>();
		
		Predicate predicate1 = null;
		Predicate predicate2 = null;
		Predicate predicate3 = null;
		Predicate andPredicate = null;
		
		
		if(topicId != null) //Topic
		{
			predicate1 = criteriaBuilder.equal(from.get("topic").get("id"), topicId);
			predicateList.add(predicate1);
			/*if (andPredicate == null)
				andPredicate = criteriaBuilder.and(predicate1);
			else
				andPredicate = criteriaBuilder.and(andPredicate, predicate1);*/
		}		
		
		if(classificationTopicId != null) //ClassificationTopic
		{
			//predicate2 = criteriaBuilder.equal(from.get("topic").get("classificationTopic").get("id"), classificationTopicId);
			predicate2 = criteriaBuilder.equal(join2.get("id"), classificationTopicId);
			predicateList.add(predicate2);
			/*if (andPredicate == null)
				andPredicate = criteriaBuilder.and(predicate2);
			else
				andPredicate = criteriaBuilder.and(andPredicate, predicate2);*/
		}		
		
		if(mainClassificationId != null) // main classification
		{
			//predicate3 = criteriaBuilder.equal(from.get("topic").get("classificationTopic").get("mainClassification").get("id"), mainClassificationId);
			predicate3 = criteriaBuilder.equal(join3.get("id"), mainClassificationId);
			predicateList.add(predicate3);
			/*if (andPredicate == null)
				andPredicate = criteriaBuilder.and(predicate3);
			else
				andPredicate = criteriaBuilder.and(andPredicate, predicate3);*/
		}
		
		if (fulltextSearch != "" && (!fulltextSearch.equals("")) && fulltextSearch != null)
		{
			Expression<String> skillDescExp = from.get("description");
			Expression<String> germanTestExp = from.get("german_text");
			
			
			/*String sql = "SELECT s FROM Keyword k join k.skill s WHERE k.name LIKE '%" + fulltextSearch + "%'";
			TypedQuery<Skill> q = entityManager().createQuery(sql, Skill.class);
			List<Skill> skillList = q.getResultList();
			
			System.out.println("SKILL QUERY : " + sql);
			System.out.println("SKILL LIST SIZE : " + skillList.size());*/
			
			Predicate pre1 = criteriaBuilder.like(skillDescExp, "%" + fulltextSearch + "%");
			Predicate pre2 = criteriaBuilder.like(germanTestExp, "%" + fulltextSearch + "%");
			
			Predicate orPre = criteriaBuilder.or(pre1, pre2/*, criteriaBuilder.in(from).value(getSkillIdList(skillList))*/);
			
			predicateList.add(orPre);
			/*if (andPredicate == null)
				andPredicate = criteriaBuilder.and(orPre);
			else
				andPredicate = criteriaBuilder.and(andPredicate, orPre);*/
		}
		
		
		if (predicateList.size() > 0)
			criteriaQuery.where(criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()])));
		
		TypedQuery<Skill> result = entityManager().createQuery(criteriaQuery);
		
		System.out.println("Critera query is :" + result.unwrap(Query.class).getQueryString());
		
		List<Skill> skillresultList  = new ArrayList<Skill>();
		
		int totalSize = 0;
		
		if (fulltextSearch != "" && (!fulltextSearch.equals("")) && fulltextSearch != null)
		{
			String sql = "SELECT s FROM Keyword k join k.skill s WHERE k.name LIKE '%" + fulltextSearch + "%'";
			TypedQuery<Skill> q = entityManager().createQuery(sql, Skill.class);
			List<Skill> skillKeywordList = q.getResultList();
			
			List<Skill> skillList = result.getResultList();
			
			Iterator<Skill> itr = skillKeywordList.iterator();
			while (itr.hasNext())
			{
				Skill skill = itr.next();
				
				if(!skillList.contains(skill))
					skillList.add(skill);
			}
			
			totalSize=skillList.size();
			
			if (totalSize > max)
			{
				 max = max + start;
				 
				 if (max > totalSize) max -= 1;
				 
				skillresultList = skillList.subList(start, max);
			}
			else
				skillresultList = skillList;
			
		}
		else
		{
			totalSize=result.getResultList().size();
			
			result.setFirstResult(start);
			
			result.setMaxResults(max);
			
			skillresultList  = result.getResultList();
			
			System.out.println("RESULTLISTSIZE : " + skillresultList.size());
		}
		
		//System.out.println("~~QUERY : " + result.unwrap(org.hibernate.Query.class).getQueryString());		
		
		/*if (chkAsc==0){
			
			//System.out.println("In side Asc ");
			Collections.sort(skillresultList,new Comparator<Skill>() {

				@Override
				public int compare(Skill o1, Skill o2) {
					return o1.getDescription().compareTo(o2.getDescription());
				}
				
				
			});
		}*/
		if (chkAsc == 1){
			
			//System.out.println("In side Desc ");
			Collections.sort(skillresultList,new Comparator<Skill>() {

				@Override
				public int compare(Skill o1, Skill o2) {
					
					if (o2.getTopic().getClassificationTopic().getMainClassification().getDescription().compareTo(o1.getTopic().getClassificationTopic().getMainClassification().getDescription()) != 0)
						return o2.getTopic().getClassificationTopic().getMainClassification().getDescription().compareTo(o1.getTopic().getClassificationTopic().getMainClassification().getDescription());
					
					if (o2.getTopic().getClassificationTopic().getDescription().compareTo(o1.getTopic().getClassificationTopic().getDescription()) != 0)
						return o2.getTopic().getClassificationTopic().getDescription().compareTo(o1.getTopic().getClassificationTopic().getDescription());		
					
					if (o2.getTopic().getTopicDescription().compareTo(o1.getTopic().getTopicDescription()) != 0)
						return o2.getTopic().getTopicDescription().compareTo(o1.getTopic().getTopicDescription());
					
					if (o2.getDescription().compareTo(o1.getDescription()) != 0)
						return o2.getDescription().compareTo(o1.getDescription());
					
					return o2.getShortcut().compareTo(o1.getShortcut());
				}
				
				
			});
		}
		
		List<SkillLevels> skillAcquiredList =findSkillAcquiredByStudents(skillresultList,studentId);
		//System.out.println("Skill Acquired size :" + skillAcquiredList.size());
		
		SkillFilteredResult finalresult = new SkillFilteredResult();
		
		/*for (Skill skill : skillresultList)
		{
			System.out.println("ID : " + skill.getId());
		}*/

		finalresult.setTotalSkill(totalSize);
		finalresult.setSkillList(skillresultList);
		finalresult.setSkilltLevelsAcquiredList(skillAcquiredList);
		
	
		
		/* calulate progress(5/20) for main/class topic/topic */
		
		/*List<Topic> topics=new ArrayList<Topic>();
		List<ClassificationTopic> ctopics=new ArrayList<ClassificationTopic>();
		List<MainClassification> mainClassifications=new ArrayList<MainClassification>();
		
		for(int i=0;i<skillresultList.size();i++)
		{
			Skill skill=skillresultList.get(i);
			Topic topic=skill.getTopic();
			ClassificationTopic ctopic=topic.getClassificationTopic();
			MainClassification mainClassification=ctopic.getMainClassification();
			
			
			if(i==0)
			{
				
				topics.add(topic);
				ctopics.add(ctopic);
				mainClassifications.add(mainClassification);
					
				
				
			}
			else
			{
				if( ( topic.getId() != skillresultList.get(i-1).getTopic().getId()))
				{
					topics.add(topic);
				}
				else if( ( ctopic.getId() != skillresultList.get(i-1).getTopic().getClassificationTopic().getId()))
					{
						ctopics.add(ctopic);
					}
				else if( ( mainClassification.getId() != skillresultList.get(i-1).getTopic().getClassificationTopic().getMainClassification().getId()))
				{
					mainClassifications.add(mainClassification);
				}
				}
			}
		
		List<String> mainClassificationProgress=new ArrayList<String>();
		List<String> mainClassificationkey=new ArrayList<String>();
		for(int i=0;i<mainClassifications.size();i++)
		{
			mainClassificationProgress.add(findProgressOfMainClassification( mainClassifications.get(i), studentId));
			mainClassificationkey.add("m"+mainClassifications.get(i));
		}
			 
		finalresult.setMainClassificationkey(mainClassificationkey);
		finalresult.setMainClassificationProgress(mainClassificationProgress);*/
		
		return finalresult;
	}  
    
  	public static String findProgressOfMainClassification(MainClassification mainClassification,Long studentId)
  	{
  		
  		List<ClassificationTopic> cts=mainClassification.getClassificationTopics();
  		Long aquiredSkillCount=0l;
  		Long totalSkill=0l;
  		for(int i=0;i<cts.size();i++)
  		{
  			ClassificationTopic ctopic=cts.get(i);
  			
  			List<Topic> topics=ctopic.getTopics();
  			
  			for(int j=0;j<topics.size();j++)
  			{
  				Topic topic=topics.get(j);
  				aquiredSkillCount=aquiredSkillCount+findTotalSkillAcquiredByTopicAndStudent(topic.getId(),studentId);
  				
  				totalSkill=totalSkill+topic.getSkills().size();
  			}
  		}
  		
  		return aquiredSkillCount+"/"+totalSkill.toString();
  	}
  	
  	public static String findProgressOfClassificationTopic(ClassificationTopic classificationTopic,Long studentId)
  	{
  		
  		List<Topic> ts=classificationTopic.getTopics();
  		Long aquiredSkillCount=0l;
  		Long totalSkill=0l;
  		for(int i=0;i<ts.size();i++)
  		{
  			Topic topic=ts.get(i);
  			
  			
  			

  				aquiredSkillCount=aquiredSkillCount+findTotalSkillAcquiredByTopicAndStudent(topic.getId(),studentId);
  				
  				totalSkill=totalSkill+topic.getSkills().size();
  			
  		}
  		
  		return aquiredSkillCount+"/"+totalSkill.toString();
  	}
  	
  	public static String findProgressOfTopic(Topic topic,Long studentId)
  	{
  		
  		
  		Long aquiredSkillCount=0l;
  		Integer totalSkill=topic.getSkills().size();
  		
  		aquiredSkillCount=aquiredSkillCount+findTotalSkillAcquiredByTopicAndStudent(topic.getId(),studentId);
  				

  		return aquiredSkillCount+"/"+totalSkill.toString();
  	}
  	
  	public static Long findTotalSkillAcquiredByTopicAndStudent(long topicId,long studentId)
    {
    	/*EntityManager em = entityManager();
    	//String query="select count(sa.skill) from SkillAcquired as sa , Skill as s where sa.skill=s.id and sa.student= "+studentId + " and sa.skill in ( select s.id from Skill as s where s.topic= "+topicId+" ) and sa.skillLevel.levelNumber<=s.skillLevel.levelNumber";
    	TypedQuery<Long> q = em.createQuery("select count(sa) from SkillAcquired as sa , Skill as s where sa.skill=s.id and sa.student= "+studentId + " and sa.skill in ( select s.id from Skill as s where s.topic= "+topicId+" ) and sa.skillLevel.levelNumber>=s.skillLevel.levelNumber", Long.class);
        //System.out.println("Query String: " + query + "=>" + q.getSingleResult());
        return q.getSingleResult();	*/
  		
  		Long count=SkillAcquired.findTotalSkillAcquiredByTopicAndStudent(topicId, studentId);
  		return count;
    }

  	
  	
    private static List<SkillLevels> findSkillAcquiredByStudents(List<Skill> skillresultList,Long studentId) {
    	
    	
    	List<SkillLevels> result = new ArrayList<SkillLevels>();
    	
    	for(Skill skill : skillresultList){
    		
    		
    		try {
    		    			
    			result.add(getSkillAcquiredbyStudentAtThisLevel(studentId, skill.getId()));
    			
    		}catch(Exception e){
    			System.out.println("Error" + e.getStackTrace());
    		}
    	}
    	
    	
    	
    	
    	return result;
    	
	
}
	public static String retrieveHtmlFile() {
    	
    	try {
    	String fileSeparator = System.getProperty("file.separator");
		String File_To_Convert =RequestFactoryServlet.getThreadLocalRequest().getSession().getServletContext().getRealPath(fileSeparator) +  "public/assignment_sp_1.htm";
		
		
		return  FileUtils.readFileToString(new File(File_To_Convert));
    	}catch(Exception e){
    		
    		return "No data found";
    	}
    }
    
    public static SkillLevels getSkillAcquiredbyStudentAtThisLevel(Long studentID,Long skillID){
    	
    	Log.info("Inside isSkillAcquiredbyStudentAtFirstLevel with student " + studentID + " skill : " +skillID );
    	EntityManager em = entityManager();
    	
    	String query = "SELECT sa.skillLevel from SkillAcquired as sa where sa.student="+studentID + " and sa.skill="+skillID ;
    	Log.info("Query is :" + query);
    	TypedQuery<SkillLevel> result = em.createQuery(query, SkillLevel.class);
    	if(result.getResultList().size()==0)
    	{
    		return SkillLevels.NONE;
    	}
    	else
    	{	
    		
    		 if(result.getResultList().get(0).getLevelNumber()==1)
    		 {
    			 return SkillLevels.SOME_PRACTICLE_EXPERIENCE;
    		 }
    		 else if(result.getResultList().get(0).getLevelNumber() ==2){
    			 return SkillLevels.ROUTINE;
    		 }
    	}
		
		return SkillLevels.NONE;
    	
    }
     public static Long findTotalSkillByTopic(long topicId)
    {
    	/*EntityManager em = entityManager();
        String query="select count(s.id) from Skill as s where s.topic= "+topicId;
    	TypedQuery<Long> q = em.createQuery("select count(s) from Skill as s where s.topic= "+topicId, Long.class);
        System.out.println("Query String: " + query);
        return q.getSingleResult();*/
    	
    	CriteriaBuilder criteriaBuilder = entityManager().getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
		Root<Skill> from = criteriaQuery.from(Skill.class);
		criteriaQuery.select(criteriaBuilder.count(from));				
		criteriaQuery.where(criteriaBuilder.equal(from.get("topic"), topicId));
		TypedQuery<Long> result = entityManager().createQuery(criteriaQuery);
		//System.out.println("~~QUERY +++ : " + result.unwrap(org.hibernate.Query.class).getQueryString());		
        return result.getSingleResult();
    }
private static String getSkillIdList(List<Skill> skillList){
		
		if (skillList == null || skillList.size() == 0) {
			Log.info("SkillList Return as null");
			return "";
		}
		Iterator<Skill> skillListIterator = skillList.iterator();
		StringBuilder skillId = new StringBuilder();
		skillId.append(",");
		while (skillListIterator.hasNext()) {
			Skill skill = skillListIterator.next();

			skillId.append(skill.getId().toString());
			if (skillListIterator.hasNext()) {
				skillId.append(" ,");
			}
		}
		return skillId.toString();
	}
    

}
