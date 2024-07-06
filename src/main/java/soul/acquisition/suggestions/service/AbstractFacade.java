/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.acquisition.suggestions.service;

import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TemporalType;

/**
 *
 * @author soullab
 */
public abstract class AbstractFacade<T> {
    private Class<T> entityClass;

    public AbstractFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected abstract EntityManager getEntityManager();

    public void create(T entity) {
        getEntityManager().persist(entity);
        //System.out.println(id);
    }
    
    public T createAndGet(T entity) {
        getEntityManager().persist(entity);
        getEntityManager().flush();
        return entity;
    }

    public void edit(T entity) {
        getEntityManager().merge(entity);
    }

    public void remove(T entity) {
        getEntityManager().remove(getEntityManager().merge(entity));
    }

    public T find(Object id) {
        return getEntityManager().find(entityClass, id);
    }

    public List<T> findBy(Object query) {
        javax.persistence.Query q = getEntityManager().createNamedQuery((String)query);
        System.out.println("q.getResultList() "+q.getResultList().get(0));
        return q.getResultList();
    }
    
    public List<T> findBy(Object query, Object attrValue) {
        javax.persistence.Query q = getEntityManager().createNamedQuery((String)query);
        List<Object> valueList = (List<Object>) attrValue;
        if(valueList.size() == 1)
            q.setParameter(q.getParameters().iterator().next().getName(), valueList.get(0));
        else
        {
            int index = 1;
            for(Object o: valueList)
            {
                if(o.getClass()==Date.class)
                    q.setParameter(index, (Date) o, TemporalType.DATE);
                else
                    q.setParameter(index, o);
                index++;
            }
        }
        return q.getResultList();
    }

    
    public List<T> findAll() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return getEntityManager().createQuery(cq).getResultList();
    }

    public List<T> findRange(int[] range) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        q.setMaxResults(range[1] - range[0]);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    public int count() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }
    
}