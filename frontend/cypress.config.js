const { defineConfig } = require('cypress');

module.exports = defineConfig({
  e2e: {
    viewportWidth: 1920,
    viewportHeight: 1080,
    baseUrl: 'http://127.0.0.1:3000',
    defaultCommandTimeout: 20000,
    video: false,
    experimentalStudio: true,
    experimentalModifyObstructiveThirdPartyCode: true,
    experimentalWebKitSupport: true,
    chromeWebSecurity: false,
  },
});
