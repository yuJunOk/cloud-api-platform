const { defineConfig } = require("@vue/cli-service");
const MonacoWebpackPlugin = require("monaco-editor-webpack-plugin");
module.exports = defineConfig({
  transpileDependencies: true,
  chainWebpack: (config) => {
    config.plugin("monaco-editor").use(MonacoWebpackPlugin, [
      {
        // Languages are loaded on demand at runtime
        languages: [
          "json",
          "html",
          "xml",
          "javascript",
          "css",
          "markdown",
          "yaml",
          "plaintext",
        ],
        features: [
          "format",
          "find",
          "contextmenu",
          "gotoError",
          "gotoLine",
          "gotoSymbol",
          "hover",
          "documentSymbols",
        ],
      },
    ]);
  },
});
