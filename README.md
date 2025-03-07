[![FINOS - Incubating](https://cdn.jsdelivr.net/gh/finos/contrib-toolbox@master/images/badge-incubating.svg)](https://finosfoundation.atlassian.net/wiki/display/FINOS/Incubating)
[![Maven Central](https://img.shields.io/maven-central/v/org.finos.legend.engine/legend-engine-server.svg)](http://search.maven.org/#search%7Cga%7C1%7Ca%3A%22legend-engine)
![Build CI](https://github.com/finos/legend-engine/workflows/Build%20CI/badge.svg)
[![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=legend-engine&metric=security_rating&token=cbbc6d136c4f5671324244170afb9f0a6c22a7fb)](https://sonarcloud.io/dashboard?id=legend-engine)
[![Bugs](https://sonarcloud.io/api/project_badges/measure?project=legend-engine&metric=bugs&token=cbbc6d136c4f5671324244170afb9f0a6c22a7fb)](https://sonarcloud.io/dashboard?id=legend-engine)

# legend-engine

Execution engine for Legend. It provides:
- A Pure parser and compiler.
- An execution engine, generating and/or executing execution plans when provided with a Pure function, a Mapping and a Runtime.
- Access point for model transformers written using the Pure language (soon to come).

## Development setup

- This applications uses Maven 3.6+ and JDK 11. Simply run `mvn install` to compile.
- In order to start the server, please use the `Main` class `org.finos.legend.engine.server.Server` with the parameters: `server legend-engine-server/src/test/resources/org/finos/legend/engine/server/test/userTestConfig.json`.
- You can test by trying http://127.0.0.1:6300 in a web browser. The swagger page can be accessed at http://127.0.0.1:6300/api/swagger

### Starting Pure IDE

If you're making changes to the `Pure` codebase, it's highly recommended that you also spin up our `Pure IDE` application:

- In order to start the server, please use the `Main` class `org.finos.legend.engine.ide.PureIDELight` with the parameters: `server legend-engine-pure-ide-light/src/main/resources/ideLightConfig.json`.
- You can now access the IDE at http://127.0.0.1:9100/ide in a web browser.

## Roadmap

Visit our [roadmap](https://github.com/finos/legend#roadmap) to know more about the upcoming features.

## Contributing

Visit Legend [Contribution Guide](https://github.com/finos/legend/blob/master/CONTRIBUTING.md) to learn how to contribute to Legend.

## License

Copyright 2020 Goldman Sachs

Distributed under the [Apache License, Version 2.0](http://www.apache.org/licenses/LICENSE-2.0).

SPDX-License-Identifier: [Apache-2.0](https://spdx.org/licenses/Apache-2.0)

