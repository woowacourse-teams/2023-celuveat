import { defineConfig } from 'cypress';

export default defineConfig({
  e2e: {
    viewportWidth: 1920,
    viewportHeight: 1080,
    baseUrl: 'https://dev.celuveat.com',
    defaultCommandTimeout: 40000,
    video: false,
    experimentalStudio: true,
    experimentalModifyObstructiveThirdPartyCode: true,
    experimentalWebKitSupport: true,
  },
});
