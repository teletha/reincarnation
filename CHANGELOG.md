# Changelog

## [1.4.0](https://github.com/teletha/reincarnation/compare/v1.3.0...v1.4.0) (2023-03-24)


### Features

* Omittable break label. ([1a30239](https://github.com/teletha/reincarnation/commit/1a302391b06390ec7a95e8ab027c0c6bca7953b8))
* Support escape sequence in string literal. ([8d86e8c](https://github.com/teletha/reincarnation/commit/8d86e8c6ebfac0a1e3ea4f1dd038f5c131b761ff))
* Support multiple cases on string switch. ([b622d2f](https://github.com/teletha/reincarnation/commit/b622d2f0083e1d399e29b15b2d2cdb1401b7bcfc))
* Support repeatable annotation. ([69f6a1f](https://github.com/teletha/reincarnation/commit/69f6a1f8e5f8956e770e887d3868efebe70377d7))
* Support switch statement by char. ([dd640c7](https://github.com/teletha/reincarnation/commit/dd640c7645c1b4691e5e4dceb936626c33263e44))
* Support switch statement by enum. ([59ef662](https://github.com/teletha/reincarnation/commit/59ef662bf62ea6ee23adbe5a6026e9f6e4fa67a6))
* Support switch statement by String. ([30898a0](https://github.com/teletha/reincarnation/commit/30898a0938ed6c9072fb5e5d0ba687b92c2da76b))
* Support switch statement for integral number. ([c5dbcca](https://github.com/teletha/reincarnation/commit/c5dbccaa5b717d626e8a15f8d24dd47412b1478e))
* Support switch statement partially. ([cf8b475](https://github.com/teletha/reincarnation/commit/cf8b4758cc9fd080a472053d8bcaebabbd0a3a46))
* Support text block. ([277da89](https://github.com/teletha/reincarnation/commit/277da891450bc3919ed2463d68e39583deb1fd5b))


### Bug Fixes

* Hide Debugger. ([45ce4a6](https://github.com/teletha/reincarnation/commit/45ce4a6e6904f329d7e72eb4c09d93c8459d57bc))
* Normalize the sequencial jump instructions. ([3972ec7](https://github.com/teletha/reincarnation/commit/3972ec7bcf632e755529215d2cc2e0851ad073d1))
* Omit empty default block on switch. ([a48a7f6](https://github.com/teletha/reincarnation/commit/a48a7f64b9b64a7dc27367cf4de28c983a3904cf))

## [1.3.0](https://github.com/teletha/reincarnation/compare/v1.2.0...v1.3.0) (2023-03-16)


### Features

* (Java) Optimize output for the implicit constructor. ([02664f1](https://github.com/teletha/reincarnation/commit/02664f12eb8211f53bc8a6615d002232b45fa40b))
* Support annotation on annotation and parameter. ([d2c5957](https://github.com/teletha/reincarnation/commit/d2c5957c22ab58c885b8927e146651406ce1f3c3))
* Support annotation on type, constructor, method and field. ([c0df1ca](https://github.com/teletha/reincarnation/commit/c0df1ca178e4132576bd64dadb06257dd0f44320))
* Support enhanced for loop by array. ([e70e99c](https://github.com/teletha/reincarnation/commit/e70e99c4104cdb79bed2a758bb05eadb43d6b438))
* Support enum features completely. ([b046aa1](https://github.com/teletha/reincarnation/commit/b046aa10e27b0ac7980ee9088b922be0fc804ecd))
* Support enum. ([1db1026](https://github.com/teletha/reincarnation/commit/1db1026c467c8c3b960b2eb7ec0b92d5af8437ec))
* Support extending class. ([e985499](https://github.com/teletha/reincarnation/commit/e9854996837eb472ffaa8ea620452be1a6addb50))
* Support GenericArrayType. ([8dae090](https://github.com/teletha/reincarnation/commit/8dae09051ecf9a8d86470d8d0e826316bd43164b))
* Support record features. ([82ddb15](https://github.com/teletha/reincarnation/commit/82ddb159273fca01574604716e57de99f1c4593a))
* Support sealed class. ([4b8bf11](https://github.com/teletha/reincarnation/commit/4b8bf11a72d42ac09ffb72c081a6db294a3788bd))


### Bug Fixes

* (Java) Optimize super() code writing. ([3e16baf](https://github.com/teletha/reincarnation/commit/3e16baf2252b89363ecf77625bf647ab55ea5bc1))
* Don't use Parameter#isSynthetic. ([b944714](https://github.com/teletha/reincarnation/commit/b9447141da7a86b03cef119d01842a6c9f465f5b))
* Hide OperandLocalVariable and Inference. ([a4cf1dd](https://github.com/teletha/reincarnation/commit/a4cf1dd07ec3a1dc0233a262c608b3ccdd3131f0))
* Hide some operand types. ([de4ebca](https://github.com/teletha/reincarnation/commit/de4ebca488bab366b925c96099898168232ce460))
* Hide some operand types. ([fa2db31](https://github.com/teletha/reincarnation/commit/fa2db3160db418b15e034c972a76aa67496ba44a))
* Move OperandUtil#isWrapper and #isUnwrapper to Classes. ([adcf70b](https://github.com/teletha/reincarnation/commit/adcf70bfffdfcd37eb0b1b27874c9e2bb7376b8f))
* Update ecj compiler. ([ce4a520](https://github.com/teletha/reincarnation/commit/ce4a5205630aa8ebb7b7194204ec27ba2dd81e55))

## [1.2.0](https://github.com/teletha/reincarnation/compare/v1.1.0...v1.2.0) (2023-03-13)


### Features

* (Java) Import classes are filtered, sorted and grouped. ([1627e9a](https://github.com/teletha/reincarnation/commit/1627e9a6c522aa2f29a522bcceab37ea68fb72c8))
* Optimize class literal for primitive types. ([35d9530](https://github.com/teletha/reincarnation/commit/35d9530b3fa1ed3e927ab8da20b6b4ecd0ab761e))
* Provide specialized ParameterizedType. ([5f1da9c](https://github.com/teletha/reincarnation/commit/5f1da9ca8b932aa6f7d51185345d847a8f7db481))
* Support all modifiers. ([ae17983](https://github.com/teletha/reincarnation/commit/ae1798328ab9e6d58be70f337b1f1465b64ed6bc))
* Support assert with message. ([375d034](https://github.com/teletha/reincarnation/commit/375d0347b788cd366f6db2ab480fd4a580c34c01))
* Support generics on type definition. ([5c811dd](https://github.com/teletha/reincarnation/commit/5c811dd1ec2636f014a88f036f1317532dbf72a7))
* Support generics partially. ([b3f0219](https://github.com/teletha/reincarnation/commit/b3f0219a174c3fd8aaec8249bf41a3c439e7f88b))
* Support lambda with contextual local variables. ([098d7f5](https://github.com/teletha/reincarnation/commit/098d7f550aa66e9f3069ed0e30bfed8938f85566))
* Support local class. ([5dcc7e1](https://github.com/teletha/reincarnation/commit/5dcc7e1667e1b53a427a3876999a9fe2aac040c9))
* Support method reference for array and constructor. ([1a47b33](https://github.com/teletha/reincarnation/commit/1a47b330b6c30295b4fa90234608e856236343cc))
* Support method reference. ([63aaa91](https://github.com/teletha/reincarnation/commit/63aaa91ac041a217c35ac24d2ebcfe7bf72a81d3))
* Support nested lambda with contextual variables. ([094c379](https://github.com/teletha/reincarnation/commit/094c379e87f710d4241a8927c3786d2187b7bc74))
* Support static method and private method on interface. ([9abb142](https://github.com/teletha/reincarnation/commit/9abb142aafe4b570a3fd1aa7ce40a176800aeaca))
* Support the bounded type variable on type definition. ([e7ec82a](https://github.com/teletha/reincarnation/commit/e7ec82adb9cc273ffe1fcf8f260e770c0ba47163))
* Support vararg. ([7759450](https://github.com/teletha/reincarnation/commit/775945052831481cf9a1a9cfa9de0df9b1b63bd6))


### Bug Fixes

* Capture assertion error if needed. ([ef5f894](https://github.com/teletha/reincarnation/commit/ef5f894bf399d89c0aa51b838fe1f0e92784bd96))
* Enable assertion on verify tests. ([16ea2cc](https://github.com/teletha/reincarnation/commit/16ea2cc2030865c04ec7b125a6fbe565f81caaf7))
* Optimize cast code. ([6321803](https://github.com/teletha/reincarnation/commit/6321803022607286ec359533035306c8618c0b5c))
* remove duplicated code ([55b50ba](https://github.com/teletha/reincarnation/commit/55b50ba28c33f57295e838e2f63fecc9861a29ed))

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
