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

package org.finos.legend.engine.language.pure.dsl.mastery.compiler.test;

import org.eclipse.collections.api.tuple.Pair;
import org.eclipse.collections.impl.utility.ListIterate;
import org.finos.legend.engine.language.pure.compiler.test.TestCompilationFromGrammar;
import org.finos.legend.engine.language.pure.compiler.toPureGraph.PureModel;
import org.finos.legend.engine.protocol.pure.v1.model.context.PureModelContextData;

import org.finos.legend.pure.generated.Root_meta_pure_mastery_metamodel_MasterRecordDefinition;
import org.finos.legend.pure.generated.Root_meta_pure_mastery_metamodel_resolution_IdentityResolution;
import org.finos.legend.pure.generated.Root_meta_pure_mastery_metamodel_resolution_ResolutionQuery;
import org.finos.legend.pure.generated.Root_meta_pure_metamodel_function_LambdaFunction_Impl;
import org.finos.legend.pure.m3.coreinstance.meta.pure.metamodel.PackageableElement;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class TestMasteryCompilationFromGrammar extends TestCompilationFromGrammar.TestCompilationFromGrammarTestSuite
{

    public static String COMPLETE_CORRECT_MODEL = "###Pure\n" +
            "Class org::dataeng::Widget\n" +
            "{\n" +
            "  widgetId: String[0..1];\n" +
            "  identifiers: org::dataeng::MilestonedIdentifier[*];\n" +
            "}\n\n" +
            "Class org::dataeng::MilestonedIdentifier\n" +
            "{\n" +
            "  identifierType: String[1];\n" +
            "  identifier: String[1];\n" +
            "  FROM_Z: StrictDate[0..1];\n" +
            "  THRU_Z: StrictDate[0..1];\n" +
            "}\n\n\n" +
            "###Mastery\n" + "MasterRecordDefinition alloy::mastery::WidgetMasterRecord" +
            "\n" +
            //"\nMasterRecordDefinition " + ListAdapter.adapt(keywords).makeString("::") + "\n" + //Fails on the use of import
            "{\n" +
            "  modelClass: org::dataeng::Widget;\n" +
            "  identityResolution: \n" +
            "  {\n" +
            "    modelClass: org::dataeng::Widget;\n"
            + //Testing picking up a different class.
            "    resolutionQueries:\n"
            + "      [\n" +
            "        {\n" +
            "          queries: [ {input: org::dataeng::Widget[1]|org::dataeng::Widget.all()->filter(widget|$widget.widgetId == $input.widgetId)}\n" +
            "                   ];\n" +
            "          keyType: GeneratedPrimaryKey;\n" +
            "          precedence: 1;\n" +
            "        },\n" +
            "        {\n" +
            //"          queries: [ {{input: org::dataeng::Widget[1],EFFECTIVE_DATE: StrictDate[1]|org::dataeng::Widget.all()->filter(widget|((($widget.identifiers.identifierType == 'ISIN') && ($input.identifiers->filter(idType|$idType.identifierType == 'ISIN').identifier == $widget.identifiers->filter(idType|$idType.identifierType == 'ISIN').identifier)) && ($widget.identifiers.FROM_Z->toOne() <= $EFFECTIVE_DATE)) && ($widget.identifiers.THRU_Z->toOne() > $EFFECTIVE_DATE))}}" +
            "          queries: [ {input: org::dataeng::Widget[1],EFFECTIVE_DATE: StrictDate[1]|org::dataeng::Widget.all()->filter(widget|" +
                                    "((($widget.identifiers.identifierType == 'ISIN') && " +
                                    "($input.identifiers->filter(idType|$idType.identifierType == 'ISIN').identifier == $widget.identifiers->filter(idType|$idType.identifierType == 'ISIN').identifier)) && " +
                                    "($widget.identifiers.FROM_Z->toOne() <= $EFFECTIVE_DATE)) && " +
                                    "($widget.identifiers.THRU_Z->toOne() > $EFFECTIVE_DATE))}\n" +
            "                   ];\n" +
            "          keyType: AlternateKey;\n" +
            "          precedence: 2;\n" +
            "        }\n" +
            "      ]\n" +
            "  }\n" +
            "}\n";

    //Simple test from ### Text
    @Test
    public void flatShape()
    {
        Pair<PureModelContextData, PureModel> result = test(COMPLETE_CORRECT_MODEL);
        PureModel model = result.getTwo();


        //TODO gallch clean up and log properly...
        System.out.println("textMetaContext:...\n\n" + COMPLETE_CORRECT_MODEL);

        PackageableElement packageableElement = model.getPackageableElement("alloy::mastery::WidgetMasterRecord");
        assertNotNull(packageableElement);
        assertTrue(packageableElement instanceof Root_meta_pure_mastery_metamodel_MasterRecordDefinition);

        //MasterRecord Definition modelClass
        Root_meta_pure_mastery_metamodel_MasterRecordDefinition masterRecordDefinition = (Root_meta_pure_mastery_metamodel_MasterRecordDefinition) packageableElement;
        assertEquals("Widget", masterRecordDefinition._modelClass()._name());

        Root_meta_pure_mastery_metamodel_resolution_IdentityResolution idRes = masterRecordDefinition._identityResolution();
        assert (idRes instanceof Root_meta_pure_mastery_metamodel_resolution_IdentityResolution);
        assertEquals("Widget", idRes._modelClass()._name());


        //Queries
        Object[] queriesArray = idRes._queries().toArray();
        assertEquals(1, ((Root_meta_pure_mastery_metamodel_resolution_ResolutionQuery) queriesArray[0])._precedence());
        assertEquals("GeneratedPrimaryKey", ((Root_meta_pure_mastery_metamodel_resolution_ResolutionQuery) queriesArray[0])._keyType()._name());
        assertPureLambdas(((Root_meta_pure_mastery_metamodel_resolution_ResolutionQuery) queriesArray[0])._resolutionQueries().toList());

        assertEquals(2, ((Root_meta_pure_mastery_metamodel_resolution_ResolutionQuery) queriesArray[1])._precedence());
        assertEquals("AlternateKey", ((Root_meta_pure_mastery_metamodel_resolution_ResolutionQuery) queriesArray[1])._keyType()._name());
        assertPureLambdas(((Root_meta_pure_mastery_metamodel_resolution_ResolutionQuery) queriesArray[0])._resolutionQueries().toList());

        System.out.println("\nmodel\n" + model);
    }

    private void assertPureLambdas(List list)
    {
        ListIterate.forEach(list, (resQuery) ->
        {
            assert (resQuery instanceof Root_meta_pure_metamodel_function_LambdaFunction_Impl);
        });
    }

    @Override
    protected String getDuplicatedElementTestCode()
    {
        return null;
    }

    @Override
    public String getDuplicatedElementTestExpectedErrorMessage()
    {
        return "COMPILATION error at [5:1-9:1]: Duplicated element 'anything::class'";
    }
}