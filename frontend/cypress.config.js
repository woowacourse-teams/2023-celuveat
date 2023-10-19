const { defineConfig } = require('cypress');

module.exports = defineConfig({
  e2e: {
    viewportWidth: 1920,
    viewportHeight: 1080,
    baseUrl: 'https://dev.celuveat.com',
    defaultCommandTimeout: 4000000,
    video: false,
    experimentalStudio: true,
    experimentalModifyObstructiveThirdPartyCode: true,
    experimentalWebKitSupport: true,
  },
});
