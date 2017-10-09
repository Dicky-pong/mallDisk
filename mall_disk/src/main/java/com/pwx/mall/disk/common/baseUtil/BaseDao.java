package com.pwx.mall.disk.common.baseUtil;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Component;

import com.pwx.mall.disk.common.pagination.Page;
import com.pwx.mall.disk.common.util.DataToConversion;


/**
 * 
     * 此类描述的是：  与数据的简要操作
     * @author: Tanchao  
     * @version: 2015-5-21 下午12:39:19
 */
@Component
public class BaseDao {
	
	private HibernateTemplate hibernateTemplate;
	
	@Resource(name="sessionFactory")
    private SessionFactory sessionFactory;

	protected final Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }
	
	// 获取自增长
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String getAutoIncrement(final String tablename) {
		return hibernateTemplate.execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                Query query = session.createSQLQuery("SELECT AUTO_INCREMENT FROM information_schema.tables "
                		+ "WHERE table_name = '"+tablename+"'");
                List list = query.list();
                if(list!=null && list.size()>0){
                	return String.valueOf(list.get(0));
                }
                return null;
            }
        });
	}
	
	// 添加
	public String add(Object o) {
	    Serializable pKey = hibernateTemplate.save(o);
	    return pKey.toString();
	}

	// 修改
	public void update(Object o) {
		hibernateTemplate.update(o);
	}
	
	//持久化
	public void flush(){
		hibernateTemplate.flush();
	}

	// 修改(在session中已存在这个对象的修改)
	public void merge(Object o) {
		hibernateTemplate.merge(o);
	}

	// 根据ID获取对象
	public Object getById(Class<?> c, Serializable id) {
		Object o = hibernateTemplate.get(c, id);
		hibernateTemplate.evict(o);
		return o;
	}

	// 删除对象
	public void delete(Object o) {
		hibernateTemplate.delete(o);
	}

	// 根据ID删除对象
	public void deleteById(Class<?> c, Serializable id) {
		delete(getById(c, id));
	}

	// 根据类的用户名去获取list集合信息
	public List<?> getAll(Class<?> c) {
		return hibernateTemplate.find("from " + c.getName());
	}
	
	
	public void batchAdd(Collection entities) {
		hibernateTemplate.saveOrUpdateAll(entities);
	}
	
	/**
	 * 
	 * TODO 根据hql 与 maps 条件进行object查询.
	 * @param hql   查询语句
	 * @param map  查询条件
	 * @return
	 */
	public Object queryObject(final String hql,final Map<String,String> map){
	    return hibernateTemplate.executeFind(new HibernateCallback<Object>() {

            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                Query query = session.createQuery(hql);
                if (map != null){
                    Set<String> keSet=map.keySet();  
                    for (Iterator<String> iterator = keSet.iterator(); iterator.hasNext();) {  
                        String str = iterator.next();
                        query.setParameter(str, map.get(str));
                    }
                }
                return query.list();
            }
        });
	}
	
	
	/**
     * 
     * TODO 根据hql 与 maps 条件进行object查询.
     * @param hql   查询语句
     * @param map  查询条件
     * @return
     */
    public Object queryObjectByIn(final String hql,final Map<String,String> map){
        return hibernateTemplate.executeFind(new HibernateCallback<Object>() {

            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                Query query = session.createQuery(hql);
                if (map != null){
                    Set<String> keSet=map.keySet();  
                    for (Iterator<String> iterator = keSet.iterator(); iterator.hasNext();) {  
                        String str = iterator.next();
                        if("arry".equals(str)){
                            String s = (String) map.get(str);
                            String[] strArry = s.split(",");
                            query.setParameterList(str, strArry);
                        }else{
                            query.setParameter(str, map.get(str));
                        }
                    }
                }
                return query.list();
            }
        });
    }
    
    /**
	 * 
	 * TODO 根据hql 与 maps 条件进行object查询.
	 * @param hql   查询语句
	 * @param map  查询条件
	 * @return
	 */
	public Object queryObjectByObj(final String hql,final Map<String,Object> map){
	    return hibernateTemplate.executeFind(new HibernateCallback<Object>() {

            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                Query query = session.createQuery(hql);
                if (map != null){
                    Set<String> keSet=map.keySet();  
                    for (Iterator<String> iterator = keSet.iterator(); iterator.hasNext();) {  
                        String str = iterator.next();
                        Object obj = map.get(str);
                        if(str.endsWith("_arry")){
                        	String s = (String) map.get(str);
                            String[] strArry = s.split(",");
                            query.setParameterList(str, strArry);
                        }else if(obj instanceof Integer ){
                        	query.setParameter(str,
                                    Integer.parseInt(map.get(str).toString()));
                        }else if(obj instanceof Double ){
                        	query.setParameter(str,
                        			Double.parseDouble(map.get(str).toString()));
                        }else{
                        	query.setParameter(str,obj);
                        }
                    }
                }
                return query.list();
            }
        });
	}
	
	
	
	
	/**
	 * 根据类去找所有条数
	 * @param c
	 * @param map
	 * @param where
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
    public Object getUnique(final Class<?> c, final Map<String, String> map,final String where) {
        return hibernateTemplate.execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
            	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Query query = session.createQuery("select count(*) from "+c.getName()+" "+where);
                if (map != null && map.size()>0){
                    Set<String> keSet=map.keySet();  
                    for (Iterator<String> iterator = keSet.iterator(); iterator.hasNext();) {  
                        String str = iterator.next();
                        //query.setParameter(str, map.get(str));
                        if (str.equals("myOrderState") || str.equals("payType")) {
                            query.setParameter(str,
                                    Integer.parseInt(map.get(str)));
                        }else if(str.equals("newsetCommodStartDate")){
                            try {
                                query.setParameter(str,
                                    sdf.parse((map.get(str))));
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }else if(str.equals("newsetCommodEndDate")){
                            try {
                                query.setParameter(str,
                                    sdf.parse((map.get(str))));
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }else{
                            query.setParameter(str, map.get(str));
                        }
                    }
                }
                return query.uniqueResult();
            }
        });
    }
	
	/**
	 * 根据sql去找表中所有记录数
	 * @param sql
	 * @param map
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
    public Object getUnique(final String sql,final Map<String, String> map) {
        return hibernateTemplate.execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                Query query = session.createQuery(sql);
                if (map != null){
                    Set<String> keSet=map.keySet();  
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    for (Iterator<String> iterator = keSet.iterator(); iterator.hasNext();) {  
                        String str = iterator.next();
                        if(str.equals("startDate")){
                            try {
                                query.setParameter(str,
                                    sdf.parse((map.get(str))));
                            } catch (ParseException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }else if(str.equals("endDate")){
                            try {
                                query.setParameter(str,
                                    sdf.parse((map.get(str))));
                            } catch (ParseException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }else if(str.equals("beginMoney")){
                            query.setParameter(str, DataToConversion.conversionBigDecimal(map.get(str)));
                        }else if(str.equals("endMoney")){
                            query.setParameter(str, DataToConversion.conversionBigDecimal(map.get(str)));
                        }else{
                            query.setParameter(str, map.get(str));
                        }
                        
                    }
                }
                return query.uniqueResult();
            }
        });
    }

	// 批量修改
	public void bulkUpdate(String hql, Object... objects) {
		hibernateTemplate.bulkUpdate(hql, objects);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Object getUnique(final String hql, final Object... objects) {
		return hibernateTemplate.execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(hql);
				if (objects != null){
					for (int i = 0; i < objects.length; i++){
						query.setParameter(i, objects[i]);
					}
				}
				return query.uniqueResult();
			}
		});
	}

	// 分页查询
	/*@SuppressWarnings("rawtypes")
	public List<?> pageQuery(final String hql, final Integer page, final Integer size, final Object... objects) {
		return hibernateTemplate.executeFind(new HibernateCallback() {

			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(hql);
				if (objects != null)
					for (int i = 0; i < objects.length; i++){
						query.setParameter(i, objects[i]);
					}
				if (page != null && size != null)
					query.setFirstResult((page - 1) * size).setMaxResults(size);
				return query.list();
			}
		});
	}*/
	
	@SuppressWarnings("rawtypes")
    public List<?> queryPage(final String hql, final Integer page, final Integer size, final Map<String,String> map) {
        return hibernateTemplate.executeFind(new HibernateCallback() {

            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                //声明Page分页Object
                Page pageTool = new Page();
                List<Page> list = new ArrayList<Page>();
                Query query = session.createQuery(hql);
                if (map != null){
                    Set<String> keSet=map.keySet();  
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    for (Iterator<String> iterator = keSet.iterator(); iterator.hasNext();) {  
                        String str = iterator.next();
                        //query.setParameter(str, map.get(str));
                        if (str.equals("myOrderState") || str.equals("payType") || str.equals("couponsType") || str.equals("status")
                        		|| str.equals("couponsWay")|| str.equals("pushStoreActivity") || str.equals("polyPreferential")) {
                            query.setParameter(str,
                                    Integer.parseInt(map.get(str)));
                        }else if(str.equals("newsetCommodStartDate")){
                            try {
                                query.setParameter(str,
                                    sdf.parse((map.get(str))));
                            } catch (ParseException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }else if(str.equals("newsetCommodEndDate")){
                            try {
                                query.setParameter(str,
                                    sdf.parse((map.get(str))));
                            } catch (ParseException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }else{
                            query.setParameter(str, map.get(str));
                        }
                    }
                }
                
                if(page != null && size != null){
                    query.setFirstResult((page - 1) * size).setMaxResults(size);
                    pageTool.setPageIndex(page);
                    pageTool.setPageSize(size);
                    List pagelist = query.list();
                    if(pagelist!=null){
                        pageTool.setData(query.list());
                    }else{
                        pageTool.setData(new ArrayList());
                    }
                }else{
                	return query.list();
                }
                list.add(pageTool);
                return list;
            }
        });
    }
	
	@SuppressWarnings("rawtypes")
    public List<?> pageQuery(final String hql, final Integer page, final Integer size, final Map<String,String> map) {
        return hibernateTemplate.executeFind(new HibernateCallback() {

            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                Query query = session.createQuery(hql);
                if (map != null){
                    Set<String> keSet=map.keySet();  
                    for (Iterator<String> iterator = keSet.iterator(); iterator.hasNext();) {  
                        String str = iterator.next();
                        query.setParameter(str, map.get(str));
                    }
                }
                if (page != null && size != null)
                    query.setFirstResult((page - 1) * size).setMaxResults(size);
                return query.list();
            }
        });
    }
	
	@SuppressWarnings("rawtypes")
    public List<?> queryByIn(final String hql, final Integer page, final Integer size, final Map<String,String> map) {
        return hibernateTemplate.executeFind(new HibernateCallback() {

            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                Page pageTool = new Page();
                List<Page> list = new ArrayList<Page>();
                //声明Page分页Object
                Query query = session.createQuery(hql);
                if (map != null){
                    Set<String> keSet=map.keySet();  
                    for (Iterator<String> iterator = keSet.iterator(); iterator.hasNext();) {  
                        String str = iterator.next();
                        //List l = map.get(str);
                        if("arry".equals(str)){
                            String s = (String) map.get(str);
                            String[] strArry = s.split(",");
                            query.setParameterList(str, strArry);
                        }else if(str.endsWith("_arry")){
                        	String s = (String) map.get(str);
                            String[] strArry = s.split(",");
                            query.setParameterList(str, strArry);
                        }else{
                            query.setParameter(str, map.get(str));
                        }
                        
                    }
                }
                
                if(page != null && size != null){
                    query.setFirstResult((page - 1) * size).setMaxResults(size);
                    pageTool.setPageIndex(page);
                    pageTool.setPageSize(size);
                    List pagelist = query.list();
                    if(pagelist!=null){
                        pageTool.setData(query.list());
                    }else{
                        pageTool.setData(new ArrayList());
                    }
                }
                list.add(pageTool);
                return list;
            }
        });
    }
	
	@SuppressWarnings("rawtypes")
    public List<?> queryByObj(final String hql, final Integer page, final Integer size, final Map<String,Object> map) {
        return hibernateTemplate.executeFind(new HibernateCallback() {

            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                Page pageTool = new Page();
                List<Page> list = new ArrayList<Page>();
                //声明Page分页Object
                Query query = session.createQuery(hql);
                if (map != null){
                    Set<String> keSet=map.keySet();  
                    for (Iterator<String> iterator = keSet.iterator(); iterator.hasNext();) {  
                        String str = iterator.next();
                        Object obj = map.get(str);
                        if(str.endsWith("_arry")){
                        	String s = (String) map.get(str);
                            String[] strArry = s.split(",");
                            query.setParameterList(str, strArry);
                        }else if(obj instanceof Integer ){
                        	query.setParameter(str,
                                    Integer.parseInt(map.get(str).toString()));
                        }else if(obj instanceof Double ){
                        	query.setParameter(str,
                        			Double.parseDouble(map.get(str).toString()));
                        }else{
                        	query.setParameter(str,obj);
                        }
                        
                    }
                }
                
                if(page != null && size != null){
                    query.setFirstResult((page - 1) * size).setMaxResults(size);
                    pageTool.setPageIndex(page);
                    pageTool.setPageSize(size);
                    List pagelist = query.list();
                    if(pagelist!=null){
                        pageTool.setData(query.list());
                    }else{
                        pageTool.setData(new ArrayList());
                    }
                }else{
                	return query.list();
                }
                list.add(pageTool);
                return list;
            }
        });
    }
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
    public Object getUniqueByIn(final Class<?> c, final Map<String, String> map,final String where) {
        return hibernateTemplate.execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                String hql = "select count(*) from "+c.getName()+" "+where;
                Query query = session.createQuery(hql);
                if (map != null && map.size()>0){
                    Set<String> keSet=map.keySet();  
                    for (Iterator<String> iterator = keSet.iterator(); iterator.hasNext();) {  
                        String str = iterator.next();
                        if("arry".equals(str)){
                            String s = (String) map.get(str);
                            String[] strArry = s.split(",");
                            query.setParameterList(str, strArry);
                        }else{
                            query.setParameter(str, map.get(str));
                        }
                    }
                }
                return query.uniqueResult();
            }
        });
    }
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Object getUniqueByObj(final String hql, final Map<String, Object> map) {
		return hibernateTemplate.execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(hql);
				if (map != null){
                    Set<String> keSet=map.keySet();  
                    for (Iterator<String> iterator = keSet.iterator(); iterator.hasNext();) {  
                        String str = iterator.next();
                        Object obj = map.get(str);
                        if(str.endsWith("_arry")){
                        	String s = (String) map.get(str);
                            String[] strArry = s.split(",");
                            query.setParameterList(str, strArry);
                        }else if(obj instanceof Integer ){
                        	query.setParameter(str,
                                    Integer.parseInt(map.get(str).toString()));
                        }else if(obj instanceof Double ){
                        	query.setParameter(str,
                        			Double.parseDouble(map.get(str).toString()));
                        }else{
                        	query.setParameter(str,obj);
                        }
                    }
                }
				return query.uniqueResult();
			}
		});
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
    public Object getUniqueByObj(final String className, final Map<String, Object> map,final String where) {
        return hibernateTemplate.execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                String hql = "select count(*) from "+className+" where 1=1 "+where;
                Query query = session.createQuery(hql);
                if (map != null){
                    Set<String> keSet=map.keySet();  
                    for (Iterator<String> iterator = keSet.iterator(); iterator.hasNext();) {  
                        String str = iterator.next();
                        Object obj = map.get(str);
                        if(str.endsWith("_arry")){
                        	String s = (String) map.get(str);
                            String[] strArry = s.split(",");
                            query.setParameterList(str, strArry);
                        }else if(obj instanceof Integer ){
                        	query.setParameter(str,
                                    Integer.parseInt(map.get(str).toString()));
                        }else if(obj instanceof Double ){
                        	query.setParameter(str,
                        			Double.parseDouble(map.get(str).toString()));
                        }else{
                        	query.setParameter(str,obj);
                        }
                    }
                }
                return query.list();
            }
        });
    }

	// 不分页查询
	public List<?> pageQuery(String hql, Object... objects) {
		return pageQuery(hql, null, null, objects);
	}
	
	public List<?> pageQuery(String hql, Map<String,String> maps) {
        return pageQuery(hql, null, null, maps);
    }

	public void save(Object o) {
		if (o != null)
			hibernateTemplate.saveOrUpdate(o);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void update(final String hql, final Map<String,Object> map){
        hibernateTemplate.execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                Query query = session.createQuery(hql);
                
                if (map != null){
                    Set<String> keSet=map.keySet();  
                    for (Iterator<String> iterator = keSet.iterator(); iterator.hasNext();) {  
                        String str = iterator.next();
                        query.setParameter(str, map.get(str));
                    }
                }
                return query.executeUpdate();
            }
        });
    }
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void update(final String hql, final Object... objects){
		hibernateTemplate.execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(hql);
				if (objects != null)
					for (int i = 0; i < objects.length; i++)
						query.setParameter(i, objects[i]);
				return query.executeUpdate();
			}
		});
	}
	
	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}
	@Resource
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Object executeSQL(String sql){
	    final String tempsql = sql;
	    return hibernateTemplate.execute(new HibernateCallback(){
	        public Object doInHibernate(Session session) throws HibernateException{
	            
	            return session.createQuery(tempsql).executeUpdate();
	        }
	    });
	}
	
	@SuppressWarnings("rawtypes")
	public List<?> executeSQLReturnlist(final Class<?> c,String sql){
        final String tempsql = sql;
        return (List<?>) hibernateTemplate.execute(new HibernateCallback<Object>(){
            public Object doInHibernate(Session session) throws HibernateException{
                Query query = session.createSQLQuery(tempsql).addEntity(c);
                List list = query.list();
                return list;//session.createSQLQuery(tempsql).addEntity(AccessResource.class).list();
            }
        });
    }
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Object[]> executeSQL2(String sql){
	    final String tempsql = sql;
	    return hibernateTemplate.execute(new HibernateCallback(){
	        public List doInHibernate(Session session) throws HibernateException{
	            
	            return session.createSQLQuery(tempsql).list();
	        }
	    });
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
    public List<Object[]> executeSQLReturnList(String sql){
        final String tempsql = sql;
        return hibernateTemplate.execute(new HibernateCallback(){
            public List doInHibernate(Session session) throws HibernateException{
                
                return session.createSQLQuery(tempsql).list();
            }
        });
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Object[]> executeSQLReturnList(final String sql, final Map<String, String> map){
        final String tempsql = sql;
        return hibernateTemplate.execute(new HibernateCallback(){
            public List doInHibernate(Session session) throws HibernateException{
            	Query query = session.createSQLQuery(tempsql);
            	if (map != null){
                    Set<String> keSet=map.keySet();  
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    for (Iterator<String> iterator = keSet.iterator(); iterator.hasNext();) {  
                        String str = iterator.next();
                        if(str.contains("startDate")){
                            try {
                                query.setParameter(str,
                                    sdf.parse((map.get(str))));
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }else if(str.contains("endDate")){
                            try {
                                query.setParameter(str,
                                    sdf.parse((map.get(str))));
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }else if(str.equals("startNum") || str.equals("pageSize")){
                        	query.setParameter(str, Integer.parseInt(map.get(str)));
                        }else if(str.contains("moneyStart") || str.equals("moneyEnd")){
                        	query.setParameter(str, new BigDecimal(map.get(str)));
                        }else{
                            query.setParameter(str, map.get(str));
                        }
                    }
                }
                return query.list();
            }
        });
    }
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Object[]> executeSQLReturnList(final String sql, final List<Object> params){
        return hibernateTemplate.execute(new HibernateCallback(){
            public List doInHibernate(Session session) throws HibernateException{
            	SQLQuery query = session.createSQLQuery(sql);
            	for(int i=0;i<params.size();i++){
            		query.setString(i, params.get(i).toString());
            	}
                return query.list();
            }
        });
    }
	
	@SuppressWarnings("rawtypes")
    public List<?> executeSQLPage(String sql,final Integer page, final Integer size, final Map<String,String> map){
        final String tempsql = sql;
        return (List<?>) hibernateTemplate.execute(new HibernateCallback<Object>(){
            public Object doInHibernate(Session session) throws HibernateException{
                //声明Page分页Object
                Page pageTool = new Page();
                List<Page> list = new ArrayList<Page>();
                Query query = session.createSQLQuery(tempsql);
                if (map != null){
                    Set<String> keSet=map.keySet();  
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    for (Iterator<String> iterator = keSet.iterator(); iterator.hasNext();) {  
                        String str = iterator.next();
                        //query.setParameter(str, map.get(str));
                        if (str.equals("myOrderState")) {
                            query.setParameter(str,
                                    Integer.parseInt(map.get(str)));
                        }else if(str.equals("newsetCommodStartDate")){
                            try {
                                query.setParameter(str,
                                    sdf.parse((map.get(str))));
                            } catch (ParseException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }else if(str.equals("newsetCommodEndDate")){
                            try {
                                query.setParameter(str,
                                    sdf.parse((map.get(str))));
                            } catch (ParseException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }else if(str.equals("beginMoney")){
                            query.setParameter(str, DataToConversion.conversionBigDecimal(map.get(str)));
                        }else if(str.equals("endMoney")){
                            query.setParameter(str, DataToConversion.conversionBigDecimal(map.get(str)));
                        }else{
                            query.setParameter(str, map.get(str));
                        }
                    }
                }
                
                if(page != null && size != null){
                    query.setFirstResult((page - 1) * size).setMaxResults(size);
                    pageTool.setPageIndex(page);
                    pageTool.setPageSize(size);
                    List pagelist = query.list();
                    if(pagelist!=null){
                        pageTool.setData(query.list());
                    }else{
                        pageTool.setData(new ArrayList());
                    }
                }
                list.add(pageTool);
                return list;
            }
        });
    }
	
	/**
     * 根据sql去找表中所有记录数
     * @param sql
     * @param map
     * @return
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public Object executeSQLGetUnique(final String sql,final Map<String, String> map) {
        return hibernateTemplate.execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                Query query = session.createSQLQuery(sql);
                if (map != null){
                    Set<String> keSet=map.keySet();  
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    for (Iterator<String> iterator = keSet.iterator(); iterator.hasNext();) {  
                        String str = iterator.next();
                        if(str.contains("startDate")){
                            try {
                                query.setParameter(str,
                                    sdf.parse((map.get(str))));
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }else if(str.contains("endDate")){
                            try {
                                query.setParameter(str,
                                    sdf.parse((map.get(str))));
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }else if(str.contains("moneyStart") || str.equals("moneyEnd")){
                        	query.setParameter(str, new BigDecimal(map.get(str)));
                        }else{
                            query.setParameter(str, map.get(str));
                        }
                        
                    }
                }
                return query.uniqueResult();
            }
        });
    }

}
