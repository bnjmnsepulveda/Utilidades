package basedatos.template;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 *
 * @author benjamin
 * @param <T>
 */
public abstract class AbstractJPAFacade<T> {

    private final Class<T> entityClass;
    protected EntityManager em;

    public AbstractJPAFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("FoodNowPU");
        em = emf.createEntityManager();
    }

    public void create(T entity) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(entity);
        tx.commit();
    }

    public void edit(T entity) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.merge(entity);
        tx.commit();
    }

    public void remove(T entity) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.remove(em.merge(entity));
        tx.commit();
    }

    public T read(Object id) {
        return em.find(entityClass, id);
    }

    public List<T> readAll() {
        String jpql = "SELECT entidad FROM " + entityClass.getSimpleName() + " entidad";
        return em.createQuery(jpql).getResultList();
    }

    public List<T> readRange(int[] range) {
        String jpql = "SELECT entidad FROM " + entityClass.getSimpleName() + " entidad";
        Query query = em.createQuery(jpql);
        query.setMaxResults(range[1] - range[0] + 1);
        query.setFirstResult(range[0]);
        return query.getResultList();
    }

    public int count() {
        String jpql = "SELECT COUNT(entidad.id) FROM " + entityClass.getSimpleName() + " entidad";
        Long count = (Long) em.createQuery(jpql).getSingleResult();
        return count.intValue();        
    }

}
