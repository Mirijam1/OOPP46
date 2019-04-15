package nl.tudelft.gogreen.server.service;

import nl.tudelft.gogreen.server.models.user.User;
import org.apache.lucene.search.Query;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collection;

@Service
public class SearchService implements ISearchService {
    private final Logger logger = LoggerFactory.getLogger(SearchService.class);
    private boolean initializedIndex = false;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void initIndex() throws InterruptedException {
        logger.info("Creating index for searching");

        FullTextEntityManager fullTextEntityManager
                = Search.getFullTextEntityManager(entityManager);
        fullTextEntityManager.createIndexer().startAndWait();

        logger.info("Search index created");
        initializedIndex = true;
    }

    @Override
    public Collection<User> searchUsersByName(String name) throws InterruptedException {
        QueryBuilder queryBuilder = getQueryBuilderForClazz(User.class);
        Query query = queryBuilder
                .keyword()
                .fuzzy()
                .withEditDistanceUpTo(2)
                .withPrefixLength(0)
                .onField("username")
                .ignoreAnalyzer()
                .matching(name)
                .createQuery();

        return executeSearchQuery(User.class, query);
    }

    @Override
    public QueryBuilder getQueryBuilderForClazz(Class<?> clazz) {
        FullTextEntityManager fullTextEntityManager
                = Search.getFullTextEntityManager(entityManager);

        return fullTextEntityManager.getSearchFactory()
                .buildQueryBuilder()
                .forEntity(clazz)
                .get();
    }

    @Override
    public <T> Collection<T> executeSearchQuery(Class<T> clazz, Query query) throws InterruptedException {
        if (!initializedIndex) {
            initIndex();
        }

        return Search.getFullTextEntityManager(entityManager).createFullTextQuery(query, clazz).getResultList();
    }
}
