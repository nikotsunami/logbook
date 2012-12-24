package logbook.server.domain;

import static logbook.shared.scaffold.LogBookConstant.UNIQUE_ID;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import logbook.shared.Gender;
import logbook.shared.StudentStatus;
import logbook.shared.StudyYears;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

import com.google.web.bindery.requestfactory.server.RequestFactoryServlet;

@RooJavaBean
@RooToString
@RooJpaActiveRecord(finders = { "findStudentsByEmailEquals" })
public class Student {

    @Size(max = 45)
    private String studentId;

    @Size(max = 50)
    private String shib_id;

    /* @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}$")*/
    @Size(max = 40)   
    private String email;

    @Enumerated
    private Gender gender;

    @NotNull
    @Size(max = 40)
    private String name;

    @NotNull
    @Size(max = 40)
    private String preName;

    @Enumerated
    private StudentStatus studentStatus;

    @Enumerated
    private StudyYears studyYear;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "student")
    private Set<SkillAcquired> skillAcquired = new HashSet<SkillAcquired>();

    public static logbook.server.domain.Student findStudentFromSession() 
    {
        HttpSession session = RequestFactoryServlet.getThreadLocalRequest().getSession();
        String mailId = (String) session.getAttribute(UNIQUE_ID);
        System.out.println("Mail id: " + mailId);
        Student student=Student.findStudentsByEmailEquals(mailId).getResultList().get(0);
        return student;
    }
}