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

package org.finos.legend.engine.testable.mastery.extension;

import java.sql.Connection;
//import org.finos.legend.engine.mastery.components.ingestmode.IngestMode;
//import org.finos.legend.engine.mastery.components.logicalplan.datasets.Dataset;
//import org.finos.legend.engine.mastery.components.logicalplan.datasets.DatasetDefinition;
//import org.finos.legend.engine.mastery.components.common.Datasets;
import org.eclipse.collections.impl.utility.ListIterate;
import org.finos.legend.engine.language.pure.compiler.toPureGraph.PureModel;
//import org.finos.legend.engine.mastery.components.relational.api.IngestorResult;
//import org.finos.legend.engine.mastery.components.relational.api.RelationalIngestor;
//import org.finos.legend.engine.mastery.components.relational.h2.H2Sink;
import org.finos.legend.engine.plan.execution.PlanExecutor;
import org.finos.legend.engine.protocol.pure.v1.model.context.PureModelContextData;
import org.finos.legend.engine.protocol.pure.v1.model.data.ExternalFormatData;
//import org.finos.legend.engine.protocol.pure.v1.model.packageableElement.mastery.Mastery;
//import org.finos.legend.engine.protocol.pure.v1.model.packageableElement.mastery.test.ConnectionTestData;
//import org.finos.legend.engine.protocol.pure.v1.model.packageableElement.mastery.test.MasteryTest;
//import org.finos.legend.engine.protocol.pure.v1.model.packageableElement.mastery.test.MasteryTestBatch;
//import org.finos.legend.engine.protocol.pure.v1.model.packageableElement.mastery.test.TestData;
import org.finos.legend.engine.protocol.pure.v1.model.packageableElement.mastery.MasterRecordDefinition;
import org.finos.legend.engine.protocol.pure.v1.model.test.AtomicTestId;
import org.finos.legend.engine.protocol.pure.v1.model.test.assertion.TestAssertion;
import org.finos.legend.engine.protocol.pure.v1.model.test.assertion.status.AssertFail;
import org.finos.legend.engine.protocol.pure.v1.model.test.assertion.status.AssertPass;
import org.finos.legend.engine.protocol.pure.v1.model.test.assertion.status.AssertionStatus;
import org.finos.legend.engine.protocol.pure.v1.model.test.result.TestError;
import org.finos.legend.engine.protocol.pure.v1.model.test.result.TestFailed;
import org.finos.legend.engine.protocol.pure.v1.model.test.result.TestPassed;
import org.finos.legend.engine.protocol.pure.v1.model.test.result.TestResult;
import org.finos.legend.engine.testable.extension.TestRunner;
import org.finos.legend.engine.testable.mastery.assertion.MasteryTestAssertionEvaluator;
//import org.finos.legend.engine.testable.mastery.mapper.DatasetMapper;
//import org.finos.legend.engine.testable.mastery.mapper.IngestModeMapper;
//import org.finos.legend.pure.generated.Root_meta_pure_mastery_metamodel_Mastery;
import org.finos.legend.pure.generated.Root_meta_pure_mastery_metamodel_MasterRecordDefinition;
import org.finos.legend.pure.generated.Root_meta_pure_test_AtomicTest;
import org.finos.legend.pure.generated.Root_meta_pure_test_TestSuite;

import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.Map;

import static org.finos.legend.engine.language.pure.compiler.toPureGraph.HelperModelBuilder.getElementFullPath;

public class MasteryTestRunner implements TestRunner
{
    public static final boolean CLEAN_STAGING_DATA_DEFAULT = true;
    public static final boolean STATS_COLLECTION_DEFAULT = true;
    public static final boolean SCHEMA_EVOLUTION_DEFAULT = false;

    private Root_meta_pure_mastery_metamodel_MasterRecordDefinition pureMasterRecordDefinition;
    private PlanExecutor planExecutor;
    private String pureVersion;

    public MasteryTestRunner(Root_meta_pure_mastery_metamodel_MasterRecordDefinition pureMastery, String pureVersion)
    {
        this.pureMasterRecordDefinition = pureMastery;
        this.planExecutor = PlanExecutor.newPlanExecutorWithAvailableStoreExecutors();
        this.pureVersion = pureVersion;
    }

    @Override
    public TestResult executeAtomicTest(Root_meta_pure_test_AtomicTest atomicTest, PureModel pureModel, PureModelContextData data)
    {
//        TestResult result;
//        Mastery mastery = ListIterate.detect(data.getElementsOfType(Mastery.class), ele -> ele.getPath().equals(getElementFullPath(pureMasterRecordDefinition, pureModel.getExecutionSupport())));
//        MasteryTest masteryTest = mastery.tests.stream().filter(test -> test.id.equals(atomicTest._id())).findFirst().get();
//        List<MasteryTestBatch> testBatches = masteryTest.testBatches;
//
//        AtomicTestId atomicTestId = new AtomicTestId();
//        atomicTestId.atomicTestId = atomicTest._id();
//        atomicTestId.testSuiteId = "";
//
//        MasteryTestH2Connection masteryTestH2Connection = new MasteryTestH2Connection();
//        Connection connection = masteryTestH2Connection.getConnection();
//
//        try
//        {
//            Dataset targetDataset = DatasetMapper.getTargetDataset(pureMasterRecordDefinition);
//            DatasetDefinition datasetDefinition = (DatasetDefinition) targetDataset;
//
//            List<AssertionStatus> assertStatuses = new ArrayList<>();
//            Set<String> fieldsToIgnore = IngestModeMapper.getFieldsToIgnore(mastery);
//            boolean isTransactionMilestoningTimeBased = IngestModeMapper.isTransactionMilestoningTimeBased(mastery);
//
//            if (!(masteryTest.isTestDataFromServiceOutput))
//            {
//                throw new UnsupportedOperationException(String.format("Mastery Test %s " +
//                        "isTestDataFromServiceOutput = %s is not supported", atomicTest._id(), masteryTest.isTestDataFromServiceOutput));
//            }
//
//            // Loop over each testBatch
//            int batchId = 0;
//            for (MasteryTestBatch testBatch : testBatches)
//            {
//                for (TestAssertion testAssertion : testBatch.assertions)
//                {
//                    AssertionStatus batchAssertionStatus = new AssertPass();
//                    if (testAssertion != null)
//                    {
//                        // Retrieve testData
//                        String testDataString = getConnectionTestData(testBatch.testData);
//                        invokeMastery(targetDataset, mastery, testDataString, connection);
//                        List<Map<String, Object>> output = masteryTestH2Connection.readTable(datasetDefinition);
//
//                        batchAssertionStatus = testAssertion.accept(new MasteryTestAssertionEvaluator(output, fieldsToIgnore));
//                    }
//                    assertStatuses.add(batchAssertionStatus);
//                    if (isTransactionMilestoningTimeBased && ++batchId < testBatches.size())
//                    {
//                        // Sleep to avoid test batches having same IN_Z
//                        Thread.sleep(1000);
//                    }
//                }
//            }
//            // Construct the Test Result
//            result = constructTestResult(atomicTestId, mastery.getPath(), assertStatuses);
//        }
//        catch (Exception exception)
//        {
//            TestError testError = new TestError();
//            testError.atomicTestId = atomicTestId;
//            testError.error = exception.getMessage();
//            result = testError;
//        }
//        finally
//        {
//            masteryTestH2Connection.closeConnection();
//        }
//        return result;
        return null;
    }

    private void invokeMastery()
//    private IngestorResult invokeMastery(Dataset targetDataset, MasterRecordDefinition mastery, String testData,
//                                         Connection connection) throws Exception
    {
//        Dataset stagingDataset = DatasetMapper.getStagingDataset(testData, pureMasterRecordDefinition);
//        IngestMode ingestMode = IngestModeMapper.from(mastery, targetDataset, stagingDataset);
//
//        RelationalIngestor ingestor = RelationalIngestor.builder()
//                .ingestMode(ingestMode)
//                .relationalSink(H2Sink.get())
//                .cleanupStagingData(CLEAN_STAGING_DATA_DEFAULT)
//                .collectStatistics(STATS_COLLECTION_DEFAULT)
//                .enableSchemaEvolution(SCHEMA_EVOLUTION_DEFAULT)
//                .build();
//
//        Datasets datasets = Datasets.of(targetDataset, stagingDataset);
//        IngestorResult result = ingestor.ingest(connection, datasets);
//        return result;
    }

    private TestResult constructTestResult(AtomicTestId atomicTestId, String testable, List<AssertionStatus> assertionStatuses)
    {
        TestResult testResult;
        List<AssertFail> failedAsserts = ListIterate.selectInstancesOf(assertionStatuses, AssertFail.class);
        if (failedAsserts.isEmpty())
        {
            testResult = new TestPassed();
        }
        else
        {
            TestFailed testFailed = new TestFailed();
            testFailed.assertStatuses = assertionStatuses;
            testResult = testFailed;
        }

        testResult.atomicTestId = atomicTestId;
        testResult.testable = testable;
        return testResult;
    }

//    private String getConnectionTestData(TestData testData)
//    {
//        ConnectionTestData conTestData = testData.connection;
//        if (conTestData.data instanceof ExternalFormatData)
//        {
//            ExternalFormatData externalFormatData = (ExternalFormatData) conTestData.data;
//            String testDataString = externalFormatData.data;
//            return testDataString;
//        }
//        else
//        {
//            throw new UnsupportedOperationException("Non ExternalFormatData is not supported");
//        }
//    }

    @Override
    public List<TestResult> executeTestSuite(Root_meta_pure_test_TestSuite testSuite, List<AtomicTestId> atomicTestIds, PureModel pureModel, PureModelContextData data)
    {
        throw new UnsupportedOperationException("TestSuite is not supported for Mastery");
    }
}