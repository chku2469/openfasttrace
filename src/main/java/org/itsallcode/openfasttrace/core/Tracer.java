package org.itsallcode.openfasttrace.core;

/*-
 * #%L
 \* OpenFastTrace
 * %%
 * Copyright (C) 2016 - 2017 itsallcode.org
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */


import java.util.List;
import java.util.stream.Collectors;

public class Tracer
{
    public Trace trace(final List<LinkedSpecificationItem> items)
    {
        final Trace.Builder builder = new Trace.Builder();
        builder.items(items);
        builder.uncleanItems(items.stream() //
                .filter(item -> item.getDeepCoverageStatus() != DeepCoverageStatus.COVERED) //
                .collect(Collectors.toList()));
        return builder.build();
    }
}
