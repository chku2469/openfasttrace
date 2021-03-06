package org.itsallcode.openfasttrace.importer;

/*-
 * #%L
 \* OpenFastTrace
 * %%
 * Copyright (C) 2016 - 2018 hamstercommunity
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

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Super class for factories producing {@link Importer}s.
 */
public abstract class ImporterFactory
{
    /**
     * Returns <code>true</code> if this {@link ImporterFactory} supports
     * importing the given file based on its file extension.
     *
     * @param file
     *            the file to check.
     * @return <code>true</code> if the given file is supported for importing.
     */
    public abstract boolean supportsFile(final Path file);

    /**
     * Create an importer that is able to read the given file.
     *
     * @param file
     *            the file from which specification items are imported
     * @param charset
     *            the charset used for importing
     * @param listener
     *            the listener to be informed about detected specification item
     *            fragments
     * @return an importer instance
     */
    public abstract Importer createImporter(final Path file, final Charset charset,
            final ImportEventListener listener);

    protected static BufferedReader createReader(final Path file, final Charset charset)
    {
        try
        {
            return Files.newBufferedReader(file, charset);
        }
        catch (final IOException e)
        {
            throw new ImporterException("Error reading file '" + file + "': " + e.getMessage(), e);
        }
    }
}
