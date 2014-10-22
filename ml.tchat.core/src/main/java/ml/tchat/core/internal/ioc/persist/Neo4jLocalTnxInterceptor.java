package ml.tchat.core.internal.ioc.persist;

import com.google.inject.Inject;
import com.google.inject.persist.Transactional;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Transaction;

import java.lang.reflect.Method;

public class Neo4jLocalTnxInterceptor implements MethodInterceptor {

  @Transactional
  private static class Internal {
  }

  @Inject
  private Neo4jPersistService graphProvider;

  public Object invoke(MethodInvocation invocation) throws Throwable {
    Transactional transactional = readTransactionMetadata(invocation);

    GraphDatabaseService graph = graphProvider.get();
    // Nested transactions in Neo4j are "flat nested transactions",
    // so we can safely open a new transaction each time we need one.
    Transaction tx = graph.beginTx();
    Object result;
    try {
      result = invocation.proceed();
    } catch (Exception e) {
      rollbackIfNecessary(transactional, e, tx);
      throw e;
    } finally {
      // Unconditionally call success() to "commit if necessary":
      // tx.success() is a no-op if tx.failure() has been called in
      // rollbackIfNecessary().
      tx.success();
      tx.finish();
    }
    return result;
  }

  // Copied from com.google.guice.persist.jpa.JpaLocalTxnInterceptor
  // TODO(dhanji): Cache this method's results.
  private Transactional readTransactionMetadata(MethodInvocation methodInvocation) {
    Transactional transactional;
    Method method = methodInvocation.getMethod();
    Class<?> targetClass = methodInvocation.getThis().getClass();

    transactional = method.getAnnotation(Transactional.class);
    if (null == transactional) {
      // If none on method, try the class.
      transactional = targetClass.getAnnotation(Transactional.class);
    }
    if (null == transactional) {
      // If there is no transactional annotation present, use the default
      transactional = Internal.class.getAnnotation(Transactional.class);
    }

    return transactional;
  }

  // Inspired by com.google.guice.persist.jpa.JpaLocalTxnInterceptor
  private void rollbackIfNecessary(Transactional transactional, final Exception e,
                                   Transaction txn) {
    for (Class<? extends Exception> rollBackOn : transactional.rollbackOn()) {
      // if one matched, try to perform a rollback
      if (rollBackOn.isInstance(e)) {
        // check ignore clauses (supercedes rollback clause)
        for (Class<? extends Exception> exceptOn : transactional.ignore()) {
          // An exception to the rollback clause was found, DON'T rollback
          // (i.e. commit and throw anyway)
          if (exceptOn.isInstance(e)) {
            return;
          }
        }

        // rollback only if nothing matched the ignore check
        txn.failure();
        return;
      }
    }
  }
}
