/*
 * Copyright (c) 2014, Francis Galiegue (fgaliegue@gmail.com)
 *
 * This software is dual-licensed under:
 *
 * - the Lesser General Public License (LGPL) version 3.0 or, at your option, any
 *   later version;
 * - the Apache Software License (ASL) version 2.0.
 *
 * The text of this file and of both licenses is available at the root of this
 * project or, if you have the jar distribution, in directory META-INF/, under
 * the names LGPL-3.0.txt and ASL-2.0.txt respectively.
 *
 * Direct link to the sources:
 *
 * - LGPL 3.0: https://www.gnu.org/licenses/lgpl-3.0.txt
 * - ASL 2.0: http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package com.github.fge.jsonschema.keyword.validator.draftv4;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import com.github.fge.jackson.jsonpointer.JsonPointer;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.jsonschema.core.processing.Processor;
import com.github.fge.jsonschema.core.report.*;
import com.github.fge.jsonschema.core.tree.SchemaTree;
import com.github.fge.jsonschema.core.util.AsJson;
import com.github.fge.jsonschema.keyword.validator.helpers.SchemaArrayValidator;
import com.github.fge.jsonschema.main.JsonSchema;
import com.github.fge.jsonschema.main.JsonSchemaFactory;
import com.github.fge.jsonschema.processors.data.FullData;
import com.github.fge.msgsimple.bundle.MessageBundle;
import com.jayway.jsonpath.JsonPath;
import net.minidev.json.JSONArray;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Keyword validator for draft v4's {@code oneOf}
 */
public final class OneOfValidator
    extends SchemaArrayValidator
{
    public OneOfValidator(final JsonNode digest)
    {
        super("oneOf");
    }

    @Override
    public void validate(final Processor<FullData, FullData> processor,
        final ProcessingReport report, final MessageBundle bundle,
        final FullData data)
        throws ProcessingException
    {
        final SchemaTree tree = data.getSchema();
        final JsonPointer schemaPointer = tree.getPointer();
        final JsonNode schemas = tree.getNode().get(keyword);
        final int size = schemas.size();
        final ObjectNode fullReport = FACTORY.objectNode();

        int nrSuccess = 0;
        ListProcessingReport subReport;
        JsonPointer ptr;
        FullData newData;

        Set<String> schemasStr = new HashSet<String>();
        JSONArray arr = JsonPath.read(schemas.toString(), "$..$ref");
        Iterator it = arr.iterator();
        while (it.hasNext()) {
            schemasStr.add(it.next().toString().substring(1));
        }


        CustomProcessingReport subReport1 = new CustomProcessingReport(schemasStr);
        List sr = new ArrayList();
        for (int index = 0; index < size; index++) {
//            subReport = new ListProcessingReport(report.getLogLevel(), LogLevel.FATAL);
            JsonPointer pointer = JsonPointer.of(keyword, index);
            ptr = schemaPointer.append(pointer);

            newData = data.withSchema(tree.setPointer(ptr));
            processor.process(subReport1, newData);
            fullReport.put(ptr.toString(), subReport1.asJson());

            if (subReport1.isSuccess()) {
                nrSuccess++;
            }
        }

        fullReport.put("matching", subReport1.getMatching());
        Iterator<String> i = subReport1.getError().iterator();
        while (i.hasNext()) {
            System.out.println(i.next());
        }

        if (nrSuccess != 1) {
            report.error(newMsg(data, bundle, "err.draftv4.oneOf.fail")
                    .putArgument("matched", nrSuccess)
                    .putArgument("nrSchemas", size)
                    .put("reports", fullReport));
        }
    }

    public static void main(String[] args) throws ProcessingException, IOException {
        final String schemaUri = "http://web.studio.dreamworks.com/services/uns/show/pam/schema/1/schemas/classification/com.dreamworks.mmonti.oneOf/latest";
        JsonSchema schema = JsonSchemaFactory.byDefault().getJsonSchema(schemaUri);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode payload = objectMapper.readTree(new File("./payload.json"));
        ProcessingReport pr = schema.validate(payload);
        Iterator<ProcessingMessage> i = pr.iterator();
        while(i.hasNext()) {
            ProcessingMessage message = i.next();
            System.out.println(message.toString());
        }
    }

    class CustomProcessingReport extends AbstractProcessingReport implements AsJson {

        private Set<String> error = new HashSet<String>();
        private Set<String> schemas = new HashSet<String>();

        public CustomProcessingReport(Set<String> schemas) {
            this.schemas = schemas;
        }

        @Override
        public void log(LogLevel level, ProcessingMessage message) {
            System.out.println(level);
            System.out.println(message);

            error.add((String) JsonPath.read(message.asJson().toString(), "$.schema.pointer"));
        }

        @Override
        public JsonNode asJson() {
            return  JsonNodeFactory.instance.objectNode().put("matching-schema", getMatching());
        }

        public String getMatching() throws IllegalStateException {
            Iterator<String> e = error.iterator();
            while (e.hasNext()) {
                schemas.remove(e.next());
            }

            if (schemas.size()>1) {
                throw new IllegalStateException("More than one matching");
            }
            if (schemas.isEmpty()) {
                return null;
            }
            return schemas.iterator().next();
        }

        public Set<String> getError() {
            return error;
        }
    }
}
