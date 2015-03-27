package nxtsearch;


public interface Predicate<A> {
    boolean holds(A a);
}
