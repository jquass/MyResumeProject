package com.jonquass.core.algebra;

import org.derive4j.ArgOption;
import org.derive4j.Data;
import java.util.function.Function;

@Data(arguments = ArgOption.checkedNotNull)
public abstract class Result<Val, Err> {
    public abstract <ValX> ValX either(
            Function<Val, ValX> value, Function<Err, ValX> error);
}

