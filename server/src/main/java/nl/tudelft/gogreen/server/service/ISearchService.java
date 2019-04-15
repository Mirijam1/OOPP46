package nl.tudelft.gogreen.server.service;

import nl.tudelft.gogreen.server.models.user.User;
import org.apache.lucene.search.Query;
import org.hibernate.search.query.dsl.QueryBuilder;

import java.util.Collection;

public interface ISearchService {
    void initIndex() throws InterruptedException;

    Collection<User> searchUsersByName(String name) throws InterruptedException;

    QueryBuilder getQueryBuilderForClazz(Class<?> clazz);

    <T> Collection executeSearchQuery(Class<T> clazz, Query query) throws InterruptedException;
}
