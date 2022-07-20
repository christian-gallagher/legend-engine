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

package org.finos.legend.engine.language.pure.dsl.mastery.grammar.from;

import org.eclipse.collections.impl.utility.ListIterate;
import org.finos.legend.engine.language.pure.grammar.from.ParseTreeWalkerSourceInformation;
import org.finos.legend.engine.language.pure.grammar.from.PureGrammarParserUtility;
import org.finos.legend.engine.language.pure.grammar.from.antlr4.MasteryParserGrammar;
import org.finos.legend.engine.language.pure.grammar.from.domain.DomainParser;
import org.finos.legend.engine.protocol.pure.v1.model.SourceInformation;
import org.finos.legend.engine.protocol.pure.v1.model.context.EngineErrorType;
import org.finos.legend.engine.protocol.pure.v1.model.packageableElement.PackageableElement;
import org.finos.legend.engine.protocol.pure.v1.model.packageableElement.mastery.resolution.IdentityResolution;
import org.finos.legend.engine.protocol.pure.v1.model.packageableElement.mastery.MasterRecordDefinition;
import org.finos.legend.engine.protocol.pure.v1.model.packageableElement.mastery.resolution.ResolutionKeyType;
import org.finos.legend.engine.protocol.pure.v1.model.packageableElement.mastery.resolution.ResolutionQuery;
import org.finos.legend.engine.protocol.pure.v1.model.packageableElement.section.ImportAwareCodeSection;
import org.finos.legend.engine.protocol.pure.v1.model.valueSpecification.raw.Lambda;
import org.finos.legend.engine.shared.core.operational.errorManagement.EngineException;

import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

public class MasteryParseTreeWalker
{
    private final ParseTreeWalkerSourceInformation walkerSourceInformation;
    private final Consumer<PackageableElement> elementConsumer;
    private final ImportAwareCodeSection section;

    private final DomainParser domainParser;

    public MasteryParseTreeWalker(ParseTreeWalkerSourceInformation walkerSourceInformation, Consumer<PackageableElement> elementConsumer, ImportAwareCodeSection section, DomainParser domainParser)
    {
        this.walkerSourceInformation = walkerSourceInformation;
        this.elementConsumer = elementConsumer;
        this.section = section;
        this.domainParser = domainParser;
    }

    /**********
     * mastery
     **********/

    public void visit(MasteryParserGrammar.DefinitionContext ctx)
    {
        ctx.mastery().stream().map(this::visitMastery).peek(e -> this.section.elements.add((e.getPath()))).forEach(this.elementConsumer);
    }

    private MasterRecordDefinition visitMastery(MasteryParserGrammar.MasteryContext ctx)
    {
        MasterRecordDefinition masterRecordDefinition = new MasterRecordDefinition();
        masterRecordDefinition.name = PureGrammarParserUtility.fromIdentifier(ctx.qualifiedName().identifier());
        masterRecordDefinition._package = ctx.qualifiedName().packagePath() == null ? "" : PureGrammarParserUtility.fromPath(ctx.qualifiedName().packagePath().identifier());
        masterRecordDefinition.sourceInformation = walkerSourceInformation.getSourceInformation(ctx);

        //modelClass
        MasteryParserGrammar.ModelClassContext modelClassContext = PureGrammarParserUtility.validateAndExtractRequiredField(ctx.modelClass(), "modelClass", walkerSourceInformation.getSourceInformation(ctx));
        masterRecordDefinition.modelClass = visitModelClass(modelClassContext);

        //IdentityResolution
        MasteryParserGrammar.IdentityResolutionContext identityResolutionContext = PureGrammarParserUtility.validateAndExtractRequiredField(ctx.identityResolution(), "identityResolution", masterRecordDefinition.sourceInformation);
        masterRecordDefinition.identityResolution = visitIdentityResolution(identityResolutionContext);

        return masterRecordDefinition;
    }

    private IdentityResolution visitIdentityResolution(MasteryParserGrammar.IdentityResolutionContext ctx)
    {
        IdentityResolution identityResolution = new IdentityResolution();
        identityResolution.sourceInformation = walkerSourceInformation.getSourceInformation(ctx);

        //modelClass
        MasteryParserGrammar.ModelClassContext modelClassContext = PureGrammarParserUtility.validateAndExtractRequiredField(ctx.modelClass(), "modelClass", identityResolution.sourceInformation);
        identityResolution.modelClass = visitModelClass(modelClassContext);

        //queries
        MasteryParserGrammar.ResolutionQueriesContext resolutionQueriesContext = PureGrammarParserUtility.validateAndExtractRequiredField(ctx.resolutionQueries(), "resolutionQueries", identityResolution.sourceInformation);
        identityResolution.resolutionQueries = ListIterate.collect(resolutionQueriesContext.resolutionQuery(), this::visitResolutionQuery);

        return identityResolution;
    }

    private ResolutionQuery visitResolutionQuery(MasteryParserGrammar.ResolutionQueryContext ctx)
    {
        SourceInformation sourceInformation = walkerSourceInformation.getSourceInformation(ctx);
        ResolutionQuery resolutionQuery = new ResolutionQuery();

        //queries
        resolutionQuery.queries = (List<Lambda>) ListIterate.flatCollect(ctx.queryExpressions(), this::visitQueryExpressions);

        //keyType
        MasteryParserGrammar.ResolutionQueryKeyTypeContext resolutionQueryKeyTypeContext = PureGrammarParserUtility.validateAndExtractRequiredField(ctx.resolutionQueryKeyType(), "keyType", sourceInformation);
        resolutionQuery.keyType = visitResolutionKeyType(resolutionQueryKeyTypeContext);

        //precedence - Field 'precedence' should be specified only once
        MasteryParserGrammar.ResolutionQueryPrecedenceContext resolutionQueryPrecedenceContext = PureGrammarParserUtility.validateAndExtractRequiredField(ctx.resolutionQueryPrecedence(), "precedence", sourceInformation);
        resolutionQuery.precedence = Integer.parseInt(resolutionQueryPrecedenceContext.INTEGER().getText());

        return resolutionQuery;
    }

    private List<Lambda> visitQueryExpressions(MasteryParserGrammar.QueryExpressionsContext ctx)
    {

        List<MasteryParserGrammar.LambdaFunctionContext> lambdaFunctionContexts = ctx.lambdaFunction();
        return ListIterate.collect(lambdaFunctionContexts, this::visitLambda).toList();
    }

    private Lambda visitLambda(MasteryParserGrammar.LambdaFunctionContext ctx)
    {
        Lambda lambda = domainParser.parseLambda(ctx.getText(), "", 0, 0, true);
        return lambda;
    }


    private String visitModelClass(MasteryParserGrammar.ModelClassContext ctx)
    {
        MasteryParserGrammar.QualifiedNameContext qualifiedNameContext = ctx.qualifiedName();
        return PureGrammarParserUtility.fromQualifiedName(qualifiedNameContext.packagePath() == null ? Collections.emptyList() : qualifiedNameContext.packagePath().identifier(), qualifiedNameContext.identifier());
    }

    private ResolutionKeyType visitResolutionKeyType(MasteryParserGrammar.ResolutionQueryKeyTypeContext ctx)
    {
        SourceInformation sourceInformation = walkerSourceInformation.getSourceInformation(ctx);
        if (ctx.RESOLUTION_QUERY_KEY_TYPE_GENERATED_PRIMARY_KEY() != null)
        {
            return ResolutionKeyType.GeneratedPrimaryKey;
        }
        if (ctx.RESOLUTION_QUERY_KEY_TYPE_SUPPLIED_PRIMARY_KEY() != null)
        {
            return ResolutionKeyType.SuppliedPrimaryKey;
        }
        if (ctx.RESOLUTION_QUERY_KEY_TYPE_ALTERNATE_KEY() != null)
        {
            return ResolutionKeyType.AlternateKey;
        }
        if (ctx.RESOLUTION_QUERY_KEY_TYPE_OPTIONAL() != null)
        {
            return ResolutionKeyType.Optional;
        }

        throw new EngineException("Unrecognized resolution key type", sourceInformation, EngineErrorType.PARSER);
    }
}
