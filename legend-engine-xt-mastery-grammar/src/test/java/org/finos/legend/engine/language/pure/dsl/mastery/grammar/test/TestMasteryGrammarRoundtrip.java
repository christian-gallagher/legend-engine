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

import org.finos.legend.engine.language.pure.grammar.test.TestGrammarRoundtrip;
import org.junit.Test;

public class TestMasteryGrammarRoundtrip extends TestGrammarRoundtrip.TestGrammarRoundtripTestSuite
{

    @Test
    public void masteryFlat()
    {
        String testInput = "###Mastery\n" +
                "MasterRecordDefinition alloy::mastery::WidgetMasterRecord" + "\n" +
                //"\nMasterRecordDefinition " + ListAdapter.adapt(keywords).makeString("::") + "\n" + //Fails on the use of import
                "{\n" +
                "  modelClass: alloy::mastery::Widget;\n" +
                "  identityResolution: \n" +
                "  {\n" +
                "    modelClass: alloy::mastery::Widget;\n" +
                "    resolutionQueries:\n" +
                "      [\n" +
                "        {\n" +
                "          queries: [ {widget: test::Widget[1]|test::Widget.all()->filter(input|$widget.widgetId == $input.widgetid)},\n" +
                "                     {widget: test::Widget[1]|test::Widget.topLevelMilestonedIdentifier.identifierType == test::TopLevelMileStonedSIdentifier.TopLevelIdentifier1},\n" +
                "                     {widget: test::Widget[1]|$widget.topLevelMilestonedIdentifier.FROM_Z->toOne() <= $EFFECTIVE_DATE}\n" +
                "                   ];\n" +
                "          keyType: Optional;\n" +
                "          precedence: 1;\n" +
                "        },\n" +
                "        {\n" +
                "          queries: [ {wodgit: test::Widget[1]|test::Widget.all()->filter(input|$widget.widgetId == $input.widgetid)}\n" +
                "                   ];\n" +
                "          keyType: GeneratedPrimaryKey;\n" +
                "          precedence: 1;\n" +
                "        }\n" +
                "      ]\n" +
                "  }\n" +
                "}\n";
        System.out.println(testInput);
        test(testInput);
    }

    //@Test
//    public void permitOptionalFieldsToBeEmptyFlat()
//    {
//        test("###Persistence\n" +
//                "import test::*;\n" +
//                "Persistence test::TestPersistence\n" +
//                "{\n" +
//                "  doc: 'test doc';\n" +
//                "  trigger: Manual;\n" +
//                "  service: test::service::Service;\n" +
//                "  persister: Batch\n" +
//                "  {\n" +
//                "    sink: Relational\n" +
//                "    {\n" +
//                "    }\n" +
//                "    ingestMode: BitemporalSnapshot\n" +
//                "    {\n" +
//                "      transactionMilestoning: BatchIdAndDateTime\n" +
//                "      {\n" +
//                "        batchIdInName: 'batchIdIn';\n" +
//                "        batchIdOutName: 'batchIdOut';\n" +
//                "        dateTimeInName: 'inZ';\n" +
//                "        dateTimeOutName: 'outZ';\n" +
//                "      }\n" +
//                "      validityMilestoning: DateTime\n" +
//                "      {\n" +
//                "        dateTimeFromName: 'FROM_Z';\n" +
//                "        dateTimeThruName: 'THRU_Z';\n" +
//                "        derivation: SourceSpecifiesFromDateTime\n" +
//                "        {\n" +
//                "          sourceDateTimeFromField: sourceFrom;\n" +
//                "        }\n" +
//                "      }\n" +
//                "    }\n" +
//                "    targetShape: Flat\n" +
//                "    {\n" +
//                "      modelClass: test::ModelClass;\n" +
//                "      targetName: 'TestDataset1';\n" +
//                "    }\n" +
//                "  }\n" +
//                "}\n");
//    }
//
//    //@Test
//    public void permitOptionalFieldsToBeEmptyMultiFlat()
//    {
//        test("###Persistence\n" +
//                "import test::*;\n" +
//                "Persistence test::TestPersistence\n" +
//                "{\n" +
//                "  doc: 'test doc';\n" +
//                "  trigger: Manual;\n" +
//                "  service: test::service::Service;\n" +
//                "  persister: Batch\n" +
//                "  {\n" +
//                "    sink: Relational\n" +
//                "    {\n" +
//                "    }\n" +
//                "    ingestMode: BitemporalDelta\n" +
//                "    {\n" +
//                "      mergeStrategy: DeleteIndicator\n" +
//                "      {\n" +
//                "        deleteField: deleted;\n" +
//                "        deleteValues: ['Y', '1', 'true'];\n" +
//                "      }\n" +
//                "      transactionMilestoning: DateTime\n" +
//                "      {\n" +
//                "        dateTimeInName: 'inZ';\n" +
//                "        dateTimeOutName: 'outZ';\n" +
//                "      }\n" +
//                "      validityMilestoning: DateTime\n" +
//                "      {\n" +
//                "        dateTimeFromName: 'FROM_Z';\n" +
//                "        dateTimeThruName: 'THRU_Z';\n" +
//                "        derivation: SourceSpecifiesFromAndThruDateTime\n" +
//                "        {\n" +
//                "          sourceDateTimeFromField: sourceFrom;\n" +
//                "          sourceDateTimeThruField: sourceThru;\n" +
//                "        }\n" +
//                "      }\n" +
//                "    }\n" +
//                "    targetShape: MultiFlat\n" +
//                "    {\n" +
//                "      modelClass: test::WrapperClass;\n" +
//                "      transactionScope: ALL_TARGETS;\n" +
//                "      parts:\n" +
//                "      [\n" +
//                "        {\n" +
//                "          modelProperty: property1;\n" +
//                "          targetName: 'TestDataset1';\n" +
//                "        },\n" +
//                "        {\n" +
//                "          modelProperty: property3;\n" +
//                "          targetName: 'TestDataset2';\n" +
//                "        }\n" +
//                "      ];\n" +
//                "    }\n" +
//                "  }\n" +
//                "}\n");
//    }
//
//    //@Test
//    public void persistenceFlat()
//    {
//        test("###Persistence\n" +
//                "import test::*;\n" +
//                "Persistence test::TestPersistence\n" +
//                "{\n" +
//                "  doc: 'test doc';\n" +
//                "  trigger: Manual;\n" +
//                "  service: test::service::Service;\n" +
//                "  persister: Batch\n" +
//                "  {\n" +
//                "    sink: Relational\n" +
//                "    {\n" +
//                "      connection:\n" +
//                "      #{\n" +
//                "        JsonModelConnection\n" +
//                "        {\n" +
//                "          class: org::dxl::Animal;\n" +
//                "          url: 'my_url2';\n" +
//                "        }\n" +
//                "      }#\n" +
//                "    }\n" +
//                "    ingestMode: BitemporalSnapshot\n" +
//                "    {\n" +
//                "      transactionMilestoning: BatchIdAndDateTime\n" +
//                "      {\n" +
//                "        batchIdInName: 'batchIdIn';\n" +
//                "        batchIdOutName: 'batchIdOut';\n" +
//                "        dateTimeInName: 'inZ';\n" +
//                "        dateTimeOutName: 'outZ';\n" +
//                "        derivation: SourceSpecifiesInDateTime\n" +
//                "        {\n" +
//                "          sourceDateTimeInField: sourceIn;\n" +
//                "        }\n" +
//                "      }\n" +
//                "      validityMilestoning: DateTime\n" +
//                "      {\n" +
//                "        dateTimeFromName: 'FROM_Z';\n" +
//                "        dateTimeThruName: 'THRU_Z';\n" +
//                "        derivation: SourceSpecifiesFromDateTime\n" +
//                "        {\n" +
//                "          sourceDateTimeFromField: sourceFrom;\n" +
//                "        }\n" +
//                "      }\n" +
//                "    }\n" +
//                "    targetShape: Flat\n" +
//                "    {\n" +
//                "      modelClass: test::ModelClass;\n" +
//                "      targetName: 'TestDataset1';\n" +
//                "      partitionFields: [propertyA, propertyB];\n" +
//                "      deduplicationStrategy: MaxVersion\n" +
//                "      {\n" +
//                "        versionField: version;\n" +
//                "      }\n" +
//                "    }\n" +
//                "  }\n" +
//                "  notifier:\n" +
//                "  {\n" +
//                "    notifyees:\n" +
//                "    [\n" +
//                "      Email\n" +
//                "      {\n" +
//                "        address: 'x.y@z.com';\n" +
//                "      },\n" +
//                "      PagerDuty\n" +
//                "      {\n" +
//                "        url: 'https://x.com';\n" +
//                "      }\n" +
//                "    ]\n" +
//                "  }\n" +
//                "}\n");
//    }
//
//    //@Test
//    public void persistenceMultiFlat()
//    {
//        test("###Persistence\n" +
//                "import test::*;\n" +
//                "Persistence test::TestPersistence\n" +
//                "{\n" +
//                "  doc: 'test doc';\n" +
//                "  trigger: Manual;\n" +
//                "  service: test::service::Service;\n" +
//                "  persister: Batch\n" +
//                "  {\n" +
//                "    sink: ObjectStorage\n" +
//                "    {\n" +
//                "      binding: test::Binding;\n" +
//                "      connection:\n" +
//                "      #{\n" +
//                "        JsonModelConnection\n" +
//                "        {\n" +
//                "          class: org::dxl::Animal;\n" +
//                "          url: 'my_url2';\n" +
//                "        }\n" +
//                "      }#\n" +
//                "    }\n" +
//                "    ingestMode: BitemporalDelta\n" +
//                "    {\n" +
//                "      mergeStrategy: DeleteIndicator\n" +
//                "      {\n" +
//                "        deleteField: deleted;\n" +
//                "        deleteValues: ['Y', '1', 'true'];\n" +
//                "      }\n" +
//                "      transactionMilestoning: DateTime\n" +
//                "      {\n" +
//                "        dateTimeInName: 'inZ';\n" +
//                "        dateTimeOutName: 'outZ';\n" +
//                "        derivation: SourceSpecifiesInAndOutDateTime\n" +
//                "        {\n" +
//                "          sourceDateTimeInField: sourceIn;\n" +
//                "          sourceDateTimeOutField: sourceOut;\n" +
//                "        }\n" +
//                "      }\n" +
//                "      validityMilestoning: DateTime\n" +
//                "      {\n" +
//                "        dateTimeFromName: 'FROM_Z';\n" +
//                "        dateTimeThruName: 'THRU_Z';\n" +
//                "        derivation: SourceSpecifiesFromAndThruDateTime\n" +
//                "        {\n" +
//                "          sourceDateTimeFromField: sourceFrom;\n" +
//                "          sourceDateTimeThruField: sourceThru;\n" +
//                "        }\n" +
//                "      }\n" +
//                "    }\n" +
//                "    targetShape: MultiFlat\n" +
//                "    {\n" +
//                "      modelClass: test::WrapperClass;\n" +
//                "      transactionScope: ALL_TARGETS;\n" +
//                "      parts:\n" +
//                "      [\n" +
//                "        {\n" +
//                "          modelProperty: property1;\n" +
//                "          targetName: 'TestDataset1';\n" +
//                "          partitionFields: [propertyA, propertyB];\n" +
//                "          deduplicationStrategy: AnyVersion;\n" +
//                "        },\n" +
//                "        {\n" +
//                "          modelProperty: property2;\n" +
//                "          targetName: 'TestDataset1';\n" +
//                "          partitionFields: [propertyA, propertyB];\n" +
//                "          deduplicationStrategy: DuplicateCount\n" +
//                "          {\n" +
//                "            duplicateCountName: 'duplicateCount';\n" +
//                "          }\n" +
//                "        },\n" +
//                "        {\n" +
//                "          modelProperty: property3;\n" +
//                "          targetName: 'TestDataset2';\n" +
//                "        }\n" +
//                "      ];\n" +
//                "    }\n" +
//                "  }\n" +
//                "  notifier:\n" +
//                "  {\n" +
//                "    notifyees:\n" +
//                "    [\n" +
//                "      Email\n" +
//                "      {\n" +
//                "        address: 'x.y@z.com';\n" +
//                "      },\n" +
//                "      PagerDuty\n" +
//                "      {\n" +
//                "        url: 'https://x.com';\n" +
//                "      }\n" +
//                "    ]\n" +
//                "  }\n" +
//                "}\n");
//    }
}
