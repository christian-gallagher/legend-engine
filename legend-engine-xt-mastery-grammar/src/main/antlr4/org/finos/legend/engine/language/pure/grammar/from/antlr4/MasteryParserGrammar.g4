parser grammar MasteryParserGrammar;

import M3ParserGrammar;

options
{
    tokenVocab = MasteryLexerGrammar;
}

// -------------------------------------- IDENTIFIER --------------------------------------

identifier:                                 VALID_STRING | STRING
                                            | TRUE | FALSE
                                            | MASTER_RECORD_DEFINITION | MODEL_CLASS
;

// -------------------------------------- DEFINITION --------------------------------------

definition:                                 //imports
                                            (mastery)*
                                            EOF
;
//TODO gallch
//imports:                                    (importStatement)*
//;
//importStatement:                            IMPORT packagePath PATH_SEPARATOR STAR SEMI_COLON
//;
mastery:                                    MASTER_RECORD_DEFINITION qualifiedName
                                                BRACE_OPEN
                                                (
                                                    modelClass
                                                    | identityResolution
                                                )*
                                                BRACE_CLOSE
;
modelClass:                                 MODEL_CLASS COLON qualifiedName SEMI_COLON
;
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
                                                    (lambdaFunction (COMMA lambdaFunction)*) //originally used
                                                    //(combinedExpression (COMMA combinedExpression)*) //originally used
                                                BRACKET_CLOSE
                                            SEMI_COLON
;

//ISLAND approach
//queryExpressions:                           RESOLUTION_QUERY_EXPRESSIONS COLON queryExpressionsIsland  //todo - to lambda function
//;
//queryExpressionsIsland:                      ISLAND_OPEN (queryExpressionsContent)*
//;
//queryExpressionsContent:                    ISLAND_START | ISLAND_BRACE_OPEN | ISLAND_CONTENT | ISLAND_HASH | ISLAND_BRACE_CLOSE | ISLAND_END
//;

//queries : #{ #{ { content

//#{\n" +
//"        JsonModelConnection\n" +
//"        {\n" +
//"          class: org::dxl::Animal;\n" +
//"          url: 'my_url2';\n" +
//"        }\n" +
//"      }#\n" +




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

