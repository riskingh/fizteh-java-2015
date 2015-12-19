package ru.fizteh.fivt.students.riskingh.CQL.impl;

import java.util.stream.Stream;

public interface Query<R> {

    Iterable<R> execute();

    Stream<R> stream();
}
