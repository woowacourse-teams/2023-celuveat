const { defineConfig } = require('cypress');

module.exports = defineConfig({
  e2e: {
    viewportWidth: 1920,
    viewportHeight: 1080,
    baseUrl: 'http://localhost:3000',
    defaultCommandTimeout: 3600000,
    video: true,
    experimentalStudio: true,
    experimentalModifyObstructiveThirdPartyCode: true,
    experimentalWebKitSupport: true,
    chromeWebSecurity: false,
  },
});
