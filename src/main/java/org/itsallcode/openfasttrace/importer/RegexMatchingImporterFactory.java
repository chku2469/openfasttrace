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

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toSet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Set;
import java.util.logging.Logger;
import java.util.regex.Pattern;

public abstract class RegexMatchingImporterFactory extends ImporterFactory
{
    private static final Logger LOG = Logger
            .getLogger(RegexMatchingImporterFactory.class.getName());

    private final Set<Pattern> supportedFilenamePatterns;

    protected RegexMatchingImporterFactory(final String... supportedFilenamePatterns)
    {
        this(asList(supportedFilenamePatterns));
    }

    protected RegexMatchingImporterFactory(final Collection<String> supportedFileExtensions)
    {
        this.supportedFilenamePatterns = supportedFileExtensions.stream() //
                .map(Pattern::compile) //
                .collect(toSet());
    }

    @Override
    public boolean supportsFile(final Path file)
    {
        final String fileName = file.getFileName().toString();
        for (final Pattern pattern : this.supportedFilenamePatterns)
        {
            if (pattern.matcher(fileName).matches())
            {
                LOG.finest(() -> "Filename '" + fileName + "' matches '" + pattern
                        + "': supported  by " + this.getClass().getName());
                return true;
            }
        }
        LOG.finest(() -> "Filename '" + fileName + "' does not match any regexp of "
                + this.supportedFilenamePatterns + ": not supported by "
                + this.getClass().getName());
        return false;
    }

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
     * @return an {@link Importer} instance
     */
    @Override
    public Importer createImporter(final Path file, final Charset charset,
            final ImportEventListener listener)
    {
        if (!supportsFile(file))
        {
            throw new ImporterException(
                    "File '" + file + "' not supported for import. Supported file name patterns: "
                            + this.supportedFilenamePatterns);
        }
        LOG.finest(() -> "Creating importer for file " + file);

        return () -> runImporter(file, charset, listener);
    }

    private void runImporter(final Path file, final Charset charset,
            final ImportEventListener listener)
    {
        try (final BufferedReader reader = createReader(file, charset))
        {
            final Importer importer = createImporter(file.toString(), reader, listener);
            importer.runImport();
        }
        catch (final IOException e)
        {
            throw new ImporterException(
                    "Error importing file '" + file + "': " + e.getMessage(), e);
        }
    }

    /**
     * Create an importer that is able to read the given file
     *
     * @param fileName
     *            the name of the file.
     * @param reader
     *            the reader from which specification items are imported
     * @param listener
     *            the listener to be informed about detected specification item
     *            fragments
     * @return an importer instance
     */
    public abstract Importer createImporter(String fileName, final Reader reader,
            final ImportEventListener listener);
}
