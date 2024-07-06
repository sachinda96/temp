/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.catalogue.service;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.TemporalType;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

/**
 *
 * @author soullab
 */
//
public abstract class AbstractFacade<T> {
    private Class<T> entityClass;

    public AbstractFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected abstract EntityManager getEntityManager();

    public void create(T entity) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<T>> constraintViolations = validator.validate(entity);
        if(constraintViolations.size() > 0){
            Iterator<ConstraintViolation<T>> iterator = constraintViolations.iterator();
            while(iterator.hasNext()){
                ConstraintViolation<T> cv = iterator.next();
                System.err.println(cv.getRootBeanClass().getName()+"."+cv.getPropertyPath() + " " +cv.getMessage());
            }
        }
        else{
            System.out.println("called");
        getEntityManager().persist(entity);
        }
    }
    
     public T createAndGet(T entity) {
         System.out.println("called.....");
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

     public void removeBy(Object query, Object attrValue) {
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
        int i = q.executeUpdate();
    }
    
    public T find(Object id) {
        return getEntityManager().find(entityClass, id);
    }

    public List<T> findAll() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return getEntityManager().createQuery(cq).getResultList();
    }
    
     public List<T> findBy(Object query) {
        javax.persistence.Query q = getEntityManager().createNamedQuery((String)query);
        return q.getResultList();
    }
    
    public List<T> findBy(Object query, Object attrValue) {
       // System.out.println("attrValue "+attrValue+"   "+query);
        javax.persistence.Query q = getEntityManager().createNamedQuery((String)query);
       //  System.out.println("qqqq "+q);
        List<Object> valueList = (List<Object>) attrValue;
       // System.out.println("valueList.get(0) "+valueList.get(0));
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
                {
                    //System.out.println("valueList.size() "+valueList.size());
                   // System.out.println("val..be..  "+index+"  ...  "+o+"   "+q.toString());
                    q.setParameter(index, o);
                  //   System.out.println("val..aaaaaaaaaf..  "+index+"  ...  "+o+"   "+q.toString());
                }
                index++;
            }
        }
        return q.getResultList();
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
    
    public int countBy(Object query, Object attrValue) {
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
        return ((Long) q.getSingleResult()).intValue();
    }
    
    public int countMaxVal(Object query) {
        javax.persistence.Query q = getEntityManager().createNamedQuery((String)query);
//        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
//        javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
//        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
//        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }
    
}