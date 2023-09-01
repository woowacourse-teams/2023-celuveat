import { defineConfig } from 'cypress';

export default defineConfig({
  e2e: {
    viewportWidth: 1920,
    viewportHeight: 1080,
    baseUrl: 'http://localhost:3000',
  },
});
