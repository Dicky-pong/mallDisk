package com.pwx.mall.disk.common.util;

import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.orm.hibernate3.support.OpenSessionInViewFilter;


public class openSessionInViewFilter extends OpenSessionInViewFilter  {

    @Override
    protected Session getSession(SessionFactory sessionFactory) throws DataAccessResourceFailureException {
        Session session = SessionFactoryUtils.getSession(sessionFactory, true);
        session.setFlushMode(FlushMode.COMMIT);
        return session;
    }

    @Override
    protected void closeSession(Session session, SessionFactory sessionFactory) {
        session.flush();
        super.closeSession(session, sessionFactory);
    }
    
}
