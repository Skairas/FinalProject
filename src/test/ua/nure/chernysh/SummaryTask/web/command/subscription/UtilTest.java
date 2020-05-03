package ua.nure.chernysh.SummaryTask.web.command.subscription;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ua.nure.chernysh.SummaryTask.db.dao.SubscriptionDAO;
import ua.nure.chernysh.SummaryTask.entity.Subscription;
import ua.nure.chernysh.SummaryTask.exception.DBException;

import java.sql.Date;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UtilTest {
    private long monthMillis = 2592000000L;
    private Long id = 1L;

    @Mock
    SubscriptionDAO mockSubDAO;

    @Test
    public void invalidateSubscriptionTest1() throws DBException {
        when(mockSubDAO.getSubscriptionByID(id)).thenReturn(new Subscription());

        Date ending = new Date((new java.util.Date()).getTime());
        ending.setTime(ending.getTime() - monthMillis);
        String endingDate = ending.toString();

        assertTrue(Util.invalidateSubscription(id, endingDate, mockSubDAO));
    }

    @Test
    public void invalidateSubscriptionTest2() throws DBException {
        when(mockSubDAO.getSubscriptionByID(id)).thenReturn(new Subscription());

        Date ending = new Date((new java.util.Date()).getTime());
        ending.setTime(ending.getTime() + monthMillis);
        String endingDate = ending.toString();

        assertFalse(Util.invalidateSubscription(id, endingDate, mockSubDAO));
    }
}