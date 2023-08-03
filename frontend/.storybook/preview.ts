import type { Preview } from '@storybook/react';
import { withThemeFromJSXProvider } from '@storybook/addon-styling';
import GlobalStyles from '../src/styles/GlobalStyles';

const preview: Preview = {
  parameters: {
    actions: { argTypesRegex: '^on[A-Z].*' },
    controls: {
      matchers: {
        color: /(background|color)$/i,
        date: /Date$/,
      },
    },
  },
};

export const decorators = [withThemeFromJSXProvider({ GlobalStyles })];

export default preview;
