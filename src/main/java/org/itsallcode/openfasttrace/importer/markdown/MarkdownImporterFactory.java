package org.itsallcode.openfasttrace.importer.markdown;

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

import java.io.Reader;

import org.itsallcode.openfasttrace.importer.*;

/**
 * {@link ImporterFactory} for Markdown files
 */
public class MarkdownImporterFactory extends RegexMatchingImporterFactory
{
    public MarkdownImporterFactory()
    {
        super("(?i).*\\.markdown", "(?i).*\\.md");
    }

    @Override
    public Importer createImporter(final String fileName, final Reader reader,
            final ImportEventListener listener)
    {
        return new MarkdownImporter(fileName, reader, listener);
    }
}
