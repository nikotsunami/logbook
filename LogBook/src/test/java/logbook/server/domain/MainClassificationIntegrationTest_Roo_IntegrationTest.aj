// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package logbook.server.domain;

import java.util.List;
import logbook.server.domain.MainClassification;
import logbook.server.domain.MainClassificationDataOnDemand;
import logbook.server.domain.MainClassificationIntegrationTest;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

privileged aspect MainClassificationIntegrationTest_Roo_IntegrationTest {
    
    declare @type: MainClassificationIntegrationTest: @RunWith(SpringJUnit4ClassRunner.class);
    
    declare @type: MainClassificationIntegrationTest: @ContextConfiguration(locations = "classpath:/META-INF/spring/applicationContext*.xml");
    
    declare @type: MainClassificationIntegrationTest: @Transactional;
    
    @Autowired
    private MainClassificationDataOnDemand MainClassificationIntegrationTest.dod;
    
    @Test
    public void MainClassificationIntegrationTest.testCountMainClassifications() {
        Assert.assertNotNull("Data on demand for 'MainClassification' failed to initialize correctly", dod.getRandomMainClassification());
        long count = MainClassification.countMainClassifications();
        Assert.assertTrue("Counter for 'MainClassification' incorrectly reported there were no entries", count > 0);
    }
    
    @Test
    public void MainClassificationIntegrationTest.testFindMainClassification() {
        MainClassification obj = dod.getRandomMainClassification();
        Assert.assertNotNull("Data on demand for 'MainClassification' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'MainClassification' failed to provide an identifier", id);
        obj = MainClassification.findMainClassification(id);
        Assert.assertNotNull("Find method for 'MainClassification' illegally returned null for id '" + id + "'", obj);
        Assert.assertEquals("Find method for 'MainClassification' returned the incorrect identifier", id, obj.getId());
    }
    
    @Test
    public void MainClassificationIntegrationTest.testFindAllMainClassifications() {
        Assert.assertNotNull("Data on demand for 'MainClassification' failed to initialize correctly", dod.getRandomMainClassification());
        long count = MainClassification.countMainClassifications();
        Assert.assertTrue("Too expensive to perform a find all test for 'MainClassification', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
        List<MainClassification> result = MainClassification.findAllMainClassifications();
        Assert.assertNotNull("Find all method for 'MainClassification' illegally returned null", result);
        Assert.assertTrue("Find all method for 'MainClassification' failed to return any data", result.size() > 0);
    }
    
    @Test
    public void MainClassificationIntegrationTest.testFindMainClassificationEntries() {
        Assert.assertNotNull("Data on demand for 'MainClassification' failed to initialize correctly", dod.getRandomMainClassification());
        long count = MainClassification.countMainClassifications();
        if (count > 20) count = 20;
        int firstResult = 0;
        int maxResults = (int) count;
        List<MainClassification> result = MainClassification.findMainClassificationEntries(firstResult, maxResults);
        Assert.assertNotNull("Find entries method for 'MainClassification' illegally returned null", result);
        Assert.assertEquals("Find entries method for 'MainClassification' returned an incorrect number of entries", count, result.size());
    }
    
    @Test
    public void MainClassificationIntegrationTest.testFlush() {
        MainClassification obj = dod.getRandomMainClassification();
        Assert.assertNotNull("Data on demand for 'MainClassification' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'MainClassification' failed to provide an identifier", id);
        obj = MainClassification.findMainClassification(id);
        Assert.assertNotNull("Find method for 'MainClassification' illegally returned null for id '" + id + "'", obj);
        boolean modified =  dod.modifyMainClassification(obj);
        Integer currentVersion = obj.getVersion();
        obj.flush();
        Assert.assertTrue("Version for 'MainClassification' failed to increment on flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }
    
    @Test
    public void MainClassificationIntegrationTest.testMergeUpdate() {
        MainClassification obj = dod.getRandomMainClassification();
        Assert.assertNotNull("Data on demand for 'MainClassification' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'MainClassification' failed to provide an identifier", id);
        obj = MainClassification.findMainClassification(id);
        boolean modified =  dod.modifyMainClassification(obj);
        Integer currentVersion = obj.getVersion();
        MainClassification merged = obj.merge();
        obj.flush();
        Assert.assertEquals("Identifier of merged object not the same as identifier of original object", merged.getId(), id);
        Assert.assertTrue("Version for 'MainClassification' failed to increment on merge and flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }
    
    @Test
    public void MainClassificationIntegrationTest.testPersist() {
        Assert.assertNotNull("Data on demand for 'MainClassification' failed to initialize correctly", dod.getRandomMainClassification());
        MainClassification obj = dod.getNewTransientMainClassification(Integer.MAX_VALUE);
        Assert.assertNotNull("Data on demand for 'MainClassification' failed to provide a new transient entity", obj);
        Assert.assertNull("Expected 'MainClassification' identifier to be null", obj.getId());
        obj.persist();
        obj.flush();
        Assert.assertNotNull("Expected 'MainClassification' identifier to no longer be null", obj.getId());
    }
    
    @Test
    public void MainClassificationIntegrationTest.testRemove() {
        MainClassification obj = dod.getRandomMainClassification();
        Assert.assertNotNull("Data on demand for 'MainClassification' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'MainClassification' failed to provide an identifier", id);
        obj = MainClassification.findMainClassification(id);
        obj.remove();
        obj.flush();
        Assert.assertNull("Failed to remove 'MainClassification' with identifier '" + id + "'", MainClassification.findMainClassification(id));
    }
    
}
