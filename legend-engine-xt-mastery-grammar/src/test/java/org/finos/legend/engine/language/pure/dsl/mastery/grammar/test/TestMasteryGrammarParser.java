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

package org.finos.legend.engine.language.pure.dsl.mastery.grammar.test;

import org.antlr.v4.runtime.Vocabulary;
import org.eclipse.collections.impl.list.mutable.ListAdapter;
import org.finos.legend.engine.language.pure.grammar.from.antlr4.MasteryParserGrammar;
import org.finos.legend.engine.language.pure.grammar.test.TestGrammarParser;
import org.junit.Test;

import java.util.List;

public class TestMasteryGrammarParser extends TestGrammarParser.TestGrammarParserTestSuite
{
    @Override
    public Vocabulary getParserGrammarVocabulary()
    {
        return MasteryParserGrammar.VOCABULARY;
    }

    /*
     *
     * ###Mastery
     * MasterRecordDefinition exampleQualifiedName
     * {
     *      model : alloy::mastery::Widget;
     *      identityResolution : IdentityResolution
     *      {
     *          model : alloy::mastery::Widget;
     *      }
     * }
     *
     */


    @Override
    public String getParserGrammarIdentifierInclusionTestCode(List<String> keywords)
    {
        String testInput = "###Mastery\n" +
                "\nMasterRecordDefinition alloy::mastery::WidgetMasterRecord" + "\n" +
                //"\nMasterRecordDefinition " + ListAdapter.adapt(keywords).makeString("::") + "\n" + //Fails on the use of import
                "{\n" +
                "  modelClass: alloy::mastery::Widget;\n" +
                "  identityResolution : \n" +
                "  {\n" +
                "      modelClass: alloy::mastery::Widget;\n" +
                "      resolutionQueries:\n" +
                "      [ \n" +
                "        {\n" +
                "           queries: [ {widget: test::Widget[1] | test::Widget.all()->filter(input|$widget.widgetId == $input.widgetid)},\n" +
                "                      {widget: test::Widget[1] | test::Widget.topLevelMilestonedIdentifier.identifierType == test::TopLevelMileStonedSIdentifier.TopLevelIdentifier1},\n" +
                "                      {widget: test::Widget[1] | $widget.topLevelMilestonedIdentifier.FROM_Z->toOne() <= $EFFECTIVE_DATE} \n" +
                "                    ];\n" +
                "           keyType: Optional;\n" +
                "           precedence: 1;\n" +
                "         },\n" +
                "         {\n" +
                "           queries: [ {wodgit: test::Widget[1] | test::Widget.all()->filter(input|$widget.widgetId == $input.widgetid)} ]; \n" +
                "           keyType: GeneratedPrimaryKey;\n" +
                "           precedence: 1;\n" +
                "         }\n" +
                "      ]\n" +
                "   }\n" +
                "}\n";
        System.out.println(testInput);
        return testInput;


//        return "###Persistence\n" +
//                "\n" +
//                "Persistence " + ListAdapter.adapt(keywords).makeString("::") + "\n" +
//                "{\n" +
//                "  doc: 'This is test documentation.';\n" +
//                "  trigger: Manual;\n" +
//                "  service: test::Service;\n" +
//                "  persister: Batch\n" +
//                "  {\n" +
//                "    sink: Relational\n" +
//                "    {\n" +
//                "    }\n" +
//                "    targetShape: Flat\n" +
//                "    {\n" +
//                "      targetName: 'TestDataset1';\n" +
//                "      modelClass: test::ModelClass;\n" +
//                "    }\n" +
//                "    ingestMode: AppendOnly\n" +
//                "    {\n" +
//                "      auditing: None;\n" +
//                "      filterDuplicates: false;\n" +
//                "    }\n" +
//                "  }\n" +
//                "}\n";
    }










    /**********
     * persistence
     **********/

//    //@Test
//    public void persistenceDoc()
//    {
//        test("###Persistence\n" +
//                "\n" +
//                "Persistence test::TestPersistence \n" +
//                "{\n" +
//                "  trigger: Manual;\n" +
//                "  service: test::Service;\n" +
//                "  persister: Batch\n" +
//                "  {\n" +
//                "    targetShape: Flat\n" +
//                "    {\n" +
//                "      targetName: 'TestDataset1';\n" +
//                "      modelClass: test::ModelClass;\n" +
//                "    }\n" +
//                "    ingestMode: AppendOnly\n" +
//                "    {\n" +
//                "      auditing: None;\n" +
//                "      filterDuplicates: false;\n" +
//                "    }\n" +
//                "  }\n" +
//                "}\n", "PARSER error at [3:1-20:1]: Field 'doc' is required");
//        test("###Persistence\n" +
//                "\n" +
//                "Persistence test::TestPersistence \n" +
//                "{\n" +
//                "  doc: 'This is test documentation.';\n" +
//                "  doc: 'This is test documentation.';\n" +
//                "  trigger: Manual;\n" +
//                "  service: test::Service;\n" +
//                "  persister: Batch\n" +
//                "  {\n" +
//                "    targetShape: Flat\n" +
//                "    {\n" +
//                "      targetName: 'TestDataset1';\n" +
//                "      modelClass: test::ModelClass;\n" +
//                "    }\n" +
//                "    ingestMode: AppendOnly\n" +
//                "    {\n" +
//                "      auditing: None;\n" +
//                "      filterDuplicates: false;\n" +
//                "    }\n" +
//                "  }\n" +
//                "}\n", "PARSER error at [3:1-22:1]: Field 'doc' should be specified only once");
//    }
}
