lexer grammar MasteryLexerGrammar;

import M3LexerGrammar;
//import DomainLexerGrammar;

// -------------------------------------- KEYWORD --------------------------------------

// COMMON
TRUE:                                       'true';
FALSE:                                      'false';
IMPORT:                                     'import';

//**********
// MASTERY
//**********

//MASTERY:                                    'Mastery';
MASTER_RECORD_DEFINITION:                       'MasterRecordDefinition';
MODEL_CLASS:                                    'modelClass';

IDENTITIY_RESOLUTION:                           'identityResolution';

RESOLUTION_QUERIES:                             'resolutionQueries';
RESOLUTION_QUERY_EXPRESSIONS:                   'queries';
RESOLUTION_QUERY_PRECEDENCE:                    'precedence';
RESOLUTION_QUERY_KEY_TYPE:                      'keyType';
RESOLUTION_QUERY_KEY_TYPE_GENERATED_PRIMARY_KEY:'GeneratedPrimaryKey'; //Validated against equality key to ensure an acrual PK and fail if don't find match
RESOLUTION_QUERY_KEY_TYPE_SUPPLIED_PRIMARY_KEY: 'SuppliedPrimaryKey'; //Validated against equality key to ensure an actuial PK and create if don't find match
RESOLUTION_QUERY_KEY_TYPE_ALTERNATE_KEY:        'AlternateKey'; //AlternateKey (In an AlternateKey is specified then at least one required in the input record or fail resolution). AlternateKey && (CurationModel field == Create) then the input source is attempting to create a new record (e.g. from UI) block if existing record found
RESOLUTION_QUERY_KEY_TYPE_OPTIONAL:             'Optional';


//MAppings from original Master configs
//MandatoryIfPresent                    -> GeneratedPrimaryKey (Validated against Equality Key and fail if don't find match)
//OneMandatory  (Single supplied PK)    -> SuppliedPrimaryKey (Validated against equality key to ensure an actuial PK and create if don't find match)
//OneMandatory  (ALt key Group)         -> AlternateKey (In an AlternateKey is specified then at least one required in the input record or fail resolution)
//FieldMandatoryIfMRFoundReturnError    -> AlternateKey && (CurationModel field == Create) then the input source is attempting to create a new record (e.g. from UI) block if existing record found
//Optionanl                             -> Optional (Used for matching if supplied but no validation applied)



