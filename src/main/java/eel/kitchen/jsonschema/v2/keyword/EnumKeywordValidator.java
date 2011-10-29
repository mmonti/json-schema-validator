/*
 * Copyright (c) 2011, Francis Galiegue <fgaliegue@gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package eel.kitchen.jsonschema.v2.keyword;

import eel.kitchen.jsonschema.v2.schema.ValidationState;
import eel.kitchen.util.CollectionUtils;
import org.codehaus.jackson.JsonNode;

import java.util.HashSet;
import java.util.Set;

public final class EnumKeywordValidator
    extends TrueFalseKeywordValidator
{
    private final Set<JsonNode> enumValues = new HashSet<JsonNode>();

    public EnumKeywordValidator(final JsonNode schema)
    {
        super(schema);
        enumValues.addAll(CollectionUtils.toSet(schema.get("enum")
            .getElements()));
    }

    @Override
    public void validate(final ValidationState state, final JsonNode node)
    {
        if (enumValues.contains(node)) {
            state.setStatus(ValidationStatus.SUCCESS);
            return;
        }

        state.addMessage("instance does not match any enumerated element");
        state.setStatus(ValidationStatus.FAILURE);
    }

    @Override
    public ValidationStatus validate(final JsonNode instance)
    {
        if (enumValues.contains(instance))
            return ValidationStatus.SUCCESS;

        messages.add("instance does not match any enumerated element");
        return ValidationStatus.FAILURE;
    }
}
