// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package logbook.server.domain;

import java.util.List;
import logbook.server.domain.SkillAcquired;
import logbook.server.domain.SkillAcquiredDataOnDemand;
import logbook.server.domain.SkillAcquiredIntegrationTest;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

privileged aspect SkillAcquiredIntegrationTest_Roo_IntegrationTest {
    
    declare @type: SkillAcquiredIntegrationTest: @RunWith(SpringJUnit4ClassRunner.class);
    
    declare @type: SkillAcquiredIntegrationTest: @ContextConfiguration(locations = "classpath:/META-INF/spring/applicationContext*.xml");
    
    declare @type: SkillAcquiredIntegrationTest: @Transactional;
    
    @Autowired
    private SkillAcquiredDataOnDemand SkillAcquiredIntegrationTest.dod;
    
    @Test
    public void SkillAcquiredIntegrationTest.testCountSkillAcquireds() {
        Assert.assertNotNull("Data on demand for 'SkillAcquired' failed to initialize correctly", dod.getRandomSkillAcquired());
        long count = SkillAcquired.countSkillAcquireds();
        Assert.assertTrue("Counter for 'SkillAcquired' incorrectly reported there were no entries", count > 0);
    }
    
    @Test
    public void SkillAcquiredIntegrationTest.testFindSkillAcquired() {
        SkillAcquired obj = dod.getRandomSkillAcquired();
        Assert.assertNotNull("Data on demand for 'SkillAcquired' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'SkillAcquired' failed to provide an identifier", id);
        obj = SkillAcquired.findSkillAcquired(id);
        Assert.assertNotNull("Find method for 'SkillAcquired' illegally returned null for id '" + id + "'", obj);
        Assert.assertEquals("Find method for 'SkillAcquired' returned the incorrect identifier", id, obj.getId());
    }
    
    @Test
    public void SkillAcquiredIntegrationTest.testFindAllSkillAcquireds() {
        Assert.assertNotNull("Data on demand for 'SkillAcquired' failed to initialize correctly", dod.getRandomSkillAcquired());
        long count = SkillAcquired.countSkillAcquireds();
        Assert.assertTrue("Too expensive to perform a find all test for 'SkillAcquired', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
        List<SkillAcquired> result = SkillAcquired.findAllSkillAcquireds();
        Assert.assertNotNull("Find all method for 'SkillAcquired' illegally returned null", result);
        Assert.assertTrue("Find all method for 'SkillAcquired' failed to return any data", result.size() > 0);
    }
    
    @Test
    public void SkillAcquiredIntegrationTest.testFindSkillAcquiredEntries() {
        Assert.assertNotNull("Data on demand for 'SkillAcquired' failed to initialize correctly", dod.getRandomSkillAcquired());
        long count = SkillAcquired.countSkillAcquireds();
        if (count > 20) count = 20;
        int firstResult = 0;
        int maxResults = (int) count;
        List<SkillAcquired> result = SkillAcquired.findSkillAcquiredEntries(firstResult, maxResults);
        Assert.assertNotNull("Find entries method for 'SkillAcquired' illegally returned null", result);
        Assert.assertEquals("Find entries method for 'SkillAcquired' returned an incorrect number of entries", count, result.size());
    }
    
    @Test
    public void SkillAcquiredIntegrationTest.testFlush() {
        SkillAcquired obj = dod.getRandomSkillAcquired();
        Assert.assertNotNull("Data on demand for 'SkillAcquired' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'SkillAcquired' failed to provide an identifier", id);
        obj = SkillAcquired.findSkillAcquired(id);
        Assert.assertNotNull("Find method for 'SkillAcquired' illegally returned null for id '" + id + "'", obj);
        boolean modified =  dod.modifySkillAcquired(obj);
        Integer currentVersion = obj.getVersion();
        obj.flush();
        Assert.assertTrue("Version for 'SkillAcquired' failed to increment on flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }
    
    @Test
    public void SkillAcquiredIntegrationTest.testMergeUpdate() {
        SkillAcquired obj = dod.getRandomSkillAcquired();
        Assert.assertNotNull("Data on demand for 'SkillAcquired' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'SkillAcquired' failed to provide an identifier", id);
        obj = SkillAcquired.findSkillAcquired(id);
        boolean modified =  dod.modifySkillAcquired(obj);
        Integer currentVersion = obj.getVersion();
        SkillAcquired merged = obj.merge();
        obj.flush();
        Assert.assertEquals("Identifier of merged object not the same as identifier of original object", merged.getId(), id);
        Assert.assertTrue("Version for 'SkillAcquired' failed to increment on merge and flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }
    
    @Test
    public void SkillAcquiredIntegrationTest.testPersist() {
        Assert.assertNotNull("Data on demand for 'SkillAcquired' failed to initialize correctly", dod.getRandomSkillAcquired());
        SkillAcquired obj = dod.getNewTransientSkillAcquired(Integer.MAX_VALUE);
        Assert.assertNotNull("Data on demand for 'SkillAcquired' failed to provide a new transient entity", obj);
        Assert.assertNull("Expected 'SkillAcquired' identifier to be null", obj.getId());
        obj.persist();
        obj.flush();
        Assert.assertNotNull("Expected 'SkillAcquired' identifier to no longer be null", obj.getId());
    }
    
    @Test
    public void SkillAcquiredIntegrationTest.testRemove() {
        SkillAcquired obj = dod.getRandomSkillAcquired();
        Assert.assertNotNull("Data on demand for 'SkillAcquired' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'SkillAcquired' failed to provide an identifier", id);
        obj = SkillAcquired.findSkillAcquired(id);
        obj.remove();
        obj.flush();
        Assert.assertNull("Failed to remove 'SkillAcquired' with identifier '" + id + "'", SkillAcquired.findSkillAcquired(id));
    }
    
}