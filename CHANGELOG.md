# Changelog

## [1.1.0](https://github.com/teletha/reincarnation/compare/v1.0.0...v1.1.0) (2023-03-02)


### Features

* (Java) Hide wrapper method call. ([0ece9ec](https://github.com/teletha/reincarnation/commit/0ece9ec685b12c1d9027fd6c1b1b140b136e7f39))
* Drop Reincarnation#rebirth(Class). ([fa15a22](https://github.com/teletha/reincarnation/commit/fa15a221fefdf5d3b1a06c4d443530cb31ba3ece))
* Enable fernflower on debug mode. ([f3122d5](https://github.com/teletha/reincarnation/commit/f3122d5e3d441dcecf8efd5cd4981000732469e2))
* Operand matching engine supports optional value. ([3448d45](https://github.com/teletha/reincarnation/commit/3448d45c665c981343ca98886dcd8e0d60e94f0a))
* OperandUtil#load supports array related operands. ([c9cf309](https://github.com/teletha/reincarnation/commit/c9cf309dfe398c667adddfdd91ddf6a3630ec6f2))
* OperandUtil#load supports object array related operands. ([05d5f9c](https://github.com/teletha/reincarnation/commit/05d5f9c56c1b33be1706a41a03f26bf6d71f00df))
* Optimize boolean condition. ([a346267](https://github.com/teletha/reincarnation/commit/a3462678d9409b5095fb1dac0ed5271496760b9a))
* Optimize empty if or else block. ([7d4e1e3](https://github.com/teletha/reincarnation/commit/7d4e1e30c5c8a04e5ddaa9d78fbd2824144a4b67))
* Optimize instanceof with pattern matching. ([fc6366c](https://github.com/teletha/reincarnation/commit/fc6366cce254a8bb101a8ed320103d6cfa78bcb3))
* Provide Naming strategy. ([d8cb5fe](https://github.com/teletha/reincarnation/commit/d8cb5fe7e7adf3973fbceccb893933cfa96af4bc))
* Remove InferredType. ([a856c55](https://github.com/teletha/reincarnation/commit/a856c555fd66e0053ca3ea136f1fcd615ddc6384))
* Support duplicated variable usage. ([da7862e](https://github.com/teletha/reincarnation/commit/da7862e72a9dc5fe4e33b80aef2d13f0a3d379c9))
* Support enhanced for loop. ([10f1614](https://github.com/teletha/reincarnation/commit/10f1614740573ebcc609a3ba34211a71f7f82ed0))
* Support enhanced for-loop for primitive wrapper classes. ([7defc41](https://github.com/teletha/reincarnation/commit/7defc416589d720c62d6437335a00d16effc5785))
* Support enhanced for-loop with break. ([1e77f61](https://github.com/teletha/reincarnation/commit/1e77f61a4ba8ecadf21d5701da8d8c0ec703a512))
* Support negation of instanceof. ([982292d](https://github.com/teletha/reincarnation/commit/982292d987ea6591ce957b3245a8f8f50376e47b))
* Support original variable name if available. ([7d5dba6](https://github.com/teletha/reincarnation/commit/7d5dba685c63b4f7f8558107ed33ebc2fb27bfe3))
* Support pattern matching for instanceof. ([10f7218](https://github.com/teletha/reincarnation/commit/10f7218afe4edc67e6b0368c19d57b95956b846a))
* Support reusable variable declaration. ([1624248](https://github.com/teletha/reincarnation/commit/162424885b955e919c86171382190f5c2c13820d))
* Support string concatenation. ([58bd29d](https://github.com/teletha/reincarnation/commit/58bd29dbdfaef7dc3f171b2dd7ddc099442ecfe9))


### Bug Fixes

* Enable debuggable on class. ([e252e17](https://github.com/teletha/reincarnation/commit/e252e175332f4145a2c7b36cdd43508758b1b64b))
* OperandInstanceOf must infer its type as boolean. ([d27f813](https://github.com/teletha/reincarnation/commit/d27f813393ac10ad7ec4e7304f8bce0964b1f100))
* OperandLocalVariable must be stateless. ([84180f4](https://github.com/teletha/reincarnation/commit/84180f417172e2562f1a03945b2452489d35a098))
* Support ALOAD oprand in Util#load. ([4d5741c](https://github.com/teletha/reincarnation/commit/4d5741c05ee86318395f76a52e6393c057df010a))

## 1.0.0 (2023-02-23)


### Features

* debuggable annotaion is enabled at test class and method ([5476927](https://github.com/teletha/reincarnation/commit/5476927e9403dc4914e351f22e78227a21f6a480))
* Debugger is thread-safe. ([89e0935](https://github.com/teletha/reincarnation/commit/89e09354057c5572608f8302eb39901396d15f33))
* enhance debug message ([6e22ff8](https://github.com/teletha/reincarnation/commit/6e22ff88bb40109a12e69f3d989d4490d61d1b91))
* Failed test uses debug mode automatically. ([2cbff2e](https://github.com/teletha/reincarnation/commit/2cbff2e21e6ad29f1f83d426dbad2eda8272b7e5))
* Optimize the immediate return. ([e548b59](https://github.com/teletha/reincarnation/commit/e548b59e2c7cd74b604166900fe400686e473549))
* Optimize value assignment. ([35b62fd](https://github.com/teletha/reincarnation/commit/35b62fdc7b4a9910acf457ff5a7dabc0a3325f2c))
* Provide Decompiler API. ([74fb411](https://github.com/teletha/reincarnation/commit/74fb411a7912e313a0c58ae210001801866b05a0))


### Bug Fixes

* debug mode causes some error ([5b70343](https://github.com/teletha/reincarnation/commit/5b70343965d115d4306666ec7404d37c4717aaf4))
* Duplicated variable name. ([faba88e](https://github.com/teletha/reincarnation/commit/faba88e4d56ebe6a977e204b455fd213093ce924))
* Pass try-finally tests. ([df2eac3](https://github.com/teletha/reincarnation/commit/df2eac33026389b3a2f909dd336a54ea7ed941ab))
* Pass TryCatchFinally tests. ([ebf2574](https://github.com/teletha/reincarnation/commit/ebf2574895ca50278d122cf1bb3662463aa963a7))
* Update bee. ([28e91fb](https://github.com/teletha/reincarnation/commit/28e91fbd12e3cf6a01664b4aebfcf78d138ec85b))
* update viewtify ([1043693](https://github.com/teletha/reincarnation/commit/104369346dae6f0bf6f5637c2c30387f2e11484e))
