parser grammar MasteryParserGrammar;

import M3ParserGrammar;

options
{
    tokenVocab = MasteryLexerGrammar;
}

// -------------------------------------- IDENTIFIER --------------------------------------

identifier:                                 VALID_STRING | STRING
                                            | TRUE | FALSE
                                            | MASTER_RECORD_DEFINITION | MODEL_CLASS | RECORD_SOURCES | SOURCE_PARTITIONS
;

masteryIdentifier:                          (VALID_STRING | '-' | INTEGER) (VALID_STRING | '-' | INTEGER)*;

// -------------------------------------- DEFINITION --------------------------------------

definition:                                 //imports
                                            (mastery)*
                                            EOF
;
imports:                                    (importStatement)*
;
importStatement:                            IMPORT packagePath PATH_SEPARATOR STAR SEMI_COLON
;

// -------------------------------------- COMMON --------------------------------------

boolean_value:                              TRUE | FALSE
;
modelClass:                                 MODEL_CLASS COLON qualifiedName SEMI_COLON
;
id:                                         ID COLON STRING SEMI_COLON
;
description:                                DESCRIPTION COLON STRING SEMI_COLON
;
tags:                                       TAGS COLON
                                            BRACKET_OPEN
                                            (
                                                STRING (COMMA STRING)*
                                            )*
                                            BRACKET_CLOSE
                                            SEMI_COLON
;

// -------------------------------------- MASTER_RECORD_DEFIMNITION --------------------------------------

mastery:                                    MASTER_RECORD_DEFINITION qualifiedName
                                                BRACE_OPEN
                                                (
                                                    modelClass
                                                    | identityResolution
                                                    | recordSources
                                                    | masterRecordTests
                                                )*
                                                BRACE_CLOSE
;

// -------------------------------------- RECORD_SOURCES --------------------------------------

recordSources:                              RECORD_SOURCES COLON
                                            BRACKET_OPEN
                                            (
                                                recordSource
                                                (
                                                    COMMA
                                                    recordSource
                                                )*
                                            )
                                            BRACKET_CLOSE
;
recordSource:                               masteryIdentifier COLON BRACE_OPEN
                                            (
                                                recordStatus
                                                | description
                                                | parseService
                                                | transformService
                                                | sequentialData
                                                | stagedLoad
                                                | createPermitted
                                                | createBlockedException
                                                | tags
                                                | sourcePartitions
                                                | recordSourceTests
                                            )*
                                            BRACE_CLOSE
;
recordStatus:                               RECORD_SOURCE_STATUS COLON
                                            (
                                                RECORD_SOURCE_STATUS_DEVELOPMENT
                                                    | RECORD_SOURCE_STATUS_TEST_ONLY
                                                    | RECORD_SOURCE_STATUS_PRODUCTION
                                                    | RECORD_SOURCE_STATUS_DORMANT
                                                    | RECORD_SOURCE_STATUS_DECOMMINISSIONED
                                            )
                                            SEMI_COLON
;
sequentialData:                             RECORD_SOURCE_SEQUENTIAL COLON boolean_value SEMI_COLON
;
stagedLoad:                                 RECORD_SOURCE_STAGED COLON boolean_value SEMI_COLON
;
createPermitted:                            RECORD_SOURCE_CREATE_PERMITTED COLON boolean_value SEMI_COLON
;
createBlockedException:                     RECORD_SOURCE_CREATE_BLOCKED_EXCEPTION COLON boolean_value SEMI_COLON
;
parseService:                               PARSE_SERVICE COLON qualifiedName SEMI_COLON
;
transformService:                           TRANSFORM_SERVICE COLON qualifiedName SEMI_COLON
;
sourcePartitions:                           SOURCE_PARTITIONS COLON
                                            BRACKET_OPEN
                                            (
                                                sourcePartiton
                                                (
                                                    COMMA
                                                    sourcePartiton
                                                )*
                                            )
                                            BRACKET_CLOSE
;
sourcePartiton:                             masteryIdentifier COLON BRACE_OPEN
                                            (
                                                 tags
                                            )*
                                            BRACE_CLOSE
;


// -------------------------------------- RESOLUTION --------------------------------------

identityResolution:                         IDENTITIY_RESOLUTION COLON
                                            BRACE_OPEN
                                            (
                                                modelClass
                                                | resolutionQueries
                                            )*
                                            BRACE_CLOSE
;
resolutionQueries:                          RESOLUTION_QUERIES COLON
                                            BRACKET_OPEN
                                            (
                                                resolutionQuery
                                                (
                                                    COMMA
                                                    resolutionQuery
                                                )*
                                            )
                                            BRACKET_CLOSE
;
resolutionQuery:                            BRACE_OPEN
                                            (queryExpressions
                                             | resolutionQueryKeyType
                                             | resolutionQueryPrecedence
                                            )*
                                            BRACE_CLOSE
;

queryExpressions:                           RESOLUTION_QUERY_EXPRESSIONS COLON
                                                BRACKET_OPEN
                                                    (lambdaFunction (COMMA lambdaFunction)*)
                                                BRACKET_CLOSE
                                            SEMI_COLON
;

resolutionQueryKeyType:                  RESOLUTION_QUERY_KEY_TYPE COLON (
                                            RESOLUTION_QUERY_KEY_TYPE_GENERATED_PRIMARY_KEY
                                            | RESOLUTION_QUERY_KEY_TYPE_SUPPLIED_PRIMARY_KEY
                                            | RESOLUTION_QUERY_KEY_TYPE_ALTERNATE_KEY
                                            | RESOLUTION_QUERY_KEY_TYPE_OPTIONAL
                                            )
                                            SEMI_COLON
;
resolutionQueryPrecedence:               RESOLUTION_QUERY_PRECEDENCE COLON INTEGER SEMI_COLON
;


// -------------------------------------- TESTS --------------------------------------
// MAstery Record Tests
masterRecordTests:                      TESTS COLON BRACKET_OPEN ( masterRecordTest ( COMMA masterRecordTest )* )? BRACKET_CLOSE
;
masterRecordTest:                       masteryIdentifier COLON BRACE_OPEN
                                        (
                                             recordSourceTestReference
                                             | testAssertions
                                        )*
                                        BRACE_CLOSE
;
recordSourceTestReference:                MASTER_RECORD_SOURCE_TESTS COLON BRACKET_OPEN ( masteryIdentifier ( COMMA masteryIdentifier )* )? BRACKET_CLOSE
;
//recordSourceTestReference:              masteryIdentifier
//;

//Record Source Tests
recordSourceTests:                      TESTS COLON BRACKET_OPEN ( recordSourceTest ( COMMA recordSourceTest )* )? BRACKET_CLOSE
;
recordSourceTest:                       identifier COLON BRACE_OPEN
                                        (
                                              testData
                                             | testAssertions
                                        )*
                                        BRACE_CLOSE
;
//testId:                                 ID COLON STRING SEMI_COLON
//;
//recordSourceId:                         RECORD_SOURCE_ID COLON STRING SEMI_COLON
//;
testData:                               DATA COLON BRACE_OPEN embeddedData BRACE_CLOSE
;
embeddedData:                           identifier ISLAND_OPEN ( embeddedDataContent )*
;
embeddedDataContent:                    ISLAND_START | ISLAND_BRACE_OPEN | ISLAND_CONTENT | ISLAND_HASH | ISLAND_BRACE_CLOSE | ISLAND_END
;
testAssertions:                         TEST_ASSERTS COLON BRACKET_OPEN ( testAssert ( COMMA testAssert)* )? BRACKET_CLOSE
;
testAssert:                             identifier COLON testAssertion
;
testAssertion:                          identifier ISLAND_OPEN ( testAssertionContent )*
;
testAssertionContent:                   ISLAND_START | ISLAND_BRACE_OPEN | ISLAND_CONTENT | ISLAND_HASH | ISLAND_BRACE_CLOSE | ISLAND_END
;



