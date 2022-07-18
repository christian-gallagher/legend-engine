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

package org.finos.legend.engine.language.pure.dsl.mastery.compiler.toPureGraph;

import org.eclipse.collections.impl.utility.Iterate;
import org.eclipse.collections.impl.utility.ListIterate;
import org.finos.legend.engine.language.pure.compiler.toPureGraph.CompileContext;
import org.finos.legend.engine.language.pure.compiler.toPureGraph.HelperValueSpecificationBuilder;
import org.finos.legend.engine.protocol.pure.v1.model.packageableElement.mastery.MasterRecordDefinition;
import org.finos.legend.engine.protocol.pure.v1.model.packageableElement.mastery.resolution.IdentityResolution;
import org.finos.legend.engine.protocol.pure.v1.model.packageableElement.mastery.resolution.IdentityResolutionVisitor;
import org.finos.legend.engine.protocol.pure.v1.model.packageableElement.mastery.resolution.ResolutionQuery;
//import org.finos.legend.pure.generated.;
import org.finos.legend.pure.generated.Root_meta_pure_mastery_metamodel_resolution_IdentityResolution;
import org.finos.legend.pure.generated.Root_meta_pure_mastery_metamodel_resolution_IdentityResolution_Impl;
import org.finos.legend.pure.generated.Root_meta_pure_mastery_metamodel_resolution_ResolutionQuery;
import org.finos.legend.pure.generated.Root_meta_pure_mastery_metamodel_resolution_ResolutionQuery_Impl;
import org.finos.legend.pure.m3.coreinstance.Package;
import org.finos.legend.pure.m3.coreinstance.meta.pure.metamodel.PackageableElement;
import org.finos.legend.pure.m3.coreinstance.meta.pure.metamodel.type.Class;
import org.finos.legend.pure.m3.coreinstance.meta.pure.metamodel.type.Type;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;

public class HelperMasterRecordDefinitionBuilder
{
    private static final String MASTERY_PACKAGE_PREFIX = "meta::pure::mastery::metamodel";
    private static final IdentityResolutionBuilder IDENTITY_RESOLUTION_BUILDER = new IdentityResolutionBuilder();

    private HelperMasterRecordDefinitionBuilder()
    {
    }

    public static org.finos.legend.pure.m3.coreinstance.meta.pure.metamodel.type.Class buildModelClass(MasterRecordDefinition val, CompileContext context)
    {

        Class<?> modelClass = context.resolveClass(val.modelClass);
        return modelClass;
    }

    private static String determineFullPath(Type type)
    {
        Deque<String> deque = new ArrayDeque<>();
        Package currentPackage = ((PackageableElement) type)._package();
        while (!currentPackage._name().equals("Root"))
        {
            deque.push(currentPackage._name());
            currentPackage = currentPackage._package();
        }

        return Iterate.makeString(deque, "", "::", "::" + type._name());
    }

    public static Root_meta_pure_mastery_metamodel_resolution_IdentityResolution buildIdentityResolution(IdentityResolution identityResolution, CompileContext context)
    {
        IDENTITY_RESOLUTION_BUILDER.context = context;
        return identityResolution.accept(IDENTITY_RESOLUTION_BUILDER);
    }

    private static class IdentityResolutionBuilder implements IdentityResolutionVisitor<Root_meta_pure_mastery_metamodel_resolution_IdentityResolution>
    {
        private CompileContext context;

        @Override
        public Root_meta_pure_mastery_metamodel_resolution_IdentityResolution visit(IdentityResolution protocolVal)
        {
            Root_meta_pure_mastery_metamodel_resolution_IdentityResolution_Impl resImpl = new Root_meta_pure_mastery_metamodel_resolution_IdentityResolution_Impl("");
            resImpl._modelClass(context.resolveClass(protocolVal.modelClass));
            resImpl._resolutionQueriesAddAll(ListIterate.flatCollect(protocolVal.resolutionQueries, this::visitResolutionQuery));
            return resImpl;
        }

        private Iterable<Root_meta_pure_mastery_metamodel_resolution_ResolutionQuery> visitResolutionQuery(ResolutionQuery protocolQuery)
        {
            ArrayList<Root_meta_pure_mastery_metamodel_resolution_ResolutionQuery> list = new ArrayList<>();
            Root_meta_pure_mastery_metamodel_resolution_ResolutionQuery resQuery = new Root_meta_pure_mastery_metamodel_resolution_ResolutionQuery_Impl("");

            String KEY_TYPE_FULL_PATH = MASTERY_PACKAGE_PREFIX + "::resolution::ResolutionKeyType";
            resQuery._keyType(context.resolveEnumValue(KEY_TYPE_FULL_PATH, protocolQuery.keyType.name()));
            resQuery._precedence(protocolQuery.precedence);

            ListIterate.forEachWithIndex(protocolQuery.queries, (lambda, i) ->
            {
                resQuery._queriesAdd(HelperValueSpecificationBuilder.buildLambda(lambda, context));
            });

            list.add(resQuery);
            return list;
        }
    }
}