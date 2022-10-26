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

import org.finos.legend.engine.language.pure.compiler.toPureGraph.PureModel;
import org.finos.legend.engine.protocol.pure.PureClientVersions;
//import org.finos.legend.engine.protocol.pure.v1.MasteryProtocolExtension;
import org.finos.legend.engine.protocol.pure.v1.model.context.PureModelContextData;
import org.finos.legend.engine.testable.extension.TestRunner;
import org.finos.legend.engine.testable.extension.TestableRunnerExtension;
import org.finos.legend.engine.testable.model.RunTestsResult;
//import org.finos.legend.pure.generated.Root_meta_pure_mastery_metamodel_Mastery;
import org.finos.legend.pure.generated.Root_meta_pure_test_AtomicTest;
import org.finos.legend.pure.m3.coreinstance.meta.pure.test.Testable;


public class MasteryTestableRunnerExtension implements TestableRunnerExtension
{
    private String pureVersion = PureClientVersions.production;

    @Override
    public String getSupportedClassifierPath()
    {
//        return MasteryProtocolExtension.MASTERY_CLASSIFIER_PATH;
        return null;
    }

    @Override
    public TestRunner getTestRunner(Testable testable)
    {
//        if (testable instanceof Root_meta_pure_mastery_metamodel_Mastery)
//        {
//            return new MasteryTestRunner((Root_meta_pure_mastery_metamodel_Mastery) testable, pureVersion);
//        }
        return null;
    }

    public RunTestsResult executeMasteryTest(Testable testable, PureModel pureModel, PureModelContextData pureModelContextData)
    {
//        if (!(testable instanceof Root_meta_pure_mastery_metamodel_Mastery))
//        {
//            throw new UnsupportedOperationException("Expected Mastery testable. Found : " + testable.getName());
//        }
//
//        MasteryTestRunner testRunner = new MasteryTestRunner((Root_meta_pure_mastery_metamodel_Mastery) testable, pureVersion);
//
//        RunTestsResult runTestsResult = new RunTestsResult();
//        ((Root_meta_pure_mastery_metamodel_Mastery) testable)._tests().forEach(test ->
//        {
//            runTestsResult.results.add(testRunner.executeAtomicTest((Root_meta_pure_test_AtomicTest) test, pureModel, pureModelContextData));
//        });
//        return runTestsResult;
        return null;
    }

    public void setPureVersion(String pureVersion)
    {
        this.pureVersion = pureVersion;
    }
}