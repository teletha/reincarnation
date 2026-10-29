# ICU data for Javet

`icudt78l.dat` is the ICU 78 little-endian data file used by the i18n-enabled
Javet 6.0.2 Node.js runtime in the JavaScript tests.

- Source: `caoccao/Javet` GitHub Actions artifact `icu-node` for Javet 6.0.2
  (workflow run `36609724673`)
- Location: `src/test/resources/icu/icudt78l.dat`
- Used by: `reincarnation.transpiler.JavetSupport`

Replace this file with the matching `icudt*.dat` when the Javet version is
upgraded. The file is test-only and is not included in the product.

The ICU data is distributed under the Unicode License Agreement.
