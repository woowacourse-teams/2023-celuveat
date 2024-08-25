const path = require('path');
const Dotenv = require('dotenv-webpack');
const { merge } = require('webpack-merge');
const ForkTsCheckerWebpackPlugin = require('fork-ts-checker-webpack-plugin');

const common = require('./webpack.common');

module.exports = merge(common, {
  mode: 'production',
  devtool: false,
  module: {
    rules: [
      {
        test: /\.(js|jsx|ts|tsx)$/i,
        exclude: /node_modules/,
        loader: 'babel-loader',
        options: {
          presets: [
            [
              '@babel/preset-env',
              {
                targets: { browsers: ['last 2 versions', '>= 5% in KR'] },
                useBuiltIns: 'usage',
                corejs: {
                  version: 3,
                },
              },
            ],
            ['@babel/preset-react', { runtime: 'automatic' }],
            '@babel/preset-typescript',
          ],
          plugins: [
            [
              'babel-plugin-styled-components',
              {
                displayName: false,
                minify: true,
                transpileTemplateLiterals: true,
                pure: true,
              },
            ],
            [
              'babel-plugin-root-import',
              {
                rootPathPrefix: '~',
                rootPathSuffix: 'src',
              },
            ],
          ],
        },
      },
    ],
  },
  optimization: {
    splitChunks: {
      chunks: 'all',
    },
  },
  plugins: [
    new ForkTsCheckerWebpackPlugin(),
    new Dotenv({
      path: path.resolve(__dirname, `../.env.prod`),
    }),
  ],
});
