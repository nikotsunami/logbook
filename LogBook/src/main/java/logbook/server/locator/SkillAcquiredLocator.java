package logbook.server.locator;

import com.google.web.bindery.requestfactory.shared.Locator;
import logbook.server.domain.SkillAcquired;
import org.springframework.roo.addon.gwt.RooGwtLocator;
import org.springframework.stereotype.Component;

@RooGwtLocator("logbook.server.domain.SkillAcquired")
@Component
public class SkillAcquiredLocator extends Locator<SkillAcquired, Long> {

    public SkillAcquired create(Class<? extends logbook.server.domain.SkillAcquired> clazz) {
        return new SkillAcquired();
    }

    public SkillAcquired find(Class<? extends logbook.server.domain.SkillAcquired> clazz, Long id) {
        return SkillAcquired.findSkillAcquired(id);
    }

    public Class<logbook.server.domain.SkillAcquired> getDomainType() {
        return SkillAcquired.class;
    }

    public Long getId(SkillAcquired skillAcquired) {
        return skillAcquired.getId();
    }

    public Class<java.lang.Long> getIdType() {
        return Long.class;
    }

    public Object getVersion(SkillAcquired skillAcquired) {
        return skillAcquired.getVersion();
    }
}
