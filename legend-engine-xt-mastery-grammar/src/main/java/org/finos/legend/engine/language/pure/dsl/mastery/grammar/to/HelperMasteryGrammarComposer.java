// Copyright 2022 Goldman Sachs
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package org.finos.legend.engine.language.pure.dsl.mastery.grammar.to;

import org.eclipse.collections.impl.utility.ListIterate;
import org.finos.legend.engine.language.pure.grammar.to.DEPRECATED_PureGrammarComposerCore;
import org.finos.legend.engine.language.pure.grammar.to.PureGrammarComposerContext;
import org.finos.legend.engine.language.pure.grammar.to.PureGrammarComposerUtility;
import org.finos.legend.engine.protocol.pure.v1.model.packageableElement.mastery.resolution.IdentityResolution;
import org.finos.legend.engine.protocol.pure.v1.model.packageableElement.mastery.resolution.IdentityResolutionVisitor;
import org.finos.legend.engine.protocol.pure.v1.model.packageableElement.mastery.MasterRecordDefinition;
import org.finos.legend.engine.protocol.pure.v1.model.packageableElement.mastery.resolution.ResolutionQuery;

import static org.finos.legend.engine.language.pure.grammar.to.PureGrammarComposerUtility.convertPath;
import static org.finos.legend.engine.language.pure.grammar.to.PureGrammarComposerUtility.convertString;
import static org.finos.legend.engine.language.pure.grammar.to.PureGrammarComposerUtility.getTabString;


/**
 * Convert the protocol to a String parsable by the grammar.
 */
public class HelperMasteryGrammarComposer
{
    private HelperMasteryGrammarComposer()
    {
    }

    public static String renderMastery(MasterRecordDefinition masterRecordDefinition, int indentLevel, PureGrammarComposerContext context)
    {
        String masteryRendered = "MasterRecordDefinition " + convertPath(masterRecordDefinition.getPath()) + "\n" +
                "{\n" +
                renderModelClass(masterRecordDefinition.modelClass, indentLevel, context) +
                renderIdentityResolution(masterRecordDefinition.identityResolution, indentLevel, context) +
                "}";
        System.out.println("masteryRendered:\n" + masteryRendered);
        return masteryRendered;
    }

    private static String renderModelClass(String modelClass, int indentLevel, PureGrammarComposerContext context)
    {
        return getTabString(indentLevel) + "modelClass: " + modelClass + ";\n";
    }

    private static String renderIdentityResolution(IdentityResolution identityResolution, int indentLevel, PureGrammarComposerContext context)
    {
        return identityResolution.accept(new IdentityResolutionComposer(indentLevel, context));
    }

    private static String renderResolutionQueries(IdentityResolution identityResolution, int indentLevel, PureGrammarComposerContext context)
    {
        StringBuilder builder = new StringBuilder();
        builder.append(getTabString(indentLevel)).append("resolutionQueries:");
        builder.append("\n").append(getTabString(indentLevel + 2)).append("[");
        ListIterate.forEachWithIndex(identityResolution.resolutionQueries, (resolutionQuery, i) ->
        {
            builder.append(i > 0 ? "," : "").append("\n").append(getTabString(indentLevel + 3)).append("{");
            builder.append("\n").append(getTabString(indentLevel + 4)).append("queries: [ ");
            builder.append(renderQueries(resolutionQuery, indentLevel + 5, context));
            builder.append(getTabString(indentLevel + 4)).append("         ];\n");
            builder.append(getTabString(indentLevel + 4)).append("keyType: ").append(resolutionQuery.keyType).append(";\n");
            builder.append(getTabString(indentLevel + 4)).append("precedence: ").append(resolutionQuery.precedence).append(";\n");
            builder.append(getTabString(indentLevel + 3)).append("}");
        });
        builder.append("\n").append(getTabString(indentLevel + 2)).append("]");
        return builder.toString();
    }

    private static class IdentityResolutionComposer implements IdentityResolutionVisitor<String>
    {
        private final int indentLevel;
        private final PureGrammarComposerContext context;

        private IdentityResolutionComposer(int indentLevel, PureGrammarComposerContext context)
        {
            this.indentLevel = indentLevel;
            this.context = context;
        }

        @Override
        public String visit(IdentityResolution val)
        {
            return getTabString(indentLevel) + "identityResolution: \n" +
                    getTabString(indentLevel) + "{\n" +
                    getTabString(indentLevel + 1) + "modelClass: " + val.modelClass + ";\n" +
                    getTabString(indentLevel) + renderResolutionQueries(val, this.indentLevel, this.context) + "\n" +
                    getTabString(indentLevel) + "}\n";
        }
    }

    private static String renderQueries(ResolutionQuery query, int indentLevel, PureGrammarComposerContext context)
    {
        StringBuilder builder = new StringBuilder();
        ListIterate.forEachWithIndex(query.queries, (lambda, i) ->
        {
            if (i > 0)
            {
                builder.append(",\n").append(getTabString(indentLevel)).append("         ");
            }
            //builder.append(getTabString(indentLevel));
            builder.append("{").append(lambda.accept(DEPRECATED_PureGrammarComposerCore.Builder.newInstance(context).build())).append("}");
        });
        return builder.append("\n").toString();
    }
}
